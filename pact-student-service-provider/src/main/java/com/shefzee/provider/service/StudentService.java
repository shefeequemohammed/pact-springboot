package com.shefzee.provider.service;

import com.shefzee.provider.entity.Student;

public interface StudentService {

    Student save(Student student);

    Student getStudentById(String id);
}
