package pl.sda.bankteller.account.command;

import lombok.AllArgsConstructor;
import pl.sda.bankteller.account.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class CommandFactory {
    public final static List<String> AVAILABLE_COMMANDS = List.of(
            "'withdraw <amount>'",
            "'deposit <amount>'",
            "'transfer <amount> <target account #>' - transfer money from your account to other"
    );
    private final AccountRepository accountRepository;
    private final String accountNumber;

    public Command fromUserInput(String userInput) throws UnknownCommandException {
        String[] inputParts = userInput.split(" ");
        if (inputParts[0].equals("withdraw")) {
            return createWithdrawCommand(inputParts);
        }
        if (inputParts[0].equals("deposit")) {
            return createDepositCommand(inputParts);
        }
        if (inputParts[0].equals("transfer")) {
            return createTransferCommand(inputParts);
        }
        throw new UnknownCommandException("Unrecognized command, please try again. Type 'help' to list available commands.");
    }

    private Command createTransferCommand(String[] inputParts) throws UnknownCommandException {
        if(inputParts.length < 3) {
            throw new UnknownCommandException("Malformed command. Should be: 'transfer <amount> <target account #>'");
        }
        BigDecimal amount = new BigDecimal(inputParts[1]);
        String targetAccount = inputParts[2];
        return new TransferCommand(accountRepository, accountNumber, targetAccount, amount);
    }

    private Command createDepositCommand(String[] inputParts) throws UnknownCommandException {
        if(inputParts.length < 2) {
            throw new UnknownCommandException("Malformed command. Should be: 'deposit <amount>'");
        }
        BigDecimal amount = new BigDecimal(inputParts[1]);
        return new DepositCommand(accountRepository, accountNumber, amount);
    }

    private Command createWithdrawCommand(String[] inputParts) throws UnknownCommandException {
        if(inputParts.length < 2) {
            throw new UnknownCommandException("Malformed command. Should be: 'withdraw <amount>'");
        }
        BigDecimal amount = new BigDecimal(inputParts[1]);
        return new WithdrawCommand(accountRepository, accountNumber, amount);
    }

}
