package webflux.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import webflux.model.User;

/**
 * @author HelloWood
 * @date 2019-01-08 14:22
 */
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
//    @Query("select id, firstname, lastname from user c where u.username = :username")
//    Mono<User> findByUsername(String username);
    Mono<Page<User>> queryFirst10ByUsername(String username, Pageable pageable);
    Mono<User> findByUsername( String username);


}
