package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Entity ( 
	var article: Long, 
	var state: Double = 1
  ) extends Element with KeyedEntity[Long] {
   
   override
    val id: Long = 0;

    def extract() = {
        this.toString;
    }


}