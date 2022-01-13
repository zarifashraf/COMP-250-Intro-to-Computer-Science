package thepackage;



public class Room {
	private String roomtype;
	private int roomprice;
	private boolean roomavail;

	public Room(String roomtype) {
		
		if (roomtype.equalsIgnoreCase("Double")) {
			this.roomtype = roomtype;
			this.roomavail = true;
			this.roomprice = 9000;
		}
		
		else if (roomtype.equalsIgnoreCase("Queen")) {
			this.roomtype = roomtype;
			this.roomavail = true;
			this.roomprice = 11000; }
		
		else if (roomtype.equalsIgnoreCase("King")) {
			this.roomtype = roomtype;
			this.roomavail = true;
			this.roomprice = 15000; }
		
		else {
		throw new IllegalArgumentException("No Room of such type can be created.");
		}
	}
	
	public Room(Room myroom) {
		this.roomtype = myroom.roomtype;
		this.roomprice = myroom.roomprice;
		this.roomavail = myroom.roomavail;		// CHECK WITH TA
	}
	
	public String getType() {
		return this.roomtype;
	}
	
	public int getPrice() {
		return this.roomprice;
	}

	
	public void changeAvailability() {
		roomavail = !roomavail; 
	}
	
	public static Room findAvailableRoom(Room[] roomarray, String typeofroom ) {
		for(int i=0; i<roomarray.length; i++) {
			if ((roomarray[i].getType().equalsIgnoreCase(typeofroom)) && (roomarray[i].roomavail == true)) {
					return roomarray[i];
					
	
			}
		}
	 return null;
	}
	
	public static boolean makeRoomAvailable(  Room[] roomarray,String typeofroom) {
		for (int j=0; j<roomarray.length; j++) {
			if ((roomarray[j].getType().equalsIgnoreCase(typeofroom)) && (roomarray[j].roomavail == false)) {
				 roomarray[j].roomavail = true;
					return true;
				}
			}
		return false;
	}
}
	
	/*public static void main (String[] args) {
		Room a = new Room();
		
	}
	}*/
		

	
		
		
	
