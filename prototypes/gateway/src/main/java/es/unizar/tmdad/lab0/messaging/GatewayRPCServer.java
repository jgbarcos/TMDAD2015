package es.unizar.tmdad.lab0.messaging;

import java.io.IOException;
import java.net.URI;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;

import es.unizar.tmdad.lab0.model.PieceOfBook;
import es.unizar.tmdad.lab0.service.SerarchService;

public class GatewayRPCServer {
	private static final String RPC_QUEUE_NAME = "rpc_queue_gateway";
	private static final String MESSAGING_HOST = "localhost";
	private static final String CLOUD_AMQP_URL = "amqp://nsxkqaba:X4f5wyHcYgyDzQ1w6LMdOlv5fFaXsyHU@chicken.rmq.cloudamqp.com/nsxkqaba";
	
	private static final Gson GSON = new Gson();
	
	public static void launch(){
		Connection connection = null;
	    Channel channel = null;
	    try {
	        ConnectionFactory factory = new ConnectionFactory();
//		    factory.setHost(MESSAGING_HOST);
		    factory.setUri(new URI(CLOUD_AMQP_URL));
	        
	        connection = factory.newConnection();
	        channel = connection.createChannel();
	        
	        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
//	        channel.basicQos(1);
	        boolean autoAck = false;
	        QueueingConsumer consumer = new QueueingConsumer(channel);
	        channel.basicConsume(RPC_QUEUE_NAME, autoAck, consumer);
	    
	        System.out.println(" [TokenizerServer] Awaiting RPC requests");
	    
	        while (true) {
	          String response = null;
	          
	          QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	          
	          BasicProperties props = delivery.getProperties();
	          BasicProperties replyProps = new BasicProperties
	                                           .Builder()
	                                           .correlationId(props.getCorrelationId())
	                                           .build();          
	          try {
	            String bookId = new String(delivery.getBody(),"UTF-8");
	            System.out.println("GatewayServer processing request...");
	            PieceOfBook bookPiece = callGateway(bookId);
	            response = GSON.toJson(bookPiece);
	          }
	          catch (Exception e){
	            System.out.println(" [.] " + e.toString());
	            response = "";
	          }
	          finally {  
	            channel.basicPublish("", props.getReplyTo(), replyProps, response.getBytes("UTF-8"));
	            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	          }
	        }
	      }
	      catch  (Exception e) {
	        e.printStackTrace();
	      }
	      finally {
	        if (connection != null) {
	          try {
	            connection.close();
	          }
	          catch (Exception ignore) {}
	        }
	      }      		      
	    }
	
	private static synchronized PieceOfBook callGateway(String bookId) throws IOException{
		//I don't like this synchronized solution... 
		// But I will mantain it for now because calling this method in a static way could cause concurrency problems
		return SerarchService.getBook(bookId, 1).get(0);
	}
}
