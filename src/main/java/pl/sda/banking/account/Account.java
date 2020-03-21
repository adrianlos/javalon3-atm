package pl.sda.banking.account;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Account {
    private final String accountNumber;
    private final BigDecimal balance;

    public Account deposit(BigDecimal amount) {
        return new Account(accountNumber, balance.add(amount));
    }

    public Account withdraw(BigDecimal amount) throws InsufficientFundsException {
        if(balance.compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        return new Account(accountNumber, balance.subtract(amount));
    }
}
