package core;

// Event.scala

import scala.collection.mutable._;
import java.util.{ Date => Date };


object EventLog extends Group[Event] {
   override
    def extract = {
        "   -Events: \n\t" + this.elements.map( e => ( e.extract ) ).mkString( "\t\n" );
    }
    
    def <=( description: String ) {
        this.add( new Event( 0, description, null ) );
    }
    
    // get by source
    // get by code
    // get from-to date
} 

class Event( val code: Int, val description: String, val source: Any, val date: Date )
  extends Element {
    def this( code: Int, description: String, source: Any) 
      = this( code,      description,         source,       new Date );
      
   override
    def extract = {
        "" + (this.getClass().toString.replace( "class ", "" )) + 
        " at " + date + 
        " \n\t\tin " + source +
        ": " + description + 
        " [code: " + code + "]\n";
    }
    // [?] How can I inherit this method from Group, so it functions without overriding?
    // Try adding a wildcard ? type to the method, to the class, the "to" definition.
    // Try not providint 
   override
    def to( group: Group[Element] ) = { // allows adding (ii)Events to an (i)ElementGroup
        group.add( this );
    }
}

class Error( code: Int, description: String, source: Any, date: Date )
  extends Event( code, description, source, date ) {
    def this( source: Any ) 
      = this( 1, "an error has occured", source, new Date );
    def this( description: String, source: Any ) 
      = this( 1, description, source, new Date );
    def this( code: Int, description: String, source: Any ) 
      = this( code, description, source, new Date );
}