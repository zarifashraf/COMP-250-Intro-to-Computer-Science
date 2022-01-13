package FinalProject_Template;

import java.util.ArrayList;

public class Twitter {
	
	private ArrayList<Tweet> tweets;
	private ArrayList<String> stopWords;
	
	private MyHashTable<String, ArrayList<Tweet>> authorhashtable;
	private MyHashTable<String, ArrayList<Tweet>> datehashtable;
	private MyHashTable<String, String> stopwordhashtable;
	private MyHashTable<String, Integer> wordshashtable;
	
	
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
	
		this.authorhashtable = new MyHashTable<String, ArrayList<Tweet>>(30); 
		this.datehashtable = new MyHashTable<String, ArrayList<Tweet>>(30); 
		this.stopwordhashtable = new MyHashTable<String, String>(stopWords.size()); 
		this.wordshashtable = new MyHashTable<String, Integer>(30);
		
	
		if (stopWords.size() == 0) {
			this.stopwordhashtable.put("", "");
		}
		else {
			for (String presentword: stopWords) { 
				this.stopwordhashtable.put(presentword.toLowerCase(), "");
			}
		}

		for (Tweet presenttweet: tweets) { 
			addTweet(presenttweet);
		}
	}
	
	
	public void addTweet(Tweet t) {
		
		if (this.authorhashtable.get(t.getAuthor()) == null) {
			
			ArrayList<Tweet> tweetit = new ArrayList<Tweet>();
			tweetit.add(t);
			this.authorhashtable.put(t.getAuthor(), tweetit);
		}
		
		else {
			this.authorhashtable.get(t.getAuthor()).add(t);
		}
		
		
		String date;
		date = t.getDateAndTime().split(" ")[0];
		
	
		if (this.datehashtable.get(date) == null) {
			
			ArrayList<Tweet> tweetit = new ArrayList<Tweet>();
			tweetit.add(t);
			this.datehashtable.put(date, tweetit);
		}
		else {
			this.datehashtable.get(date).add(t);
		}
		
		
		ArrayList<String> twordslist = Twitter.getWords(t.getMessage());

		for (int z = 0; z < twordslist.size(); z++) {
			
			twordslist.set(z, twordslist.get(z).toLowerCase());
			String presenttword;
			presenttword = twordslist.get(z);
			
			if (this.stopwordhashtable.get(presenttword) != null) { 
				twordslist.remove(z);
				z = z - 1;
			}
		}
		

		for (int z = 0; z < twordslist.size(); z++) {
			String presenttword;
			presenttword = twordslist.get(z);
			
	
			for (int x = z + 1; x < twordslist.size(); x++) {
				if (twordslist.get(x).equalsIgnoreCase(presenttword)) {
					twordslist.remove(x);
					x = x -1;
				}
			}
		}
		
	
		for (String presenttword : twordslist) { 
			if (this.wordshashtable.get(presenttword) == null) {
				this.wordshashtable.put(presenttword, 1);
			}
			
			else {
				this.wordshashtable.put(presenttword, this.wordshashtable.get(presenttword) + 1);
			}
		}
		
	}
	
	public Tweet latestTweetByAuthor(String author) {
		
		ArrayList<Tweet> twitterrepublic = this.authorhashtable.get(author);
		
		if (twitterrepublic == null) {
			return null;
		}
		
		Tweet thelatestupdate = twitterrepublic.get(0); 
		
		for (int z = 1; z < twitterrepublic.size(); z++) { 
			if (thelatestupdate.compareTo(twitterrepublic.get(z)) < 0) {
				thelatestupdate = twitterrepublic.get(z);
			}
		}
		return thelatestupdate;
	}



	public ArrayList<Tweet> tweetsByDate(String date) {
	
		ArrayList<Tweet> twitterfeed = this.datehashtable.get(date);
		
		if (twitterfeed == null) {
			return null;
		}
		
		for (int z = 0; z < twitterfeed.size(); z++) { 
			
			if (!twitterfeed.get(z).getDateAndTime().split(" ")[0].equals(date)) {
				twitterfeed.remove(z);
				z = z - 1;
			}
		}
		return twitterfeed;
	}
	
	
	public ArrayList<String> trendingTopics() {
		
		for (Tweet t: tweets) {
    		
    		ArrayList<String> tweetedwords = getWords(t.getMessage());
    		tweetedwords.removeAll(stopWords);
    		
    		for (String s: tweetedwords) {
    			
    			if (wordshashtable.get(s).equals(null))
    				wordshashtable.put(s, 1);
    			else
    				wordshashtable.put(s, wordshashtable.get(s)+1);	
    		}
    	}
    	
    	ArrayList<String> tweetedwords = MyHashTable.fastSort(this.wordshashtable);
        
    	return tweetedwords;
    }
	
	
	private static ArrayList<String> getWords(String msg) {
		msg = msg.replace('\'', ' ');
		String[] words = msg.split(" ");
		ArrayList<String> wordsList = new ArrayList<String>(words.length);
		for (int i=0; i<words.length; i++) {
			String w = "";
			for (int j=0; j< words[i].length(); j++) {
				char c = words[i].charAt(j);
				if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
					w += c;
				
			}
			wordsList.add(w);
		}
		return wordsList;
	}

}
