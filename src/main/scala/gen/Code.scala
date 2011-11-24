package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Code ( 
	var code: String, 
	var family: String, 
	var entity: Int
  ) extends Element with KeyedEntity[Int] {   
    val id = 0;

    def extract() = {
        this.toString;
    }

}