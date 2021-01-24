package technicalblog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import technicalblog.controller.HomeController;

@SpringBootTest()
public class HomeControllerTest {

	@Autowired
	private HomeController controller;

	@Test
	public void contextLoads() {
	}

	@Test
	public void controllerIsNotNull() throws Exception {
		assertThat(controller).isNotNull();
	}

}