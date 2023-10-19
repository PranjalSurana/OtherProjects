# Node RESTful Widget API

A Node.js RESTful API for CRUD operations on a widget database.

This is a Node.js version of the Spring Boot widget REST service. 

This version uses a stub DAO. Examples in Chapter 8 use a DAO that
queries an Oracle database.

## Project Structure

The API is defined in rest-controller.js. 

## Running the project

    $ npm install
    $ node src/rest-controller/rest-controller.js

    Now send GET/POST/PUT/DELETE requests to http://localhost:3000

## TODO

Add integration specs for the server API
