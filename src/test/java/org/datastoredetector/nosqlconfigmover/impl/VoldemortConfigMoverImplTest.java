package org.datastoredetector.nosqlconfigmover.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.datastoredetector.nosqlconfigmover.NoSQLConfigMover;
import org.datastoredetector.nosqlextractor.impl.NoSQLExtractorImpl;
import org.datastoredetector.nosqllinkfinder.impl.VoldemortLinkFinder;
import org.datastoredetector.nosqlloader.impl.NoSQLLoaderImpl;
import org.datastoredetector.nosqlremover.impl.NoSQLRemoverImpl;
import org.junit.Test;
import org.sample.nosqlloader.impl.NoSQLLoaderTest;

public class VoldemortConfigMoverImplTest {

	@Test
	public void testToString() {
		VoldemortConfigMoverImpl voldemortMover = new VoldemortConfigMoverImpl( new String[] { "stores.xml",
				"cluster.xml" } );
		String str = voldemortMover.toString();
		String expectedStr = "org.datastoredetector.nosqlconfigmover.impl.VoldemortConfigMoverImpl configuration fies: stores.xml,cluster.xml";
		assertTrue( "expected " + expectedStr + " , but found " + str, str.equals( expectedStr ) );
	}

	@Test
	public void testMoveConfigFiles() {
		VoldemortConfigMoverImpl voldemortMover = new VoldemortConfigMoverImpl( new String[] { "stores.xml",
				"cluster.xml" } );
		voldemortMover.moveConfigFiles( null );
		NoSQLConfigMover voldemortMover1 = new VoldemortConfigMoverImpl( new String[] { "stores.xml" } );
		voldemortMover1.moveConfigFiles( null );
	}

	@Test(expected = RuntimeException.class)
	public void testMoveConfigFilesWithInvalidNumberOfFiles() {
		NoSQLConfigMover voldemortMover = new VoldemortConfigMoverImpl( new String[] { "stores.xml", "cluster.xml",
				"server.properties", "sample.txt" } );
		voldemortMover.moveConfigFiles( null );
	}

	@Test(expected = RuntimeException.class)
	public void testMoveConfigFilesWithInvalidFileName() {
		NoSQLConfigMover voldemortMover = new VoldemortConfigMoverImpl( new String[] { "invalid.xml" } );
		voldemortMover.moveConfigFiles( null );
	}

	@Test
	public void testMoveConfigFilesWithInvalidDestination() throws IOException {
		NoSQLConfigMover voldemortMover = new VoldemortConfigMoverImpl( new String[] { "stores.xml", "cluster.xml" } );
		assertFalse( voldemortMover.moveConfigFiles( new File( System.getProperty( "user.home" ) + File.separator
				+ "sample" ) ) );
		File f = new File( System.getProperty( "user.home" ) + File.separator + "YetAnotherNoSQL" );
		assertFalse( f.exists() );
		FileWriter writer = new FileWriter( f );
		writer.flush();
		writer.close();
		assertTrue( f.exists() );
		assertFalse( voldemortMover.moveConfigFiles( f ) );
		assertTrue( f.delete() );
	}

	@Test
	public void testMoveConfigFilesWithValidDestination() {
		File downloadedFile = new NoSQLLoaderImpl( NoSQLLoaderTest.TEST_VOLDEMORT_DOWNLOADED_PATH )
				.downloadNoSQL( new VoldemortLinkFinder().findDownloadLinkFor( "" ) );
		NoSQLExtractorImpl extractor = new NoSQLExtractorImpl( downloadedFile );
		List<File> list = extractor.extractNoSQL( downloadedFile );
		NoSQLConfigMover voldemortMover = new VoldemortConfigMoverImpl( new String[] { "stores.xml", "cluster.xml" } );
		File f = new File( System.getProperty( "user.home" ) + File.separator + NoSQLLoaderTest.VOLDEMORT_VERSION
				+ File.separator + "config" + File.separator + "test_config1" + File.separator + "config" );
		assertTrue( voldemortMover.moveConfigFiles( f ) );
		assertTrue( new NoSQLRemoverImpl( downloadedFile ).removeNoSQL( null ) );
	}
}
