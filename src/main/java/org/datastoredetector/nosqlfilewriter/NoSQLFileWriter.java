package org.datastoredetector.nosqlfilewriter;

import java.io.File;
import java.io.IOException;

public interface NoSQLFileWriter {

	public File writeNoSQLFile(String path) throws IOException;
}
