package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Code ( 
	var code: String, 
	var family: String, 
	var entity: Int
  ) extends Element with KeyedEntity[Int] {   

    def extract() = {
        this.toString;
    }

}