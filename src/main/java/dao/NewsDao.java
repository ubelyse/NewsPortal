package dao;

import models.Department_news;
import models.News;

import java.util.List;

public interface NewsDao {

    //create

    void addNews(News news);
    void addDepartmentNews(Department_news department_news);

    //read
    List<News> getAll();

    News findById(int id);

    //update

    //delete

    void clearAllNews();
}
