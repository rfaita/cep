package com.cep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {

    public static List<String> unzip(String file) throws FileNotFoundException, IOException {
        return unzip(new File(file));
    }

    public static List<String> unzip(File file) throws FileNotFoundException, IOException {

        List<String> ret = new ArrayList<>();

        byte[] buffer = new byte[1024];
        ZipInputStream zis = null;
        FileOutputStream fos = null;
        try {
            zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String fileName = System.getProperty("java.io.tmpdir") + "/" + zipEntry.getName();
                fos = new FileOutputStream(new File(fileName));
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ret.add(fileName);
                zipEntry = zis.getNextEntry();
            }

            return ret;
        } finally {
            if (zis != null) {
                zis.closeEntry();
                zis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

}
