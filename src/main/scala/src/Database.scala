package core;

// Database.scala

import scala.collection.mutable._;

import java.sql.{DriverManager, Connection};

import com.mysql.jdbc.Driver;

import org.squeryl._;
import org.squeryl.Schema;
import org.squeryl.adapters._;
import org.squeryl.SessionFactory;
import org.squeryl.annotations.Column;
import org.squeryl.PrimitiveTypeMode._;
import org.squeryl.internals.FieldMetaData;



abstract
 class ElementDatabase extends Group[Element] {
    
    var connection: Connection = this.connect;
    
    // connects to the database via DriverManager from java.sql
    // supplying url, username, password
    def connect: Connection = {
        Class.forName("com.mysql.jdbc.Driver");
        
        val connection: Connection =
        DriverManager.getConnection( 
            "jdbc:mysql://localhost/core", 
            "core", 
            "67hTdGTpc3NZxFWc" 
        );
        SessionFactory.concreteFactory = Some( () =>
            Session.create( connection, new MySQLAdapter ) 
        );        
        return connection;
    }
    
   protected
    object Stockpile extends Schema {
        val personTable      = table[Person]  ( "person" );
        val loanTable        = table[Loan]    ( "loan" );
        val articleTable     = table[Article] ( "article" );
        val entityTable      = table[Entity]  ( "entity" );
        val codeTable        = table[Code]    ( "code" );
        
        on( personTable )( p => declare(
            // p.id    is( indexed )
            p.name is ( unique )
          )  
        )
        
        on( loanTable )( l => declare(
            // l.id    is( indexed )
          )  
        )
        
        on( articleTable )( a => declare(
            a.name is ( unique )
          )  
        )
        
        on( entityTable )( e => declare(
            // e.id    is( indexed )
          )  
        )
        
        on( codeTable )( c => declare(
            // c.id    is( indexed )
          )  
        )
    } 

    inTransaction {
        import Stockpile._;            
        drop
        create 
    }  
    
}

class PersonDatabase extends ElementDatabase {

    import Stockpile._;
    
    def addPerson( person: Person ) = { 
        connect;
        inTransaction {
            personTable.insert( person );
        }
    }
    
    def getPersonById( id: Int ) {
        connect;
        inTransaction {
            personTable.lookup( id );

        } 
    }
    
    def removePerson( person: Person ) = {
        connect;
        inTransaction {
            personTable.deleteWhere( p => p.name === person.name );
        }
    }
    

    
}