package thepackage;


public class BnBReservation extends HotelReservation {

	public BnBReservation(String name3, Hotel hotel2, String roomtype, int nights) {
		super(name3, hotel2, roomtype, nights);
		
	}

	public int getCost() {
		int money = ((super.getCost()) + (1000 * super.getNumOfNights()));
		return money;
	}

}

