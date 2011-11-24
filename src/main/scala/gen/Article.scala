package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Article ( 
	var name: String, 
	var description: String
  ) extends Element with KeyedEntity[Int] {   
    val id = 0;

    def extract() = {
        this.toString;
    }

}