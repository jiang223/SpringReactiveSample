package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.entity.po.User;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}