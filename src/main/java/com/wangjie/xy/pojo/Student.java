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
 * @TableName tb_student
 */
@TableName("tb_student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    
    private String sno;

    
    private String name;

    
    private String gender;

    
    private String password;

    
    private String email;

    
    private String telephone;

    
    private String address;

    
    private String introducation;

    
    private String portraitPath;

    
    private String clazzName;
}