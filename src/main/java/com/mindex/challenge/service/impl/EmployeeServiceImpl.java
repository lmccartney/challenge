package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Getting employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllReports(Employee employee) {
        LOG.debug("Getting all reports for employee [{}]", employee.getEmployeeId());

        LOG.debug("Getting direct reports for employee [{}]", employee.getEmployeeId());
        List<Employee> directReports = employee.getDirectReports();
        if (directReports == null) {
            LOG.debug("There are no reports for Employee [{}], returning empty List", employee.getEmployeeId());
            return Collections.emptyList();
        }
        List<Employee> allReports = new ArrayList<>(directReports);

        LOG.debug("Getting indirect reports for employee [{}]", employee.getEmployeeId());
        for (Employee directReport: directReports){
            allReports.addAll(this.getAllReports(directReport));
        }
        return allReports;
    }
}
