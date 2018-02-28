import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;


public class EmailApplication 
{
	public static Scanner kb;
	public static void main(String[] args) throws FileNotFoundException
	{	
		kb=new Scanner(System.in);
		ArrayList<EmailAccount> accounts=new ArrayList<>();
		
		boolean displayMenu=true;
		
		while(displayMenu)
		{
			System.out.println("Type in a number for an option from below:");
			System.out.println("1 - New Account.");
			System.out.println("2 - Log In.");
			System.out.println("3 - Quit.");
			
			int option=readInt(3);
			
			if(option==1)
			{
				String addressName=getAddress();
				EmailAddress address=new EmailAddress(splitAddress(addressName,true), splitAddress(addressName,false));
				
				System.out.println("Type in a password:");
				String password=kb.nextLine();
				
				accounts.add(new EmailAccount(address, password));
				System.out.println("Account created successfully!");
			}
			else if(option==2)
			{
				EmailAccount selectedAccount=null;
				String answer;
				do
				{
					System.out.println("Type in an email address that exists or press enter to go back.");
					do
					{
						System.out.println("Type in an email address:");
						answer=kb.nextLine();
					}
					while(answer.indexOf('@')==-1 && !answer.equals(""));
					
					if(!answer.equals(""))
					{
						for(EmailAccount elem: accounts)
						{
							if(elem.getAddress().toString().equals(answer))
							{
								selectedAccount=elem;
								break;
							}
						}
					}
				}
				while(selectedAccount==null && !answer.equals(""));
				
				if(!answer.equals(""))
				{
					String password;
					do
					{
						System.out.println("Type in the correct password or press enter to go back.");
						password=kb.nextLine();
					}
					while(!password.equals(selectedAccount.getPassword()) && !password.equals(""));
					
					if(!password.equals(""))
					{
						selectedAccount.logInOut();
						
						boolean displayAccountMenu=true;
						while(displayAccountMenu)
						{
							System.out.println("Choose an option from below:");
							System.out.println("1 - New Email.");
							System.out.println("2 - Move email.");
							System.out.println("3 - Delete email.");
							System.out.println("4 - Display Email box");
							System.out.println("5 - Display all email boxes.");
							System.out.println("6 - New email box.");
							System.out.println("7 - Delete email box.");
							System.out.println("8 - Change password.");
							System.out.println("9 - Log out.");
							
							int accountOption=readInt(9);
							
							switch(accountOption)
							{
								case 1:
									newEmail(selectedAccount, accounts);
									break;
								case 2:
									moveEmail(selectedAccount);
									break;
								case 3:
									deleteEmail(selectedAccount);
									break;
								case 4:
									EmailBox someBox=getEmailBox(selectedAccount,"Type in the name of the box you want to see, or enter to cancel.");
									
									if(someBox!=null)
									{
										System.out.println(someBox.toString());
									}
									break;
								case 5:
									
									for(EmailBox elem: selectedAccount.getFolders())
									{
										System.out.println(elem.shortString());
									}
									break;
								case 6:
									newEmailBox(selectedAccount);
									break;
								case 7:
									deleteEmailBox(selectedAccount);
									break;
								case 8:
									System.out.println("Type in your new password, or press enter to cancel.");
									String newPassword=kb.nextLine();
									
									if(selectedAccount.setPassword(newPassword))
									{
										System.out.println("Password changed!");
									}
									else
									{
										System.out.println("Cannot change the password.");
									}
									break;
								case 9:
									selectedAccount.logInOut();
									displayAccountMenu=false;
									System.out.println("Logged out successfully!");
									break;
							}
						}
					}
				
				}
			}
			else
			{
				String yesOrNo;
				
				do 
				{
					System.out.println("Are you sure? Yes or no.");
					yesOrNo=kb.nextLine();
				}
				while(!yesOrNo.equalsIgnoreCase("yes") && !yesOrNo.equalsIgnoreCase("no"));
				
				if(yesOrNo.equalsIgnoreCase("yes"))
				{
					displayMenu=false;
				}
			}
		}
		kb.close();
	}
	
	public static void newEmail(EmailAccount account, ArrayList<EmailAccount> accountList)
	{
		kb=new Scanner(System.in);
		boolean editEmail=true;
		Email newEmail=new Email (account.getAddress(), new EmailAddressList(),"","");
		
		while(editEmail)
		{
			System.out.println("Choose an option from below:");
			System.out.println("1 - Add recipient.");
			System.out.println("2 - Set email body.");
			System.out.println("3 - Set subject.");
			System.out.println("4 - Add to CC list.");
			System.out.println("5 - Add to BCC list");
			System.out.println("6 - Set ReplyToAddress");
			System.out.println("7 - Send Email.");
			
			int option=readInt(7);
			
			switch(option)
			{
				case 1:
					System.out.println("Type in an address to add to the recipients list.");
					String address=getAddress();
					newEmail.getRecipients().addRecipient(new EmailAddress(splitAddress(address,true),
							splitAddress(address,false)));
					
					System.out.println(address+" added to recipients list!");
					break;
				case 2:
					System.out.println("Type in a new body for your email.");
					String body=kb.nextLine();
					newEmail.setBody(body);
					break;
				case 3:
					System.out.println("Type in a new subject for your email.");
					String subject=kb.nextLine();
					newEmail.setSubject(subject);
					break;
				case 4:
					if(newEmail.getCC()==null)
					{
						newEmail.setCC(new EmailAddressList());
					}
					System.out.println("Type in an address to add to the CC list.");
					String ccAddress=getAddress();
					
					newEmail.getCC().addRecipient(new EmailAddress(splitAddress(ccAddress,true),
							splitAddress(ccAddress,false)));
					System.out.println(ccAddress+" added to CC list!");
					break;
				case 5:
					if(newEmail.getBCC()==null)
					{
						newEmail.setBCC(new EmailAddressList());
					}
					System.out.println("Type in an address to add to the BCC list.");
					String bccAddress=getAddress();
					
					newEmail.getBCC().addRecipient(new EmailAddress(splitAddress(bccAddress,true),
							splitAddress(bccAddress,false)));
					System.out.println(bccAddress+" added to BCC list!");
					break;
				case 6:
					System.out.println("Type in an ReplyTo address.");
					String replyToAddress=getAddress();
					
					newEmail.setReplyTo(new EmailAddress(splitAddress(replyToAddress,true),
							splitAddress(replyToAddress,false)));
					break;
				case 7:
					if(!newEmail.getBody().equals("") && newEmail.getRecipients().
							getAddressList().size()!=0)
					{
						account.sendEmail(newEmail);
						for(EmailAccount elem: accountList)
						{
							for(EmailAddress recipient: newEmail.getRecipients().getAddressList())
							{
								if(recipient.toString().equals(elem.getAddress().toString()))
								{
									elem.receiveEmail(newEmail);
								}
							}
							
							for(EmailAddress ccRecipient: newEmail.getCC().getAddressList())
							{
								if(ccRecipient.toString().equals(elem.getAddress().toString()))
								{
									elem.receiveEmail(newEmail);
								}
							}
							
							for(EmailAddress bccRecipient: newEmail.getBCC().getAddressList())
							{
								if(bccRecipient.toString().equals(elem.getAddress().toString()))
								{
									elem.receiveEmail(newEmail);
								}
							}
						}
						System.out.println("Email sent successfully!");
						editEmail=false;
					}
					else
					{
						System.out.println("You cannot send the email unless the body is not empty and "
								+"you have at least one recipient");
					}
					break;
			}
		}
	}
	
	public static void moveEmail(EmailAccount account)
	{
		EmailBox initialBox=getEmailBox(account, "Type in the name of the email box you want to see where the email you want to move is, or enter to cancel.");
		
		if(initialBox!=null)
		{
			System.out.println(initialBox.shortString());
			System.out.println("Type in the number of the email you want to move.");
			
			int emailNumber=readInt(initialBox.getEmails().size());
			
			EmailBox finalBox=getEmailBox(account,"Type in the name of the email box you want to move the email to,or enter to go cancel.");
			
			if(finalBox!=null)
			{
				System.out.println("Email moved successfully to "+finalBox.getName()+".");
				account.moveEmail(finalBox, initialBox, initialBox.getEmails().get(emailNumber-1));
			}
			else
			{
				System.out.println("Email move canceled.");
			}
		}
	}
	
	public static void deleteEmail(EmailAccount account)
	{
		EmailBox wantedBox=getEmailBox(account,"Type in the name of the email box where the email you want to delete is, or press enter to cancel.");
		
		if(wantedBox!=null)
		{
			System.out.println(wantedBox.shortString());
			System.out.println("Type in the number of the email you want to delete.");
			
			int emailNumber=readInt(wantedBox.getEmails().size());
			
			System.out.println("Email deleted successfully!");
			account.deleteEmail(wantedBox, wantedBox.getEmails().get(emailNumber-1));
		}
		else
		{
			System.out.println("Email deletion canceled.");
		}
	}
	
	public static void newEmailBox(EmailAccount account)
	{
		kb=new Scanner(System.in);
		String boxName;
		
		System.out.println("Type in the name of your new email box! Or press enter to cancel");
		boxName=kb.nextLine();
		
		if(boxName.equals(""))
		{
			System.out.println("Email box creation canceled.");
		}
		else
		{
			account.addEmailBox(boxName);;
			System.out.println("New email box successfully added");
		}
	}
	
	public static void deleteEmailBox(EmailAccount account)
	{
		EmailBox emailBox=getEmailBox(account,"Type in the name of the email box you want to delete.");
		
		
		if(emailBox==null)
		{
			System.out.println("Email box deletion canceled.");
		}
		else
		{
			if(account.deleteEmailBox(emailBox))
			{
				System.out.println("Email box successfully deleted!");
			}
		}
	}
	
	public static int readInt(int maximum)
	{
		kb=new Scanner(System.in);
		int number=0;
		do
		{
			System.out.println("Type in a number between 1 and "+maximum+".");
			while(!kb.hasNextInt())
			{
				System.out.println("Type in a number between 1 and "+maximum+".");
				kb.nextLine();
			}
			number=kb.nextInt();
		}
		while(number<1 || number>maximum);
		
		kb.nextLine();
		return number;
	}
	
	public static String getAddress()
	{
		kb=new Scanner(System.in);
		String address;
		do
		{
			System.out.println("Type in an email address:");
			address=kb.nextLine();
		}
		while(address.indexOf('@')==-1);
		
		return address;
	}
	
	public static String splitAddress(String address, boolean localPart)
	{
		int i=0;
		for(;i<address.length();i++)
		{
			if(address.charAt(i)=='@')
			{
				break;
			}
		}
		
		if(localPart)
		{
			return address.substring(0,i);
		}
		else
		{
			return address.substring(i+1);
		}
	}
	
	public static EmailBox getEmailBox(EmailAccount account, String message)
	{
		kb=new Scanner(System.in);
		String boxName;
		EmailBox emailBox=null;
		do
		{
			//System.out.println("Type in the name of the email box you want to see where the email you want to move is, or enter to cancel.");
			System.out.println(message);
			boxName=kb.nextLine();

			for(EmailBox elem: account.getFolders())
			{
				if(boxName.equals(elem.getName()))
				{
					emailBox=elem;
					break;
				}
			}
		}
		while(emailBox==null && !boxName.equals("")); 
		
		if(boxName.equals(""))
		{
			return null;
		}
		else
		{
			return emailBox;
		}
	}
}
