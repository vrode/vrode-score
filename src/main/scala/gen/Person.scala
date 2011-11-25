package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Person ( 
	var name: String
  ) extends Element with KeyedEntity[Int] {   

    def extract() = {
        this.toString;
    }

}