package technicalblog;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import technicalblog.controller.PostController;
import technicalblog.service.PostService;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    private static String CREATE_NEW_POST = "<h1>Create New Post</h1>";

	@Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PostService postService;

	@Test
	public void createNewPostControllerResponseBodyShouldContainCREATE_NEW_POST() throws Exception {
        this.mockMvc
            .perform(get("/posts/newpost"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(CREATE_NEW_POST)));
	}
}