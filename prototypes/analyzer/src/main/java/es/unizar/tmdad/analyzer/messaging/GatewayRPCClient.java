package es.unizar.tmdad.analyzer.messaging;

import java.net.URI;
import java.util.UUID;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;

import es.unizar.tmdad.analyzer.interfacing.BookRaw;

public class GatewayRPCClient {
	private Connection connection;
	private Channel channel;
	private static final String RPC_QUEUE_NAME = "rpc_queue_gateway";
	private static final String MESSAGING_HOST = "localhost";
	private static final String CLOUD_AMQP_URL = "amqp://nsxkqaba:X4f5wyHcYgyDzQ1w6LMdOlv5fFaXsyHU@chicken.rmq.cloudamqp.com/nsxkqaba";
	private String replyQueueName;
	private QueueingConsumer consumer;
	
	private static final Gson GSON = new Gson();
	
	public GatewayRPCClient()throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
//	    factory.setHost(MESSAGING_HOST);
	    factory.setUri(new URI(CLOUD_AMQP_URL));
	    connection = factory.newConnection();
	    channel = connection.createChannel();
	    
	    replyQueueName = channel.queueDeclare().getQueue();
	    consumer = new QueueingConsumer(channel);
	    channel.basicConsume(replyQueueName, true, consumer);
	}
	
	public BookRaw call(String bookId) throws Exception {
	    String response = null;
	    String corrId = UUID.randomUUID().toString();

	    BasicProperties props = new BasicProperties
	                                .Builder()
	                                .correlationId(corrId)
	                                .replyTo(replyQueueName)
	                                .build();
	    String message = bookId;
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
	    return GSON.fromJson(response, BookRaw.class);
	  }

	  public void close() throws Exception {
	    connection.close();
	  }
}
