package webflux.service;

import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.model.Resource;
import webflux.model.User;

import java.util.List;

public interface IUserService {
    Mono<User> findUserBySome(String username);
    Mono<List<PathPatternParserServerWebExchangeMatcher>> findPatternMatcherByRole(String rolename);

    Flux<Resource> findResourceByRole(String rolename);
}
