package technicalblog;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import technicalblog.controller.UserController;
import technicalblog.model.User;
import technicalblog.service.PostService;
import technicalblog.service.UserService;

@WebMvcTest(UserController.class)
public class UserConTest {

    @Autowired
	private MockMvc mockMvc;

    @MockBean
    private PostService postService;

	@MockBean
	private UserService userService;

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
        fakeUser.setId(1);
        fakeUser.setUsername("foo");
        fakeUser.setPassword("bar");

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
