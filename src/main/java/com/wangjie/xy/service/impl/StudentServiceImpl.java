package com.wangjie.xy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjie.xy.mapper.StudentMapper;
import com.wangjie.xy.pojo.LoginForm;
import com.wangjie.xy.pojo.Student;
import com.wangjie.xy.service.StudentService;
import com.wangjie.xy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("name",loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword()));
        Student student = baseMapper.selectOne(studentQueryWrapper);
        return student;
    }

    @Override
    public IPage<Student> getStudentByPage(Page<Student> studentPage, String clazzName, String name) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper();
        if(clazzName!=null && !clazzName.equals("")){
            queryWrapper.eq("clazz_name",clazzName);
        }
        if(name!=null && !name.equals("")){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");

        Page<Student> page = baseMapper.selectPage(studentPage, queryWrapper);

        return page;
    }
}
