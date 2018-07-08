package pl.sheryane.restexample.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.sheryane.restexample.PostRepository;
import pl.sheryane.restexample.model.Post;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)

public class PostControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    PostRepository postRepository;

    //Jackson
    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnCreatedPost() throws Exception {
        //given
        Post postToAdd = new Post("title", "message");
        Post createdPost = new Post(1L, "title", "message");

        when(postRepository.save(postToAdd)).thenReturn(createdPost);
        //when
        ResultActions result = mvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postToAdd))); //tworzy json z obiektu
        //then
        result.andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(createdPost)));
    }

    @Test
    public void shouldReturnAlreadyExistingPosts() throws Exception {
        //given
        List<Post> getPosts = new ArrayList<>();
        Post postToAdd = new Post("title", "message");
        Post postToAdd2 = new Post("title2", "message2");
        getPosts.add(postToAdd);
        getPosts.add(postToAdd2);

        when(postRepository.findAll()).thenReturn(getPosts);
        //when
        ResultActions result = mvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON));
        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(getPosts)));
    }
}
