
For start app: 
а) First variant:
    1. Run mvn clean install
    2. Run mvn spring-boot:run
b) Second variant:
    1. Open project in IDEA as new project
    2. Add and run SpringBoot configuration

    
Register User
curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/user/register/ -v

View popular movies
curl -X POST --data "email=test@test2.com&password=test" http://localhost:8080/movie/popular/ -v

Add favorite actor
curl -X POST --data "email=test@test2.com&password=test&actorName=Tom Cruise" http://localhost:8080/favoriteActor/add/ -v

Remove favorite actor
curl -X POST --data "email=test@test2.com&password=test&actorName=Tom Cruise" http://localhost:8080/favoriteActor/remove/ -v

Mark movie watched
curl -X POST --data "email=test@test2.com&password=test&movieName=Oblivion" http://localhost:8080/movie/watchedMovie/ -v

Mark movie unwatched
curl -X POST --data "email=test@test2.com&password=test&movieName=Oblivion" http://localhost:8080/movie/unwatchedMovie/ -v

Search unwatched movies with favorite actors by date
curl -X POST --data "email=test@test2.com&password=test&year=2013&month=04" http://localhost:8080/movie/findMovies/ -v

