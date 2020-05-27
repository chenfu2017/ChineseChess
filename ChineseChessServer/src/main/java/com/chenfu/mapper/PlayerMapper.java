package com.chenfu.mapper;

import com.chenfu.pojo.Player;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface PlayerMapper extends Mapper<Player> {

    @Select("select password from player where username =#{username}")
    String getPasswordByUsername(@Param("username") String username);
}
