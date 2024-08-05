package com.moyanshushe.service.impl;

import com.moyanshushe.exception.InitFailedException;
import com.moyanshushe.model.entity.ItemCodeTable;
import com.moyanshushe.service.InitService;
import com.moyanshushe.utils.ItemCodeGenerator;
import jakarta.annotation.PostConstruct;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Service
public class InitServiceImpl implements InitService {

    private final JSqlClient jsqlClient;
    private final ItemCodeTable itemCodeTable;

    public InitServiceImpl(JSqlClient jsqlClient) {
        this.jsqlClient = jsqlClient;
        this.itemCodeTable = ItemCodeTable.$;
    }

    @Override
    @PostConstruct
    public void initService() {
        List<Integer> list = jsqlClient.createQuery(itemCodeTable)
                .select(itemCodeTable.codePart2().max())
                .execute();

        if (list.size() != 1) {
            throw new InitFailedException();
        } else {
            ItemCodeGenerator.init(list.getFirst());
        }
    }
}
