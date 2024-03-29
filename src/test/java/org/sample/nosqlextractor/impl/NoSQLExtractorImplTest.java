package org.sample.nosqlextractor.impl;

import java.io.File;
import java.util.List;

import org.datastoredetector.nosqlexecutor.impl.NoSQLExecutorImpl;
import org.datastoredetector.nosqlextractor.impl.NoSQLExtractorImpl;
import org.datastoredetector.nosqllinkfinder.impl.VoldemortLinkFinder;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sample.nosqlloader.impl.NoSQLLoaderTest;

import static org.junit.Assert.*;

public class NoSQLExtractorImplTest {

	private NoSQLExtractorImpl extractor;
	private File downloadedFile;

	@Test
	public void testRemoveExtractedNoSQLForMongoDB() {
		downloadedFile = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_MONGO_DOWNLOADED_PATH )
				.downloadNoSQL( NoSQLLoaderTest.TEST_MONGO_URL1 );
		extractor = new NoSQLExtractorImpl( downloadedFile );
		List<File> list = extractor.extractNoSQL( downloadedFile );
		assertTrue( extractor.removeExtractedNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_MONGO_DOWNLOADED_PATH ).exists() );
	}

	@Test
	public void testRemoveExtractedNoSQLForVoldemort() {
		downloadedFile = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_VOLDEMORT_DOWNLOADED_PATH )
				.downloadNoSQL( new VoldemortLinkFinder().findDownloadLinkFor( "" ) );
		extractor = new NoSQLExtractorImpl( downloadedFile );
		List<File> list = extractor.extractNoSQL( downloadedFile );
		assertTrue( extractor.removeExtractedNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_VOLDEMORT_DOWNLOADED_PATH ).exists() );
	}

	@Test
	public void testRemoveExtractedNoSQLForRedis() {
		downloadedFile = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_REDIS_DOWNLOADED_PATH )
				.downloadNoSQL( NoSQLLoaderTest.TEST_REDIS_URL1 );
		extractor = new NoSQLExtractorImpl( downloadedFile );
		List<File> list = extractor.extractNoSQL( downloadedFile );
		assertTrue( extractor.removeExtractedNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_REDIS_DOWNLOADED_PATH ).exists() );
	}

	@Test
	public void testEachProcessFromExtractToStopForMongoDB() {
		downloadedFile = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_MONGO_DOWNLOADED_PATH )
				.downloadNoSQL( NoSQLLoaderTest.TEST_MONGO_URL1 );
		extractor = new NoSQLExtractorImpl( downloadedFile );
		List<File> list = extractor.extractNoSQL( downloadedFile );
		NoSQLExecutorImpl executor = new NoSQLExecutorImpl();
		Process p = executor.execute( downloadedFile );
		executor.stop( p );
		assertTrue( extractor.removeExtractedNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_MONGO_DOWNLOADED_PATH ).exists() );
	}

	@Test
	public void testEachProcessFromExtractToStopForVoldemort() {
		downloadedFile = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_VOLDEMORT_DOWNLOADED_PATH )
				.downloadNoSQL( new VoldemortLinkFinder().findDownloadLinkFor( "" ) );
		extractor = new NoSQLExtractorImpl( downloadedFile );
		List<File> list = extractor.extractNoSQL( downloadedFile );
		NoSQLExecutorImpl executor = new NoSQLExecutorImpl();
		Process p = executor.execute( downloadedFile );
		executor.stop( p );
		assertTrue( extractor.removeExtractedNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_VOLDEMORT_DOWNLOADED_PATH ).exists() );
	}

	@Test
	public void testEachProcessFromExtractToStopForRedis() {
		downloadedFile = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_REDIS_DOWNLOADED_PATH )
				.downloadNoSQL( NoSQLLoaderTest.TEST_REDIS_URL1 );
		extractor = new NoSQLExtractorImpl( downloadedFile );
		List<File> list = extractor.extractNoSQL( downloadedFile );
		NoSQLExecutorImpl executor = new NoSQLExecutorImpl();
		Process p = executor.execute( downloadedFile );
		executor.stop( p );
		assertTrue( extractor.removeExtractedNoSQL( null ) );
		assertFalse( new File( NoSQLLoaderTest.TEST_REDIS_DOWNLOADED_PATH ).exists() );
	}
}
