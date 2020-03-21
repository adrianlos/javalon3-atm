package pl.sda.bankteller.importing;

import lombok.AllArgsConstructor;
import pl.sda.bankteller.account.Account;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccountImporter {
    private final String fileName;

    public List<Account> getAccounts() {
        try {
            return Files.readAllLines(Paths.get(fileName)).stream()
                    .map(line -> line.split(","))
                    .map(this::toAccount)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("FATAL", e);
        }
    }

    private Account toAccount(String[] parts) {
        String accountNumber = parts[0];
        String accountBalance = parts[1];
        return new Account(accountNumber, new BigDecimal(withoutCurrency(accountBalance)));
    }

    private String withoutCurrency(String accountBalance) {
        return accountBalance.replace("£", "");
    }
}
