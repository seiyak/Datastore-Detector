package org.datastoredetector.nosqlexecutor.impl;

import java.io.File;
import java.io.IOException;

import org.datastoredetector.nosqlexecutor.NoSQLExecutor;
import org.datastoredetector.nosqlhelper.NoSQLCommandGenerator;
import org.datastoredetector.nosqlhelper.NoSQLPropertyChecker;

public class NoSQLExecutorImpl implements NoSQLExecutor {

	private File noSQLServerPath;
	private final NoSQLPropertyChecker propertyChecker;
	private final NoSQLCommandGenerator commandGenerator;
	private String noSQLCommand;

	public NoSQLExecutorImpl() {
		propertyChecker = new NoSQLPropertyChecker();
		commandGenerator = new NoSQLCommandGenerator();
	}

	public NoSQLExecutorImpl(File noSQLServerPath) {
		this.noSQLServerPath = noSQLServerPath;
		propertyChecker = new NoSQLPropertyChecker();
		commandGenerator = new NoSQLCommandGenerator();
	}

	public Process execute(File noSQLServerPath) {
		boolean propertySet = propertyChecker.checkPropertyOn( this, "noSQLServerPath" );
		if ( !propertySet && noSQLServerPath == null ) {
			throw new RuntimeException( "NoSQL server path is invalid" );
		}

		if ( propertySet && noSQLServerPath != null ) {
			return executeNoSQLServer( noSQLServerPath );
		}
		else if ( !propertySet && noSQLServerPath != null ) {
			return executeNoSQLServer( noSQLServerPath );
		}
		else if ( propertySet && noSQLServerPath == null ) {
			return executeNoSQLServer( this.noSQLServerPath );
		}
		return null;
	}

	private Process executeNoSQLServer(File noSQLServerPath) {
		Process p = null;
		try {
			noSQLCommand = commandGenerator.generateCommandFor( noSQLServerPath );
			System.out.println( "about to start '" + noSQLCommand + "'" );
			p = Runtime.getRuntime().exec( noSQLCommand );

			if ( p != null ) {
				System.out.println( "'" + noSQLCommand + "' started successfully" );
			}
		}
		catch ( IOException e ) {
			e.printStackTrace();
		}

		return p;
	}

	public void stop(Process p) {
		if ( p != null ) {
			p.destroy();
			System.out.println( "'" + noSQLCommand + "' terminated successfully" );
			return;
		}

		throw new RuntimeException( "the parameter, Process should not be null, " + p );
	}
}
