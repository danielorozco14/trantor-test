# trantor-test
Messaging REST API Developed with Spring Boot, H2, JPA and Spring Security.

-The application is developed to be accessed from an HTTP client.

-There are two users configured. **user** and **admin**. Each one has the same password which is: **password**  .

-The application is made with the H2 database with persistence in file configuration, for simplicity reasons.

-Spring Security has been configured and following all security standards.

-In order to test it I suggest to first import the environment to Postman and then the collection. 

-The authentication of the API endpoints is with Basic Auth so two HTTP clients are needed for a smooth communication if you don't want to change credentials for each message sending request.

**NOTE**: 
1. As I mentioned before, the security measures implemented include security against CSRF attacks so **POST**, **PUT**, **DELETE** requests will give error 403 Forbidden due to the lack of the CSRF token.
2. To avoid this error, within the collection, the **GET** /chat/read request includes a TEST Script that traps the CSRF token and places it as an environment variable in POSTMAN. So for any other request to the endpoints, you must first make a **GET** /chat/read request. This is mandatory to do at least once before making requests. It is not necessary to do it always, only until the token expires and it is necessary to make another **GET** /chat/read request to renew it.

