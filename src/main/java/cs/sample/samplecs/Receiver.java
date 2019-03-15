package cs.sample.samplecs;

import static org.awaitility.Awaitility.await;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.awaitility.core.ConditionTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Receiver {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	Set<String> expectedMessages = Collections.synchronizedSet(new HashSet<>());
	
	long maxdelay = Long.MIN_VALUE;
	long avgdelay = 0;
	long msgCount = 0;
	
	@KafkaListener(topics = "${kafka.topic}")
	public void listen(
			@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key, 
			@Payload Message message) {
		logger.info("Received Message in group foo: " + key);
		expectedMessages.remove(key);
		
		long delay = System.currentTimeMillis() - message.getTimestamp();
		
		maxdelay = Math.max(maxdelay, delay);
		avgdelay = (avgdelay * msgCount + delay) / (msgCount + 1);
		msgCount++;
		
	}



	public void expect(String key) {
		expectedMessages.add(key);
		
		
	}
	
	public void printStat() {
		System.out.println("Count: " + msgCount);
		System.out.println("Avg: " + avgdelay);
		System.out.println("Max: " + maxdelay);
		
	}



	public boolean allRevceived(int timeoutInMilis) {
		try {
			await().atMost(timeoutInMilis, TimeUnit.MILLISECONDS).until(() -> allReceived());
			return true;
		} catch (ConditionTimeoutException e) {
			return false;
		}
		
		
	}



	private boolean allReceived() {
		return expectedMessages.isEmpty();
	}
}
