/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.email.resources;



import com.paycraftsystems.cmfb.dto.MailObj;
import com.paycraftsystems.resources.ValidationHelper;
//import com.paycraftsystems.fsi.resources.ValidationHelper;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author root
 */
public class MailTemplates {
    
    private static Logger LOGGER = LoggerFactory.getLogger(MailTemplates.class);
    
    
    /*
    public String doSelfServiceMail(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>CU PIN Notification :::</title>"+
		"<style>"+
		".tdBg { background-color: #054123 }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: #B29600;"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: #19b56d; background-color: #B29600 }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong> Covenant University Fees Payment Notification </strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.getReceipient()+", </p>"+
		"      <p>Please click on the link below to supply your matric number and complete this payment</p>"+
		"      <p>payments@covenantmfb.com.ng or call 01-454-5571"+
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Payment Reference :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.getPaymentReference()+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Please click on the link :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.getOtherInfo()+"\"><b>PLEASE CLICK TO SUPPLY YOUR MATRIC No.</b></a></td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>Covenant Micro Finance Bank</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\"https://covenantmfb.com.ng/\"><b>Covenant Micro Finance Bank</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>CMFB Support Team.</p>"+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
        
        }
        
     return resp;
    }*/
    
    
     public String doWelcomeMail(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>"+obj.mailTitle+" :::</title>"+
		"<style>"+
		".tdBg { background-color: "+obj.tableHDColor+" }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: "+obj.tableBGColor+";"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: "+obj.tableBorderColor+"; background-color: "+obj.tableBGColor+" }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>"+obj.banner+"</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.addressee+", </p>"+
		"      <p>We are so happy you are now of us, please see your welcome and other relevant information below</p>"+
	
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Virtaul Account :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.toShare+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Virtaul Account Bank :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.toShareLabel+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Please click on the link :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.ourUrl+"\"><b>Kindly click to access your personal dashboard.</b></a></td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>"+obj.US+"</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.www+"/\"><b>"+obj.US+"</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>"+obj.supportInfo+".</p>"+
                " <p>we can be reached via the channels below.</p>"+
                " <p>"+obj.supportLines+""+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(" Exception -- ",e);
        }
        
     return resp;
    }
     
      public String doAdminWelcomeMail(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>"+obj.mailTitle+" :::</title>"+
		"<style>"+
		".tdBg { background-color: "+obj.tableHDColor+" }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: "+obj.tableBGColor+";"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: "+obj.tableBorderColor+"; background-color: "+obj.tableBGColor+" }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>"+obj.banner+"</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.addressee+", </p>"+
		"      <p>We are so happy you are now of us, please see your welcome and other relevant information below</p>"+
	
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> OTP :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.toShare+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'> Information :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+obj.toShare1+"</td>"+
		"  </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Please click on the link :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.ourUrl+"\"><b>Kindly click to access your personal dashboard.</b></a></td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>"+obj.US+"</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.www+"/\"><b>"+obj.US+"</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>"+obj.supportInfo+".</p>"+
                " <p>we can be reached via the channels below.</p>"+
                " <p>"+obj.supportLines+""+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(" Exception -- ",e);
        }
        
     return resp;
    }
     
     
     
     public String doOTPMail(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>"+obj.mailTitle+" :::</title>"+
		"<style>"+
		".tdBg { background-color: "+obj.tableHDColor+" }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: "+obj.tableBGColor+";"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: "+obj.tableBorderColor+"; background-color: "+obj.tableBGColor+" }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>"+obj.banner+"</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.addressee+", </p>"+
		"      <p>Kindly find  your OTP  "+obj.toShare+", it is good to note that this OTP expires in  "+obj.otpExpiration+" Minutes</p>"+
		
		"      </td>"+
		"    </tr>"+
                "  <tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Please click on the link :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.ourUrl+"\"><b>Kindly click to access your personal dashboard.</b></a></td>"+
		"  </tr>"+
                "  <tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'>"+
		"      <p>Thank you for choosing <b>"+obj.US+"</b> your trusted partner </p>"+
		
		"      </td>"+
		"  </tr>"+
		"<tr valign='middle' bgcolor='#FFFFFF'>"+
		"    <td width='99' align='right' valign='middle' class'norm_text'><span class='norm_text'>Our URL :</span>&nbsp;</td>"+
		"    <td width='158' valign='middle' class='norm_text'>"+"<a href=\""+obj.www+"/\"><b>"+obj.US+"</b></a>"+"</td>"+
		"</tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>"+obj.supportInfo+".</p>"+
                " <p>We can be reached via the channels below.</p>"+
                " <p>"+obj.supportLines+""+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(" Exception -- ",e);
        }
        
     return resp;
    }
     
     
    public String doOTPMailV2(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>"+obj.mailTitle+" :::</title>"+
		"<style>"+
		".tdBg { background-color: "+obj.tableHDColor+" }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: "+obj.tableBGColor+";"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: "+obj.tableBorderColor+"; background-color: "+obj.tableBGColor+" }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>"+obj.banner+"</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.addressee+", </p>"+
		"      <p>Kindly find  your OTP  "+obj.toShare+"</p>"+
                "      <p>It is good to note that this OTP expires in  "+obj.otpExpiration+" Minutes</p>"+
		
		"      </td>"+
		"    </tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>"+obj.supportInfo+".</p>"+
                " <p>We can be reached via the channels below.</p>"+
                " <p>"+obj.supportLines+""+
		"    </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(" Exception -- ",e);
        }
        
     return resp;
    }
    
    
    public String doResetMailV2(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>"+obj.mailTitle+" :::</title>"+
		"<style>"+
		".tdBg { background-color: "+obj.tableHDColor+" }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: "+obj.tableBGColor+";"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: "+obj.tableBorderColor+"; background-color: "+obj.tableBGColor+" }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>"+obj.banner+"</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.addressee+", </p>"+
		"      <p>Kindly find  your Secure API credentials </p>"+
              
                "      <p>IV  "+obj.toShare1+" </p>"+
                "      <p> KEY  "+obj.toShare2+" </p>"+
                "      <p> Expiration in  "+obj.toShare+" Day's </p>"+
                "      <p>You can  choose to reset, via the reset endpoint at any point in time or the expiration time</p>"+
                "      <p>Kindly note that, whenever these keys are changed, you are required to call the sys-login end point again to get a new JWT access token</p>"+
		
		"      </td>"+
		"    </tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>"+obj.supportInfo+".</p>"+
                " <p>We can be reached via the channels below.</p>"+
                " <p>"+obj.supportLines+""+
		" </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(" Exception -- ",e);
        }
        
     return resp;
    }
    
    
    public String doCustomerWelcomeMailV2(MailObj obj) {
        String resp = "";
        try 
        {
            
                resp =   "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'"+
		"'http://www.w3.org/TR/html4/loose.dtd'>"+
		"<html>"+
		"<head>"+
		"<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>"+
		"<title>"+obj.mailTitle+" :::</title>"+
		"<style>"+
		".tdBg { background-color: "+obj.tableHDColor+" }"+
		".norm_text {"+
		"	font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_bold {"+
		"	font-weight:bold; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".norm_text_white { "+
		"	color:#FFFFFF; font-size: 8pt; font-family: Tahoma,Verdana, Geneva, Arial, helvetica, sans-serif;"+
		"}"+
		".row_color1 {"+
		"	background-color: "+obj.tableBGColor+";"+
		"}"+
		".bordered_table1{ border-width:0.5pt; border-style:solid; border-color: "+obj.tableBorderColor+"; background-color: "+obj.tableBGColor+" }"+
		"</style>"+
		"</head>"+ 
		"<body>"+
		"<table width='555' border='0' cellpadding='2' cellspacing='1' class='bordered_table1' align='center'>"+
		"  <tr valign='middle' class='tdBg'>"+
		"    <td height='20' colspan='4' class='norm_text_white'><strong>"+obj.banner+"</strong></td>"+
		"  </tr>"+
		"	<tr valign='middle' class='row_color1'>"+
		"    <td height='20' colspan='4' class='norm_text_bold'><p>Dear "+obj.addressee+", </p>"+
		"    <p>Welcome to the Collabo  API Gate way System</p>"+
                "    <p>Your Customer Code  for ("+obj.toShare2+") is  "+obj.toShare+"</p>"+      
                "    <p>Kindly find  your Secure API credentials </p>"+
              
                "      <p>Administrator UserName  "+obj.toShare1+" </p>"+
                "      <p>Your Sign up OTP is  "+obj.message+" </p>"+
                 "      <p>Kindly note that this expires after 10 Minutes</p>"+
                "      <p>Administrator Password : As Securely Chosen by you, or you can do a reset using the username displayed above </p>"+
                "      <p>Should you need any clarity, support or assistance, Kindly contact us, via our support Channels as shown below</p>"+
		"      </td>"+
		"    </tr>"+
		"<tr valign='middle' class='row_color1'>"+
		"<td height='20' colspan='4' class='norm_text_bold'><p>Regards, </p>"+
		" <p>"+obj.supportInfo+".</p>"+
                " <p>Our Support Channels</p>"+
                " <p>"+obj.supportLines+""+
		" </td>"+
		"</tr>"+
		"</table>"+
		"</body>"+
		"</html>";
            
        } 
        catch (Exception e) {
        
             e.printStackTrace();
             
             LOGGER.error(" Exception -- ",e);
        }
        
     return resp;
    }
     
     
     
     
     
   public JsonObject doPepEmail(String mailFrom, String fromName,  String mailTo,  String subject, String msg) {
        
        ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder(); 
        try 
        {
          
            job.add("personalizations", Json.createArrayBuilder().add(Json.createObjectBuilder()
                    .add("recipient",  rh.toDefault(mailTo))))
                    .add("from", Json.createObjectBuilder().add("fromEmail", rh.toDefault(mailFrom)).add("fromName", rh.toDefault(fromName)))
                    .add("subject", rh.toDefault(subject)).add("content",rh.toDefault(msg));
            
        } 
        catch (Exception e) {
       
            
            e.printStackTrace();
        
        }
     return job.build();
    }
    
    public JsonObject doV2NEmail(String msg, String subject, boolean isHTML, String senderName, String senderEmail, String receiverName, String receiverEmail, String copyName, String copyEmail) {
         ValidationHelper rh = new ValidationHelper();
        JsonObjectBuilder job = Json.createObjectBuilder();
        try 
        {
           
            job.add("message", rh.toDefault(msg))
               .add("sender", Json.createObjectBuilder().add("name", rh.toDefault(senderName)).add("address", rh.toDefault(senderEmail)))
               .add("subject", subject)
               .add("isHTML", isHTML)
               .add("receiver", Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", rh.toDefault(receiverName)).add("address", receiverEmail)))
               .add("copy", Json.createArrayBuilder().add(Json.createObjectBuilder().add("name", rh.toDefault(copyName)).add("address", rh.toDefault(copyEmail))))
                    ;
            
        } 
        catch (Exception e) {
       
            e.printStackTrace();
        
        }
       return job.build(); 
    }
		
    
}
