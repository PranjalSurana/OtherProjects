# Node RESTful Widget API

A Node.js RESTful API for CRUD operations on a widget database.

This is a Node.js version of the Spring Boot widget REST service. 
It communicates with the Oracle XE instance running on the localhost.

## Project Structure

The API is defined in server.js. 

The server accesses Oracle using the DAO defined in dao/widget-dao.js. 
Specs for the DAO are in dao/spec/widget-dao.spec.js

## Running the project

    $ npm install
    $ npm test
    $ node server.js

    Now send GET/POST/PUT/DELETE requests to http://localhost:3000
