package webflux.model.vo;

import lombok.Data;
import webflux.model.Menu;

import java.util.List;


@Data
public class MenuPageRes extends Menu {
    private List children;
}
