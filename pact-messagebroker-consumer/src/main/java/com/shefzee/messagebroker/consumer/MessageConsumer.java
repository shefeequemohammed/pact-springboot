package com.shefzee.messagebroker.consumer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.nio.charset.Charset;

public class MessageConsumer
{
  public boolean processMessage(byte[] message) {
    try {
      //Read the message and extract values
      JsonElement element = new JsonParser().parse(new String(message, Charset.forName("UTF-8")));
      JsonObject jsonObject = element.getAsJsonObject();
      String id = jsonObject.getAsJsonPrimitive("id").getAsString();
      String firstName = jsonObject.getAsJsonPrimitive("firstName").getAsString();
      String lastName = jsonObject.getAsJsonPrimitive("lastName").getAsString();
      long age = jsonObject.getAsJsonPrimitive("age").getAsLong();
      return true;
    } catch (JsonSyntaxException ex) {
      return false;
    }
  }
}
