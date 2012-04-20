package org.datastoredetector.nosqllinkfinder.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CassandraLinkFinder extends NoSQLLinkFinder {

	private static String DOWNLOAD_PAGE = "http://cassandra.apache.org/download/";

	public String findDownloadLinkFor(String datastoreClass) {

		try {
			return extractLink(Jsoup.connect(DOWNLOAD_PAGE).get());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String extractLink(Document downloadPage) {

		try {
			String latestCassandra = downloadPage.select("a.filename").first()
					.attr("href");
			return Jsoup
					.connect(
							downloadPage.select("a.filename").first()
									.attr("href"))
					.get()
					.select("a[href$="
							+ latestCassandra.substring(latestCassandra
									.lastIndexOf("/") + 1) + "]").first()
					.attr("href");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
