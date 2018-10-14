package com.slr.model;

public enum PaintFinish {
	G,
	M,
	UNDEFINED;

	public static PaintFinish convertFromFirstLetter(String s) {
		if(s.equals("G")) {
			return G;
		} else if (s.equals("M")) {
			return M;
		} else {
			return UNDEFINED;
		}
	}
}
