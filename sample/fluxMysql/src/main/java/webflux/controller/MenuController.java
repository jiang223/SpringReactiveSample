package webflux.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import webflux.common.NotFoundException;
import webflux.model.Menu;
import webflux.model.vo.MenuPageRes;
import webflux.model.vo.MenuQuery;
import webflux.service.IMenuService;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

/**
 * @author lzj
 * @date
 */
@RestController
@RequestMapping("/Menu")
@Slf4j
public class MenuController {
    
    @Autowired
    R2dbcEntityTemplate template;
    @Autowired
    IMenuService menuService;

    @GetMapping
    public Flux<MenuPageRes> getPage(@RequestBody MenuQuery queryMenu) {
        Query query= Query.empty();
        if(!StringUtils.isEmpty(queryMenu.getUsername()))
            query=query(where("Menuname").like(queryMenu.getUsername()));
        query=query.query(where("parent_id").is("-1"));
        query=query.with(PageRequest.of(queryMenu.getPage(),queryMenu.getSize()));
        query=query.sort(Sort.by("order_num"));
        return template.select(Menu.class).from("menu").matching(query).all()
                .flatMap(menu->{
                    MenuPageRes menuPageRes=new MenuPageRes();
                    BeanUtils.copyProperties(menu,menuPageRes);
                    return menuService.reInMenu(menuPageRes);
                    //return menuPageRes;
                });


    }

    @GetMapping("/{id}")
    public Mono<Menu> get(@PathVariable("id") Long id) {
        return template.selectOne(Query.query(where("id").is(id)),Menu.class)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))));
    }

    @PutMapping("/{id}")
    public Mono<Menu> update(@PathVariable("id") Long id, @RequestBody Menu Menu) {
        return template.selectOne(Query.query(where("id").is(id)),Menu.class)
                             .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                             .map(u -> {
                                 u=Menu;
                                 return u;
                             })
                             .flatMap(u -> template.update(Menu));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Menu> save(@RequestBody Menu Menu) {
        return template.insert(Menu)
                .doOnError(throwable -> Mono.error(new NotFoundException(String.valueOf(Menu))))
                ;
    }

    @DeleteMapping("/{id}")
    public Mono<Menu> delete(@PathVariable("id") Long id) {
        Menu Menu=new Menu();
        Menu.setId(String.valueOf(id));
        return
                template.selectOne(query(where("id").is(id)),Menu.class)
                .switchIfEmpty(Mono.error(new NotFoundException(String.valueOf(id))))
                .then(template.delete(Menu));
    }
}