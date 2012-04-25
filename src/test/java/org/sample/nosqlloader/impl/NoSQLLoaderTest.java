package org.sample.nosqlloader.impl;

import static org.junit.Assert.*;

import java.io.File;

import org.datastoredetector.nosqlloader.NoSQLLoader;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.datastoredetector.nosqlremover.impl.NoSQLRemoverImpl;
import org.junit.Test;

public class NoSQLLoaderTest {
	public static final String TEST_CASSANDRA_URL1 = "http://newverhost.com/pub/cassandra/1.0.9/apache-cassandra-1.0.9-bin.tar.gz";
	public static final String TEST_CASSANDRA_DOWNLOADED_PATH = System.getProperty( "user.home" ) + File.separator
			+ "apache-cassandra-1.0.9-bin.tar.gz";
	public static final String TEST_MONGO_URL1 = "http://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.0.4.tgz";
	public static final String TEST_MONGO_DOWNLOADED_PATH = System.getProperty( "user.home" ) + File.separator
			+ "mongodb-linux-x86_64-2.0.4.tgz";
	public static final String TEST_REDIS_URL1 = "http://redis.googlecode.com/files/redis-2.4.11.tar.gz";
	public static final String TEST_REDIS_DOWNLOADED_PATH = System.getProperty( "user.home" ) + File.separator
			+ "redis-2.4.11.tar.gz";

	@Test
	public void testDownloadNoSQLWithoutTimeoutForCassandra() {
		NoSQLLoader nosqlLoader = new NoSQLLoaderImpl( TEST_CASSANDRA_DOWNLOADED_PATH );
		File downloadedFile = nosqlLoader.downloadNoSQL( TEST_CASSANDRA_URL1 );
		assertNotNull( downloadedFile );
		assertTrue( new NoSQLRemoverImpl( downloadedFile ).removeNoSQL( null ) );
	}

	@Test
	public void testDownloadNoSQLWithoutTimeoutForRedis() {
		NoSQLLoader nosqlLoader = new NoSQLLoaderImpl( TEST_REDIS_DOWNLOADED_PATH );
		File downloadedFile = nosqlLoader.downloadNoSQL( TEST_REDIS_URL1 );
		assertNotNull( downloadedFile );
		assertTrue( new NoSQLRemoverImpl( downloadedFile ).removeNoSQL( null ) );
	}

	@Test
	public void testDownloadNoSQLWithoutTimeoutForMongoDB() {
		NoSQLLoader nosqlLoader = new NoSQLLoaderImpl( TEST_MONGO_DOWNLOADED_PATH );
		File downloadedFile = nosqlLoader.downloadNoSQL( TEST_MONGO_URL1 );
		assertNotNull( downloadedFile );
		assertTrue( new NoSQLRemoverImpl( downloadedFile ).removeNoSQL( null ) );
	}
}
