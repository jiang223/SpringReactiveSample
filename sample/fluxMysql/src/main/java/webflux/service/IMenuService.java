package webflux.service;

import reactor.core.publisher.Mono;
import webflux.model.vo.MenuPageRes;

public interface IMenuService {
    Mono<MenuPageRes> reInMenu(MenuPageRes menuPageRes);
}
