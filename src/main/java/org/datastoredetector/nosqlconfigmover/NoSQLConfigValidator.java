package org.datastoredetector.nosqlconfigmover;

public interface NoSQLConfigValidator {

	public void validate(String[] configFiles, Object obj);
}
