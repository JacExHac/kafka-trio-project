# kafka-trio-project
HOW TO USE THIS PROJECT:

1. USE DOCKER COMMAND docker network create kafka-trio-project-net
2.RUN THESE COMMANDS:


 KAFKA-SERVER:
		docker run --net kafka-trio-project-net --name kafka-server -e EULA="https://licenses.lenses.io/d/?id=82e1b9bf-249d-11ee-8f1e-42010af01003" --rm -p 3030:3030 -p 9092:9092 -p 2181:2181 lensesio/box

CLIENT:
	./mvnw package
	docker build -f src/main/docker/Dockerfile.jvm -t quarkus/client-jvm .
	docker run --name client --net kafka-trio-project-net -e KAFKA_BOOTSTRAP_SERVERS=kafka-server:9092 -e APP_IP=client -i --rm -p 8080:8080 quarkus/client-jvm


WEATHER-SERVICE:
	./mvnw package
	docker build -f src/main/docker/Dockerfile.jvm -t quarkus/weather-service-jvm .
	docker run --name weather-service --net kafka-trio-project-net -e SERVICE_IP=weather-service -e KAFKA_BOOTSTRAP_SERVERS=kafka-server:9092 -i --rm -p 8081:8080 quarkus/weather-service-jvm


LOG-TRACKER-GENERATOR:
	./mvnw package
	docker build -f src/main/docker/Dockerfile.jvm -t quarkus/log-tracker-generator-jvm .
	docker run --name log-tracker-generator --net kafka-trio-project-net -e KAFKA_BOOTSTRAP_SERVERS=kafka-server:9092 -i --rm -p 8082:8080 quarkus/log-tracker-generator-jvm
