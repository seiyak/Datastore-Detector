package org.datastoredetector.nosqllinkfinder.impl;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RedisLinkFinder extends NoSQLLinkFinder {

	private static String DOWNLOAD_PAGE = "http://redis.io/download";

	public String findDownloadLinkFor(String datastoreClass) {

		try {
			Document downloadPage = Jsoup.connect( DOWNLOAD_PAGE ).get();
			if ( isLinux() ) {
				Elements elements = downloadPage.select( "tr.current" ).select( "a" );
				for ( Iterator<Element> itr = elements.iterator(); itr.hasNext(); ) {
					Element element = itr.next();
					if ( element.text().equals( "Download" ) ) {
						return element.attr( "href" );
					}
				}
			}
		}
		catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
