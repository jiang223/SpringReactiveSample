package webflux.controller;


import webflux.common.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import webflux.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.repository.PostRepository;

/**
 * @author HelloWood
 * @date 2019-01-08 15:22
 */
@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public Flux<Post> list() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable("id") Long id) {
        return postRepository.findById(id)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))));
    }

    @PutMapping("/{id}")
    public Mono<Post> update(@PathVariable("id") Long id, @RequestBody Post post) {
        return postRepository.findById(id)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                             .map(p -> {
                                 p.setTitle(post.getTitle());
                                 p.setContent(post.getContent());
                                 return p;
                             })
                             .flatMap(p -> postRepository.save(p));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Post> save(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return postRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                .then(postRepository.deleteById(id));
    }
}