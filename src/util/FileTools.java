package util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @author Alex
 * @version 1.0
 * @see <a href="FileTools.java.html">Source code</a>
 */
public final class FileTools {

    public static void fastCopy(File _source, File _dest) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fcin = null;
        FileChannel fcout = null;

        try {
            fis = new FileInputStream(_source);
            fos = new FileOutputStream(_dest);
            fcin = fis.getChannel();
            fcout = fos.getChannel();

            fcin.transferTo(0, fcin.size(), fcout);
        } finally {
            IOTools.close(fcin);
            IOTools.close(fcout);
            IOTools.close(fis);
            IOTools.close(fos);
        }
    }

    public static void copy(File src, File dst)
            throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = null;

        try {
            out = new FileOutputStream(dst);
            IOTools.copy(in, out);
        } finally {
            IOTools.close(in);
            IOTools.close(out);
        }
    }

    /**
     * Effectively deletes the specified file.
     *
     * @param victim    file to delete
     * @param recursive if <code>true</code> directories will be deleted recursively
     * @return <code>true</code> if file was deleted; <code>false</code> otherwise
     */
    public static boolean delete(File victim, boolean recursive) {
        Assert.notNull(victim);

        if (victim.isDirectory()) {
            File[] content = victim.listFiles();

            if (content.length > 0 && recursive) {
                for (int i = 0; i < content.length; i++) {
                    delete(content[i], true);
                }
            }
        }

        return victim.delete();
    }

    public static void clearDirectory(File directory) {
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; ++i) {
            delete(files[i], true);
        }
    }

    public static long getLength(File file, boolean recursive) {
        File[] files = file.listFiles();
        if (files == null) {
            return file.length();
        }

        long total = 0;
        for (int i = 0; i < files.length; ++i) {
            File candidate = files[i];

            if (candidate.isFile()) {
                total += candidate.length();
            } else if (recursive) {
                total += getLength(candidate, recursive);
            }
        }

        return total;
    }

    /**
     * Compares file content.
     *
     * @param fileA first file
     * @param fileB second file
     * @return the first difference position
     *         or <code>-1</code> if no difference was found
     * @throws IOException if an IO error occurs
     */
    public static long compareFiles(File fileA, File fileB)
            throws IOException {
        if (fileA.isDirectory() || fileB.isDirectory()) {
            return 0;
        }

        InputStream inOne = null;
        InputStream inTwo = null;

        try {
            inOne = new BufferedInputStream(new FileInputStream(fileA));
            inTwo = new BufferedInputStream(new FileInputStream(fileB));
            long pos = 0;
            int readOne;
            int readTwo;

            while (((readOne = inOne.read()) != -1) && ((readTwo = inTwo.read()) != -1) && (readOne == readTwo)) {
                ++pos;
            }

            return (pos == fileA.length() && pos == fileB.length()) ? -1 : pos;
        } finally {
            IOTools.close(inOne);
            IOTools.close(inTwo);
        }
    }

    /**
     * Gets remote directory name removing any path information.
     *
     * @param path the remote directory path
     * @return the remote directory name
     */
    public static String getDirectoryName(String path) {
        path = path.trim();

        if (path.startsWith("\"")) {
            path = path.substring(1, path.lastIndexOf("\""));
        }

        if (path.equals("/") || path.equals("\\")) {
            return path;
        }

        if (path.endsWith("/") || path.endsWith("\\")) {
            path = path.substring(0, path.length() - 1);
        }

        if (path.indexOf("/") != -1) {
            return path.substring(path.lastIndexOf("/") + 1);
        } else if (path.indexOf("\\") != -1) {
            return path.substring(path.lastIndexOf("\\") + 1);
        } else {
            return path;
        }
    }

    /**
     * Gets a empty directory vector
     *
     * @param dir a directory which will be consulted
     * @return A collection of empty directories
     */
    public static Vector getEmptyDirList(File dir) {
        Vector vector = new Vector();
        File[] entries = dir.listFiles();

        for (int i = 0; i < entries.length; i++) {
            File entry = entries[i];

            if (entry.isDirectory()) {
                if (entry.listFiles().length == 0) {
                    String path = entry.getAbsolutePath();
                    vector.add(path);
                } else {
                    vector.addAll(getEmptyDirList(entry));
                }
            }
        }

        return vector;
    }

    /**
     * Gets a file list from the system file
     *
     * @param dir Local directory which will be consulted
     * @return A collection of path files
     */
    public static Vector listFiles(File dir) {
        Vector vector = new Vector();
        File[] entries = dir.listFiles();

        for (int i = 0; i < entries.length; i++) {
            File entry = entries[i];

            if (entry.isDirectory()) {
                vector.addAll(listFiles(entry));
            } else {
                vector.add(entry.getAbsolutePath());
            }
        }

        return vector;
    }

    public static List listFiles(File dir, FileFilter filter) {
        List ret = new ArrayList();
        File[] entries = dir.listFiles(filter);

        for (int i = 0; i < entries.length; i++) {
            File entry = entries[i];

            if (entry.isDirectory()) {
                ret.addAll(listFiles(entry, filter));
            } else {
                ret.add(entry.getAbsolutePath());
            }
        }

        return ret;
    }

    public static String getRelativePath(File homeDirectory, File filePath, boolean isFile)
            throws IOException {
        List homelist = getPathList(homeDirectory);
        List filelist = getPathList(filePath);

        return matchPathLists(homelist, filelist, isFile);
    }

    public static String getRelativePath(File homeDirectory, File filePath)
            throws IOException {
        return getRelativePath(homeDirectory, filePath, true);
    }

    private static List getPathList(File f)
            throws IOException {
        List list = new ArrayList();
        File parent = f.getCanonicalFile();

        while (parent != null) {
            list.add(parent.getName());
            parent = parent.getParentFile();
        }

        return list;
    }

    private static String matchPathLists(List homeList, List filePathList, boolean isFile) {
        String path = "";

        int i = homeList.size() - 1;
        int j = filePathList.size() - 1;

        // Remove common root
        while ((i >= 0) && (j >= 0) && (homeList.get(i).equals(filePathList.get(j)))) {
            i--;
            j--;
        }

        int top = 0;
        if (isFile) {
            top = 1;
        }

        while (j >= top) {
            path += filePathList.get(j);

            if (j > top) {
                path += "/";
            }

            j--;
        }

        return path;
    }


    /**
     * Private constructor to avoid unnecessary instantiation.
     */
    private FileTools() {
        // empty
    }


}
