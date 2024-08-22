package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.CategoryConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.category.*;
import com.moyanshushe.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// CategoryController类负责处理标签相关的HTTP请求
@Api
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService labelService;

    // 构造函数，注入CategoryService
    public CategoryController(CategoryService labelService) {
        this.labelService = labelService;
    }

    /*
     * 添加标签
     * @param category 要添加的标签信息
     * @return 返回添加结果，成功则返回成功信息，失败则返回失败信息
     */
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> addCategory(@RequestBody CategoryCreateInput category) {
        labelService.add(category);

        // 根据添加结果返回相应的HTTP响应
        return ResponseEntity.ok(Result.success(CategoryConstant.CATEGORY_ADD_SUCCESS));
    }

    /*
     * 查询标签
     * @param labelSpecification 查询条件
     * @return 返回标签查询结果
     */
    @Api
    @PostMapping("/query")
    public ResponseEntity<Result> queryCategories(@RequestBody CategorySpecification labelSpecification) {
        Page<CategorySubstance> result = labelService.query(labelSpecification);

        // 返回查询结果的HTTP响应
        return ResponseEntity.ok(Result.success(result));
    }

    /*
     * 更新标签
     * @param category 要更新的标签信息
     * @return 返回更新结果，成功则返回成功信息，失败则返回失败信息
     */
    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> updateCategory(@RequestBody CategoryUpdateInput category) {
        labelService.update(category);

        // 根据更新结果返回相应的HTTP响应
        return ResponseEntity.ok(Result.success(CategoryConstant.CATEGORY_UPDATE_SUCCESS));
    }

    /*
     * 删除标签
     * @param category 要删除的标签信息
     * @return 返回删除结果，成功则返回成功信息，失败则返回失败信息
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteCategory(@RequestBody CategoryForDelete category) {
        labelService.delete(category);

        // 根据删除结果返回相应的HTTP响应
        return ResponseEntity.ok(Result.success(CategoryConstant.CATEGORY_DELETE_SUCCESS));
    }
}
