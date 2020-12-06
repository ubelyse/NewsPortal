package models;

public class News {

    private int id;
    private String news_content;
    private int dept_id;
    private String news_name;

    public News(int dept_id,String news_name,String news_content) {
        this.news_content = news_content;
        this.dept_id = dept_id;
        this.news_name = news_name;
    }

    public int getNews_id() {
        return id;
    }

    public void setNews_id(int news_id) {
        this.id = news_id;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }
}
