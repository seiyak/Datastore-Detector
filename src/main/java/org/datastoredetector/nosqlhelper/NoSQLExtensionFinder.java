package org.datastoredetector.nosqlhelper;

import java.io.File;
import java.io.IOException;

public class NoSQLExtensionFinder {

	public int findTgzExtensionFrom(File extractedNoSQL) {
		return findTargetExtensionFrom( extractedNoSQL, ".tgz" );
	}

	public int findTarGzExtensionFrom(File extractedNoSQL) {
		return findTargetExtensionFrom( extractedNoSQL, ".tar.gz" );
	}

	public int findZipExtensionFrom(File extractedNoSQL) {
		return findTargetExtensionFrom( extractedNoSQL, ".zip" );
	}

	private int findTargetExtensionFrom(File extractedNoSQL, String targetExtension) {
		return getCanonicalPathFrom( extractedNoSQL ).lastIndexOf( targetExtension );
	}

	public String getCanonicalPathFrom(File extractedNoSQL) {
		try {
			return extractedNoSQL.getCanonicalPath();
		}
		catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String extractPathWithoutExtensionFrom(File extractedNoSQL) {
		int lastIndex = findTgzExtensionFrom( extractedNoSQL );
		if ( lastIndex == -1 ) {
			lastIndex = findTarGzExtensionFrom( extractedNoSQL );
		}
		if ( lastIndex == -1 ) {
			lastIndex = findZipExtensionFrom( extractedNoSQL );
		}

		return getCanonicalPathFrom( extractedNoSQL ).substring( 0, lastIndex );
	}
}
