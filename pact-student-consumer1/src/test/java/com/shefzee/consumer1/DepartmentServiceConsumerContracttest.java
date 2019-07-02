package com.shefzee.consumer1;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.shefzee.consumer1.dto.DepartmentResponse;
import com.shefzee.consumer1.entity.Student;
import au.com.dius.pact.consumer.dsl.*;
import com.shefzee.consumer1.helper.RandomPortRule;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import io.pactfoundation.consumer.dsl.LambdaDslObject;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class DepartmentServiceConsumerContracttest {

    @ClassRule
    public static RandomPortRule randomPort = new RandomPortRule();

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("StudentServiceProvider", "localhost",randomPort.getPort(), this);
    private RestTemplate restTemplate=new RestTemplate();

    @Pact(consumer = "StudentServiceConsumer1")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap();
        headers.put("Content-Type", "application/json");
        final DslPart actualPactDsl = LambdaDsl.newJsonBody((bodyDsl) -> {
            bodyDsl
                    .stringType("id", "1")
                    .stringType("name","Dep 1")
                    .minArrayLike("students",1,(stud) ->{
                        stud
                            .stringType("id","1")
                               .stringType("firstName","John")
                               .stringType("lastName","Smith")
                               .numberType("age",21);
                    });

        }).build();


        return builder
                .given("Department Response contract check")
                .uponReceiving("A request for DepartmentResponse Object with Id 1")
                .path("/department/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(actualPactDsl).toPact();
    }

    @Test
    @PactVerification(fragment = "createPact")
    public void doTest() {
        System.setProperty("pact.rootDir","../pacts-poc");  // Change output dir for generated pact-files

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        ResponseEntity<DepartmentResponse> responseEntity = restTemplate.getForEntity(mockProvider.getUrl()+"/department/1", DepartmentResponse.class);

       DepartmentResponse departmentResponse = responseEntity.getBody();
       assertEquals("John", departmentResponse.getStudents().get(0).getFirstName());

    }
}
