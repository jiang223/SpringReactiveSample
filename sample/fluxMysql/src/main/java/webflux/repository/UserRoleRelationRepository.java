package webflux.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import webflux.model.UserRoleRelation;

/**
 * @author lzj
 * @date 2019-01-08 14:22
 */
public interface UserRoleRelationRepository extends ReactiveCrudRepository<UserRoleRelation, Long> {

}
