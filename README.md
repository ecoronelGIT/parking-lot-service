# Parking Lot Service

## Exercise
1. The parking lot has 25 total spaces
2. The parking lot can hold motorcycles, cars and vans
3. The parking lot has motorcycle spots, compact car spots and regular spots
4. A car can park in a single compact spot, or a regular spot
5. A van can park, but it will take up 3 regular spots (for simplicity we don't need to make
   sure these spots are beside each other)
   Acceptance Criteria

* Service endpoints
   a. Park vehicle
   b. Vehicle leaves parking lot
   c. Find how many spots are remaining
   d. Check if all parking spots are taken for a given vehicle type

### Assumptions
For the purpose of this exercise, I will consider the motorcycle spot, the single compact spot, and the regular spot as equivalent.
All spot will be created as Regular.


## Explanation
For this exercise we are going to create 25 REGULAR parking lot spots. When a vehicle is park, is going to take one spot available.
When a vehicle is unpark is going to set to null the relation and delete the vehicle from the database.

We are going to have 2 entities, Vehicle and ParkingSpot. Vehicle has a list of ParkingSpot and a ParkingSpot has a Vehicle.

Project Structure:
 - api: All related to Rest endpoints
 - common: Class in common for all packages
 - core: Businesses decisions
 - domain: Model fo the project

Test:
 Used mockServer to call the server ante test the API. 
 - Test database saved data
 - Test API response
 - Test Errors
 - Test all the possible flows

## Technologies
 - JDK 21
 - SpringBoot 3.2.1
 - H2
 - Actuator
 - MockServer
 - Flyway

## URLs

 - Project:  http://localhost:8080/parking-lot
 - Swagger: http://localhost:8080/parking-lot/swagger-ui/index.html
 - H2: http://localhost:8080/parking-lot/h2-console/login.jsp

## Database
 The project use Flyway for the initialization.
 
## How to run it

- Set Java home for JDK 21
- run `./gradlew build`
- run `java -jar build/libs/parking-lot-service-0.0.1-SNAPSHOT.jar`