package com.wangjie.xy.controller;

import com.wangjie.xy.pojo.Admin;
import com.wangjie.xy.pojo.LoginForm;
import com.wangjie.xy.pojo.Student;
import com.wangjie.xy.pojo.Teacher;
import com.wangjie.xy.service.AdminService;
import com.wangjie.xy.service.StudentService;
import com.wangjie.xy.service.TeacherService;
import com.wangjie.xy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token")String token){
        Object user = null;
        Map<String,Object> map = new HashMap();
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null,ResultCodeEnum.TOKEN_ERROR);
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        switch (userType){
            case 1:
                user = adminService.getById(userId);break;
            case 2:
                user = teacherService.getById(userId);break;
            case 3:
                user = studentService.getById(userId);break;
        }
        map.put("userType",userType);
        map.put("user",user);

        return Result.ok(map);
    }


    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("verifiCode", verifiCode);
//        session.setMaxInactiveInterval(10);
        try {
            ImageIO.write(verifiCodeImage, "JPEG", httpServletResponse.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String verifiCode = loginForm.getVerifiCode();
        if (sessionVerifiCode == null || sessionVerifiCode.equals("")) {
            return Result.fail().message("验证码失效，请重新输入!");
        }
        if (verifiCode == null || !verifiCode.equalsIgnoreCase(sessionVerifiCode)) {
            return Result.fail().message("验证码错误，请重新输入!");
        }

        session.removeAttribute("verifiCode");

        Map<String, Object> map = new HashMap<>();
        switch (loginForm.getUserType()) {
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if (admin != null) {
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if (teacher != null) {
                        map.put("token", JwtHelper.createToken(teacher.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Student student = studentService.login(loginForm);
                    if (student != null) {
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或密码错误");
                    }
                    return Result.ok(map);
                }catch (RuntimeException e){
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }

        return Result.fail().message("用户错误");
    }

//    /sms/system/headerImgUpload
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile){
        String uuid = UUID.randomUUID().toString();
        String newFileName=uuid+"__"+multipartFile.getOriginalFilename();
        String portraitPath="C:\\Users\\86150\\IdeaProjects\\xy\\target\\classes\\public\\upload\\".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail().message("上传失败");
        }
        return Result.ok("upload/".concat(newFileName));
    }

//    /sms/system/updatePwd/admin/wangjie
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@RequestHeader("token")String token,
                            @PathVariable("oldPwd")String oldPwd,
                            @PathVariable("newPwd")String newPwd){
        Long userId = JwtHelper.getUserId(token);

        switch (JwtHelper.getUserType(token)){
            case 1:
                Admin admin = adminService.getById(userId);
                if(admin!=null&&admin.getPassword().equals(MD5.encrypt(oldPwd))){
                    admin.setPassword(MD5.encrypt(newPwd));
                    adminService.saveOrUpdate(admin);
                }else {
                    return Result.fail().message("密码修改失败");
                }break;
            case 2:
                Teacher teacher = teacherService.getById(userId);
                if(teacher!=null&&teacher.getPassword().equals(MD5.encrypt(oldPwd))){
                    teacher.setPassword(MD5.encrypt(newPwd));
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return Result.fail().message("密码修改失败");
                }break;
            case 3:
                Student student = studentService.getById(userId);
                if(student!=null&&student.getPassword().equals(MD5.encrypt(oldPwd))){
                    student.setPassword(MD5.encrypt(newPwd));
                    studentService.saveOrUpdate(student);
                }else {
                    return Result.fail().message("密码修改失败");
                }
        }

        return Result.ok();
    }
}
