pipeline {
    agent any

    environment {
        PROJECT_ID = 'project-curiosity-428718'
        CLUSTER_NAME = 'dev-cluster'
        CLUSTER_ZONE = 'us-east1-b'
        NAMESPACE = 'dev-cluster'
        GCR_KEY_PATH = '/var/secrets/google/service-account-key.json'
        IMAGE_NAME = "gcr.io/${PROJECT_ID}/demo-app"
        GITHUB_CREDENTIALS_ID = "github-secret"
    }

    stages {
        stage('Clean Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                script {
                    // Checkout the project from GitHub
                    checkout([$class: 'GitSCM',
                              branches: [[name: '*/main']],
                              userRemoteConfigs: [[url: 'https://github.com/bhuvaneshshukla1/demo-app.git',
                                                   credentialsId: env.GITHUB_CREDENTIALS_ID]]])
                }
            }
        }

        stage('Set Permissions') {
            steps {
                script {
                    // Set execute permissions on the mvnw script
                    sh 'chmod +x mvnw'
                }
            }
        }

        stage('Build Jar') {
            steps {
                script {
                    // Build the project and generate the JAR file
                    sh './mvnw clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
                    sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // Authenticate to GCR using the mounted secret
                    sh "gcloud auth activate-service-account --key-file=${GCR_KEY_PATH}"
                    sh "gcloud auth configure-docker"

                    // Push the Docker image to GCR
                    sh "docker push ${IMAGE_NAME}:${BUILD_NUMBER}"
                }
            }
        }

        stage('Deploy to GKE') {
            steps {
                script {
                    // Authenticate to GCP using the mounted secret
                    sh "gcloud auth activate-service-account --key-file=${GCR_KEY_PATH}"

                    // Set the GCP project
                    sh "gcloud config set project ${PROJECT_ID}"

                    // Get credentials for GKE
                    sh "gcloud container clusters get-credentials ${CLUSTER_NAME} --zone ${CLUSTER_ZONE}"

                    // Create the namespace if it doesn't exist
                    sh 'kubectl apply -f deployment-configs/namespace.yaml'

                    // Deploy the application to the specified namespace
                    sh 'kubectl apply -f deployment-configs/deployment-app.yaml'
                    sh 'kubectl apply -f deployment-configs/service.yaml'
                }
            }
        }
    }
}
