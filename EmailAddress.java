import java.util.Scanner;
/*Class models an email address. Contains a constuctor, methods that check if 
 * the localPart and the domain are valid, getters, and a toString method that
 *  displays it as an address*/
public class EmailAddress 
{
	private final String localPart;
	private final String domain;
	private Scanner kb=new Scanner (System.in);
	
	//constructor, loops until you enter a valid address
	public EmailAddress (String localPart, String domain)
	{
		//get a valid local part
		if(!EmailAddress.isValidLocalPart(localPart))
		{
			do
			{
				System.out.println("Invalid local part. Type in a correct local "+
						"part for the address.");
				localPart=kb.nextLine();
			}
			while(!EmailAddress.isValidLocalPart(localPart));
		}
		this.localPart=localPart;
		
		//get a valid domain
		if(!EmailAddress.isValidDomain(domain))
		{
			do
			{
				System.out.println("Invalid domain. Type in a correct domain"+
						"for the address.");
				domain=kb.nextLine();
			}
			while(!EmailAddress.isValidDomain(domain));
		}
		
		this.domain=domain;
	}
	
	public String getLocalPart()
	{
		return localPart;
	}
	
	public String getDomain()
	{
		return domain;
	}
	
	@Override
	public String toString()
	{
		return localPart+"@"+domain;
	}
	
	/*Method to check if the domain is valid according to the rules on the wikipedia
	 * page https://en.wikipedia.org/wiki/Email_address.Domains made from labels, 
	 * strings separated by dots.*/
	public static boolean isValidDomain(String domain)
	{
		
		if(domain.length()==0)
		{
			return false;
		}
		/*We go through the domain, and when we find a dot we call isValidLabel to check if the label is valid*/
		int lastDot=0;
		for(int i=0;i<domain.length();i++)
		{
			if(domain.charAt(i)==46 /*.*/)
			{
				//if its not a valid label, the domain is invalid
				if(!isValidLabel(domain.substring(lastDot, i)))
				{
					return false;
				}
				
				//index i is at a dot now, and the next label starts from the
				//next letter after the dot
				lastDot=i+1;
			}
			//if we reached the last letter of the domain, we have to check the last 
			//label, from the last dot to the end
			else if(i==domain.length()-1 && !isValidLabel(domain.substring(lastDot)))
			{
				return false;
			}
			
		}
		
		return true;
	}
	/*Method checks if a given label is valid according to wikipedia rules.
	 * Private bc its only used in this class.*/
	private static boolean isValidLabel(String label)
	{
		//labels cant be more than 63 character in length
		//if the domain has adjacent dots, some labels will be empty, and that is not valid
		if(label.equals("") || label.length()>63)
		{
			return false;
		}
		
		/*Go through each character.Labels can only have latin letters, digits or hypens, or
		 * parantheses if they are in a comment*/
		for(int i=0;i<label.length();i++)
		{
			//if parantheses arent in a comment, its invalid
			if((label.charAt(i)==40 /* ( */|| label.charAt(i)==41/* ) */) && 
					!isCommented(label, i))
			{
				return false;
			}
			//include parantheses in this boolean bc otherwise if they were commented 
			//it would then return false at the next condition
			boolean validLabelCharacter=(label.charAt(i)==40 || label.charAt(i)==41 
					|| label.charAt(i)==45 /*-*/ || (label.charAt(i)>=48 && label.charAt(i)<=57)
					/*0 to 9*/|| (label.charAt(i)>=65 && label.charAt(i)<=90) /*A to B*/
					|| (label.charAt(i)>=97 && label.charAt(i)<=122)) /*a to b*/;
			
			if(!validLabelCharacter)
			{
				return false;
			}
		}
		
		return true;
	}
	/*Method that determines if a local part of the address is valid, based on 
	 * the rules on the wikipedia page https://en.wikipedia.org/wiki/Email_address*/
	public static boolean isValidLocalPart(String localPart)
	{
		/*Method goes through each letter and checks a number of things to see if
		 *  it is valid. If it is not, it will return false.If it passes through
		 *  all the letters and all are are valid, the method returns true.*/
		for(int i=0;i<localPart.length();i++)
		{
			/*Only 7 bit ASCII characters are allowed and control characters from 
			 * 0 to 32 and 127 (DEL) are not allowed. Space (32) is allowed with 
			 * restrictions.*/
			if(localPart.charAt(i)>126 || localPart.charAt(i)<32)
			{
				return false;
			}
			
			/*Parantheses (ASCII 40 and 41) are only allowed in quotations or 
			 * as part of a comment, so we check them separately.*/
			if(localPart.charAt(i)==40 || localPart.charAt(i)==41)
			{
				//if its neither commented or quoted, its incorrect
				if(!isQuoted(localPart, i) && !isCommented(localPart, i))
				{
					return false;
				}
				/*if is its commented, the comment must either be at the beginning 
				 * or the end of the local part*/
				else if(isCommented(localPart,i) && !(localPart.charAt(i)==40 && i==0)
						&& !(localPart.charAt(i)==41 && i==localPart.length()-1))
				{
					return false;
				}
			}
			
			/*Quotation marks (ASCII 34) are allowed in quoted strings and those quoted
			 *  parts must be separated by dots from the normal part or if all the local
			 *  part is a quoted string*/
			if(localPart.charAt(i)==34)
			{
				//check if they are part of a quoted string
				if(!isQuoted(localPart,i))
				{
					return false;
				}
				//if the " is not the first or last character and it is not preceded or 
				//succeded by a dot, then it is invalid
				else if( (i!=0 && i!=localPart.length()-1) && (localPart.charAt(i-1)!=46
						 && localPart.charAt(i+1)!=46))
				{
					return false;
				}
			}
			/*Besides parantheses and quotation marks, ',:;<>@[\] ' and space
			 *  are also only allowed in quoted strings. I use a boolean to make the
			 *  if statement look clearer.*/
			boolean onlyAllowedinQuotes= localPart.charAt(i)==32 /*space*/ || 
					localPart.charAt(i)==44 /*,*/ || (localPart.charAt(i)>57 && 
					localPart.charAt(i)<61 /*:,; and <*/) || localPart.charAt(i)==62
					/*>*/ || localPart.charAt(i)==64 || (localPart.charAt(i)>90 && 
					localPart.charAt(i)<94 /*[, \ and ]*/);
			//if they are one of those and not in a quoted, the local part is invalid
			if(onlyAllowedinQuotes && !isQuoted(localPart, i))
			{
				return false;
			}
			
			/*additionally, \ (ASCII 92) must also be preceded by another 
			 *backslash , even within a quoted*/
			if(localPart.charAt(i)==92 && (i==0 /*this is put in so if i is 0 
				it wont crash because f charAt(i-1)*/|| localPart.charAt(i-1)!=92) 
				&& (i==localPart.length()-1 /*if i is the last index,i+1
				would be out of bounds, but that expression will not be evauated 
				by Java*/|| localPart.charAt(i+1)!=92))
			{
				return false;
			}
			
			/*Dots (ASCII 46) that are not within quotes must not be the first 
			 * or last character and must be next to another dot.*/
			if(localPart.charAt(i)==46 && !isQuoted(localPart, i))
			{
				if(i==0 || i==localPart.length()-1)
				{
					return false;
				}
				//check of the character before or after are dots
				else if (localPart.charAt(i-1)==46 || localPart.charAt(i+1)==46)
				{
					return false;
				}
			}
		}
		
		//if it has not returned false so far, then all the characters are 
		//valid, so the local part is valid
		return true;
		
	}
	
	/*Method that checks if a character within a string is in a quoted part
	 *of the string. Also checks that " within the string are preceded by a \ .
	 *Method is private because we only use it in the isValidLocalPart method*/
	private static boolean isQuoted(String address, int index )
	{
		/*an empty quoted string is considered invalid*/
		if(address.length()<3)
		{
			return false;
		}
		/*We compare the quotes before and after the index to see if its within 
		 * a quoted part of the string. For instance, for the string 
		 * a"bb"a"bb"a"bb"a. If index is 7, a b in the middle, it will have 3
		 * quotes before and 3 quotes after. For any other case, a b will have 
		 * an odd number of quotes on each side. An a like index 5 will have 
		 * an even number on each side. A special case is when the index is 
		 * at a quotation mark.If we count the quote itself among the quotes 
		 * before, we can see in each case the number of quotes before and 
		 * the one after have the same parity.*/
		int quotesBefore=0, quotesAfter=0;
		
		/*start from the index itself, so if address.charAt(index) is a ", it 
		 * will be counted at quotesBefore.We say that i=0 or the character 
		 * before is a backslash so if the first character is a quote, we dont
		 *  get index out of bounds errors*/
		for(int i=index; i>=0;i--)
		{
			if(address.charAt(i)==34 /*"*/ && (i==0 || address.charAt(i-1)!=92/* \ */))
			{
				quotesBefore++;
			}
		}
		
		/*we start from index+1, but if index is the last letter, we would have 
		 * an error, so we avoid with a conditional*/
		if(index==address.length()-1)
		{
			quotesAfter=0;
		}
		else
		{
			for(int i=index+1; i<address.length();i++)
			{
				if(address.charAt(i)==34 && (i==address.length()-1 || 
						address.charAt(i-1)!=92))
				{
					quotesAfter++;
				}
			}
			
		}
		
		//if both values are odd, it is in quotes
		if(quotesBefore%2==1 && quotesAfter%2==1)
		{
			return true;
		}
		//if the index is at a " and doesnt have \ beofre and the 2 values have
		//the same parity, its also quoted
		else if((address.charAt(index)==34 && (index==0 || 
				address.charAt(index-1)!=92)) && 
				quotesBefore%2==quotesAfter%2)
		{
			return true;
		}
		else
		{
			//if its neither of those cases, its not within a quoted
			return false;
		}
	}
	/*Method to check a character is within a comment in a string.Private bc
	 *  its only used in this class. It only gets called when .charAt(index) 
	 *  is a parantheses. So if it is a ( finds to the left of it a ) or its 
	 *  a ) and finds a ( to the right of its, it return true*/
	private static boolean isCommented(String address, int index)
	{
		int i=index;
		if(address.charAt(index)==40 /* ( */)
		{
			while(i<address.length())
			{
				if(address.charAt(i)==41/* ) */)
				{
					return true;
				}
				i++;
			}
		}
		
		i=index;
		
		if(address.charAt(index)==41 /* ) */)
		{
			while(i>=0)
			{
				if(address.charAt(i)==40)
				{
					return true;
				}
				i--;
			}
		}
		//if it has not returned true by now, its not in a comment
		return false;
	}
}
