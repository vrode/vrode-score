package core;

// Link.scala

import scala.collection.mutable._

class Link extends Element {
    
   override
    val id: Long = 0;
   
    var source: AnyRef = "source";
    var target: AnyRef = "target";

   override
    def extract(  ) = {
        "<" + source + ", " + target + ">"
    }    
}