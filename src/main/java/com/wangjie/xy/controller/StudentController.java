package com.wangjie.xy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjie.xy.pojo.Clazz;
import com.wangjie.xy.pojo.Student;
import com.wangjie.xy.service.ClazzService;
import com.wangjie.xy.service.StudentService;
import com.wangjie.xy.util.MD5;
import com.wangjie.xy.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    //    /sms/clazzController/getStudentByOpr/1/3
    @ApiOperation("根据班级名称和学生名称模糊查询，分页")
    @GetMapping("/getStudentByOpr/{page}/{pageSize}")
    public Result getStudent(@ApiParam("分页查询的页码数") @PathVariable("page")Integer page,
                            @ApiParam("分页查询的数据条数")@PathVariable("pageSize")Integer pageSize,
                            @ApiParam("分页查询的年级名称")@RequestParam(value = "clazzName",required = false)String clazzName,
                            @ApiParam("分页查询的学生名称")@RequestParam(value = "name",required = false)String name){
        Page<Student> studentPage = new Page<>(page,pageSize);
        IPage<Student> iPage = studentService.getStudentByPage(studentPage,clazzName,name);
        return Result.ok(iPage);
    }

    //    /sms/clazzController/addOrUpdateStudent
    @PostMapping("/addOrUpdateStudent")
    public Result saveOrUpdateClazz(@RequestBody Student student){
        if(student!=null&&student.getPassword()!=null&&student.getPassword()!=""&&student.getId()==null){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        boolean flag = studentService.saveOrUpdate(student);
        if(!flag)
            return Result.fail().message("添加失败");
        return Result.ok();
    }

    //  /sms/clazzController/delStudentById
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> deleteStudentId){
        boolean flag = studentService.removeByIds(deleteStudentId);
        if(!flag)
            return Result.fail().message("删除失败");
        return Result.ok();
    }
}
