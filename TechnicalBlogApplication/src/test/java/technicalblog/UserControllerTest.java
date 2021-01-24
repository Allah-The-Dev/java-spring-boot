package technicalblog;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private static String LOGIN_MESSAGE = "<h2>Please Login:</h2>";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void userLoginReponseBodyShouldContainLOGIN_MESSAGE() throws Exception {
        this.mockMvc.perform(get("/users/login"))
            .andDo(print())
            .andExpect(status().isOk())
			.andExpect(content().string(containsString(LOGIN_MESSAGE)));
	}
}
