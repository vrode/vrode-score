package core;

// Database.scala

import scala.collection.mutable._;

import java.sql.{ DriverManager, Connection };

import com.mysql.jdbc.Driver;

import org.squeryl._;
import org.squeryl.Schema;
import org.squeryl.adapters._;
import org.squeryl.SessionFactory;
import org.squeryl.annotations.Column;
import org.squeryl.PrimitiveTypeMode._;
import org.squeryl.internals.FieldMetaData;



abstract
 class ElementDatabase extends Schema {
    
    connect;
    initialize;
    
   protected
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
    
    def initialize() {
        
        inTransaction {
            drop
            create 
        }
    }
}

object PersonDatabase extends ElementDatabase {
 
    connect;
 
    val table: Table[Person] = super.table[Person];
    
    on( table )( 
      p => declare(
        p.id is ( unique  ),
        p.name is ( unique ),
        p.email defaultsTo( "" )
      )  
    )    
    
    def addPerson( person: Person ) = { 
        connect;
        transaction {
            table.insert( person );
        }
    }
    
    def getPersonById( id: Long ): Option[Person] = {
        
        inTransaction {
            val candidate = table.lookup( id ).get;

            if ( candidate.isInstanceOf[Person] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getPersonByName( name: String ): Option[Person] = {
        
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
        transaction {
            table.deleteWhere( p => p.name === person.name );
        }
    }
    
}

object LoanDatabase extends ElementDatabase {

    connect;    
    val table: Table[Loan] = super.table[Loan];

    on( table )( 
      l => declare(
        l.entity is ( unique ) 
      )  
    )
    
    def addLoan( loan: Loan ) = {
        
        inTransaction {
            table.insert( loan );
        }
    }

    def getLoanById( id: Long ): Option[Loan] = {
        
        inTransaction {
            val candidate = table.lookup( id ).get;

            if ( candidate.isInstanceOf[Loan] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getLoansTo( person: Person ): List[Loan] = {
        
        inTransaction {
            val candidates = table.where( l => person.id === l.toPerson )
            return candidates.toList;
        }
    }
    
    def getLoansFrom( person: Person ): List[Loan] = {
        
        inTransaction {
            val candidates = table.where( l => person.id === l.fromPerson )
            return candidates.toList;            
        }
    }
    
    def removeLoansByEntity( entity: Entity ) = {
        
        inTransaction {
            table.deleteWhere( l => l.entity === entity.id );         
        }       
    }    
    
    def removeLoansTo( person: Person ) = {
        
        inTransaction {
            table.deleteWhere( l => l.toPerson === person.id );         
        }    
    }
    
    def removeLoan( loan: Loan ) {
        
        inTransaction {
            table.deleteWhere( l => l.id === loan.id );         
        }     
    }
}

object EntityDatabase extends ElementDatabase {

    connect;
    val table: Table[Entity] = super.table[Entity];

    on( table )( 
      e => declare(
        // e.entity is ( unique ) 
      )  
    )
    
    def addEntity( entity: Entity ) {
        
        inTransaction {
            table.insert( entity )
        }
    }
    
    def getEntitiesByArticle( article: Article ): List[Entity] = {
        
        inTransaction {
            val candidates = table.where( e => e.article === article.id );
            return candidates.toList;
        }
    }
    
}

object ArticleDatabase extends ElementDatabase {

    connect;    
    val table: Table[Article] = super.table[Article];
    
    on( table )( 
      a => declare(
        a.name is ( unique ),
        a.value defaultsTo( 0L )
      )  
    )    
    
    def addArticle( article: Article ) {
        
        inTransaction {
            table.insert( article )
        }
    }

    def getArticleById( id: Long ): Option[Article] = {
        
        inTransaction {
            val candidate = table.lookup( id ).get;
            if ( candidate.isInstanceOf[Article] )
                return Some( candidate );
            else
                return None;
        }
    }
    
    def getArticleByName( articleName: String ): Option[Article] = {
        
        inTransaction {
            val candidate = table.where( a => articleName === a.name );
            return Some( candidate.toList.head );
        }
    }
    
    def removeArticle( article: Article ) {
        
        inTransaction {
            table.deleteWhere( a => a.id === article.id );
        }
    }
    

}

object CodeDatabase extends ElementDatabase {

    connect;    
    val table: Table[Code] = super.table[Code];

    on( table )( 
      c => declare(
        // c.id    is( indexed )
      )  
    )
    
    def addCode( code: Code ) {
        
        inTransaction {
            table.insert( code )
        }
    }
    
    def getCodesByEntity( entity: Entity ) {
        
        inTransaction {
            val candidates = table.where( c => c.entity === entity.id );
        }
    }
    
    def removeCode( code: Code ) {
        
        inTransaction {
            table.deleteWhere( c => c.id === code.id );
        }
    }    

}




class GenericDatabase[E <: Element] extends ElementDatabase {
    
    // not sure if works
    def table[E] = super.table; 
    
    def addElement( element: E ) {
        
        val table = findTablesFor[E]( element ).head
        inTransaction {
            table.insert( element );
        }
    }
    
    def getElement( element: E ) = {
        
        val table = findTablesFor[E]( element ).head
        inTransaction {
            table.where( e => element.id === e.id )
        }
    }
    
    def getElementById( id: Long ): Option[E] = {
        
        val candidate: E = table.lookup( id ).get;

        if ( candidate.isInstanceOf[E] )
            return Some( candidate );
        else
            return None;       
    
    }
    
    def removeElement( element: E ) {
        
        val table = findTablesFor[E]( element ).head
        inTransaction {
            table.deleteWhere( e => e.id === element.id )
        }
    }
}