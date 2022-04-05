package commands;

import exceptions.CommandException;
import exceptions.ParameterException;
import exceptions.RecursiveException;

public interface Command {
    public void run(String arg) throws CommandException, ParameterException, RecursiveException;
}
