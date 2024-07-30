# Dockerfile
FROM jenkins/jenkins:lts

USER root

# Install Docker
RUN apt-get update && \
    apt-get install -y docker.io && \
    rm -rf /var/lib/apt/lists/*

# Install Google Cloud SDK and kubectl
RUN echo "deb [signed-by=/usr/share/keyrings/cloud.google.gpg] http://packages.cloud.google.com/apt cloud-sdk main" | tee -a /etc/apt/sources.list.d/google-cloud-sdk.list && \
    apt-get install -y apt-transport-https ca-certificates gnupg && \
    curl https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key --keyring /usr/share/keyrings/cloud.google.gpg add - && \
    apt-get update -y && \
    apt-get install -y google-cloud-sdk kubectl && \
    rm -rf /var/lib/apt/lists/*

# Install gke-gcloud-auth-plugin
RUN apt-get update && apt-get install -y google-cloud-sdk-gke-gcloud-auth-plugin

USER jenkins
