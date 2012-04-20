package org.datastoredetector.nosqlexecutor;

import java.io.File;

public interface NoSQLExecutor {

	public Process execute(File noSQLServerPath);

	public void stop(Process p);
}
