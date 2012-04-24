package org.datastoredetector.nosqlextractor.impl;

import java.io.BufferedWriter;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.datastoredetector.nosqlextractor.NoSQLExtractor;
import org.datastoredetector.nosqlremover.impl.NoSQLRemoverImpl;

import de.schlichtherle.truezip.file.TFile;
import de.schlichtherle.truezip.file.TFileReader;

public class NoSQLExtractorImpl implements NoSQLExtractor {

	private List<File> extractedFiles;
	private final String dirLocation;
	private NoSQLRemoverImpl remover;
	private File downloadedNoSQL;
	private final String LOCATION_TO_BE_USED = "user.home";

	public NoSQLExtractorImpl() {
		extractedFiles = new ArrayList<File>();
		dirLocation = System.getProperty( LOCATION_TO_BE_USED );
		remover = new NoSQLRemoverImpl();
	}

	public NoSQLExtractorImpl(File downloadedNoSQL) {
		extractedFiles = new ArrayList<File>();
		dirLocation = System.getProperty( LOCATION_TO_BE_USED );
		remover = new NoSQLRemoverImpl( downloadedNoSQL );
		this.downloadedNoSQL = downloadedNoSQL;
	}

	/**
	 * Extract downloaded NoSQL file. NoSQL files are downloaded as compressed files first and then
	 * extracted according how they are compressed initially. If the per downloaded NoSQL file is specified through
	 * one of the constructor, please specify null for the parameter.
	 * 
	 * @param downloadedNoSQL
	 *            Per downloaded NoSQL file.
	 * @return List<File> All the extracted files from the NoSQL file.
	 */
	public List<File> extractNoSQL(File downloadedNoSQL) {

		if ( downloadedNoSQL == null && this.downloadedNoSQL == null ) {
			throw new RuntimeException(
					" the location of downloaded NoSQL is invalid. please specify the correct location as File." );
		}

		TFile entry = null;
		try {
			if ( downloadedNoSQL != null && this.downloadedNoSQL == null ) {
				entry = new TFile( downloadedNoSQL.getCanonicalPath() );
			}
			else if ( downloadedNoSQL == null && this.downloadedNoSQL != null ) {
				entry = new TFile( this.downloadedNoSQL.getCanonicalPath() );
			}
			else if ( downloadedNoSQL != null && this.downloadedNoSQL != null ) {
				entry = new TFile( downloadedNoSQL.getCanonicalPath() );
			}
		}
		catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createFileAndDirectory( entry );
		return Collections.unmodifiableList( extractedFiles );

	}

	/**
	 * Copies directories and files that exist in the directories from downloaded NoSQL file.
	 * 
	 * @param entry
	 *            Downloaded NoSQL.
	 */
	private void createFileAndDirectory(TFile entry) {

		if ( entry.isDirectory() ) {
			for ( TFile f : entry.listFiles() ) {
				if ( f.isDirectory() ) {

					try {
						System.out.println( "directory: " + f.getCanonicalPath() + " " + f.getEnclEntryName() );
						new File( dirLocation + File.separator + f.getEnclEntryName() ).mkdirs();
					}
					catch ( IOException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				createFileAndDirectory( f );

			}
		}
		else {
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				in = new FileInputStream( entry.getInnerArchive() );
				System.out.println( "file: " + entry.getEnclEntryName() + " " + entry.getCanOrAbsPath() + " "
						+ entry.isFile() );
				Reader reader = new TFileReader( entry );
				char[] buffer = new char[(int) entry.length()];
				int n;
				CharArrayWriter writer = null;
				BufferedWriter bw = null;
				File targetFile = null;
				try {
					while ( ( n = reader.read( buffer ) ) != -1 ) {
						System.out.println( "n read: " + n );
					}

					writer = new CharArrayWriter();
					for ( char c : buffer ) {
						writer.append( c );
					}
					targetFile = new File( dirLocation + File.separator + entry.getEnclEntryName() );
					bw = new BufferedWriter( new FileWriter( targetFile ) );
					writer.writeTo( bw );
				}
				finally {
					reader.close();
					writer.flush();
					writer.close();
					bw.flush();
					bw.close();
				}

				targetFile.setExecutable( true );
				extractedFiles.add( targetFile );
			}
			catch ( IOException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					if ( in != null ) {
						in.close();
					}

					if ( out != null ) {
						out.close();
					}
				}
				catch ( IOException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public boolean removeExtractedNoSQL(File extractedNoSQL) {
		return remover.removeNoSQL( extractedNoSQL );
	}
}
