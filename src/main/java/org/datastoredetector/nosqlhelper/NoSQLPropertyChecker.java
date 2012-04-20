package org.datastoredetector.nosqlhelper;

import java.lang.reflect.Field;

public class NoSQLPropertyChecker {

	/**
	 * Checks if the required property is correctly set. If so, does nothing. Otherwise, throws RuntimeException.
	 * 
	 * @param obj
	 *            Where the property is looked for.
	 * @param propertyName
	 *            Property name to be looked for.
	 * @return boolean True if the specified property exists on the Object and has values other than null.Otherwise,
	 *         returns false.
	 */
	public boolean checkPropertyOn(Object obj, String propertyName) {

		for ( Field field : obj.getClass().getDeclaredFields() ) {
			System.out.println( "found field: " + field.getName() + " on: " + obj.getClass().getCanonicalName() );
			field.setAccessible( true );
			if ( field.getName().equals( propertyName ) ) {
				try {
					if ( field.get( obj ) != null ) {

						return true;
					}
				}
				catch ( IllegalArgumentException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( IllegalAccessException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;
	}
}
