package thepackage;


public class Hotel  {
	private String hotelname;
	private Room[] hotelrooms;

public Hotel(String hotelname,Room[] hotelrooms) {
	this.hotelname = hotelname;
	
	Room[] roomsofthehotel = new Room[hotelrooms.length];
	for (int k = 0; k<hotelrooms.length; k++) {
		roomsofthehotel[k]=new Room( hotelrooms[k]);
		}
	this.hotelrooms = roomsofthehotel;
	}

public int reserveRoom(String typeofroom) {
		Room empty = new Room(typeofroom);
		empty = Room.findAvailableRoom(hotelrooms, typeofroom);
		if (empty == null) {
			throw new IllegalArgumentException("None of the rooms are available");
		}
		else {
			empty.changeAvailability();
			return empty.getPrice();
		}
}	
			
		public boolean cancelRoom(String typeofroom) {
			boolean success = Room.makeRoomAvailable(hotelrooms, typeofroom);
			return success;
			
		}
	}
