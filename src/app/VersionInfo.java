package app;

import util.Assert;

/**
 * ���������� � ������
 *
 * @author Igor Usenko
 *         Date: 14.11.2009
 */
public class VersionInfo {

    private final String title;
    private final String version;
    private final String build;

    public VersionInfo(final String _title, final String _version, final String _build) {
        Assert.isValidString(_title, "Application title is not valid");
        this.title = _title;

        Assert.isValidString(_version, "Application version number is not valid");
        this.version = _version;

        Assert.isValidString(_build, "Application build number is not valid");
        this.build = _build;
    }

    public final String getCliApplicationTitle() {
        return this.title + " " + getVersionAndBuild();
    }

    public final String getGuiApplicationTitle() {
        return "nmdLite [ " + getVersionAndBuild() + " ]";
    }

    public final String getVersionAndBuild() {
        return this.version + "." + this.build;
    }
}
