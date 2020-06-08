package com.example.discovery.student.service;

import com.example.discovery.student.model.Student;

/**
 * @Author: fzx
 * @DateTime: 2020/6/7 11:10
 * @Description: TODO
 */
public interface StudentService {
    /**
     * 新增学生
     * @param student
     * @return
     */
    Boolean addStudent(Student student);
}
