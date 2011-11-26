package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class PersonGroup ( 
	var name: String, 
	var person: Long
  ) extends Element with KeyedEntity[Long] {
   
   override
    val id: Long = 0;

    def extract() = {
        this.toString;
    }


}