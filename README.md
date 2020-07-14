# Everis project1 customers

Java Spring Boot Backend connected to mongodb.

### Command for run Dockerfile and start container
cd /project1-customers

docker build -t "customers"

docker run --restart always --name custumers -8080:9000 -d costumers:latest

### Sonarqube
docker run -d --name sonarqube -p 8090:9000 sonarqube
