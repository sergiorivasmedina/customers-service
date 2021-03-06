FROM openjdk:8-alpine
LABEL maintainer="Sergio Rivas Medina -> srivasme@everis.com"

COPY ./target /app 
RUN cd /app/
EXPOSE 8081
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "customer-0.0.1-SNAPSHOT.jar"]