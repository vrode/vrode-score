package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Person ( 
	var name: String, 
	var phone: Long = 0
  ) extends Element with KeyedEntity[Long] {
   
   override
    val id: Long = 0;

    def extract() = {
        this.toString;
    }


}