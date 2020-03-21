package pl.sda.bankteller;

import pl.sda.bankteller.account.Account;
import pl.sda.bankteller.account.AccountRepository;
import pl.sda.bankteller.atm.ATM;
import pl.sda.bankteller.importing.AccountImporter;
import pl.sda.bankteller.importing.UserImporter;

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
