package webflux.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;


@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("menu")
public class Menu {
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


}
