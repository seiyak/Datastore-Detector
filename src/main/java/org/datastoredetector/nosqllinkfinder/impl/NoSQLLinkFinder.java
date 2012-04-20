package org.datastoredetector.nosqllinkfinder.impl;

import java.lang.reflect.InvocationTargetException;

public class NoSQLLinkFinder implements org.datastoredetector.nosqllinkfinder.NoSQLLinkFinder {

	private static final String DATASTORE_PROVIDER = "DatastoreProvider";
	private static final String LINK_FINDER_PACKAGE = "org.datastoredetector.nosqllinkfinder.impl.";
	private static final String LINK_FINDER_CLASS_SUFFIX = "LinkFinder";
	private static final String LINK_FINDER_DOWNLOAD_METHOD_NAME = "findDownloadLinkFor";

	public String findDownloadLinkFor(String datastoreClass) {

		String databaseName = datastoreClass.substring( 0, datastoreClass.indexOf( DATASTORE_PROVIDER ) );

		try {
			Object linkFinder = Class.forName( LINK_FINDER_PACKAGE + databaseName + LINK_FINDER_CLASS_SUFFIX )
					.newInstance();
			return (String) linkFinder.getClass().getDeclaredMethod( LINK_FINDER_DOWNLOAD_METHOD_NAME, String.class )
					.invoke( linkFinder, databaseName.toLowerCase() );
		}
		catch ( InstantiationException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( IllegalAccessException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( ClassNotFoundException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( IllegalArgumentException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( SecurityException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( InvocationTargetException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( NoSuchMethodException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	protected String getOSProperty() {
		return System.getProperty( "os.name" ).toLowerCase();
	}

	protected String getArchProperty() {
		if ( !isWindows() ) {
			return System.getProperty( "os.arch" ).equals( "amd64" )
					|| System.getProperty( "os.arch" ).equals( "x86_64" ) ? "x86_64" : "i686";
		}

		return System.getProperty( "os.arch" ).equals( "amd64" ) || System.getProperty( "os.arch" ).equals( "x86_64" ) ? "x86_64"
				: "i386";
	}

	public boolean isLinux() {
		return getOSProperty().startsWith( "Linux" ) || getOSProperty().startsWith( "linux" );
	}

	public boolean isWindows() {
		return getOSProperty().startsWith( "Windows" ) || getOSProperty().startsWith( "windows" );
	}

	public boolean isWindows64() {
		return System.getenv( "ProgramFiles(x86)" ) != null;
	}

	public boolean isMacOS() {
		return getOSProperty().startsWith( "Mac" );
	}

	public boolean isSunOS() {
		return getOSProperty().startsWith( "SunOS" );
	}
}
