package mypackage;

import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		int numberofstations = 1;
		TrainStation counter = this.leftTerminus;
		while (!counter.isRightTerminal()) {
			counter = counter.getRight();
			numberofstations = numberofstations + 1;
		}
	return numberofstations;
	}
	

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		
			if (current.hasConnection && !previous.equals(current.getTransferStation())) {
			return current.getTransferStation();
		}
		else {
			return getNext(current); 
		}
	}

	
	public TrainStation getNext(TrainStation station) {
		
		try { 
			station = findStation(station.getName());
		}
		catch (StationNotFoundException error) {
			throw error;
		}
		
		if (station.isRightTerminal() && goingRight) {
			reverseDirection();
			return station.getLeft();
			}
		else if (station.isLeftTerminal() && !goingRight) {
			reverseDirection();
			return station.getRight();
		}
		else if (goingRight) {
		return station.getRight();
	} 
		else {
		return station.getLeft(); 
	}
	}
	
	public TrainStation findStation(String name) throws StationNotFoundException {
		TrainStation comparator = this.leftTerminus;
		TrainStation match = null;

		while (!comparator.isRightTerminal()) {
			if (comparator.getName().equals(name)) {
				match = comparator;
			}
			comparator = comparator.getRight();
		}
		
		if (comparator.getName().equals(name)) {
			match = comparator;
		}

		if (match == null) {
			throw new StationNotFoundException("Station of the name: " + name + "has not been found.");
		} else {
			return match;
		}
	}

	public void sortLine() {
	boolean indicator;
	TrainStation mystation;

	do {
		indicator = false;
		mystation = this.leftTerminus;

		while (!mystation.getRight().isRightTerminal()) {
			
			if (mystation.getName().compareTo(mystation.getRight().getName()) > 0) {
				if (mystation.isLeftTerminal()) {
					TrainStation next = mystation.getRight();
					mystation.setRight(mystation.getRight().getRight());
					mystation.setLeft(next);
					mystation.setNonTerminal();
					next.setLeft(null);
					next.setLeftTerminal();
					next.setRight(mystation);
					mystation.getRight().setLeft(mystation);
					this.leftTerminus = next;
				} else {
					TrainStation next = mystation.getRight();
					TrainStation nextNext = mystation.getRight().getRight();
					TrainStation previous = mystation.getLeft();
					previous.setRight(next);
					mystation.setLeft(next);
					mystation.setRight(nextNext);
					next.setLeft(previous);
					next.setRight(mystation);
					nextNext.setLeft(mystation);
				}
				indicator = true;
			} else {
				mystation = mystation.getRight();
			}
		}

		
		if (mystation.getName().compareTo(mystation.getRight().getName()) > 0) {
			
			mystation.getRight().setNonTerminal();
			mystation.getRight().setLeft(mystation.getLeft());
			mystation.getRight().setRight(mystation);
			mystation.getLeft().setRight(mystation.getRight());
			mystation.setLeft(mystation.getRight());
			mystation.setRight(null);
			mystation.setRightTerminal();
			this.rightTerminus = mystation;
			indicator = true;
		}

	} while (indicator != false);

	this.lineMap = this.getLineArray();
}

	

	public TrainStation[] getLineArray() {
		int arraysize = this.getSize();
		
		TrainStation currentStation = this.getLeftTerminus();
		TrainStation[] stationArray = new TrainStation[arraysize];
		
		for (int i = 0; i < arraysize; i++) {
			stationArray[i] = currentStation;
			currentStation = currentStation.getRight();
		}
		return stationArray;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);

		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(this.getLineArray());

		
		for (int i = 0; i < shuffledArray.length; i++) {
			shuffledArray[i].setNonTerminal();
			shuffledArray[i].setLeft(null);
			shuffledArray[i].setRight(null);
		}
		
		for (int i = 0; i < shuffledArray.length; i++) {
			if (i == 0) {
				shuffledArray[i].setLeft(null);
				shuffledArray[i].setRight(shuffledArray[i+1]);
				shuffledArray[i].setLeftTerminal();
			}
			else if (i == (shuffledArray.length - 1)) {
				shuffledArray[i].setLeft(shuffledArray[i-1]);
				shuffledArray[i].setRight(null);
				shuffledArray[i].setRightTerminal();
			}
			else {
				shuffledArray[i].setLeft(shuffledArray[i-1]);
				shuffledArray[i].setRight(shuffledArray[i+1]);
			}
		}
		
		this.leftTerminus = shuffledArray[0];
		this.rightTerminus = shuffledArray[shuffledArray.length-1];	
	}



	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}

