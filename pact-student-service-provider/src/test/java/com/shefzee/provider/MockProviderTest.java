package com.shefzee.provider;


import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.shefzee.provider.dto.DepartmentResponse;
import com.shefzee.provider.entity.Student;
import com.shefzee.provider.service.DepartmentService;
import com.shefzee.provider.service.StudentService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRestPactRunner.class)
@Provider("StudentServiceProvider")
@PactFolder("../pacts-poc")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockProviderTest {

    @MockBean
    StudentService studentService;

    @MockBean
    DepartmentService departmentService;

    @State("Check for Specific Student id") // Method will be run before testing interactions that require "with-data" state
    public void checkForRandomStudent() {

        System.out.println("Check for Specific Student Contract" );
        when(studentService.getStudentById("1")).thenReturn(getStudentForId1());
        when(studentService.getStudentById("2")).thenReturn(getStudentForId2());
    }

    @State("Check for random Student id") // Method will be run before testing interactions that require "with-data" state
    public void checkForSpecificStudent() {

        System.out.println("Check for Student Contract" );
        when(studentService.getStudentById(any())).thenReturn(getStudent());
    }

    @State("Department Response contract check") // Method will be run before testing interactions that require "with-data" state
    public void departmentTest() {
        System.out.println("Department Response contract check");
        when(departmentService.getDepartmentById(any())).thenReturn(getDepartment());
    }

    @TestTarget // Annotation denotes Target that will be used for tests
    public final Target target = new SpringBootHttpTarget();

    private Student getStudent(){
        return new Student("3","Shef","Mohd", 33);
    }
    private Student getStudentForId1(){
        return new Student("1","Steve","Waugh", 21);
    }
    private Student getStudentForId2(){
        return new Student("2","Brian","Lara", 22);
    }

    private DepartmentResponse getDepartment(){
        Student student = new Student("1","Shef","Mohd", 33);
        DepartmentResponse departmentResponse = new DepartmentResponse();
        departmentResponse.setId("1");
        departmentResponse.setName("Dep name");
        departmentResponse.setStudents(Arrays.asList(student));
        return departmentResponse;
    }
}
