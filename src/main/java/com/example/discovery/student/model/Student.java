package com.example.discovery.student.model;


import lombok.*;

import java.util.Date;

/**
 * @Author: fzx
 * @DateTime: 2020/6/7 11:19
 * @Description: TODO
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Data
public class Student {
    /**
     *  主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     *
     */
    private Date deletedAt;

}
