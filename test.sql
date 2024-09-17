drop database if exists item_sell_db_test;

create database item_sell_db_test;

use item_sell_db_test;

create table address_part2
(
    id             int unsigned auto_increment comment 'id'
        primary key,
    name           varchar(127) not null comment '区名',
    parent_address int unsigned null comment '隶属于'
)
    comment '区';

create table address_part1
(
    id             int unsigned auto_increment comment '主键'
        primary key,
    name           varchar(127) not null comment '地址名',
    parent_address int unsigned not null comment '隶属于的地址',
    constraint address_part1_address_part2_id_fk
        foreign key (parent_address) references address_part2 (id)
)
    comment '园栋';

create table address
(
    id               int unsigned auto_increment
        primary key,
    address          varchar(255) default 'cqu'             not null comment '详细地址',
    address_part1    int unsigned default '0'               not null comment '地址段1',
    address_part2    int unsigned default '0'               not null comment '地址段2',
    is_deleted       int unsigned default '0'               not null comment '逻辑删除',
    create_person_id int unsigned default '0'               not null comment '创建人id',
    update_person_id int unsigned default '0'               not null comment '更新人id',
    create_time      timestamp    default CURRENT_TIMESTAMP not null,
    update_time      timestamp    default CURRENT_TIMESTAMP not null,
    constraint address_address_part1_id_fk
        foreign key (address_part1) references address_part1 (id),
    constraint address_address_part2_id_fk
        foreign key (address_part2) references address_part2 (id)
)
    comment '地址表';

create index ADDRESS_id_index
    on address (id);

create table admin
(
    id               int unsigned auto_increment comment '管理员id，主键'
        primary key,
    name             varchar(20)  default ''          not null comment '管理员名',
    age              int unsigned default '18'        null comment '管理员年龄',
    gender           tinyint                          null comment '管理员性别',
    email            varchar(30)  default 'null'      null comment '管理员邮箱',
    phone            varchar(20)                      null comment '管理员手机号',
    password         varchar(64)                      not null comment '管理员密码',
    status           tinyint                          null comment '管理员状态  1: 正常，2：冻结',
    create_time      date         default (curdate()) not null comment '管理员创建时间',
    update_time      date         default (curdate()) not null comment '管理员更新时间',
    profile_url      varchar(128)                     null comment '头像网址',
    last_login_time  date         default (curdate()) not null comment '上一次登录时间',
    address_id       int unsigned default '0'         null comment '管理员地址对应的id',
    is_deleted       tinyint(1)   default 0           not null comment '账户是否已经删除',
    update_person_id int unsigned default '0'         not null comment '更新人id',
    type             int unsigned default '1'         not null comment '管理员形式',
    create_person_id int unsigned default '0'         not null
);

create table category
(
    id          int unsigned auto_increment comment 'category id，主键'
        primary key,
    name        varchar(60)  default ''                    not null comment 'category 名',
    description varchar(255) default 'default description' not null comment 'category 描述'
);

create table item_code
(
    id         int unsigned auto_increment
        primary key,
    code_part1 int unsigned not null comment '地址',
    code_part2 int unsigned not null comment '编码',
    item_id    int unsigned null comment '对应的物品id'
)
    comment '物件码';

create table label
(
    id          int unsigned auto_increment
        primary key,
    name        varchar(63)  default 'nothing'             not null,
    description varchar(255) default 'default description' not null
);

create table member
(
    id                  int unsigned auto_increment comment '管理员id'
        primary key,
    name                varchar(63)                            not null comment '管理员名称',
    gender              tinyint      default 0                 not null comment '性别',
    status              tinyint      default 1                 not null comment '状态',
    profile_url         varchar(255)                           not null comment '头像网址',
    address_id          int unsigned                           null comment '地址信息',
    is_deleted          tinyint(1)   default 0                 not null comment '逻辑删除字段',
    area_responsibility int unsigned default '1'               null comment '负责区域',
    phone               char(11)                               not null comment '手机号',
    email               varchar(63)                            not null comment '邮箱',
    password            char(64)                               not null comment '密码',
    create_person_id    int unsigned default '0'               not null comment '创建人',
    update_person_id    int unsigned                           not null comment '更新人',
    create_time         timestamp    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time         timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint member_address_id_fk
        foreign key (address_id) references address (id)
)
    comment '成员表';

create index member_name_index
    on member (name);

create table member_address_responsibility_mapping
(
    id               int unsigned auto_increment comment '主键'
        primary key,
    member_id        int unsigned             not null comment '成员id',
    address_id       int unsigned             not null comment '管理地区id',
    is_deleted       int unsigned default '1' not null comment '逻辑删除',
    create_person_id int unsigned default '1' not null comment '创建人员id',
    update_person_id int unsigned default '1' not null comment '更新人id',
    constraint admin_responsibility_address_mapping_address_id_fk
        foreign key (address_id) references address (id),
    constraint admin_responsibility_address_mapping_admin_id_fk
        foreign key (member_id) references member (id)
)
    comment '成员管理地区映射表';

create table member_confirm
(
    id           int unsigned auto_increment comment '主键',
    confirm_code char(32)                 not null comment '确定码',
    status       int unsigned             not null comment '状态',
    operation    int unsigned             not null comment '操作，接收或者送出',
    member_id    int unsigned             not null comment '对应管理员id',
    is_deleted   int unsigned default '1' not null comment '逻辑删除',
    update_time  timestamp                not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint member_confirm_confirm_code_uindex
        unique (confirm_code),
    constraint member_item_confirm_pk
        unique (id),
    constraint member_confirm_member_id_fk
        foreign key (member_id) references member (id)
);

create table user
(
    id               int unsigned auto_increment comment '用户id，主键'
        primary key,
    name             varchar(20)  default ''          not null comment '用户名',
    age              int unsigned default '18'        null comment '用户年龄',
    gender           tinyint                          null comment '用户性别',
    email            varchar(30)  default 'null'      null comment '用户邮箱',
    phone            varchar(20)                      null comment '用户手机号',
    password         varchar(64)                      not null comment '用户密码',
    status           tinyint      default 1           null comment '用户状态  1: 正常，2：冻结，3：过期',
    create_time      date         default (curdate()) not null comment '用户创建时间',
    update_time      date         default (curdate()) not null comment '用户更新时间',
    profile_url      varchar(128)                     null comment '头像网址',
    last_login_time  date         default (curdate()) not null comment '上一次登录时间',
    address_id       int unsigned default '1'         null comment '用户地址对应的id',
    is_deleted       tinyint(1)   default 0           not null comment '账户是否已经删除',
    update_person_id int unsigned default '0'         not null comment '更新人id',
    type             int unsigned default '1'         not null comment '用户形式',
    create_person_id int unsigned default '0'         not null,
    constraint user_address_id_fk
        foreign key (address_id) references address (id)
);

create table comment
(
    id           int unsigned auto_increment
        primary key,
    content      text                                   not null,
    commenter_id int unsigned                           not null,
    label_id     int unsigned                           null,
    parent_id    int unsigned                           null,
    comment_time timestamp    default CURRENT_TIMESTAMP null,
    likes        int unsigned default '0'               null,
    dislikes     int unsigned default '0'               null,
    status       tinyint      default 1                 null comment '1 正常，0 隐藏',
    is_deleted   tinyint      default 0                 null comment '逻辑删除, 0 未删除，1 已删除',
    constraint comment_ibfk_1
        foreign key (commenter_id) references user (id),
    constraint comment_ibfk_2
        foreign key (label_id) references label (id),
    constraint comment_ibfk_3
        foreign key (parent_id) references comment (id)
);

create index commenter_id
    on comment (commenter_id);

create index item_id
    on comment (label_id);

create index parent_id
    on comment (parent_id);

create table comment_history
(
    id            int unsigned auto_increment
        primary key,
    comment_id    int unsigned                           not null,
    content       text                                   not null,
    modified_by   int unsigned                           not null,
    modified_time timestamp    default CURRENT_TIMESTAMP not null,
    is_deleted    int unsigned default '0'               not null,
    constraint comment_history_ibfk_1
        foreign key (comment_id) references comment (id),
    constraint comment_history_ibfk_2
        foreign key (modified_by) references user (id)
);

create index comment_id
    on comment_history (comment_id);

create index modified_by
    on comment_history (modified_by);

create table comment_like
(
    id         int unsigned auto_increment
        primary key,
    user_id    int unsigned                        not null,
    comment_id int unsigned                        not null,
    like_time  timestamp default CURRENT_TIMESTAMP null,
    constraint comment_likes_ibfk_1
        foreign key (user_id) references user (id),
    constraint comment_likes_ibfk_2
        foreign key (comment_id) references comment (id)
);

create index comment_id
    on comment_like (comment_id);

create table comment_report
(
    id          int unsigned auto_increment
        primary key,
    comment_id  int unsigned                        not null,
    reporter_id int unsigned                        not null,
    report_time timestamp default CURRENT_TIMESTAMP null,
    reason      text                                null,
    status      tinyint   default 0                 null comment '状态，0 未处理， 1 处理中， 2 处理结束',
    is_deleted  tinyint   default 0                 null comment '逻辑删除, 0 未删除，1 已删除',
    constraint comment_reports_ibfk_1
        foreign key (comment_id) references comment (id),
    constraint comment_reports_ibfk_2
        foreign key (reporter_id) references user (id)
);

create index comment_id
    on comment_report (comment_id);

create index reporter_id
    on comment_report (reporter_id);

create table coupon
(
    id              int unsigned auto_increment comment '优惠券id，主键'
        primary key,
    name            varchar(20)  default ''                                 not null comment '优惠券名',
    price           decimal(10, 2)                                          not null comment '优惠券价格',
    description     varchar(128) default '这是一个优惠券'                   not null comment '优惠券描述',
    status          tinyint      default 1                                  not null comment '优惠券状态  0: 正常，1：下架',
    create_time     datetime     default (curtime())                        not null comment '优惠券创建时间',
    expiration_time datetime     default ((`create_time` + interval 7 day)) not null comment '优惠券过期时间',
    user_id         int unsigned                                            not null comment '优惠券所属用户id',
    constraint coupon_ibfk_1
        foreign key (user_id) references user (id)
);

create table item
(
    id               int unsigned auto_increment comment '书籍id，主键'
        primary key,
    name             varchar(20)  default ''          not null comment '书籍名',
    price            decimal(10, 2)                   not null comment '书籍价格',
    description      varchar(128)                     null comment '书籍描述',
    create_time      date         default (curdate()) not null comment '书籍创建时间',
    update_time      timestamp    default (curdate()) not null on update CURRENT_TIMESTAMP comment '书籍更新时间',
    profile_url      varchar(128)                     null comment '书籍图片网址',
    user_id          int unsigned                     not null comment '书籍所属用户id',
    update_person_id int unsigned default (`user_id`) not null comment '更新人',
    amount           int unsigned default '1'         not null comment '数量',
    is_deleted       int unsigned default '0'         not null,
    status           smallint     default 1           not null,
    category_id      int unsigned                     not null comment '书籍分类id',
    constraint item_ibfk_1
        foreign key (user_id) references user (id)
);

create index user_id
    on item (user_id);

create table item_image
(
    id        int unsigned auto_increment comment '书籍图片id，主键'
        primary key,
    item_id   int unsigned not null comment '书籍id',
    image_url varchar(255) not null comment '图片网址',
    constraint item_image_item_id_fk
        foreign key (item_id) references item (id)
)
    comment '书籍图片';

create table item_label_mapping
(
    id       int unsigned auto_increment comment '书籍标签映射id，主键'
        primary key,
    item_id  int unsigned not null comment '书籍id',
    label_id int unsigned not null comment '标签id',
    constraint item_label_mapping_ibfk_1
        foreign key (item_id) references item (id),
    constraint item_label_mapping_ibfk_2
        foreign key (label_id) references label (id)
);

create index item_id
    on item_label_mapping (item_id);

create index label_id
    on item_label_mapping (label_id);

create table member_confirm_item_mapping
(
    id           int unsigned auto_increment comment 'id'
        primary key,
    confirm_code char(32)     not null comment '确认码',
    item_id      int unsigned not null comment '对应的物件id',
    constraint member_confirm_item_mapping_item_id_fk
        foreign key (item_id) references item (id),
    constraint member_confirm_item_mapping_member_confirm_confirm_code_fk
        foreign key (confirm_code) references member_confirm (confirm_code)
);

create table order_detail
(
    order_id         char(36)                            not null comment 'id',
    status           tinyint   default 0                 not null comment '订单状态',
    create_time      datetime  default CURRENT_TIMESTAMP not null comment '创建时间',
    user_id          int unsigned                        not null comment '订单对应用户id',
    update_time      timestamp default (now())           not null on update CURRENT_TIMESTAMP,
    update_person_id int unsigned                        not null,
    is_deleted       tinyint   default 0                 not null comment '逻辑删除',
    constraint order_detail_order_id_uindex
        unique (order_id),
    constraint shopping_cart_user_id_fk
        foreign key (user_id) references user (id)
)
    comment '订单';

create table order_item_mapping
(
    id          int unsigned auto_increment comment '订单映射id，主键'
        primary key,
    order_id    char(36)                 not null comment '订单id',
    item_id     int unsigned             not null comment '物品id',
    item_number int unsigned default '1' not null comment '物品数量',
    constraint order_item_mapping_ibfk_2
        foreign key (item_id) references item (id),
    constraint order_item_mapping_order_detail_order_id_fk
        foreign key (order_id) references order_detail (order_id)
);

create index id_index
    on user (id);



INSERT INTO address_part2 (name)
VALUES ('渝北区'),
       ('南岸区'),
       ('沙坪坝区'),
       ('九龙坡区'),
       ('江北区');

INSERT INTO address_part1 (name, parent_address)
VALUES ('大学城', 1),
       ('解放碑', 2),
       ('磁器口', 3),
       ('石桥铺', 4),
       ('观音桥', 5);

INSERT INTO address (address, address_part1, address_part2, create_person_id, update_person_id)
VALUES ('沙坪坝大学城南路1号', 1, 1, 1, 1),
       ('南岸区南滨路1号', 2, 2, 1, 1),
       ('渝北区新牌坊转盘', 3, 1, 1, 1),
       ('九龙坡区杨家坪步行街', 4, 4, 1, 1),
       ('江北区观音桥步行街', 5, 5, 1, 1);

INSERT INTO member (name, gender, status, profile_url, phone, email, password, create_person_id, update_person_id)
VALUES ('Napbad', 1, 1, 'https://memberA.com/avatar.jpg', '13800000001', 'memberA@example.com', 'hashed_pwd_A', 1, 1),
       ('管理员B', 0, 1, 'https://memberB.com/avatar.jpg', '13800000002', 'memberB@example.com', 'hashed_pwd_B', 1, 2),
       ('管理员C', 1, 1, 'https://memberC.com/avatar.jpg', '13800000003', 'memberC@example.com', 'hashed_pwd_C', 1, 3),
       ('管理员D', 0, 1, 'https://memberD.com/avatar.jpg', '13800000004', 'memberD@example.com', 'hashed_pwd_D', 1, 4),
       ('管理员E', 1, 1, 'https://memberE.com/avatar.jpg', '13800000005', 'memberE@example.com', 'hashed_pwd_E', 1, 5);


INSERT INTO user (name, age, gender, email, password, update_person_id, address_id, status)
VALUES ('Napbad', 18, 1, 'userA@example.com', 'hashed_pwd_userA', 1, 1, 1),
       ('用户乙', 28, 0, 'userB@example.com', 'hashed_pwd_userB', 2, 3, 1),
       ('用户丙', 25, 1, 'userC@example.com', 'hashed_pwd_userC', 3, 2, 1),
       ('用户丁', 30, 0, 'userD@example.com', 'hashed_pwd_userD', 4, 5, 1),
       ('Moyan', 26, 1, 'userE@example.com', 'hashed_pwd_userE', 5, 4, 2);

INSERT INTO coupon (name, price, description, status, user_id)
VALUES ('迎新优惠券', 10.00, '欢迎新用户，满100元可用', 0, 1),
       ('会员专享', 5.00, '会员特惠，全场通用', 0, 2),
       ('周末特卖', 20.00, '本周末购物满200元减20元', 0, 3),
       ('节日红包', 3.50, '节日限定，无门槛使用', 0, 4),
       ('生日礼券', 15.00, '生日当月专享，全场八折', 0, 5);
-- 第一条记录
INSERT INTO category (name)
VALUES ('科幻'),
       ('历史'),
       ('编程'),
       ('心理学'),
       ('文学');

INSERT INTO label (name)
VALUES ('Data Structure'),
       ('Algorithm '),
       ('Computer Science'),
       ('Design Pattern'),
       ('MySQL');

INSERT INTO item (name, price, description, status, create_time, update_time, profile_url, user_id, update_person_id, category_id)
VALUES ('编程珠玑', 88.50, '探索编程之美与高效算法实践。', 0, CURDATE(), NOW(),
        'https://example.com/programming-pearls.jpg', 1, 1, 3)
     , ('深入浅出MySQL', 55.00, '全面理解MySQL核心原理与优化技巧。', 0, CURDATE(), NOW(),
        'https://example.com/mysql-inside-out.jpg', 1, 1, 3)
     , ('算法导论', 120.00, '算法领域的权威著作，涵盖基础到高级算法。', 0, CURDATE(), NOW(),
        'https://example.com/introduction-to-algorithms.jpg', 1, 1, 3)
     , ('设计模式：可复用面向对象软件的基础', 79.99, '软件工程中经典的设计模式介绍。', 0, CURDATE(), NOW(),
        'https://example.com/design-patterns.jpg', 1, 1, 3)
     , ('计算机程序的构造和解释', 99.00, '深入讲解编程语言原理的经典教材。', 0, CURDATE(), NOW(),
        'https://example.com/sicp.jpg', 1, 1, 3);


-- 示例订单数据插入
INSERT INTO order_detail(order_id, status, create_time, user_id, update_time, update_person_id, is_deleted)
VALUES ('e5b9fc6b-d2f2-4a88-8a04-203efcd4d081', 00, NOW(), 1, NOW(), 1, 0),
       ('f762aa11-2d1f-4c42-a4bb-056dbae1c37b', 10, NOW() - INTERVAL 1 DAY, 3, NOW(), 1, 0),
       ('ca3f4f7c-93d6-4c5d-b47e-cfa2ee74df7a', 20, NOW() - INTERVAL 2 DAY, 2, NOW(), 1, 0),
       ('d4d6b23d-e9b6-41ad-85cb-746a30f40a2d', 10, NOW(), 1, NOW(), 3, 0),
       ('ab9c1def-77c8-4acb-a22c-f10bddd0f743', 10, NOW() - INTERVAL 3 HOUR, 4, NOW(), 2, 0),
       ('9e7b8a3c-2b9d-4d42-a551-7bddd766943b', 20, NOW() - INTERVAL 1 WEEK, 5, NOW(), 1, 0),
       ('cfebe1b3-7f57-4876-a7e5-2c1f4c1d294d', 10, NOW(), 1, NOW(), 3, 0),
       ('a8c5ebf6-c7a5-4da1-9436-40e2e9f4c389', 10, NOW() - INTERVAL 6 HOUR, 2, NOW(), 4, 0),
       ('b45d6a9d-955f-4264-a766-450e93e5d402', 20, NOW() - INTERVAL 3 DAY, 3, NOW(), 5, 0),
       ('e9f758e8-cc66-43ce-8d26-31f3e2730f70', 10, NOW(), 2, NOW(), 3, 0);

INSERT INTO order_item_mapping(order_id, item_id, item_number)
VALUES ('e5b9fc6b-d2f2-4a88-8a04-203efcd4d081', 1, 1), -- 订单1关联商品1
       ('e5b9fc6b-d2f2-4a88-8a04-203efcd4d081', 2, 1), -- 订单1关联商品2
       ('f762aa11-2d1f-4c42-a4bb-056dbae1c37b', 3, 1), -- 订单2关联商品3
       ('ca3f4f7c-93d6-4c5d-b47e-cfa2ee74df7a', 4, 1), -- 订单3关联商品4
       ('d4d6b23d-e9b6-41ad-85cb-746a30f40a2d', 5, 1), -- 订单4关联商品5
       ('ab9c1def-77c8-4acb-a22c-f10bddd0f743', 1, 1);
-- 订单5关联商品1

-- 插入第一条记录
INSERT INTO item_code (code_part1, code_part2, item_id)
VALUES (1, 001, 1);

-- 插入第二条记录
INSERT INTO item_code (code_part1, code_part2, item_id)
VALUES (2, 002, 2);

-- 假设存在用户ID为1, 2, 3和物品ID为101, 102
INSERT INTO comment (commenter_id, label_id, content,comment_time, parent_id)
VALUES
    (1, 1, '这是一个非常好的产品！', NOW() , NULL),
    (2, 1, '我同意，质量超出了我的预期。', NOW() , 1),
    (3, 2, '对于价格来说，这真是个好交易。', NOW() , NULL),
    (1, 2, '确实，性价比很高。', NOW() , 3),
    (2, 3, '包装非常专业，我很满意。', NOW() , NULL),
    (1, 3, '我也注意到了，包装真的很用心。', NOW() , 5),
    (2, 4, '我收到了错误的产品，正在等待解决。', NOW() , NULL),
    (2, 4, '很遗憾听到这个问题，希望客服能很快解决。', NOW() , 7),
    (1, 5, '产品描述准确无误，非常满意。', NOW() , NULL),
    (1, 5, '是的，完全符合描述，推荐购买。', NOW() , 9);

insert into comment_history (comment_id, content, modified_by)
values (1, '这是个很棒的产品！', 1),
       (2, '确实不错，但是价格偏高。', 2),
       (3, '我同意楼上的观点。', 3),
       (4, '产品设计非常人性化。', 1),
       (5, '质量非常好，强烈推荐。', 2);

insert into comment_like (user_id, comment_id)
values (1, 1),
       (2, 1),
       (3, 2),
       (1, 4),
       (2, 5);

-- 假设存在评论ID为1, 2, 3, 4, 5和用户ID为1, 2, 3
insert into comment_report (comment_id, reporter_id, reason)
values (1, 3, '不实评论'),
       (2, 1, '广告嫌疑');

