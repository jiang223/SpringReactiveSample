package webflux.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import webflux.model.User;
import webflux.service.IUserService;

@Service
public class UserService implements IUserService {
    @Autowired
    private webflux.repository.UserRepository UserRepository;

    @Override
    public Mono<User> findUserBySome(String username) {
        return UserRepository.findByUsername(username);
    }
}
