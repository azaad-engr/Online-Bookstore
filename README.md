# Online Bookstore
## _By Moinudeen Ali Azad_

Online Book Store Application is basically allows to user to manage the bookstore with set of CRUD API's.

## Features

- Add Book to Store
- Get Book,Get All Books in Store
- Update & Delete Book from store
- Checkout Book(s) compute the total payable amount after applying discounts if any based on the promo code

## Technical Consideration

Dillinger uses a number of open source projects to work properly:

- [SpingBoot](https://spring.io/projects/spring-boot) - Springboot is used developing this application
- [JDK 11](https://jdk.java.net/11/) - Used for Development bundled with [IntelliJ](https://www.jetbrains.com/idea/) IDE
- In memory DB is used for demonstration purposes.
- Open API Configuration and Documentation is added which can be accessible using [URL](http://localhost:8080/swagger-ui.html) 
- Test cases are added for MVC and service layer using mockMvc and mockito.
- Controller Advise is added for better error handling.
- Custom response entity object added to follow the uniformity across application api's.
- Logger is added in case of any monitoring/debugging required.
- Minimal JSR validations are added on the request params.

## Installation

To start the springboot application

Using maven springboot plugin
```sh
cd Online-Bookstore
mvn clean install
mvn spring-boot:run
```

Using normal bootstarp

```sh
cd Online-Bookstore
mvn clean install
java -jar target\bookstore-0.0.1-SNAPSHOT.jar com.online.bookstore.BookstoreApplication
```
## License

Free to use :)