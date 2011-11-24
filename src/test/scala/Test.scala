package core;

// Test.scala

import org.scalatest.Spec

class CoreSpec extends Spec {

    val datatype = new Datatype( "test" );

    describe( "A Datatype, as a subclass of Group" ) {
        it ( "[5 points] can be created via a concise syntax" ) {
            assert( datatype.name === "test" );
        }
        it ( "[10 points] accepts properties and returns their values" ) {            
            datatype has new Property( "a", "Int", 32 );
            datatype has new Property( "b", "String", 255 );
            datatype has new Property( "c", "Int", 65543 );
            datatype has new Property( "d", "String", 1 );
            datatype has new Property( "e", "Int", 2 );
            datatype has new Property( "f", "String", 3 );
            datatype has new Property( "g", "Int", 0 );
            
            assert( (datatype.keywords contains "a") === true );
        }
        it ( "[2 points] does not alter the count of elements when a duplicate is added" ) {
            val originalSize = datatype.count;        
            datatype has new Property( "a", "Int", 32 );
            assert( originalSize === datatype.count );
        }
    }
    
    var group:   Group[Element]   = new Group[Element]();
    var element: TestElement      = new TestElement( "test", "square" );
    
    describe( "Element as a part of a Group" ) {

        it( "is instantiated and added to a group" ) {
            element to group
            assert( group.count === 1 );
        }
        it( "has the specified name" ) {
            assert( element.name === "test" );
        }    
    }
}