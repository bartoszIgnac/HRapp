package pl.Ignac.Bartosz.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import pl.Ignac.Bartosz.model.Department;
import pl.Ignac.Bartosz.model.Employee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DepartmentDao {

    private static Logger LOG = Logger.getLogger(DepartmentDao.class.getName());

    private ObjectMapper objectMapper;
    private EmployeeDao employeeDao;

    public DepartmentDao() {

        this.objectMapper = new ObjectMapper();
        employeeDao = new EmployeeDao();
    }

    public List<Department> findAll() {

        return getDepartments();
    }

    private List<Department> getDepartments() {
        try {
            return objectMapper.readValue(Files.readString(Paths.get("./departments.txt")), new TypeReference<>() {
            });
        } catch(IOException e){
            LOG.log(Level.WARNING, "Error on getDepartments", e);
            return new ArrayList<>();
        }
    }

    public void add(Department department) {

            List<Department> departments = getDepartments();
            departments.add(department);
            saveDepartment(departments);
    }

    private void saveDepartment(List<Department> departments)  {
        try {
            Files.writeString(Paths.get("./departments.txt"), objectMapper.writeValueAsString(departments));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on save Department", e);
        }
    }

    public void addEmployeeToDepartment(Employee employee, Department department) {
        List<Department> departments = getDepartments();
        for(Department d : departments){
            if(Objects.equals(d.getName(), department.getName())){
                d.getEmployees().add(employee);
            }
        }
        saveDepartment(departments);
    }

    public Optional<Department> findOne(String name) {
        return getDepartments().stream().filter(c->c.getName().equals(name)).findAny();
    }

    public void changeFile() {
        try {
            Files.move(Paths.get("./departments.txt"), Paths.get("./old/departments.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on remove employee from Department", e);
        }

        File file = new File("./departments.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on remove employee from Department", e);
        }
    }

    public void removeEmployeeFromDepartment(int employeeId, Department department) {

        List<Department> departments = getDepartments();
        for(Department d : departments){
            if(Objects.equals(d.getName(), department.getName())){
                if(!d.getEmployees().removeIf(employee -> employee.getIdNumber() == employeeId)){
                    throw new IllegalArgumentException("employee not found " + employeeId);
                }
            }
        }
        save(departments);

    }

    private void save(List<Department> departments) {
        changeFile();
        saveDepartment(departments);
        try {
            Files.delete(Paths.get("./old/departments.txt"));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on save file", e);
        }
    }

    public void removeEmployeeFromAllDepartment(int employeeId) {

        List<Department> departments = getDepartments();
        for(Department d : departments){
            d.getEmployees().removeIf(employee -> employee.getIdNumber() == employeeId);
        }
        save(departments);

    }

    public void removeDepartment(String departmentName) {
        List<Department> departments = getDepartments();
        if(!departments.removeIf(department -> department.getName().equals(departmentName))){
            throw new IllegalArgumentException("department not found " + departmentName);
        }
        save(departments);
    }
}
