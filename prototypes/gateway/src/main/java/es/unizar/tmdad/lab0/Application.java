package es.unizar.tmdad.lab0;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import es.unizar.tmdad.lab0.messaging.GatewayRPCServer;

//@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) {
    	//Launches Spring -> Rest Service
        SpringApplication.run(Application.class, args);
        
        //Launches Rabbit Client -> Messaging Service
        GatewayRPCServer.launch();
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
    
    private static Class<Application> applicationClass = Application.class;
}
