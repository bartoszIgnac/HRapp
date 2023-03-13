package pl.Ignac.Bartosz.handlers;

import pl.Ignac.Bartosz.dao.EmployeeDao;
import pl.Ignac.Bartosz.input.UserInputCommand;
import pl.Ignac.Bartosz.model.Employee;

import java.util.List;
import java.util.logging.Logger;

public class EmployeeCommandHandler extends BaseCommandHandler {

    private static Logger LOG = Logger.getLogger(EmployeeCommandHandler.class.getName());

    private static final String COMMAND_NAME = "employee";

    private EmployeeDao employeeDao;

    public EmployeeCommandHandler() {
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
                LOG.info("List of employees");

                if(!command.getParam().isEmpty()){
                    throw new IllegalArgumentException("employee list doesn't support any additional params");
                }

                List<Employee> employees = employeeDao.findAll();
                employees.forEach(System.out::println);
                break;
            case ADD :
                LOG.info("Add employee");

                if(command.getParam().size()!=3){
                    throw new IllegalArgumentException("wrong command format. Check help for more information");
                }

                String employeeName = command.getParam().get(0);
                String employeeSurname = command.getParam().get(1);
                //Todo validate ID number
                int employeeId = Integer.parseInt(command.getParam().get(2));

                employeeDao.add(new Employee(employeeName, employeeSurname, employeeId));
                break;
            case REMOVE:
                LOG.info("Remove employee");

                if(command.getParam().size()!=1){
                    throw new IllegalArgumentException("wrong command format. Check help for more information");
                }

                employeeId = Integer.parseInt(command.getParam().get(0));

                employeeDao.remove(employeeId);

                break;

            default: {
                throw new IllegalArgumentException(String.format("Unknown action: %s from command: %s", command.getAction(), command.getCommand()));
            }
        }

    }
}
