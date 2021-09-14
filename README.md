# Contact Manager

A multi-user web application that allows the logged in user to create and 
maintain a list of his contacts. 

The app should send an email to the user when there is a contact with an upcoming birthday.
The email should be sent X hours before the birthday where X is configurable by the user.

## Requirement

- docker
- sbt
- java 11

## How to run

```shell
just run
```
or
```shell
docker start smtp4dev 2>/dev/null || docker run --name smtp4dev  -d -p 1080:80 -p 1025:25 rnwood/smtp4dev:v3
sbt run
```

The server will be available at http://localhost:9000 and mail server will be available at http://localhost:1080.
