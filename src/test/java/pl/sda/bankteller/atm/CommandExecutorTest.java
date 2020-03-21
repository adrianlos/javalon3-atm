package pl.sda.bankteller.atm;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import pl.sda.bankteller.account.Account;
import pl.sda.bankteller.account.InsufficientFundsException;
import pl.sda.bankteller.account.NoSuchAccountException;
import pl.sda.bankteller.account.command.Command;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandExecutorTest {

    @Test
    void shouldNotFailOnUndoingEmptyHistory() {
        //given
        CommandExecutor commandExecutor = new CommandExecutor();
        //when
        assertDoesNotThrow(commandExecutor::undo);
    }

    @Test
    void shouldUndoCommandsInOrder() throws InsufficientFundsException, NoSuchAccountException {
        //given
        Account someAccount = new Account("", BigDecimal.ZERO);

        Command command1 = mock(Command.class);
        when(command1.execute()).thenReturn(someAccount);
        when(command1.undo()).thenReturn(someAccount);

        Command command2 = mock(Command.class);
        when(command2.execute()).thenReturn(someAccount);
        when(command2.undo()).thenReturn(someAccount);

        CommandExecutor commandExecutor = new CommandExecutor();
        //when
        commandExecutor.execute(command1);
        commandExecutor.execute(command2);
        commandExecutor.undo();
        commandExecutor.undo();
        //then
        InOrder inOrder = inOrder(command2, command1);
        inOrder.verify(command2).undo();
        inOrder.verify(command1).undo();
    }

}