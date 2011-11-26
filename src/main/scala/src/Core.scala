package core;

// Core.scala

import scala.collection.mutable._;
import java.util.{ Date => Date };
import scala.swing._;

package core.gui {

    // object FrontEnd extends SimpleSwingApplication {
        // def top = new MainFrame {
            // title = "Core Frontend"
            // contents = { 
                // new BoxPanel( Orientation.Vertical ) {
                    // contents += new TextArea {
                                    // tooltip = "Console Log";
                                    // border = Swing.EmptyBorder( 30, 30, 10, 30 );
                                // }
                    // contents += new TextArea {
                    // }
                    // contents += new Button {
                                    // text_=( "Send" );

                    // }
                // }
            // }
        // }
    // }

}

        
object Core {

    def initialize = {
    
        // no Ints, bro, only Longs
    
        // TYPE (types are unique)
        val article = new Datatype( "article" );
         article has new Property ( "name",         "String"        );
         article has new Property ( "description",  "String"        );
         article has new Property ( "value",        "Long",      "0" );
         
        
        // COPY (types can have multiple copies)
        val entity = new Datatype( "entity" );
            entity has (
               new Property( "article", "Long" ),
               new Property( "state", "Double", "1" ) // shows state from 0 to 1; 0.5 is half-destroyed
            );
        
        
        // IDENTIFICATION (copies can have multiple identifications)
        val code = new Datatype( "code" );  
         code has new Property ( "code",     "String" );           // allows alphanumeric coding 
         code has new Property ( "family",   "String" );           // QR or barcode
         code has new Property ( "entity",   "Long"    );
        
        
        val person = new Datatype( "person" );
         person has new Property ( "name",      "String"          );
         person has new Property ( "phone",     "Long",       "0" );
         person has new Property ( "email",     "String",     "\"\""  );
        

        val loan = new Datatype( "loan" );        

        loan has (
            new Property( "entity",         "Long"      ),
            new Property( "fromPerson",     "Long"      ),       // connects two persons
            new Property( "toPerson",       "Long"      ),       // 'from' and 'to'
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
         personGroup has new Property( "person", "Long" );
         
        val articleGroup = new Datatype( "articleGroup" );
         articleGroup has new Property( "name", "String" );
         articleGroup has new Property( "article", "Long" );   
        
        Console.print( "- Would you fancy to generate Element definitions? (yes/no): \n- " );
        def generate( r: String ) = r match {
            case "yes" => {
                Generator.write( loan );
                Generator.write( person );
                Generator.write( code );
                Generator.write( entity );
                Generator.write( article );
                Generator.write( personGroup );
                Generator.write( articleGroup );
                Console.println( "- All done, sire" );
            }
            case _ => {
                Console.println( "- As you wish, sire." );
            }
        }
        generate( Console.readLine );
        
    }
    
    def main( args: Array[String] ) {
    
      space
      time
      space
      
      EventLog.add ( new Event( 0, "The core is started.", this ) );       
        
      space
        
        initialize
        
        val personDatabase  = new PersonDatabase();
        val loanDatabase    = new LoanDatabase();
        val articleDatabase = new ArticleDatabase();
        val entityDatabase  = new EntityDatabase();
        val codeDatabase    = new CodeDatabase();
            
        personDatabase.initialize();
        
        personDatabase.addPerson( new Person( "David Murray" ) );
        personDatabase.addPerson( new Person( "John Carr" ) );
        personDatabase.addPerson( new Person( "Elvis Santiago" ) );
        personDatabase.addPerson( new Person( "Jesus Geist" ) );
        personDatabase.addPerson( new Person( "Emilio Stevens" ) );       



       
        ( List( 
                new Article( "Aktiv Heoytaler", "", 0 ),
                new Article( "DJ Flight Newmark", "", 0 ),
                new Article( "DJ Flight Pioneer", "", 0 ),
                new Article( "Platespiller", "", 0 ),
                new Article( "Mikser Blaa", "", 0 ),
                new Article( "Mikser Graa", "", 0 ),
                new Article( "Mikrofon SM 57", "", 0 ),
                new Article( "DJ Boks", "", 0 ),
                new Article( "Mikrofonstativ", "", 0 ),
                new Article( "XLR Kabel", "", 0 ),
                new Article( "Streomkabel", "", 0 ),
                new Article( "Phonokabel", "", 0 ),
                new Article( "Phono-minijack kabel", "", 0 ),
                new Article( "Prosjektor Graa", "", 0 ),
                new Article( "Prosjektor Svart", "", 0 ),
                new Article( "VGA (skjerm) kabel", "", 0 )
            )
        ).map( articleDatabase.addArticle( _ ) );
        
        entityDatabase.addEntity( 
            new Entity( articleDatabase.getArticleByName( "Phonokabel" ).get.id )
        );
        entityDatabase.addEntity( 
            new Entity( articleDatabase.getArticleByName( "Phonokabel" ).get.id )
        );
        
        codeDatabase.addCode( new Code( "504506010222955", "qr", 12 ) );
        codeDatabase.addCode( new Code( "504506010222955", "barcode", 12 ) );
        
        
        var deliever: Long = Console.readLong;
        
        
        
        
        
        
      
      
      
      space
      space

        
        println( EventLog.extract );
      
      separator
      
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
            print( "-" );
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
           appendix += "-";
        }
        
        println( "--    " + date + "  " + appendix )
    }
}

