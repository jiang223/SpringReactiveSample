package webflux.model.vo;

import lombok.Data;

@Data
public class MenuQuery {
        private String username;
        private int pageNo;
        private int pageSize;


}
