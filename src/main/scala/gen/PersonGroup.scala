package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class PersonGroup ( 
	var name: String, 
	var person: Int
  ) extends Element with KeyedEntity[Int] {   

    def extract() = {
        this.toString;
    }

}