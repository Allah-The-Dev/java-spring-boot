package technicalblog;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import technicalblog.model.User;
import technicalblog.service.PostService;
import technicalblog.service.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

	private static String LOGIN_MESSAGE = "<h2>Please Login:</h2>";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@MockBean
	private UserService userService;

	@Test
	public void userLoginReponseBodyShouldContainLOGIN_MESSAGE() throws Exception {
		this.mockMvc.perform(get("/users/login")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString(LOGIN_MESSAGE)));
	}

	@Test
	public void returnLoginPage_OnLoginForNotRegisteredUser() throws Exception {
		User fakeUser = new User();

		Mockito.when(userService.login(fakeUser)).thenReturn(null);

		ObjectMapper mapper = new ObjectMapper();

		// Send Post1 as body to /post/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
				.accept(MediaType.APPLICATION_XHTML_XML).content(mapper.writeValueAsString(fakeUser))
				.contentType(MediaType.APPLICATION_JSON);

		// execute the request
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// match status code redirect 302
		assertThat(response.getStatus()).isEqualTo(200);

		// match if redirect URI is correct
		assertThat(response.getContentAsString()).contains("Please Login:");
	}

	@Test
	public void redirectToPostPage_OnSuccessfulLogin() throws Exception {
		User fakeUser = new User();

		Mockito.when(userService.login(any(User.class))).thenReturn(fakeUser);

		ObjectMapper mapper = new ObjectMapper();

		// Send Post1 as body to /post/create
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
				.accept(MediaType.APPLICATION_XHTML_XML).content(mapper.writeValueAsString(fakeUser))
				.contentType(MediaType.APPLICATION_JSON);

		// execute the request
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		// match status code redirect 302
		assertThat(response.getStatus()).isEqualTo(302);

		// match if redirect URI is correct
		assertThat(response.getRedirectedUrl()).isEqualTo("/posts");
	}
}
