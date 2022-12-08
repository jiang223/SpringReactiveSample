package webflux.model;

import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
@Table("role_resource_relation")
public class RoleResourceRelation {
    @Basic
    @Column(name = "id")
    private String id;
    @Basic
    @Column(name = "resource_id")
    private String resourceId;
    @Basic
    @Column(name = "role_id")
    private String roleId;
    @Basic
    @Column(name = "created_time")
    private Timestamp createdTime;
    @Basic
    @Column(name = "updated_time")
    private Timestamp updatedTime;
    @Basic
    @Column(name = "created_by")
    private String createdBy;
    @Basic
    @Column(name = "updated_by")
    private String updatedBy;


}
