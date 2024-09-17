package com.moyanshushe.controller;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/9 上午22:34
    @Description: 
*/

import com.moyanshushe.client.CommonServiceClient;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.address.AddressCreateInput;
import com.moyanshushe.model.dto.address.AddressForDelete;
import com.moyanshushe.model.dto.address.AddressSpecification;
import com.moyanshushe.model.dto.address.AddressUpdateInput;
import com.moyanshushe.model.dto.address_part1.AddressPart1CreateInput;
import com.moyanshushe.model.dto.address_part1.AddressPart1ForDelete;
import com.moyanshushe.model.dto.address_part1.AddressPart1Specification;
import com.moyanshushe.model.dto.address_part1.AddressPart1UpdateInput;
import com.moyanshushe.model.dto.address_part2.AddressPart2CreateInput;
import com.moyanshushe.model.dto.address_part2.AddressPart2ForDelete;
import com.moyanshushe.model.dto.address_part2.AddressPart2Specification;
import com.moyanshushe.model.dto.address_part2.AddressPart2UpdateInput;
import com.moyanshushe.model.dto.coupon.CouponCreateInput;
import com.moyanshushe.model.dto.coupon.CouponForDelete;
import com.moyanshushe.model.dto.coupon.CouponInputForUpdate;
import com.moyanshushe.model.dto.coupon.CouponSpecification;
import com.moyanshushe.model.dto.item.ItemForAdd;
import com.moyanshushe.model.dto.item.ItemForDelete;
import com.moyanshushe.model.dto.item.ItemForUpdate;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.dto.category.CategoryCreateInput;
import com.moyanshushe.model.dto.category.CategoryForDelete;
import com.moyanshushe.model.dto.category.CategorySpecification;
import com.moyanshushe.model.dto.category.CategoryUpdateInput;
import com.moyanshushe.model.dto.order.OrderForAdd;
import com.moyanshushe.model.dto.order.OrderForDelete;
import com.moyanshushe.model.dto.order.OrderForUpdate;
import com.moyanshushe.model.dto.order.OrderSpecification;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

/**
 * 管理员，负责处理管理员操作相关的HTTP请求
 */
@Api

@RestController
@RequestMapping({"/admin-manage"})
public class AdminManageController {

    private final CommonServiceClient commonServiceClient;

    @Autowired
    public AdminManageController(CommonServiceClient commonServiceClient) {
        this.commonServiceClient = commonServiceClient;
    }

// Item Operations

    /**
     * 获取商品列表
     *
     * @param specification 商品规格
     *                      ItemSpecification {
     *                      ids id列表
     *                      like(name) 匹配商品名称
     *                      le(price) 最大价格
     *                      ge(price) 最小价格
     *                      status 状态
     *                      flat(user) {
     *                      valueIn(id) as userIds 用户ID列表
     *                      }
     *                      flat(categories) {
     *                      valueIn(id) as labelIds 标签ID列表
     *                      }
     *                      page: Int? 当前页码
     *                      pageSize: Int? 每页数量
     *                      <p>
     *                      orderByPrice: OrderRule? 按价格排序规则
     *                      orderByCreateTime: OrderRule? 按创建时间排序规则
     *                      orderByUpdateTime: OrderRule? 按更新时间排序规则
     *                      }
     * @return 返回商品列表
     */
    @Api
    @PostMapping("/items/fetch")
    public ResponseEntity<Result> fetchItems(@RequestBody ItemSpecification specification) {
        return commonServiceClient.queryItems(specification);
    }

    /**
     * 添加商品
     *
     * @param itemForAdd 商品数据
     *                   ItemForAdd {
     *                   id
     *                   name!
     *                   price!
     *                   description！
     *                   status！
     *                   <p>
     *                   images {
     *                   imageUrl
     *                   }
     *                   <p>
     *                   userId!
     *                   <p>
     *                   categories {
     *                   id
     *                   }
     *                   }
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/items/add")
    public ResponseEntity<Result> addItem(@RequestBody ItemForAdd itemForAdd) {
        return commonServiceClient.addItem(itemForAdd);
    }

    /**
     * 更新商品
     *
     * @param itemForUpdate 商品更新数据
     *                      ItemForUpdate {
     *                      id!
     *                      name
     *                      price
     *                      description
     *                      status
     *                      <p>
     *                      user {
     *                      id
     *                      }
     *                      <p>
     *                      categories {
     *                      id
     *                      }
     *                      <p>
     *                      images {
     *                      imageUrl
     *                      }
     *                      <p>
     *                      updatePerson {
     *                      id
     *                      }
     *                      }
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/items/update")
    public ResponseEntity<Result> updateItem(@RequestBody ItemForUpdate itemForUpdate) {
        return commonServiceClient.updateItem(itemForUpdate);
    }

    /**
     * 删除商品
     *
     * @param itemForDelete 商品删除数据
     *                      ItemForDelete {
     *                      ids: MutableList<Int?>
     *                      operatorId: Int? 操作员ID
     *                      }
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/items/delete")
    public ResponseEntity<Result> deleteItems(@RequestBody ItemForDelete itemForDelete) {
        return commonServiceClient.deleteItems(itemForDelete);
    }

// Address Operations

    /**
     * 添加地址
     *
     * @param addressSubstance 地址数据
     *                         AddressSubstance {
     *                         id
     *                         address!
     *                         <p>
     *                         addressPart1 {
     *                         id
     *                         }!
     *                         addressPart2 {
     *                         id
     *                         }!
     *                         <p>
     *                         createPerson {
     *                         id
     *                         }?
     *                         updatePerson {
     *                         id
     *                         }?
     *                         }
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/addresses/add")
    public ResponseEntity<Result> addAddress(@RequestBody AddressCreateInput addressSubstance) {
        return commonServiceClient.add(addressSubstance);
    }

    /**
     * 查询地址
     *
     * @param addressForQuery 地址查询条件
     *                        AddressSpecification {
     *                        id
     *                        like(address) 匹配地址字符串
     *                        page: Int? 当前页码
     *                        pageSize: Int? 每页数量
     *                        <p>
     *                        createPerson {
     *                        id
     *                        }
     *                        updatePerson {
     *                        id
     *                        }
     *                        }
     * @return 返回地址列表
     */
    @Api
    @PostMapping("/addresses/query")
    public ResponseEntity<Result> queryAddress(@RequestBody AddressSpecification addressForQuery) {
        return commonServiceClient.queryAddress(addressForQuery);
    }

    /**
     * 更新地址
     *
     * @param addressSubstance 地址数据
     *                         AddressSubstance {
     *                         id!
     *                         address!
     *                         <p>
     *                         addressPart1 {
     *                         id
     *                         }!
     *                         addressPart2 {
     *                         id
     *                         }!
     *                         <p>
     *                         createPerson {
     *                         id
     *                         }?
     *                         updatePerson {
     *                         id
     *                         }?
     *                         }
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/addresses/update")
    public ResponseEntity<Result> updateAddress(@RequestBody AddressUpdateInput addressSubstance) {
        return commonServiceClient.updateAddress(addressSubstance);
    }

    /**
     * 删除地址
     *
     * @param addressForDelete 地址删除数据
     *                         AddressForDelete {
     *                         ids: MutableList<Int?>
     *                         }
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/addresses/delete")
    public ResponseEntity<Result> deleteAddress(@RequestBody AddressForDelete addressForDelete) {
        return commonServiceClient.deleteAddress(addressForDelete);
    }

// Image Upload Operation

    /**
     * 上传图片
     *
     * @param file 图片文件
     * @return 返回上传结果
     */
    @Api
    @PostMapping("/images/upload")
    public CompletableFuture<Result> uploadImage(@RequestParam("file") MultipartFile file) {
        return commonServiceClient.uploadImage(file);
    }

// Category Operations

    /**
     * 查询标签
     *
     * @param labelSpecification 标签查询条件
     *                           CategorySpecification {
     *                           ids: MutableList<Int?> 标签ID列表
     *                           page: Int? 当前页码
     *                           pageSize: Int? 每页数量
     *                           like(name) 匹配标签名称
     *                           }
     * @return 返回标签列表
     */
    @Api
    @PostMapping("/categories/query")
    public ResponseEntity<Result> queryCategories(@RequestBody CategorySpecification labelSpecification) {
        return commonServiceClient.queryCategories(labelSpecification);
    }

    /**
     * 添加标签
     *
     * @param labelInput 标签数据
     *                   input CategoryInput {
     *                   id
     *                   name!
     *                   }
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/categories/add")
    public ResponseEntity<Result> addCategory(@RequestBody CategoryCreateInput labelInput) {
        return commonServiceClient.addCategory(labelInput);
    }

    /**
     * 更新标签
     *
     * @param labelInput 标签数据
     *                   input CategoryInput {
     *                   id!
     *                   name!
     *                   }
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/categories/update")
    public ResponseEntity<Result> updateCategory(@RequestBody CategoryUpdateInput labelInput) {
        return commonServiceClient.updateCategory(labelInput);
    }

    /**
     * 删除标签
     *
     * @param labelForDelete 标签删除数据
     *                       CategoryForDelete {
     *                       ids: MutableList<Int?>
     *                       }
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/categories/delete")
    public ResponseEntity<Result> deleteCategory(@RequestBody CategoryForDelete labelForDelete) {
        return commonServiceClient.deleteCategory(labelForDelete);
    }


    // Order Operations

    /**
     * 获取订单
     *
     * @param specification 订单查询条件
     *                      OrderSpecification {
     *                      orderId
     *                      userId
     *                      status
     *                      itemId: Int? 物品ID
     *                      page: Int? 当前页码
     *                      pageSize: Int? 每页数量
     *                      }
     * @return 返回订单列表
     */
    @Api
    @PostMapping("/orders/fetch")
    public ResponseEntity<Result> queryOrder(@RequestBody OrderSpecification specification) {
        return commonServiceClient.queryOrder(specification);
    }

    /**
     * 添加订单
     *
     * @param orderForAdd 订单添加数据
     *                    input OrderForAdd {
     *                    orderId?
     *                    userId
     *                    items {
     *                    id
     *                    }
     *                    createTime?
     *                    updatePerson{
     *                    id
     *                    }?
     *                    }
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/orders/add")
    public ResponseEntity<Result> addOrder(@RequestBody OrderForAdd orderForAdd) {
        return commonServiceClient.addOrder(orderForAdd);
    }

    /**
     * 更新订单
     *
     * @param orderForUpdate 订单更新数据
     *                       OrderForUpdate {
     *                       orderId!
     *                       userId!
     *                       items {
     *                       id
     *                       }
     *                       updatePerson{
     *                       id
     *                       }?
     *                       }
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/orders/update")
    public ResponseEntity<Result> updateOrder(@RequestBody OrderForUpdate orderForUpdate) {
        return commonServiceClient.updateOrder(orderForUpdate);
    }

    /**
     * 删除订单
     *
     * @param orderForDelete 订单删除数据
     *                       OrderForDelete {
     *                       ids: MutableList<UUID?>!
     *                       userId!
     *                       }
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/orders/delete")
    public ResponseEntity<Result> deleteOrder(@RequestBody OrderForDelete orderForDelete) {
        return commonServiceClient.deleteOrder(orderForDelete);
    }

// Coupon Operations

    /**
     * 获取优惠券
     *
     * @param couponSpecification 优惠券查询条件
     *                            CouponSpecification {
     *                            ids: MutableList<Int?>
     *                            userIds: MutableList<Int?>
     *                            status
     *                            like(code) 优惠券代码模糊匹配
     *                            ge(value) 优惠券价值大于等于
     *                            le(value) 优惠券价值小于等于
     *                            ge(startTime) 生效开始时间大于等于
     *                            le(endTime) 生效结束时间小于等于
     *                            page: Int? 当前页码
     *                            pageSize: Int? 每页数量
     *                            }
     * @return 返回优惠券列表
     */
    @Api
    @PostMapping("/coupons/query")
    public ResponseEntity<Result> queryCoupon(@RequestBody CouponSpecification couponSpecification) {
        return ResponseEntity.ok().body(Result.success(commonServiceClient.queryCoupon(couponSpecification)));
    }

    /**
     * 添加优惠券
     *
     * @param couponSubstance 优惠券数据
     *                        CouponSubstance {
     *                        id
     *                        name!
     *                        price!
     *                        status  normal -> 1 expired -> 2
     *                        createTime?
     *                        expirationTime?
     *                        userId!
     *                        }
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/coupons/add")
    public ResponseEntity<Result> addCoupon(@RequestBody CouponCreateInput couponSubstance) {
        return commonServiceClient.addCoupon(couponSubstance);
    }

    /**
     * 更新优惠券
     *
     * @param couponSubstance 优惠券数据
     *                        CouponSubstance {
     *                        id
     *                        name!
     *                        price!
     *                        status  normal -> 1 expired -> 2
     *                        createTime?
     *                        expirationTime?
     *                        userId!
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/coupons/update")
    public ResponseEntity<Result> updateCoupon(@RequestBody CouponInputForUpdate couponSubstance) {
        return commonServiceClient.updateCoupon(couponSubstance);
    }

    /**
     * 删除优惠券
     *
     * @param couponForDelete 优惠券删除数据
     *                        CouponForDelete {
     *                        ids: MutableList<Int?>
     *                        }
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/coupons/delete")
    public ResponseEntity<Result> deleteCoupon(@RequestBody CouponForDelete couponForDelete) {
        return commonServiceClient.deleteCoupon(couponForDelete);
    }

// Address Part 1 Operations

    /**
     * 查询地址部分1信息 (地址部分1是精细的地值，宿舍楼栋)
     *
     * @param specification 地址部分1查询条件
     *                      AddressPart1Specification {
     *                      ids: MutableList<Int?>
     *                      like(name) 名称模糊匹配
     *                      page: Int? 当前页码
     *                      pageSize: Int? 每页数量
     *                      }
     * @return 返回地址部分1列表
     */
    @Api
    @PostMapping("/address-parts1/quert")
    public ResponseEntity<Result> queryAddressPart1(@RequestBody AddressPart1Specification specification) {
        return commonServiceClient.queryAddressPart1(specification);
    }

    /**
     * 添加地址部分1信息
     *
     * @param addressPart1Input 地址部分1数据
     *                          AddressPart1Input {
     *                          id
     *                          name!
     *                          parentAddress
     *                          }
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/address-parts1/add")
    public ResponseEntity<Result> addAddressPart1(@RequestBody AddressPart1CreateInput addressPart1Input) {
        return commonServiceClient.addAddressPart1(addressPart1Input);
    }

    /**
     * 更新地址部分1信息
     *
     * @param addressPart1Input 地址部分1数据
     *                          AddressPart1Input {
     *                          id
     *                          name!
     *                          parentAddress
     *                          }
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/address-parts1/update")
    public ResponseEntity<Result> updateAddressPart1(@RequestBody AddressPart1UpdateInput addressPart1Input) {
        return commonServiceClient.updateAddressPart1(addressPart1Input);
    }

    /**
     * 删除地址部分1信息
     *
     * @param addressPart1ForDelete 地址部分1删除数据
     *                              AddressPart1ForDelete {
     *                              ids: MutableList<Int?>
     *                              }
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/address-parts1/delete")
    public ResponseEntity<Result> deleteAddressPart1(@RequestBody AddressPart1ForDelete addressPart1ForDelete) {
        return commonServiceClient.deleteAddressPart1(addressPart1ForDelete);
    }

// Address Part 2 Operations

    /**
     * 查询地址部分2信息
     *
     * @param specification 地址部分2查询条件
     *                      AddressPart2Specification {
     *                      ids: MutableList<Int?>
     *                      like(name) 名称模糊匹配
     *                      page: Int? 当前页码
     *                      pageSize: Int? 每页数量
     *                      }
     * @return 返回地址部分2列表
     */
    @Api
    @PostMapping("/address-parts2/query")
    public ResponseEntity<Result> readAddressPart2(@RequestBody AddressPart2Specification specification) {
        return commonServiceClient.readAddressPart2(specification);
    }

    /**
     * 添加地址部分2信息(地址部分2是更加高级的地区，如校区)
     *
     * @param addressPart2Input 地址部分2数据
     *                          AddressPart2Input {
     *                          id
     *                          name
     *                          parentAddress
     *                          }
     * @return 返回添加结果
     */
    @Api
    @PostMapping("/address-parts2/add")
    public ResponseEntity<Result> addAddressPart2(@RequestBody AddressPart2CreateInput addressPart2Input) {
        return commonServiceClient.addAddressPart2(addressPart2Input);
    }

    /**
     * 更新地址部分2信息
     *
     * @param addressPart2Input 地址部分2数据
     *                          AddressPart2Input {
     *                          id！
     *                          name
     *                          parentAddress
     *                          }
     * @return 返回更新结果
     */
    @Api
    @PostMapping("/address-parts2/update")
    public ResponseEntity<Result> updateAddressPart2(@RequestBody AddressPart2UpdateInput addressPart2Input) {
        return commonServiceClient.updateAddressPart2(addressPart2Input);
    }

    /**
     * 删除地址部分2信息
     *
     * @param addressPart2ForDelete 地址部分2删除数据
     *                              <p>
     *                              AddressPart2ForDelete {
     *                              ids: MutableList<Int?>
     *                              }
     * @return 返回删除结果
     */
    @Api
    @PostMapping("/address-parts2/delete")
    public ResponseEntity<Result> deleteAddressPart2(@RequestBody AddressPart2ForDelete addressPart2ForDelete) {
        return commonServiceClient.deleteAddressPart2(addressPart2ForDelete);
    }

}
