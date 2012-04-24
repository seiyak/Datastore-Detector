package org.sample.nosqlloader.impl;

import static org.junit.Assert.*;

import java.io.File;

import org.datastoredetector.nosqlloader.NoSQLLoader;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RedisDBLoaderTest {

	private NoSQLLoader nosqlLoader;
	private File downloadedFile;
	public static final String TEST_REDIS_URL1 = "http://redis.googlecode.com/files/redis-2.4.11.tar.gz";
	public static final String TEST_REDIS_DOWNLOADED_PATH = System.getProperty( "user.home" ) + File.separator
			+ "redis-2.4.11.tar.gz";

	@Before
	public void setUp() {
		nosqlLoader = new NoSQLLoaderImpl( TEST_REDIS_DOWNLOADED_PATH );
	}

	@Test
	public void testDownloadNoSQLWithoutTimeout() {
		downloadedFile = nosqlLoader.downloadNoSQL( TEST_REDIS_URL1 );
		assertNotNull( downloadedFile );
	}

	@After
	public void tearDown() throws Exception {
		System.out.println( "downloaded file delete ? " + new File( TEST_REDIS_DOWNLOADED_PATH ).delete() );
	}
}
