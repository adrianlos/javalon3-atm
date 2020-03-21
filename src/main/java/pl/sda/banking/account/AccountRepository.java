package pl.sda.banking.account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository {
    private final Map<String, Account> accounts = new HashMap<>();

    public static AccountRepository of(List<Account> accountList) {
        AccountRepository accountRepository = new AccountRepository();
        accountList.forEach(accountRepository::save);
        return accountRepository;
    }

    public Account findByNumber(String accountNumber) throws NoSuchAccountException {
        if(!accounts.containsKey(accountNumber)) {
            throw new NoSuchAccountException();
        }
        return accounts.get(accountNumber);
    }

    public Account save(Account account) {
        accounts.put(account.getAccountNumber(), account);
        return account;
    }

}
