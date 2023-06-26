/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.munan.fitnesstrackerAPI.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author godwi
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "response", "status", "message", "date"
})
public class ApiResponse<T> {
    
    @JsonProperty("response")
    private String response;

    @JsonProperty("message")
    private String message;
    
    @JsonProperty("status")
    private int status;
    
    @JsonProperty("data")
    private T data;


    public ApiResponse() {
    }

    public ApiResponse(int status, String response, String message) {
        this.status = status;
        this.response = response;
        this.message = message;
    }

    public ApiResponse(int status, String response, T data) {
        this.status = status;
        this.response = response;
        this.data = data;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
