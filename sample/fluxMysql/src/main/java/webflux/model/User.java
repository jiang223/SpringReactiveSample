package webflux.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
public class User {
    @Id
    private String id      ;
    private String username   ;
    private  String password    ;
    private  String name          ;
    private  String mobile        ;
    private String description   ;
    private String deleted       ;
    private Integer enabled       ;
    private  Integer account_non_expired   ;
    private   Integer credentials_non_expired  ;
    private  Integer account_non_locked     ;
    @CreatedDate
    private   Date created_time          ;
    private   Date updated_time        ;
    private String created_by        ;
    private   String updated_by        ;

}
