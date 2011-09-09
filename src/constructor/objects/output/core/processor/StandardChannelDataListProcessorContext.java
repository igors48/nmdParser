package constructor.objects.output.core.processor;

import app.VersionInfo;
import constructor.objects.output.configuration.DateSectionMode;
import util.Assert;

/**
 * Контекст стандартного процессора списка данных канала
 *
 * @author Igor Usenko
 *         Date: 11.10.2009
 */
public class StandardChannelDataListProcessorContext {

    private final boolean resolveImageLinks;
    private final VersionInfo versionInfo;
    private final DateSectionMode dateSectionMode;
    private final boolean fromNewToOld;

    public StandardChannelDataListProcessorContext(final boolean resolveImageLinks, final VersionInfo _versionInfo, final DateSectionMode _dateSectionMode, final boolean _fromNewToOld) {
        this.resolveImageLinks = resolveImageLinks;

        Assert.notNull(_versionInfo, "Version information is null");
        this.versionInfo = _versionInfo;

        Assert.notNull(_dateSectionMode, "Date section mode is null");
        this.dateSectionMode = _dateSectionMode;

        this.fromNewToOld = _fromNewToOld;
    }

    public boolean isResolveImageLinks() {
        return this.resolveImageLinks;
    }

    public VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public DateSectionMode getDateSectionMode() {
        return this.dateSectionMode;
    }

    public boolean isFromNewToOld() {
        return this.fromNewToOld;
    }
}