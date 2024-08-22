package com.moyanshushe.service;

import com.moyanshushe.model.dto.member.*;
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

    boolean updatePassword(MemberForUpdatePassword memberForUpdatePassword);

    boolean bind(MemberForBinding memberForBinding);
}
