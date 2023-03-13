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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDao {

    private static Logger LOG = Logger.getLogger(EmployeeDao.class.getName());

    private ObjectMapper objectMapper;

    public EmployeeDao() {

        this.objectMapper = new ObjectMapper();
    }

    public List<Employee> findAll() {
        return getEmployees();

    }

    private List<Employee> getEmployees() {
        try {
            return objectMapper.readValue(Files.readString(Paths.get("./employees.txt")), new TypeReference<>() {
            });
        } catch(IOException e){
            LOG.log(Level.WARNING, "Error on getEmployees", e);
            return new ArrayList<>();
        }
    }

    private void saveEmployees(List<Employee> employees)  {
        try {
            Files.writeString(Paths.get("./employees.txt"), objectMapper.writeValueAsString(employees));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on save employees", e);
        }
    }

    public void add(Employee employee) {


        List<Employee> employees = getEmployees();
        employees.add(employee);
        saveEmployees(employees);

    }

    public Optional<Employee> findOne(int id) {
        return getEmployees().stream().filter(c->c.getIdNumber() == id).findAny();
    }

    public void changeFile() {
        try {
            Files.move(Paths.get("./employees.txt"), Paths.get("./old/employees.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on remove employee", e);
        }

        File file = new File("./employees.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error on remove employee", e);
        }
    }


    public void remove(int employeeId) {
        List<Employee> employees = getEmployees();
        if (employees.removeIf(employee -> employee.getIdNumber() == employeeId)) {
            changeFile();
            saveEmployees(employees);
            try {
                Files.delete(Paths.get("./old/employees.txt"));
            } catch (IOException e) {
                LOG.log(Level.WARNING, "Error on remove employee", e);
            }
            DepartmentDao departmentDao  = new DepartmentDao();
            departmentDao.removeEmployeeFromAllDepartment(employeeId);
        } else {
            throw new IllegalArgumentException("employee not found " + employeeId);
        }
    }
}
