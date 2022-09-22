package com.wangjie.xy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjie.xy.mapper.TeacherMapper;
import com.wangjie.xy.pojo.Admin;
import com.wangjie.xy.pojo.Clazz;
import com.wangjie.xy.pojo.LoginForm;
import com.wangjie.xy.pojo.Teacher;
import com.wangjie.xy.service.TeacherService;
import com.wangjie.xy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("name",loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        Teacher teacher = baseMapper.selectOne(teacherQueryWrapper);
        return teacher;
    }

    @Override
    public IPage<Teacher> getTeacherByPage(Page<Teacher> clazzPage, String clazzName, String name) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper();
        if(clazzName!=null && !clazzName.equals("")){
            queryWrapper.eq("clazz_name",clazzName);
        }
        if(name!=null && !name.equals("")){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");

        Page<Teacher> page = baseMapper.selectPage(clazzPage, queryWrapper);

        return page;
    }
}
