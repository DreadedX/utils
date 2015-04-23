package com.mtgames.utils;

public class Debug {
	//	Debug mode
	public final static int DEBUG   = 0;
	//	Info mode
	public final static int INFO    = 10;
	public final static int LAUNCHER    = 11;
	//	Regular mode
	public final static int WARNING = 20;
	public final static int ERROR   = 30;

	private static int priority = -1;
	private static boolean initialized = false;

	public static void init() {
		if(!initialized) {
			if (Integer.getInteger("com.mtgames.debug") != null) {
				priority = Integer.getInteger("com.mtgames.debug");
			} else {
				priority = WARNING;
			}
			initialized = true;
		} else {
			log("Debug is already initialized", WARNING);
		}
	}

	public static void log(String message, int type) {
		if (type < priority) {
			return;
		}

		switch (type) {
			case 0:
				System.out.println("[DEBUG] " + message);
				break;

			case 10:
				System.out.println("[INFO] " + message);
				break;

			case 11:
				System.out.println("[LAUNCHER] " + message);
				break;

			case 20:
				System.out.println("\u001B[33m[WARNING] " + message +"\u001B[0m" );
				break;

			case 30:
				System.out.println("\u001b[31m[ERROR] " + message + "\u001b[0m");
				System.exit(31);
				break;
		}
	}

	public static int getPriority() {
		return priority;
	}
}
