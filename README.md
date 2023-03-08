# Home Assignment

---

### Frontend

+ Simple interface consisting of two blocks
+ Button to download new tickets from csv file
+ Ticket validation input form with reactive header depending on response
+ List of all events with ticket statistics

---

### Backend
+ API: localhost:8080/api
+ Data Access Object for each database entity
+ JDBCTemplate is used to query the database.

---

### Database

+ The MySQL database runs in a docker container as a compose service
+ In order to run database, go to the backend directory and type `docker-compose up -d`
+ Username: test, Password: test
+ Liquibase was used for initial database management (tables creation and initial events)

---

###Classificators

#### Event Type
+ 0: Music
+ 1: Theatre
+ 2: Sports

#### Ticket State
+ 0: Obtainable
+ 1: Sold
+ 2: Validated