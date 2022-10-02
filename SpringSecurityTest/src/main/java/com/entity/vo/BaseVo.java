package com.entity.vo;


import lombok.Data;
import lombok.NoArgsConstructor;
import com.entity.po.BasePo;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseVo<T extends BasePo> implements Serializable {
    private String id;
}
