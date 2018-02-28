import java.util.ArrayList;
/*Class models a list of email addresses. Has few methods because most things
 *  we need come by default with an ArrayList*/
public class EmailAddressList 
{
	//arrayList of email addresses
	private ArrayList<EmailAddress> addressList;
		
	public EmailAddressList()
	{
		addressList=new ArrayList<>();
	}
	//add method that is more explicit and sorts the list each time
	public void addRecipient(EmailAddress address)
	{
		addressList.add(address);
		
		//sort the list alphabetically
		insertionSort(addressList);
	}
	
	//get method returns the whole list;
	public ArrayList<EmailAddress> getAddressList()
	{
		return addressList;
	}
	
	//arrayList is private so we need a getter for individual itmes
	public EmailAddress getRecipient(int index)
	{
		return addressList.get(index);
	}
	
	//method for removing a recipient from the list
	public void removeRecipient(int index)
	{
		addressList.remove(index);
	}
	
	//toString displays the list nicely
	@Override
	public String toString()
	{
		String displayedList="";
		int count=1;
		
		for(EmailAddress elem: addressList)
		{
			//if its the last element, we want a dot after it
			if(count==addressList.size())
			{
				displayedList+=elem+".";
			}
			//only displays 5 addresess per row, so we put in a newline characer
			else if(count%5==0 && count!=0)
			{
				displayedList+=elem+",\n";
			}
			//adds just a comma if its not at the end of a row
			else
			{
				displayedList+=elem+", ";
			}
			
			count++;
		}
		
		return displayedList;
	}
	
	
	
	//sorts the address list in alphabetical order of the local part of the address
	private void insertionSort(ArrayList<EmailAddress> list)
	{
		/*this method is called each time an address is added, so the list will 
		 *be sorted except for the last element, so this is where we start. 
		 *We do not need a for loop for firstUnsorted, as it would only loop once.*/
		int firstUnsorted=list.size()-1;
		
		boolean placeFound=false;
		int index=firstUnsorted;
		while(index>0 && !placeFound)
		{
			//compareTo method returns a positive integer if the first string
			//is alphabetically after the second one
			if(list.get(index).getLocalPart().compareTo(list.get(index-1).getLocalPart())<0)
			{
				EmailAddress temp=list.get(index);
				list.set(index,list.get(index-1));
				list.set(index-1, temp);
			}
			else
			{
				placeFound=true;
			}
				
			index--;
		}
	}
}
