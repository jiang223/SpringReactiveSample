package com.entity.vo;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import com.entity.po.User;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserVo  {

    public UserVo(User user) {
        BeanUtils.copyProperties(user, this);
    }

    private String name;
    private String mobile;
    private String username;
    private String description;
    private String deleted;
    private Set<String> roleIds;
    private String createdBy;
    private String updatedBy;
    private Date createdTime;
    private Date updatedTime;
}
