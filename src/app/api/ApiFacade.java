package app.api;

import app.cli.blitz.BlitzRequestHandler;
import app.controller.Controller;
import app.iui.command.ExternalConverterContext;
import app.metadata.ObjectMetaData;
import app.templater.TemplateParameters;
import constructor.dom.locator.Mask;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import timeservice.TimeService;

import java.util.List;
import java.util.Map;

/**
 * ����� ����������. ��� �������� - ������������, �.� �������� �������
 * �(���) �������� ����� ���� - �� �� ��������
 *
 * @author Igor Usenko
 *         Date: 15.04.2009
 */
public interface ApiFacade extends BlitzRequestHandler {

    /**
     * ���������� ��� ���������� ��������� ���������
     *
     * @return ��� ��������� ��� ������ ������ ���� �� ����������
     */
    String getDefaultSettingsName();

    /**
     * ���������� ��� ���������� �������� ������������
     *
     * @return ��� �������� ������������ ��� ������ ������ ���� �� ����������
     */
    String getDefaultWorkspaceName();

    /**
     * ���������� ������ ��������� ������� �����������
     *
     * @return ������ ��������� ������� �����������
     * @throws FatalException ���� �� ����������
     */
    List<String> getWorkspacesNames() throws FatalException;

    /**
     * ��������� ��������� �������� ���������
     *
     * @param _id ������������� ���������
     * @throws FatalException ���� �� ����������
     */
    void loadSettings(String _id) throws FatalException;

    /**
     * ��������� ��������� ������� ������������
     *
     * @param _id ������������� �������� ������������
     * @throws FatalException ���� �� ����������
     */
    void loadWorkspace(String _id) throws FatalException;

    /**
     * ���������� ������ ���������� ���� �������� � ������� ������������
     * ���������� ��� ��� ��� ����� ��� ��� ������ � ��������
     *
     * @return ������ ���������� ���� �������� � ������� ������������
     * @throws FatalException ���� �� ����������
     */
    List<ObjectMetaData> getObjectsMetaData() throws FatalException;

    /**
     * ������� ������� ������������ � ��������� ���������������
     *
     * @param _id �������������
     * @throws FatalException ���� �� ����������
     */
    void createWorkspace(String _id) throws FatalException;

    /**
     * ������� ������� ������������ � ��������� ���������������
     *
     * @param _id �������������
     * @throws FatalException ���� �� ����������
     */
    void deleteWorkspace(String _id) throws FatalException;

    /**
     * ��������������� ������� ������������
     *
     * @param _oldId ������ �������������
     * @param _newId ����� �������������
     * @throws FatalException ���� �� ����������
     */
    void renameWorkspace(String _oldId, String _newId) throws FatalException;

    /**
     * ��������� ��������, ����� � ������
     *
     * @param _sourceId                 ������������� ���������
     * @param _channelId                ������������� ������
     * @param _outputId                 ������������� �������������
     * @param _forcedDays               ���������� ���� ��� ��������������� ���������� -1 - ��� ������; 0 - ���; >0 - ���������� ����
     * @param _controller               ���������� ��� �������������
     * @param _context                  �������� ����������
     * @param _externalConverterContext �������� �������� ����������
     * @return ������ ������ ���� �������������� ������
     * @throws FatalException       ���� ������������� ������� �� �������
     * @throws RecoverableException ���� �� ������� ������������ ��������
     */
    List<String> updateFullSet(String _sourceId, String _channelId, String _outputId, int _forcedDays, Controller _controller, Map<String, String> _context, ExternalConverterContext _externalConverterContext) throws FatalException, RecoverableException;

    /**
     * ���������� ������ ������������������ � ������� ������������
     * ���������� �����������. �������� ���������� ������ �����.
     *
     * @param _mask ����� ������
     * @return ������ ����������
     * @throws FatalException ���� �� ����������
     */
    List<String> getSourcesNames(Mask _mask) throws FatalException;

    /**
     * ��������� ��������� �������� �����������
     *
     * @param _id         ������������� ���������
     * @param _controller ���������� ��� �������������
     * @param _context    �������� ����������
     * @throws FatalException       ���� �� ������� ������� ��������
     * @throws RecoverableException ���� �������� �� ������� ��������
     */
    void updateSource(String _id, Controller _controller, Map<String, String> _context) throws FatalException, RecoverableException;

    /**
     * ���������� ������ ������������������ � ������� ������������
     * �������. �������� ���������� ������ �����.
     *
     * @param _mask ����� ������
     * @return ������ �������
     * @throws FatalException ���� �� ����������
     */
    List<String> getChannelsNames(Mask _mask) throws FatalException;

    /**
     * ��������� ��������� �����
     *
     * @param _id         ������������� ������
     * @param _forcedDays ���������� ���� ��� ��������������� ���������� -1 - ��� ������; 0 - ���; >0 - ���������� ����
     * @param _controller ���������� ��� �������������
     * @param _context    �������� ����������
     * @throws FatalException       ���� �� ������� ������� �����
     * @throws RecoverableException ���� ����� �� ������� ��������
     */
    void updateChannel(String _id, int _forcedDays, Controller _controller, Map<String, String> _context) throws FatalException, RecoverableException;

    /**
     * ���������� ������ ������������������ � ������� ������������
     * ����������� �������� ����������. �������� ���������� ������ �����.
     *
     * @param _mask ����� ������
     * @return ������ �����������
     * @throws FatalException ���� �� ����������
     */
    List<String> getOutputsNames(Mask _mask) throws FatalException;

    /**
     * ��������� �������� ��������
     *
     * @param _id         ������������� �������������
     * @param _forcedDays ���������� ���� ��� ��������������� ���������� -1 - ��� ������; 0 - ���; >0 - ���������� ����
     * @param _controller ���������� ��� �������������
     * @param _context    �������� ����������
     * @return ������ ������ ���� �������������� ������
     * @throws FatalException       ���� ������������� ������� �� �������
     * @throws RecoverableException ���� �� ������� ������������ ��������
     */
    List<String> updateOutput(String _id, int _forcedDays, Controller _controller, Map<String, String> _context) throws FatalException, RecoverableException;

    /**
     * ���������� ������ ������������������ � ������� ������������
     * ���������. �������� ���������� ������ �����.
     *
     * @param _mask ����� ������
     * @return ������ ���������
     * @throws FatalException ���� �� ����������
     */
    List<String> getSimplersNames(Mask _mask) throws FatalException;

    /**
     * ��������� (���������) ������������ ��������
     *
     * @param _configuration ������������
     * @throws FatalException ���� �� ����������
     */
    void storeSimplerConfiguration(SimplerConfiguration _configuration) throws FatalException;

    /**
     * ������� ������������ ��������
     *
     * @param _configuration ������������
     * @throws FatalException ���� �� ����������
     */
    void removeSimplerConfiguration(SimplerConfiguration _configuration) throws FatalException;

    /**
     * ��������� ������ ���������� �������
     */
    void enableDebugConsole();

    /**
     * ������� ����� �������� ��� ������ � �����
     *
     * @param _parameters - ��������� �������
     * @throws FatalException ���� �� ����������
     */
    void createTemplates(TemplateParameters _parameters) throws FatalException;

    /**
     * ������������ �������
     *
     * @param _name       ������ ���� � ����� ��������
     * @param _forcedDays ���������� ���� ��� ��������������� ���������� -1 - ��� ������; 0 - ���; >0 - ���������� ����
     * @param _context    �������� ����������
     * @return ������ ������ ���� �������������� ������
     * @throws FatalException ���� �� ����������
     */
    List<String> processSnippet(String _name, int _forcedDays, Map<String, String> _context) throws FatalException;

    /**
     * ���������� ���������� ������� ���������� ���������/������/���������
     *
     * @return ���������� �������
     * @throws FatalException ���� �� ����������
     */
    int getUpdateAttempts() throws FatalException;

    /**
     * ���������� ������� ����� ��������� ���������� ���������/������/���������
     *
     * @return ������� � �������������
     * @throws FatalException ���� �� ����������
     */
    long getUpdateTimeout() throws FatalException;

    /**
     * ���������� ��������� ������ �������
     *
     * @return ������ �������
     * @throws FatalException ���� �� ����������
     */
    TimeService getTimeService() throws FatalException;

    /**
     * ������� ��������� ����� ��������� �����������
     *
     * @param _mask ����� ������ ��������
     * @throws FatalException ���� �� ����������
     */
    void removeServiceFiles(Mask _mask) throws FatalException;

    /**
     * ��������� ������ � API
     */
    void cleanup();

    /**
     * ���������� ����� ��������� �������� ���������� ������ ���
     */
    public class FatalException extends Exception {

        public FatalException() {
        }

        public FatalException(String _s) {
            super(_s);
        }

        public FatalException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public FatalException(Throwable _throwable) {
            super(_throwable);
        }
    }

    /**
     * ���������� ������ ��������� - ����� ������� ��� ���� �������
     */
    public class RecoverableException extends Exception {

        public RecoverableException() {
        }

        public RecoverableException(String _s) {
            super(_s);
        }

        public RecoverableException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public RecoverableException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
