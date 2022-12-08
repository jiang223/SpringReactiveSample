package webflux.model.vo;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
@Data
public class UserQuery {
        private String username;
        private PageRequest page;


}
