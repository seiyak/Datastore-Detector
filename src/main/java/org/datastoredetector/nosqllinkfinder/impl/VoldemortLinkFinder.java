package org.datastoredetector.nosqllinkfinder.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class VoldemortLinkFinder extends NoSQLLinkFinder {

	private static String DOWNLOAD_PAGE = "https://github.com/voldemort/voldemort/downloads";

	public String findDownloadLinkFor(String datastoreClass) {

		try {
			Document downloadPage = Jsoup.connect( DOWNLOAD_PAGE ).get();
			return "https://github.com" + downloadPage.select( "ol.download-list" ).select( "a" ).first().attr( "href" );
		}
		catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
