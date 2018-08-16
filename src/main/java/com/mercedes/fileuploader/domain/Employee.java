package com.mercedes.fileuploader.domain;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "t_employee_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    private final UUID id = UUID.randomUUID();

    @CsvBindByName(column = "emp_id")
    @Column(name = "emp_id")
    private Long employeeId;

    @CsvBindByName(column = "name")
    @Column(name = "name")
    private String name;

    @CsvBindByName(column = "email")
    @Column(name = "email")
    private String email;

    @CsvBindByName(column = "department")
    @Column(name = "department")
    private String department;

}
