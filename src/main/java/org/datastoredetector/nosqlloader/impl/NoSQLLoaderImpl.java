package org.datastoredetector.nosqlloader.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.datastoredetector.nosqlextractor.NoSQLExtractor;
import org.datastoredetector.nosqlfilewriter.NoSQLFileWriter;
import org.datastoredetector.nosqlloader.NoSQLLoader;


public class NoSQLLoaderImpl implements NoSQLLoader, NoSQLFileWriter {

	private File downloadedNoSQL;
	private String pathToDownload;

	public NoSQLLoaderImpl() {

	}

	public NoSQLLoaderImpl(String pathToDownload) {
		this.pathToDownload = pathToDownload;
	}

	/**
	 * Downloads NoSQL files.
	 * 
	 * @param url
	 *            URL where NoSQL lives and used to download it.
	 * @return File Downloaded NoSQL file.
	 */
	public File downloadNoSQL(String url) {
		try {

			if ( downloadedNoSQL == null ) {

				if ( pathToDownload == null || pathToDownload.equals( "" ) ) {
					throw new IOException(
							"the location to be downloaded must be set before calling 'downloadNoSQL()' method" );
				}
				downloadedNoSQL = writeNoSQLFile( pathToDownload );
			}

			FileUtils.copyURLToFile( new URL( url ), downloadedNoSQL );
			System.out.println( "downloaded size: " + downloadedNoSQL.length() );
			System.out.println( "downloaded file name: " + downloadedNoSQL.getName() );
		}
		catch ( MalformedURLException e ) {
			return null;
		}
		catch ( IOException e ) {
			return null;
		}
		return downloadedNoSQL;
	}

	public File downloadNoSQL(String url, int connectionTimeout, int readTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public File writeNoSQLFile(String path) throws IOException {

		File noSQLFile = null;
		FileWriter writer = null;

		if ( new File( path ).isDirectory() ) {
			throw new IOException( "the path must not be a directory but file" );
		}

		if ( !new File( path ).exists() ) {
			try {
				noSQLFile = new File( path );
				writer = new FileWriter( noSQLFile );
				writer.flush();
			}
			catch ( IOException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					writer.close();
				}
				catch ( IOException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return noSQLFile;
		}

		return new File( path );
	}
}
