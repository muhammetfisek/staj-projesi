package com.fisek.ws.error;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ApiError {

    private int status;

    private String message;

    private String path;

    private long timestamp =new Date().getTime(); // timestamp değişkenini tanımla ve şu anki zamanı milisaniye cinsinden al

private Map<String , String> validationErrors=null;

    public Map<String, String> getValidationErrors() {
    return validationErrors;
}
public void setValidationErrors(Map<String, String> validationErrors) {
    this.validationErrors = validationErrors;
}
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status=status;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
