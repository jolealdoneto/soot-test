/* Soot - a J*va Optimization Framework
 * Copyright (C) 1997-1999 Raja Vallee-Rai
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

/*
 * Modified by the Sable Research Group and others 1997-1999.  
 * See the 'credits' file distributed with Soot for the complete list of
 * contributors.  (Soot is distributed at http://www.sable.mcgill.ca/soot)
 */


package br.com.lealdn;

import soot.*;
import soot.jimple.*;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.internal.JIdentityStmt;
import soot.options.Options;
import soot.util.*;

import java.io.*;
import java.util.*;

/** Example of using Soot to create a classfile from scratch.
 * The 'createclass' example creates a HelloWorld class file using Soot.
 * It proceeds as follows:
 *
 * - Create a SootClass <code>HelloWorld</code> extending java.lang.Object.
 *
 * - Create a 'main' method and add it to the class.
 *
 * - Create an empty JimpleBody and add it to the 'main' method.
 *
 * - Add locals and statements to JimpleBody.
 *
 * - Write the result out to a class file.
 */

public class CopyOfMain
{
    public void main(String[] args) throws FileNotFoundException, IOException
    {
    	configure(CopyOfMain.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    	SootClass sootClass = Scene.v().loadClassAndSupport("br.com.lealdn.DumpClass");
    	sootClass.setApplicationClass();

    	// Retrieve the method and its body
    	SootMethod m = sootClass.getMethodByName("sayHi");
    	JimpleBody b = (JimpleBody)m.retrieveActiveBody();
    	
    	System.out.println(m.getTags());
    	
    }
    
    public static void configure(String classpath) {

        Options.v().set_verbose(false);
        Options.v().set_keep_line_number(true);
        Options.v().set_src_prec(Options.src_prec_class);
        Options.v().set_soot_classpath(classpath);
        Options.v().set_prepend_classpath(true);

        PhaseOptions.v().setPhaseOption("bb", "off");
        PhaseOptions.v().setPhaseOption("tag.ln", "on");
        PhaseOptions.v().setPhaseOption("jj.a", "on");
        PhaseOptions.v().setPhaseOption("jj.ule", "on");

        Options.v().set_whole_program(true);
    }
    
    
    class InterceptorTransformer extends BodyTransformer {
		@Override
		protected void internalTransform(Body body, String phase, Map options) {
			final SootMethod m = body.getMethod();
			System.out.println("Instrumenting: " + m.getName());
		}
    }
    
        
}