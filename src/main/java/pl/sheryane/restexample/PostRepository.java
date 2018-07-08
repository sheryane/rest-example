package pl.sheryane.restexample;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sheryane.restexample.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
