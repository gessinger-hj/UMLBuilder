package org.gessinger.UMLBuilder ;
import org.gessinger.gepard.Event ;
import org.gessinger.gepard.User ;
import org.gessinger.gepard.* ;

import net.sourceforge.plantuml.FileFormat; 
import net.sourceforge.plantuml.FileFormatOption; 
import net.sourceforge.plantuml.SourceStringReader; 

import java.io.*;
import java.util.*;
import org.gessinger.vx.util.*;

public class UMLBuilder
{
  static Client client ;
  static public void main ( String[] args ) throws Exception
  {
    Util.argsToProperties ( args ) ;
    // UMLBuilder ub = new UMLBuilder() ;
    // ub.create ( args ) ;
    client = Client.getInstance() ;
    try
    {
      client.on ( "plantuml:getFileList", (e) -> {
      	User u = e.getUser() ;
      	if ( u == null ) {
          e.setStatus ( 1, "ERROR", "Action not allowed, missing User ") ;
          e.sendBack() ;
          return ;
      	}
        System.out.println ( e ) ;
      }) ;
    }
    catch ( Exception exc )
    {
      exc.printStackTrace() ;
    }
  }
  void create ( String[] args )
  throws Exception
  {
    QCFile f = new QCFile ( args[0] ) ;
    String str = f.asString();
    File parentFile = f.getParentFile() ;
    File fout = new File ( parentFile, f.getName() + ".png" ) ;
    storeDiagram ( str, fout.toString() ) ;
  }
  void storeDiagram(String source, String fileName)
  throws Exception
  {
    SourceStringReader reader = new SourceStringReader(source); 
    final ByteArrayOutputStream os = new ByteArrayOutputStream(); 
    // Write the first image to "os" 
    // reader.generateImage(os, new FileFormatOption(FileFormat.SVG)); 
    // os.close(); 
    FileOutputStream fos = new FileOutputStream(fileName); 

    reader.generateImage(fos, new FileFormatOption(FileFormat.PNG)); 
    fos.close(); 
  }
}
