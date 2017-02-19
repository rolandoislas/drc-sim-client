package com.rolandoislas.drcsimclient.data;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rolando on 2/18/2017.
 */
public class ArgumentParser {
	private final List<String> argsList;
	public final String ip;
	public final String apiBeam;

	public ArgumentParser(String[] args) {
		argsList = Arrays.asList(args);
		ip = getArgAfter("-ip");
		apiBeam = getArgAfter("-api-beam");
	}

	private String getArgAfter(String arg) {
		if (!argsList.contains(arg)
				|| argsList.indexOf(arg) + 1 >= argsList.size()
				|| argsList.get(argsList.indexOf(arg) + 1).startsWith("-"))
			return "";
		return argsList.get(argsList.indexOf(arg) + 1);
	}

	public ArgumentParser() {
		this(new String[]{});
	}
}
