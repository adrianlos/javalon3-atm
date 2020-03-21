package pl.sda.banking.atm;

import pl.sda.banking.account.Account;
import pl.sda.banking.account.InsufficientFundsException;
import pl.sda.banking.account.NoSuchAccountException;
import pl.sda.banking.account.command.Command;

import java.time.LocalDateTime;
import java.util.EmptyStackException;
import java.util.Stack;

class CommandExecutor {
    private final Stack<CommandExecution> commandHistory = new Stack<>();

    void execute(Command command) {
        try {
            Account updatedAccount = command.execute();
            commandHistory.push(new CommandExecution(command, LocalDateTime.now()));
            System.out.println(command.description());
            printCurrentBalance(updatedAccount);
        } catch (InsufficientFundsException ignore) {
            System.out.println("Insufficient funds to perform this operation.");
        } catch (NoSuchAccountException e) {
            System.out.println("Account does not exist");
        }
    }

    void undo() {
        try {
            Command command = commandHistory.pop().getCommand();
            Account updatedAccount = command.undo();
            System.out.println(command.undoDescription());
            printCurrentBalance(updatedAccount);
        } catch (EmptyStackException ignore) {
            System.out.println("No actions done in this session.");
        } catch (InsufficientFundsException e) {
            throw new RuntimeException("FATAL: Could not undo command.");
        } catch (NoSuchAccountException e) {
            System.out.println("Account does not exist");
        }
    }

    void printCurrentBalance(Account account) {
        System.out.println("Current account balance: £" + account.getBalance());
    }

    void printHistory() {
        commandHistory.forEach(commandExecution ->
                System.out.println("[" + commandExecution.getExecutionTime() + "] " + commandExecution.getCommand().description())
        );
    }

}
