package http.data;

import http.Data;

/**
 * ������ ������
 *
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class EmptyData implements Data {

    public byte[] getData() throws DataException {
        return new byte[0];
    }

    public byte[] getData(final int _from, final int _to) throws DataException {
        return new byte[0];
    }

    public int size() {
        return 0;
    }

    public String getCharsetName() {
        return null;
    }

}
