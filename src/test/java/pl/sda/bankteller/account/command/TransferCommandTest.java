package pl.sda.bankteller.account.command;

import org.junit.jupiter.api.Test;
import pl.sda.bankteller.account.Account;
import pl.sda.bankteller.account.AccountRepository;
import pl.sda.bankteller.account.InsufficientFundsException;
import pl.sda.bankteller.account.NoSuchAccountException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransferCommandTest {

    AccountRepository accountRepository = AccountRepository.of(
            List.of(
                    new Account("123", new BigDecimal("100")),
                    new Account("321", new BigDecimal("200"))
            )
    );

    @Test
    void shouldTransferMoney() throws InsufficientFundsException, NoSuchAccountException {
        //given
        Command command = new TransferCommand(accountRepository, "321", "123", new BigDecimal("100"));
        //when
        command.execute();
        //then
        Account account1 = accountRepository.findByNumber("123");
        Account expectedAccount1 = new Account("123", new BigDecimal("200"));
        assertEquals(expectedAccount1, account1);

        Account account2 = accountRepository.findByNumber("321");
        Account expectedAccount2 = new Account("321", new BigDecimal("100"));
        assertEquals(expectedAccount2, account2);
    }

    @Test
    void shouldUndoTransfer() throws InsufficientFundsException, NoSuchAccountException {
        //given
        Command command = new TransferCommand(accountRepository, "321", "123", new BigDecimal("100"));
        //when
        command.execute();
        command.undo();
        //then
        Account account1 = accountRepository.findByNumber("123");
        Account expectedAccount1 = new Account("123", new BigDecimal("100"));
        assertEquals(expectedAccount1, account1);

        Account account2 = accountRepository.findByNumber("321");
        Account expectedAccount2 = new Account("321", new BigDecimal("200"));
        assertEquals(expectedAccount2, account2);
    }

    @Test
    void shouldNotTransferMoreThanPossible() {
        //given
        Command command = new TransferCommand(accountRepository, "321", "123", new BigDecimal("500"));
        //expect
        assertThrows(InsufficientFundsException.class, command::execute);
    }

    @Test
    void shouldNotTransferToNotExistingAccount() {
        //given
        Command command = new TransferCommand(accountRepository, "321", "124", new BigDecimal("100"));
        //expect
        assertThrows(NoSuchAccountException.class, command::execute);
    }

}