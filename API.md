# Api Document

## Response Format

所有的响应格式为JSON，包含以下字段：

```json
{
"code": "int   0 -> fail, 1 -> success",
"data": "object  real data, the `output` of the API， 此处是响应的数据",
"message": "string 此处是响应的消息如‘删除成功’"
}
```


## UserController

### verify (/user/verify)

发送验证码到用户

输入, email, phone选择其一发送，只能接受一个合法输入且仅能发送到一个
验证码时长10min

```json
{
    "email": "string",
    "phone": "string"
}
```

返回

```json
{
    "message": ""
}

```

### register (/user/register)

注册

输入

暂时无需输入手机号码，email需要与verify发送验证码一致才能注册成功，同时要求用户名，邮箱，唯一

默认address可以为空

type 1 为普通用户，2 为商户，暂时只支持注册普通用户，若type为空，默认为1，非空亦会被设置为1

用户名要求如下： 名字可能由字母（包括大小写）、数字、下划线和连字符组成，且可以包含空格，长度在4 - 20之间
密码要求如下：   要求必须包含至少一个数字、一个字母，长度在8到2     0个字符之间
电话号码：      电话号码校验仅适用于中国手机号码
```json
    {
        "name": "张三",
        "email": "zhangsan@example.com",
        "password": "password", 
        "captcha": "123456",
        "address": {
            "id": 1,        
            "address": "No. 123, Example Street",
            "addressPart1": {
                "id": 1
            },
            "addressPart2": {
                "id": 2
            }
        },
        "type": 1
    }

```

返回

```json

{
    "id": 1,
    "name": "李四",
    "gender": "male",
    "age": 30,
    "type": "user",
    "status": "active",
    "email": "lisi@example.com",
    "address": {
        "id": 1,
        "address": "No. 123, Example Street",
        "addressPart1": {
            "id": 1,
            "name": "District A"
        },
        "addressPart2": {
            "id": 2,
            "name": "City B"
        }
    }
}

```

### login (/user/login)

登录

输入：

同时只支持一种登陆方式（id, name, 邮箱或者手机）

```json

{
    "id": 1,
    "name": "赵六",
    "phone": "12345678901",
    "email": "zhaoliu@example.com",
    "password": "password"
}

```

输出：
返回用户的个人信息
status ： 1为正常，2为冻结

```json

{ "user":{
    "id": 1,
    "name": "王五",
    "age": 28,
    "gender": "female",
    "email": "wangwu@example.com",
    "phone": "12345678901",
    "status": 1,
    "type": 1,
    "profileUrl": "https://example.com/profile/wangwu",
    "address": {
        "id": 1,
        "address": "No. 123, Example Street",
        "addressPart1": {
            "id": 1,
            "name": "District A"
        },
        "addressPart2": {
            "id": 2,
            "name": "City B"
        }
}
},
"token": "token ajdfljkacjinoea uoisvnovflksfvjncwucfhidjfv " 
}

```

### update (/user/update)

更新用户信息

输入：
```json

{
    "id": 1,
    "name": "张三",
    "gender": "male",
    "age": 25,
    "profileUrl": "https://example.com/profile/zhangsan",
    "address": {
        "name": "No. 123, Example Street",
        "addressPart1": {
            "id": 1
        },
        "addressPart2": {
            "id": 2
        }
    }
}

```

输出：

```json

{
    "id": 1,
    "name": "王五",
    "age": 28,
    "gender": "female",
    "email": "wangwu@example.com",
    "phone": "12345678901",
    "status": 1,
    "type": 1,
    "profileUrl": "https://example.com/profile/wangwu",
    "address": {
        "id": 1,
        "address": "No. 123, Example Street",
        "addressPart1": {
            "id": 1,
            "name": "District A"
        },
        "addressPart2": {
            "id": 2,
            "name": "City B"
        }
    }
}

```

### update password (/user/change-password)

修改密码
输入：

```json
{
    "id": 1,
    "email": "zhangsan@example.com",
    "newPassword": "password", 
    "captcha": "123456"
}
```

输出：

```json
{
    "message": ""
}
```

### item query (/user/item/query)

查询用户发布的商品
输入：

OrderRule: ASC, DESC

```json
{       
    "ids": [1, 2, 3],  
    "name": "example_name",  
    "maxPrice": 100,  
    "minPrice": 50,  
    "status": 1,

    "categoryIds": [101, 102],
    "labelIds": [1, 2],

    "page": 1,  
    "pageSize": 10,  

    "orderByPrice": "ASC",  
    "orderByCreateTime": "DESC",  
    "orderByUpdateTime": "ASC"  
}
```

输出：

```json
[
{
    "id": 1,
    "name": "Example Product",
    "price": 99.99,
    "description": "This is an example product description.",
    "status": 1,
    "amount": 10,

    "images": [
        {
            "imageUrl": "https://example.com/image1.jpg"
        },
        {
            "imageUrl": "https://example.com/image2.jpg"
        }
    ],

    "category": {
        "id": 1,
        "name": "Electronics"
    },

    "labels": [
        {
            "id": 1,
            "name": "New Arrival"
        },
        {
            "id": 2,
            "name": "Best Seller"
        }
    ],

    "createTime": "2023-10-01T12:00:00Z",
    "updateTime": "2023-10-02T10:00:00Z"
}
]
```

### item add (/user/item/add)

添加商品

输入：

名称，价格，状态都不应为空

状态： 0： 未开售 1： 已开售

```json
{
    "name": "Example Product",
    "price": 99.99,
    "description": "This is an example product description.",
    "status": 1,
    "amount": 10,

    "images": [
        {
            "imageUrl": "https://example.com/image1.jpg"
        },
        {
            "imageUrl": "https://example.com/image2.jpg"
        }
    ],

    "category": {
        "id": 1
    },

    "labels": [
        {
            "id": 1
        },
        {
            "id": 2
        }
    ]
}
```

输出：

```json
{
    "id": 1,
    "name": "Example Product",
    "price": 99.99,
    "description": "This is an example product description.",
    "status": "active",
    "amount": 10,

    "images": [
        {
            "imageUrl": "https://example.com/image1.jpg"
        },
        {
            "imageUrl": "https://example.com/image2.jpg"
        }
    ],

    "category": {
        "id": 1,
        "name": "Electronics"
    },

    "labels": [
        {
            "id": 1,
            "name": "New Arrival"
        },
        {
            "id": 2,
            "name": "Best Seller"
        }
    ],

    "createTime": "2023-10-01T12:00:00Z"
}
```

### item update (/user/item/update)

更新商品信息，需要完整数据，之后会支持不完整数据

输入：

```json
{
    "id": 1,
    "name": "Example Product",
    "price": 99.99,
    "description": "This is an example product description.",
    "status": 0             ,

    "category": {
        "id": 1
    },

    "images": [
        {
            "imageUrl": "https://example.com/image1.jpg"
        },
        {
            "imageUrl": "https://example.com/image2.jpg"
        }
    ],

    "labels": [
        {
            "id": 1
        },
        {
            "id": 2
        }
    ]
}
```

<!-- or 
```json
{
    "id": 1,
    "name": "Example Product",
    "description": "This is an example product description.",

    "category": {
        "id": 1
    }
}
``` -->

输出：
```json
{
    "id": 1,
    "name": "Example Product",
    "price": 99.99,
    "description": "This is an example product description.",
    "status": "active",
    "amount": 10,

    "images": [
        {
            "id":1,
                        "imageUrl": "https://example.com/image1.jpg"
        },
        {
            "id":2,
            "imageUrl": "https://example.com/image2.jpg"
        }
    ],

    "category": {
        "id": 1,
        "name": "Electronics"
    },

    "labels": [
        {
            "id": 1,
            "name": "New Arrival"
        },
        {
            "id": 2,
            "name": "Best Seller"
        }
    ],

    "createTime": "2023-10-01T12:00:00Z",
    "updateTime": "2023-10-02T10:00:00Z"
}
```

### item delete (/user/item/delete)

删除商品

输入：
```json
{
    "ids": [1, 2, 3],
    "operatorId": 1
}
```

输出：
```json
{
    "message": ""
}
```

### category query(/user/category/query)

查询分区列表

输入
```json
{
  "ids": [1, 2, 3],
  "page": 0,
  "pageSize": 10,
  "name": "name(模糊查询)"
}
```

输出：
```json
{
  "totalRowCount": 2,
  "totalPageCount": 1,
  "rows": [
    {
      "id": 0,
      "name": "java"
    },
    {
      "id": 0,
      "name": "cpp"
    }
  ]
}
```

### upload image (/file/upload/image)

上传图片
输入：

> multipart/form-data
> name = file

输出：
```json
{
    "message": "actual url"
}
``` 

### query address (user/address/query)

查询地址

输入：

```json
{
  "id": 1,
  "like": "address",
  "page": 1,
  "pageSize": 10
}
```
输出：
```json
{
  "rows": [
    {
      "id": 1,
      "address": "Address 1",
      "addressPart1": {
        "id": 101,
        "name": "Part 1-1"
      },
      "addressPart2": {
        "id": 201,
        "name": "Part 2-1"
      }
    },
    {
      "id": 2,
      "address": "Address 2",
      "addressPart1": {
        "id": 102,
        "name": "Part 1-2"
      },
      "addressPart2": {
        "id": 202,
        "name": "Part 2-2"
      }
    }
  ],
  "totalRowCount": 2,
  "totalPageCount": 1
}
```

### add address (user/address/add)

添加地址

输入：
```json
    {
    "address": "Sample Address",
    "addressPart1": {
        "id": 101
    },
    "addressPart2": {
        "id": 201
    }
    }
```

输出：
```json
{
  "id": 1,
  "address": "Sample Address",
  "addressPart1": {
    "id": 101,
    "name": "Part 1 Name"
  },
  "addressPart2": {
    "id": 201,
    "name": "Part 2 Name"
  }
}
```

### query address part1 (user/address-part1/query)

查询地址一级分区

输入：
```json
{
  "id": 1,
  "like": "name",
  "parentAddressId": 1,
  "page": 1,
  "pageSize": 10
}
```

输出：
```json
{
  "rows": [
    {
      "id": 1,
      "name": "Name 1",
      "parentAddress": {
        "id": 101,
        "name": "Parent Address 1"
      }
    },
    {
      "id": 2,
      "name": "Name 2",
      "parentAddress": {
        "id": 102,
        "name": "Parent Address 2"
      }
    }
  ],
  "totalRowCount": 2,
  "totalPageCount": 1
}
```

### query address part2 (user/address-part2/query)

查询地址二级分区

输入：
```json
{
  "id": 1,
  "like": "name",
  "parentAddress": 1,
  "page": 1,
  "pageSize": 10
}
```

输出：
```json
{
  "rows": [
    {
      "id": 1,
      "name": "Name 1"
    },
    {
      "id": 2,
      "name": "Name 2"
    }
  ],
  "totalRowCount": 2,
  "totalPageCount": 1
}
```

### query order (user/order/query)

查询订单

输入：

status: 
    NORMAL = 10;    
    WAIT_FOR_PAYING = 20;
    FINISHED = 30;

```json
{
  "orderId": "123e4567-e89b-12d3-a456-426614174000",
  "userId": "19427890",
  "status": 1,
  "itemId": 1001,
  "page": 1,
  "pageSize": 10
}

```

输出：
```json
{
    "rows": [
        {
  "userId": "19427890",
  "orderId": "123e4567-e89b-12d3-a456-426614174000",
  "status": 10,
  "items": [
    {
      "id": 1,
      "images": [
        {
          "id": 101,
          "imageUrl": "https://example.com/image1.jpg"
        },
        {
          "id": 102,
          "imageUrl": "https://example.com/image2.jpg"
        }
      ],
      "category": {
        "id": 201,
        "name": "Electronics"
      },
      "name": "Product 1",
      "status": "In Stock",
      "description": "This is a great product.",
      "user": {
        "name": "John Doe",
        "profileUrl": "https://example.com/profile/johndoe",
        "type": "Customer",
        "status": "Active"
      },
      "price": 199.99
    },
    {
      "id": 2,
      "images": [
        {
          "id": 201,
          "imageUrl": "https://example.com/image3.jpg"
        },
        {
          "id": 202,
          "imageUrl": "https://example.com/image4.jpg"
        }
      ],
      "category": {
        "id": 202,
        "name": "Books"
      },
      "name": "Product 2",
      "status": "Out of Stock",
      "description": "This is another great product.",
      "user": {
        "name": "Jane Smith",
        "profileUrl": "https://example.com/profile/janesmith",
        "type": "Vendor",
        "status": "Inactive"
      },
      "price": 29.99
    }
  ],
  "createTime": "2023-10-01T12:00:00Z"
}
    ],
    "totalRowCount": 1,
    "totalPageCount": 1
}

```

### add order (user/order/add)

添加订单

输入：
```json
{
  "userId": "19427890",
  "items": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ],
  "createTime": "2023-10-01T12:00:00Z"
}
```

输出：
```json
{
  "userId": "19427890",
  "orderId": "123e4567-e89b-12d3-a456-426614174000",
  "status": 10,
  "items": [
    {
      "id": 1,
      "images": [
        {
          "id": 101,
          "imageUrl": "https://example.com/image1.jpg"
        },
        {
          "id": 102,
          "imageUrl": "https://example.com/image2.jpg"
        }
      ],
      "category": {
        "id": 201,
        "name": "Electronics"
      },
      "name": "Product 1",
      "status": "In Stock",
      "description": "This is a great product.",
      "user": {
        "name": "John Doe",
        "profileUrl": "https://example.com/profile/johndoe",
        "type": "Customer",
        "status": "Active"
      },
      "price": 199.99
    },
    {
      "id": 2,
      "images": [
        {
          "id": 201,
          "imageUrl": "https://example.com/image3.jpg"
        },
        {
          "id": 202,
          "imageUrl": "https://example.com/image4.jpg"
        }
      ],
      "category": {
        "id": 202,
        "name": "Books"
      },
      "name": "Product 2",
      "status": "Out of Stock",
      "description": "This is another great product.",
      "user": {
        "name": "Jane Smith",
        "profileUrl": "https://example.com/profile/janesmith",
        "type": "Vendor",
        "status": "Inactive"
      },
      "price": 29.99
    }
  ],
  "createTime": "2023-10-01T12:00:00Z"
}
```

### update order (user/order/update)

更新订单

输入：
```json
{
  "orderId": "123e4567-e89b-12d3-a456-426614174000  ",
  "userId": "1234",
  "items": [
    {
      "id": 1
    }
  ],
  "updatePerson": {
    "id": 1234
  }
}
```

输出：
```json
{
  "userId": "19427890",
  "orderId": "ORD123456",
  "status": 10,
  "items": [
    {
      "id": 1,
      "images": [
        {
          "id": 101,
          "imageUrl": "https://example.com/image1.jpg"
        },
        {
          "id": 102,
          "imageUrl": "https://example.com/image2.jpg"
        }
      ],
      "category": {
        "id": 201,
        "name": "Electronics"
      },
      "name": "Product 1",
      "status": "In Stock",
      "description": "This is a great product.",
      "user": {
        "name": "John Doe",
        "profileUrl": "https://example.com/profile/johndoe",
        "type": "Customer",
        "status": "Active"
      },
      "price": 199.99
    }
  ],
  "createTime": "2023-10-01T12:00:00Z"
}
```

### delete order (user/order/delete)

删除订单

输入：
```json
{
  "ids": [
    "123e4567-e89b-12d3-a456-426614174000",
    "123e4567-e89b-12d3-a456-426614174001",
    "123e4567-e89b-12d3-a456-426614174002"
  ],
  "userId": "19427890"
}
```
输出：
```json
{
    "message": ""
}
```

### query coupon (user/coupon/query)

查询优惠券

status:
        NORMAL = 1;
        EXPIRED = 2;
输入：
```json
{
  "id": 1,
  "like": "name",
  "priceMin": 100.0,
  "priceMax": 500.0,
  "createTimeMin": "2023-10-01T00:00:00Z",
  "createTimeMax": "2023-10-31T23:59:59Z",
  "expirationTimeMin": "2023-11-01T00:00:00Z",
  "expirationTimeMax": "2023-12-31T23:59:59Z",
  "status": 1,
  "userId": "1234980",
  "page": 1,
  "pageSize": 10
}
```

输出：
```json
{
  "id": 1,
  "name": "Example Product",
  "price": 199.99,
  "status": "Active",
  "createTime": "2023-10-01T12:00:00Z",
  "expirationTime": "2023-12-31T23:59:59Z",
  "userId": "13687612",
  "description": "This is an example product description."
}
```

##  SearchController

### search item (search/item)

搜索商品

status: normal = 1;
        unsafe = 2;
        freeze = 3;

type:   normal = 1;
        store  = 2;

输入：
```json
{
    "ids": [],
    "name": "",
    "maxPrice": 0,
    "minPrice": 2147483647,
    "status": 0,
    "userIds": [],
    "userType": 0,
    "categoryIds": [],
    "page": 0,
    "pageSize": 10,
    "orderByPrice": "ASC",
    "orderByCreateTime": "DESC",
    "orderByUpdateTime": "ASC   "
}
```

输出：
```json
{
  "id": null,
  "name": null,
  "price": null,
  "description": null,
  "status": null,
  "images": [
    {
      "imageUrl": null
    }
  ],
  "user": {
    "id": 0     ,
    "name": null
  },
  "category": {
    "id": 0,
    "name": null
  }   
}
```

### search user (search/user)

搜索用户

输入：
```json
{
  "id": null,
  "name": null,
  "type": null,
  "email": null,
  "phone": null,
  "page": null,
  "pageSize": null
}
```

输出：
```json
{
  "id": 0,
  "name": "",
  "gender": "",
  "status": "",
  "profileUrl": "",
  "type": ""
}
```

## CommentController

### addComment (/user/comment/add)

添加评论

**输入**:

```json
{
  "content": "这是一个评论",
  "commenterId": 1,
  "labelId": 2,
  "parentId": 1,
  "commentTime": 1690000000000
}
```

**返回**:

```json
{
  "message": "评论成功"
}
```

### deleteComment (/user/comment/delete)

删除评论

**输入**:

```json
{
  "commenterId": 1,
  "ids": [1,2,3]
}
```

**返回**:

```json
{
  "message": "评论删除成功"
}
```

### updateComment (/user/comment/update)

更新评论

**输入**:

```json
{
  "commenterId": 1,
  "id": 1,
  "content": "string"
}
```

**返回**:

```json
{
  "data": {
    "id": 1,
    "content": "string"
  },
  "message": "评论更新成功"
}
```

### queryComment (/user/comment/query)

查询评论

**输入**:

```json
{
  "specification": "object"
}
```

**返回**:

```json
{
  "data": {
    "rows": [
      {
        "id": 1,
        "content": "string",
        "commenter": {
          "id": 1,
          "name": "string",
          "type": "string",
          "profileUrl": "string"
        },
        "label": {
          "id": 1,
          "name": "string"
        },
        "parentId": 2,
        "commentTime": "2023-08-08T08:08:08.888Z",
        "children": [],
        "likes": 10
      }
    ],
    "totalRowCount": 100,
    "totalPageCount": 10
  },
  "message": "评论查询成功"
}
```

## CommentLikeController

### addCommentLike (/user/comment-like/add)

添加点赞

**输入**:

```json
{
  "userId": "int",
  "commentId": "int"
}
```

**返回**:

```json
{
  "message": "点赞成功"
}
```

### deleteCommentLike (/user/comment-like/delete)

删除点赞

**输入**:

```json
{
  "userId": "int",
  "commentId": "int"
}
```

**返回**:

```json
{   
  "message": "取消点赞成功"
}
```

### queryCommentLike (/user/comment-like/query)

查询点赞情况

**输入**:

```json
{
  "userId": "int",
  "commentIds": "list of int",
  "likeTimeMax": "time-string",
  "likeTimeMin": "time-string",

  "page": "int",
  "pageSize": "int"
}
```

**返回**:

```json
{
  "code": "int 0 -> fail, 1 -> success",

  "data": {
    "rows": [
      {
        "id": "1",
        "user": {
          "id": 123,
          "name": "张三",
          "type": 0,
          "status": 1,
          "profileUrl": "https://example.com/profile/u123"
        },
        "comment": {
          "id": 456,
          "content": "这是一个示例评论。",
          "likes": 15,
          "commenter": {
            "id": 789,
            "name": "李四",
            "type": 0,
            "status": 1,
            "profileUrl": "https://example.com/profile/u789"
          }
        },
        "likeTime": "2023-10-01T10:30:00Z"
      }
    ],
    "totalRowCount": 100,
    "totalPageCount": 10
  },

  "message": "查询点赞成功"
}
```


