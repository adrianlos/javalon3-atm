package pl.sda.banking.atm;

import lombok.AllArgsConstructor;
import pl.sda.banking.User;
import pl.sda.banking.account.Account;
import pl.sda.banking.account.AccountRepository;
import pl.sda.banking.account.NoSuchAccountException;
import pl.sda.banking.account.command.CommandFactory;
import pl.sda.banking.account.command.UnknownCommandException;

import java.util.Scanner;

@AllArgsConstructor
class UserContext {
    private final User user;
    private final AccountRepository accountRepository;


    void run() {
        CommandExecutor commandExecutor = new CommandExecutor();
        CommandFactory commandFactory = new CommandFactory(accountRepository, user.getAccountNumber());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                System.out.println("Bye!");
                System.exit(0);
            } else if (input.equals("logout")) {
                System.out.println("Logged out.");
                break;
            } else if (input.equals("help")) {
                printHelp();
            } else if (input.equals("history")) {
                commandExecutor.printHistory();
            } else if (input.equals("balance")) {
                printBalance(commandExecutor);
            } else if (input.equals("undo")) {
                commandExecutor.undo();
            } else {
                try {
                    commandExecutor.execute(commandFactory.fromUserInput(input));
                } catch (UnknownCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void printBalance(CommandExecutor commandExecutor) {
        try {
            Account account = accountRepository.findByNumber(user.getAccountNumber());
            commandExecutor.printCurrentBalance(account);
        } catch (NoSuchAccountException ignore) {
            throw new RuntimeException("FATAL: Operating on not existing account");
        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("'help' - prints this message");
        System.out.println("'exit' - exit the program");
        System.out.println("'logout' - log out of current session");
        System.out.println("'history' - prints current session's history");
        System.out.println("'undo' - undo last operation");
        System.out.println("'balance' - check current account balance");
        CommandFactory.AVAILABLE_COMMANDS.forEach(System.out::println);
    }
}
