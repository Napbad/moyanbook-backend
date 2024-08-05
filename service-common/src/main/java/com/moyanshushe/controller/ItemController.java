package com.moyanshushe.controller;

import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.item.ItemForAdd;
import com.moyanshushe.model.dto.item.ItemForDelete;
import com.moyanshushe.model.dto.item.ItemForUpdate;
import com.moyanshushe.model.dto.item.ItemSpecification;
import com.moyanshushe.model.entity.Item;
import com.moyanshushe.service.ItemService;
import com.moyanshushe.utils.AliOssUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ItemController类负责处理所有与书籍管理相关的REST API操作。
 * 它使用ItemService与底层数据层进行交互。
 */
@Api
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    /**
     * 一个私有的ItemService实例，处理书籍操作的业务逻辑。
     */
    private final ItemService itemService;

    private final AliOssUtil aliOssUtil;

    /**
     * 根据给定的规格获取一页书籍的API端点。
     *
     * @param specification 书籍查询规格，定义了查询的过滤器和排序
     * @return 符合规格的书籍实体列表（分页）
     */
    @Api
    @PostMapping("/fetch")
    public Page<Item> fetch(@RequestBody ItemSpecification specification) {
        return itemService.query(specification);
    }

    /**
     * 向系统中添加新书的API端点。
     *
     * @param itemForAdd 包含新书详情的ItemForAdd DTO
     * @return 表示书本已成功添加的成功响应
     */
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> add(@RequestBody ItemForAdd itemForAdd) {
        itemForAdd.getImages().stream()
                .map(ItemForAdd.TargetOf_images::getImageUrl)
                .forEach(
                        aliOssUtil::checkUrlIsAliOss
                );

        itemService.add(itemForAdd);

        return ResponseEntity.ok(Result.success());
    }

    /**
     * 更新系统中已有书籍的API端点。
     *
     * @param itemForUpdate 包含更新后书籍详情的ItemForUpdate DTO
     * @return 表示书本已成功更新的成功响应
     */
    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> update(@RequestBody ItemForUpdate itemForUpdate) {
        itemService.update(itemForUpdate);

        itemForUpdate.getImages().stream()
                .map(ItemForUpdate.TargetOf_images::getImageUrl)
                .forEach(
                        aliOssUtil::checkUrlIsAliOss
                );

        return ResponseEntity.ok(Result.success());
    }

    /**
     * 从系统中删除书籍的API端点。
     *
     * @param itemForDelete 包含要删除书籍标识的ItemForDelete DTO
     * @return 表示书本已成功删除的成功响应
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> delete(@RequestBody ItemForDelete itemForDelete) {
        itemService.delete(itemForDelete);

        return ResponseEntity.ok(Result.success());
    }

    /**
     * 根据给定的规格获取一页书籍的API端点。
     *
     * @param specification 书籍查询规格，定义了查询的过滤器和排序
     * @return 符合规格的书籍实体列表（分页）
     */
    @Api
    @PostMapping("/fetch-public")
    public Page<Item> fetchPublic(@RequestBody ItemSpecification specification) {
        return itemService.query(specification);
    }
}
