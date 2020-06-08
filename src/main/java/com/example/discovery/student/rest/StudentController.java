package com.example.discovery.student.rest;

import com.example.discovery.student.model.Student;
import com.example.discovery.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: fzx
 * @DateTime: 2020/6/7 11:05
 * @Description: TODO
 */
@Controller
@RequestMapping("api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("")
    public void addStudent(@RequestBody Student student){
        studentService.addStudent(student);
    }
}
