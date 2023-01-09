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
import webflux.model.Roles;
import webflux.model.UserRoleRelation;
import webflux.model.vo.RolesQuery;
import webflux.repository.UserRoleRelationRepository;

import java.util.List;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

/**
 * @author lzj
 * @date
 */
@RestController
@RequestMapping("/Roles")
@Slf4j
public class RolesController {
    @Autowired
    private UserRoleRelationRepository userRoleRelationRepository;
    @Autowired
    R2dbcEntityTemplate template;

    @GetMapping
    public Flux<Roles> getPage(@RequestBody RolesQuery queryRoles) {
        Query query= Query.empty();
        if(!StringUtils.isEmpty(queryRoles.getName()))
            query=query(where("Rolesname").like(queryRoles.getName()));
        query=query.with(PageRequest.of(queryRoles.getPage(),queryRoles.getSize()));
        return template.select(Roles.class).from("Roles").matching(query).all();

    }

    @GetMapping("/{id}")
    public Mono<Roles> get(@PathVariable("id") Long id) {
        return template.selectOne(Query.query(where("id").is(id)),Roles.class)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))));
    }

    @PutMapping("/{id}")
    public Mono<Roles> update(@PathVariable("id") Long id, @RequestBody Roles Roles) {
        return template.selectOne(Query.query(where("id").is(id)),Roles.class)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                             .map(u -> {
                                 Roles.setId(u.getId());
                                 return Roles;
                             })
                             .flatMap(u -> template.update(u));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Roles> save(@RequestBody Roles Roles) {
        return template.insert(Roles)
                .doOnError(throwable -> Mono.error(new NotFoundException(String.valueOf(Roles))))
                ;
    }

    @DeleteMapping("/{id}")
    public Mono<Roles> delete(@PathVariable("id") Long id) {
        Roles Roles=new Roles();
        Roles.setId(id);
        return
                template.selectOne(query(where("id").is(id)),Roles.class)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                .then(template.delete(Roles));
    }

    @PostMapping("srrr")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> srrr(@RequestBody List<UserRoleRelation> userRoleRelations) {
           return   userRoleRelationRepository.deleteAll()
                   .then(userRoleRelationRepository.saveAll(userRoleRelations).then())
                    .doOnError(throwable -> Mono.error(new NotFoundException("")));

    }
}