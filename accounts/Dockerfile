### instructions
# build the image from the dockerfile with name accountName/imageName:tag
# docker build . -t fdifrison/accounts:0.0.1
# run in detached mode (externalPort:containerPort)
# docker run -d -p 8080:8080 fdifrison/accounts:0.0.1
# inspect running containers
# docker ps


#java version
FROM openjdk:17-jdk-slim
# info on the image mantainer
MAINTAINER fdifrison
# take the jar and copy it in the dockerimage
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
# command to lunch the jar
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]