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
 class ElementDatabase {
    
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
            // entity is a unique representation of article
            // for instance: cable#25 is an entity of "cable"-article
            l.entity is ( unique ) 
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
        // drop
        // create 
    }  
    
}

class PersonDatabase extends ElementDatabase {

    import Stockpile._;
    val table = personTable;
    
    // Singular methods apply only to a single element,
    // deals with ambiguity by fetching the first returned row
    
    def addPerson( person: Person ) = { 
        connect;
        inTransaction {
            table.insert( person );
        }
    }
    
    def getPersonById( id: Int ): Option[Person] = {
        connect;
        inTransaction {
            val candidate = table.lookup( id ).get;

            if ( candidate.isInstanceOf[Person] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getPersonByName( name: String ): Option[Person] = {
        connect;
        inTransaction {
            val candidate = table.where( p => p.name === name ).single;
            
            if ( candidate.isInstanceOf[Person] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getPerson( person: Person ): Person = {
        // [+]
        // This method should accept a blueprint and return a person
        // this way it will check all properties a blueprint has
        // and then return a person that fit them exactly,
        // or return the blueprint back without any changes.
        // When a blueprint is returned, it should be checked
        // for equality with the one that is sent it.
        // Equality of blueprint and return would mean that
        // the function didn't find anything.
        new Person( "" );
    }
    
    
    def removePerson( person: Person ) = {
        connect;
        inTransaction {
            table.deleteWhere( p => p.name === person.name );
        }
    }
    
}

class LoanDatabase extends ElementDatabase {

    import Stockpile._
    
    val table = loanTable;
    
    def addLoan( loan: Loan ) = {
        connect;
        inTransaction {
            table.insert( loan );
        }
    }
    
    def getLoansToPerson( person: Person ): List[Loan] = {
        connect;
        inTransaction {
            val candidate = table.where( l => person.id === l.toPerson )
            return candidate.toList;
        }
    }
    
    def getLoansFrom( person: Person ): List[Loan] = {
        connect;
        inTransaction {
            val candidate = table.where( l => person.id === l.fromPerson )
            return candidate.toList;            
        }
    }
    
    def removeLoansByEntity( entity: Entity ) = {
        connect;
        inTransaction {
            table.deleteWhere( l => l.entity === entity.id );         
        }       
    }
    
    def removeLoansTo( person: Person ) = {
        connect;
        inTransaction {
            table.deleteWhere( l => l.toPerson === person.id );         
        }    
    }
    
    def removeLoan( loan: Loan ) {
        connect;
        inTransaction {
            table.deleteWhere( l => l.id === loan.id );         
        }     
    }

}

class EntityDatabase extends ElementDatabase {

    import Stockpile._;
    
    var table = entityTable;

    
    def addEntity( entity: Entity ) {
        connect;
        inTransaction {
            table.insert( entity )
        }
    }
    
}

class ArticleDatabase extends ElementDatabase {

    import Stockpile._;
    
    var table = articleTable;
    
    def addArticle( article: Article ) {
        connect;
        inTransaction {
            table.insert( article )
        }
    }
    
    def removeArticle( article: Article ) {
        connect;
        inTransaction {
            table.deleteWhere( a => a.id === article.id );
        }
    }
    

}

class CodeDatabase extends ElementDatabase {

    import Stockpile._;
    
    var table = codeTable;
    
    def addCode( code: Code ) {
        connect;
        inTransaction {
            table.insert( code )
        }
    }    

}


class GenericDatabase[E <: Element] ( table: Table[E] ) extends ElementDatabase {

    import Stockpile._;
        
    def addElement( element: E ) {
        connect;
        inTransaction {
            table.insert( element );
        }
    }
    
    def getElement( element: E ) {
        connect;
        inTransaction {
            table.where( e => element.id === e.id )
        }
    }
    
    def removeElement( element: E ) {
        connect;
        inTransaction {
            table.deleteWhere( e => e.id === element.id )
        }
    }  
}