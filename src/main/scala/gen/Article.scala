package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Article ( 
	var name: String, 
	var description: String, 
	var value: Long = 0
  ) extends Element with KeyedEntity[Long] {
   
   override
    val id: Long = 0;

    def extract() = {
        this.toString;
    }


}