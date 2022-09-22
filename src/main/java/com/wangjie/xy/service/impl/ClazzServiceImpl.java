package com.wangjie.xy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjie.xy.mapper.ClazzMapper;
import com.wangjie.xy.pojo.Clazz;
import com.wangjie.xy.pojo.Grade;
import com.wangjie.xy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage<Clazz> getClazzByPage(Page clazzPage, String gradeName, String name) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper();
        if(gradeName!=null && !gradeName.equals("")){
            queryWrapper.eq("grade_name",gradeName);
        }
        if(name!=null && !name.equals("")){
            queryWrapper.like("name",name);
        }
        queryWrapper.orderByDesc("id");

        Page<Clazz> page = baseMapper.selectPage(clazzPage, queryWrapper);

        return page;
    }
}
