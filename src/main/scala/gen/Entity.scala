package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Entity ( 
	var article: Int
  ) extends Element with KeyedEntity[Int] {
   
   override
    val id = 0;

    def extract() = {
        this.toString;
    }


}