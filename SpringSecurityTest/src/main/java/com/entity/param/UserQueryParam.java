package com.entity.param;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.entity.po.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryParam extends BaseParam<User> {
    private String name;
    private String mobile;
    private String username;
}
