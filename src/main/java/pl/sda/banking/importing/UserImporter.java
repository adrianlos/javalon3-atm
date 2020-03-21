package pl.sda.banking.importing;

import lombok.AllArgsConstructor;
import pl.sda.banking.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UserImporter {
    private final String fileName;

    public Set<User> getUsers() {
        try {
            return Files.readAllLines(Paths.get(fileName)).stream()
                    .map(line -> line.split(","))
                    .map(this::toUser)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException("FATAL", e);
        }
    }

    private User toUser(String[] parts) {
        String username = parts[0];
        String password = parts[1];
        String accountNumber = parts[2];
        return new User(username, password, accountNumber);
    }
}
