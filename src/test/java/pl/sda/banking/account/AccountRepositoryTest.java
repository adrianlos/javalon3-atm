package pl.sda.banking.account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountRepositoryTest {

    @Test
    void shouldFindAccountByNumber() throws NoSuchAccountException {
        //given
        AccountRepository accountRepository = AccountRepository.of(
                List.of(
                        new Account("321", BigDecimal.ZERO),
                        new Account("123", BigDecimal.ZERO)
                )
        );
        //when
        Account foundAccount = accountRepository.findByNumber("123");
        //then
        Account expected = new Account("123", BigDecimal.ZERO);
        assertEquals(expected, foundAccount);

    }

    @Test
    void shouldReturnUpdatedAccount() {
        //given
        AccountRepository accountRepository = AccountRepository.of(
                List.of(new Account("123", BigDecimal.ZERO))
        );
        Account accountWithUpdate = new Account("123", BigDecimal.TEN);
        //when
        Account updatedAccount = accountRepository.save(accountWithUpdate);
        //then
        assertEquals(accountWithUpdate, updatedAccount);
    }

    @Test
    void shouldUpdateExistingAccount() throws NoSuchAccountException {
        //given
        AccountRepository accountRepository = AccountRepository.of(
                List.of(new Account("123", BigDecimal.ZERO))
        );
        Account accountWithUpdate = new Account("123", BigDecimal.TEN);
        //when
        accountRepository.save(accountWithUpdate);
        Account updatedAccount = accountRepository.findByNumber("123");

        //then
        assertEquals(BigDecimal.TEN, updatedAccount.getBalance());
    }

    @Test
    void shouldSaveNewAccount() throws NoSuchAccountException {
        //given
        AccountRepository accountRepository = AccountRepository.of(
                List.of(new Account("321", BigDecimal.ZERO))
        );
        Account newAccount = new Account("123", BigDecimal.TEN);
        //when
        accountRepository.save(newAccount);
        Account foundAccount = accountRepository.findByNumber("123");
        Account otherAccount = accountRepository.findByNumber("321");

        //then
        assertEquals(BigDecimal.TEN, foundAccount.getBalance());
        assertEquals(BigDecimal.ZERO, otherAccount.getBalance());
    }

}