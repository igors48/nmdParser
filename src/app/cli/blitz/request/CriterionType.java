package app.cli.blitz.request;

//todo юзаетс€ еще и сниппете, походу не здесь ему место

/**
 * “ип критери€ отбора контента
 *
 * @author Igor Usenko
 *         Date: 28.10.2009
 */
public enum CriterionType {
    /**
     * отбор по XPath запросу
     */
    XPATH,
    /**
     * отбор по регул€рному выражению
     */
    REGEXP,
    /**
     * отбор фильтром контента
     */
    FILTER
}
