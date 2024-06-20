.PHONY: docker-up docker-down docker-clean gradle

# dockerfile 및 docker-compose.yml 파일로 이미지 및 컨테이너를 빌드하고 서버를 실행한다.
docker-up:
		docker-compose -f ./docker/docker-compose-prod.yml up --build

# 서버를 종료하고 docker-up으로 생겨난 컨테이너를 삭제한다. (이미지와 볼륨은 유지한다.)
docker-down:
		docker-compose -f ./docker/docker-compose-prod.yml down

# dockerfile 및 docker-compose.yml로 생겨난 모든 리소스를 삭제한다.
docker-clean:
		docker-compose -f ./docker/docker-compose-prod.yml down --rmi all --volumes --remove-orphans

gradle:
		./gradlew clean build -Dspring.profiles.active=prod -x test