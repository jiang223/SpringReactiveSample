package webflux.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
@Table("roles")

public class Roles {
    @Id
    private Long id;
    private String code;
    private String name;

    private String description;
    @CreatedDate
    private Timestamp createdTime;

    private Timestamp updatedTime;

    private String createdBy;
    private String updatedBy;


}
