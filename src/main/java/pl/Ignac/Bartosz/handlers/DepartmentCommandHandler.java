package pl.Ignac.Bartosz.handlers;

import pl.Ignac.Bartosz.dao.DepartmentDao;
import pl.Ignac.Bartosz.dao.EmployeeDao;
import pl.Ignac.Bartosz.input.UserInputCommand;
import pl.Ignac.Bartosz.model.Department;
import pl.Ignac.Bartosz.model.Employee;


import java.util.List;
import java.util.logging.Logger;

public class DepartmentCommandHandler extends BaseCommandHandler {

    private static Logger LOG = Logger.getLogger(DepartmentCommandHandler.class.getName());

    private static final String COMMAND_NAME = "department";

    private DepartmentDao departmentDao;
    private EmployeeDao employeeDao;

    public DepartmentCommandHandler() {
        departmentDao = new DepartmentDao();
        employeeDao = new EmployeeDao();

    }

    @Override
    protected String getCommandName() {

        return COMMAND_NAME;
    }

    @Override
    public void handle(UserInputCommand command) {
        if(command.getAction()==null){
            throw new IllegalArgumentException("action can't be null");
        }

        switch(command.getAction()){
            case LIST :
                LOG.info("List of departments");

                if(!command.getParam().isEmpty()){
                    throw new IllegalArgumentException("department list doesn't support any additional params");
                }

                List<Department> departments = departmentDao.findAll();
                departments.forEach(System.out::println);
                break;
            case NEW :
                LOG.info("Add new department");

                if(command.getParam().size()!=1){
                    throw new IllegalArgumentException("wrong command format. Check help for more information");
                }

                String departmentName = command.getParam().get(0);
                departmentDao.add(new Department(departmentName));
                break;

            case DELETE:
                LOG.info("Delete department");

                if(command.getParam().size()!=1){
                    throw new IllegalArgumentException("wrong command format. Check help for more information");
                }
                departmentName = command.getParam().get(0);
                departmentDao.removeDepartment(departmentName);

                break;

            case ADD :
                LOG.info("Add new employee to department");

                if(command.getParam().size()!=2){
                    throw new IllegalArgumentException("wrong command format. Check help for more information");
                }

                int employeeId = Integer.parseInt(command.getParam().get(0));
                Employee employee = employeeDao.findOne(employeeId).orElseThrow(() -> new IllegalArgumentException("employee not found " + employeeId));

                departmentName = command.getParam().get(1);
                Department department = departmentDao.findOne(departmentName).orElseThrow(() -> new IllegalArgumentException("department not found " + departmentName));
                departmentDao.addEmployeeToDepartment(employee, department);
                break;

            case REMOVE:
                LOG.info("Remove employee from department");
                if(command.getParam().size()!=2){
                    throw new IllegalArgumentException("wrong command format. Check help for more information");
                }

                employeeId = Integer.parseInt(command.getParam().get(0));

                departmentName = command.getParam().get(1);
                department = departmentDao.findOne(departmentName).orElseThrow(() -> new IllegalArgumentException("department not found " + departmentName));

                departmentDao.removeEmployeeFromDepartment(employeeId, department);
                break;

            default: {
                throw new IllegalArgumentException(String.format("Unknown action: %s from command: %s", command.getAction(), command.getCommand()));
            }
        }
    }
}
