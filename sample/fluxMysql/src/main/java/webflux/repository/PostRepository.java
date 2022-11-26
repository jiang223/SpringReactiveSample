package webflux.repository;

import webflux.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

/**
 * @author HelloWood
 * @date 2019-01-08 14:22
 */
public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

}
