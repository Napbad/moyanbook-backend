package com.moyanshushe.controller;

import com.moyanshushe.constant.AddressConstant;
import com.moyanshushe.constant.CommonConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address.AddressForDelete;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressSubstance;
import com.moyanshushe.model.entity.Address;
import com.moyanshushe.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地址管理控制器
 * 负责处理与用户地址相关的HTTP请求，包括查询、添加、更新和删除地址。
 */
@Api
@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    /**
     * 通过@Autowired注解自动注入AddressService实例
     * 用于在控制器中调用地址服务方法。
     *
     * @param addressService 地址服务接口的实现
     */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * 查询地址列表
     * 根据提供的查询条件，获取用户的地址列表。
     *
     * @param addressSpecification 查询条件对象，包含ID等信息
     * @return 包含地址列表的结果对象
     */
    @Api
    @PostMapping("/get")
    public ResponseEntity<Result> get(
            @RequestBody AddressSpecification addressSpecification) {

        Page<Address> page = addressService.query(addressSpecification);

        return ResponseEntity.ok(
                Result.success(page)
        );
    }

    /**
     * 添加地址
     * 接收一个新的地址对象，将其添加到数据库。
     *
     * @param addressSubstance 待添加的地址详情对象
     * @return 添加结果的对象，成功或失败
     */
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> add(
            @RequestBody AddressSubstance addressSubstance) {

        Boolean result = addressService.add(addressSubstance);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result) ? Result.success(AddressConstant.ADDRESS_ADD_SUCCESS) : Result.error(AddressConstant.ADDRESS_ADD_FAILURE)
        );
    }

    /**
     * 更新地址
     * 根据提供的地址对象更新数据库中的地址信息。
     *
     * @param addressSubstance 待更新的地址详情对象，包含ID等标识信息
     * @return 更新结果的对象，成功或失败
     */
    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> update(
            @RequestBody AddressSubstance addressSubstance) {

        if (addressSubstance.getId() == null) {
            return ResponseEntity.ok(Result.error(CommonConstant.INPUT_INVALID));
        }

        Boolean result = addressService.update(addressSubstance);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result)
                        ? Result.success(AddressConstant.ADDRESS_UPDATE_SUCCESS)
                        : Result.error(AddressConstant.ADDRESS_UPDATE_FAILURE)
        );
    }

    /**
     * 删除地址
     * 根据提供的删除条件，从数据库中删除相应的地址。
     *
     * @param addressForDelete 删除条件对象，包含待删除地址的ID等信息
     * @return 删除结果的对象，成功或失败
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> delete(
            @RequestBody AddressForDelete addressForDelete) {

        Boolean result = addressService.delete(addressForDelete);

        return ResponseEntity.ok(
                Boolean.TRUE.equals(result)
                        ? Result.success(AddressConstant.ADDRESS_DELETE_SUCCESS)
                        : Result.error(AddressConstant.ADDRESS_DELETE_FAILURE)
        );
    }
}
