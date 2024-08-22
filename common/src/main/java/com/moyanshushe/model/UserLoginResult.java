package com.moyanshushe.model;


/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 8/22/24
    @Description: 

*/

import com.moyanshushe.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResult {
    private User user;
    private String token;
}
