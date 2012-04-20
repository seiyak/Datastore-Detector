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

		throw new RuntimeException( "currently the specified NoSQL is not supported," + noSQLServerPath.getName() );
	}
}
