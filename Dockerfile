FROM maven:latest
MAINTAINER srivasme@everis.com

COPY . /app 
RUN cd /app/ && mvn clean package
EXPOSE 8080
WORKDIR /app/target
ENTRYPOINT ["java", "-jar", "customer-0.0.1-SNAPSHOT.jar"]