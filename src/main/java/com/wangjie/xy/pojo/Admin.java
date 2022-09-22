package com.wangjie.xy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName tb_admin
 */
@TableName("tb_admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String portraitPath;

}