# Driver - advice and best practice for drivers
A simple application providing users with advice regarding the road traffic safety 
<br><br>
## Table of contents
* [Intro](#intro)
* [Technology](#technology)
* [Features](#features)
<br><br>
## Intro
The application is meant to be a training solution for drivers, increasing the overall road safety through involving them into the process of improving their car driving skills. It allows users to get familiar with various safe driving best practices and check their knowledge gains by solving related quizes.<br>
The application provides the <b>REST API</b> and has been prepared as the Coders Lab's 'Java Developer: Web' course graduation project.
<br><br>
## Technology
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JSON Web Token
* MySQL DB
* Jackson
* Lombok
* Swagger
<br><br>
## Features
#### Not authenticated
* Register new account
* Authenticate (log in)
#### Authenticated user
* Get unread articles
* Get newest article, most popular articles, the article of the week
* Post own articles, edit them and delete
* Solve quizes regarding particular articles
* Get list of existing forums topics
* Look for an interesting topic via the search engine
* Post messages/replies in existing topics, edit and delete one's messages
* Start a new forums topic
#### Authenticated admin
* CRUD operations on articles, quizes and their answers
* CRUD operations on forums topics and messages
* CRUD operations on users and their roles

