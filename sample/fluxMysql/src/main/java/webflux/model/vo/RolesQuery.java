package webflux.model.vo;

import lombok.Data;

@Data
public class RolesQuery {
        private String name;
        private int page;
        private int size;
}
