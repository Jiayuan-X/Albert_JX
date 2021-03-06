/*
     Copyright 2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.

     Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
     except in compliance with the License. A copy of the License is located at

         http://aws.amazon.com/apache2.0/

     or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
     the specific language governing permissions and limitations under the License.
*/

package com.github.sixers.albert.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.impl.IntentRequestHandler;
import com.amazon.ask.model.Intent;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class OrderFoodHandler implements IntentRequestHandler {

    @Override
    public boolean canHandle(HandlerInput handlerInput, IntentRequest intentRequest) {
        return (handlerInput.matches(intentName("OrderFood")));

    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput, IntentRequest intentRequest)  {
//
        Intent intent = intentRequest.getIntent();
        //Food names
        Slot foodone = intent.getSlots().get("Foodone");
        Slot foodtwo = intent.getSlots().get("Foodtwo");
        Slot foodthree = intent.getSlots().get("Foodthree");
        
        //Number
        Slot numone = intent.getSlots().get("Numberone");
        Slot numtwo = intent.getSlots().get("Numbertwo");
        Slot numthree = intent.getSlots().get("Numberthree");

        //defining default value for strings
        String foodtwoName = "";
        String foodthreeName = "";

        String numoneValue = "";
        String numtwoValue = "";
        String numthreeValue = "";

        //Checking slot values and assign the corresponding string value

        //ordered food name
        String foodoneName = foodone.getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();
        if (foodtwo.getValue() != null){
            foodtwoName = foodtwo.getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();
        }
        if (foodthree.getValue() != null){
            foodthreeName = foodthree.getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();
        }

        //ordered number value of food
        if (numone.getValue() != null){
            numoneValue = numone.getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();
        }
        if (numtwo.getValue() != null){
            numtwoValue = numtwo.getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();
        }
        if (numthree.getValue() != null){
            numthreeValue = numthree.getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();
        }



//        String foodNumber = number.getResolutions().getResolutionsPerAuthority().get(0).getValues().get(0).getValue().getName();

//        String speechText = "You order " + foodNumber +" " + foodName;

//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("http://albert.visgean.me/api/orders/");
//        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//        nameValuePairs.add(new BasicNameValuePair("Number", foodNumber));
//        nameValuePairs.add(new BasicNameValuePair("Name", foodName));
//        try {
//            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
//            CloseableHttpResponse response = httpClient.execute(httpPost);
//            httpClient.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        //String foodName = food.getValue();
        //String foodNumber = number.getValue();

        //Construct respond text
        String speechText = "You ordered ";
        if (numoneValue != ""){
            speechText = speechText+ numoneValue +" ";
        }
        speechText = speechText +foodoneName + " ";
        if (numtwoValue != ""){
            speechText = speechText +numtwoValue + " ";
        }
        if (foodtwoName != ""){
            speechText = speechText + foodtwoName +" ";
        }
        if (numthreeValue != ""){
            speechText = speechText +numthreeValue + " ";
        }
        if (foodthreeName != ""){
            speechText = speechText +foodthreeName;
        }



        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .build();
    }

}
