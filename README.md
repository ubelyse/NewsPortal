# Organisational News Portal

### Author
Belyse Inema

## About 
- a rest REST API for querying and retrieving scoped news and information. There should be news/articles/posts that are available to all employees without navigating into any department, and others that are housed/classified within departments

## Setup Requirements
To clone this repository :
* IntelliJ IDEA.
* postgresql.
- jdk 8 and above
- gradle 6. and above

## Setup Instructions

 ###### fork and clone the project from github
###### Run the following scripts to create the postgres database;
 - CREATE DATABASE newsportal;
 - \c newsportal;
 - CREATE TABLE departments (
   id SERIAL PRIMARY KEY,
   name VARCHAR,
   description VARCHAR,
   size int
   );
 - CREATE TABLE news (
   id SERIAL PRIMARY KEY,
   news_type VARCHAR,
   department_id INT,
   user_id INT,
   title VARCHAR,
   description VARCHAR
   );
 - CREATE TABLE staff (
   id SERIAL PRIMARY KEY,
   name VARCHAR,
   staff_position VARCHAR,
   staff_role VARCHAR
   );
 - CREATE TABLE users_departments (
   id SERIAL PRIMARY KEY,
   user_id INT,
   department_id INT
   );
 - CREATE DATABASE newsportal_test WITH TEMPLATE newsportal;
 
 - run the command "gradle run" from the root project folder
- 

## Technologies Used

* Java
* Spark
* HTML5, CSS3 and Bootstrap
* JUnit



## License and Copyright information

This project is licensed under the MIT License.

The MIT License (MIT)

Copyright (c) 2020, Belyse Inema.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


## Contact information
 - For queries ,comments compliments etc ,feel free to reach out to Belyse on the following platforms
  
    #### Instagram :
    * ###### @belyse_paonne
    
    #### Email:
    * ###### ubelyse1@gmail.com