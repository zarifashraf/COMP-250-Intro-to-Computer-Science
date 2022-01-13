package thepackage;


public class Basket {
	private Reservation[] reservelist;

public Basket() {
	reservelist = new Reservation[] { };
}

public Reservation[] getProducts() {
	
	Reservation[] renewedlist = new Reservation[reservelist.length];
	for (int i = 0; i < reservelist.length; i++) {
		renewedlist[i] = reservelist[i];
		}
	return renewedlist;
}

public int add(Reservation r) {
	try {
		int i = reservelist.length ; }
	catch (ArrayIndexOutOfBoundsException e){
		this.reservelist = new Reservation[] {r};
	}
	
	
	int z = ((reservelist.length) + 1);
	Reservation[] newarray = new Reservation[z];
	for (int i = 0; i < (reservelist.length); i++) {
		newarray[i] = reservelist[i];
		}
	newarray[z-1] = r;
	this.reservelist = newarray;
	return z;
}

public boolean remove(Reservation q) {
	if (reservelist.length == 0) {
		throw new IllegalArgumentException("There are no reservations to remove.");
	}
	int o = (reservelist.length) - 1;
	int i = 0;
	String alpha = q.reservationName();
	Reservation[] smallarray = new Reservation[o];
	 for (int index = 0, k = 0; index < reservelist.length; index ++) {
		 String thename = reservelist[index].reservationName();
		 	if(!thename.equalsIgnoreCase(alpha)) {
		 			smallarray[k++] = reservelist[index];
		 	}
		 	else if (thename.equalsIgnoreCase(alpha)) {
				 i = i + 1;
				 continue;
				 }
		 	}
	 	if (i == 0 || reservelist == null) {
	return false;
	}
	else {
		this.reservelist = smallarray;
		return true;
	}
 
}

public void clear() {
	this.reservelist = null;
}

public int getNumOfReservations() {
	return this.reservelist.length;
}

public int getTotalCost() {
	int cumCost = 0;
	
	for (int i = 0; i < reservelist.length; i++) {
		int currentcost = reservelist[i].getCost();
		cumCost = cumCost + currentcost;
	}
	return cumCost;
	}
}

