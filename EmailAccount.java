import java.util.ArrayList;

/*Class models an EmailAccount.This account has an address, a password, and a list of email boxes.
 *  By default, it has an Inbox, Sent box and a Trash box. It has a constructor setter for the password,
 *  method to log in and out, getters for the address, password, log status, email folders, methods to 
 *  send an email, receive email, delete email, move mail between boxes, add custom boxes and delete email boxes.*/

public class EmailAccount 
{
	private final EmailAddress accountAddress;
	private String password;
	private boolean loggedIn; //tracks log in status
	private ArrayList<EmailBox> emailFolders; //arrayList of email boxes

	//constructor builds the default email boxes automatically
	public EmailAccount(EmailAddress emailAddress, String password)
	{
		accountAddress=emailAddress;
		this.password=password;
		//if you make an account, you log in automatically
		loggedIn=false;
		
		//create the list of folders and add the default ones
		emailFolders=new ArrayList<>();
		emailFolders.add(new EmailBox("Inbox"));
		emailFolders.add(new EmailBox("Sent Mail"));
		emailFolders.add(new EmailBox("Trash"));
	}
	
	//getters for all the variables
	public EmailAddress getAddress()
	{
		return accountAddress;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean getLogStatus()
	{
		return loggedIn;
	}
	
	public ArrayList<EmailBox> getFolders()
	{
		return emailFolders;
	}
	
	// method to change the password
	public boolean setPassword(String password)
	{
		// we can only do that if you are logged in to your account
		if (loggedIn)
		{
			this.password = password;
			return true;
		} 
		else
		{
			return false;
		}
	}

	// method to toggle the log in status
	public void logInOut()
	{
		loggedIn=!loggedIn;
		String inOut;
		if (loggedIn)
		{
			inOut="in";
		} 
		else
		{
			inOut="out";
		}
		
		System.out.println("You logged "+inOut+" !");
	}

	// method to add a user created email box
	public void addEmailBox(String name)
	{
		emailFolders.add(new EmailBox(name));

		// user created email boxes are ordered alphabetically by their name
		insertionSort(emailFolders);
	}

	// method to delete a user created email box
	public boolean deleteEmailBox(EmailBox box)
	{
		// default boxes can not be deleted, and they occupy the first three positions
		// in the arrayList
		if (emailFolders.indexOf(box) <= 2)
		{
			System.out.println("You cant delete Inbox, Trash or Sent Mail.");
			return false;
		}
		// cannot delete a box that does not exist
		else if (emailFolders.indexOf(box) == -1)
		{
			System.out.println("Index out of bounds.");
			return false;
		} 
		else
		{
			emailFolders.remove(box);
			return true;
		}

	}
	
	//method to send an email
	public void sendEmail(Email someEmail)
	{
		//.get(1) returns the SentEmail box, we add the email there
		emailFolders.get(1).addEmail(someEmail);
	}
	
	//method to receive an email
	public void receiveEmail(Email someMail)
	{
		//we make a copy of the received email and put it in the inbox
		//otherwise when we delete the BCC list, it would also delete it for the email in the sender's account
		Email receivedEmail=new Email(someMail.getSender(),someMail.getRecipients(),
				someMail.getCC(),someMail.getBCC(),someMail.getReplyTo(),someMail.getSubject(),
				someMail.getBody());
		emailFolders.get(0).addEmail(receivedEmail);
		//BCC list should not be seen by recipients, so we delete it
		receivedEmail.setBCC(null);
	}
	
	//method to delete an email
	public boolean deleteEmail(EmailBox box, Email someMail)
	{
		//can only do it if the specified email and box exist 
		if(emailFolders.contains(box))
		{
			if(box.getEmails().remove(someMail))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	//method to move an email between folders
	public void moveEmail(EmailBox finalBox, EmailBox initialBox, Email someEmail)
	{
		finalBox.addEmail(someEmail); // add it to the new box
		deleteEmail(initialBox, someEmail); //delete from the first box
	}
	
	@Override
	public String toString()
	{
		String account="Address: "+accountAddress.toString();
		account+="Logged In: ";
		
		if(loggedIn)
		{
			account+="Yes.\n";
		}
		else
		{
			account+="No.\n";
		}
		
		for(EmailBox elem: emailFolders)
		{
			account+=elem.toString()+"\n";
		}
		
		return account;
	}
	
	// sorts the user created email boxes in alphabetical order of the local part of the address
	private void insertionSort(ArrayList<EmailBox> list) 
	{
		/*this method is called each time an email box is added, so the list will be
		 * sorted except for the last element, so this is where we start. We do not need
		 * a for loop for firstUnsorted, as it would only loop once.*/
		int firstUnsorted = list.size() - 1;

		boolean placeFound = false;
		int index = firstUnsorted;
		//index >3 because the first 3 email boxes are by default and are always shown first,
		//so we do not sort them
		while (index > 3 && !placeFound) {
			// compareTo method returns a positive integer if the first string
			// is alphabetically after the second one, so we swap them
			if (list.get(index).getName().compareTo(list.get(index-1).getName())<0) 
			{
				EmailBox temp = list.get(index);
				list.set(index, list.get(index - 1));
				list.set(index - 1, temp);
			} 
			else 
			{
				placeFound = true;
			}

			index--;
		}
	}
}
