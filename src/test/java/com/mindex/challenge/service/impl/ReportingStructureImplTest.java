package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureImplTest extends ServicesTestHelper {

    private String reportingStructureUrl;
    private String employeeIdUrl;

    @Autowired
    ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reporting-structure/{id}";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
    }

    @Test
    public void testRead(){
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();

        // Make sure we have a good employee
        assertNotNull(readEmployee);

        ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, readEmployee.getEmployeeId()).getBody();

        // Make sure we'v returned a proper ReportingStructure
        assertNotNull(reportingStructure);

        // Verify that the employee is equivalent to what is provided by the employee endpoint
        assertEmployeeEquivalence(readEmployee, reportingStructure.getEmployee());
        // Validate the number of reports given.
        assertEquals(reportingStructure.getNumberOfReports(), 4);
    }
}
