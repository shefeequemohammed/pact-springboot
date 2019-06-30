package com.shefzee.provider.service.impl;

import com.shefzee.provider.dto.DepartmentResponse;
import com.shefzee.provider.entity.Student;
import com.shefzee.provider.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  @Override
   public  DepartmentResponse getDepartmentById(String id){

      Student student1 = new Student();
      student1.setId("1");
      student1.setAge(23);
      student1.setFirstName("John");
      student1.setLastName("Smith");

      Student student2 = new Student();
      student2.setId("2");
      student2.setAge(21);
      student2.setFirstName("Adam");
      student2.setLastName("Zamba");

      DepartmentResponse department = new DepartmentResponse();
      department.setId(id);
      department.setName("Dep 1");
      department.setStudents(Arrays.asList(student1,student2 ));

      return department;
    }
}
