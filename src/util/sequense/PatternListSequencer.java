package util.sequense;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Генератор последовательностей на основании списка параметров генерации
 *
 * @author Igor Usenko
 *         Date: 28.02.2010
 */
public class PatternListSequencer {

    public static List<String> getSequence(final List<SequenceGenerationParams> _sequenceGenerationParams) {
        Assert.notNull(_sequenceGenerationParams, "Generation parameters list is null");

        List<String> result = new ArrayList<String>();

        for (SequenceGenerationParams current : _sequenceGenerationParams) {
            List<String> candidates = StringSequencer.getSequence(current.getPattern(),
                    current.getFrom(),
                    current.getTo(),
                    current.getStep(),
                    current.getMult(),
                    current.getLen(),
                    current.getPadding());

            result.addAll(candidates);
        }

        return result;
    }


}
