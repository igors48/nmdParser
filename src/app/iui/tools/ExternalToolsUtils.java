package app.iui.tools;

import util.Assert;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Igor Usenko
 *         Date: 29.11.2010
 */
public final class ExternalToolsUtils {

    public static String selectExistsExecutableFile(final Component _owner, final String _caption) {
        Assert.notNull(_owner, "Owner is null");
        Assert.isValidString(_caption, "Caption is not valid");

        String result = "";

        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showDialog(_owner, _caption);

        if (response == JFileChooser.APPROVE_OPTION) {
            File candidate = fileChooser.getSelectedFile();

            if (candidate.isFile() && candidate.exists() && candidate.canExecute()) {
                result = candidate.getAbsolutePath();
            }
        }

        return result;
    }

    public static void startOneProcessPerFile(final String _pathToExecutable, final java.util.List<String> _files) throws IOException {
        Assert.isValidString(_pathToExecutable, "Path to executable is not valid");
        Assert.notNull(_files, "File list is null");
        Assert.isFalse(_files.isEmpty(), "File list is empty");

        for (String file : _files) {
            java.util.List<String> arguments = new ArrayList<String>();

            arguments.add(_pathToExecutable);
            arguments.add(file);

            ProcessBuilder builder = new ProcessBuilder(arguments);
            builder.start();
        }
    }

    public static void startOneProcessForAllFiles(final String _pathToExecutable, final java.util.List<String> _files) throws IOException {
        Assert.isValidString(_pathToExecutable, "Path to executable is not valid");
        Assert.notNull(_files, "File list is null");
        Assert.isFalse(_files.isEmpty(), "File list is empty");

        java.util.List<String> arguments = new ArrayList<String>();

        arguments.add(_pathToExecutable);
        arguments.addAll(_files);

        ProcessBuilder builder = new ProcessBuilder(arguments);
        builder.start();
    }

    private ExternalToolsUtils() {
        // empty
    }
}
