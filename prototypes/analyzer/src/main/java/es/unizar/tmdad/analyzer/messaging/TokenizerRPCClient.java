package es.unizar.tmdad.analyzer.messaging;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.Connection;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.swing.event.ListSelectionEvent;

import com.rabbitmq.client.AMQP.BasicProperties;

import es.unizar.tmdad.analyzer.interfacing.BookRaw;
import es.unizar.tmdad.analyzer.interfacing.BookTokenized;
import es.unizar.tmdad.analyzer.service.BookRawAndTerms;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

public class TokenizerRPCClient {
	
	private Connection connection;
	private Channel channel;
	private static final String RPC_QUEUE_NAME = "rpc_queue";
	private static final String MESSAGING_HOST = "localhost";
	private String replyQueueName;
	private QueueingConsumer consumer;
	
	private static final Gson GSON = new Gson();
	
	public TokenizerRPCClient() throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(MESSAGING_HOST);
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    
	    replyQueueName = channel.queueDeclare().getQueue();
	    consumer = new QueueingConsumer(channel);
	    channel.basicConsume(replyQueueName, true, consumer);
	}
	
	public BookTokenized call(BookRaw bookRaw, List<String> tokens) throws Exception {
	    String response = null;
	    String corrId = UUID.randomUUID().toString();

	    BasicProperties props = new BasicProperties
	                                .Builder()
	                                .correlationId(corrId)
	                                .replyTo(replyQueueName)
	                                .build();
	    
	    //Create message JSON -> Is this any kind of EIP??
	    BookRawAndTerms bookRawAndTerms = new BookRawAndTerms(bookRaw.getId(), bookRaw.getContent(), tokens);
	    String message = GSON.toJson(bookRawAndTerms);
	    //CHECK
	    BookRawAndTerms bookRawAndTerms2 = GSON.fromJson(message, BookRawAndTerms.class);
	    if(bookRawAndTerms2.equals(bookRawAndTerms)){
	    	System.out.println("*** BookRawAndTerms aparently is Jsoning OK***");
	    	System.out.println("TERMS: " +bookRawAndTerms2.getTerms().toArray().toString());
	    }
	    //Publish message on default exchange, to the RPC queue
	    channel.basicPublish("", RPC_QUEUE_NAME, props, message.getBytes("UTF-8"));

	    //Wait for reply
	    while (true) {
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	      if (delivery.getProperties().getCorrelationId().equals(corrId)) {
	        response = new String(delivery.getBody(),"UTF-8");
	        break;
	      }
	    }
	    return GSON.fromJson(response, BookTokenized.class);
	  }

	  public void close() throws Exception {
	    connection.close();
	  }
	
}
