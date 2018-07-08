package pl.sheryane.restexample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.sheryane.restexample.PostRepository;
import pl.sheryane.restexample.model.Post;

import java.util.List;

//This class will act as a Controller, so we could use HTTP methods.
@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post addNewPost(@RequestBody Post postToAdd) {
        return postRepository.save(postToAdd);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> findAll() {
        return postRepository.findAll();
    }
}
