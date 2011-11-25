package core;

// Generator.scala

import scala.collection.mutable._;
import java.io.{ File, FileWriter};



object Generator {
    def write( datatype: Datatype, path: String = "..\\gen" ) {
        val content = ( 
            new VirtualClass( datatype ) 
        ).toString;
        
        val fw = new FileWriter( 
                Configuration.projectLocation + "\\" + datatype.name.capitalize + ".scala" ); 
            fw.write( content ); 
            fw.close();
    }
}

trait Indented { 
    var indentation: Int = 0; 
    // implement methods to manipulate indentation
}

trait SingleIndentation { var indentation: Int = 1 }
trait DoubleIndentation { var indentation: Int = 2 }
trait TripleIndentation { var indentation: Int = 3 }


// 1st Level Class
abstract
 class Segment extends Group[Element] {
    
   override
    val id: Int = 0;
    
    def header: String;
    def root:   String;
    def prefix: String = "{";
    def suffix: String = "}";
    
    
   override 
    def toString = {
        header + prefix + 
            "\n" + 
        root + 
            "\n" +
        suffix
    }
}

// 2nd Level Class
class Block extends Group[Line] with SingleIndentation {}

class IndentedBlock extends Block {
   override
    def add( element: Line ) = {
        element.indentation = this.indentation;
        this.elements += element;
    }
}

// 3rd Level Class
// To put a line into the Second Level - put it in a simple, non-tabbed block
class Line extends Element with TripleIndentation {
    
   override
    val id: Int = 0;

    val contents: String = "";
    def length = { contents.size }
    def extract = { contents }
}

// 2-4th Level Class
class Word( val word: String = "" ) extends Element with Indented {
   override 
    val id: Int = 0;

   override
    def extract() = this.word;
   override
    def toString() = this.word;
}

class Listing
    ( elements: ListBuffer[(String)] ) 
  extends Line {
    
    val prefix:     String = "( ";
    val separator:  String = ", ";
    val suffix:     String = " )";
    
   override
    def toString = prefix + ( elements.mkString( separator ) ) + suffix;
}















class VirtualClass ( datatype: Datatype ) extends Segment {
   
   override
    val id: Int = 0;
    
   override
    def header  = { 
        val parameters: ListBuffer[ (String,String,String) ] =
            // makes a 3-tuple of parameter-necessary datatype properties
            ( datatype.keywords, datatype.formats, datatype.defaults ).zipped.map{ (_, _, _) }; 
        
        val parents = new ListBuffer[String];
         parents += "Element with KeyedEntity[Int]";
        
        // imports 
        "package core;\n\n\nimport java.util.{ Date => Date };\nimport org.squeryl.KeyedEntity;\n\n\n" +
        ( new Declaration( parameters, datatype.name, "", parents ) ).toString;
    }
    
   override
    def prefix  = "{";

   override
    def root  = """|   
                   |
                   |    val id = 0;
                   |
                   |    def extract() = {
                   |        this.toString;
                   |    }
                   |
                   |""".stripMargin;
    
   override
    def suffix  = "}";
    
    
   override
    def toString = {
        header + prefix + root + suffix;
    }
    
    class Declaration(  // [/] extends Listing
        elements:   ListBuffer[(String, String, String )], // parameters
        name:       String, 
        scope:      String  = "",
        parents:    ListBuffer[String],
        header:     String  = "",
        prefix:     String  = "( ",
        suffix:     String  = " )",
        separator:  String  = ", " 
      ) 
    {

       override 
        def toString: String = {
            // class scope {private, protected}
            scope +
            // class header
            "class " + name.capitalize + " " + prefix +
             { // assigns parameters by creating a list and joining it (mkString)
              for( e <- elements ) 
              yield "\n\tvar " + (e _1) + 
                    ": " + (e _2) + 
                    {   // assigns default value
                        if ( (e _3) != "") " = " + (e _3); 
                        else "" 
                    }
            }.mkString( separator ) + "\n " +
            suffix + " " +
            {
                "extends " +
                { if ( parents.size != 0 ) parents.mkString( " with ");
                  else "";
                }
            
            } + " "
        }
        
    }
 
    // [+]
    class Function() {}
 
}















































