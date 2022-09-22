package com.wangjie.xy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjie.xy.mapper.AdminMapper;
import com.wangjie.xy.pojo.Admin;
import com.wangjie.xy.pojo.Clazz;
import com.wangjie.xy.pojo.LoginForm;
import com.wangjie.xy.service.AdminService;
import com.wangjie.xy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Override
    public Admin login(LoginForm loginForm) {

        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("name",loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        Admin admin = baseMapper.selectOne(adminQueryWrapper);
        return admin;
    }

    @Override
    public IPage<Admin> getAdminByPage(Page<Admin> adminPage, String name) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper();
        if(name!=null && !name.equals("")){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");

        Page<Admin> page = baseMapper.selectPage(adminPage, queryWrapper);

        return page;
    }
}
