package es.unizar.tmdad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.unizar.tmdad.messaging.TokenizerRPCServer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	//Launches Spring -> Rest Service
        SpringApplication.run(Application.class, args);
        
        //Launches Rabbit Client -> Messaging Service
        TokenizerRPCServer.launch();
    }
}
