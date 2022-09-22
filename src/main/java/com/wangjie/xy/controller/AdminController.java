package com.wangjie.xy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wangjie.xy.pojo.Admin;
import com.wangjie.xy.pojo.Teacher;
import com.wangjie.xy.service.AdminService;
import com.wangjie.xy.service.TeacherService;
import com.wangjie.xy.util.MD5;
import com.wangjie.xy.util.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {


    @Autowired
    private AdminService adminService;

    @ApiOperation("根据管理员名称模糊查询，分页")
    @GetMapping("/getAllAdmin/{page}/{pageSize}")
    public Result getTeachers(@ApiParam("分页查询的页码数") @PathVariable("page")Integer page,
                              @ApiParam("分页查询的数据条数")@PathVariable("pageSize")Integer pageSize,
                              @ApiParam("分页查询的管理员名称")@RequestParam(value = "name",required = false)String name){
        Page<Admin> adminPage = new Page<>(page,pageSize);
        IPage<Admin> iPage = adminService.getAdminByPage(adminPage,name);
        return Result.ok(iPage);
    }

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        if(admin!=null&&admin.getPassword()!=null&&admin.getPassword()!=""&&admin.getId()==null){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        boolean flag = adminService.saveOrUpdate(admin);
        if(!flag)
            return Result.fail().message("添加失败");
        return Result.ok();
    }

    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> deleteAdminId){
        boolean flag = adminService.removeByIds(deleteAdminId);
        if(!flag)
            return Result.fail().message("删除失败");
        return Result.ok();
    }
}
