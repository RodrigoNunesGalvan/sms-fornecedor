FROM openjdk:8-jdk-alpine

ENV TZ='GMT-3'

VOLUME /tmp

EXPOSE 8083

ARG JAR_FILE=target/*.jar

ADD ${JAR_FILE} sms-fornecedor.jar

ENTRYPOINT ["java","-Xmx1G","-jar","/sms-fornecedor"]
