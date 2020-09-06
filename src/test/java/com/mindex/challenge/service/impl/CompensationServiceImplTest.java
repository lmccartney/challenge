package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest extends ServicesTestHelper {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImplTest.class);
    @Autowired
    CompensationService compensationService;
    private String compensationUrl;
    private String compensationIdUrl;
    private String employeeIdUrl;
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static void assertCompensationEquivalence(Compensation actual, Compensation expected) {
        assertEquals(actual.getEmployee().getEmployeeId(), expected.getEmployee().getEmployeeId());
        assertEquals(actual.getSalary(), expected.getSalary(), 1);
        assertEquals(actual.getEffectiveDate(), expected.getEffectiveDate());
    }

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        compensationIdUrl = employeeIdUrl + "/compensation";
    }

    @Test
    public void testCreateRead() {
        // Getting employee
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        assertNotNull(readEmployee.getEmployeeId());

        // Defining compensation
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(readEmployee);
        testCompensation.setSalary((float) 100.1);
        testCompensation.setEffectiveDate(new Date(10000));

        // Call compensation post api
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        // assert created object looks correct
        assertNotNull(createdCompensation);
        assertNotNull(createdCompensation.getEmployee());
        assertEmployeeEquivalence(createdCompensation.getEmployee(), readEmployee);
        assertEquals(createdCompensation.getSalary(), testCompensation.getSalary(), 1);
        assertEquals(createdCompensation.getEffectiveDate(), testCompensation.getEffectiveDate());

        // Make get request
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();

        // Assert get looks like created
        assertNotNull(readCompensation);
        assertNotNull(readCompensation.getEmployee());
        assertCompensationEquivalence(readCompensation, createdCompensation);

    }

}
