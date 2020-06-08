package com.example.discovery.student.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.discovery.student.model.Student;

/**
 * @Author: fzx
 * @DateTime: 2020/6/7 11:13
 * @Description: TODO
 */

public interface StudentMapper extends BaseMapper<Student> {
    /**
     * 新增學生
     * @param student
     * @return
     */
    Integer addStudent(Student student);
}
