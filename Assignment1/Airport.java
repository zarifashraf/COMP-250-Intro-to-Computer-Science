package thepackage;


public class Airport {
	private int xcoord;
	private int ycoord;
	private int airfees;

	public Airport(int xcoord, int ycoord, int airfees) {
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		this.airfees = airfees;
	}
	
	public int getFees() {
		return this.airfees;
	}

	public static int getDistance(Airport A1, Airport A2) 
	{
		int x1 = A1.xcoord;
		int x2 = A2.xcoord;
		int y1 = A1.ycoord;
		int y2 = A2.ycoord;
		int Distance;
		
		Distance = (int) Math.ceil(Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2, 2)));
		return Distance;
		}
	
		
}



