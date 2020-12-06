package dao;

import static org.junit.Assert.*;
import models.News;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


public class sql2oNewsTest {

    private static Connection conn;
    private static sql2oNews newsDao;
    private static sql2oDepartments departmentsDao;

    public News setupNews(){
        return new News(1,"snitch","guess who's broke?");
    }

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/newsportal_test";
        Sql2o sql2o = new Sql2o(connectionString, "belyse", "belyse");
        newsDao = new sql2oNews(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
    //Add news
    @Test
    public void addNews() throws Exception{
        News testNews = setupNews();
        newsDao.add(testNews);
        int newId = testNews.getNews_id();
        assertEquals(newId,testNews.getNews_id());
    }
}