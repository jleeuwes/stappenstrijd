package eu.lwstn.stappenstrijd.scoreservice;

import eu.lwstn.stappenstrijd.domain.core.BunchOfSteps;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Based on <a href="https://www.baeldung.com/spring-kafka">this tutorial</a>.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {
	// Guessing here; bootstrapAddress was not defined in the tutorial
	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Bean
	public ConsumerFactory<String, BunchOfSteps> consumerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "stappen-score");
		// configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		// configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		JsonDeserializer<BunchOfSteps> jsonDeserializer = new JsonDeserializer<>();
		jsonDeserializer.addTrustedPackages(BunchOfSteps.class.getPackageName());
		return new DefaultKafkaConsumerFactory<>(configProps, new StringDeserializer(), jsonDeserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BunchOfSteps> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, BunchOfSteps> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}