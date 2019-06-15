package com.shefzee.consumer1;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactSpecVersion;
import au.com.dius.pact.model.RequestResponsePact;
import com.shefzee.consumer1.entity.Student;
import com.shefzee.consumer1.helper.RandomPortRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class StudentServiceConsumerContracttest {

    @ClassRule
    public static RandomPortRule randomPort = new RandomPortRule();

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("StudentServiceProvider", "localhost",randomPort.getPort(), this);
    private RestTemplate restTemplate=new RestTemplate();

    @Pact(consumer = "StudentServiceConsumer1")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        DslPart studentData = new PactDslJsonBody()
                .stringType("id", "1")
                .stringType("firstName", "John")
                .stringType("lastName","Smith")
                .integerType("age", 18)
                .asBody();

        return builder
                .given("Student 1 exist")
                .uponReceiving("A request for Student with Id 1")
                .path("/student/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(studentData).toPact();
    }

    @Test
    @PactVerification(fragment = "createPact")
    public void doTest() {
        System.setProperty("pact.rootDir","../pacts-poc");  // Change output dir for generated pact-files

        //Student student=new Student("1","John","Smith",23);
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
       // HttpEntity<Object> request=new HttpEntity<Object>(student, headers);
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity(mockProvider.getUrl()+"/student/1", Student.class);
        Student student = responseEntity.getBody();
        assertEquals("1",student.getId());
        assertEquals("John",student.getFirstName());
        assertEquals("Smith",student.getLastName());
        assertEquals(18,student.getAge());
    }
}
