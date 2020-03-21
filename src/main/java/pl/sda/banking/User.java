package pl.sda.banking;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private final String login;
    private final String password;
    private final String accountNumber;
}
