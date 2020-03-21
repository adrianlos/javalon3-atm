package pl.sda.bankteller.atm;

import lombok.AllArgsConstructor;
import pl.sda.bankteller.User;
import pl.sda.bankteller.account.AccountRepository;
import pl.sda.bankteller.account.command.UnknownCommandException;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@AllArgsConstructor
public class ATM {
    private final Set<User> users;
    private final AccountRepository accountRepository;

    public void run() {
        while(true) {
            UserContext userContext = login();
            userContext.run();
        }
    }

    private UserContext login() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Type: 'login <user> <password>' to log in.");
            System.out.println("Type: 'exit' to terminate.");
            System.out.print("> ");

            String input = scanner.nextLine();
            if (input.startsWith("login")) {
                try {
                    User loggedInUser = getUser(input.split(" "));
                    System.out.println("Login successful.");
                    return new UserContext(loggedInUser, accountRepository);
                } catch (BadCredentialsException | UnknownCommandException e) {
                    System.out.println(e.getMessage());
                }
            } else if (input.startsWith("exit")) {
                System.out.println("Bye!");
                System.exit(0);
            } else {
                System.out.println("You need to log in first.");
            }
        }
    }

    private User getUser(String[] inputParts) throws BadCredentialsException, UnknownCommandException {
        if (inputParts.length < 3) {
            throw new UnknownCommandException("Malformed command.");
        }
        return getUser(inputParts[1], inputParts[2]).orElseThrow(() -> new BadCredentialsException("Incorrect username or password."));
    }

    private Optional<User> getUser(String userName, String encryptedPassword) {
        return users.stream()
                .filter(user -> user.getLogin().equals(userName))
                .filter(user -> user.getPassword().equals(encryptedPassword))
                .findAny();
    }
}
