package webflux.service;

import reactor.core.publisher.Mono;
import webflux.model.User;

public interface IUserService {
    Mono<User> findUserBySome(String username);
}
