package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Person ( 
	var name: String
  ) extends Element with KeyedEntity[Int] {   
    val id = 0;

    def extract() = {
        this.toString;
    }

}