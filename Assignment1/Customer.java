package thepackage;


public class Customer {
	private String customername;
	private int customerbalance;
	private Basket customerbasket;
	
	public Customer(String customername, int customerbalance) {
		this.customername = customername;
		this.customerbalance = customerbalance;
		this.customerbasket = new Basket();
		
	}
	
	public String getName() {
		return this.customername;
	}
	
	public int getBalance() {
		return this.customerbalance;
	}
	
	public Basket getBasket() {
		return this.customerbasket;
	}
	
	
	
	public int addFunds(int balance) {
		if(balance <0) {
			throw new IllegalArgumentException("You may not add a negative balance.");
		}
		else {
			customerbalance = customerbalance + balance;
			return this.customerbalance;
		}
	}
	
	public int addToBasket(Reservation l) {
		if(l.reservationName().equalsIgnoreCase(this.customername)) {
			int a = customerbasket.add(l);
			return a;
		}
		else {
			throw new IllegalArgumentException("Reservation unsuccessful.");
		}
	 
	}
	
	public int addToBasket(Hotel hotel2, String roomtype, int nights, boolean food) {
		if (food == true) {
			BnBReservation b = new BnBReservation(customername, hotel2, roomtype, nights);
			int c = this.customerbasket.add(b);
			return c;
		}
		else {
			HotelReservation d = new HotelReservation(customername, hotel2, roomtype, nights);
			int e = this.customerbasket.add(d);
			return e;
		}
		
	}
	
	public int addToBasket(Airport firstairport, Airport secondairport) {
		FlightReservation a = new FlightReservation(customername, firstairport, secondairport);
		int f = this.customerbasket.add(a);
		return f;
	}
	
	public boolean removeFromBasket(Reservation g) {
		boolean removed = this.customerbasket.remove(g);
		return removed;
	}
	
	public int checkOut() {
		int totalcost = this.customerbasket.getTotalCost();
			
		if (customerbalance < totalcost) {
			throw new IllegalStateException("Shortage of customerbalance for making this reservation");
		}
		else {
			int balanceleft = customerbalance - totalcost;
			this.customerbasket = new Basket();
			return balanceleft;
		}
		
	}

}

