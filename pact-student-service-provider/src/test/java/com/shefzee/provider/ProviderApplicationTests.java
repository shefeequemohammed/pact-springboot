package com.shefzee.provider;

import com.shefzee.provider.dto.DepartmentResponse;
import com.shefzee.provider.entity.Department;
import com.shefzee.provider.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProviderApplicationTests {

    @Test
    public void doTest() {

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
        department.setId("1");
        department.setName("Dep 1");
        department.setStudents(Arrays.asList(student1,student2));

        //
    }

}
