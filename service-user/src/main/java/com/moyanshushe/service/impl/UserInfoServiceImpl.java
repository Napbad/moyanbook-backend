package com.moyanshushe.service.impl;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/7 上午23:42
    @Description: 
    - A file
*/

import com.moyanshushe.model.dto.user.PublicUserSpecification;
import com.moyanshushe.model.dto.user.PublicUserView;
import com.moyanshushe.model.entity.User;
import com.moyanshushe.model.entity.UserTable;
import com.moyanshushe.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final JSqlClient jsqlClient;
    private final UserTable table = new UserTable();

    @Override
    public @NotNull Page<User> queryUser(PublicUserSpecification userSpecification) {
        return jsqlClient.createQuery(table)
                .where(userSpecification)
                .select(
                        table.fetch(
                                PublicUserView.METADATA.getFetcher()
                        )
                ).fetchPage(
                        userSpecification.getPage() != null ? userSpecification.getPage() : 0,
                        userSpecification.getPageSize() != null ? userSpecification.getPageSize() : 10
                );
    }
}
