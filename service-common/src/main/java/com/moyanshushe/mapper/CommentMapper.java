package com.moyanshushe.mapper;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/22 上午17:23
    @Description: 

*/

import com.moyanshushe.model.OrderRule;
import com.moyanshushe.model.dto.comment.*;
import com.moyanshushe.model.entity.*;
import org.babyfish.jimmer.Page;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.mutation.SimpleSaveResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final JSqlClient jSqlClient;

    private final CommentTable table;

    public CommentMapper(JSqlClient jSqlClient) {
        this.jSqlClient = jSqlClient;
        this.table = CommentTable.$;
    }

    public void add(CommentForAdd comment) {
        jSqlClient.save(comment);
    }

    public @NotNull  Page<Comment> query(CommentSpecification specification, CommentFetcher fetcher) {
        return jSqlClient.createQuery(table)
                .where(specification)
                .orderByIf(
                        specification.getOrderByLikes() != null,
                        specification.getOrderByLikes() == OrderRule.DESC ?
                                table.likes().desc() : table.likes().asc()
                )
                .orderByIf(
                        specification.getOrderByCommentTime() != null,
                        specification.getOrderByCommentTime() == OrderRule.DESC ?
                                table.commentTime().desc() : table.commentTime().asc()
                )
                .orderByIf(
                        specification.getOrderById() != null,
                        specification.getOrderById() == OrderRule.DESC ?
                                table.id().desc() : table.id().asc()
                )
                .select(
                        table.fetch(
                            fetcher
                        )
                ).fetchPage(
                        specification.getPage() != null ? specification.getPage() : 0,
                        specification.getPageSize() != null ? specification.getPageSize() : 10
                );
    }

    public SimpleSaveResult<Comment> update(CommentForUpdate comment) {
        return jSqlClient.save(comment.toEntity());
    }

    public void delete(CommentForDelete comment) {
        jSqlClient.deleteByIds(Comment.class, comment.getIds());
    }

    public @NotNull Page<Comment> query(PublicCommentSpecification specification, CommentFetcher fetcher) {
        return jSqlClient.createQuery(table)
                .where(specification)
                .select(
                        table.fetch(
                                fetcher
                        )
                )
                .fetchPage(
                        specification.getPage() != null ? specification.getPage() : 0,
                        specification.getPageSize() != null ? specification.getPageSize() : 10
                );
    }
}
