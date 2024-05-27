> [!IMPORTANT]
> 
> # essential(필수)
> 1. git
> 2. git-flow
> 3. java-version21
> 4. gradle-version8.7
> 5. mysql-version8.3

> [!NOTE]
> # Optional(선택)
> 1. docker
> 2. docker-compose
> 3. makefile-CLI

> [!IMPORTANT]
> # LOCAL REPOSITORY SETTING GUIDE
> 1. `git clone <git_addr>` or `gh repo clone <git_addr>`
> 2. `git flow init` or `git flow init -f`
> 3. ```bash
>    Which branch should be used for bringing forth production releases?
>    - develop
>    - main
>    Branch name for production releases: [main] 
> 
>    Which branch should be used for integration of the "next release"?
>    - develop
>    Branch name for "next release" development: [develop] 
> 
>    How to name your supporting branch prefixes?
>    Feature branches? [feature/] 
>    Release branches? [release/] 
>    Hotfix branches? [hotfix/] 
>    Support branches? [support/] 
>    Version tag prefix? [ver.]
>    ```

>[!TIP]
> # EXECUTION GUIDE
> ## LOCAL
> `gradle bootRun`
> ## DOCKER
> 1. dockerfile 및 docker-compose.yml 파일로 이미지 및 컨테이너를 빌드하고 서버를 실행한다. `make docker-up`
> 2. 서버를 종료하고 docker-up으로 생겨난 컨테이너를 삭제한다. (이미지와 볼륨은 유지한다.) `make docker-down`
> 3. 서버를 실행한다. 만약 컨테이너가 존재하지 않는다면 docker-up이 선행되어야 한다. `make docker-start`
> 4. 서버를 종료한다. 어떤 리소스도 삭제하지 않는다. `make docker-stop`
> 5. dockerfile 및 docker-compose.yml로 생겨난 모든 리소스를 삭제한다. `make docker-clean`
