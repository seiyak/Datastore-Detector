package org.datastoredetector.nosqlconfigmover.impl;

public class VoldemortConfigMoverImpl extends NoSQLConfigMoverImpl {

	private String[] defaultConfigFiles;
	private static final String CLUSTER_XML = "cluster.xml";
	private static final String SERVER_PROPERTIES = "server.properties";
	private static final String STORES_XML = "stores.xml";

	public VoldemortConfigMoverImpl(String[] configFiles) {
		super( configFiles );
		defaultConfigFiles = new String[] { CLUSTER_XML, SERVER_PROPERTIES, STORES_XML };
	}
}
