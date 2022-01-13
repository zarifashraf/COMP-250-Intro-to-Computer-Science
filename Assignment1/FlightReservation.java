package thepackage;


public class FlightReservation extends Reservation {
	private Airport departairport;
	private Airport arriveairport;
	
	public FlightReservation(String name1, Airport departairport, Airport arriveairport) {
		
		super(name1);
		
		int dist = Airport.getDistance(departairport, arriveairport);
		
		if(dist == 0) {
			throw new IllegalArgumentException("The departure and arrival airports are the same.");
		}
	
		else {
			this.reservationName();
			this.departairport = departairport;
			this.arriveairport = arriveairport;
		}
	
	}		

		public int getCost() {
			int dist = Airport.getDistance(departairport, arriveairport);
int Cost = (int) Math.ceil(((dist/167.52)*124) + (departairport.getFees()) + (arriveairport.getFees()) + 5375);
		return Cost;
		}

		public boolean equals(Object object) {
			{
				if (object instanceof FlightReservation && ((FlightReservation) object).reservationName() == this.reservationName()){
					return true;	
			}
				else {
					return false;
				}
			}
		
		
		}		
		
}
		
		



