package pl.sda.bankteller.atm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.sda.bankteller.account.command.Command;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
class CommandExecution {
    private final Command command;
    private final LocalDateTime executionTime;
}
