package core;

// Core.scala

import scala.collection.mutable._;
import java.util.{ Date => Date };
import scala.swing._;


// object FrontEnd extends SimpleSwingApplication {
    // def top = new MainFrame {
        // title = "First App"
        // contents = { 
            // new Button { text = "Click me" }
        // }
    // }
// }

        
object Core {

    def createDatabase = {
        // TYPE (types are unique)
        val article = new Datatype( "article" );
         article has new Property ( "name",         "String"     );
         article has new Property ( "description",  "String"     );
         article has new Property ( "value",        "Int",   default = "0" );
         
        
        // COPY (types can have multiple copies)
        val entity = new Datatype( "entity" );
        
        entity has (
           new Property( "article", "Int" )
        );
        
        
        // IDENTIFICATION (copies can have multiple identifications)
        val code = new Datatype( "code" );  
         code has new Property ( "code",     "String" );           // allows alphanumeric coding 
         code has new Property ( "family",   "String" );           // QR or barcode
         code has new Property ( "entity",   "Int"    );
        
        
        
        
        
        val person = new Datatype( "person" );
         person has new Property ( "name",        "String"  );
         // person has new Property ( "phone",       "Int"     );
         // person has new Property ( "affiliation", "String"  );
        

        
        
        val loan = new Datatype( "loan" );        

        loan has (
            new Property( "entity",         "Int"       ),
            new Property( "fromPerson",     "Int"       ),       // connects two persons
            new Property( "toPerson",       "Int"       ),       // 'from' and 'to'
            new Property( "timeOrdered",    "Date"      ),       
            new Property( "timeFetched",    "Date"      ),       // has other properties
            new Property( "timeExpired",    "Date"      ),       // that describe the connection
            new Property( "timeReturned",   "Date"      ),       
            new Property( "location",       "String"    ),
            new Property( "damage",         "String"    ),
            new Property( "purpose",        "String"    )        // event where article is used
        );
        
        val personGroup = new Datatype( "personGroup" );    // gives rights, like loan registration
         personGroup has new Property( "name", "String" );
         personGroup has new Property( "person", "Int" );
         
        val articleGroup = new Datatype( "articleGroup" );
         articleGroup has new Property( "name", "String" );
         articleGroup has new Property( "article", "Int" );   
        
        
        
        
        
        
        
          
        
    }
    
    def main( args: Array[String] ) {
    
      space
      time
      space
      
      EventLog.add ( new Event( 0, "The core is started.", this ) );       
        
      space
        
        createDatabase
        
        val personDatabase = new PersonDatabase();
            personDatabase.initialize();
        
   
        
        
        
      
      
      
      space
      space
      
      println( "Events: " );
      println( EventLog.extract );
        
      space
      space
      space
      
    }
    
    val frame: Int = 80;
    
    def space() {
        print( "\n\n\n\n" );
    }    
    def space( n: Int ) {
        ( 1 to n ).foreach( x => print( "\n" ) )
    }
    def separator() {
        for ( n <- 1 to this.frame ) {
            print( "*" );
        }
        print( "\n" );   
    }
    def separator( width: Int ) {
        for ( n <- 1 to width ) {
            print( "-" );
        }
        print( "\n" );
    }
    def time() {
        val date: String = (new Date).toString;
        val dateWidth: Int = date.length;
        var appendix: String = ""
        for ( i <- 1 to ( this.frame - (dateWidth + 8 ) )) {
           appendix += "*";
        }
        
        println( "**    " + date + "  " + appendix )
    }
}

