package dao;

import static org.junit.Assert.*;

import models.Department_news;
import models.Departments;
import models.News;
import models.Users;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


public class sql2oNewsTest {

    private static sql2oDepartments sql2oDepartments;
    private static sql2oUsers sql2oUsers;
    private static sql2oNews sql2oNews;
    private static Connection conn;

    @Before
    public void setUp() throws Exception {

        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "belyse", "belyse");


        sql2oDepartments=new sql2oDepartments(sql2o);
        sql2oUsers=new sql2oUsers(sql2o);
        sql2oNews=new sql2oNews(sql2o);
        System.out.println("connected");
        conn=sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        sql2oDepartments.clearAllDept();
        sql2oUsers.clearAllUsers();
        sql2oNews.clearAllNews();
        System.out.println("clearing");
    }

    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addNews() {
        Users users=setUsers();
        sql2oUsers.add(users);
        Departments departments=setDepartment();
        sql2oDepartments.addDept(departments);
        News news=new News("payments","paying salary",users.getId());
        sql2oNews.addNews(news);

//        assertEquals(users.getId(),sql2oNews.findById(news.getId()).getUid());
//        assertEquals(news.getDeptid(),sql2oNews.findById(news.getId()).getDeptid());
    }




    @Test
    public void addDepartmentNews() {
        Users users=setUsers();
        sql2oUsers.add(users);
        Departments departments=setDepartment();
        sql2oDepartments.addDept(departments);
        Department_news department_news =new Department_news("payments","paying salary",departments.getId()
                ,users.getId());
        sql2oNews.addDepartmentNews(department_news);
//        assertEquals(users.getId(),sql2oNews.findById(department_news.getId()).getUid());
//        assertEquals(department_news.getDeptid(),sql2oNews.findById(department_news.getId()).getDeptid());

    }

    @Test
    public void getAll() {
        Users users=setUsers();
        sql2oUsers.add(users);
        Departments departments=setDepartment();
        sql2oDepartments.addDept(departments);
        Department_news department_news =new Department_news("payments","paying salary",departments.getId()
                ,users.getId());
        sql2oNews.addDepartmentNews(department_news);
        News news=new News("payments","paying salary",users.getId());
        sql2oNews.addNews(news);
//        assertEquals(2,sql2oNews.getAll().size());
    }



    @Test
    public void findById() {
    }


    private Departments setDepartment() {
        return new Departments("sale","marketing");
    }
    private Users setUsers() {
        return new Users("belyse","hr","recruiting");
    }

}