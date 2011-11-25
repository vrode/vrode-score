package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Loan ( 
	var entity: Int, 
	var fromPerson: Int, 
	var toPerson: Int, 
	var timeOrdered: Date, 
	var timeFetched: Date, 
	var timeExpired: Date, 
	var timeReturned: Date, 
	var location: String, 
	var damage: String, 
	var purpose: String
  ) extends Element with KeyedEntity[Int] {
   
   override
    val id = 0;

    def extract() = {
        this.toString;
    }


}