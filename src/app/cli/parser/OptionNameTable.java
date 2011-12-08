package app.cli.parser;

/**
 * ����� �����, ��������� � �� �������� ��� ��������� ������
 *
 * @author Igor Usenko
 *         Date: 20.04.2009
 */
public final class OptionNameTable {

    private static final String NAME_TOKEN = "name";
    private static final String MASKS_TOKEN = "mask(s)";
    private static final String NAMES_TOKEN = "name(s)";
    private static final String URL_TOKEN = "url";
    private static final String URLS_TOKEN = "url(s)";
    private static final String TEMPLATES_TOKEN = "name rss_url";
    private static final String EMAIL_PASSWORD_TOKEN = "email password";
    private static final String EMAIL_FEED_URL_TOKEN = "email feed_url";
    private static final String EMAIL_TOKEN = "email";

    public static final String DEBUG_MODE_OPTION_SHORT_NAME = "d";
    public static final String DEBUG_MODE_OPTION_FULL_NAME = "debug";
    public static final String DEBUG_MODE_OPTION_DESCRIPTION = "Enable debug console";

    public static final String FORCED_MODE_OPTION_SHORT_NAME = "f";
    public static final String FORCED_MODE_OPTION_FULL_NAME = "forced";
    public static final String FORCED_MODE_OPTION_ATTRIBUTE_NAME = "day(s)";
    public static final String FORCED_MODE_OPTION_DESCRIPTION = "Switch update mode to forced";

    public static final String SETTINGS_OPTION_SHORT_NAME = "s";
    public static final String SETTINGS_OPTION_FULL_NAME = "settings";
    public static final String SETTINGS_OPTION_ATTRIBUTE_NAME = NAME_TOKEN;
    public static final String SETTINGS_OPTION_DESCRIPTION = "Select settings with <" + SETTINGS_OPTION_ATTRIBUTE_NAME + "> as current";

    public static final String WORKSPACE_OPTION_SHORT_NAME = "w";
    public static final String WORKSPACE_OPTION_FULL_NAME = "workspace";
    public static final String WORKSPACE_OPTION_ATTRIBUTE_NAME = NAME_TOKEN;
    public static final String WORKSPACE_OPTION_DESCRIPTION = "Select workspace with <" + WORKSPACE_OPTION_ATTRIBUTE_NAME + "> as current";

    public static final String CREATE_WORKSPACE_OPTION_SHORT_NAME = "cw";
    public static final String CREATE_WORKSPACE_OPTION_FULL_NAME = "create-workspace";
    public static final String CREATE_WORKSPACE_OPTION_ATTRIBUTE_NAME = NAME_TOKEN;
    public static final String CREATE_WORKSPACE_OPTION_DESCRIPTION = "Create workspace with <" + CREATE_WORKSPACE_OPTION_ATTRIBUTE_NAME + "> identifier";

    public static final String UPDATE_ALL_OPTION_SHORT_NAME = "ua";
    public static final String UPDATE_ALL_OPTION_FULL_NAME = "update-all";
    public static final String UPDATE_ALL_OPTION_ATTRIBUTE_NAME = MASKS_TOKEN;
    public static final String UPDATE_ALL_OPTION_DESCRIPTION = "Update all (1)sources (2)channels (3)outputs with optional mask(s)";

    public static final String UPDATE_SOURCES_OPTION_SHORT_NAME = "us";
    public static final String UPDATE_SOURCES_OPTION_FULL_NAME = "update-sources";
    public static final String UPDATE_SOURCES_OPTION_ATTRIBUTE_NAME = NAMES_TOKEN;
    public static final String UPDATE_SOURCES_OPTION_DESCRIPTION = "Update source <" + UPDATE_SOURCES_OPTION_ATTRIBUTE_NAME + ">";

    public static final String UPDATE_ALL_SOURCES_OPTION_SHORT_NAME = "uas";
    public static final String UPDATE_ALL_SOURCES_OPTION_FULL_NAME = "update-all-sources";
    public static final String UPDATE_ALL_SOURCES_OPTION_ATTRIBUTE_NAME = MASKS_TOKEN;
    public static final String UPDATE_ALL_SOURCES_OPTION_DESCRIPTION = "Update all sources with optional mask(s)";

    public static final String UPDATE_CHANNELS_OPTION_SHORT_NAME = "uc";
    public static final String UPDATE_CHANNELS_OPTION_FULL_NAME = "update-channels";
    public static final String UPDATE_CHANNELS_OPTION_ATTRIBUTE_NAME = NAMES_TOKEN;
    public static final String UPDATE_CHANNELS_OPTION_DESCRIPTION = "Update channels <" + UPDATE_CHANNELS_OPTION_ATTRIBUTE_NAME + ">";

    public static final String UPDATE_ALL_CHANNELS_OPTION_SHORT_NAME = "uac";
    public static final String UPDATE_ALL_CHANNELS_OPTION_FULL_NAME = "update-all-channels";
    public static final String UPDATE_ALL_CHANNELS_OPTION_ATTRIBUTE_NAME = MASKS_TOKEN;
    public static final String UPDATE_ALL_CHANNELS_OPTION_DESCRIPTION = "Update all channels with optional mask(s)";

    public static final String UPDATE_OUTPUTS_OPTION_SHORT_NAME = "uo";
    public static final String UPDATE_OUTPUTS_OPTION_FULL_NAME = "update-outputs";
    public static final String UPDATE_OUTPUTS_OPTION_ATTRIBUTE_NAME = NAMES_TOKEN;
    public static final String UPDATE_OUTPUTS_OPTION_DESCRIPTION = "Update outputs <" + UPDATE_OUTPUTS_OPTION_ATTRIBUTE_NAME + ">";

    public static final String UPDATE_ALL_OUTPUTS_OPTION_SHORT_NAME = "uao";
    public static final String UPDATE_ALL_OUTPUTS_OPTION_FULL_NAME = "update-all-outputs";
    public static final String UPDATE_ALL_OUTPUTS_OPTION_ATTRIBUTE_NAME = MASKS_TOKEN;
    public static final String UPDATE_ALL_OUTPUTS_OPTION_DESCRIPTION = "Update all outputs with optional mask(s)";

    public static final String CREATE_FULL_TEXT_TEMPLATES_OPTION_SHORT_NAME = "cf";
    public static final String CREATE_FULL_TEXT_TEMPLATES_OPTION_FULL_NAME = "create-full-text";
    public static final String CREATE_FULL_TEXT_TEMPLATES_OPTION_ATTRIBUTE_NAME = TEMPLATES_TOKEN;
    public static final String CREATE_FULL_TEXT_TEMPLATES_OPTION_DESCRIPTION = "Create templates for work with full text RSS feed";

    public static final String CREATE_BRIEF_TEXT_TEMPLATES_OPTION_SHORT_NAME = "cb";
    public static final String CREATE_BRIEF_TEXT_TEMPLATES_OPTION_FULL_NAME = "create-brief-text";
    public static final String CREATE_BRIEF_TEXT_TEMPLATES_OPTION_ATTRIBUTE_NAME = TEMPLATES_TOKEN;
    public static final String CREATE_BRIEF_TEXT_TEMPLATES_OPTION_DESCRIPTION = "Create templates for work with brief text RSS feed";

    public static final String PROPERTIES_OPTION_SHORT_NAME = "P";
    public static final String PROPERTIES_OPTION_ATTRIBUTE_NAME = "property=value";
    public static final String PROPERTIES_OPTION_DESCRIPTION = "Use value for given property";

    public static final String PROCESS_SNIPPET_OPTION_SHORT_NAME = "ps";
    public static final String PROCESS_SNIPPET_OPTION_FULL_NAME = "process-snippet";
    public static final String PROCESS_SNIPPET_OPTION_ATTRIBUTE_NAME = NAMES_TOKEN;
    public static final String PROCESS_SNIPPET_OPTION_DESCRIPTION = "Process given snippets";

    public static final String BLITZ_PAGE_REQUEST_OPTION_SHORT_NAME = "bp";
    public static final String BLITZ_PAGE_REQUEST_OPTION_FULL_NAME = "blitz-page";
    public static final String BLITZ_PAGE_REQUEST_OPTION_ATTRIBUTE_NAME = URLS_TOKEN;
    public static final String BLITZ_PAGE_REQUEST_OPTION_DESCRIPTION = "Process blitz request for given urls";

    public static final String BLITZ_FEED_REQUEST_OPTION_SHORT_NAME = "bf";
    public static final String BLITZ_FEED_REQUEST_OPTION_FULL_NAME = "blitz-feed";
    public static final String BLITZ_FEED_REQUEST_OPTION_ATTRIBUTE_NAME = URL_TOKEN;
    public static final String BLITZ_FEED_REQUEST_OPTION_DESCRIPTION = "Process blitz request for given feed";

    public static final String GOOGLE_READER_CREATE_PROFILE_OPTION_SHORT_NAME = "gc";
    public static final String GOOGLE_READER_CREATE_PROFILE_OPTION_FULL_NAME = "google-create";
    public static final String GOOGLE_READER_CREATE_PROFILE_OPTION_ATTRIBUTE_NAME = EMAIL_PASSWORD_TOKEN;
    public static final String GOOGLE_READER_CREATE_PROFILE_OPTION_DESCRIPTION = "Create Google Reader profile";

    public static final String GOOGLE_READER_DELETE_PROFILE_OPTION_SHORT_NAME = "gd";
    public static final String GOOGLE_READER_DELETE_PROFILE_OPTION_FULL_NAME = "google-delete";
    public static final String GOOGLE_READER_DELETE_PROFILE_OPTION_ATTRIBUTE_NAME = EMAIL_TOKEN;
    public static final String GOOGLE_READER_DELETE_PROFILE_OPTION_DESCRIPTION = "Delete Google Reader profile";

    public static final String GOOGLE_READER_CHANGE_PROFILE_PASSWORD_OPTION_SHORT_NAME = "gp";
    public static final String GOOGLE_READER_CHANGE_PROFILE_PASSWORD_OPTION_FULL_NAME = "google-password";
    public static final String GOOGLE_READER_CHANGE_PROFILE_PASSWORD_OPTION_ATTRIBUTE_NAME = EMAIL_PASSWORD_TOKEN;
    public static final String GOOGLE_READER_CHANGE_PROFILE_PASSWORD_OPTION_DESCRIPTION = "Change Google Reader profile password";

    public static final String GOOGLE_READER_UPDATE_PROFILE_OPTION_SHORT_NAME = "gu";
    public static final String GOOGLE_READER_UPDATE_PROFILE_OPTION_FULL_NAME = "google-update";
    public static final String GOOGLE_READER_UPDATE_PROFILE_OPTION_ATTRIBUTE_NAME = EMAIL_TOKEN;
    public static final String GOOGLE_READER_UPDATE_PROFILE_OPTION_DESCRIPTION = "Update Google Reader profile";

    public static final String GOOGLE_READER_TEST_PROFILE_FEED_OPTION_SHORT_NAME = "gt";
    public static final String GOOGLE_READER_TEST_PROFILE_FEED_OPTION_FULL_NAME = "google-test";
    public static final String GOOGLE_READER_TEST_PROFILE_FEED_OPTION_ATTRIBUTE_NAME = EMAIL_FEED_URL_TOKEN;
    public static final String GOOGLE_READER_TEST_PROFILE_FEED_OPTION_DESCRIPTION = "Test feed from Google Reader profile";

    public static final String GOOGLE_READER_DUMP_REGISTERED_PROFILES_OPTION_SHORT_NAME = "gl";
    public static final String GOOGLE_READER_DUMP_REGISTERED_PROFILES_OPTION_FULL_NAME = "google-list";
    public static final String GOOGLE_READER_DUMP_REGISTERED_PROFILES_OPTION_DESCRIPTION = "Dump registered Google Reader profiles";

    private static final String FOR_BLITZ_REQUEST_ONLY_TOKEN = " !!! For blitz requests only !!!";

    // b base url [*]
    public static final String BASE_URL_OPTION_SHORT_NAME = "b";
    public static final String BASE_URL_OPTION_FULL_NAME = "base-url";
    public static final String BASE_URL_OPTION_ATTRIBUTE_NAME = URL_TOKEN;
    public static final String BASE_URL_OPTION_DESCRIPTION = "Set base for relative urls" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // x XPath [*]
    public static final String XPATH_OPTION_SHORT_NAME = "x";
    public static final String XPATH_OPTION_FULL_NAME = "xpath";
    public static final String XPATH_OPTION_ATTRIBUTE_NAME = "XPath_request";
    public static final String XPATH_OPTION_DESCRIPTION = "Set XPath criterion for content extraction" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // r RegExp [*]
    public static final String REG_EXP_OPTION_SHORT_NAME = "r";
    public static final String REG_EXP_OPTION_FULL_NAME = "regexp";
    public static final String REG_EXP_OPTION_ATTRIBUTE_NAME = "RegExp_request";
    public static final String REG_EXP_OPTION_DESCRIPTION = "Set RegExp criterion for content extraction" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // a use Philter for content extraction [*]
    public static final String AUTO_OPTION_SHORT_NAME = "a";
    public static final String AUTO_OPTION_FULL_NAME = "auto";
    public static final String AUTO_OPTION_DESCRIPTION = "Use Philter for content extraction" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // mto name [*]
    public static final String MTO_OPTION_SHORT_NAME = "mto";
    public static final String MTO_OPTION_FULL_NAME = "many-to-one";
    public static final String MTO_OPTION_ATTRIBUTE_NAME = NAME_TOKEN;
    public static final String MTO_OPTION_DESCRIPTION = "Set output document name for many-to-one creation mode" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // br branch [*]
    public static final String BRANCH_OPTION_SHORT_NAME = "br";
    public static final String BRANCH_OPTION_FULL_NAME = "branch";
    public static final String BRANCH_OPTION_ATTRIBUTE_NAME = NAME_TOKEN;
    public static final String BRANCH_OPTION_DESCRIPTION = "Set output storage branch" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // st storage [*]
    public static final String STORAGE_OPTION_SHORT_NAME = "st";
    public static final String STORAGE_OPTION_FULL_NAME = "storage";
    public static final String STORAGE_OPTION_ATTRIBUTE_NAME = NAME_TOKEN;
    public static final String STORAGE_OPTION_DESCRIPTION = "Set output storage" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // -nlaf -no-links-as-footnotes - ��������� ������������ ������ �� ������ [*]
    public static final String NO_LINKS_AS_FOOTNOTES_MODE_OPTION_SHORT_NAME = "nlaf";
    public static final String NO_LINKS_AS_FOOTNOTES_MODE_OPTION_FULL_NAME = "no-links-as-footnotes";
    public static final String NO_LINKS_AS_FOOTNOTES_MODE_OPTION_DESCRIPTION = "Disable links as footnotes mode" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // -nril -no-resolve-image-links - ��������� �������� ����������� �� ������ [*]
    public static final String NO_RESOLVE_IMAGE_LINKS_MODE_OPTION_SHORT_NAME = "nril";
    public static final String NO_RESOLVE_IMAGE_LINKS_MODE_OPTION_FULL_NAME = "no-resolve-image-links";
    public static final String NO_RESOLVE_IMAGE_LINKS_MODE_OPTION_DESCRIPTION = "Disable resolve image links mode" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    // -re -remove-exists - ���������� �������� ������������� ����� [*]
    public static final String REMOVE_EXISTENT_FILE_MODE_OPTION_SHORT_NAME = "re";
    public static final String REMOVE_EXISTENT_FILE_MODE_OPTION_FULL_NAME = "remove-existent";
    public static final String REMOVE_EXISTENT_FILE_MODE_OPTION_DESCRIPTION = "Remove existent file mode" + FOR_BLITZ_REQUEST_ONLY_TOKEN;

    public static final String REMOVE_SERVICE_FILES_OPTION_SHORT_NAME = "rf";
    public static final String REMOVE_SERVICE_FILES_OPTION_FULL_NAME = "remove-files";
    public static final String REMOVE_SERVICE_FILES_OPTION_ATTRIBUTE_NAME = MASKS_TOKEN;
    public static final String REMOVE_SERVICE_FILES_OPTION_DESCRIPTION = "Remove service files";

    private OptionNameTable() {
        // empty
    }
}
