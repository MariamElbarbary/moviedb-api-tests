# moviedb-api-tests
This repo is for testing  api tests for some endpoints in the https://www.themoviedb.org/

The manual test cases are written in the TestCases folder

To Run the project make sure you have java and maven installed

cd to MovieApiAutomation then run
mvn clean install
mvn test

To run the tests in a docker container
make sure you have docker installed 

to build the image from the dockerfile in the project run
`docker build -t maventest:apiTests .`
then run`docker images` 
and check that the image with the tag apiTests is created

To run docker in an interactive mode with bash terminal
`docker run -it --name apiTestcontainer maventest:apiTests /bin/bash
`
then run `mvn clean test` to execute the tests

if the container is already running and you want to execute the tests again run
`docker exec -it apiTestcontainer mvn clean install
`
In performance/ a performance test is written  with Jmeter for GET toprated movies endpoint
In order to run the test
## Prerequisites 
- apache Jmeter is downloaded
- run command `jmeter -n -t getMovies.jmx -l result.csv -e -o output`
- the report index.html will be stored in the output folder

