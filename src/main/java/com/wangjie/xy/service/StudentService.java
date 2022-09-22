package com.wangjie.xy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjie.xy.pojo.LoginForm;
import com.wangjie.xy.pojo.Student;


public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    IPage<Student> getStudentByPage(Page<Student> studentPage, String clazzName, String name);
}
