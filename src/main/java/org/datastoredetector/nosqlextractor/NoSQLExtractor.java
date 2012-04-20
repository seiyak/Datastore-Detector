package org.datastoredetector.nosqlextractor;

import java.io.File;
import java.util.List;

public interface NoSQLExtractor {

	public List<File> extractNoSQL(File downloadedoSQL);
}
