package webflux.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.common.NotFoundException;
import webflux.model.User;
import webflux.repository.UserRepository;

/**
 * @author lzj
 * @date
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository UserRepository;

    @GetMapping
    public Flux<User> list() {
        return UserRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> get(@PathVariable("id") Long id) {
        return UserRepository.findById(id)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))));
    }

    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable("id") Long id, @RequestBody User user) {
        return UserRepository.findById(id)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                             .map(u -> {
                                 u=user;
                                 return u;
                             })
                             .flatMap(u -> UserRepository.save(u));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> save(@RequestBody User User) {
        return UserRepository.save(User);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") Long id) {
        return UserRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                .then(UserRepository.deleteById(id));
    }
}