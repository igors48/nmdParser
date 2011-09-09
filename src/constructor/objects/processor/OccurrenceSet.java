package constructor.objects.processor;

import util.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Множество номеров вхождений. Если пустое - считает,
 * что нужное вхождение только одно - первое(нулевое).
 * Если содержит только один элемент равный -1 - считает,
 * что все вхождения нужны.
 *
 * @author Igor Usenko
 *         Date: 22.05.2009
 */
public class OccurrenceSet {

    public static final String ALL_ACCEPTABLE_TOKEN = "[ all acceptable ]";

    private final Set<Integer> occurrences;

    private boolean allAcceptable;

    public OccurrenceSet() {
        this.occurrences = new HashSet<Integer>();
    }

    public void add(final int _occurrence) {
        Assert.greaterOrEqual(_occurrence, -1, "Occurrence number < -1.");

        if (!this.allAcceptable) {

            if (_occurrence == -1) {
                this.allAcceptable = true;
                this.occurrences.clear();
            } else {
                this.occurrences.add(_occurrence);
            }
        }
    }

    public boolean acceptable(final int _occurrence) {
        Assert.greaterOrEqual(_occurrence, 0, "Occurrence number < 0.");

        return this.allAcceptable || checkOccurrence(_occurrence);
    }

    public String toString() {
        String result = ALL_ACCEPTABLE_TOKEN;

        if (!this.allAcceptable) {
            Integer[] values = this.occurrences.toArray(new Integer[this.occurrences.size()]);
            Arrays.sort(values);

            result = Arrays.toString(values);
        }

        return result;
    }

    private boolean checkOccurrence(int _occurrence) {
        return this.occurrences.isEmpty() ? _occurrence == 0 : this.occurrences.contains(_occurrence);
    }
}
