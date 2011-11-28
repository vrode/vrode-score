package core;

// Database.scala

import scala.collection.mutable._;

import java.sql.{ DriverManager, Connection };
import java.util.Properties;

import com.mysql.jdbc.Driver;
import org.postgresql.Driver;

import org.squeryl._;
import org.squeryl.Schema;
import org.squeryl.adapters._;
import org.squeryl.SessionFactory;
import org.squeryl.annotations.Column;
import org.squeryl.PrimitiveTypeMode._;
import org.squeryl.internals.DatabaseAdapter;


/**
 *  The concrete implementations of Database-object no longer drop
 *  the whole database. But only the table they define.
 */
abstract
 class ElementDatabase extends Schema {
    
    // default to MySQL, changed when connect() is called with parameter
    var adapter: DatabaseAdapter = new MySQLAdapter; 
   
    SessionFactory.concreteFactory = Some( () =>
        Session.create( connect(), adapter ) 
    ); 
   
   protected // check if wirks with PostgreSQL constructed in this fashion
    def connect( databaseType: String = "" ): Connection = databaseType match {
        
        case "mysql" => {
           
            this.adapter = new MySQLAdapter;
           
            Class.forName("com.mysql.jdbc.Driver");
            
            val properties: Properties = new Properties();
                properties.setProperty( "user", "core" );
                properties.setProperty( "password", "67hTdGTpc3NZxFWc" ); 
                properties.setProperty( "autoReconnect", "true" ); 
                properties.setProperty( "maxReconnects", "100" );  
                
            val connection: Connection =
            DriverManager.getConnection( 
                "jdbc:mysql://localhost/core", 
                 properties
            );
            
            return connection;
                
        }
    
        case "postgresql" => {
            
            this.adapter = new PostgreSqlAdapter;
            
            Class.forName("org.postgresql.Driver");

            val properties: Properties = new Properties();
                properties.setProperty( "user", "core" );
                properties.setProperty( "password", "67hTdGTpc3NZxFWc" ); 
            
            val connection: Connection =
            DriverManager.getConnection( 
                "jdbc:postgresql://146.247.221.160/core", 
                 properties
            );
            
            SessionFactory.concreteFactory = Some( () =>
                Session.create( connection, new PostgreSqlAdapter ) 
            );    
            
            return connection;
        }
        
        case _ => this.connect( "mysql" );
    }
    
    def reset = {
        transaction {
            drop
            create
        }
    }
    
}

object PersonDatabase extends ElementDatabase {
 
    val table: Table[Person] = super.table[Person];      
    
    on( table )( 
      p => declare(
        p.id is ( unique  ),
        p.name is ( unique )
     // p.email defaultsTo( "" )
      )  
    )
    
    def addPerson( person: Person ) = {
        connect();
        inTransaction {
            table.insert( person );
        }
    }
    
    def getPersonById( id: Long ): Option[Person] = {
        connect();
        inTransaction {
            val candidate = table.lookup( id ).get;

            if ( candidate.isInstanceOf[Person] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getPersonByName( name: String ): Option[Person] = {
        connect();
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
        connect();
        inTransaction {
            table.deleteWhere( p => p.name === person.name );
        }
    }
    
}

object LoanDatabase extends ElementDatabase {
    
    val table: Table[Loan] = super.table[Loan];

    on( table )( 
      l => declare(
        l.entity is ( unique ) 
      )  
    )
    
    def addLoan( loan: Loan ) = {
        connect();
        inTransaction {
            table.insert( loan );
        }
    }

    def getLoanById( id: Long ): Option[Loan] = {
        connect();
        inTransaction {
            val candidate = table.lookup( id ).get;

            if ( candidate.isInstanceOf[Loan] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getLoansTo( person: Person ): List[Loan] = {
        connect();
        inTransaction {
            val candidates = table.where( l => person.id === l.toPerson )
            return candidates.toList;
        }
    }
    
    def getLoansFrom( person: Person ): List[Loan] = {
        connect();
        inTransaction {
            val candidates = table.where( l => person.id === l.fromPerson )
            return candidates.toList;            
        }
    }
    
    def removeLoansByEntity( entity: Entity ) = {
        connect();
        inTransaction {
            table.deleteWhere( l => l.entity === entity.id );         
        }       
    }    
    
    def removeLoansTo( person: Person ) = {
        connect();
        inTransaction {
            table.deleteWhere( l => l.toPerson === person.id );         
        }    
    }
    
    def removeLoan( loan: Loan ) {
        connect();
        inTransaction {
            table.deleteWhere( l => l.id === loan.id );         
        }     
    }
}

object EntityDatabase extends ElementDatabase {
    
    val table: Table[Entity] = super.table[Entity];

    on( table )( 
      e => declare(
        e.id is ( unique ) 
      )  
    )
    
    def addEntity( entity: Entity ) {
        connect();
        inTransaction {
            table.insert( entity )
        }
    }
    
    def getEntitiesByArticle( article: Article ): List[Entity] = {
        connect();
        inTransaction {
            val candidates = table.where( e => e.article === article.id );
            return candidates.toList;
        }
    }
    
}

object ArticleDatabase extends ElementDatabase {
    
    val table: Table[Article] = super.table[Article];
    
    on( table )( 
      a => declare(
        a.name is ( unique ),
        a.value defaultsTo( 0L )
      )  
    )    
    
    def addArticle( article: Article ) {
        connect();
        inTransaction {
            table.insert( article )
        }
    }

    def getArticleById( id: Long ): Option[Article] = {
        connect();
        inTransaction {
            val candidate = table.lookup( id ).get;
            if ( candidate.isInstanceOf[Article] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getArticleByName( articleName: String ): Option[Article] = {
        connect();
        inTransaction {
            val candidate = table.where( a => articleName === a.name );
            return Some( candidate.toList.head );
        }
    }
    
    def removeArticle( article: Article ) {
        connect();
        inTransaction {
            table.deleteWhere( a => a.id === article.id );
        }
    }
    

}

object CodeDatabase extends ElementDatabase {
    
    val table: Table[Code] = super.table[Code];

    on( table )( 
      c => declare(
        c.code    is( unique )
      )  
    )
    
    def addCode( code: Code ) {
        connect();
        inTransaction {
            table.insert( code )
        }
    }
    
    def getCodesByEntity( entity: Entity ) {
        connect();
        inTransaction {
            val candidates = table.where( c => c.entity === entity.id );
        }
    }
    
    def removeCode( code: Code ) {
        connect();
        inTransaction {
            table.deleteWhere( c => c.id === code.id );
        }
    }    

}




class GenericDatabase[E <: Element] extends ElementDatabase {
    
    // not sure if works
    def table[E] = super.table; 
    
    def addElement( element: E ) {
        connect();
        val table = findTablesFor[E]( element ).head
        inTransaction {
            table.insert( element );
        }
    }
    
    def getElement( element: E ) = {
        connect();
        val table = findTablesFor[E]( element ).head
        inTransaction {
            table.where( e => element.id === e.id )
        }
    }
    
    def getElementById( id: Long ): Option[E] = {
        connect();
        val candidate: E = table.lookup( id ).get;

        if ( candidate.isInstanceOf[E] )
            return Some( candidate );
        else
            return None;       
    
    }
    
    def removeElement( element: E ) {
        connect();
        val table = findTablesFor[E]( element ).head
        inTransaction {
            table.deleteWhere( e => e.id === element.id )
        }
    }
}