//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.moyanshushe.service;

import com.moyanshushe.model.dto.admin.*;
import com.moyanshushe.model.entity.Admin;
import org.springframework.transaction.annotation.Transactional;

public interface AdminService {
    @Transactional(
            rollbackFor = {Exception.class}
    )
    Boolean adminRegister(AdminForRegister adminForRegister);

    @Transactional(
            rollbackFor = {Exception.class}
    )
    Admin adminLogin(AdminForLogin adminForLogin);

    @Transactional(
            rollbackFor = {Exception.class}
    )
    boolean adminUpdate(AdminForUpdate adminForUpdate);

    void adminVerify(AdminForVerify adminForVerify);

    boolean bind(AdminForBinding adminForBinding);

    boolean updatePassword(AdminForUpdatePassword adminForUpdatePassword);
}
