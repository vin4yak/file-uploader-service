package com.mercedes.fileuploader.service;

import com.mercedes.fileuploader.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
public class CsvServiceTest {

    @InjectMocks
    private CsvService csvService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void fetchEmployeeDetails_shouldCallEmployeeRepository() {
        csvService.fetchEmployeeDetails();

        verify(employeeRepository).findAll();
    }

    @Test
    public void deleteAllEmployeeDetails_shouldCallEmployeeRepository() {
        csvService.deleteAllEmployeeDetails();

        verify(employeeRepository).deleteAll();
    }
}