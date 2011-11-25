package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Article ( 
	var name: String, 
	var description: String, 
	var value: Int = 0
  ) extends Element with KeyedEntity[Int] {
   
   override
    val id = 0;

    def extract() = {
        this.toString;
    }


}