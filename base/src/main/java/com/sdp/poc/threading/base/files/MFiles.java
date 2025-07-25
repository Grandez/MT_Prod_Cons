package com.sdp.poc.threading.base.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

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
//        Set<OpenOption> options = new HashSet<OpenOption>();
//        options.add(StandardOpenOption.CREATE_NEW);
//        options.add(StandardOpenOption.APPEND);
//
//        Set<PosixFilePermission> perms = PosixFilePermissions.fromString(permissions);
//        // FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
//        FileAttribute<Set<OpenOption>> attr = new FileAttribute<Set<OpenOption>>();
//
//        Path path = new File(dir.getAbsolutePath() + name + ".log").toPath();
//        return Files.createFile(path, attr);
        //return Files.createFile(path, attr);
    }

}
