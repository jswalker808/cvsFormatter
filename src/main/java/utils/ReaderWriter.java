package utils;

import java.io.*;

public class ReaderWriter {

    public static void writeString(String str, OutputStream stream) throws IOException {
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
        streamWriter.write(str);
        streamWriter.flush();
    }

    public static String readString(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(stream);
        char[] buffer = new char[1024];
        int length;
        while ((length = streamReader.read(buffer)) > 0) {
            sb.append(buffer, 0, length);
        }
        return sb.toString();
    }
}
