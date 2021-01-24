package technicalblog;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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

import technicalblog.controller.PostController;
import technicalblog.model.Post;
import technicalblog.service.PostService;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    private static String CREATE_NEW_POST = "<h1>Create New Post</h1>";

	@Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private PostService postService;

    String examplePostJSON = "{}";

	@Test
	public void newPostControllerResponseBodyShouldContainCREATE_NEW_POST() throws Exception {
        this.mockMvc
            .perform(get("/posts/newpost"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(CREATE_NEW_POST)));
    }
    
    @Test
	public void createNewPostControllerForCorrectRequestBodyShouldReturnStatusRedirected() throws Exception {
        Post post1 = new Post();
        post1.setTitle("This is your Post");
        post1.setBody("This is your Post. It has some valid content");
        post1.setDate(new Date());

        ObjectMapper mapper = new ObjectMapper();

        //mock postService createPost behaviour
        Mockito.when(postService.createPost(Mockito.any(Post.class))) 
            .thenReturn(true);

        //Send Post1 as body to /post/create 
        RequestBuilder requestBuilder = MockMvcRequestBuilders
            .post("/posts/create")
            .accept(MediaType.APPLICATION_XHTML_XML)
            .content(mapper.writeValueAsString(post1))
            .contentType(MediaType.APPLICATION_JSON);

        //execute the request
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        //match status code redirect 302
        assertThat(response.getStatus()).isEqualTo(302);

        //match if redirect URI is correct
        assertThat(response.getRedirectedUrl()).isEqualTo("/posts");

	}
}