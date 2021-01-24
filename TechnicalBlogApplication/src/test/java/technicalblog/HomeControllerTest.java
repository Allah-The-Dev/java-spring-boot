package technicalblog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import technicalblog.controller.HomeController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HomeControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private HomeController controller;

	@Test
	public void contextLoads() {
	}

	@Test
	public void controllerIsNotNull() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void homeContrllerReponseShouldConntainExpected() throws Exception {
		String url = "http://localhost:" + port + "/";
		assertThat(this.restTemplate.getForObject(url, String.class)).contains("<h1>All Technical Posts</h1>");
	}

}