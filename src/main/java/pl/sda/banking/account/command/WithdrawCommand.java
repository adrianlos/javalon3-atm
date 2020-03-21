package pl.sda.banking.account.command;

import pl.sda.banking.account.Account;
import pl.sda.banking.account.AccountRepository;
import pl.sda.banking.account.InsufficientFundsException;
import pl.sda.banking.account.NoSuchAccountException;

import java.math.BigDecimal;

class WithdrawCommand extends DepositCommand {

    public WithdrawCommand(AccountRepository accountRepository, String accountNumber, BigDecimal amount) {
        super(accountRepository, accountNumber, amount);
    }

    @Override
    public Account execute() throws InsufficientFundsException, NoSuchAccountException {
        return super.undo();
    }

    @Override
    public Account undo() throws InsufficientFundsException, NoSuchAccountException {
        return super.execute();
    }

    @Override
    public String description() {
        return "Withdraw £" + amount + " from account #" + accountNumber;
    }
}
