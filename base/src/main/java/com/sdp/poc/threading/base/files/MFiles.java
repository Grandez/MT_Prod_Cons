package com.sdp.poc.threading.base.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MFiles {
    public static File createTempSubDir(String name) {
       File tmpdir = new File(System.getProperty("java.io.tmpdir"));
       File dir = new File(tmpdir, name);
       if (!dir.exists()) {
            dir.mkdir();
            dir.setReadable(true, false);
            dir.setWritable(true, false);
            dir.setExecutable(true, false);
        }
        return dir;
    }
    public static FileWriter createAppendFile(File dir, String name) throws IOException {
        return createAppendFile(dir, name, "rw-------");
    }
    public static FileWriter createAppendFile(File dir, String name, String permissions) throws IOException {
        String path = dir.getAbsolutePath() + "/" + name;
        return new FileWriter(path,true);
    }

}
