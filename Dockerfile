FROM openjdk:11
MAINTAINER Brian Onchari
RUN mkdir /daraja-api
COPY target/daraja_api-0.0.1-SNAPSHOT.jar/ /daraja-api
WORKDIR /daraja-api
CMD java -classpath src/main/java/com/safaricom/daraja_api/DarajaApiApplication.java
