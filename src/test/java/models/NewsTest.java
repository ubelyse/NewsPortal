package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void NewsInstantiatesCorrectlyTrue() throws Exception{
        News news = setNews();
        assertTrue(news instanceof News);
    }

    @Test
    public void getNewsName() throws Exception {
        News news = setNews();
        assertEquals("home", news.getNews_name());
    }
    @Test
    public void setNewsId() throws Exception{
        News news = setNews();
        news.setNews_id(2);
        assertNotEquals(3,news.getNews_id());
    }
    @Test
    public void NewsInstantiatesCorrectly() throws Exception{
        News news = setNews();
        assertEquals("home",news.getNews_name());
        assertEquals("children",news.getNews_content());
        assertEquals(2,news.getDept_id());
    }

    public News setNews() {
        return new News(2, "home", "children");
    }
}