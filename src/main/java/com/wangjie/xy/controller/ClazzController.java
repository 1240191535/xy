package com.wangjie.xy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjie.xy.pojo.Clazz;
import com.wangjie.xy.pojo.Grade;
import com.wangjie.xy.pojo.Teacher;
import com.wangjie.xy.service.ClazzService;
import com.wangjie.xy.service.TeacherService;
import com.wangjie.xy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    public ClazzController() {
    }

    @ApiOperation("根据年级名称和班级名称模糊查询，分页")
    @GetMapping({"/getClazzsByOpr/{page}/{pageSize}"})
    public Result getClazzs(
            @ApiParam("分页查询的页码数") @PathVariable("page") Integer page,
            @ApiParam("分页查询的数据条数") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的年级名称") @RequestParam(value = "gradeName", required = false) String gradeName,
            @ApiParam("分页查询的班级名称") @RequestParam(value = "name", required = false) String name) {
        Page<Clazz> clazzPage = new Page((long) page, (long) pageSize);
        IPage<Clazz> iPage = this.clazzService.getClazzByPage(clazzPage, gradeName, name);
        return Result.ok(iPage);
    }

    @PostMapping({"/saveOrUpdateClazz"})
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz) {
        boolean flag = this.clazzService.saveOrUpdate(clazz);
        return !flag ? Result.fail().message("添加失败") : Result.ok();
    }

    @DeleteMapping({"/deleteClazz"})
    public Result deleteClazz(@RequestBody List<Integer> deleteClazzId) {
        boolean flag = this.clazzService.removeByIds(deleteClazzId);
        return !flag ? Result.fail().message("删除失败") : Result.ok();
    }

    @GetMapping({"/getClazzs"})
    public Result getClazzs() {
        List allClazzsName = this.clazzService.list();
        return Result.ok(allClazzsName);
    }
}
