package com.wuyue.video.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String sign; // ✨ 补上这个个性签名字段

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}