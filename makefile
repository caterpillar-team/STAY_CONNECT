.PHONY: docker-up docker-down docker-start docker-stop docker-clean docker-init

# dockerfile 및 docker-compose.yml 파일로 이미지 및 컨테이너를 빌드하고 서버를 실행한다.
docker-up:
		docker-compose -f ./docker/docker-compose.yml up --build

# 서버를 종료하고 docker-up으로 생겨난 컨테이너를 삭제한다. (이미지와 볼륨은 유지한다.)
docker-down:
		docker-compose -f ./docker/docker-compose.yml down

# 서버를 실행한다. 만약 컨테이너가 존재하지 않는다면 docker-up이 선행되어야 한다.
docker-start:
		docker-compose -f ./docker/docker-compose.yml start app-bootrun mysql

# 서버를 종료한다. 어떤 리소스도 삭제하지 않는다.
docker-stop:
		docker-compose -f ./docker/docker-compose.yml stop app-bootrun mysql

# dockerfile 및 docker-compose.yml로 생겨난 모든 리소스를 삭제한다.
docker-clean:
		docker-compose -f ./docker/docker-compose.yml down --rmi all --volumes --remove-orphans

# docker의 모든 인스턴스 삭제
docker-init:
		docker system prune -a --volumes -f