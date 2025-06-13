compose:
	docker build -t emiren-co/fqworkstation-root:latest .
	cd docker && docker-compose up --build
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

add-hosts:
	@if ! grep -q "127.0.0.1 fqw.rudn.ru" /etc/hosts; then \
		echo "127.0.0.1 fqw.rudn.ru" | sudo tee -a /etc/hosts; \
	fi
docker-remove:
	cd docker && docker-compose down && docker rmi docker-fqworkstation-builder-1

test-load:
	mvn gatling:test

release:
	@if [ -z "$version" ]; then \
	    echo "Error: No version specified. Use 'make github-action version=<tag_name>'"; \
	    exit 1; \
	fi
	git tag v$(version)
	git push origin --tags
	@echo "Created and pushed tag: v$(version)"

minikube:
    kubectl apply -f fqworkstation.yaml

all: add-hosts compose

.PHONY: compose docker-build docker all add-hosts
