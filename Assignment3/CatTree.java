package package3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
      
    public Iterator<CatInfo> iterator() {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {
        		
        	try {
        		
        		if (this.data == null) {
            		this.data = c.data;
            		}
        			
        		else if (c.data.monthHired < this.data.monthHired) {
				
        			if (this.senior == null) {
        				this.senior = c;
        			}
				
        			else {
        				this.senior.addCat(c);
					}
        		}
        	
        		else if (c.data.monthHired > this.data.monthHired) {
				
        			if (this.junior == null) {
        				this.junior = c;
        			}
				
        			else {
        				this.junior.addCat(c);
        			}
        		}
				
        		else if (c.data.monthHired == this.data.monthHired && c.data.furThickness < this.data.furThickness) {		
					
        			if (this.same == null) {
						this.same = c;
					}
				
					else {
						this.same.addCat(c);
					}
        		}
				
			
        		else if (c.data.monthHired == this.data.monthHired && c.data.furThickness > this.data.furThickness) {

					CatInfo tmp = this.data;
					this.data = c.data;
					c.data = tmp;
					
					
					if (this.same == null) {
						this.same = c;
					}
					
					else {
						this.same.addCat(c);
						}
        		}
				
        		else {
					if (this.same == null) {
						this.same = c;
						}
					else {
						this.same.addCat(c);
						}
        		}
        		return this;
        	}
		 catch (Exception e) {
			 System.out.println("The method addCat encountered an exception.");
			 return this;
			}
        }
        
        
        
        public CatNode removeCat(CatInfo c) {
        	try {
				
        		if (this.data == null) {
					System.out.println("Catnode on which removeCat was called has no data.");
					return null;
				}
        		
				else if (c.monthHired < this.data.monthHired) {
					
					if (this.senior == null) {
						System.out.println("Looing for senior cats - there are no senior cats with that data to remove.");
						return this;
					}
					else {
						this.senior = this.senior.removeCat(c);
					}
				}
        		
        		else if (c.monthHired > this.data.monthHired) {
					
					if (this.junior == null) {
						System.out.println("Looking for junior cats - there are no junior cats with that data to remove.");
						return this; 
					}
					else  {
						this.junior = this.junior.removeCat(c);
					}
				}
				
				 
				else if (c.monthHired == this.data.monthHired && c.furThickness < this.data.furThickness) {
					
						if (this.same == null) {
							System.out.println("Looking for same cats - there are no less furrier cats with that data to remove.");
							return this;
						}
						else if (this.same != null) {
							this.same = this.same.removeCat(c);
						}
					}
					
					
			else if (c.monthHired == this.data.monthHired && c.furThickness == this.data.furThickness) {
						
				if (c.equals(this.data)) {
							
							if (this.same != null) {
								
								CatInfo temp = this.data;
								this.data = this.same.data;
								temp = null;
								
								this.same.addCat(this.junior);
								this.same.addCat(this.senior);
								
								this.junior = this.same.junior;
								this.senior = this.same.senior;
								
								this.same = this.same.same;
								
								return this;
							}
							
					else if (this.same == null && this.senior != null) {
									
								if (this.junior != null) {
								
									this.senior.addCat(this.junior);
								}
								
								CatInfo temp2 = this.data;
								this.data = this.senior.data;
								temp2 = null;
								
								this.same = this.senior.same;
								this.junior = this.senior.junior;
								
								this.senior = this.senior.senior;
								
								return this;
						}
							
			else if (this.same == null && this.junior != null) {
								
					if (this.senior != null) {
									
							this.junior.addCat(this.senior);
							}
						
							CatInfo temp3 = this.data;
							this.data = this.junior.data;
							temp3= null;
							
							this.same = this.junior.same;
							this.senior = this.junior.senior;
							
							this.junior = this.junior.junior;
							
							return this;
					}
			
					else {
						this.data = null;	
						return null;
						}
					}
				
				else if (!c.equals(this.data)) {
					if (this.same != null) {
						this.same= this.same.removeCat(c);
						}
					
					else { 
						System.out.println("No data match found while trying to remove a cat.");
						return this;
						}
					} 
				}
        		return this;
        	
        	} 
        	catch (Exception e) {
				System.out.println("The method removeCat encountered an exception.");
        		return this;
			}
        	
        }
        
        public int mostSenior() {
        	
        	int oldest; 
        	
        	if (this.data == null) {
    			System.out.println("CatNode on which mostSenior was called on has no data.");
    			return 0;
    		}
        	
        	else {
        		oldest = this.data.monthHired;	
        	}
			
        	try {
        		
        		if (this.senior != null) {
					oldest = this.senior.mostSenior();
				}
				
				return oldest;
			} 
        	
        	catch (Exception e) {
				System.out.println("The method mostSenior encountered an exception.");
        		return oldest;
			}
        }
		
        
        
        public int fluffiest() {
			int thefluff,smallfluff, samefluff, bigfluff;
			
			if (this.data == null) {
				System.out.println("CatNode on which fluffiest was called has no data.");
				return 0;
			}
			
			else {
				thefluff = this.data.furThickness;
			}
			
        	try {
			
				if (this.junior != null) {
					smallfluff = this.junior.fluffiest();
						
						if (smallfluff > thefluff) {
							thefluff = smallfluff;
					}
				}
				
				if (this.same != null) {
					samefluff = this.same.fluffiest();
					if (samefluff > thefluff) {
						thefluff = samefluff;
					}
				}
				
				if (this.senior != null) {
					bigfluff = this.senior.fluffiest();
					if (bigfluff > thefluff) {
						thefluff = bigfluff;
					}
				}
				
				return thefluff;
				
			} 
        	
        	catch (Exception e) {
				System.out.println("The method fluffiest encountered an exception.");
        		return thefluff;
			}
		}
        
        
        public int hiredFromMonths(int monthMin, int monthMax) {
        	int catcounter;
        	
        	if (this.data == null) {
        		System.out.println("Catnode on which hiredFromMonths was called has no data.");
        		return 0;
        	}
        	
        	else {
        		catcounter = 0;
        	}
			try {
				
				if (monthMin > monthMax) {
					return 0;
				}
				
				if (this.data.monthHired <= monthMax && this.data.monthHired >= monthMin) {
					catcounter = catcounter + 1;
				
				}
				
				if (this.same != null) {
					catcounter = catcounter + this.same.hiredFromMonths(monthMin, monthMax);
				}
				
				if (this.junior != null) {
					catcounter = catcounter + this.junior.hiredFromMonths(monthMin, monthMax);
				}
				
				if (this.senior != null) {
					catcounter = catcounter + this.senior.hiredFromMonths(monthMin, monthMax);
				}
				
				return catcounter;
			} 
			
			catch (Exception e) {
				System.out.println("The method hiredFromMonths encountered an exception.");
				return catcounter;
			}

		}
        
        public CatInfo fluffiestFromMonth(int month) {
        	CatInfo needsatrim = null;
			
        	try {
				if (this.data != null) {
					
					if (month < this.data.monthHired && this.senior != null) {
					needsatrim = this.senior.fluffiestFromMonth(month);
				}
				
					else if (month > this.data.monthHired && this.junior != null) {
					needsatrim = this.junior.fluffiestFromMonth(month);
				}
				
					else if (month == this.data.monthHired) {
					needsatrim = this.data;
				}
				
					else {
					return null;
				}
				
				
			} 
				return needsatrim;
        }
			catch (Exception e) {
				System.out.println("The method fluffiestFromMonth encountered an exception.");
				return needsatrim;
			}
		}
        
       
        public int[] costPlanning(int nbMonths) {
        	
        	int Marchtwentytwenty = 243;
        	
        	
        		if (nbMonths < 0) {
        	
        		System.out.println("Invalid month");
        		return null;
        		}
        		
        		int[] costarray = new int[nbMonths];
        		
        		if (nbMonths >= 0) {
        			
        			try {
				
        			CatTree trimtree;
				
				if (this != root) {
					trimtree = new CatTree(this);
				}
				else  
				{
					trimtree = CatTree.this;
				}
				
	for (CatInfo trimdata : trimtree) {
				
	if (trimdata.nextGroomingAppointment >= Marchtwentytwenty && trimdata.nextGroomingAppointment < (Marchtwentytwenty + nbMonths)) {
					costarray[trimdata.nextGroomingAppointment - Marchtwentytwenty] += trimdata.expectedGroomingCost;
					}
				} 
        	
        		
        	}
        		catch (Exception e) {
        			System.out.println("The method costPlanning encountered an exception.");
        		}
        	
			}
		return costarray;
        }
    }
        
    
    
    private class CatTreeIterator implements Iterator<CatInfo> {
  
    		ArrayList<CatInfo> listofcats = new ArrayList<CatInfo>();
    		int Index;
    		
    		public CatTreeIterator() {
    			Index = 0;
    			inOrderTraversal(root);
    		}
    		
    		private void inOrderTraversal(CatNode root) {
    			try {
    			
    				if (root.senior != null) {
    					inOrderTraversal(root.senior);
    				}
    				
    				
    				if (root.same != null) {
    					inOrderTraversal(root.same);
    				}
    				
    				
    				listofcats.add(root.data);
    				
    				if (root.junior != null) {
    					inOrderTraversal(root.junior);
    				}
    			} 
    			
    			catch (Exception e) {
    				return;
    			}
    		}
    		
    		public CatInfo next(){
    			Index = Index + 1;
    		
    			if (Index - 1 >= listofcats.size()) {
    				throw new NoSuchElementException("Next cat is not available.");
    			}
    			return listofcats.get(Index - 1);
    		}
    		
    		public boolean hasNext() {
    			try {
   
    				if (Index + 1 > listofcats.size()) {
    					return false;
    				}    				
    				else {
    					return true;
    				}
    			} 
    			catch (Exception e) {
    				return false;
    			}
    		}
    	}
    
    }
  



