# builder step used to download and configure spark environment
ARG debian_buster_image_tag=8-jre-slim
FROM openjdk:${debian_buster_image_tag}

# -- Layer: OS + Python 3.7
ARG shared_workspace=/opt/workspace
RUN mkdir -p ${shared_workspace} && \
    apt-get update && \
    apt-get install -y python3 && \
    ln -s /usr/bin/python3 /usr/bin/python && \
    rm -rf /var/lib/apt/lists/*
    
ENV SHARED_WORKSPACE=${shared_workspace}

#RUN update-alternatives --install "/usr/bin/python" "python" "$(which python3)" 1

# -- Runtime
VOLUME ${shared_workspace}
CMD ["bash"]