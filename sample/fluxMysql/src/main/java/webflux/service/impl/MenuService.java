package webflux.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import webflux.model.Menu;
import webflux.model.vo.MenuPageRes;
import webflux.service.IMenuService;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@Service
public class MenuService implements IMenuService{
    @Autowired
    R2dbcEntityTemplate template;


    @Override
    public Mono<MenuPageRes> reInMenu(MenuPageRes menuPageRes) {
        Query query= Query.empty();
        query=query(where("parent_id").is(menuPageRes.getId()));
        query=query.sort(Sort.by("order_num"));
        return template.select(Menu.class).from("menu").matching(query).all()
                .flatMap(menu -> {
                    MenuPageRes menuPageRes2 = new MenuPageRes();
                    BeanUtils.copyProperties(menu, menuPageRes2);
                    return reInMenu(menuPageRes2);
                })
                .collectList()
                .map(list->{
                    menuPageRes.setChildren(list);
                    return menuPageRes;
                });
         //return  menuPageRes;
    }
}
