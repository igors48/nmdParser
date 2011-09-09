package constructor.objects.processor;

/**
 * ������� ��� ������ ����������� ����������
 *
 * @author Igor Usenko
 *         Date: 26.10.2009
 */
public final class VariableProcessorUtils {

    public static boolean specified(final String _name) {
        boolean result = false;

        if (_name != null) {

            if (!_name.isEmpty()) {
                result = true;
            }
        }

        return result;
    }

    private VariableProcessorUtils() {
        // empty
    }
}
