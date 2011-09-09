package app.cli.parser;

import app.api.ApiFacade;
import app.cli.command.Command;
import org.apache.commons.cli.CommandLine;

import java.util.List;
import java.util.Map;

/**
 * ��������� ������� �����. ������ ������� - �������������
 * ������ ����� � ������ ������
 *
 * @author Igor Usenko
 *         Date: 09.05.2009
 */
public interface OptionParser {

    //todo api ����� ��������� � ������� ��� ����� �����������, � �� ������ ��� ���� ������ ���� �������� �������

    /**
     * ��������������� ������ ����� � ������ ������
     *
     * @param _commandLine ������ �����(��������� ������)
     * @param _api         ������ �� API
     * @param _context     �������� ���������� �������
     * @return ������ ������
     */
    List<Command> parse(CommandLine _commandLine, ApiFacade _api, Map<String, String> _context);

}
