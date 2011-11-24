package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class ArticleGroup ( 
	var name: String, 
	var article: Int
  ) extends Element with KeyedEntity[Int] {   
    val id = 0;

    def extract() = {
        this.toString;
    }

}