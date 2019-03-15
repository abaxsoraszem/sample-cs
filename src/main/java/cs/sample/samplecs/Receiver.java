package cs.sample.samplecs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@KafkaListener(topics = "${kafka.topic}", groupId = "foo")
	public void listen(String message) {
		logger.info("Received Messasge in group foo: " + message);
	}
}
