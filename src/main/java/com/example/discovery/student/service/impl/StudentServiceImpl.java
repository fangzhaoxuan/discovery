package com.example.discovery.student.service.impl;

import com.example.discovery.student.mapper.StudentMapper;
import com.example.discovery.student.model.Student;
import com.example.discovery.student.service.StudentService;
import org.graalvm.compiler.serviceprovider.ServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: fzx
 * @DateTime: 2020/6/7 11:10
 * @Description: TODO
 */
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Boolean addStudent(Student student) {
        Integer counter  = studentMapper.addStudent(student);
        return counter > 0;
    }
}
