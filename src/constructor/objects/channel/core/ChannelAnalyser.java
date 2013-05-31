package constructor.objects.channel.core;

import dated.item.modification.Modification;
import http.BatchLoader;

/**
 * ��������� ����������� ������. ������ - �����-���� �������� �������
 * �������� ��������� ������.
 *
 * @author Igor Usenko
 *         Date: 31.12.2008
 */
public interface ChannelAnalyser {

    //todo _coverUrl ����� ��� � �� � ���������

    ChannelDataHeader getHeader(Modification _modification, BatchLoader _batchLoader, String _coverUrl) throws ChannelAnalyserException;

    public class ChannelAnalyserException extends Exception {

        public ChannelAnalyserException() {
        }

        public ChannelAnalyserException(final String _s) {
            super(_s);
        }

        public ChannelAnalyserException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public ChannelAnalyserException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
