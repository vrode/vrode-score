package core; 

// Manager.scala

import scala.collection.mutable._;

import java.io.{ File, FileWriter }


object Manager { 
    val path: String = Configuration.projectLocation; 
    val log:  String = Configuration.logLocation; 
    
/*    def toFile( name: String, datatype: Datatype, superclass: String = "" ) {
        def write( path: String, name: String, content: String ) {
            val fw = new FileWriter( path + "/" + name + "E lement.scala" ); 
                fw.write( content ); 
                fw.close();
        }
        def buildConstructor( keywords: Set[String], formats: List[String] ): String = {
            var tuples = (keywords zip formats)            
            val result = tuples.map( 
                     t => ( "var " + (t _1) + ": " + (t _2) )  
            )
            return "( datatype: Datatype, " + result.mkString( ", " ) + " )";
        }
        def buildName( name: String ): String = {
            var capitalized = ( s: String ) 
                            => ( 
                                ( s(0).toUpper ) + s.substring( 1, s.length ).toLowerCase
                                )
            return "class " + capitalized( name ) + "Element";
        }
        def buildInheritance(): String = {
            return "\n  extends Element()";
        }
        def buildBody(): String = {
        
            return """  | {    
                        |   override
                        |    def extract(): String = {
                        |        ""
                        |    }
                        |   
                        |    def keywords(): Set[ String ] = {
                        |        // return datatype.keywords;
                        |        val fields = ( this.getClass.getDeclaredFields() )
                        |        val result = ( for ( field <- fields ) yield {
                        |            field.setAccessible( true );
                        |            ( field.getName );
                        |        } ).toSet
                        |        result;
                        |    }
                        |    
                        |    def values() = {
                        |        val fields = ( this.getClass.getDeclaredFields() )
                        |        val result = ( for ( field <- fields ) yield {
                        |            field.setAccessible( true );
                        |            ( field.getName, field.get( this ) );
                        |        } ).toSet;
                        |        result;
                        |    }
                        |    
                        |    def to( group: Group ) = group.add( this );
                        | }""" stripMargin
        }
          
        write( this.path, name, 
                buildName( name ) +
                buildConstructor( datatype.keywords, datatype.formats ) +
                buildInheritance( ) +
                buildBody
        );
    
    }*/
}
