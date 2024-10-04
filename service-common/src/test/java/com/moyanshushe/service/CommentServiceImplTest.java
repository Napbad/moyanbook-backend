package com.moyanshushe.service;
/*
    @Author: Napbad
    @Version: 0.1    
    @Date: 2024/7/25 上午16:52
    @Description: 

*/

import com.moyanshushe.model.OrderRule;
import com.moyanshushe.model.dto.comment.CommentForAdd;
import com.moyanshushe.model.dto.comment.CommentForDelete;
import com.moyanshushe.model.dto.comment.CommentForUpdate;
import com.moyanshushe.model.dto.comment.CommentSpecification;
import com.moyanshushe.model.entity.Comment;
import org.babyfish.jimmer.Page;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CommentServiceImplTest {

    @Autowired
    CommentService service;

    @Test
    @Order(1)
    void add() {
        ArrayList<CommentForAdd> commentForAdds = new ArrayList<>();

        CommentForAdd commentForAdd1 = new CommentForAdd();
        commentForAdd1.setCommenterId(1);
        commentForAdd1.setItemId(4); // 根据JSON数据修正itemId
        commentForAdd1.setCommentTime(LocalDateTime.parse("2023-04-01 12:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        commentForAdd1.setContent("这款产品真的超乎我的期待，质量非常好。");
        commentForAdd1.setDislikes(2);
        commentForAdd1.setLikes(15);
        commentForAdd1.setParentId(null);
        commentForAdds.add(commentForAdd1);

        CommentForAdd commentForAdd2 = new CommentForAdd();
        commentForAdd2.setCommenterId(2);
        commentForAdd2.setItemId(4);
        commentForAdd2.setCommentTime(LocalDateTime.parse("2023-04-01 12:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        commentForAdd2.setContent("我同意楼上，这是我买过的最好的产品之一。");
        commentForAdd2.setDislikes(1);
        commentForAdd2.setLikes(10);
        commentForAdd2.setParentId(1); // 设置parentId为1，表示是commentForAdd1的子评论
        commentForAdds.add(commentForAdd2);

        CommentForAdd commentForAdd3 = new CommentForAdd();
        commentForAdd3.setCommenterId(2);
        commentForAdd3.setItemId(1);
        commentForAdd3.setCommentTime(LocalDateTime.parse("2023-04-01 13:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        commentForAdd3.setContent("包装也很精美，送礼的话是个不错的选择。");
        commentForAdd3.setDislikes(0);
        commentForAdd3.setLikes(8);
        commentForAdd3.setParentId(null);
        commentForAdds.add(commentForAdd3);

        CommentForAdd commentForAdd4 = new CommentForAdd();
        commentForAdd4.setCommenterId(1);
        commentForAdd4.setItemId(3);
        commentForAdd4.setCommentTime(LocalDateTime.parse("2023-04-01 14:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        commentForAdd4.setContent("我遇到了一些问题，希望客服能尽快解决。");
        commentForAdd4.setDislikes(0);
        commentForAdd4.setLikes(3);
        commentForAdd4.setParentId(null);
        commentForAdds.add(commentForAdd4);


        commentForAdds.forEach(service::add);

        Page<Comment> page = service.query(new CommentSpecification());

        assertEquals(commentForAdds.size() + 10, page.getTotalRowCount());

    }

    @Test
    @Order(2)
    void query() {
        Page<Comment> page = service.query(new CommentSpecification());

        assertEquals(14, page.getTotalRowCount());

        CommentSpecification commentSpecification = new CommentSpecification();

        commentSpecification.setOrderById(OrderRule.DESC);
        page = service.query(commentSpecification);

        final Integer[] id = {page.getRows().getFirst().id()};
        page.getRows().forEach(
                comment -> {
                    assertTrue(comment.id() <= id[0]);
                    id[0] = comment.id();
                }
        );

        commentSpecification.setOrderById(OrderRule.ASC);
        page = service.query(commentSpecification);
        assertEquals(14, page.getTotalRowCount());
        id[0] = page.getRows().getFirst().id();
        page.getRows().forEach(
                comment -> {
                    assertTrue(comment.id() >= id[0]);
                    id[0] = comment.id();
                }
        );

        commentSpecification.setCommenterId(1);
        page = service.query(commentSpecification);

        assertEquals(7, page.getTotalRowCount());

        commentSpecification.setItemId(4);
        page = service.query(commentSpecification);

        assertEquals(1, page.getTotalRowCount());
    }

    @Test
    @Order(3)
    void update() {
        // 假设我们有一个现有的评论ID
        CommentSpecification commentSpecification = new CommentSpecification();
        commentSpecification.setPage(1);
        Page<Comment> query = service.query(commentSpecification);
        int existingCommentId = query.getRows().getFirst().id();

        // 创建一个更新的DTO对象
        CommentForUpdate updatedComment = new CommentForUpdate();
        updatedComment.setId(existingCommentId);
        updatedComment.setContent("更新后的评论内容");
        updatedComment.setDislikes(3);
        updatedComment.setLikes(20);
        updatedComment.setItemId(4);
        updatedComment.setCommenterId(3);
        updatedComment.setCommentTime(LocalDateTime.now());
        
        // 调用服务层的更新方法
        service.update(updatedComment);

        // 查询更新后的评论
        commentSpecification.setId(existingCommentId);
        commentSpecification.setPage(0);
        Page<Comment> page = service.query(commentSpecification);
        
        // 验证更新是否成功
        Comment comment = page.getRows().getFirst();
        assertEquals(updatedComment.getCommenterId(), comment.commenterId());
        assertEquals(updatedComment.getContent(), comment.content());
        assertEquals(updatedComment.getLikes(), comment.likes());


    }

    @Test
    @Order(4)
    void delete() {
        // 假设我们要删除的评论ID
        CommentSpecification commentSpecification = new CommentSpecification();
        commentSpecification.setPageSize(20);
        Page<Comment> page = service.query(commentSpecification);

        List<Integer> list = page.getRows().stream()
                .map(Comment::id)
                .filter(id -> id > 10)
                .toList();

        CommentForDelete commentForDelete = new CommentForDelete();
        commentForDelete.setIds(list);

        // 调用服务层的删除方法
        service.delete(commentForDelete);

        // 验证评论是否已被删除
        page = service.query(commentSpecification);
        
        // 如果删除成功，应该返回大小为10的结果集
        assertEquals(10, page.getTotalRowCount());
    }

}
