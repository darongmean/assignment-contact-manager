_default:
    @just --list

run-mail-server:
    docker start smtp4dev 2>/dev/null || docker run --name smtp4dev  -d -p 1080:80 -p 1025:25 rnwood/smtp4dev:v3

run: run-mail-server
    sbt run
