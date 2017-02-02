package com.vo2.javatest.domain.dto;

/**
 * Created by VO2 on 16/01/2017.
 * SampleDto exposed to front ends (REST or WEB)
 */
public class SampleDto {

    private Long id;
    private String message;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SampleDto{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
