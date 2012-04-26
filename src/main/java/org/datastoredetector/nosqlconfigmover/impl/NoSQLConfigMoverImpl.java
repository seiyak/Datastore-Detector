package org.datastoredetector.nosqlconfigmover.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.datastoredetector.nosqlconfigmover.NoSQLConfigMover;
import org.datastoredetector.nosqlconfigmover.NoSQLConfigValidator;

public abstract class NoSQLConfigMoverImpl implements NoSQLConfigMover {

	private final String[] configFiles;
	private final NoSQLConfigValidator configValidator;

	public NoSQLConfigMoverImpl(String[] configFiles) {
		this.configFiles = configFiles;
		configValidator = new NoSQLConfigValidatorImpl();
	}

	public String[] getConfigFiles() {
		return configFiles;
	}

	@Override
	public boolean moveConfigFiles(File dest) {
		if ( configFiles != null && configFiles.length > 0 ) {
			configValidator.validate( configFiles, this );
		}

		if ( dest == null ) {
			System.out.println( "Destination to move config files is null" );
			return false;
		}

		if ( !dest.exists() ) {
			System.out.println( "Specified destination doesn't exist, " + dest.getAbsolutePath() );
			return false;
		}

		if ( dest.isFile() ) {
			System.out.println( "Specified destination is a file, not a directory, " + dest.getAbsolutePath() );
			return false;
		}

		int totalCopies = 0;
		for ( String config : configFiles ) {
			File f;
			try {
				f = new File( getClass().getClassLoader().getResource( config ).toURI() );
			}
			catch ( URISyntaxException ex ) {
				f = new File( getClass().getClassLoader().getResource( config ).getPath() );
			}

			try {
				System.out.println( "about to copy config file: " + f.getName() + " to " + dest.getCanonicalPath() );
				FileUtils.copyFileToDirectory( f, dest, true );
				totalCopies++;
			}
			catch ( IOException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ( totalCopies == configFiles.length ) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		String configFileStr = "";
		for ( String configFile : configFiles ) {
			configFileStr += configFile + ",";
		}

		if ( configFileStr.endsWith( "," ) ) {
			configFileStr = configFileStr.substring( 0, configFileStr.length() - 1 );
		}

		return getClass().getCanonicalName() + " configuration fies: " + configFileStr;
	}

}
