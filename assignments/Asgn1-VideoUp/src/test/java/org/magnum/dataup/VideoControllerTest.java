package org.magnum.dataup;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.magnum.dataup.model.Video;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
@WebAppConfiguration
public class VideoControllerTest {
	
	private MockMvc mockMvc;

	@Test
	public void test() {
		Video video = Video.create().withContentType("video/mpeg")
				.withDuration(123).withSubject("Mobile Cloud")
				.withTitle("Programming Cloud Services for ...").build();
		
		fail("Not yet implemented");
	}

}
