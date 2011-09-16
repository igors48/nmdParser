package constructor.objects.interpreter.core;

import constructor.objects.AdapterException;
import dated.item.modification.Modification;
import http.BatchLoader;

/**
 * ��������� �������� ��������������
 *
 * @author Igor Usenko
 *         Date: 09.04.2009
 */
public interface InterpreterAdapter {

    /**
     * ���������� ���������� ������ �������
     *
     * @return ���������� ������ �������
     * @throws AdapterException ���� �� ����������
     */
    PageListAnalyser getPageListAnalyser() throws AdapterException;

    /**
     * ���������� ��������� �������
     *
     * @return ��������� �������
     * @throws AdapterException ���� �� ����������
     */
    BatchLoader getWebPageLoader() throws AdapterException;

    /**
     * ���������� ���������� ���������
     *
     * @return ���������� ���������
     * @throws AdapterException ���� �� ����������
     */
    FragmentAnalyser getFragmentAnalyser() throws AdapterException;

    /**
     * ���������� ���������� ��������
     *
     * @return ���������� ��������
     * @throws AdapterException ���� �� ����������
     */
    PageAnalyser getPageAnalyser() throws AdapterException;

    /**
     * ���������� �����������, ������� ����� ����������������
     *
     * @return �����������
     * @throws AdapterException ���� �� ����������
     */
    Modification getModification() throws AdapterException;

    /**
     * ���������� ������������� ��������������
     *
     * @return �������������
     * @throws AdapterException ���� �� ����������
     */
    String getId() throws AdapterException;

    /**
     * ���������� ������������ ���������� ������������ ��������� � ������
     *
     * @return ������������ ���������� ������������ ��������� � ������. 0 - ���� ����� ��� ��������
     */
    int getLastItemCount();

    /**
     * ���������� ���������� �������������� ���������� ���������
     *
     * @return ���������� �������������� ���������� ���������
     */
    int getPrecachedItemsCount();

    /**
     * ���������� ����� �������� ����� �������� ���������������� ��������. Anti-DDoS
     *
     * @return ����� �������� � �������������
     */
    long getPauseBetweenRequests();

    /**
     * ���������� ���� ������ ������
     *
     * @return true �� ���������� false - ����������
     */
    boolean isCancelled();
}
