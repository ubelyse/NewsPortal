package dao;

import models.Department_news;
import models.News;
import models.Users;
import org.junit.After;
import org.sql2o.Sql2o;
import org.junit.*;
import org.sql2o.Connection;
import models.Departments;

import java.util.Arrays;

import static org.junit.Assert.*;

public class sql2oDepartmentsTest {
    private static sql2oDepartments sql2oDepartments;
    private static sql2oUsers sql2oUsersDao;
    private static sql2oNews sql2oNewsDao;
    private static Connection conn;

    @Before
    public void setUp() {

        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "belyse", "belyse");

        sql2oDepartments=new sql2oDepartments(sql2o);
        sql2oUsersDao=new sql2oUsers(sql2o);
        sql2oNewsDao=new sql2oNews(sql2o);
        System.out.println("connected to database");
        conn=sql2o.open();

    }

    @After
    public void tearDown() {
        sql2oDepartments.clearAllDept();
        sql2oUsersDao.clearAllUsers();
        sql2oNewsDao.clearAllNews();
        System.out.println("clearing database");
    }

    @AfterClass
    public static void shutDown() {
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void AddedDepartment() {
        Departments department=setDepartment();
        int originalId=department.getId();
        sql2oDepartments.addDept(department);
        assertNotEquals(originalId,department.getId());
    }



    @Test
    public void addUserIntoDept() {
        Departments department=setDepartment();
        sql2oDepartments.addDept(department);
        Users user=setUser();
        Users otherUser= new Users("belyse","developer","Coding");
        sql2oUsersDao.add(user);
        sql2oUsersDao.add(otherUser);
        sql2oDepartments.addUserIntoDept(user,department);
        sql2oDepartments.addUserIntoDept(otherUser,department);
        //assertEquals(2,sql2oDepartments.getAllUsersInDepartment(department.getId()).size());
        assertEquals(2,sql2oDepartments.findById(department.getId()).getSize());
    }

    @Test
    public void getAllDept() {
        Departments department=setDepartment();
        Departments otherDepartment=new Departments("hr","recruiting");
        sql2oDepartments.addDept(department);
        sql2oDepartments.addDept(otherDepartment);
        assertEquals(department,sql2oDepartments.getAllDept().get(0));
        assertEquals(otherDepartment,sql2oDepartments.getAllDept().get(1));
    }

    @Test
    public void DepartmentFindById() {
        Departments department=setDepartment();
        Departments otherDepartment=new Departments("hr","recruiting");
        sql2oDepartments.addDept(department);
        sql2oDepartments.addDept(otherDepartment);
        assertEquals(department,sql2oDepartments.findById(department.getId()));
        assertEquals(otherDepartment,sql2oDepartments.findById(otherDepartment.getId()));

    }

    @Test
    public void getAllUsersIntoDepartment() {
        Departments department=setDepartment();
        sql2oDepartments.addDept(department);
        Users user=setUser();
        Users otherUser= new Users("belyse","developer","coding");
        sql2oUsersDao.add(user);
        sql2oUsersDao.add(otherUser);
        sql2oDepartments.addUserIntoDept(user,department);
        sql2oDepartments.addUserIntoDept(otherUser,department);
        //assertEquals(2,sql2oDepartments.getAllUsersInDepartment(department.getId()).size());
        //assertEquals(2,sql2oDepartments.findById(department.getId()).getSize());
    }
    @Test
    public void getDepartmentNews() {
        Users users=setUser();
        sql2oUsersDao.add(users);
        Departments departments=setDepartment();
        sql2oDepartments.addDept(departments);
        Department_news department_news =new Department_news("hr","recruiting",departments.getId()
                ,users.getId());
        sql2oNewsDao.addDepartmentNews(department_news);
        News news=new News("sales","Marketing",users.getId());
        sql2oNewsDao.addNews(news);

        //assertEquals(department_news.getTitle(),sql2oDepartments.getDepartmentNews(department_news.getId()).get(0).getTitle());
    }

    //helper
    private Departments setDepartment() {
        return new Departments("sale","marketing");
    }
    private Users setUser() {
        return new Users("belyse","hr","recruiting");
    }

}