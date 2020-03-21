package pl.sda.banking.account.command;

import org.junit.jupiter.api.Test;
import pl.sda.banking.account.Account;
import pl.sda.banking.account.AccountRepository;
import pl.sda.banking.account.InsufficientFundsException;
import pl.sda.banking.account.NoSuchAccountException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepositCommandTest {

    AccountRepository accountRepository = AccountRepository.of(
            List.of(
                    new Account("123", BigDecimal.TEN),
                    new Account("321", BigDecimal.ONE)
            )
    );

    @Test
    void shouldDepositMoney() throws InsufficientFundsException, NoSuchAccountException {
        //given
        Command command = new DepositCommand(accountRepository, "321", new BigDecimal("100"));
        //when
        command.execute();
        //then
        Account actual = accountRepository.findByNumber("321");
        Account expected = new Account("321", new BigDecimal("101"));
        assertEquals(expected, actual);
    }

    @Test
    void shouldUndoDeposit() throws InsufficientFundsException, NoSuchAccountException {
        Command command = new DepositCommand(accountRepository, "321", new BigDecimal("100"));
        //when
        command.execute();
        command.undo();
        //then
        Account actual = accountRepository.findByNumber("321");
        Account expected = new Account("321", BigDecimal.ONE);
        assertEquals(expected, actual);
    }

}
