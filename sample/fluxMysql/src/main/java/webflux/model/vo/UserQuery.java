package webflux.model.vo;

import lombok.Data;
@Data
public class UserQuery {
        private String username;
        private int page;
        private int size;


}
