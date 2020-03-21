package pl.sda.banking;

import pl.sda.banking.account.Account;
import pl.sda.banking.account.AccountRepository;
import pl.sda.banking.atm.ATM;
import pl.sda.banking.importing.AccountImporter;
import pl.sda.banking.importing.UserImporter;

import java.util.List;
import java.util.Set;

class Main {
    public static void main(String[] args) {
        System.out.println("Hello, this is an ATM.");

        Set<User> users = new UserImporter("src/main/resources/users.csv").getUsers();
        List<Account> accounts = new AccountImporter("src/main/resources/accounts.csv").getAccounts();

        new ATM(users, AccountRepository.of(accounts)).run();
    }
}
