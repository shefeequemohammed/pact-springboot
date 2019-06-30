package com.shefzee.provider.controller;

import com.shefzee.provider.service.DepartmentService;
import com.shefzee.provider.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    StudentService studentService;
    @Autowired
    DepartmentService departmentService;

    @GetMapping("/student/{id}")
    public ResponseEntity getStudentById(@PathVariable String id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/department/{id}")
    public ResponseEntity getDepartmentId(@PathVariable String id){
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }
}
