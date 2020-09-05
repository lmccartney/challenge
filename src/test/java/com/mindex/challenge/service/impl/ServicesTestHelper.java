package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;

import static org.junit.Assert.assertEquals;

public class ServicesTestHelper {

    public static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
