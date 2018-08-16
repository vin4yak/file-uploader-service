package com.mercedes.fileuploader.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    public EmployeeResponse(Integer httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private Integer httpStatus;

    private String message;

    private List<Employee> resources;

}
