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
        
        if(id.equals("1"))
             return new Student(id, "Steve", "Waugh", 21);
        else if(id.equals("2"))
             return  new Student(id, "Brian", "Lara", 22);
        else
             return  new Student(id, "Rahul", "Dravid", 23);
    }
}
