package com.mercedes.fileuploader.service;

import com.mercedes.fileuploader.domain.Employee;
import com.mercedes.fileuploader.repository.EmployeeRepository;
import com.mercedes.fileuploader.util.FileUtil;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Service
public class CsvService {

    private final EmployeeRepository employeeRepository;

    public CsvService(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String saveCsv(MultipartFile multipartFile) throws IOException {
        final File file = FileUtil.convertFile(multipartFile);

        final Reader reader = new FileReader(file);

        final CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                .withType(Employee.class)
                .withIgnoreLeadingWhiteSpace(true).build();

        final List<Employee> employeeList = csvToBean.parse();

        employeeList.forEach(employee -> employeeRepository.save(employee));

        return "Saved contents of " + file.getName() + " successfully!";
    }

    public List<Employee> fetchEmployeeDetails() {
        return employeeRepository.findAll();
    }

    public void deleteAllEmployeeDetails() {
        employeeRepository.deleteAll();
    }

}
