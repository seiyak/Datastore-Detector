/* 
 * JBoss, Home of Professional Open Source
 * Copyright 2012 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.datastoredetector.agentloader;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import com.sun.tools.attach.VirtualMachine;

/**
 * @author Seiya Kawashima <skawashima@uchicago.edu>
 */
public class JavaAgentLoader {
	private final String agentJarPath;
	private VirtualMachine vm;

	public JavaAgentLoader(String agentJarPath) {
		this.agentJarPath = agentJarPath;
	}

	public void loadAgent() {
		String vmName = ManagementFactory.getRuntimeMXBean().getName();
		int p = vmName.indexOf( "@" );
		String pid = vmName.substring( 0, p );

		try {
			vm = VirtualMachine.attach( pid );
			System.out.println( "attaching ..." );
			vm.loadAgent( agentJarPath, null );
			System.out.println( "after attached" );
		}
		catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}

	public void detachAgent() {
		try {
			if ( vm != null ) {
				vm.detach();
			}
		}
		catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
