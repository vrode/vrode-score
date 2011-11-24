package core;

// Link.scala

import scala.collection.mutable._

class Link extends Element {

    var source: AnyRef = "source";
    var target: AnyRef = "target";

   override
    def extract(  ) = {
        "<" + source + ", " + target + ">"
    }    
}