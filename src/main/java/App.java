import com.google.gson.Gson;
import dao.sql2oNews;
import dao.sql2oEmployees;
import dao.sql2oDepartments;
import models.Departments;
import Exceptions.ApiExceptions;
import models.Employees;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        sql2oDepartments departmentsDao;
        sql2oNews newsDao;
        sql2oEmployees employeesDao;
        Connection conn;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/newsportal";
        Sql2o sql2o = new Sql2o(connectionString, "belyse", "belyse");

        departmentsDao = new sql2oDepartments(sql2o);
        employeesDao = new sql2oEmployees(sql2o);
        newsDao = new sql2oNews(sql2o);
        conn = sql2o.open();

        //create
        post("/departments/new", "application/json", (req, res) -> {
            Departments departments = gson.fromJson(req.body(), Departments.class);
            departmentsDao.add(departments);
            res.status(201);
            res.type("application/json");
            return gson.toJson(departmentsDao.getAllDepartments());
        });
    }
}
