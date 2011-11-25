package core;

// Property.scala

import scala.collection.mutable._;

class Property 
  ( val keyword: String, 
    val format: String   = "String" ,
    val default: String  = ""       , 
    val length: Int      = 255      ,
    val empty: Boolean   = true     ,
    val unique: Boolean  = false    , 
    val primary: Boolean = false     ) 
 extends Element {
    
   override
    def extract(): String = "\tProperty: " + this.keyword + " (" + this.length + ")";
    
    def to( datatype: Datatype )            = datatype.add( this ); 
    def of( datatype: Datatype )            = datatype.add( this );
    def describes( datatype: Datatype )     = datatype.add( this );
    
    def ::( property: Property ) = List( this, property );
}



class Datatype( val name: String = "datatype" ) extends Group[Property] {

   override
    def add( element: Property ): Unit = {
        if ( this.keywords contains element.keyword ) {
            EventLog.add ( new Error( 1, 
                "attempted to add a duplicate element: " + element, 
            this ) );
        }
        else {
            this.elements += element;
        }
    }
    
   override
    def extract = {
        "~ Datatype: '" + name + "' with properties: " + "(" + this.keywords.mkString( ", ") + ")";
    }
    
    def has( properties: List[Property] ) {
        for( p <- properties ) {
            this.add( p );
        }
    }
    
    
    def has( properties: Property* ) {
        for( p <- properties ) {
            this.add( p );
        }
    }
    
    
    def keywords: ListBuffer[String] = {
        this.elements.map( 
            e => ( e.keyword )
        )
    }
    def formats: ListBuffer[String] = {
        this.elements.map( 
            e => ( e.format )
        )
    }
    def length: ListBuffer[Int] = {
        this.elements.map( 
            e => ( e.length )
        )
    }
    def defaults: ListBuffer[String] = {
        this.elements.map( 
            e => ( e.default )
        )
    }
    def empties: ListBuffer[Boolean] = {
        this.elements.map( 
            e => ( e.empty )
        )
    }
    def uniques: ListBuffer[Boolean] = {
        this.elements.map( 
            e => ( e.unique )
        )
    }
    def primaries: ListBuffer[Boolean] = {
        this.elements.map( 
            e => ( e.primary )
        )
    }
    
    def formatsPairs: ListBuffer[(String,String)] = {
        this.keywords.zip( this.elements.map( 
            e => ( e.format )
        ) )
    }
    def lengthsPairs: ListBuffer[(String, Int)] = {
        this.keywords.zip( this.elements.map( 
            e => ( e.length )
        ) )
    }
    def defaultsPairs: ListBuffer[(String, String)] = {
        this.keywords.zip( this.elements.map( 
            e => ( e.default )
        ) )
    }
    def emptiesPairs: ListBuffer[(String, Boolean)] = {
        this.keywords.zip( this.elements.map( 
            e => ( e.empty )
        ) )
    }
    def uniquesPairs: ListBuffer[(String, Boolean)] = {
        this.keywords.zip( this.elements.map( 
            e => ( e.unique )
        ) )
    }
    def primariesPairs: ListBuffer[(String, Boolean)] = {
        this.keywords.zip( this.elements.map( 
            e => ( e.primary )
        ) )
    }
    
    def formatsMap: HashMap[String, String] = {
        val result: HashMap[String, String] = new HashMap();
        for( p <- this.formatsPairs ) {
            result += p;
        }
        return result;
    }
    def lengthsMap: HashMap[String, Int] = {
        val result: HashMap[String, Int] = new HashMap();
        for( p <- this.lengthsPairs ) {
            result += p;
        }
        return result;
    }
    def defaultsMap: HashMap[String, String] = {
        val result: HashMap[String, String] = new HashMap();
        for( p <- this.defaultsPairs ) {
            result += p;
        }
        return result;
    }
    def emptiesMap: HashMap[String, Boolean] = {
        val result: HashMap[String, Boolean] = new HashMap();
        for( p <- this.emptiesPairs ) {
            result += p;
        }
        return result;
    }
    def uniquesMap: HashMap[String, Boolean] = {
        val result: HashMap[String, Boolean] = new HashMap();
        for( p <- this.uniquesPairs ) {
            result += p;
        }
        return result;
    }
    def primariesMap: HashMap[String, Boolean] = {
        val result: HashMap[String, Boolean] = new HashMap();
        for( p <- this.primariesPairs ) {
            result += p;
        }
        return result;
    }
}
