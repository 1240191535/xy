package com.wangjie.xy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjie.xy.pojo.Grade;


public interface GradeService extends IService<Grade> {
    IPage<Grade> getGradeByPage(Page page, String gradeName);
}
