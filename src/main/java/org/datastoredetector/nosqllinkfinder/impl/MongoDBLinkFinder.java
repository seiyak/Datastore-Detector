package org.datastoredetector.nosqllinkfinder.impl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MongoDBLinkFinder extends NoSQLLinkFinder {

	private static String DOWNLOAD_PAGE = "http://www.mongodb.org/downloads";

	public String findDownloadLinkFor(String datastoreClass) {
		return extractLink(datastoreClass + "-" + combineOSAndArchProperties());
	}

	private String combineOSAndArchProperties() {
		if (isMacOS()) {
			return "osx" + "-" + getArchProperty();
		} else if (isWindows()) {
			// // TODO add support for windows 2008 r2 +
			/**
			 * The Windows 2008+ build uses newer features of Windows to enhance
			 * performance. Use this build if you are running with 64-bit
			 * Windows Server 2008 R2, Windows 7, or greater.
			 */
			return "win32" + "-" + getArchProperty();
		}
		return getOSProperty() + "-" + getArchProperty();
	}

	private String extractLink(String targetMongoDBFile) {

		try {
			Document downloadPage = Jsoup.connect(DOWNLOAD_PAGE).get();
			return downloadPage.select("tr.not_last")
					.select("a[href*=" + targetMongoDBFile + "]").first()
					.attr("href");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
