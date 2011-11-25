package core;

// Core.scala

import scala.collection.mutable._;
import java.util.{ Date => Date };

// [+] allow inserting commands into QR codes
object Core {
    def main( args: Array[String] ) {
    
      space
      time
      space
      
      EventLog.add ( new Event( 0, "The core is started.", this ) );       
        
      space
        
        // TYPE (types are unique)
        val article = new Datatype( "article" );
         article has new Property ( "name",          "String",   255 );
         article has new Property ( "description",   "String",   255 );
        
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
         person has new Property ( "name",   "String",   255 );
        

        
        
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
        
        // Console.print( "- Would you fancy to generate Element definitions? (yes/no): \n- " );
        // def generate( r: String ) = r match {
            // case "yes" => {
                // Generator.write( loan );
                // Generator.write( person );
                // Generator.write( code );
                // Generator.write( entity );
                // Generator.write( article );
                // Generator.write( personGroup );
                // Generator.write( articleGroup );
                // Console.println( "- All done, sire" );
            // }
            // case _ => {
                // Console.println( "- As you wish, sire." );
            // }
        // }
        // generate( Console.readLine );
        
        val database = new PersonDatabase();
         // database.addPerson( new Person( "Adam Kesher" ) );
         // database.addPerson( new Person( "Ola Nordvik" ) );
         // database.addPerson( new Person( "Jens Havik" ) );
         // database.addPerson( new Person( "Terrence Hogan" ) );
         // database.addPerson( new Person( "David Murray" ) );
         // database.addPerson( new Person( "John Carr" ) );
         // database.addPerson( new Person( "Elvis Santiago" ) );
         // database.addPerson( new Person( "Jesus Geist" ) );
         // database.addPerson( new Person( "Emilio Stevens" ) );
         
         // database.removePerson( new Person( "Adam Kesher" ) );
        
        try {
            var result = database.getPersonById( 2 ).get;
            println( result.name )
        } catch {
            case e: java.util.NoSuchElementException => 
                        ( EventLog <= "Person not found" )
        }
        
        val loanDatabase = new LoanDatabase();
         loanDatabase.addLoan(
            new Loan(
                0, 2, 4, new Date, new Date, new Date, new Date,
                "location",
                "none",
                "purpose"
            )
         );
        
         
        try {
            var p = database.getPersonById( 4 ).get
            var result = loanDatabase.getLoansToPerson( p );
            println( result );
        } catch {
            case e: java.util.NoSuchElementException =>
                ( EventLog <= ("No loans for the person" ) )
        }
      
      
      space
      space
       
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

