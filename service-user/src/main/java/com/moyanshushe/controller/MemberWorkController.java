package com.moyanshushe.controller;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.moyanshushe.constant.WebIOConstant;
import com.moyanshushe.model.Result;
import com.moyanshushe.model.dto.item.ItemForConfirm;
import com.moyanshushe.model.entity.MemberConfirm;
import com.moyanshushe.service.MemberWorkService;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.meta.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
// 成员工作类，由于无支付功能，暂时弃用
@Api
@Slf4j
@RestController
@RequestMapping({"/member-work"})
public class MemberWorkController {

    private final MemberWorkService memberWorkService;

    public MemberWorkController(MemberWorkService memberWorkService) {
        this.memberWorkService = memberWorkService;
    }

    @GetMapping("/query-by-queryid/{queryId}")
    public ResponseEntity<Result> queryByQueryId (
            @PathVariable String queryId
    ) {
        Collection<MemberConfirm> list = memberWorkService.queryItems(queryId);

        return ResponseEntity.ok(Result.success(list));
    }

    @PostMapping()
    public ResponseEntity<Result> remind() {
        memberWorkService.remindToReceiveItem();

        return ResponseEntity.ok(Result.success());
    }

    @PostMapping("/confirm-receive")
    public ResponseEntity<Result> confirmReceive(
            @RequestParam String confirmCode
    ) {
        memberWorkService.confirmToReceive(confirmCode);

        return ResponseEntity.ok(Result.success());
    }

    @PostMapping("/deliver")
    public ResponseEntity<Result> deliver (
            @RequestBody ItemForConfirm itemForConfirm
            ) {
        if (itemForConfirm.getMemberConfirm() == null) {
            return ResponseEntity.badRequest().body(Result.error(WebIOConstant.INPUT_INVALID));
        }

        memberWorkService.confirmToDeliver(itemForConfirm);

        return ResponseEntity.ok(Result.success());
    }
}
