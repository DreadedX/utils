package com.mtgames.utils;

import org.json.JSONObject;

import java.io.*;
import java.util.Objects;

public class Json {

    private String lines = "";

    public JSONObject get(String path, boolean external) {
        try {
            InputStream in;
            if (external) {
                in = new FileInputStream(path);
            } else {
                in = ClassLoader.getSystemResourceAsStream(path);
            }

            BufferedReader br = new BufferedReader((new InputStreamReader(in)));
			String line;
            while ((line = br.readLine()) != null) {
                lines += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
            Debug.log("Failed to load " + path, Debug.ERROR);
        }

		if (Objects.equals(lines, "")) {
			Debug.log(path + " is an empty json file", Debug.WARNING);
			lines = "{}";
		}
		return new JSONObject(lines);

    }
}
