package core;

// Test.scala

import org.scalatest.Spec

import java.util.{ Date => Date };

class CoreSpec extends Spec {

    val datatype = new Datatype( "test" );

    describe( "A Datatype, as a subclass of Group" ) {
        it ( "can be created via a concise syntax" ) {
            assert( datatype.name === "test" );
        }
        it ( "accepts properties and returns their values" ) {            
            datatype has new Property( "a", "Int", 32 );
            datatype has new Property( "b", "String", 255 );
            datatype has new Property( "c", "Int", 65543 );
            datatype has new Property( "d", "String", 1 );
            datatype has new Property( "e", "Int", 2 );
            datatype has new Property( "f", "String", 3 );
            datatype has new Property( "g", "Int", 0 );
            
            assert( (datatype.keywords contains "a") === true );
        }
        it ( "does not alter the count of elements when a duplicate is added" ) {
            val originalSize = datatype.count;        
            datatype has new Property( "a", "Int", 32 );
            assert( originalSize === datatype.count );
        }
    }
    
    // var group:   Group[Element]   = new Group[Element]();
    // var element: TestElement      = new TestElement( "test", "square" );
    
    // describe( "Element as a part of a Group" ) {

        // it( "is instantiated and added to a group" ) {
            // element to group
            // assert( group.count === 1 );
        // }
        // it( "has the specified name" ) {
            // assert( element.name === "test" );
        // }    
    // }
    
    describe( "Database" ) {
    
        val personDatabase  = new PersonDatabase();
        val loanDatabase    = new LoanDatabase();
        val articleDatabase = new ArticleDatabase();
        val entityDatabase  = new EntityDatabase();
        val codeDatabase    = new CodeDatabase();
    
        it ( "for Persons is created and functions" ) {
        
            try {
                personDatabase.addPerson( new Person( "Adam Kesher" ) );
                personDatabase.addPerson( new Person( "Ola Nordvik" ) );
                personDatabase.addPerson( new Person( "Jens Havik" ) );
                personDatabase.addPerson( new Person( "Terrence Hogan" ) );
                personDatabase.addPerson( new Person( "David Murray" ) );
                personDatabase.addPerson( new Person( "John Carr" ) );
                personDatabase.addPerson( new Person( "Elvis Santiago" ) );
                personDatabase.addPerson( new Person( "Jesus Geist" ) );
                personDatabase.addPerson( new Person( "Emilio Stevens" ) );
                
                personDatabase.removePerson( new Person( "Adam Kesher" ) );
                personDatabase.removePerson( new Person( "Ola Nordvik" ) );
                personDatabase.removePerson( new Person( "Jens Havik" ) );
                personDatabase.removePerson( new Person( "Terrence Hogan" ) );
                personDatabase.removePerson( new Person( "David Murray" ) );
                personDatabase.removePerson( new Person( "John Carr" ) );
                personDatabase.removePerson( new Person( "Elvis Santiago" ) );
                personDatabase.removePerson( new Person( "Jesus Geist" ) );
                personDatabase.removePerson( new Person( "Emilio Stevens" ) );
                
            } catch {
                case e: java.lang.RuntimeException => ()
            }
            
            var found: Boolean = true;
            
            try {
                var john = personDatabase.getPersonByName( "John Carr" ).get;
            } catch {
                case e: java.util.NoSuchElementException => 
                            ( found = false )
            }

            assert( found === false );
            
        }

        it ( "for Loans accepts new instances, removes and keeps the entity unique" ) {
            
            var loan = new Loan(
                1, 2, 4, 
                new Date, new Date, new Date, new Date,
                "location", "none", "purpose"
            )
            
            
            // loanDatabase.addLoan( loan );
            
            try { 
                loanDatabase.addLoan( loan );
            } catch {
                case e: java.lang.RuntimeException => ()
            }
            
            
            loanDatabase.removeLoan( loan );
            loanDatabase.getLoanById( loan.id );
            
        }
    }    
    
}