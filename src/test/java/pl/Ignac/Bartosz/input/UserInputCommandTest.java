package pl.Ignac.Bartosz.input;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInputCommandTest {


    @Test
    void shouldBuildCorrectInputCommand() {
        //given
        String input = "department new departmentName";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        //then
        assertEquals("department", userInputCommand.getCommand());
        assertLinesMatch(List.of("departmentName"), userInputCommand.getParam());
    }
    @Test
    void shouldBuildCorrectInputCommandWithMultipleParams() {
        //given
        String input = "employee add name surname idNumber";

        //when
        UserInputCommand userInputCommand = new UserInputCommand(input);

        //then
        assertEquals("employee", userInputCommand.getCommand());
        assertLinesMatch(List.of("name", "surname", "idNumber"), userInputCommand.getParam());
    }

}