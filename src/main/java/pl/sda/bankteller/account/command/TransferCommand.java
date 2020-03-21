package pl.sda.bankteller.account.command;

import lombok.AllArgsConstructor;
import pl.sda.bankteller.account.Account;
import pl.sda.bankteller.account.AccountRepository;
import pl.sda.bankteller.account.InsufficientFundsException;
import pl.sda.bankteller.account.NoSuchAccountException;

import java.math.BigDecimal;

@AllArgsConstructor
class TransferCommand implements Command {
    private final AccountRepository accountRepository;
    private final String fromAccountNumber;
    private final String toAccountNumber;
    private final BigDecimal amount;

    @Override
    public Account execute() throws InsufficientFundsException, NoSuchAccountException {
        transfer(fromAccountNumber, toAccountNumber);
        return accountRepository.findByNumber(fromAccountNumber);
    }

    @Override
    public Account undo() throws InsufficientFundsException, NoSuchAccountException {
        transfer(toAccountNumber, fromAccountNumber);
        return accountRepository.findByNumber(fromAccountNumber);
    }

    public void transfer(String sourceAccountNumber, String targetAccountNumber) throws InsufficientFundsException, NoSuchAccountException {
        Account sourceAccount = accountRepository.findByNumber(sourceAccountNumber);
        Account targetAccount = accountRepository.findByNumber(targetAccountNumber);

        accountRepository.save(sourceAccount.withdraw(amount));
        accountRepository.save(targetAccount.deposit(amount));
    }

    @Override
    public String description() {
        return "Transfer £" + amount + " from account #" + fromAccountNumber + " to account #" + toAccountNumber;
    }
}
