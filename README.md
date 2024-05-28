<img src="https://img.shields.io/badge/apache tomcat-20232a.svg?style=for-the-badge&logo=apachetomcat&logoColor=F8DC75" />

<img src="https://img.shields.io/badge/spring boot-20232a.svg?style=for-the-badge&logo=springboot&logoColor=6DB33F" /> <img src="https://img.shields.io/badge/spring security-20232a.svg?style=for-the-badge&logo=springsecurity&logoColor=6DB33F" /> <img src="https://img.shields.io/badge/gradle-20232a.svg?style=for-the-badge&logo=gradle&logoColor=02303A" />

<img src="https://img.shields.io/badge/mysql-20232a.svg?style=for-the-badge&logo=MySQL&logoColor=4479A1" />

<img src="https://img.shields.io/badge/docker-20232a.svg?style=for-the-badge&logo=docker&logoColor=2496ED" /> <img src="https://img.shields.io/badge/git-20232a.svg?style=for-the-badge&logo=git&logoColor=F05032" /> <img src="https://img.shields.io/badge/github-20232a.svg?style=for-the-badge&logo=github&logoColor=181717" /> <img src="https://img.shields.io/badge/github actions-20232a.svg?style=for-the-badge&logo=githubactions&logoColor=2088FF" /> <img src="https://img.shields.io/badge/dependabot-20232a.svg?style=for-the-badge&logo=dependabot&logoColor=025E8C" /> 

<img src="https://img.shields.io/badge/thymeleaf-20232a.svg?style=for-the-badge&logo=thymeleaf&logoColor=005F0F" /> <img src="https://img.shields.io/badge/java script-20232a.svg?style=for-the-badge&logo=javascript&logoColor=F7DF1E" /> <img src="https://img.shields.io/badge/css3-20232a.svg?style=for-the-badge&logo=css3&logoColor=1572B6" /> <img src="https://img.shields.io/badge/html5-20232a.svg?style=for-the-badge&logo=html5&logoColor=E34F26" /> 

<img src="https://img.shields.io/badge/visual studio code-20232a.svg?style=for-the-badge&logo=visualstudiocode&logoColor=007ACC" /> <img src="https://img.shields.io/badge/intellij idea-20232a.svg?style=for-the-badge&logo=intellijidea&logoColor=000000" /> 

<img src="https://img.shields.io/badge/macos-20232a.svg?style=for-the-badge&logo=apple&logoColor=000000" /> <img src="https://img.shields.io/badge/windows 11-20232a.svg?style=for-the-badge&logo=windows11&logoColor=0078D4" /> 

<img src="https://img.shields.io/badge/homebrew-20232a.svg?style=for-the-badge&logo=homebrew&logoColor=FBB040" /> <img src="https://img.shields.io/badge/iterm2-20232a.svg?style=for-the-badge&logo=iterm2&logoColor=000000" /> <img src="https://img.shields.io/badge/windows terminal-20232a.svg?style=for-the-badge&logo=windowsterminal&logoColor=4D4D4D" /> 

<img src="https://img.shields.io/badge/slack-20232a.svg?style=for-the-badge&logo=slack&logoColor=4A154B" /> 

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
>    2번째 명령어 입력시 출력
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

# HOW TO USE GIT FLOW
## feature 추가할 때
```bash
git flow feature start <feature name>
```
해당 명령어 실행시 현재 브랜치의 위치와 상관없이 develop 브랜치를 기반으로 feature/<feature name> 브랜치 생성
기능을 추가해야할 때 사용하면 된다. 평소에 가장 많이 사용될 것으로 기대된다.
## 완료한 feature 브랜치 develop 브랜치에 적용
```bash
git flow feature finish <feature name>
```
해당 명령어 실행시 feature/<feature name> 브랜치를 자동으로 develop 브랜치에 병합한다. 우리는 pull request로 프로젝트를 진행할 예정이기 때문에 해당 명령어는 사용할 일이 없을 것 같다.
위 명령어 대신 feature/<feature name> 브랜치에서 `gh pr create --base develop --title "Update example feature" --body "This pull request updates the feature example."` 형식으로 작성하면 된다.
또는 github 웹 사이트에서 pull request 요청을 해도 된다.
## 원격 레포지토리에 있는 feature 가져오기
```bash
git flow feature pull origin <feature name>
```

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


