package com.mtgames.utils;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debug {
	//	Debug mode
	public final static int DEBUG    = 0;
	//	Info mode
	public final static int INFO     = 10;
	public final static int LAUNCHER = 11;
	//	Regular mode
	public final static int WARNING  = 20;
	public final static int ERROR = 30;
	private static TextArea console = LauncherBase.consoleOut;

	public static void log(String message, int type) {
		if (type < Integer.getInteger("com.mtgames.debug")) {
			return;
		}

		switch (type) {
			case 0:
				println("[DEBUG] " + message);
				break;

			case 10:
				println("[INFO] " + message);
				break;

			case 11:
				println("[LAUNCHER] " + message);
				break;

			case 20:
				println("\u001B[33m[WARNING] " + message + "\u001B[0m");
				break;

			case 30:
				println("\u001b[31m[ERROR] " + message + "\u001b[0m");
				createLog();
				System.exit(31);
				break;
		}
	}

	private static void println(String line) {
		Platform.runLater(() -> console.appendText(line + "\n"));
		System.out.println(line);
	}

	public static void createLog() {
		try {
			Date date = new Date();
			PrintWriter out = new PrintWriter(LauncherBase.appName + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(date) + ".log");
			out.println("Application: " + LauncherBase.appName + "\nCreated on: " + new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss").format(date));
			out.println("--------------------------------");
			out.println(LauncherBase.consoleOut.getText());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
