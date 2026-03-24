package com.lcu.mapper;

import com.lcu.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserInfo(String email);

    User passwordLogin(@Param("username") String username,@Param("password") String password);
}
