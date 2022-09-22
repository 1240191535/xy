package com.wangjie.xy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjie.xy.pojo.Grade;
import com.wangjie.xy.service.GradeService;
import com.wangjie.xy.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

//    /sms/gradeController/getGrades/1/3?gradeName=
    @ApiOperation("根据年级名称模糊查询，分页")
    @GetMapping("/getGrades/{page}/{pageSize}")
    public Result getGrades(@ApiParam("分页查询的页码数") @PathVariable("page")Integer page,
                            @ApiParam("分页查询的数据条数")@PathVariable("pageSize")Integer pageSize,
                            @ApiParam("分页查询的年级名称")@RequestParam(value = "gradeName",required = false)String gradeName){
        Page<Grade> gradePage = new Page<>(page,pageSize);
        IPage<Grade> iPage = gradeService.getGradeByPage(gradePage,gradeName);
        return Result.ok(iPage);
    }

//    /sms/gradeController/saveOrUpdateGrade

    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
        boolean flag = gradeService.saveOrUpdate(grade);
        if(!flag)
            return Result.fail().message("添加失败");
        return Result.ok();
    }

//    /sms/gradeController/deleteGrade
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> deleteGradeId){
        boolean flag = gradeService.removeByIds(deleteGradeId);
        if(!flag)
            return Result.fail().message("删除失败");
        return Result.ok();
    }

    //    /sms/gradeController/getGrades
    @GetMapping("/getGrades")
    public Result getGrades(){
        List allGradesName=gradeService.list();
        return Result.ok(allGradesName);
    }
}
