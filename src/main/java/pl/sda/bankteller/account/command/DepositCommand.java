package pl.sda.bankteller.account.command;

import lombok.AllArgsConstructor;
import pl.sda.bankteller.account.Account;
import pl.sda.bankteller.account.AccountRepository;
import pl.sda.bankteller.account.InsufficientFundsException;
import pl.sda.bankteller.account.NoSuchAccountException;

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
