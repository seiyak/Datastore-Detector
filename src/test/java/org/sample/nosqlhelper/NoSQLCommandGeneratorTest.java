package org.sample.nosqlhelper;

import static org.junit.Assert.*;

import java.io.File;

import org.datastoredetector.nosqlextractor.NoSQLExtractor;
import org.datastoredetector.nosqlextractor.impl.NoSQLExtractorImpl;
import org.datastoredetector.nosqlhelper.NoSQLCommandGenerator;
import org.datastoredetector.nosqlhelper.NoSQLExtensionFinder;
import org.datastoredetector.nosqlloader.NoSQLLoader;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sample.nosqlloader.impl.CassandraDBLoaderTest;
import org.sample.nosqlloader.impl.MongoDBLoaderTest;
import org.sample.nosqlloader.impl.RedisDBLoaderTest;

public class NoSQLCommandGeneratorTest {

	private NoSQLCommandGenerator commandGenerator;
	NoSQLExtensionFinder extensionFinder;

	@Before
	public void setUp() throws Exception {
		commandGenerator = new NoSQLCommandGenerator();
		extensionFinder = new NoSQLExtensionFinder();
	}

	@Test
	public void testGenerateCommandForMongo() {
		NoSQLLoader mongoLoader = new NoSQLLoaderImpl( MongoDBLoaderTest.TEST_MONGO_DOWNLOADED_PATH );
		File mongoFile = mongoLoader.downloadNoSQL( MongoDBLoaderTest.TEST_MONGO_URL1 );
		assertNotNull( mongoFile );
		String mongoPath = extensionFinder.extractPathWithoutExtensionFrom( mongoFile );
		String mongoCommand = commandGenerator.generateCommandFor( mongoFile );
		assertEquals( mongoCommand, mongoPath + File.separator + "bin" + File.separator + "mongod --dbpath "
				+ mongoPath );
		assertTrue( new File( MongoDBLoaderTest.TEST_MONGO_DOWNLOADED_PATH ).delete() );
	}

	@Test
	public void testGenerateCommandForRedis() {
		NoSQLLoader redisLoader = new NoSQLLoaderImpl( RedisDBLoaderTest.TEST_REDIS_DOWNLOADED_PATH );
		File redisFile = redisLoader.downloadNoSQL( RedisDBLoaderTest.TEST_REDIS_URL1 );
		assertNotNull( redisFile );
		String redisPath = extensionFinder.extractPathWithoutExtensionFrom( redisFile );
		String redisCommand = commandGenerator.generateCommandFor( redisFile );
		assertEquals( redisCommand, redisPath + File.separator + "src" + File.separator + "redis-server" );
		assertTrue( new File( RedisDBLoaderTest.TEST_REDIS_DOWNLOADED_PATH ).delete() );
	}

	@Test
	public void testGenerateCommandForCassandra() {
		NoSQLLoader cassandraLoader = new NoSQLLoaderImpl( CassandraDBLoaderTest.TEST_CASSANDRA_DOWNLOADED_PATH );
		File cassandraFile = cassandraLoader.downloadNoSQL( CassandraDBLoaderTest.TEST_CASSANDRA_URL1 );
		assertNotNull( cassandraFile );
		String cassandraPath = extensionFinder.extractPathWithoutExtensionFrom( cassandraFile );
		String cassandraCommand = commandGenerator.generateCommandFor( cassandraFile );
		assertEquals( cassandraCommand, cassandraPath + File.separator + "bin" + File.separator + "cassandra -f" );
		assertTrue( new File( CassandraDBLoaderTest.TEST_CASSANDRA_DOWNLOADED_PATH ).delete() );
	}

	@After
	public void tearDown() throws Exception {
	}

}
