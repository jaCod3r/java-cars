package app.ajuber.cars;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CarsApplication {

  public static final String EXCHANGE_NAME = "Car";
  public static final String QUEUE_NAME = "Log";

  public static void main(String[] args) {
    SpringApplication.run(CarsApplication.class, args);
  }

  @Bean // exchange we are going to use
  public TopicExchange appExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean // queue we are going to us
  public Queue appQueue() {
    return new Queue(QUEUE_NAME);
  }

  @Bean // bind these together
  public Binding declareBinding() {
    return BindingBuilder.bind(appQueue()).to(appExchange()).with(QUEUE_NAME);
  }

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }


}

