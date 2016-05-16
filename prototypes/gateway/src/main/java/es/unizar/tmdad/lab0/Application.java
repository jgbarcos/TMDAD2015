package es.unizar.tmdad.lab0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.unizar.tmdad.lab0.messaging.GatewayRPCServer;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	//Launches Spring -> Rest Service
        SpringApplication.run(Application.class, args);
        
        //Launches Rabbit Client -> Messaging Service
        GatewayRPCServer.launch();
    }
}
