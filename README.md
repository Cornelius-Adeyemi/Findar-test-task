# Book Store management



## Getting started

To run this project on your local, please set the following environment variables for your MySQL database 

- [ ] DATABASE_PORT - your database port 
- [ ] DATABASE_NAME  - your database name
- [ ] DATABASE_PASSWORD - your database password
- [ ] DATABASE_USERNAME - database username

## Features

  In this test task, users can have one of these roles- ADMIN or USER

#### ADMIN -An admin can do the following:

- [ ] add book 
- [ ] delete book
- [ ] update book details 
- [ ] and every other things a user role can do


- To register an admin use this endpoint - http://localhost:8080/api/v1/admin/auth/register with the passcode "I love God"


#### USER -A user can do the following:

- [ ] borrow a book. A user can only borrow if the user doesn't have an outstanding book to return
- [ ] return a book. A user can only return a book they borrow
- [ ] get a book details
- [ ] get list of book details


## Testing
To test the endpoints visit swagger UI - http://localhost:8080/swagger-ui/index.html
