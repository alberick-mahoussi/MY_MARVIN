FROM jenkins/jenkins:latest
WORKDIR /marvin
COPY . /marvin
RUN /usr/local/bin/install-plugins.sh cloudbees-folder configuration-as-code credentials github job-dsl script-security structs role-strategy ws-cleanup
COPY ./my_marvin.yml /var/jenkins_home/casc_configs/