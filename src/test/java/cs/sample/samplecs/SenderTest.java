package cs.sample.samplecs;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SenderTest {

	@Autowired
	Sender sender;
	
	@Autowired
	Receiver receiver;
	
	@Test
	public void testSendMessage() {
		
		receiver.expect("message1");
		receiver.expect("message2");
		
		sender.sendMessage("message1", "a");
		sender.sendMessage("message2", "b");
		
		Assert.assertTrue("Not Received on time", receiver.allRevceived(50000));
	}

}
