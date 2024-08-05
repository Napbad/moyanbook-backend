package com.moyanshushe.service;

import com.moyanshushe.model.dto.member.MemberForLogin;
import com.moyanshushe.model.dto.member.MemberForRegister;
import com.moyanshushe.model.dto.member.MemberForUpdate;
import com.moyanshushe.model.dto.member.MemberForVerify;
import com.moyanshushe.model.entity.Member;
import org.jetbrains.annotations.NotNull;


/*
 * Author: Napbad
 * Version: 1.0
 */
public interface MemberAccountService {

    @NotNull
    Boolean memberRegister(MemberForRegister memberForRegister);

    Member memberLogin(MemberForLogin memberForLogin);

    @NotNull
    Boolean memberUpdate(MemberForUpdate memberForUpdate);

    void memberVerify(MemberForVerify memberForVerify);
}
