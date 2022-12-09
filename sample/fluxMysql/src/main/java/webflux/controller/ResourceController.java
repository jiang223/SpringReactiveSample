package webflux.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.common.NotFoundException;
import webflux.model.Resource;
import webflux.model.vo.ResourceQuery;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

/**
 * @author lzj
 * @date
 */
@RestController
@RequestMapping("/resource")
@Slf4j
public class ResourceController {
    
    @Autowired
    R2dbcEntityTemplate template;

    @GetMapping
    public Flux<Resource> getPage(@RequestBody ResourceQuery queryResource) {
        Query query= Query.empty();
        if(!StringUtils.isEmpty(queryResource.getUsername()))
            query=query(where("Resourcename").like(queryResource.getUsername()));
        query=query.with(PageRequest.of(queryResource.getPage(),queryResource.getSize()));
        return template.select(Resource.class).from("resource").matching(query).all();

    }

    @GetMapping("/{id}")
    public Mono<Resource> get(@PathVariable("id") Long id) {
        return template.selectOne(Query.query(where("id").is(id)),Resource.class)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))));
    }

    @PutMapping("/{id}")
    public Mono<Resource> update(@PathVariable("id") Long id, @RequestBody Resource Resource) {
        return template.selectOne(Query.query(where("id").is(id)),Resource.class)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                             .map(u -> {
                                 u=Resource;
                                 return u;
                             })
                             .flatMap(u -> template.update(Resource));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Resource> save(@RequestBody Resource Resource) {
        return template.insert(Resource)
                .doOnError(throwable -> Mono.error(new NotFoundException(String.valueOf(Resource))))
                ;
    }

    @DeleteMapping("/{id}")
    public Mono<Resource> delete(@PathVariable("id") Long id) {
        Resource resource=new Resource();
        resource.setId(String.valueOf(id));
        return
                template.selectOne(query(where("id").is(id)),Resource.class)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                .then(template.delete(resource));
    }
}