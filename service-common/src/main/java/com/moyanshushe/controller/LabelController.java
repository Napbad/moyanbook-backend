package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.LabelConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.label.LabelForDelete;
import com.moyanshushe.model.dto.label.LabelSpecification;
import com.moyanshushe.model.dto.label.LabelInput;
import com.moyanshushe.model.dto.label.LabelSubstance;
import com.moyanshushe.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// LabelController类负责处理标签相关的HTTP请求
@Api
@Slf4j
@RestController
@RequestMapping("/label")
public class LabelController {

    private final LabelService labelService;

    // 构造函数，注入LabelService
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    /*
     * 添加标签
     * @param label 要添加的标签信息
     * @return 返回添加结果，成功则返回成功信息，失败则返回失败信息
     */
    @Api
    @PostMapping("/add")
    public ResponseEntity<Result> addLabel(LabelInput label) {
        Boolean result = labelService.add(label);

        // 根据添加结果返回相应的HTTP响应
        if (Boolean.TRUE.equals(result)){
            return ResponseEntity.ok(Result.success(LabelConstant.LABEL_ADD_SUCCESS));
        } else {
            return ResponseEntity.ok(Result.error(LabelConstant.LABEL_ADD_FAILED));
        }
    }

    /*
     * 查询标签
     * @param labelSpecification 查询条件
     * @return 返回标签查询结果
     */
    @Api
    @PostMapping("/get")
    public ResponseEntity<Result> queryLabels(LabelSpecification labelSpecification) {
        Page<LabelSubstance> result = labelService.query(labelSpecification);

        // 返回查询结果的HTTP响应
        return ResponseEntity.ok(Result.success(result));
    }

    /*
     * 更新标签
     * @param label 要更新的标签信息
     * @return 返回更新结果，成功则返回成功信息，失败则返回失败信息
     */
    @Api
    @PostMapping("/update")
    public ResponseEntity<Result> updateLabel(LabelInput label) {
        Boolean result = labelService.update(label);

        // 根据更新结果返回相应的HTTP响应
        if (Boolean.TRUE.equals(result)){
            return ResponseEntity.ok(Result.success(LabelConstant.LABEL_UPDATE_SUCCESS));
        } else {
            return ResponseEntity.ok(Result.error(LabelConstant.LABEL_UPDATE_FAILED));
        }
    }

    /*
     * 删除标签
     * @param label 要删除的标签信息
     * @return 返回删除结果，成功则返回成功信息，失败则返回失败信息
     */
    @Api
    @PostMapping("/delete")
    public ResponseEntity<Result> deleteLabel(LabelForDelete label) {
        Boolean result = labelService.delete(label);

        // 根据删除结果返回相应的HTTP响应
        if (Boolean.TRUE.equals(result)){
            return ResponseEntity.ok(Result.success(LabelConstant.LABEL_DELETE_SUCCESS));
        } else {
            return ResponseEntity.ok(Result.error(LabelConstant.LABEL_DELETE_FAILED));
        }
    }
}
