package pl.sda.bankteller.account.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.sda.bankteller.account.AccountRepository;

import static org.junit.jupiter.api.Assertions.*;

class CommandFactoryTest {

    @Test
    void shouldRecognizeDepositCommand() throws UnknownCommandException {
        //given
        String input = "deposit 123";
        CommandFactory commandFactory = new CommandFactory(new AccountRepository(), "");
        //when
        Command command = commandFactory.fromUserInput(input);
        //then
        assertTrue(command instanceof DepositCommand);
    }

    @Test
    void shouldRecognizeWithdrawCommand() throws UnknownCommandException {
        //given
        String input = "withdraw 123";
        CommandFactory commandFactory = new CommandFactory(new AccountRepository(), "");
        //when
        Command command = commandFactory.fromUserInput(input);
        //then
        assertTrue(command instanceof WithdrawCommand);
    }

    @Test
    void shouldRecognizeTransferCommand() throws UnknownCommandException {
        //given
        String input = "transfer 123 123abc";
        CommandFactory commandFactory = new CommandFactory(new AccountRepository(), "");
        //when
        Command command = commandFactory.fromUserInput(input);
        //then
        assertTrue(command instanceof TransferCommand);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "withdraw",
            "deposit",
            "transfer 123",
            "some command"
    })
    void shouldNotAcceptMalformedInput(String input) {
        //given
        CommandFactory commandFactory = new CommandFactory(new AccountRepository(), "");
        //expect
        assertThrows(UnknownCommandException.class, () -> commandFactory.fromUserInput(input));
    }

}