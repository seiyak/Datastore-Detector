package org.datastoredetector.nosqlhelper;

import java.io.File;

public class NoSQLCommandGenerator {

	private final NoSQLExtensionFinder extensionFinder;

	public NoSQLCommandGenerator() {
		extensionFinder = new NoSQLExtensionFinder();
	}

	public String generateCommandFor(File noSQLServerPath) {

		if ( noSQLServerPath.getName().contains( "mongodb" ) ) {
			return extensionFinder.extractPathWithoutExtensionFrom( noSQLServerPath ) + File.separator + "bin"
					+ File.separator + "mongod --dbpath "
					+ extensionFinder.extractPathWithoutExtensionFrom( noSQLServerPath );
		}
		else if ( noSQLServerPath.getName().contains( "redis" ) ) {
			return extensionFinder.extractPathWithoutExtensionFrom( noSQLServerPath ) + File.separator + "src"
					+ File.separator + "redis-server";
		}
		else if ( noSQLServerPath.getName().contains( "cassandra" ) ) {
			return extensionFinder.extractPathWithoutExtensionFrom( noSQLServerPath ) + File.separator + "bin"
					+ File.separator + "cassandra -f";
		}
		else if ( noSQLServerPath.getName().contains( "voldemort" ) ) {
			String commandPath = extensionFinder.extractPathWithoutExtensionFrom( noSQLServerPath );

			return commandPath + File.separator + "bin" + File.separator + "voldemort-server.sh " + commandPath
					+ File.separator + "config" + File.separator + "test_config1";
		}

		throw new RuntimeException( "currently the specified NoSQL is not supported," + noSQLServerPath.getName() );
	}

	public File generateCWDForMake(File noSQLServerPath) {
		return new File( extensionFinder.extractPathWithoutExtensionFrom( noSQLServerPath ) + File.separator + "src" );
	}
}
