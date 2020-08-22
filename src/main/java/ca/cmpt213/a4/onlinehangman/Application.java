package ca.cmpt213.a4.onlinehangman;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Application class starts the SpringBoot Tomcat web server on the localhost:8080
 * This class calls the Controller class to access the endpoints.
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }
}
