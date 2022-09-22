package com.wangjie.xy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangjie.xy.mapper.GradeMapper;
import com.wangjie.xy.pojo.Grade;
import com.wangjie.xy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByPage(Page page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper();
        if(gradeName!=null && !gradeName.equals("")){
            queryWrapper.like("name",gradeName);
        }
        queryWrapper.orderByDesc("id");
        Page<Grade> page1 = baseMapper.selectPage(page, queryWrapper);
        return page1;
    }

}
