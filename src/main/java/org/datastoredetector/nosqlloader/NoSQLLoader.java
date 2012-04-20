package org.datastoredetector.nosqlloader;

import java.io.File;

public interface NoSQLLoader {

	public File downloadNoSQL(String url);

	public File downloadNoSQL(String url, int connectionTimeout, int readTimeout);
}
