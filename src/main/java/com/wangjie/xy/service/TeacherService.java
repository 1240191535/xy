package com.wangjie.xy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjie.xy.pojo.LoginForm;
import com.wangjie.xy.pojo.Teacher;


public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    IPage<Teacher> getTeacherByPage(Page<Teacher> teacherPage, String clazzName, String name);
}
