/*Class models an email. It has a sender, a recipient(s), a subject and a body. It also
 *  supports having multiple recipients, CC, BCC and ReplyTo addresses. Has multiple 
 *  constructors for when you dont need CC, BCC ,etc. I also has set and get methods 
 *  for each of these things, except no setter for the sende.r*/
public class Email 
{
	private EmailAddress sender;
	private EmailAddressList recipients;
	private EmailAddressList CCList;
	private EmailAddressList BCCList;
	private EmailAddress replyToAddress;
	private String subject;
	private String body;
	
	//constructor for email with no CC or BCC
	public Email (EmailAddress sender, EmailAddressList recipients, String subject, String body)
	{
		this.sender=sender;
		this.recipients=recipients;
		this.subject=subject;
		this.body=body;
	}
	
	//constructor for email with CC and BCC
	public Email (EmailAddress sender, EmailAddressList recipients, EmailAddressList CCList,
			EmailAddressList BCCList, String subject, String body)
	{
		this.sender=sender;
		this.recipients=recipients;
		this.CCList=CCList;
		this.BCCList=BCCList;
		this.subject=subject;
		this.body=body;
	}
	
	//constructor with CC, BCC and ReplyTo
	public Email (EmailAddress sender, EmailAddressList recipients, EmailAddressList CCList,
			EmailAddressList BCCList,EmailAddress replyToAddress, String subject, String body)
	{
		this.sender=sender;
		this.recipients=recipients;
		this.CCList=CCList;
		this.BCCList=BCCList;
		this.replyToAddress=replyToAddress;
		this.subject=subject;
		this.body=body;
	}
	
	//setters for all the variables
	public void setSubject(String subject)
	{
		this.subject=subject;
	}
	
	public void setBody(String body)
	{
		this.body=body;
	}
	
	public void setRecipients(EmailAddressList recipients)
	{
		this.recipients=recipients;
	}
	
	public void setCC(EmailAddressList CCList)
	{
		this.CCList=CCList;
	}
	
	public void setBCC(EmailAddressList BCCList)
	{
		this.BCCList=BCCList;
	}
	
	public void setReplyTo(EmailAddress replyToAddress)
	{
		this.replyToAddress=replyToAddress;
	}
	
	//getter for all the variables
	public EmailAddress getSender()
	{
		return sender;
    }
	
	public EmailAddressList getRecipients()
	{
		return recipients;
	}
	
	public EmailAddressList getCC()
	{
		return CCList;
	}
	
	public EmailAddressList getBCC()
	{
		return BCCList;
	}
	
	public EmailAddress getReplyTo()
	{
		return replyToAddress;
    }
	
	public String getSubject()
	{
		return subject;
	}
	
	public String getBody()
	{
		return body;
	}
	
	@Override
	public String toString()
	{
		String email;
		email="Sender: "+sender.toString()+"\n\n";
		email+="Recipients: "+recipients.toString()+"\n\n";
		if(CCList!=null)
		{
			email+="CC: "+CCList.toString()+"\n\n";
		}
		
		if(BCCList!=null)
		{
			email+="BCC: "+	BCCList.toString()+"\n\n";
		}
		email+="Subject: "+subject+"\n\n";
		if(replyToAddress!=null)
		{
			email+="Reply To: "+replyToAddress.toString()+"\n\n";
		}
		email+=body+"\n\n";
		
		return email;
		
	}
	
	public String shortString()
	{
		String email;
		email="Sender: "+sender.toString()+"\n\n";
		email+="Recipients: "+recipients.toString()+"\n\n";
		if(CCList!=null)
		{
			email+="CC: "+CCList.toString()+"\n\n";
		}
		
		if(BCCList!=null)
		{
			email+="BCC: "+	BCCList.toString()+"\n\n";
		}
		email+="Subject: "+subject+"\n\n";
		if(replyToAddress!=null)
		{
			email+="Reply To: "+replyToAddress.toString()+"\n\n";
		}
		int shortenedBodySize;
		
		if(body.length()<=200)
		{
			shortenedBodySize=body.length()/5;
		}
		else
		{
			shortenedBodySize=40;
		}
		email+=body.substring(0,shortenedBodySize)+"...\n\n";
		return email;
	}
}
