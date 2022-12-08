package webflux.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.common.StaticConstant;
import webflux.model.Resource;
import webflux.model.User;
import webflux.service.IUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private webflux.repository.UserRepository UserRepository;
    @Autowired
    private webflux.repository.ResourceRepository repository;
    @Autowired
    @Qualifier("reactiveRedisTemplate")
    private ReactiveRedisTemplate redisTemplate;

    @Override
    public Mono<User> findUserBySome(String username) {
        return UserRepository.findByUsername(username);
    }

    @Override
    public Mono<List<PathPatternParserServerWebExchangeMatcher>> findPatternMatcherByRole(String role) {//Todo redis cache delete and rabbitMQ notice?
        ReactiveHashOperations<String, String, List<PathPatternParserServerWebExchangeMatcher>> opsForHash = redisTemplate.opsForHash();
        return  opsForHash.get(StaticConstant.REDIS_ROLE,role)
                .switchIfEmpty(findResourceByRole(role).collectList().map(resourceList->{
                    List<PathPatternParserServerWebExchangeMatcher> pathPatternParserServerWebExchangeMatcherList=resourceList.stream().map(resource->
                    {
                        PathPatternParserServerWebExchangeMatcher pathPatternParserServerWebExchangeMatcher = new PathPatternParserServerWebExchangeMatcher(resource.getUrl(), HttpMethod.resolve(resource.getMethod()));
                        return pathPatternParserServerWebExchangeMatcher;
                    }
                    ).collect(Collectors.toList());
                    //resource need  to add assert null?
                    opsForHash.put(StaticConstant.REDIS_ROLE,role,pathPatternParserServerWebExchangeMatcherList);
                    return pathPatternParserServerWebExchangeMatcherList;
                }));

    }

    @Override
    public Flux<Resource> findResourceByRole(String rolename) {
        return repository.findResourceByRoleName(rolename);
    }

}
