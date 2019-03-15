package cs.sample.samplecs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Value(value = "${kafka.topic}")
	private String topic;

	public void sendMessage(String key, String message) {
		logger.info("Sending");
		kafkaTemplate.send(topic, key, message);
		logger.info("sent");
	}
}
