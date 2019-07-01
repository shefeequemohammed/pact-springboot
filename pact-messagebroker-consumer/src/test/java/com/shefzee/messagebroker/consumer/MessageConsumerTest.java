package com.shefzee.messagebroker.consumer;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.MessagePactProviderRule;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.model.v3.messaging.MessagePact;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MessageConsumerTest
{
    @Rule
    public MessagePactProviderRule mockProvider = new MessagePactProviderRule(this);

    @Pact(provider = "studentMessageProducer", consumer = "studentMessageConsumer")
    public MessagePact createPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
            .stringType("id", "1")
            .stringType("firstName", "John")
                .stringType("lastName", "Wick")
                .numberType("age", 21);

        return builder
          .expectsToReceive("a student data")
          .withContent(body)
          .toPact();
    }

    @Test
    @PactVerification
    public void testMessage() throws Exception {
        assertThat(new MessageConsumer().processMessage(mockProvider.getMessage()), is(true));
    }
}
