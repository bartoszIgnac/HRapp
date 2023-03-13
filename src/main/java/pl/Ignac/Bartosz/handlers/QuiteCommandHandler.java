package pl.Ignac.Bartosz.handlers;

import pl.Ignac.Bartosz.QuiteHRappException;
import pl.Ignac.Bartosz.input.UserInputCommand;

public class QuiteCommandHandler extends BaseCommandHandler {

    private static final String COMMAND_NAME = "quite";

    @Override
    public void handle(UserInputCommand command) {
        throw new QuiteHRappException();
    }
    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
