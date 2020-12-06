package dao;

import org.junit.After;
import org.junit.Before;
import org.sql2o.Sql2o;
import org.junit.*;
import org.sql2o.Connection;
import models.Departments;
import models.Employees;

import java.util.Arrays;

import static org.junit.Assert.*;

public class sql2oDepartmentsTest {
    private static Connection conn;
    private  static sql2oDepartments departmentsDao;
    private static sql2oEmployees employeesDao;
    private static sql2oNews newsDao;

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "belyse", "belyse");
        employeesDao = new sql2oEmployees(sql2o);
        departmentsDao = new sql2oDepartments(sql2o);

        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("database clear");
        employeesDao.clearAll();
        departmentsDao.clearAll();

    }
    //run once after all tests in this file completed
    @AfterClass
    public static void shutDown() throws Exception{ //changed to static
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addDepartments() throws Exception {
        Departments departments = setDepartments();
        assertEquals(0, departments.getId());
    }

    @Test
    public void getAll() throws Exception {
        Departments departments = setDepartments();
        departmentsDao.add(departments);
        Departments department2 = setDepartments();
        departmentsDao.add(department2);
        assertTrue(departmentsDao.getAllDepartments().contains(departments));
        assertTrue(departmentsDao.getAllDepartments().contains(department2));
    }

    @Test
    public void findById() {
        Departments departments = setDepartments();
        departmentsDao.add(departments);
        Departments department2 = departmentsDao.findById(departments.getId());
        assertEquals(departments,department2);
    }

    @Test
    public void addDepartment() {
        Departments departments = setDepartments();
        departmentsDao.add(departments);
        int departId = departments.getId();
        assertEquals(departId,departments.getId());
    }

    @Test
    public void deletingDepartmentsAlsoUpdatesJoinTable() throws Exception {
        Employees employees = setEmployees();
        employeesDao.add(employees);

        Departments departments = setDepartments();
        departmentsDao.add(departments);

        Departments departments2 = setDepartments2();
        departmentsDao.add(departments2);

        departmentsDao.addDeptToEmployees(departments,employees);
        departmentsDao.addDeptToEmployees(departments2,employees);

        departmentsDao.deleteById(departments.getId());
        assertEquals(0, departmentsDao.getAllEmployeesIntoDepartment(departments.getId()).size());
    }

    @Test
    public void  addDptToEmployees() {
        Employees employees = new Employees("belyse", "assistant", "writing","secretary");
        employeesDao.add(employees);
        Employees employees2 = new Employees("belyse", "assistant", "writing","secretary");
        employeesDao.add(employees2);

        Departments departments = setDepartments();
        departmentsDao.add(departments);
        departmentsDao.addDeptToEmployees(departments,employees);
        departmentsDao.addDeptToEmployees(departments,employees2);
        Employees[] addDeptToEmployee = {employees,employees2};
        assertEquals(Arrays.asList(addDeptToEmployee),departmentsDao.getAllEmployeesIntoDepartment(departments.getId()));
    }
    @Test
    public void clearAll() {
        Departments departments = setDepartments();
        departmentsDao.add(departments);
        departmentsDao.clearAll();
        assertEquals(0,departmentsDao.getAllDepartments().size());
    }

    public Departments setDepartments(){
        return new Departments("hr", "recruiting", 20);
    }
    public Employees setEmployees(){
        return new Employees("belyse", "assistant", "writing","secretary");
    }
    public Departments setDepartments2(){ return new Departments("buy", "cook", 10);}

}