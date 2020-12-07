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

        //read all

        get("/departments", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(departmentsDao.getAllDepartments());//send it back to be displayed
        });
        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int dpt_id = Integer.parseInt(req.params("id"));

            Departments departmentsToFind = departmentsDao.findById(dpt_id);
            if (departmentsToFind == null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            res.type("application/json");
            return gson.toJson(departmentsDao.findById(dpt_id));
        });

        //news : dpt

        //Add news 2 department
        post("/departments/:id/news/new","application/json",(request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            News news = gson.fromJson(request.body(),News.class);
            news.setNews_id(id);
            newsDao.add(news);
            response.type("application/json");
            response.status(201);
            return gson.toJson(news);
        });
        //access news for a certain department
        get("/departments/:id/dptNews", "application/json", (request, response) -> {
            int id = Integer.parseInt(request.params("id"));
            return gson.toJson(newsDao.getAllNews());
        });
        post("/departments/:dpt_id/employees/new", "application/json", (req, res) -> {
            int dpt_id = Integer.parseInt(req.params("dpt_id"));
            Employees employees = gson.fromJson(req.body(), Employees.class);
            employees.setId(dpt_id);
            employeesDao.add(employees);
            res.status(201);
            res.type("application/json");
            return gson.toJson(employees);
        });

        //Add an employee
        post("/employees/new", "application/json", (request, response) -> {
            Employees employees = gson.fromJson(request.body(), Employees.class);
            employeesDao.add(employees);
            response.type("application/json");
            response.status(201);
            return gson.toJson(employees);
        });
        //access all employees
        get("/employees", "application/json", (request, response) -> gson.toJson(employeesDao.getAllEmployees()));
        //Assign a department to an employee
        post("/employees/emp_id/departments/:dpt_id","application/json",(request, response) -> {
            int empid = Integer.parseInt(request.params("emp_id"));
            int dptid = Integer.parseInt(request.params("dpt_id"));
            Employees empFound = employeesDao.findById(empid);
            Departments dptFound = departmentsDao.findById(dptid);

            if (dptFound != null && empFound!= null){
                departmentsDao.addDeptToEmployees(dptFound,empFound);
                response.type("application/json");
                response.status(201);
                return gson.toJson(String.format("Employees '%s' and Department '%s' successfully created",empFound.getEmp_name(), dptFound.getDept_name()));
            }
            else {
                throw new ApiExceptions(404, String.format("Employee or Department not found"));
            }
        });

        get("/employees/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int dpt_id = Integer.parseInt(req.params("id"));

            Employees employeesToFind = employeesDao.findById(dpt_id);
            if (employeesToFind == null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            res.type("application/json");
            return gson.toJson(employeesDao.findById(dpt_id));
        });

        get("/employees/:emp_id/departments","application/json",(request, response) -> {
            int empid = Integer.parseInt(request.params("emp_id"));
            Employees employeesTofind = employeesDao.findById(empid);

            if (employeesTofind == null){
                throw new Exception("Employee with that id does not exist");
            }else if(employeesDao.getAllDeptIntoToEmployees(empid).size() == 0){
                return "{\"message\":\"Sorry! Employee not associated with any of the departments\"}";
            }else {
                return gson.toJson(employeesDao.getAllDeptIntoToEmployees(empid));
            }
        });


        //Add news
        post("/news/new","application/json",(request, response) -> {
            News news = gson.fromJson(request.body(),News.class);
            newsDao.add(news);
            response.type("application/json");
            response.status(201);
            return gson.toJson(news);
        });

        get("/news", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            return gson.toJson(newsDao.getAllNews());//send it back to be displayed
        });
        get("/news/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int dpt_id = Integer.parseInt(req.params("id"));

            News newsToFind = newsDao.findByiId(dpt_id);
            if (newsToFind == null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            res.type("application/json");
            return gson.toJson(newsDao.findByiId(dpt_id));
        });
        exception(ApiExceptions.class, (exc, req, res) -> {
            ApiExceptions err = (ApiExceptions) exc;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json"); //after does not run in case of an exception.
            res.status(err.getStatusCode()); //set the status
            res.body(gson.toJson(jsonMap));  //set the output.
        });
        //FILTERS
        after((req, res) ->{
            res.type("application/json");
        });
    }
}
