package com.shefzee.provider;


import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;


//@RunWith(PactRunner.class) // Say JUnit to run tests with custom Runner
@RunWith(SpringRestPactRunner.class)
@Provider("StudentServiceProvider") // Set up name of tested provider
@PactFolder("../pacts-poc") // Point where to find pacts (See also section Pacts source in documentation)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class Consumer1StudentServiceProviderVerifierTest {


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

    @TestTarget // Annotation denotes Target that will be used for tests
    public final Target target = new HttpTarget(8085); // Out-of-the-box implementation of Target (for more information take a look at Test Target section)

}
