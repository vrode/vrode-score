package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Code ( 
	var code: String, 
	var family: String, 
	var entity: Long
  ) extends Element with KeyedEntity[Long] {
   
   override
    val id: Long = 0;

    def extract() = {
        this.toString;
    }


}