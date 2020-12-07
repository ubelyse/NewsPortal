package dao;

import models.Departments;
import models.News;
import models.Users;

import java.util.List;

public interface DepartmentsDao {

    //create
    void addDept(Departments department);
    void addUserIntoDept(Users user, Departments department);
    //read

    List<Departments> getAllDept();
    Departments findById(int id);
    List<Users> getAllUsersInDepartment(int department_id);
    List<News> getDepartmentNews(int id);
    //update
    //delete
    void clearAllDept();
}
