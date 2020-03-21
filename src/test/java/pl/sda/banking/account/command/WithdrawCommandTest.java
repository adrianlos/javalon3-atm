package pl.sda.banking.account.command;

import org.junit.jupiter.api.Test;
import pl.sda.banking.account.Account;
import pl.sda.banking.account.AccountRepository;
import pl.sda.banking.account.InsufficientFundsException;
import pl.sda.banking.account.NoSuchAccountException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawCommandTest {

    AccountRepository accountRepository = AccountRepository.of(
            List.of(
                    new Account("123", new BigDecimal("100")),
                    new Account("321", new BigDecimal("200"))
            )
    );

    @Test
    void shouldWithdrawMoney() throws InsufficientFundsException, NoSuchAccountException {
        //given
        Command command = new WithdrawCommand(accountRepository, "321", BigDecimal.ONE);
        //when
        command.execute();
        //then
        Account actual = accountRepository.findByNumber("321");
        Account expected = new Account("321", new BigDecimal("199"));
        assertEquals(expected, actual);
    }

    @Test
    void shouldUndoWithdrawal() throws InsufficientFundsException, NoSuchAccountException {
        Command command = new WithdrawCommand(accountRepository, "321", new BigDecimal("100"));
        //when
        command.execute();
        command.undo();
        //then
        Account actual = accountRepository.findByNumber("321");
        Account expected = new Account("321", new BigDecimal("200"));
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotWithdrawMoreThanPossible() {
        Command command = new WithdrawCommand(accountRepository, "321", new BigDecimal("500"));
        //when
        assertThrows(InsufficientFundsException.class, command::execute);
    }

}