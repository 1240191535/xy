package com.wangjie.xy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjie.xy.pojo.Admin;
import com.wangjie.xy.pojo.LoginForm;


public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginForm);

    IPage<Admin> getAdminByPage(Page<Admin> adminPage, String name);
}
