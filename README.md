# PACT - Springboot

## pact-student-service-provider

This project exposes two API's
1) "/student/{id}" which will give a response in the following Json format:-

                {
                    "id": "1",
                    "firstName": "Steve",
                    "lastName": "Waugh",
                    "age": 21
                 }

Response is hard coded as follows:=

        if(id.equals("1"))
             return new Student(id, "Steve", "Waugh", 21);
        else if(id.equals("2"))
             return  new Student(id, "Brian", "Lara", 22);
        else
             return  new Student(id, "Rahul", "Dravid", 23);

2) "/department/{id}" which will give a response as follows:-

             {
                "id": "1",
                "name": "Dep 1",
                "students": [
                        {
                          "id": "1",
                          "firstName": "John",
                          "lastName": "Smith",
                          "age": 23
                         },
                         {
                          "id": "2",
                          "firstName": "Adam",
                          "lastName": "Zamba",
                          "age": 21
                         }
                            ]
               }
The response has a student array in it.

## pact-student-consumer1

#### StudentServiceConsumerContracttest

This function has two states for the student response API

1. State = Check for random Student id. This will check for the datatype of the field returned. It will not look for the exact value.

                
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
                 

2. State = "Check for Specific Student id". This state checks for the data type and the exact value returned. Note that method chaining is used here to interact with the API two times with different input.

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
                
   #### DepartmentServiceConsumerContracttest
   
   1) There is only one state in this function for the department response API. Note that this expects atleast one student array record. Note the use of minArrayLike.
   
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
                .given("Department 1 exist")
                .uponReceiving("A request for DepartmentResponse Object with Id 1")
                .path("/department/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(actualPactDsl).toPact();
            }

Once both the tests are run, the pact file will be generated. By default, the pact file is created in target/pacts. But here we have overridden the location in the test as below:-

                  System.setProperty("pact.rootDir","../pacts-poc");
                  
 As per my observation, this can be done only or upto version 3.5.10 of pact-jvm-consumer-junit_2.12. For higher versions, pacts will be generated in target/pacts folder only.
 
 ## pact-contract-verifyer
 
 #### Consumer1StudentServiceProviderVerifierTest
 
 This test will verify the States present in the Pact file with the provider instance.
 
          @State("Check for Specific Student id") // Method will be run before testing interactions that require "with-data" state
          public void checkForRandomStudent() {
            System.out.println("Check for Specific Student Contract" );
          }

          @State("Check for random Student id") // Method will be run before testing interactions that require "with-data" state
           public void checkForSpecificStudent() {
           System.out.println("Check for Student Contract" );
          } 

         @State("Department Response contract check") // Method will be run before testing interactions that require "with-data" state
         public void departmentTest() {
         System.out.println("Department Response contract check");
          }
 
 
