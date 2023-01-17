package webflux.model.vo;

import lombok.Data;
import java.util.List;
@Data
public class PageRes {
    private int pageNo;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List data;
}
