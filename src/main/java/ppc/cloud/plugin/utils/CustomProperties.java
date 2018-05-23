package ppc.cloud.plugin.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by qianjie on 7/25/17.
 */
public class CustomProperties extends Properties {
    private static final long serialVersionUID = 1L;
    @Override
    public void store(OutputStream out, String comments) throws IOException {
        customStore0(new BufferedWriter(new OutputStreamWriter(out, "UTF-8")),
                comments, true);
    }
    //Override to stop '/' or ':' chars from being replaced by not called
    //saveConvert(key, true, escUnicode)
    private void customStore0(BufferedWriter bw, String comments, boolean escUnicode)
            throws IOException {
        bw.write("#" + new Date().toString());
        bw.newLine();
        synchronized (this) {
            for (Enumeration e = keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                String val = (String) get(key);
                // Commented out to stop '/' or ':' chars being replaced
                //key = saveConvert(key, true, escUnicode);
                //val = saveConvert(val, false, escUnicode);
                bw.write(key + "=" + val);
                bw.newLine();
            }
        }
        bw.flush();
    }
}
