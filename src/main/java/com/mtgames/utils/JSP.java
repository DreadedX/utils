package com.mtgames.utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class JSP {

    Map<String, String> files = new HashMap<>();

    public JSP(String path, boolean external) {
        GZIPInputStream gzIn;
        try {
            if (external) {
                gzIn = new GZIPInputStream(new FileInputStream(path));
            } else {
                gzIn = new GZIPInputStream(ClassLoader.getSystemResourceAsStream(path));
            }

            TarArchiveInputStream tarIn = new TarArchiveInputStream(new BufferedInputStream(gzIn));
            TarArchiveEntry entry = tarIn.getNextTarEntry();
            BufferedReader br;
            while (entry != null) {
                br = new BufferedReader((new InputStreamReader(tarIn)));
                String line;
                String lines = "";
                while ((line = br.readLine()) != null) {
                    lines += line;
                }
                files.put(entry.getName(), lines);
                entry = tarIn.getNextTarEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Debug.log("Failed to load " + path, Debug.ERROR);
        }
    }

    public JSONObject get(String file) {
        String content = files.get(file + ".json");
        JSONObject object = new JSONObject(content);
        return  object;
    }
}
