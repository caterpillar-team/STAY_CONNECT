.PHONY: docker-up docker-down

docker-up:
		docker-compose -f ./docker/docker-compose.yml up --build

docker-down:
		docker-compose -f ./docker/docker-compose.yml down

docker-start:
		docker-compose -f ./docker/docker-compose.yml start app-bootrun mysql

docker-stop:
		docker-compose -f ./docker/docker-compose.yml stop app-bootrun mysql