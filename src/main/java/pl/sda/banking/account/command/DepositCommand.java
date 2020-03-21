package pl.sda.banking.account.command;

import lombok.AllArgsConstructor;
import pl.sda.banking.account.Account;
import pl.sda.banking.account.AccountRepository;
import pl.sda.banking.account.InsufficientFundsException;
import pl.sda.banking.account.NoSuchAccountException;

import java.math.BigDecimal;

@AllArgsConstructor
class DepositCommand implements Command {
    private final AccountRepository accountRepository;
    protected final String accountNumber;
    protected final BigDecimal amount;

    @Override
    public Account execute() throws InsufficientFundsException, NoSuchAccountException {
        Account account = accountRepository.findByNumber(accountNumber);
        return accountRepository.save(account.deposit(amount));
    }

    @Override
    public Account undo() throws InsufficientFundsException, NoSuchAccountException {
        Account account = accountRepository.findByNumber(accountNumber);
        return accountRepository.save(account.withdraw(amount));
    }

    @Override
    public String description() {
        return "Deposit £" + amount + " to account #" + accountNumber;
    }
}
