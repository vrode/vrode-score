package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class ArticleGroup ( 
	var name: String, 
	var article: Long
  ) extends Element with KeyedEntity[Long] {
   
   override
    val id: Long = 0;

    def extract() = {
        this.toString;
    }


}