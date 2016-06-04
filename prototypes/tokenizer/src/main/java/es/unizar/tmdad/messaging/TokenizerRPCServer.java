package es.unizar.tmdad.messaging;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

import es.unizar.tmdad.service.BookRawAndTerms;
import es.unizar.tmdad.service.BookTokenized;
import es.unizar.tmdad.tokenizer.Counter;
import es.unizar.tmdad.tokenizer.Tokenizer;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import com.google.gson.Gson;
import com.rabbitmq.client.AMQP.BasicProperties;

public class TokenizerRPCServer {
	
	private static final String RPC_QUEUE_NAME = "rpc_queue_tokenizer";
	private static final String MESSAGING_HOST = "localhost";
	private static final String CLOUD_AMQP_URL = "amqp://nsxkqaba:X4f5wyHcYgyDzQ1w6LMdOlv5fFaXsyHU@chicken.rmq.cloudamqp.com/nsxkqaba"; //amqp://user:pass@host:10000/vhost

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
	            String message = new String(delivery.getBody(),"UTF-8");
	            //TODO Check if Message -> BookRaw + TermsList is OK
	            BookRawAndTerms bookRawAndTerms = GSON.fromJson(message, BookRawAndTerms.class);
	            System.out.println("TokenizerServer processing request...");
	            BookTokenized bookTok = callTokenizer(bookRawAndTerms);
	            response = GSON.toJson(bookTok);
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
	
	private static synchronized BookTokenized callTokenizer(BookRawAndTerms bookRawAndTerms) throws IOException{
		//I don't like this synchronized solution... 
		// But I will mantain it for now because calling this method in a static way could cause concurrency problems
		BookTokenized bookTok = new BookTokenized(bookRawAndTerms.getId());
		try{
			Tokenizer tokenizer = new Tokenizer(bookRawAndTerms.getContent());
			Map<String, String> chapters = tokenizer.tokenize();
			Counter counter = new Counter(bookTok, chapters, bookRawAndTerms.getTerms());
			return counter.countTerms();
		}catch(IOException ioex){
			System.out.println(ioex);
			throw ioex;
		}
	}
}
