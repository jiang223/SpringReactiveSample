package webflux.model.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;


@Data
public class MenuPageRes {
    @Id
    private String id;

    private String parentId;

    private String type;

    private String href;

    private String icon;

    private String name;

    private String description;

    private Integer orderNum;

    private LocalDate createdTime;

    private LocalDate updatedTime;

    private String createdBy;

    private String updatedBy;

    private List MenuPageRes;
}
