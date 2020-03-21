package pl.sda.bankteller.account.command;

import pl.sda.bankteller.account.Account;
import pl.sda.bankteller.account.InsufficientFundsException;
import pl.sda.bankteller.account.NoSuchAccountException;

public interface Command {
    Account execute() throws InsufficientFundsException, NoSuchAccountException;

    Account undo() throws InsufficientFundsException, NoSuchAccountException;

    String description();

    default String undoDescription() {
        return "Undo: " + description();
    }
}
