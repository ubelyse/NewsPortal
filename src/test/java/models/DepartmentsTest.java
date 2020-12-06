package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DepartmentsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    //get DepartmentName
    @Test
    public void getDepartmentName() throws Exception {
        Departments departments = setDepartments();
        assertEquals("hr", departments.getDpt_name());
    }

    //get Department Description
    @Test
    public void getDepartmentDescription() throws Exception {
        Departments departments = setDepartments();
        assertEquals("recruiting", departments.getDpt_description());
    }

    @Test
    public void getEmployeesNumbers() throws Exception {
        Departments departments = setDepartments();
        assertEquals(20, departments.getDpt_empNo());
    }
    @Test
    public void setEmployeesNumber(){
        Departments departments = setDepartments();
        departments.setDpt_empNo(20);
        assertNotEquals(10, departments.getDpt_empNo());
    }

    @Test
    public void setDepartmentName(){
        Departments departments = setDepartments();
        departments.setDpt_name("HR");
        assertNotEquals("office", departments.getDpt_name());

    }
    @Test
    public void setDepartmentsDescription(){
        Departments departments = setDepartments();
        departments.setDpt_description("recruiting");
        assertNotEquals("driver", departments.getDpt_description());
    }

    public Departments setDepartments() {
        return new Departments("hr", "recruiting", 20);
    }
}