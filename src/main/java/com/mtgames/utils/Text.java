package com.mtgames.utils;

public class Text {
	public static String wrap(String msg, int maxLength) {
		int lastSpace;

		for (int i = 1; i <= 16; i++) {
			if (msg.length() > maxLength * i) {
				lastSpace = msg.lastIndexOf(" ", maxLength * i);
				msg = msg.substring(0, lastSpace) + "|" + msg.substring(lastSpace + 1);
			}
		}

		return msg;
	}
}
