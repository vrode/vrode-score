package core;

// Configuration.scala

import scala.collection.mutable._;


object Configuration {
    val projectName:     String = "core";
    val projectLocation: String = "src/main/scala/gen";
    val logLocation:     String = "src/main/scala/log";
    
    val currentEventCode = 0;
    
    
    trait  TYPE {}
    object NUMBER extends TYPE {}
    object DIGIT extends TYPE {}
    object TEXT extends TYPE {}
    object TIME extends TYPE {}
    object LINK extends TYPE {}
    object MAIN extends TYPE {}
    object WORD extends TYPE {}
    
    // [+] Try to implement properties as traits 1p.
    
}

