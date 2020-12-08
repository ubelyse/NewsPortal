import com.google.gson.Gson;
import dao.sql2oDepartments;
import dao.sql2oNews;
import dao.sql2oUsers;
import Exceptions.ApiExceptions;
import spark.ModelAndView;

import models.Departments;
import models.News;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import static spark.Spark.staticFileLocation;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {


        sql2oNews sql2oNewsDao;
        sql2oUsers sql2oUsersDao;
        sql2oDepartments sql2oDepartmentsDao;
        Connection conn;
        Gson gson = new Gson();

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        String connectionString = "jdbc:postgresql://localhost:5432/newsportal";
        Sql2o sql2o = new Sql2o(connectionString, "belyse", "belyse");

        sql2oDepartmentsDao=new sql2oDepartments(sql2o);
        sql2oNewsDao=new sql2oNews(sql2o);
        sql2oUsersDao=new sql2oUsers(sql2o);
        conn=sql2o.open();

        staticFileLocation("/public");

        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Departments> allDepartments = sql2oDepartmentsDao.getAllDept();
            model.put("departments", allDepartments);
            List<News> tasks = sql2oNewsDao.getAll();
            model.put("news", tasks);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        //read users,news,departments
        get("/users", "application/json", (request, response) -> {

            if(sql2oDepartmentsDao.getAllDept().size() > 0){
                return gson.toJson(sql2oUsersDao.getAll());
            }
            else {
                return "{\"message\":\"User not registered.\"}";
            }
        });

        get("/departments","application/json",(request, response) -> {
            if(sql2oDepartmentsDao.getAllDept().size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAllDept());
            }
            else {
                return "{\"message\":\"departments Not currently listed in the database.\"}";
            }
        });
        get("/news/general","application/json",(request, response) -> {
            if(sql2oNewsDao.getAll().size()>0){
                return gson.toJson(sql2oNewsDao.getAll());
            }
            else {
                return "{\"message\":\"No news available.\"}";
            }
        });
        get("/user/:id/departments","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oUsersDao.getAllUserDepartments(id).size()>0){
                return gson.toJson(sql2oUsersDao.getAllUserDepartments(id));
            }
            else {
                return "{\"message\":\"but user Not in the departments.\"}";
            }
        });
        get("/user/:id", "application/json", (request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oUsersDao.findById(id)==null){
                throw new ApiExceptions(404, String.format("User not available: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oUsersDao.findById(id));
            }
        });
        get("/department/:id/users","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentsDao.getAllUsersInDepartment(id).size()>0){
                return gson.toJson(sql2oDepartmentsDao.getAllUsersInDepartment(id));
            }
            else {
                return "{\"message\":\"Department not available:\"}";
            }
        });
        get("/department/:id","application/json",(request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            if(sql2oDepartmentsDao.findById(id)==null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            else {
                return gson.toJson(sql2oDepartmentsDao.findById(id));
            }
        });
        get("/news/department/:id","application/json",(request, response) -> {

            int id=Integer.parseInt(request.params("id"));
            Departments departments=sql2oDepartmentsDao.findById(id);
            if(departments==null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            if(sql2oDepartmentsDao.getDepartmentNews(id).size()>0){
                return gson.toJson(sql2oDepartmentsDao.getDepartmentNews(id));
            }
            else {
                return "{\"message\":\"News not available.\"}";
            }
        });



        //create user,news,department

        post("/users/new","application/json",(request, response) -> {
            Users user=gson.fromJson(request.body(),Users.class);
            sql2oUsersDao.add(user);
            response.status(201);
            return gson.toJson(user);
        });
        post("/departments/new","application/json",(request, response) -> {
            Departments departments =gson.fromJson(request.body(),Departments.class);
            sql2oDepartmentsDao.addDept(departments);
            response.status(201);
            return gson.toJson(departments);
        });

        post("/news/new/general","application/json",(request, response) -> {

            News news =gson.fromJson(request.body(),News.class);
            sql2oNewsDao.addNews(news);
            response.status(201);
            return gson.toJson(news);
        });
        post("/news/new/department","application/json",(request, response) -> {
            News department_news =gson.fromJson(request.body(),News.class);
            Departments departments=sql2oDepartmentsDao.findById(department_news.getDeptid());
            Users users=sql2oUsersDao.findById(department_news.getUid());
            if(departments==null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists",
                        request.params("id")));
            }
            if(users==null){
                throw new ApiExceptions(404, String.format("No user with the id: \"%s\" exists",
                        request.params("id")));
            }
            sql2oNewsDao.addNews(department_news);
            response.status(201);
            return gson.toJson(department_news);
        });

        post("/news/new/general","application/json",(request, response) -> {

            News news =gson.fromJson(request.body(),News.class);
            sql2oNewsDao.addNews(news);
            response.status(201);
            return gson.toJson(news);
        });
        post("/add/user/:user_id/department/:department_id","application/json",(request, response) -> {

            int user_id=Integer.parseInt(request.params("user_id"));
            int department_id=Integer.parseInt(request.params("department_id"));
            Departments departments=sql2oDepartmentsDao.findById(department_id);
            Users users=sql2oUsersDao.findById(user_id);
            if(departments==null){
                throw new ApiExceptions(404, String.format("No department with the id: \"%s\" exists",
                        request.params("department_id")));
            }
            if(users==null){
                throw new ApiExceptions(404, String.format("No user with the id: \"%s\" exists",
                        request.params("user_id")));
            }
            sql2oDepartmentsDao.addUserIntoDept(users,departments);

            List<Users> departmentUsers=sql2oDepartmentsDao.getAllUsersInDepartment(departments.getId());

            response.status(201);
            return gson.toJson(departmentUsers);
        });
        //FILTERS
        exception(ApiExceptions.class, (exception, request, response) -> {
            ApiExceptions err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            response.type("application/json");
            response.status(err.getStatusCode());
            response.body(gson.toJson(jsonMap));
        });


        after((request, response) ->{
            response.type("application/json");
        });

    }
}
