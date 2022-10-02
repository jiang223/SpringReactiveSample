package com.entity.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel
@Data
public class UserQueryForm  {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    @ApiModelProperty(value = "查询开始时间")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    @ApiModelProperty(value = "查询结束时间")
    private Date createdTimeEnd;
}
