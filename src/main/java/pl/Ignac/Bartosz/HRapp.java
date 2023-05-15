package pl.Ignac.Bartosz;

import pl.Ignac.Bartosz.handlers.*;
import pl.Ignac.Bartosz.input.UserInputCommand;
import pl.Ignac.Bartosz.input.UserInputManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HRapp {

    private static Logger LOG = Logger.getLogger(HRapp.class.getName());
    public static void main(String[] args) {
        new HRapp().start();
    }

    private void start() {
        LOG.info("Start app...");

        /**
         * department list -> departmentList()
         * department new departmentName -> departmentAdd(name)
         * department add idEmployee departmentName -> addEmployeeToDepartment(idEmployee)
         *
         * employee list - > employeeList()
         * employee add name + surname + idNumber -> employeeAdd(name, surname, idNumber)
         *
         * quite -> quiteApplication();
         *
         * salary list -> salaryList()
         * salary add idNumber -> salaryAdd(salary, idNumber)
         *
         */

        boolean applicationLoop = true;

        UserInputManager userInputManager = new UserInputManager();

        List<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new HelpCommandHandler()); //help
        handlers.add(new QuiteCommandHandler()); //quite
        handlers.add(new EmployeeCommandHandler());
        handlers.add(new DepartmentCommandHandler());


        while(applicationLoop){
            try {
                UserInputCommand userInputCommand = userInputManager.nextCommand();
                LOG.info(userInputCommand.toString());

                Optional<CommandHandler> currentHandler = Optional.empty();
                for (CommandHandler handler : handlers) {
                    if (handler.supports(userInputCommand.getCommand())) {
                        currentHandler = Optional.of(handler);
                        break;
                    }
                }
                currentHandler
                        .orElseThrow(() -> new IllegalArgumentException("Unknown handler: " + userInputCommand.getCommand()))
                        .handle(userInputCommand);
            } catch (QuiteHRappException e){
                LOG.info("Quite...");
                applicationLoop = false;

            } catch (IllegalArgumentException e){
                LOG.log(Level.WARNING, "Validation exception" + e.getMessage());

            } catch (Exception e){
                LOG.log(Level.SEVERE, "Unknown error", e);
            }
        }


    }

}
