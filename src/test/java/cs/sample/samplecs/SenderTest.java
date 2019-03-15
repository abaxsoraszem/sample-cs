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

	int msgcount = 100;

	@Test
	public void testSendMessage() {

		for (int i = 0; i < msgcount; i++) {

			String key = "Msg" + i;

			receiver.expect(key);

			sender.sendMessage(key, "a");
		}

		Assert.assertTrue("Not Received on time", receiver.allRevceived(50000));

		receiver.printStat();
	}

}
