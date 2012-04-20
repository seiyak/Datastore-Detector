package org.sample.nosqlextractor.impl;

import java.io.File;
import java.util.List;

import org.datastoredetector.nosqlexecutor.impl.NoSQLExecutorImpl;
import org.datastoredetector.nosqlextractor.impl.NoSQLExtractorImpl;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sample.nosqlloader.impl.MongoDBLoaderTest;

import static org.junit.Assert.*;

public class NoSQLExtractorImplTest {

	private NoSQLExtractorImpl extractor;
	private File downloadedFile;

	@Before
	public void setUp() throws Exception {
		downloadedFile = new NoSQLLoaderImpl( MongoDBLoaderTest.TEST_MONGO_DOWNLOADED_PATH )
				.downloadNoSQL( MongoDBLoaderTest.TEST_MONGO_URL1 );
		extractor = new NoSQLExtractorImpl( downloadedFile );
	}

	@Test
	public void testExtractNoSQL() {
		List<File> list = extractor.extractNoSQL( downloadedFile );
		assertFalse( list.isEmpty() );
	}

	@Test
	public void testRemoveExtractedNoSQL() {
		List<File> list = extractor.extractNoSQL( downloadedFile );
		assertTrue( extractor.removeExtractedNoSQL( null ) );
	}

	@Test
	public void testEachProcessFromExtractToRemove() {
		List<File> list = extractor.extractNoSQL( downloadedFile );
		NoSQLExecutorImpl executor = new NoSQLExecutorImpl();
		executor.execute( downloadedFile );
		extractor.removeExtractedNoSQL( null );
	}

	@Test
	public void testEachProcessFromExtractToStop() {
		List<File> list = extractor.extractNoSQL( downloadedFile );
		NoSQLExecutorImpl executor = new NoSQLExecutorImpl();
		Process p = executor.execute( downloadedFile );
		extractor.removeExtractedNoSQL( null );
		executor.stop( p );
	}

	@After
	public void tearDown() throws Exception {
		System.out.println( "deleted ? " + new File( MongoDBLoaderTest.TEST_MONGO_DOWNLOADED_PATH ).delete() );
	}

}
