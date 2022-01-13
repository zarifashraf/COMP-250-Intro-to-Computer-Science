package thepackage;


public class HotelReservation extends Reservation {
	private Hotel hotel1;
	private String roomtype;
	private int nights;
	private int priceofstay;

	public HotelReservation(String name2, Hotel hotel1, String roomtype, int nights) {
		
		super(name2);
		
		int x = hotel1.reserveRoom(roomtype);
		if ((x > 0)) {
			reservationName();
			this.hotel1 = hotel1;
			this.roomtype = roomtype;
			this.nights = nights;
		}
		
	}

	public int getNumOfNights() {
		return this.nights;
	}
	
	public int getCost() {
		priceofstay = hotel1.reserveRoom(roomtype) * nights;
		return this.priceofstay;
	}
	
	public boolean equals(Object object) {
if (object instanceof HotelReservation && ((HotelReservation) object).reservationName() == this.reservationName()){
	return true;
}
else {
	return false;
}
	
}

}

