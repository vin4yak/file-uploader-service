package com.mercedes.fileuploader.controller;

import com.mercedes.fileuploader.domain.Employee;
import com.mercedes.fileuploader.domain.EmployeeResponse;
import com.mercedes.fileuploader.service.CsvService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class CsvControllerTest {

    @InjectMocks
    private CsvController csvController;

    @Mock
    private CsvService csvService;

    @Test
    public void uploadCsv_shouldCallCsvService() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/test/resources/test.csv");
        MockMultipartFile file = new MockMultipartFile("file",
                "NameOfTheFile", "multipart/form-data", inputStream);

        csvController.uploadCsv(file);

        verify(csvService).saveCsv(file);
    }

    @Test
    public void uploadCsv_shouldReturnOkResponseWithSuccessfulMessageWhenCsvIsPassed() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/test/resources/test.csv");
        MockMultipartFile file = new MockMultipartFile("file",
                "NameOfTheFile", "multipart/form-data", inputStream);

        when(csvService.saveCsv(file)).thenReturn("Successful!");

        ResponseEntity<EmployeeResponse> responseEntity = csvController.uploadCsv(file);

        assertThat(responseEntity.getBody().getHttpStatus(), is(HttpStatus.OK.value()));
        assertThat(responseEntity.getBody().getMessage(), is("Successful!"));
    }

    @Test
    public void findAllEmployees_shouldCallCsvService() {
        csvController.findAllEmployees();

        verify(csvService).fetchEmployeeDetails();
    }

    @Test
    public void findAllEmployees_shouldReturnEmployeeDetails() {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee = new Employee();
        employee.setName("Vinayak");
        employee.setDepartment("DevOps");
        employee.setEmail("vinayak@test.com");
        employee.setEmployeeId(1L);
        employeeList.add(employee);

        when(csvService.fetchEmployeeDetails()).thenReturn(employeeList);

        ResponseEntity<EmployeeResponse> responseEntity = csvController.findAllEmployees();

        assertThat(responseEntity.getBody().getHttpStatus(), is(200));
        assertThat(responseEntity.getBody().getResources().size(), is(1));
        assertThat(responseEntity.getBody().getResources().get(0).getName(), is("Vinayak"));
    }

    @Test
    public void deleteAllEmployees_shouldCallDeleteCsvService() {
        csvController.deleteAllEmployees();

        verify(csvService).deleteAllEmployeeDetails();
    }
}