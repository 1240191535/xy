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
 * @TableName tb_grade
 */
@TableName("tb_grade")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grade implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;


    private String name;


    private String manager;


    private String email;


    private String telephone;


    private String introducation;
}