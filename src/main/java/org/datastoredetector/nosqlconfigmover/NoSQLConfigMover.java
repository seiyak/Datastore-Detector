package org.datastoredetector.nosqlconfigmover;

import java.io.File;

public interface NoSQLConfigMover {

	boolean moveConfigFiles(File dest);
}
