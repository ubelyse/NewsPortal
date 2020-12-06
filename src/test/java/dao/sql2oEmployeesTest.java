package dao;

import models.Employees;
import models.Departments;
import org.junit.Test;
import org.sql2o.Connection;
import org.junit.After;
import java.util.Arrays;
import org.junit.Before;
import org.sql2o.Sql2o;
import static org.junit.Assert.*;

public class sql2oEmployeesTest {

    private static sql2oEmployees employeesDao;
    private  static Connection conn;
    private static sql2oDepartments departmentsDao;

    @Before
    public void setUp() throws Exception {
        String connectingString= "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectingString,"belyse","belyse");
        employeesDao = new sql2oEmployees(sql2o);
        departmentsDao = new sql2oDepartments(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addAndSetId() {
        Employees employees = setEmployees();
        employeesDao.add(employees);
        int empId= employees.getId();
        assertEquals(employees.getId(),empId);
    }
    @Test
    public void getAllEmployees(){
        Employees employees = setEmployees();
        employeesDao.add(employees);
        Employees employees1 = setEmployees();
        employeesDao.add(employees1);
        assertTrue(employeesDao.getAllEmployees().contains(employees));
        assertTrue(employeesDao.getAllEmployees().contains(employees1));
    }
    @Test
    public void clearAll() {
        Employees employees = setEmployees();
        employeesDao.add(employees);
        employeesDao.clearAll();
        assertEquals(0,employeesDao.getAllEmployees().size());
    }
    @Test
    public void addEmployeeToDepartment() {
        Employees employees = setEmployees();
        employeesDao.add(employees);

        Departments departments = setDepartments();
        departmentsDao.add(departments);
        Departments department1 = setDepartments2();
        departmentsDao.add(department1);

        employeesDao.addEmpToDepartments(employees,departments);
        employeesDao.addEmpToDepartments(employees,department1);
        Departments[] EmpToDepartment = {departments,department1};
        assertEquals(Arrays.asList(EmpToDepartment),employeesDao.getAllDeptIntoToEmployees(employees.getId()));
    }


    @Test
    public void findById() {
        Employees employees = setEmployees();
        employeesDao.add(employees);
        Employees employee2 = employeesDao.findById(employees.getId());
        assertEquals(employee2,employees);
    }
    public Employees setEmployees(){
        return new Employees("belyse", "assistant", "writing","secretary");
    }
    public Departments setDepartments(){ return new Departments("hr", "recruiting", 20);}
    public Departments setDepartments2(){ return new Departments("buy", "cook", 10);}
}