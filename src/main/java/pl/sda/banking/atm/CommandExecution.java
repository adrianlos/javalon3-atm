package pl.sda.banking.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sda.banking.account.command.Command;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class CommandExecution {
    private final Command command;
    private final LocalDateTime executionTime;
}
