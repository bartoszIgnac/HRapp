package pl.Ignac.Bartosz.handlers;

import pl.Ignac.Bartosz.input.UserInputCommand;

public interface CommandHandler {
    void handle(UserInputCommand command);

    boolean supports(String name);
}
