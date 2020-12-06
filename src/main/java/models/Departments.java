package models;

import java.util.Objects;

public class Departments {

    private String dept_name;
    private String dept_description;
    private int dept_empNo;
    private int id;

    //Constructor
    public Departments(String dept_name, String dept_description, int dept_empNo) {
        this.dept_name = dept_name;
        this.dept_description = dept_description;
        this.dept_empNo = dept_empNo;

    }
    //Override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departments that = (Departments) o;
        return dept_empNo == that.dept_empNo &&
                Objects.equals(dept_name, that.dept_name) &&
                Objects.equals(dept_description, that.dept_description) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dept_name, dept_description, dept_empNo, id);
    }

    public String getDpt_name() {
        return dept_name;
    }

    public void setDpt_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDpt_description() {
        return dept_description;
    }

    public void setDpt_description(String dept_description) {
        this.dept_description = dept_description;
    }

    public int getDpt_empNo() {
        return dept_empNo;
    }

    public void setDpt_empNo(int dept_empNo) {
        this.dept_empNo = dept_empNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
