FROM maven:latest
MAINTAINER srivasme@everis.com

COPY ./target /app 
RUN cd /app/
EXPOSE 8080
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "customer-0.0.1-SNAPSHOT.jar"]