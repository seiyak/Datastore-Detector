package org.datastoredetector.detector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.datastoredetector.agentloader.JavaAgentLoader;

import sun.instrument.InstrumentationImpl;

import com.google.gson.Gson;

public class DatastoreDetectorImpl implements DatastoreDetector {

	private static final String AGENT_JAR_PATH = "org" + File.separator + "datastoredetector" + File.separator
			+ "datastore-detector";
	private static final String AGENT_JAR_VERSION = "1.0-SNAPSHOT";
	private static final String AGENT_JAR_NAME = "datastore-detector-1.0-SNAPSHOT-jar-with-dependencies.jar";
	private static final String DATASTORE_PROVIDER = "DatastoreProvider";
	private static final String HIBERNATE_PROPERTIES = "hibernate.properties";
	private static final String PROVIDER_PREFIX = "hibernate.ogm.datastore.provider=";

	@Override
	public String getDatastoreProviderName() {
		
		String agentPath = createPathToAgentJar();
		
		JavaAgentLoader agentLoader = new JavaAgentLoader( agentPath );
		agentLoader.loadAgent();
		InstrumentationImpl instrumentation = (InstrumentationImpl) new Gson().fromJson(
				System.getProperty( "instrumentation" ), InstrumentationImpl.class );

		String providerNameFromAgent = "";
		String providerNameFromProperties = "";
		String providerName = "";
		try {
			providerNameFromAgent = getProviderNameFromAgent( instrumentation );
			System.out.println( "provider name from agent: " + providerNameFromAgent );
			providerNameFromProperties = getProviderNameFromHibernateProperties();
			System.out.println( "provider name from hibernate.properties: " + providerNameFromProperties );
			providerName = validateProviderNames( providerNameFromAgent, providerNameFromProperties );
			System.out.println( "provider name to be used: " + providerName );
		}
		finally {
			agentLoader.detachAgent();
			System.out.println( "detaching from VM." );
		}

		return providerName;
	}

	private String createPathToAgentJar() {
		return System.getProperty( "user.home" ) + File.separator + ".m2" + File.separator + "repository"
				+ File.separator + AGENT_JAR_PATH + File.separator + AGENT_JAR_VERSION + File.separator
				+ AGENT_JAR_NAME;
	}

	private String getProviderNameFromAgent(InstrumentationImpl instrumentation) {

		System.out.println( "searching for XXXDatastoreProvider." );
		for ( Class cls : instrumentation.getAllLoadedClasses() ) {
			if ( cls != null ) {
				try {
					if ( cls.toString().endsWith( DATASTORE_PROVIDER ) && !cls.isInterface() ) {
						System.out.println( "found: " + cls.toString() );
						return cls.getSimpleName();
					}
				}
				catch ( Exception ex ) {

				}
			}
		}

		return "";
	}

	private String getProviderNameFromHibernateProperties() {
		String providerName = "";
		InputStream is = getClass().getClassLoader().getResourceAsStream( HIBERNATE_PROPERTIES );
		if ( is != null ) {
			StringWriter writer = new StringWriter();
			try {
				IOUtils.copy( is, writer );
				providerName = this.getProviderNameFrom( writer.toString() );
			}
			catch ( IOException e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					is.close();
					writer.close();
				}
				catch ( IOException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return providerName;
	}

	private String getProviderNameFrom(String hibernateProperties) {
		// // TODO sometimes it doesn't pick up the right part of the string.
		// // this is not good enough. Use regex instead.
		String providerLine = hibernateProperties.substring( hibernateProperties.lastIndexOf( PROVIDER_PREFIX ),
				hibernateProperties.length() );
		return providerLine.substring( providerLine.lastIndexOf( "." ) + 1, providerLine.length() );
	}

	private String validateProviderNames(String providerNameFromAgent, String providerNameFromProperties) {

		if ( providerNameFromAgent.equals( "" ) && providerNameFromProperties.equals( "" ) ) {
			throw new RuntimeException(
					"could not determine the current DatastoreProvider. DatastoreProvider is null from agent and hibernate.properties" );
		}

		if ( providerNameFromAgent.equals( "" ) && !providerNameFromProperties.equals( "" ) ) {
			return providerNameFromProperties;
		}
		else if ( !providerNameFromAgent.equals( "" ) && providerNameFromProperties.equals( "" ) ) {
			return providerNameFromAgent;
		}
		else if ( providerNameFromAgent.equals( providerNameFromProperties ) ) {
			return providerNameFromAgent;
		}
		else if ( !providerNameFromAgent.equals( "" ) && !providerNameFromProperties.equals( "" )
				&& !providerNameFromAgent.equals( providerNameFromProperties ) ) {
			if ( providerNameFromAgent.contains( DATASTORE_PROVIDER ) ) {
				return providerNameFromAgent;
			}
			else if ( providerNameFromProperties.contains( DATASTORE_PROVIDER ) ) {
				return providerNameFromProperties;
			}
		}

		throw new RuntimeException( "should not come gere at the end of validateProviderNames(). "
				+ providerNameFromAgent + " from agent " + providerNameFromProperties + " from hibernate.properties" );
	}

}
