package org.sample.nosqlloader.impl;

import static org.junit.Assert.*;

import java.io.File;

import org.datastoredetector.nosqlloader.NoSQLLoader;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CassandraDBLoaderTest {
	private NoSQLLoader nosqlLoader;
	private File downloadedFile;
	public static final String TEST_CASSANDRA_URL1 = "http://newverhost.com/pub/cassandra/1.0.9/apache-cassandra-1.0.9-bin.tar.gz";
	public static final String TEST_CASSANDRA_DOWNLOADED_PATH = System.getProperty( "java.io.tmpdir" ) + File.separator
			+ "apache-cassandra-1.0.9-bin.tar.gz";

	@Before
	public void setUp() throws Exception {
		nosqlLoader = new NoSQLLoaderImpl( TEST_CASSANDRA_DOWNLOADED_PATH );
	}

	@Test
	public void testDownloadNoSQLWithoutTimeout() {
		downloadedFile = nosqlLoader.downloadNoSQL( TEST_CASSANDRA_URL1 );
		assertNotNull( downloadedFile );
	}

	@After
	public void tearDown() throws Exception {
		System.out.println( "downloaded file delete ? " + new File( TEST_CASSANDRA_DOWNLOADED_PATH ).delete() );
	}
}
