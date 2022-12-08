package webflux.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import webflux.model.Resource;

/**
 * @author HelloWood
 * @date 2019-01-08 14:22
 */
public interface ResourceRepository extends ReactiveCrudRepository<Resource, Long> {
    @Query("select url,method from resource  r left join role_resource_relation rrr on r.id = rrr.resource_id\n" +
            "where  rrr.role_code=:rolename")
    Flux<Resource> findResourceByRoleName(String rolename);

}
