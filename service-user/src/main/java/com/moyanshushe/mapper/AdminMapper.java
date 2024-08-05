package com.moyanshushe.mapper;

import com.moyanshushe.model.dto.admin.AdminForRegister;
import com.moyanshushe.model.dto.admin.AdminForUpdate;
import com.moyanshushe.model.entity.Admin;
import com.moyanshushe.model.entity.AdminFetcher;
import com.moyanshushe.model.entity.AdminTable;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 针对表【admin】的数据库操作Mapper
 */
@Component
public class AdminMapper {
    private final JSqlClient jSqlClient;

    static final AdminTable table = AdminTable.$;

    @Autowired
    public AdminMapper(JSqlClient jSqlClient) {
        this.jSqlClient = jSqlClient;
    }

    /**
     * 根据用户名和获取器查询用户列表
     *
     * @param name 用户名
     * @param fetcher 数据获取器，用于指定需要获取的关联数据
     * @return 用户列表，如果用户名为空或查询无结果则返回新创建的空列表
     */
    public List<Admin> findByName(String name, AdminFetcher fetcher) {
        // 如果用户名为空，返回新创建的空列表
        if (name == null) {
            return new ArrayList<>();
        }

        // 执行带关联数据查询的操作
        return jSqlClient.createQuery(
                table
        ).where(
                table.name().eq(name)
        ).select(
                table.fetch(
                        fetcher
                )
        ).execute();
    }

    /**
     * 更新用户信息。
     *
     * @param adminForUpdate 包含需要更新的用户信息的对象。此对象应能转换为对应的数据库实体。
     *                      通过该对象，可以更新用户的各种信息，如姓名、电子邮件等。
     * @return 返回一个SimpleSaveResult<Admin>对象，包含了更新操作的结果，例如是否成功更新、影响的行数等。
     */
    public SimpleSaveResult<Admin> update(AdminForUpdate adminForUpdate) {
        // 将用户更新信息转换为数据库实体并保存，实现用户信息的更新
        return jSqlClient.save(adminForUpdate.toEntity());
    }



    /**
     * 更新用户信息。
     *
     * @param admin 包含更新后用户信息的对象。
     * 该方法会将传入的用户对象信息更新到数据库中。
     * 对象中的每个属性都会被检查并更新，如果该属性已经存在。
     */
    public void update(Admin admin) {
        jSqlClient.update(admin); // 使用jSqlClient的update方法更新用户信息到数据库
    }


    /**
     * 添加新用户
     *
     * @param admin 用户注册信息，用于创建新用户
     * @return 用户保存结果
     */
    public SimpleSaveResult<Admin> addAdmin(AdminForRegister admin) {
        return jSqlClient.save(admin.toEntity());
    }

    /**
     * 更新用户登录时间
     *
     * @param id 用户ID
     * @param loginTime 登录时间
     */
    public void updateLoginTime(long id, LocalDate loginTime) {
        // 更新指定用户ID的登录时间
        jSqlClient.createUpdate(table).where(
                table.id().eq((int) id)
        ).set(
                table.lastLoginTime(),
               loginTime
        ).execute();
    }

    /**
     * 根据邮箱查询用户列表
     *
     * @param email 邮箱地址
     * @param fetcher 数据获取器，用于指定需要获取的关联数据
     * @return 用户列表
     */
    public List<Admin> findByEmail(String email, AdminFetcher fetcher) {
        return jSqlClient.createQuery(table)
                .where(table.email().eq(email))
                .select(table.fetch(fetcher))
                .execute();
    }

    /**
     * 根据手机号查询用户列表
     *
     * @param phone 手机号码
     * @param fetcher 数据获取器，用于指定需要获取的关联数据
     * @return 用户列表
     */
    public List<Admin> findByPhone(String phone, AdminFetcher fetcher) {
        return jSqlClient.createQuery(table)
                .where(table.phone().eq(phone))
                .select(table.fetch(fetcher))
                .execute();
    }

    public Collection<Admin> findById(int id, AdminFetcher fetcher) {
        return jSqlClient.createQuery(table)
                .where(table.id().eq(id))
                .select(table.fetch(fetcher))
                .execute();
    }
}
