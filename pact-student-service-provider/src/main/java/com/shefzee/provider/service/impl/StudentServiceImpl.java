package com.shefzee.provider.service.impl;

import com.shefzee.provider.entity.Student;
import com.shefzee.provider.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Override
    public Student save(Student student) {
        return student;
    }

    @Override
    public Student getStudentById(String id) {
        return new Student(id, "John", "Smith", 21);
    }
}
