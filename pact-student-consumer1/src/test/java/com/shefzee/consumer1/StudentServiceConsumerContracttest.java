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

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;


public class StudentServiceConsumerContracttest {

    //We have two states, one for Random student id and one for specific id and values

    @ClassRule
    public static RandomPortRule randomPort = new RandomPortRule();

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("StudentServiceProvider", "localhost",randomPort.getPort(), this);
    private RestTemplate restTemplate=new RestTemplate();

    @Pact(consumer = "StudentServiceConsumer1")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");

        DslPart studentDataForId1 = new PactDslJsonBody()
                .stringValue("id", "1")
                .stringValue("firstName", "Steve")
                .stringValue("lastName","Waugh")
                .numberValue("age", 21)
                .asBody();

        DslPart studentDataForId2 = new PactDslJsonBody()
                .stringValue("id", "2")
                .stringValue("firstName", "Brian")
                .stringValue("lastName","Lara")
                .numberValue("age", 22)
                .asBody();

        return builder
                .given("Check for Specific Student id")
                .uponReceiving("A request for Student with id 1")
                .path("/student/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(studentDataForId1)
                .uponReceiving("A request for Student with id 2")
                .path("/student/2")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(studentDataForId2).toPact();

    }
    @Pact(consumer = "StudentServiceConsumer1")
    public RequestResponsePact createPact2(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        DslPart studentData = new PactDslJsonBody()
                .stringType("id", "3")
                .stringType("firstName", "John")
                .stringType("lastName", "Smith")
                .integerType("age", 18)
                .asBody();

        return builder
                .given("Check for random Student id")
                .uponReceiving("A request for Student with random id")
                .path("/student/3")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(studentData).toPact();
    }

    @Test
    @PactVerification(fragment = "createPact")
    public void doTestForSpecificId() {
        System.setProperty("pact.rootDir","../pacts-poc");  // Change output dir for generated pact-files
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        ResponseEntity<Student> responseEntity1 = restTemplate.getForEntity(mockProvider.getUrl()+"/student/1", Student.class);
        Student student1 = responseEntity1.getBody();
        assertEquals("1",student1.getId());
        assertEquals("Steve",student1.getFirstName());
        assertEquals("Waugh",student1.getLastName());
        assertEquals(21,student1.getAge());

        ResponseEntity<Student> responseEntity2 = restTemplate.getForEntity(mockProvider.getUrl()+"/student/2", Student.class);
        Student student2 = responseEntity2.getBody();
        assertEquals("2",student2.getId());
        assertEquals("Brian",student2.getFirstName());
        assertEquals("Lara",student2.getLastName());
        assertEquals(22,student2.getAge());

    }
    @Test
    @PactVerification(fragment = "createPact2")
    public void doTestForRandomId() {
        System.setProperty("pact.rootDir", "../pacts-poc");  // Change output dir for generated pact-files
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<Student> responseEntity = restTemplate.getForEntity(mockProvider.getUrl() + "/student/3", Student.class);
        Student student = responseEntity.getBody();
        assertEquals("3", student.getId());
        assertEquals("John", student.getFirstName());
        assertEquals("Smith", student.getLastName());
        assertEquals(18, student.getAge());
    }
}
