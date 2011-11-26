package core;


import java.util.{ Date => Date };
import org.squeryl.KeyedEntity;


class Loan ( 
	var entity: Long, 
	var fromPerson: Long, 
	var toPerson: Long, 
	var timeOrdered: Date, 
	var timeFetched: Date, 
	var timeExpired: Date, 
	var timeReturned: Date, 
	var location: String, 
	var damage: String, 
	var purpose: String
  ) extends Element with KeyedEntity[Long] {
   
   override
    val id: Long = 0;

    def extract() = {
        this.toString;
    }


}