package com.mindex.challenge.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

public class Compensation {
    @DBRef
    private Employee employee;
    private Float salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() { return employee; }

    public void setEmployee(Employee employee) { this.employee = employee; }

    public Float getSalary() { return salary; }

    public void setSalary(Float salary) { this.salary = salary; }

    public Date getEffectiveDate() { return effectiveDate; }

    public void setEffectiveDate(Date date) { this.effectiveDate = date; }
}
