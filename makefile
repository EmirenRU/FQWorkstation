ifeq ($(OS),Linux)

compose:
	docker build -t emiren-co/fqworkstation-root:latest .
	cd docker && docker-compose up --build --pull=never
build:
	mvn package
start-nginx:
	nginx -c /config/nginx.conf
docker:
	mvn clean package
	mkdir -p docker-build/docker docker-build/fqw docker-build/support docker-build/protocol docker-build/frontend
	cp fqw/target/*.jar docker-build/fqw/target/app.jar
	cp support/target/*.jar docker-build/support/target/app.jar
	cp protocol/target/*.jar docker-build/protocol/target/app.jar
	rm -rf ./frontend/node_modules ./frontend/dist
	cp -r frontend/* docker-build/frontend/
7z:
	7z a -mx=9 -m0=lzma2 docker-build.7z ./docker-build/

else ifeq ($(OS),Windows_NT)

react-install:
	cd frontend ; npm install
react:
	cd frontend ; npm run dev
compose:
	#docker build -t emiren-co/fqworkstation-root:latest .
	cd docker ; docker-compose up --build
compose-down:
	cd docker ; docker-compose down
k8c:
	docker build -t emiren-co/fqworkstation-root:latest .
	docker build -t emiren-co/fqworkstation-frontend:latest ./frontend
	minikube start  --driver=docker  --cache-images
	kubectl apply -f ./fqworkstation.yaml --validate=false
	kubectl get all
build:
	mvn package
start-nginx:
	nginx.exe -c /config/nginx.conf
docker:
	mvn clean package
	mkdir -p docker-build/docker docker-build/fqw docker-build/support docker-build/protocol docker-build/frontend
	cp fqw/target/*.jar docker-build/fqw/target/app.jar
	cp support/target/*.jar docker-build/support/target/app.jar
	cp protocol/target/*.jar docker-build/protocol/target/app.jar
	rm -rf ./frontend/node_modules ./frontend/dist
	cp -r frontend/* docker-build/frontend/
	7z a -mx=9 -m0=lzma2 docker-build.7z ./docker-build
7z:
	7z a -mx=9 -m0=lzma2 docker-build.7z ./docker-build/
else

compose:
	@echo "Not Supported OS"

endif

.PHONY: docker docker-build

test-load:
	mvn gatling:test

all: compose

.PHONY: compose docker-build docker all
