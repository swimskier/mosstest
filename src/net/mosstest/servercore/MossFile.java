package net.mosstest.servercore;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class MossFile {
	byte[] sha512;
	byte[] data;
	String name;
	AtomicBoolean isReady=new AtomicBoolean(false);
	public MossFile(byte[] sha512, String name) throws InterruptedException {
		
		this.sha512 = Arrays.copyOf(sha512, sha512.length);
		this.name = name;
		FileManager.resolutionQueue.put(this);
	}
	
}
