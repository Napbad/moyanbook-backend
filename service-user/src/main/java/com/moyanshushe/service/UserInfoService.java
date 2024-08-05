package com.moyanshushe.service;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/7 上午23:40
    @Description: 
    - A file
*/

import com.moyanshushe.model.dto.user.PublicUserSpecification;
import com.moyanshushe.model.entity.User;
import org.babyfish.jimmer.Page;
import org.jetbrains.annotations.NotNull;

public interface UserInfoService {
    @NotNull Page<User> queryUser(PublicUserSpecification userSpecification);
}
