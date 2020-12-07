package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeesTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void EmployeesInstantiates() throws Exception{
        Employees employees = setEmployees();
        assertTrue(employees instanceof Employees);
    }

    @Test
    public void setEmployeesId()throws Exception{
        Employees employees = setEmployees();
        employees.setId(1);
        assertNotEquals(2,employees.getId());
    }

    @Test
    public void EmployeesInstantiatesCorrectly()throws Exception{
        Employees employees= setEmployees();
        assertEquals("belyse",employees.getEmp_name());
        assertEquals("assistant",employees.getEmp_details());
        assertEquals("secretary",employees.getEmp_position());
        assertEquals("writing",employees.getEmp_role());
    }

    public Employees setEmployees(){
        return new Employees("belyse", "assistant", "writing","secretary");
    }
}