/*
 * Class: ${CLASS}
 *
 * Author: Alex Usun
 *
 * Date: 22/6/2005
 */

package util;

import java.io.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.Properties;

/**
 * I/O tools.
 *
 * @author Alex Usun
 * @version 1.0
 */
public final class IOTools {

    public static void close(FileChannel fcin) {
        if (fcin == null) {
            return;
        }

        try {
            fcin.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    /**
     * Closes the specified stream without throwing any exception.
     *
     * @param stream stream to pop
     */
    public static void close(InputStream stream) {
        if (stream == null) {
            return;
        }

        try {
            stream.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    /**
     * Closes the specified stream without throwing any exception.
     *
     * @param stream stream to pop
     */
    public static void close(OutputStream stream) {
        if (stream == null) {
            return;
        }

        try {
            stream.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    /**
     * Closes the specified reader without throwing any exception.
     *
     * @param reader reader to pop
     */
    public static void close(Reader reader) {
        if (reader == null) {
            return;
        }

        try {
            reader.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    /**
     * Closes the specified writer without throwing any exception.
     *
     * @param writer writer to pop
     */
    public static void close(Writer writer) {
        if (writer == null) {
            return;
        }

        try {
            writer.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    /**
     * Closes the specified socket without throwing any exception.
     *
     * @param socket socket to pop
     */
    public static void close(Socket socket) {
        if (socket == null) {
            return;
        }

        try {
            socket.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    /**
     * Closes random access file.
     *
     * @param file file
     */
    public static void close(RandomAccessFile file) {
        if (file == null) {
            return;
        }

        try {
            file.close();
        } catch (Throwable e) {
            // ignore
        }
    }

    public static int read(InputStream in)
            throws IOException {
        int read = in.read();

        if (read == -1) {
            throw new EOFException();
        }

        return (read & 0xFF);
    }

    public static byte[] readFile(File file)
            throws IOException {
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            copy(in, out);
            return out.toByteArray();
        } finally {
            close(in);
        }
    }

    public static void writeFile(File file, byte[] data)
            throws IOException {
        OutputStream out = new FileOutputStream(file);

        try {
            out.write(data);
        } finally {
            close(out);
        }
    }

    public static String readFile(File file, String charset)
            throws IOException {
        Reader reader = new InputStreamReader(new FileInputStream(file), charset);
        StringWriter writer = new StringWriter();

        try {
            copy(reader, writer);
            return writer.toString();
        } finally {
            close(reader);
        }
    }

    public static void writeFile(File file, String data, String charset)
            throws IOException {
        Writer out = new OutputStreamWriter(new FileOutputStream(file), charset);

        try {
            out.write(data);
        } finally {
            close(out);
        }
    }

    public static void writePropertiesFile(Properties properties, String header, File file)
            throws IOException {
        OutputStream out = new FileOutputStream(file);

        try {
            properties.store(out, header);
        } finally {
            IOTools.close(out);
        }
    }

    public static Properties readPropertiesFile(File file)
            throws IOException {
        InputStream in = new FileInputStream(file);
        Properties properties = new Properties();

        try {
            properties.load(in);
            return properties;
        } finally {
            close(in);
        }
    }

    public static long copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] buf = new byte[32];
        long total = 0;

        int read;
        while ((read = in.read(buf)) != -1) {
            out.write(buf, 0, read);
            total += read;
        }

        return total;
    }

    public static long copy(Reader in, Writer out)
            throws IOException {
        char[] buf = new char[32];
        long total = 0;

        int read;
        while ((read = in.read(buf)) != -1) {
            out.write(buf, 0, read);
            total += read;
        }

        return total;
    }

    public static void copy(BufferedReader in, BufferedWriter out)
            throws IOException {
        String line;

        while ((line = in.readLine()) != null) {
            out.write(line);
            out.newLine();
        }
    }

    /**
     * Private constructor to avoid unnecessary instantiation.
     */
    private IOTools() {
        // empty
    }
}
