/*Class models an email box. It can hold multiple emails, and you can add and remove
 * email from it. Besides, it has a construcotr and getters for the emails and 
 * the name of the box.*/

import java.util.ArrayList;

public class EmailBox 
{
	//has an ArrayList of Email objects
	private ArrayList<Email> emails;
	private String name;
	
	//when you make a new box, it will have no emails in it
	public EmailBox(String name)
	{
		this.name=name;
		
		emails=new ArrayList<>();
	}
	
	//adds email at the beginning of the list
	public void addEmail(Email someEmail)
	{
		emails.add(0,someEmail);
	}
	
	//removes email based on an index
	public void removeEmail(int index)
	{
		emails.remove(index);
	}
	
	//getter for the list of emails
	public ArrayList<Email> getEmails()
	{
		return emails;
	}
	
	//getter for the name
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		String box=name+":\n";
		
		for(Email elem: emails)
		{
			box+=elem.toString()+"\n\n";
		}
		return box;
	}
	
	public String shortString()
	{
		String box=name+":\n";
		int i=1;
		
		for(Email elem: emails)
		{
			box+=i+" - "+elem.shortString()+"\n\n";
			i++;
		}
		return box;
	}
}
