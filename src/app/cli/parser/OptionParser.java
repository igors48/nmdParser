package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.Command;
import org.apache.commons.cli.CommandLine;

import java.util.List;
import java.util.Map;

/**
 * Интерфейс парсера опций. Задача парсера - преобразовать
 * список опций в список команд
 *
 * @author Igor Usenko
 *         Date: 09.05.2009
 */
public interface OptionParser {

    //todo api можно инжектить в команду уже перед выполнением, а не тянуть его сюда только ради создания команды

    /**
     * Преобразовывает список опций в список команд
     *
     * @param _commandLine список опций(командная строка)
     * @param _api         ссылка на API
     * @param _context     контекст исполнения команды
     * @return список команд
     */
    List<Command> parse(CommandLine _commandLine, ApiFacade _api, Map<String, String> _context);

}
