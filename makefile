ifeq ($(OS),Linux)
KAFKA_PATH=/usr/lib/kafka
compose:
    cd docker && docker-compose up --build
kafka:
     cd docker/src && docker-compose up --build
create-kafka:
	cd ${KAFKA_PATH} && /bin/zookeeper-server-start /config/zookeeper.properties ; /bin/kafka-server-start /config/server.properties ; /bin/kafka-topics --create --bootstrap-server localhost:9092 --partitions 5 --topic info-system-sql-event

else ifeq ($(OS),Windows_NT)
KAFKA_PATH=C:\\SDK\\kafka
compose:
	cd docker ; docker-compose up --build
exec-zookeeper:
	${KAFKA_PATH}//bin//windows//zookeeper-server-start.bat ${KAFKA_PATH}//config//zookeeper.properties ;
exec-kafka:
	${KAFKA_PATH}//bin//windows//kafka-server-start.bat ${KAFKA_PATH}//config//server.properties
create-kafka-topic:
	 ${KAFKA_PATH}//bin//windows//kafka-topics.bat  --create --bootstrap-server localhost:9092 --partitions 5 --topic info-system-sql-event
# Created for debugging
kafka:
	cd docker/kafka ; docker-compose up --build

else
compose:
    @echo "Not Supported OS"
endif

# before it `build` then run this
rundocker:
    docker run -t java-server:latest

# to exec the Spring Boot Application
exec:
    mvn spring-boot:run

# To Create a Jar in 'target' folder
build:
    mvn spring-boot:build-image

all: compose build rundocker

.PHONY: compose rundocker all
