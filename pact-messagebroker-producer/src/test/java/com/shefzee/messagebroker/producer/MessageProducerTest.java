package com.shefzee.messagebroker.producer;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.AmqpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.runner.RunWith;

@RunWith(PactRunner.class)
@Provider("studentMessageProducer")
@PactFolder("../pact-messagebroker-consumer/target/pacts")
public class MessageProducerTest {
    @TestTarget
    public final Target target = new AmqpTarget();

    @PactVerifyProvider("a student data")
    public String generateTestMessage() throws JsonProcessingException {

        Student student = new Student("1","Shef","Mohd",23);

        return new MessageProducer().generateStudentMessage(student);
    }
}
