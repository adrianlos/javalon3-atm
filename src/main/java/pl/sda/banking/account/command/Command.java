package pl.sda.banking.account.command;

import pl.sda.banking.account.Account;
import pl.sda.banking.account.InsufficientFundsException;
import pl.sda.banking.account.NoSuchAccountException;

public interface Command {
    Account execute() throws InsufficientFundsException, NoSuchAccountException;

    Account undo() throws InsufficientFundsException, NoSuchAccountException;

    String description();

    default String undoDescription() {
        return "Undo: " + description();
    }
}
