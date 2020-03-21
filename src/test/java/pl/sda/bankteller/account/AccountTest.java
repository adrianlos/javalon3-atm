package pl.sda.bankteller.account;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldDepositMoney() {
        //given
        Account originalAccount = new Account("123", BigDecimal.ZERO);
        //when
        Account accountWithMoney = originalAccount.deposit(new BigDecimal("100"));
        //then
        assertEquals(new BigDecimal("100"), accountWithMoney.getBalance());
        assertNotSame(accountWithMoney, originalAccount);
    }

    @Test
    void shouldWithdrawMoney() throws InsufficientFundsException {
        //given
        Account originalAccount = new Account("123", new BigDecimal("100"));
        //when
        Account accountWithLessMoney = originalAccount.withdraw(new BigDecimal("10"));
        //then
        assertEquals(new BigDecimal("90"), accountWithLessMoney.getBalance());
        assertNotSame(accountWithLessMoney, originalAccount);
    }

    @Test
    void shouldNotWithdrawMoreThanBalance() {
        //given
        Account originalAccount = new Account("123", new BigDecimal("100"));
        //expect
        assertThrows(InsufficientFundsException.class, () -> originalAccount.withdraw(new BigDecimal("1000")));
    }

}