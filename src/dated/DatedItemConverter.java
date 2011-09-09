package dated;

import flowtext.Section;

/**
 * @author Igor Usenko
 *         Date: 07.11.2008
 */
public interface DatedItemConverter {
    Section convert(DatedItem _item, final DatedItemConverterContext _context);
}
