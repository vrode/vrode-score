package core;

// Element.scala

import scala.collection.mutable._;
import org.squeryl.KeyedEntity;

abstract 
 class Element extends KeyedEntity[Int] {
    
    val id: Int = 0;
 
    def extract: String
    def to( group: Group[Element] ) = {
        group.add( this );
    }
}

class Group[T <: Element] extends Element {

   override
    val id: Int = 0;
    
    val elements: ListBuffer[T] = new ListBuffer();
    
    def add( element: T ): Unit = {
        this.elements += element;
    }
    
    def add( list: List[T] ): Unit = {
        this.elements.appendAll( list );
    }
    
    def addUnique( element: T ): Unit = {
        if ( this.elements contains element ) {
            EventLog.add ( 
                new Error( 1, "attempted to add a duplicate element: " + element, this ) 
            );
        }
        else {
            this.elements += element;
        }
    }
    
   override
    def extract: String = {
        this.elements.map( e => ( e.extract ) ).mkString;
    }
    def count: Int = this.elements.size;
}