package work.unit.reflectools;

import junit.framework.TestCase;
import util.ReflecTools;

import java.lang.reflect.Method;

/**
 * @author Igor Usenko
 *         Date: 03.03.2009
 */
public class ReflecToolsTest extends TestCase {

    public ReflecToolsTest(String s) {
        super(s);
    }

    // ����� ������������� ���� �� ���������� � ��������

    public void testFindMethod() {
        Fixture fixture = new Fixture();

        try {
            Method method = ReflecTools.findMethod(fixture, "set", "name");

            assertFalse(method == null);
        } catch (NoSuchMethodException e) {
            fail();
        }
    }

    // ����� ������������� ���� �� ���������� �� � ������� �����������

    public void testFindMethodWrongParms() {
        Fixture fixture = new Fixture();

        try {
            Method method = ReflecTools.findMethod(fixture, "set", "oops");

        } catch (NoSuchMethodException e) {
            return;
        }

        fail();
    }

    // ����� ���������������

    public void testFindMethodNotExists() {
        Fixture fixture = new Fixture();

        try {
            Method method = ReflecTools.findMethod(fixture, "set", "noexists");

        } catch (NoSuchMethodException e) {
            return;
        }

        fail();
    }

}
