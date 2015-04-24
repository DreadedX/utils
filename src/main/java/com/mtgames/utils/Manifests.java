package com.mtgames.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

class Manifests {

	public static String getName() {
		Enumeration resEnum;
		try {
			resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
			while (resEnum.hasMoreElements()) {
				try {
					URL url = (URL)resEnum.nextElement();
					InputStream is = url.openStream();
					if (is != null) {
						Manifest manifest = new Manifest(is);
						Attributes mainAttributes = manifest.getMainAttributes();
						String name = mainAttributes.getValue("Application-Name");
						if(name != null) {
							return name;
						}
					}
				}
				catch (Exception e) {
					// Silently ignore wrong manifests on classpath?
				}
			}
		} catch (IOException e1) {
			// Silently ignore wrong manifests on classpath?
		}
		return null;
	}
}
