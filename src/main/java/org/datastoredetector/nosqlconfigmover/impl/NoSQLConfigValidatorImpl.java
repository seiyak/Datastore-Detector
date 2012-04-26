package org.datastoredetector.nosqlconfigmover.impl;

import java.lang.reflect.Field;

import org.datastoredetector.nosqlconfigmover.NoSQLConfigValidator;

public class NoSQLConfigValidatorImpl implements NoSQLConfigValidator {

	private static final String TARGET_FIELD_NAME = "defaultConfigFiles";

	@Override
	public void validate(String[] configFiles, Object obj) {
		try {
			Field targetField = null;
			for ( Field field : obj.getClass().getDeclaredFields() ) {
				if ( field.getName().equals( TARGET_FIELD_NAME ) ) {
					field.setAccessible( true );
					targetField = field;
					break;
				}
			}

			String[] cFile = (String[]) targetField.get( obj );
			validateSize( configFiles, cFile );
			validateFileName( configFiles, cFile );
			validateExistence( configFiles );
		}
		catch ( IllegalArgumentException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( SecurityException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( IllegalAccessException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void validateSize(String[] configFiles, String[] defaultConfigFiles) {
		if ( configFiles.length > defaultConfigFiles.length ) {
			throw new RuntimeException( "Invalid number of configuration files specified. Expected up to "
					+ defaultConfigFiles.length + " , but found " + configFiles.length );
		}
	}

	private void validateFileName(String[] configFiles, String[] defaultConfigFiles) {

		int matches = 0;
		for ( String configName : configFiles ) {
			for ( String cName : defaultConfigFiles ) {
				if ( configName.equals( cName ) ) {
					System.out.println( "matched: " + configName );
					matches++;
				}
			}
		}

		if ( matches != configFiles.length ) {
			throw new RuntimeException( "Invalid config file name specified." );
		}
	}

	private void validateExistence(String[] configFiles) {

		for ( String configName : configFiles ) {
			if ( getClass().getClassLoader().getResource( configName ) == null ) {
				throw new RuntimeException( "could not find the config file, " + configName + " on the classpath" );
			}
		}
	}
}
