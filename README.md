Spring Music
============

This is a sample application for using database services on [Cloud Foundry](http://cloudfoundry.org) with the [Spring Framework](http://spring.io) and [Spring Boot](http://projects.spring.io/spring-boot/).


## Building

This project requires Java 8 to compile. It will not compile with Java 9 or later.

To build a runnable Spring Boot jar file, run the following command: 

~~~
$ ./gradlew clean assemble
~~~

## Persistence 
spring-music1 runs 

|env|bound service|persistence|
|---|-------------|-----------|
|local|-|in memory H2 DB|
|CF|-|in memory H2 DB|
|CF|bound Relational DB service|Relational DB|

 
 
 