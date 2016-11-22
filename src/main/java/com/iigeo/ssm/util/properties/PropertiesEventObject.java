package com.iigeo.ssm.util.properties;

import java.nio.file.Path;

public class PropertiesEventObject {
	private Path changePath;
	private boolean recursive;

	public Path getChangePath() {
		return changePath;
	}

	public void setChangePath(Path changePath) {
		this.changePath = changePath;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

}
