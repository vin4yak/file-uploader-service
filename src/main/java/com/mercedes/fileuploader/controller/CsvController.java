package com.mercedes.fileuploader.controller;

import com.mercedes.fileuploader.domain.Employee;
import com.mercedes.fileuploader.domain.EmployeeResponse;
import com.mercedes.fileuploader.service.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class CsvController {

    private final CsvService csvService;

    public CsvController(final CsvService csvService) {
        this.csvService = csvService;
    }

    @RequestMapping(value = "/csv/upload", method = RequestMethod.POST)
    public ResponseEntity<EmployeeResponse> uploadCsv(@RequestPart(value = "file") MultipartFile multipartFile) throws IOException {
        String message = csvService.saveCsv(multipartFile);

        return ResponseEntity.ok(new EmployeeResponse(200, message));
    }

    @RequestMapping(value = "/csv/fetch")
    public ResponseEntity<EmployeeResponse> findAllEmployees() {
        List<Employee> employeeList = csvService.fetchEmployeeDetails();

        return ResponseEntity.ok(new EmployeeResponse(200, "Successfully fetched all employee details!", employeeList));
    }

    @RequestMapping(value = "/csv/delete", method = RequestMethod.POST)
    public ResponseEntity<EmployeeResponse> deleteAllEmployees() {
        csvService.deleteAllEmployeeDetails();

        return ResponseEntity.ok(new EmployeeResponse(200, "Successfully deleted all employee details!"));
    }

}
