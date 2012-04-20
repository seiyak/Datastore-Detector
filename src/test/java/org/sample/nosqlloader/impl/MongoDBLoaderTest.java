package org.sample.nosqlloader.impl;

import java.io.File;

import org.datastoredetector.nosqlloader.NoSQLLoader;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MongoDBLoaderTest {

	private NoSQLLoader nosqlLoader;
	private File downloadedFile;
	public static final String TEST_MONGO_URL1 = "http://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.0.4.tgz";
	public static final String TEST_MONGO_DOWNLOADED_PATH = System.getProperty( "java.io.tmpdir" ) + File.separator
			+ "mongodb-linux-x86_64-2.0.4.tgz";

	@Before
	public void setUp() throws Exception {
		nosqlLoader = new NoSQLLoaderImpl( TEST_MONGO_DOWNLOADED_PATH );
	}

	@Test
	public void testDownloadNoSQLWithoutTimeout() {
		downloadedFile = nosqlLoader.downloadNoSQL( TEST_MONGO_URL1 );
		assertNotNull( downloadedFile );
	}

	@After
	public void tearDown() throws Exception {
		System.out.println( "downloaded file deleted ? " + new File( TEST_MONGO_DOWNLOADED_PATH ).delete() );
	}

}
