package pl.Ignac.Bartosz.handlers;

import pl.Ignac.Bartosz.input.UserInputCommand;

public class HelpCommandHandler extends BaseCommandHandler {

    private static final String COMMAND_NAME = "help";

    @Override
    public void handle(UserInputCommand command) {
        System.out.println("Help HPapp");
        System.out.println("Allowed commands:");
        System.out.println("employee add employeeName employeeSurname employeeIdNumber - add employee");
        System.out.println("employee remove employeeIdNumber - remove employee");
        System.out.println("employee list- list of employees");
        System.out.println("department new departmentName - create new department");
        System.out.println("department delete departmentName - delete department");
        System.out.println("department add employeeIdNumber departmentName - add employee to department");
        System.out.println("department remove employeeIdNumber  - remove employee from department");
        System.out.println("department list - list of departments");

    }

    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
