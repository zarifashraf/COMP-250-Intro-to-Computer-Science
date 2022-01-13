package mypackage;

public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
    	this.networkLines = new TrainLine[nLines];
    }
    
    public void addLines(TrainLine[] lines) {
    	this.networkLines = lines;
    }
    
    public TrainLine[] getLines() {
    	return this.networkLines;
    }
    
    public void dance() {
    	System.out.println("The tracks are moving!");
    	for (int i = 0; i < networkLines.length; i++) {
			networkLines[i].shuffleLine();
    }
    }
    
    public void undance() {
    	for (int j = 0; j < networkLines.length; j++) {
			networkLines[j].sortLine();
    }
    }
    
    
    public int travel(String startStation, String startLine, String endStation, String endLine) {

		TrainLine curLine = getLineByName(startLine);
		TrainStation curStation = curLine.findStation(startStation);
		
		try {
			curStation = curLine.findStation(startStation); 
			System.out.println("Departure station: " + startStation + " found on line " + startLine);
		}
    	catch(StationNotFoundException e) {
			System.out.println("Departure Station: " + startStation + " cannot be found");
			return 168;
		}
    	
		try {
			this.getLineByName(endLine).findStation(endStation); 
			System.out.println("Arrival station: " + endStation + " found on line " + endLine);
		}
		
    	catch(StationNotFoundException e) {
			System.out.println("Arrival Station: " + endStation + " cannot be found");
			return 168;
		}
    	
    	
		int hoursCount = 0;
		System.out.println("Departing from " + startStation);
		TrainStation tempStation = curStation;
		
		while (true) {
			
			
			if (curStation.getName().equals(endStation)) {
				break;
			}
			if (hoursCount == 168) {
				System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
				return hoursCount;
			}
		
			
			TrainStation nextStation = curLine.travelOneStation(curStation, tempStation);
			tempStation = curStation;
			curStation = nextStation;
			curLine = getLineByName(curStation.getLine().getName());
			
			hoursCount = hoursCount + 1;
			if (hoursCount % 2 == 0) {
				dance();
			
			}
			
	    	System.out.println("Traveling on line "+curLine.getName()+":"+curLine.toString());
	    	System.out.println("Hour "+hoursCount+". Current station: "+curStation.getName()+" on line "+curLine.getName());
	    	System.out.println("=============================================");
	    	
    		}
	    	
	    	System.out.println("Arrived at destination after "+hoursCount+" hours!");
	    	return hoursCount;
    }
    

		public TrainLine getLineByName(String linecalled){
    	for (int k = 0; k < networkLines.length; k++) {
			if (networkLines[k].getName().equals(linecalled)) {
				return networkLines[k];
			}
		}

		throw new LineNotFoundException("The line: " + linecalled +  "does not exist. Please re-enter a valid name.");
	}
    
    
  //prints a plan of the network for you.
    public void printPlan() {
    	System.out.println("CURRENT TRAIN NETWORK PLAN");
    	System.out.println("----------------------------");
    	for(int i=0;i<this.networkLines.length;i++) {
    		System.out.println(this.networkLines[i].getName()+":"+this.networkLines[i].toString());
    		}
    	System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	   String name;

	   public LineNotFoundException(String n) {
	      name = n;
	   }

	   public String toString() {
	      return "LineNotFoundException[" + name + "]";
	   }
	}