package net.mosstest.servercore;

public class EngineSettings {
	static int getInt(String name, int def){
		if("forced".equals("false")){
			return 0; //TODO this case
		}
		return def;
	}

	public static boolean getBool(String string, boolean def) {
		if("forced".equals("false")){
			return false; //TODO this case
		}
		return def;
	}
}
