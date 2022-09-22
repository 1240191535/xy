package com.wangjie.xy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjie.xy.pojo.Teacher;
import com.wangjie.xy.service.TeacherService;
import com.wangjie.xy.util.MD5;
import com.wangjie.xy.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("根据班级名称和教师名称模糊查询，分页")
    @GetMapping("/getTeachers/{page}/{pageSize}")
    public Result getTeachers(@ApiParam("分页查询的页码数") @PathVariable("page")Integer page,
                             @ApiParam("分页查询的数据条数")@PathVariable("pageSize")Integer pageSize,
                             @ApiParam("分页查询的年级名称")@RequestParam(value = "clazzName",required = false)String clazzName,
                             @ApiParam("分页查询的学生名称")@RequestParam(value = "name",required = false)String name){
        Page<Teacher> TeacherPage = new Page<>(page,pageSize);
        IPage<Teacher> iPage = teacherService.getTeacherByPage(TeacherPage,clazzName,name);
        return Result.ok(iPage);
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        if(teacher!=null&&teacher.getPassword()!=null&&teacher.getPassword()!=""&&teacher.getId()==null){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        boolean flag = teacherService.saveOrUpdate(teacher);
        if(!flag)
            return Result.fail().message("添加失败");
        return Result.ok();
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> deleteTeacherId){
        boolean flag = teacherService.removeByIds(deleteTeacherId);
        if(!flag)
            return Result.fail().message("删除失败");
        return Result.ok();
    }
}
