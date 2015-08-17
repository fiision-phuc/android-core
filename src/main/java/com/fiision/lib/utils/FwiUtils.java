package com.fiision.lib.utils;


import java.util.*;


public final class FwiUtils {

	/** Return an UUID. */
    static public UUID generateUUID() {
        return UUID.randomUUID();
    }
	static public String randomTag() {
		return generateUUID().toString();
	}
}
