package eu.lwstn.stappenstrijd.stappenservice;

import eu.lwstn.stappenstrijd.domain.core.BunchOfSteps;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * Based on <a href="https://www.baeldung.com/spring-kafka">this tutorial</a>.
 */
@Configuration
public class KafkaTopicConfig {
	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topicSteps() {
		return new NewTopic(BunchOfSteps.KAFKA_TOPIC, 1, (short) 1);
	}
}
