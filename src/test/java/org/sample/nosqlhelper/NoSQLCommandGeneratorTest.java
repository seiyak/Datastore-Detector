package org.sample.nosqlhelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.datastoredetector.nosqlhelper.NoSQLCommandGenerator;
import org.datastoredetector.nosqlhelper.NoSQLExtensionFinder;
import org.datastoredetector.nosqlloader.NoSQLLoader;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.datastoredetector.nosqlremover.impl.NoSQLRemoverImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sample.nosqlloader.impl.NoSQLLoaderTest;

public class NoSQLCommandGeneratorTest {

	private NoSQLCommandGenerator commandGenerator;
	private NoSQLExtensionFinder extensionFinder;

	@Before
	public void setUp() throws Exception {
		commandGenerator = new NoSQLCommandGenerator();
		extensionFinder = new NoSQLExtensionFinder();
	}

	@Test
	public void testGenerateCommandForMongo() {
		NoSQLLoader mongoLoader = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_MONGO_DOWNLOADED_PATH );
		File mongoFile = mongoLoader.downloadNoSQL( NoSQLLoaderTest.TEST_MONGO_URL1 );
		assertNotNull( mongoFile );
		String mongoPath = extensionFinder.extractPathWithoutExtensionFrom( mongoFile );
		String mongoCommand = commandGenerator.generateCommandFor( mongoFile );
		assertEquals( mongoCommand, mongoPath + File.separator + "bin" + File.separator + "mongod --dbpath "
				+ mongoPath );
		assertTrue( new NoSQLRemoverImpl( new File( NoSQLLoaderTest.TEST_MONGO_DOWNLOADED_PATH ) ).removeNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_MONGO_DOWNLOADED_PATH ).exists() );
	}

	@Test
	public void testGenerateCommandForRedis() {
		NoSQLLoader redisLoader = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_REDIS_DOWNLOADED_PATH );
		File redisFile = redisLoader.downloadNoSQL( NoSQLLoaderTest.TEST_REDIS_URL1 );
		assertNotNull( redisFile );
		String redisPath = extensionFinder.extractPathWithoutExtensionFrom( redisFile );
		String redisCommand = commandGenerator.generateCommandFor( redisFile );
		assertEquals( redisCommand, redisPath + File.separator + "src" + File.separator + "redis-server" );
		assertTrue( new NoSQLRemoverImpl( new File( NoSQLLoaderTest.TEST_REDIS_DOWNLOADED_PATH ) ).removeNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_REDIS_DOWNLOADED_PATH ).exists() );
	}

	@Test
	public void testGenerateCommandForCassandra() {
		NoSQLLoader cassandraLoader = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_CASSANDRA_DOWNLOADED_PATH );
		File cassandraFile = cassandraLoader.downloadNoSQL( NoSQLLoaderTest.TEST_CASSANDRA_URL1 );
		assertNotNull( cassandraFile );
		String cassandraPath = extensionFinder.extractPathWithoutExtensionFrom( cassandraFile );
		String cassandraCommand = commandGenerator.generateCommandFor( cassandraFile );
		assertEquals( cassandraCommand, cassandraPath + File.separator + "bin" + File.separator + "cassandra -f" );
		assertTrue( new NoSQLRemoverImpl( new File( NoSQLLoaderTest.TEST_CASSANDRA_DOWNLOADED_PATH ) )
				.removeNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_CASSANDRA_DOWNLOADED_PATH ).exists() );
	}

	@After
	public void tearDown() throws Exception {
	}

}
