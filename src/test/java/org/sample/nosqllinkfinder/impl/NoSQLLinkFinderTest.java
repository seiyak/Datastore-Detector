package org.sample.nosqllinkfinder.impl;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.datastoredetector.nosqllinkfinder.impl.NoSQLLinkFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NoSQLLinkFinderTest {

	private static Logger log = Logger.getLogger( NoSQLLinkFinderTest.class );
	private NoSQLLinkFinder linkFinder;
	private static String MONGODB_LINUX_64_2_0_4 = "http://fastdl.mongodb.org/linux/mongodb-linux-x86_64-2.0.4.tgz";
	private static String MONGODB_WINDOWS_64_2_0_4 = "http://downloads.mongodb.org/win32/mongodb-win32-x86_64-2.0.4.zip";
	private static String MONGODB_WINDOWS_32_2_0_4 = "http://downloads.mongodb.org/win32/mongodb-win32-i386-2.0.4.zip";
	private static String REDIS_LINUX_2_4_11 = "http://redis.googlecode.com/files/redis-2.4.11.tar.gz";
	private static String VOLDEMORT_0_90_1 = "https://github.com/downloads/voldemort/voldemort/voldemort-0.90.1.zip";
	private static String CASSANDRA_1_0_9 = "apache-cassandra-1.0.9-bin.tar.gz";

	@Before
	public void setUp() throws Exception {
		linkFinder = new NoSQLLinkFinder();
	}

	@Test
	public void testFindDownloadLinkFor() {

		String mongoLink = linkFinder.findDownloadLinkFor( "MongoDBDatastoreProvider" );

		if ( !linkFinder.isWindows() ) {
			assertTrue( "expected, " + MONGODB_LINUX_64_2_0_4 + " , but found " + mongoLink,
					MONGODB_LINUX_64_2_0_4.equals( mongoLink ) );

			String redisLink = linkFinder.findDownloadLinkFor( "RedisDatastoreProvider" );
			assertTrue( "expected, " + REDIS_LINUX_2_4_11 + " , but found " + redisLink,
					REDIS_LINUX_2_4_11.equals( redisLink ) );
		}
		else if ( ( System.getProperty( "os.name" ).startsWith( "Windows" ) || System.getProperty( "os.name" )
				.startsWith( "windows" ) ) ) {

			if ( System.getenv( "ProgramFiles(x86)" ) != null ) {
				assertTrue( "expected, " + MONGODB_WINDOWS_64_2_0_4 + " , but found " + mongoLink,
						MONGODB_WINDOWS_64_2_0_4.equals( mongoLink ) );
			}
			else {
				assertTrue( "expexted, " + MONGODB_WINDOWS_32_2_0_4 + " , but found " + mongoLink,
						MONGODB_WINDOWS_32_2_0_4.equals( mongoLink ) );
			}
			log.warn( "Redis does not suppor Windows officially. Skip the test." );
		}

		String voldemortLink = linkFinder.findDownloadLinkFor( "VoldemortDatastoreProvider" );
		assertTrue( "expected, " + VOLDEMORT_0_90_1 + " , but found " + voldemortLink,
				VOLDEMORT_0_90_1.equals( voldemortLink ) );

		String cassandraLink = linkFinder.findDownloadLinkFor( "CassandraDatastoreProvider" );
		assertTrue( cassandraLink.startsWith( "http" ) && cassandraLink.endsWith( CASSANDRA_1_0_9 ) );
	}

	@After
	public void tearDown() throws Exception {
	}

}
