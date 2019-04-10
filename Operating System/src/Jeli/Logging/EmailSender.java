package Jeli.Logging;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

public class EmailSender
{
  private String to;
  private String from;
  private String host;
  private java.util.Properties props;
  
  public EmailSender(String to, String from, String host)
  {
    this.to = to;
    this.from = from;
    this.host = host;
    props = new java.util.Properties();
    props.put("mail.smtp.host", host);
  }
  
  public boolean sendHtmlMessage(String s, String sub)
  {
    return sendAMessage(s, sub, "text/html");
  }
  
  public boolean sendTextMessage(String s, String sub) {
    return sendAMessage(s, sub, "text/plain");
  }
  
  private boolean sendAMessage(String s, String sub, String type)
  {
    try {
      return sendMessage(s, sub, type);
    } catch (Throwable e) {}
    return false;
  }
  

  private boolean sendMessage(String s, String sub, String type)
  {
    javax.mail.Session session = javax.mail.Session.getInstance(props);
    try {
      Message msg = new javax.mail.internet.MimeMessage(session);
      msg.setFrom(new InternetAddress(from));
      InternetAddress[] address = { new InternetAddress(to) };
      msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
      msg.setSubject(sub);
      msg.setSentDate(new java.util.Date());
      msg.setContent(s, type);
      javax.mail.Transport.send(msg);
      return true;
    } catch (javax.mail.MessagingException e) {}
    return false;
  }
}
