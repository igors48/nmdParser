package util.batchtext;

import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Класс для пакетного удаления/извлечения фрагментов строки
 *
 * @author Igor Usenko
 *         Date: 23.05.2009
 */
public final class BatchTextTools {

    /**
     * Удаляет из строки диапазоны заданные списком
     *
     * @param _data       исходная строка
     * @param _boundaries список удаляемых диапазонов
     * @return строка с удаленными диапазонами
     */
    public static String remove(final String _data, final List<Boundary> _boundaries) {
        Assert.isValidString(_data, "Data is not valid.");
        Assert.notNull(_boundaries, "Boundaries is null.");

        String result = _data;

        if (!_boundaries.isEmpty()) {
            List<Boundary> removes = joinList(_boundaries);

            StringBuffer buffer = new StringBuffer();

            for (int index = 0; index < _data.length(); ++index) {

                if (!inBoundaries(index, removes)) {
                    buffer.append(_data.substring(index, index + 1));
                }
            }
            result = buffer.toString();
        }

        return result;
    }

    /**
     * Объединяет два диапазона - возвращает объединенный диапазон.
     *
     * @param _first  первый диапазон
     * @param _second второй диапазон
     * @return результат объединения или null если диапазоны не пересекаются
     */
    public static Boundary join(final Boundary _first, final Boundary _second) {
        Assert.notNull(_first, "First boundary is null.");
        Assert.notNull(_second, "Second boundary is null.");

        Boundary result = null;

        if (isIntersected(_first, _second)) {
            int left = Math.min(_first.getStart(), _second.getStart());
            int right = Math.max(_first.getEnd(), _second.getEnd());

            result = new Boundary(left, right);
        }

        return result;
    }

    /**
     * Вычисляет результат объединения списка диапазонов
     *
     * @param _boundaries список диапазонов
     * @return список диапазонов получившихся в результате обработки параметра
     */
    public static List<Boundary> joinList(final List<Boundary> _boundaries) {
        Assert.notNull(_boundaries, "Boundaries list is null.");

        List<Boundary> result = newArrayList();

        if (!_boundaries.isEmpty()) {
            result.add(_boundaries.get(0));

            for (Boundary boundary : _boundaries) {
                List<Boundary> intersections = findIntersections(boundary, result);

                if (intersections.isEmpty()) {
                    result.add(boundary);
                } else {
                    Boundary joined = createJoined(boundary, intersections);

                    result.removeAll(intersections);
                    result.add(joined);
                }
            }
        }

        return result;
    }

    private static Boundary createJoined(final Boundary _boundary, final List<Boundary> _intersections) {
        List<Boundary> joinedList = newArrayList();
        joinedList.add(_boundary);
        joinedList.addAll(_intersections);

        return joinIntersections(joinedList);
    }

    private static List<Boundary> findIntersections(final Boundary _boundary, final List<Boundary> _boundaries) {
        List<Boundary> result = newArrayList();

        for (Boundary boundary : _boundaries) {

            if (isIntersected(_boundary, boundary)) {
                result.add(boundary);
            }
        }

        return result;
    }

    private static Boundary joinIntersections(final List<Boundary> _boundaries) {
        Boundary result = null;

        if (!_boundaries.isEmpty()) {
            result = _boundaries.get(0);

            for (Boundary boundary : _boundaries) {
                result = join(result, boundary);
            }
        }

        return result;
    }

    private static boolean isIntersected(final Boundary _first, final Boundary _second) {
        return inBoundary(_first.getStart(), _second) || inBoundary(_first.getEnd(), _second);
    }

    private static boolean inBoundary(final int _point, final Boundary _boundary) {
        return _point >= _boundary.getStart() && _point <= _boundary.getEnd();
    }

    private static boolean inBoundaries(final int _point, final List<Boundary> _boundary) {
        boolean result = false;

        for (Boundary current : _boundary) {

            if (inBoundary(_point, current)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private BatchTextTools() {
        // empty
    }
}
