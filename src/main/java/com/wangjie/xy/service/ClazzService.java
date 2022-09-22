package com.wangjie.xy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjie.xy.pojo.Clazz;


public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzByPage(Page clazzPage, String gradeName, String name);
}
