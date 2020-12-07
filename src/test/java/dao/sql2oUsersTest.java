package dao;

import models.Departments;
import models.Users;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class sql2oUsersTest {

    private static sql2oDepartments sql2oDepartments;
    private static sql2oUsers sql2oUsers;
    private static Connection conn;

    @Before
    public void setUp() throws Exception {

        //uncomment the two lines below to run locally and change to your  credentials
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "belyse", "belyse");

        sql2oDepartments=new sql2oDepartments(sql2o);
        sql2oUsers=new sql2oUsers(sql2o);
        System.out.println("connected");
        conn=sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
        sql2oDepartments.clearAllDept();
        sql2oUsers.clearAllUsers();
        System.out.println("clearing");
    }
    @AfterClass
    public static void shutDown() throws Exception{
        conn.close();
        System.out.println("connection closed");
    }


    @Test
    public void addingUser() {
        Users user = setNewUser();
        int uid= user.getId();
        sql2oUsers.add(user);
        assertNotEquals(uid,user.getId());
    }

    @Test
    public void addedUser() {
        Users user = setNewUser();
        sql2oUsers.add(user);
        assertEquals(user.getName(),sql2oUsers.findById(user.getId()).getName());
    }

    @Test
    public void UserInstances() {

        Users users=setNewUser();
        Users otherUser= new Users("belyse","hr","recruiting");
        sql2oUsers.add(users);
        sql2oUsers.add(otherUser);
        assertEquals(users.getName(),sql2oUsers.getAll().get(0).getName());
        assertEquals(otherUser.getName(),sql2oUsers.getAll().get(1).getName());
    }
    @Test
    public void getDepartmentsUserIsIn() {
        Departments department=setDepartment();
        Departments otherDepartment=new Departments("sale","marketing");
        sql2oDepartments.addDept(department);
        sql2oDepartments.addDept(otherDepartment);
        Users user=setNewUser();
        Users otherUser= new Users("belyse","hr","recruiting");
        sql2oUsers.add(user);
        sql2oUsers.add(otherUser);
        sql2oDepartments.addUserIntoDept(user,department);
        sql2oDepartments.addUserIntoDept(otherUser,department);
        sql2oDepartments.addUserIntoDept(user,otherDepartment);
        assertEquals(2,sql2oUsers.getAllUserDepartments(user.getId()).size());
        assertEquals(1,sql2oUsers.getAllUserDepartments(otherUser.getId()).size());
    }

    //helper
    private Users setNewUser() {
        return new Users("belyse","hr","recruiting");
    }
    private Departments setDepartment() {
        return new Departments("hr","marketing");
    }
}