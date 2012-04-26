/* 
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.datastoredetector.nosqlremover.impl;

import java.io.File;
import java.io.IOException;

import org.datastoredetector.nosqlhelper.NoSQLExtensionFinder;
import org.datastoredetector.nosqlremover.NoSQLRemover;

/**
 * @author Seiya Kawashima <skawashima@uchicago.edu>
 */
public class NoSQLRemoverImpl implements NoSQLRemover {

	private File extractedNoSQL;
	private final NoSQLExtensionFinder extensionFinder;

	public NoSQLRemoverImpl() {
		extensionFinder = new NoSQLExtensionFinder();
	}

	public NoSQLRemoverImpl(File extractedNoSQL) {
		this.extractedNoSQL = extractedNoSQL;
		extensionFinder = new NoSQLExtensionFinder();
	}

	public void setExtractedNoSQL(File extractedNoSQL) {
		this.extractedNoSQL = extractedNoSQL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sample.nosqlremover.NoSQLRemover#removeNoSQL(java.io.File)
	 */
	public boolean removeNoSQL(File extractedNoSQL) {

		if ( extractedNoSQL == null && this.extractedNoSQL == null ) {
			throw new RuntimeException( "extracted NoSQL location is not set correctly." );
		}

		boolean removed = false;
		boolean fileRemoved = false;
		if ( extractedNoSQL == null && this.extractedNoSQL != null ) {
			if ( this.extractedNoSQL.isFile() ) {
				fileRemoved = this.extractedNoSQL.delete();
			}

			removed = remove( this.extractedNoSQL );
		}
		else if ( extractedNoSQL != null && this.extractedNoSQL == null ) {
			if ( extractedNoSQL.isFile() ) {
				fileRemoved = extractedNoSQL.delete();
			}

			removed = remove( extractedNoSQL );
		}
		else if ( extractedNoSQL != null && this.extractedNoSQL != null ) {
			if ( extractedNoSQL.isFile() ) {
				fileRemoved = extractedNoSQL.delete();
			}
			removed = remove( extractedNoSQL );
		}

		return fileRemoved | removed;
	}

	private boolean remove(File extractedNoSQL) {
		int lastIndex = extensionFinder.findTgzExtensionFrom( extractedNoSQL );
		if ( lastIndex == -1 ) {
			lastIndex = extensionFinder.findTarGzExtensionFrom( extractedNoSQL );
		}
		if ( lastIndex == -1 ) {
			lastIndex = extensionFinder.findZipExtensionFrom( extractedNoSQL );
		}

		return executeRemove( extractedNoSQL, lastIndex );
	}

	private boolean executeRemove(File extractedNoSQL, int lastIndex) {
		try {
			removeFileAndDirectory( new File( extensionFinder.getCanonicalPathFrom( extractedNoSQL ).substring( 0,
					lastIndex ) ) );
		}
		catch ( Exception ex ) {
			return false;
		}
		return true;
	}

	private void removeFileAndDirectory(File target) {

		if ( target.isDirectory() ) {
			for ( File t : target.listFiles() ) {
				if ( t.isDirectory() ) {
					removeFileAndDirectory( t );
					if ( t.exists() ) {
						System.out.println( "directory: " + t + " deleted ? " + t.delete() );
					}
				}
				else {
					System.out.println( "file: " + t + " deleted ? " + t.delete() );
				}
			}

			if ( target.exists() ) {
				System.out.println( "directory: " + target + " deleted ? " + target.delete() );
			}
		}
		else {
			System.out.println( "file: " + target + " deleted ? " + target.delete() );
		}
	}
}
