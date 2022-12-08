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
//@EntityListeners(AuditingEntityListener.class)
@Table("users")
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
    private LocalDate created_time          ;
    private   LocalDate  updated_time        ;
    private String created_by        ;
    private   String updated_by        ;

}
