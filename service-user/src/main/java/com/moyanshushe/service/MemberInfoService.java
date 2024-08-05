package com.moyanshushe.service;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/8 上午0:08
    @Description: 

*/

import com.moyanshushe.model.dto.member.PublicMemberSpecification;
import com.moyanshushe.model.entity.Member;
import org.babyfish.jimmer.Page;
import org.jetbrains.annotations.NotNull;

public interface MemberInfoService {
    @NotNull
    Page<Member> queryMember(PublicMemberSpecification memberSpecification);
}
