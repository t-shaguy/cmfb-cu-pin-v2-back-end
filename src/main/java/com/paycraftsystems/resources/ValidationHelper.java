
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change DDhis template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.resources;

//import com.github.sisyphsu.dateparser.DateParserUtils;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.Phonenumber;
import com.paycraftsystems.cmfb.sec.AESCrypter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
//import com.paycraftsystems.sec.resources.AESCrypter;
import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.Calendar;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author paycraftsystems-i
 */
@Slf4j
public class ValidationHelper {
    
   // private static  Logger LOGGER = LoggerFactory.getLogger(ValidationHelper.class);
     
    public static final String EMAIL_REGEX  = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    public static final String MOBILE_E_123_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
    
    public static final String DOMAIN_NAME_PATTERN = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
    
    public static final String PASSWORD_COMPLEXITY  = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{6,20}$";
    
    public static final String IP_REGEX  = "\\b((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.|$)){4}\\b";
    
    public static final String PIN_REGEX  = "^(\\d)(?!\\1+$)\\d{3}$";
    
    public static final String TELCO [] = {"GLO", "MTN", "AIRTEL", "9MOBILE", "ETISALAT"};
    
    public static final String TRANS_TYPE [] = {"Credit", "Debit"};
    
    
    public static final String IMAGE_TYPE [] = {"data:image/png;base64", "data:image/jpg;base64", "data:image/jpeg;base64", "data:image/gif;base64"};
     
    public static final String BLOCK_TYPE [] = {"block", "unblock"};
     
    public static final String GENDER [] = {"M", "F", "C"};
    
    public static final String FW_VALID_TRANS_STATUS [] = {"successful", "failed"};
    
    public static final String FW_VALID_RESOURCE_STATUS [] = {"inactive", "active"};
   
     
    public static final String FW_VALID_CHARGEBACK_STATUS [] = {"lost", "won", "initiated", "accepted", "declined"};
                                              //^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$
    public static final String BASE_64_REGEX = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";
   
    
    public static final String FEE_TRANS_TYPE [] = {"T", "W"};
    
    
    public static final String FW_SPLIT_TYPE [] = {"percentage", "flat"};
    
     //percentage or flat
    
    public static final String FLW_BILLPAYMENT_FREQ  [] = {"ONCE", "HOURLY", "DAILY", "WEEKLY", "MONTHLY"};
   
    /*
    0- No Restriction
1- Debit Restriction 
2- Credit Restriction 
3- Total Restriction
    */
    
    
    public static  String formatAmtStr(String amount)
    {
         if(amount == null || amount.isEmpty()) amount = "0.0";
         return new DecimalFormat("#0.00").format(Double.valueOf(amount.trim()));
       
    }
    
    
    public   String getSubscriptionType(long days)
    { 
         String  duration = "";
         if(days == 0 ||  days == 1)
         {
             duration = "DAILY";
         }
         if(days == 6 || days == 7)
         {
             duration = "WEEKLY";
         }
         if(days > 28)
         {
             duration = "MONTHLY";
         }
        
         return duration;
       
    }
    
    public String getSubscriptionType(LocalDateTime start,LocalDateTime end)
    { 
         String  duration = "";
         Long days =  start.until(end, ChronoUnit.DAYS);
         if(days == 0 ||  days == 1)
         {
             duration = "DAILY";
         }
         if(days == 6 || days == 7)
         {
             duration = "WEEKLY";
         }
         if(days > 26)
         {
             duration = "MONTHLY";
         }
        
         return duration;
       
    }
    
    public static boolean containSpecialCharacter(String str) {
        
        boolean contains = false;
        try 
        {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_ ]*$");//[a-zA-Z0-9]*");
            Matcher matcher = pattern.matcher(str);

            if (!matcher.matches()) {
                 contains = true;
                 System.out.println("string '"+str + "' contains special character");
            }
            
        } catch (Exception e) {
        
        
            
        }
      
        return contains;
    }
   
    
    
    public String  doPassStatus(int status) {
        String resp = "";
        try 
        {
           // 2 lost, 1 won, 0 initiated, 3 accepted,4 declined
            switch(status)
            {
                case 0:
                resp = "initiated";
                break;
                case 1:
                resp = "won";
                break;
                case 2:
                resp = "lost";
                break;
                case 3:
                resp = "accepted";
                break;
                case 4:
                resp = "declined";
                break;
                default:
                   resp = "all";
                break;
               
            }
            
        } catch (Exception e) {
        
        
        }
     return resp;
    }
    
 /*
    public boolean isValidJsonArray(String json) {
    try 
    {
        new JSONObject(json);
    } 
    catch (JSONException e) {
        try 
        {
            new JSONArray(json);
            
            
        } catch (JSONException ne) {
            return false;
        }
    }
    return true;
}
*/  
    
    /*
    public static boolean isValidJsonObj(String json) {
    try 
    {
        JsonParser.parseString(json);
        
    } catch (JsonSyntaxException e) {
        return false;
    }
    return true;
   }
   */
     public static final String STERLING_RESTRICTION_CODE  [] = {"0", "1", "2", "3"};
   
    
    public static final String FLW_BILLER_CATEGORIES [] = {"0", "1", "2", "3", "4","5", "6"}; //) -all  1- airtime 2- databundle 3- toll 4- power , 5 - internet  6 - cable 
    
    //public Response doPullBillCategories(@HeaderParam("Authorization") String SecretKey, @QueryParam("airtime") int airtime,  @QueryParam("data_bundle") int data_bundle,@QueryParam("toll") int toll,@QueryParam("power") int power,@QueryParam("internet") int internet,@QueryParam("cable") int cable, @QueryParam("biller_code") String biller_code);
   
    
    public static final String BROADCAST_TO [] = {"ALL", "AGENTS", "SUBSCRIBERS"};
    
     public static final String FLW_OTP_MEDIUM [] = {"email", "sms","whatsapp"};
  
    public static final String NOTIFICATION_OPTIONS [] = {"0", "1", "2","4", "CW", "E-OTP","CRX", "TRX", "FR"}; // 0 - OTP  - 1 welcome 2 EOTP
    
    public static final String BILLPAY_OPTIONS [] = {"1", "2", "3", "4", "5", "6", "7", "8"};  // 1- pay TV 2- electricity 3- reversed  4- internet 5- LCC 6 reserved 7 - DATA 8 VTU
    
    
    public static final String WALLET_TIER_OPTIONS [] = {"1", "2", "3"};
    
    public static String  CURRENCY_REQUIRING_DEST_BRANCH_CODE [] = {"GHS", "UGX", "XOF", "XAF", "TZS"};
    
    public static String  FW_VALID_TRANS_CURRENCY [] = {"GHS","NGN", "UGX", "KES", "ZAR", "TZS", "USD"};
     
    public static String  FW_FETCH_TRANS_CURRENCY [] = {"USD","NGN", "EUR", "GBP"};
    

     
     public static String  FW_VALID_TRANS_RATE_CURRENCY [] = {"GHS","NGN", "UGX", "KES", "RWF",  "USD"};
     
     public static String  FW_PAYMENT_PLAN_INTERVAL [] = {"yearly","quarterly", "monthly", "daily"};
     
     //yearly, quarterly, monthly, monthly, daily
     
      public static String  FW_VALID_PAYMENT_TYPE [] = {"card","debit_ng_account", "mobilemoney", "bank_transfer", "ach_payment"};
     
   //  card, debit_ng_account, mobilemoney, bank_transfer, and ach_payment.Kindly
    // NGN, USD, GHS, KES, UGX, RWF
    
    
    public static final String KYC_FILE_TYPE [] = {"1", "2", "3"}; //1 - VALID_ID(1), GAURANTOR_FORM(2), GAURANTOR_ID(3), UTILITY_BILL(4);
    
    public String toDefault(String value) {
        
        return (value == null || value.isEmpty())?"NA":value.trim();
    }
    
    public String toFWMobile(String value) {
        
        return (value != null && value.startsWith("+234") && value.length() == 14)?"0"+value.substring(4):value.trim();
    }
    
   
    
    public String toDefault(Double value) {
        
        return (value == null )?"0.00":""+value.doubleValue();
    }
    
    public String toDefault(byte[] value) {
        
        return (value == null || value.length ==0 )?"NA":Base64.getEncoder().encodeToString((value));
    }
    
    public int toDefault(BigInteger value) {
        
        return (value == null )?0:value.intValue();
    }
    
    public String toDefault(Date value) {
        
        return (value == null)?"0000-NON-00":new SimpleDateFormat("yyyy-MMM-dd").format(value);
    }
    
    public String toDefaultMM(Date value) {
        
        return (value == null)?"0000-00-00":new SimpleDateFormat("yyyy-MM-dd").format(value);
    }
    
    static boolean isValidIMEI(long n)
    {
        // Converting the number into String
        // for finding length
        String s = Long.toString(n);
        int len = s.length();
 
        if (len != 15)
            return false;
 
        int sum = 0;
        for (int i = len; i >= 1; i--)
        {
            int d = (int)(n % 10);
 
            // Doubling every alternate digit
            if (i % 2 == 0)
                d = 2 * d;
 
            // Finding sum of the digits
            sum += sumDig(d);
            n = n / 10;
        }
 
        return (sum % 10 == 0);
    }
    
    static int sumDig(int n)
    {
        int a = 0;
        while (n > 0)
        {
            a = a + n % 10;
            n = n / 10;
        }
        return a;
    }
    
    public String toDefault(LocalDate value) {
        //System.out.println("valueDT = " + value);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//.ISO_LOCAL_DATE;
        try 
        {
             if(value !=null)
             {
               String valueDT = value.format(formatter);// ""+LocalDateTime.parse(value.toString(), formatter);
             //System.out.println("### valueDT = "+valueDT);
               return (value == null)?"0000-00-00":valueDT;//new SimpleDateFormat("yyyy-MM-dd").format(value);
             }
             else
             {
                 
                 return "0000-00-00";
                 
             }
            
        } catch (Exception e) {
        
            e.printStackTrace();
            return "0000-00-00";
        }
      
    }
    public String toDefaultLD(LocalDate value) {
        //System.out.println("valueDT = " + value);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//.ISO_LOCAL_DATE;
        try 
        {
               return  value ==null?LocalDate.now().format(formatter):value.format(formatter);// ""+LocalDateTime.parse(value.toString(), formatter);
            
        } catch (Exception e) {
        
            e.printStackTrace();
            return "0000-00-00";
        }
      
    }
    public String toDefault(String value, String defaultValue) {
        
        return (value == null || value.isEmpty())?defaultValue:value.trim();
    }
    
    public String toDefault(Double value, String defaultValue) {
        
        return (value == null )?defaultValue:new DecimalFormat("#,###.##").format(value);
    }
    
   public LocalDate dateToLD(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
   }
   
   
   public LocalDate doddMMMyyToLDT(String passed)
   {
       LocalDate date = null;
       try 
       {
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");
           date = LocalDate.parse(passed, formatter);
           
           
       } catch (Exception e) {
           
           e.printStackTrace();
       }
       
    return date;
   }
   
   
   
    public LocalDate dateToLD(String dateStr) {
    log.info(" --  dateToLD -- "+dateStr);
   
    
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//.ISO_LOCAL_DATE;
        try 
        {
            // System.out.println(": -  : "+formatter.toString());
             System.out.println(" :ld to str : "+dateStr.toString());
             //String valueDT = value.format(formatter);// ""+LocalDateTime.parse(value.toString(), formatter);
             //System.out.println("### valueDT = "+valueDT);
             return LocalDate.parse(dateStr, formatter);//?"0000-00-00":valueDT;//new SimpleDateFormat("yyyy-MM-dd").format(value);
            
        } catch (Exception e) {
        
            e.printStackTrace();
            return null;//"0000-00-00";
        }
   }
   public LocalDate dateAsMMDDYYYToLD(String dateStr) {
  // dateStr.substring(2, 4));  //dateStr.substring(6, 10)  dateStr.substring(0, 2)
     Calendar instance = Calendar.getInstance();
            instance.set(Integer.parseInt(dateStr.substring(6, 10)), Integer.parseInt(dateStr.substring(2, 4)), Integer.parseInt(dateStr.substring(0, 2)));
           
    Date time = instance.getTime();
         
    return time.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
   }
   
   public LocalDateTime dateAsMMDDYYYToLDT(String dateStr) {
  // dateStr.substring(2, 4));  //dateStr.substring(6, 10)  dateStr.substring(0, 2)
     Calendar instance = Calendar.getInstance();
            instance.set(Integer.parseInt(dateStr.substring(0, 4)), Integer.parseInt(dateStr.substring(4, 6)), Integer.parseInt(dateStr.substring(6, 8)));
           
    Date time = instance.getTime();
         
    return time.toInstant()
      .atZone(ZoneId.systemDefault()).toLocalDateTime();
   }
   
public static boolean isDateValid(String date) 
{
     final  String DATE_FORMAT = "yyyy-mm-dd";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
}
    
    public String to2PD(double value) {
        
        return new DecimalFormat("#,###.##").format(value);
    }
    
    public String toDefault(BigDecimal value, String defaultValue) {
        
        return (value == null )?defaultValue:new DecimalFormat("#,###,###.##").format(value);
    }
    
    public String toDefault(long value) {
        
        return new DecimalFormat("#,###,###").format(value);
    }
    
    /*
    public String toDefault(BigDecimal value, String defaultValue) {
        
        return (value == null )?defaultValue:new DecimalFormat("#,###,###.##").format(value);
    }*/
    
    public String doTrim(String thestring, int maxlen)
    {
        return (thestring !=null && thestring.trim().length() > maxlen)?thestring.substring(0,maxlen):thestring;
    }
    
    public boolean isClean(Long pid, double active, LocalDateTime bhashDate, String iv, String key,  String hash) {
       log.info(">> isClean encrypt = " + active+" bhashDate- "+bhashDate+" hash : "+hash+"-pid - "+pid+" iv "+iv+" key : "+key);
        boolean clean = false;
        try {

         
           if(bhashDate != null && hash !=null)
           {
                String plainhash = new AESCrypter(iv,key).decrypt(hash);// String plainhash = new AESCrypter(key,iv).decrypt(hash);
                log.info("encrypt =>> " + plainhash.split("#")[0]+" pid "+pid);
                double amx = Double.parseDouble(plainhash.split("#")[0]);
                log.info("active "+active+"->>amx = " + amx + " - " + new BigDecimal(amx).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
                clean = (active == new BigDecimal(amx).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
                double dd = Double.parseDouble(plainhash.split("#")[0]);
                BigDecimal setScale = new BigDecimal(dd).setScale(2, RoundingMode.HALF_DOWN);
                log.info("encrypt = " + clean + " -+- " + setScale.doubleValue());
           }
           else
           {
               clean = true;
           }

        } catch (Exception e) {

            e.printStackTrace();
            log.error("Exception @isClean ",e);
        }
        return clean;
    }
    
    public boolean doWriteFile(String fileDir, String fileName, String base64Str) {
      log.info("fileDir = " + fileDir+" : fileName "+fileName);
      boolean done = false;
      try 
      {
            File file = new File(fileDir+"/"+fileName);
            try ( FileOutputStream fos = new FileOutputStream(file); ) {
              byte[] decoder = Base64.getDecoder().decode(base64Str);

              fos.write(decoder);
              log.info("-- fileName File Saved");
              
              done = true;
            } catch (Exception e) {
             // e.printStackTrace();
              
               log.error("EXCEPTION @ inner doWriteFile ",e);
            
            
            }
      
      
      
      } catch (Exception e) {
      
          
         // e.printStackTrace();
         
         log.error("EXCEPTION @ doWriteFile ",e);
          
          
      
      }

     return done;
           
  }
    
  public boolean doWriteFile(String fileDir, String fileName, ByteArrayInputStream bis) {
      log.info("fileDir = " + fileDir+" : fileName "+fileName);
      boolean done = false;
      try 
      {
            File file = new File(fileDir+"/"+fileName);
            try ( FileOutputStream fos = new FileOutputStream(file); ) {
              byte[] decoder = bis.readAllBytes();// Base64.getDecoder().decode(base64Str);

              fos.write(decoder);
              log.info("-- fileName File Saved");
              
              done = true;
            } catch (Exception e) {
             // e.printStackTrace();
              
               log.error("EXCEPTION @ inner doWriteFile ",e);
            
            
            }
      
      
      
      } catch (Exception e) {
      
          
         // e.printStackTrace();
         
         log.error("EXCEPTION @ doWriteFile ",e);
          
          
      
      }

     return done;
           
  }
  
   public LocalDateTime doParseUhuruDate(String dateStr) 
   {
        LocalDateTime dateObj = null;
        try 
        {
            if(dateStr !=null && dateStr.trim().length() == 14)
            {
                dateObj = LocalDateTime.of(Integer.parseInt(dateStr.substring(0, 4)), Integer.parseInt(dateStr.substring(4, 6)), Integer.parseInt(dateStr.substring(6, 8)), Integer.parseInt(dateStr.substring( 8, 10)), Integer.parseInt(dateStr.substring( 10, 12)), Integer.parseInt(dateStr.substring( 12, 14)));
            }
            /*
            else
            {
               dateObj =  LocalDateTime();
            }
            */
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
   return dateObj;
  }
    
  
  /*
  public String method() {
         byte[] encoded = null;
        try 
        {
            Base64 codec = new Base64();
           [encoded = codec.encode("Hello".getBytes());
            
        } catch (Exception e) {
        
            LOGGER.error(IP_REGEX);
        
        }
        
    }
   */
    
     
     
    public Date toDate(String dateStr) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	//convert String to LocalDate
	LocalDate localDate = LocalDate.parse(dateStr, formatter);
        
        return convertToDateViaInstant(localDate);
    }
    
    
    
    public boolean isValidLocalDate(String dateStr) {
        boolean isValid = false;
        try 
        {
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	     LocalDate localDate = LocalDate.parse(dateStr, formatter);
             
             isValid =  true;
        } 
        catch (Exception e) {
        
           e.printStackTrace();
                   
        }
      return isValid;
    }
    
    
    public LocalDateTime toLocalDateTime(String dateStr) {
    //.toLocalDate()..toDate().getTime())   
        try 
        {
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	     return  LocalDateTime.parse(dateStr, formatter);
            
        } 
        catch (Exception e) {
        
           e.printStackTrace();
                   
        }
      return null;
    }
    
     public long toLocalDateTimeMillis(String dateStr) {
    //.toLocalDate()..toDate().getTime())   
        try 
        {
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

             //LocalDateTime.parse(dateStr).toLocalDate().toDate().getTime();
            LocalDateTime ldt = LocalDateTime.parse(dateStr, formatter);
            Instant inst = ldt.toInstant(ZoneOffset.UTC);
	    return inst.toEpochMilli();
            
        } 
        catch (Exception e) {
        
           e.printStackTrace();
                   
        }
      return 0;
    }
    
     public LocalDate toLocalDate(String dateStr) {
       
        try 
        {
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	     return  LocalDate.parse(dateStr, formatter);
            
        } 
        catch (Exception e) {
        
           e.printStackTrace();
                   
        }
      return null;
    }
     
     public boolean isValidLocalDateTime(String dateStr) {
       boolean valid = false;
        try 
        {
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	     LocalDateTime.parse(dateStr, formatter);
             
             valid = true;
            
        } 
        catch (Exception e) {
        
           e.printStackTrace();
                   
        }
      return valid;
    }
     
    public boolean isValidLocalDateTimeWithTZ(String dateStr) {
       boolean valid = false;
        try 
        {
            //MM/dd/yyyy
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	     LocalDateTime.parse(dateStr, formatter);
             
             valid = true;
            
        } 
        catch (Exception e) {
        
           e.printStackTrace();
                   
        }
      return valid;
    }
    
    public boolean isValidLocalDateTimeWithSterlingTZ(String dateStr) {
       boolean valid = false;
        try 
        {
            //MM/dd/yyyy
             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy'T'HH:mm:ss.SSSZ");

	     LocalDateTime.parse(dateStr, formatter);
             
             valid = true;
            
        } 
        catch (Exception e) {
        
           e.printStackTrace();
                   
        }
      return valid;
    }
    
    public Date convertToDateViaInstant(LocalDate dateToConvert) {
    return java.util.Date.from(dateToConvert.atStartOfDay()
      .atZone(ZoneId.systemDefault())
      .toInstant());
    }
    
    public long doTransAgeInHrs(Instant inst) {
        
        LocalDateTime ofInstant = LocalDateTime.ofInstant(inst, ZoneOffset.systemDefault());
        
        return  ofInstant.until(LocalDateTime.now(), ChronoUnit.HOURS);
    }
    
    
    public long doTransAgeInHrs(LocalDateTime ldt) {
        
        //LocalDateTime ofInstant = LocalDateTime.ofInstant(inst, ZoneOffset.systemDefault());
        
        return  ldt.until(LocalDateTime.now(), ChronoUnit.HOURS);
    }
    
    
    public static String doLDTToYYYYMMDDHHMMSS(LocalDateTime  passed) {
        String formatted = "";
        try 
        {   
            formatted = passed.format(DateTimeFormatter.ofPattern("YYYYMMDDHHMMSS"));
            //System.out.println("this = " + ));
        } 
        catch (Exception e) {
        e.printStackTrace();
        }
      return formatted; 
    }
    
    public String doBhash(LocalDateTime ldt, double amt) {
        //double amt = 20.00;
        String tohash = "";
        try 
        {
            tohash  = String.valueOf(amt)+"#"+ldt.format(DateTimeFormatter.ofPattern("YYYYMMddHHmmss"));
            System.out.println("amt = " + ""+amt+" -- "+String.valueOf(amt)+"tohash");
            //System.out.println("this = " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYYMMDDHHMMSS")));
        } 
        catch (Exception e) {
        e.printStackTrace();
        }
        return tohash;
    }
    
     public String toMessageStatus(String option) {
        if(option == null) option = "";
        String resp = "";
        try 
        {
            //System.out.println("toPad = " + option);
            switch(option)
            {
                case "R":
                resp = "Read";
                break;
                case "D":
                resp = "Delivered";
                break;
                case "E":
                resp = "Editted";
                break;
                default:
                resp = "Unknown";
                break;
            }
           
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
        return resp;
    }
     
    public static String toISOAmount(double value) {
        
        return new DecimalFormat("000000000000").format(value  * 100);
     }
     
      public static String toISOAmount(String valueStr) {
          
        double value = (valueStr == null || valueStr.trim().equals(""))?0.0: Double.parseDouble(valueStr.trim());
        
        return new DecimalFormat("000000000000").format(value  * 100);
     }
    
     
     public static String toISOFee(double value) {
        
        return new DecimalFormat("D000000000").format(value  * 100);
     }
     
     public static String toISOFee(String valueStr) {
         
        double value = (valueStr == null || valueStr.trim().equals(""))?0.0: Double.parseDouble(valueStr.trim());
       
        return new DecimalFormat("D000000000").format(value  * 100);
     }
     
     public String toAccountClass(String option) {
       
        String resp = "";
        try 
        {
            //System.out.println("toPad = " + option);
            switch(option)
            {
                case "22":
                resp = "personal";
                break;
                case "23":
                resp = "business";
                break;
                default:
                resp = "Other";
                break;
            }
           
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
        return resp;
    }
     
    public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false;
    }
    try {
        double d = Double.parseDouble(strNum);
    } catch (NumberFormatException nfe) {
        return false;
    }
    return true;
   }
    
    
    public static double doDoubleValue(String strNum) {
    double d = 0.0;
    try 
    {
         d = Double.parseDouble(strNum);
    } 
    catch (NumberFormatException nfe) {
        return 0.0;
    }
    return d;
   }
    
    
    
    public boolean doVerifyAmount(String amount) {
        boolean valid = false;
        double slotSize = 500;
        double modulo = 0;
        try 
        {
            double parseDouble = Double.parseDouble(amount.trim());
           
            modulo = (parseDouble % slotSize);
            
            if(modulo < 0.1)
            {
                valid = true;
            }
               
        } 
        catch (NumberFormatException e) {
        
          e.printStackTrace();
                  
        }
     return valid;
    }
    
    public String toDefault(LocalDateTime value) {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return (value == null)?"0000-NON-00":value.format(ofPattern);
    }
    
    
    public String toDefaultAMPM(LocalDateTime value) {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        return (value == null)?"0000-NON-00":value.format(ofPattern);
    }
    
    public String toDefaultAMPM(LocalDateTime value, String defaultTo) {
        DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        return (value == null)?defaultTo:value.format(ofPattern);
    }
    
    public String toDateStr(Date dt) {
        
        
        return (dt == null)?"NA":new SimpleDateFormat("yy-MMM-dd").format(dt);
    }
    
    public String doFormatDate() {
         String format = "";
         try 
         {
              DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
              format = LocalDateTime.now().minusDays(Long.parseLong("1")).format(ofPattern);
             
         } catch (Exception e) {
         
         }
         
       return format;
     }
    
      public String doParseDesc(String transType, String src, String dest, String customerDesc, String date, String amount, String delims) {
      
        return transType+delims+src+delims+dest+delims+customerDesc+delims+date+delims+amount;
    
    }
      
    public int doDRorCR(String flag) {
        
    return (flag.trim().toUpperCase().equals("DR"))?0:1;
    }
     
    
   public long doIsoAmount(double dx)
   {
       long amt = 0;
       try 
       {
           amt = (dx > 0)?Double.valueOf((dx * 100)).longValue():0l;
       } 
       catch (Exception e) {
       
           e.printStackTrace();
       
       }
   
   return amt;
   }
    
    public  String padZero(int i)
    {
      String intzero = "";
      for (int j = 0; j < i; j++) {
        intzero = intzero + "0";
      }
      return intzero;
    }
    
    
    public  String padZero(int i, String sequenceNo)
    {
      String intzero = "";
      i = (i - sequenceNo.trim().length());
      for (int j = 0; j < i; j++) {
        intzero = intzero + "0";
      }
      return intzero+sequenceNo;
    }
    
    
    public String MSISDNStripper(String msisdn, String partnerId) {
      
        return partnerId+msisdn;//+schemeShortCode; // return msisdn.substring(4)+schemeShortCode;
    }
    
    public String MSISDNStripper(String msisdn) {
       
        return msisdn.substring(1);//+schemeShortCode; // return msisdn.substring(4)+schemeShortCode;
    }
    
    
    public boolean isValidAmount(String value) {
        
        boolean valid = false;
        try 
        {
            if(Double.parseDouble(value.trim().replaceAll(",", "")) > 0.0)
            {
                 valid = true;
            }
            
        } 
        catch (Exception e) {
         
            e.printStackTrace();
        
        }
     return valid;
    }
    
    public boolean isValidAmount(String value, boolean canbyZero ,int dp) {
        
        boolean valid = false;
        try 
        {
            if(!canbyZero)
            {
                    if(Double.parseDouble(value.trim()) > 0.0)
                    {
                         valid = isValidAmount(value.trim(), dp);
                    }
            }
            else
            {
                 Double.parseDouble(value.trim());
                 
                 valid = isValidAmount(value.trim(), dp);
               
                
            }
            
        } 
        catch (NumberFormatException e) {
         
            e.printStackTrace();
        
        }
        catch (Exception e) {
         
            e.printStackTrace();
        
        }
     return valid;
    }
    
    public boolean doValidateLDT(String dateStr) {
        
        LocalDateTime toLDT = null;
        String fromPad = " 00:00:00";
        String toPad = " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        boolean valid = false;
        try 
        {
          
                toLDT =  LocalDateTime.parse(dateStr, formatter);
                valid = true;
            
        } 
        catch (Exception e) {
        
            //e.printStackTrace();
        }
       
        return valid;
    }
    
     
     public  LocalDateTime strToLDT(String dateStr) {
        
        LocalDateTime toLDT = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try 
        {
                if(dateStr !=null && !dateStr.equals("N/A") && !dateStr.equals("NA"))
                {
                     toLDT =  LocalDateTime.parse(dateStr, formatter);
                }
               
               
         
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       
        return toLDT;
    }
     
    public boolean isValidXAuth(String passed, String ourValue) {
        
        if(passed == null || ourValue == null)
        {
            return false;
        }
        
        return passed.trim().equalsIgnoreCase(ourValue.trim());
    }
    
    public boolean doCompare(String passed, String ourValue) {
        
        if(passed == null || ourValue == null)
        {
            return false;
        }
        
        return passed.trim().equals(ourValue.trim());
    }
    
     
    public  LocalDateTime strToLDT(String dateStr, String ext) {
        
        LocalDateTime toLDT = null;
        String fromPad = " 00:00:00";
        String toPad = " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try 
        {
            
                if(ext !=null && ext.trim().equals("F"))
                {
                    dateStr = dateStr+fromPad;
                }
                else if(ext !=null && ext.trim().equals("T"))
                {
                    dateStr = dateStr+toPad;
                }
          
                toLDT =  LocalDateTime.parse(dateStr, formatter);
               
         
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       
        return toLDT;
    }
    
     public  LocalDateTime providusStrToLDT(String dateStr) {
        
        LocalDateTime toLDT = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy h:mm:ss a"); //.ofPattern("yyyy-MM-dd HH:mm:ss");

        try 
        {
          
                toLDT =  LocalDateTime.parse(dateStr, formatter);
               
         
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       
        return toLDT;
    }
    
    public  boolean isValidAmount(String amount, int decimalPlaces) {
        
        boolean valid = false;
        
        if(amount !=null && amount.contains(".")) // && !amount.contains(",")
        {
           // System.out.println("valid = " + amount.indexOf(".")+1);
            if( amount.substring(amount.indexOf(".")+1).length() == decimalPlaces)
            {
                Double.parseDouble(amount.trim().replace(",", ""));
                valid = true;
            }
        }
        else  if(amount !=null && !amount.contains(".") && decimalPlaces == 0)
        {
                valid = true;    
        }
        
     return valid;
    }
    
    public boolean isValid(String value) {
        //LOGGER.info("-- is valid -value- "+value);
        return (value == null || value.trim().length() ==0)?false:true;
    }
    
    
    public boolean isValidWithLen(String value, int len) {
        
        return (value == null || value.trim().length() !=len)?false:true;
    }
    
    public boolean isValidWithLenAndChars(String value, int len, boolean isChars) {
        
        try 
        {
              if(isChars)
              {
                 
                 return  (value != null)? value.matches("[a-zA-Z]*"):false;
                //  return false;
              }
              else
              {
                  Long.parseLong(value);
                  return true;
              }
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
       //return false;
    }
     
    public boolean isWithinValidLen(String value, int max) {
        
        return (value == null || value.trim().equals("") || value.trim().length() > max)?false:true;
    }
    
    public boolean isWithinValidLen(int max,int min,  int passed) {
        
        return (passed > max || min > passed)?false:true;
    }
    
    public boolean doValidateMSISDN(String msisdn) {
        
        boolean valid = false;
        try 
        {
            if(msisdn !=null && msisdn.length() > 5 && msisdn.startsWith("+234") && msisdn.substring(4, 5).equals("0")) msisdn= "+234"+msisdn.substring(5);
            if(msisdn !=null && ((msisdn.trim().startsWith("+") && msisdn.trim().length() == 14) || !msisdn.trim().startsWith("+") && msisdn.trim().length() == 13))
            {
                valid = true;
            }
            
        } catch (Exception e) {
        }
    
      return valid;
    }
    
    public String doFormatMSISDN(String msisdn) {
        
        String valid = "";
        try 
        {
            if(msisdn !=null && msisdn.length() > 5 && msisdn.startsWith("+234") && msisdn.substring(4, 5).equals("0")) msisdn= "+234"+msisdn.substring(5);
            if(msisdn !=null && ((msisdn.trim().startsWith("+") && msisdn.trim().length() == 14) || !msisdn.trim().startsWith("+") && msisdn.trim().length() == 13))
            {
                msisdn = msisdn;
            }
            
        } catch (Exception e) {
        }
    
      return msisdn;
    }
   
    
    public  LocalDateTime toLDT(String option) {
        
        LocalDateTime toLDT = null;
        //String fromPad = "T00:00:00";
        //String toPad = "T59:59:59";
        try 
        {
            
            LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            
            //System.out.println("toPad = " + option);
            switch(option)
            {
                case "F":
                toLDT =  LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
                break;
                case "T":
                toLDT =  LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX) ; // LocalDate.now().atTime(LocalTime.MAX);
                break;
                default:
                toLDT = null;//  LocalDateTime.parse("1900-01-21"+fromPad);
               
                break;
            }
           
            
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       
        return toLDT;
    }
  /*  
  public boolean isValidMsisdn(String msisdn) {
        boolean valid = false;
        try 
        {
            
            PhoneNumberUtil nosUtil = PhoneNumberUtil.getInstance();
            
            Phonenumber.PhoneNumber nos = nosUtil.parse(msisdn==null?"NA":msisdn.trim(), Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN.name());
            
           valid = nosUtil.isValidNumber(nos);
            
            
        } catch (Exception e) {
        
            
            log.error(" -- Exception in isValidMsisdn-- ",e);
            
        }
       return valid; 
    }
  */
  public boolean isValidPhoneNo(String msisdn) {
        
        boolean isvalid = false;
        try 
        {
            if(msisdn == null|| msisdn.isEmpty() || !msisdn.startsWith("0"))
            {
               return false;
            }
            else
            {
            String regex = "^\\d{11}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(msisdn);
            isvalid = matcher.matches(); // returns true if pattern matches, else returns false
            }
            
        } catch (Exception e) {
        }
     return isvalid; 
    }
  
  public boolean isValidFSIPhoneNo(String msisdn) {
        
        boolean isvalid = false;
        try 
        {
            if(msisdn == null|| msisdn.isEmpty() )//|| !msisdn.startsWith("234")
            {
               return false;
            }
            else
            {
            String regex = "^\\d{13}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(msisdn);
            isvalid = matcher.matches(); // returns true if pattern matches, else returns false
            }
            
        } catch (Exception e) {
        }
     return isvalid; 
    }
  
  /*
   public boolean isValidMsisdn(String msisdn) {
        boolean valid = false;
        try 
        {
            
            PhoneNumberUtil nosUtil = PhoneNumberUtil.getInstance();
            
            Phonenumber.PhoneNumber nos = nosUtil.parse(msisdn, Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN.name());
            
           valid = nosUtil.isValidNumber(nos);
            
            
        } catch (Exception e) {
        
        }
       return valid; 
    }
    */
  
    public LocalDateTime doCurrentMillisToLDT(long currentTimeMillis) {
            LocalDateTime date = null;
            try 
            {
                
                   date =  LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTimeMillis), ZoneId.systemDefault());
                
            } 
            catch (Exception e) {
                
                e.printStackTrace();
            
            }
      return  date;
    }
    
    
    public LocalDateTime doFirstDayOfYearLDT()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now().withDayOfYear(1);
        
       // LocalDateTime.now().minusMonths(Long.valueOf("1"))
        
       return toLDT("F",formatter.format(now));
    }
    
    public boolean  isValidRequestID(String passed, int len) {
        //System.out.println("len = " + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        boolean isValid = false;
        try 
        {
            if(passed == null || passed.isEmpty() || passed.trim().length() !=len)
            {
                isValid = false;
            }
            if(passed.substring(0, 8).equals(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)))
            {
                isValid = true;
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
        }
    
        return isValid;
    }
    
    public LocalDateTime toLDT(Instant inst) {
        
        return LocalDateTime.ofInstant(inst, ZoneOffset.systemDefault());
    }
    
    public LocalDateTime toLDT(Instant inst, Long hrs) {
        
        return LocalDateTime.ofInstant(inst, ZoneOffset.systemDefault()).plusHours(hrs);
    }
    
    
    
    
     public boolean isValidPhoneNumber(String mobile) {
      
        boolean valid = false;
        try 
        {
            if(mobile == null) mobile = "";
            Pattern pattern = Pattern.compile(MOBILE_E_123_REGEX);

            Matcher matcher = pattern.matcher(mobile);
            //System.out.println(mobile +" : "+ matcher.matches());
            
            valid =  matcher.matches();

        } 
        catch (Exception e) {
        
        
            e.printStackTrace();
        
        }
   
     return valid;
    }
     
    public boolean isValidGender(String passed) {
        
        
       return Arrays.asList(GENDER).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
     public boolean isValidFLWBillPaymentFrequency(String passed) {
        
        
       return Arrays.asList(FLW_BILLPAYMENT_FREQ).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
   
    public boolean isValidSterlingAccountRestrictionID(String passed) {
        
        
       return Arrays.asList(STERLING_RESTRICTION_CODE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
     
    
     
    public boolean isValidEmail(String email) {
      
        boolean valid = false;
        try 
        {
            if(email == null) email = "";
            Pattern pattern = Pattern.compile(EMAIL_REGEX);

            Matcher matcher = pattern.matcher(email.trim());
            //System.out.println(email +" : "+ matcher.matches());
            
            valid =  matcher.matches();

        } 
        catch (Exception e) {
        
        
            e.printStackTrace();
        
        }
   
     return valid;
    }
   
   
    
    public String toDefault(Date value, String defaultValue) {
        
        return (value == null)?defaultValue:new SimpleDateFormat("yyyy-MMM-dd").format(value);
    }
  
  
    public boolean isValidLen(String value, int len, String digsOrAlpha) {
        
        boolean valid = false;
        
        try 
        {
            
            if(value !=null && value.length() == len)
            {
                if("D".equals(digsOrAlpha))
                {
                  Long.parseLong(value.trim());
                  valid = true;
                }
                else if("A".equals(digsOrAlpha))
                {
                    valid = true;
                }
            }
            
        } 
        catch (Exception e) {
       
           e.printStackTrace();
           
        }
        
        return valid;
    }
    
    
   
    
     public long doNumberOfMonthsBTW(Date fromDate, Date toDate) {
        long numberOfDays = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try 
        { 
            numberOfDays = ChronoUnit.MONTHS.between( YearMonth.from(LocalDate.parse(sdf.format(fromDate))), YearMonth.from(LocalDate.parse(sdf.format(toDate))));
            
        } catch (Exception e) {
        
        }
       return numberOfDays; 
    }
    
    
    public boolean isApprovalValid(Date fromDate, Date toDate, int noOfMonths) {
        long numberOfMonths = 0;
        boolean isValid = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try 
        { 
            numberOfMonths = ChronoUnit.MONTHS.between( YearMonth.from(LocalDate.parse(sdf.format(fromDate))), YearMonth.from(LocalDate.parse(sdf.format(toDate))));
            
           isValid = numberOfMonths < noOfMonths; 
            
            
        } catch (Exception e) {
        
        }
       return isValid ; 
    }
    
    
    public  boolean isValidDomainName(String domainName) {
        boolean valid = false;
        try 
        {
                 Pattern pattern = Pattern.compile(DOMAIN_NAME_PATTERN);
            	return pattern.matcher(domainName).find();
            
        } catch (Exception e) {
        
        
        }
       return valid;
    }
 
    public boolean isValidInt(String value) {
        
        boolean valid = false;
        try 
        {
            Integer.parseInt(value.trim());
            valid = true;
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
     return valid;
    }
    
    
    public Date toDate(String passedDate, String option) {
        if(option == null) option = "";
        Date parse = null;
        String fromPad = " 00:00:00";
        String toPad = " 59:59:59";
        try 
        {
            //System.out.println("toPad = " + option);
            switch(option)
            {
                case "F":
                parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(passedDate+fromPad);
                break;
                case "T":
                parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(passedDate+toPad);
                break;
                default:
                parse = new SimpleDateFormat("yyyy-MM-dd").parse(passedDate);
                break;
            }
           
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
        return parse;
    }
   
    public static LocalDateTime toLDT(String option, String dateStr) {
        
        LocalDateTime toLDT = null;
        String fromPad = " 00:00:00";
        String toPad = " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try 
        {
            
            //System.out.println("toPad = " + option);
            switch(option)
            {
                
                
                case "F":
                toLDT =  LocalDateTime.parse(dateStr+fromPad, formatter);
                break;
                case "T":
                toLDT =  LocalDateTime.parse(dateStr+toPad, formatter);
                break;
                default:
                toLDT = null;//  LocalDateTime.parse("1900-01-21"+fromPad);
               
                break;
            }
           
            
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
            log.error("Exception @ LocalDateTime toLDT : ", e);
            
            
        }
       
        return toLDT;
    }
    
    public static LocalDateTime doFullDatetoLDT(String option, String dateStr) {
        
        LocalDateTime toLDT = null;
        String fromPad = " 00:00:00";
        String toPad = " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try 
        {
            
            //System.out.println("toPad = " + option);
            switch(option)
            {
                
                
                case "F":
                toLDT =  LocalDateTime.parse(dateStr, formatter);
                break;
                case "T":
                toLDT =  LocalDateTime.parse(dateStr, formatter);
                break;
                default:
                toLDT = null;//  LocalDateTime.parse("1900-01-21"+fromPad);
               
                break;
            }
           
            
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
           // LOGGER.error("Exception @ LocalDateTime toLDT : ", e);
            
            
        }
       
        return toLDT;
    }
    
    public static LocalDateTime fromFullDateStringToLDT(String option, String dateStr) {
        
        LocalDateTime toLDT = null;
        String fromPad = " 00:00:00";
        String toPad = " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try 
        {
          
            //System.out.println("toPad = " + option);
            switch(option)
            {
                
                
                case "F":
                toLDT =  LocalDateTime.parse(dateStr, formatter);
                break;
                case "T":
                toLDT =  LocalDateTime.parse(dateStr, formatter);
                break;
                default:
                toLDT = null;//  LocalDateTime.parse("1900-01-21"+fromPad);
               
                break;
            }
           
            
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
            
            
        }
       
        return toLDT;
    }
    
    
    public LocalDateTime doFirstDayOfWeekStrLDT()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        //System.out.println(now.with(fieldISO, 1)); // 2015-02-09 (Monday)
            
       return toLDT("F",formatter.format(now.with(fieldISO, 1)));
    }
    
    
    public LocalDateTime doLastDayOfWeekLDT()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
       // System.out.println(now.with(fieldISO, 7)); // 2015-02-09 (Monday)
       
       
            
       return  toLDT("T",formatter.format(now.with(fieldISO, 7)));
    }
    
    
     public String rollBackDateByMonths(int noOfMonths) {
        String date ="";
        try 
        {
            LocalDateTime minusMonths = LocalDateTime.now().minusMonths(noOfMonths);
            
           date = minusMonths.format(DateTimeFormatter.ISO_DATE);
            
        } catch (Exception e) {
       
        }
   
        return date;
     }
     
     public String doLDTTodayAsStr() {
        String date ="";
        try 
        {
            LocalDateTime minusMonths = LocalDateTime.now();
            
            date = minusMonths.format(DateTimeFormatter.ISO_DATE);
            
        } catch (Exception e) {
       
        }
   
        return date;
     }
     
     public boolean doValidateFLWDT(String dateStr) {
        
        LocalDate toLDT = null;
        String fromPad = " 00:00:00";
        String toPad = " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        boolean valid = false;
        try 
        {
          
                toLDT =  LocalDate.parse(dateStr, formatter);
                valid = true;
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       
        return valid;
    }
     
     /*
      public LocalDateTime doStringWithTimeZoneToLDT(String dateStr ) {
        LocalDateTime date = null;
        try 
        {
            if(isValid(dateStr) && !"0000-NON-00".equals(dateStr))
            {
               date  =  DateParserUtils.parseDateTime(dateStr);
            }
            
            
        } catch (Exception e) {
             
            log.error("EXCEPTION doStringWithTimeZoneToLDT -- ",e);
        }
   
        return date;
     }
     
     */
      public  String playChannel(String channelId) {
       String resp = "";
        try 
        {
            switch(channelId)
            {
                case "1":
                resp = "WEB";
                break;
                case "2":
                resp = "MOBILE";
                break;
                case "3":
                resp = "USSD";
                break;
                default:
                 resp = "UNKNOWN";
                        
            }
        } 
        catch (Exception e) {
       
            e.printStackTrace();
        }
        
        return resp;
    }
    
    
     public  String playedBy(String playfor, String playfrom) {
       String resp = "";
        try 
        {
             if(playfor != null && playfrom != null)
             {
                 resp = (playfrom.trim().equals(playfor.trim()))?"SELF":"AGENT";
             }
        } 
        catch (Exception e) {
       
            e.printStackTrace();
        }
        
        return resp;
    }
    
    
    
    
    public static final String FREQUENCY [] = {"WEEKLY", "BI-WEEKLY", "MONTHLY", "QUARTERLY", "BI-YEARLY", "YEARLY"};
   
    
    public int toDefault(Integer value) {
        
        return (value == null )?0:value.intValue();
    }
   
     public  String strToLDT2AmPm(String dateStr) {
        
        LocalDateTime toLDT = null;
        String fromPad = " 00:00:00";
        String toPad = " 23:59:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try 
        {
            
          toLDT =  LocalDateTime.parse(dateStr, formatter);
             
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       
        return (toLDT !=null)?toLDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")):null;
    }
     
   public  String strToLDT2AmPm(LocalDateTime toLDTStr) {
      
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
        String resp = "";
        try 
        {
          
                resp = toLDTStr.format(formatter);
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       
        return resp;//(toLDT !=null)?toLDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a")):null;
    }
    
    
    public String[] toCountDownInfo(Date dt) {
        
        String[] resp  = new String[4];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        try 
        {
                System.out.println("---sdf = " + sdf.format(dt));
               
                String drawDate = sdf.format(dt);
                
                String[] split = drawDate.split("-");
               
                LocalDateTime toDateTime = LocalDateTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 12, 0, 0);

                LocalDateTime tempDateTime = LocalDateTime.now();//.from( fromDateTime );

                long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS );
                tempDateTime = tempDateTime.plusYears( years );

                long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS );
                tempDateTime = tempDateTime.plusMonths( months );

                long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS );
                tempDateTime = tempDateTime.plusDays( days );


                long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
                tempDateTime = tempDateTime.plusHours( hours );

                long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
                tempDateTime = tempDateTime.plusMinutes( minutes );

                long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS );
                
                
                 resp [0] = String.valueOf(days);
                 resp [1] = String.valueOf(hours);
                 resp [2] = String.valueOf(minutes);
                 resp [3] = String.valueOf(seconds);

                System.out.println( years + " years " + months + " months " +  days + " days " +hours + " hours " + minutes + " minutes " + seconds + " seconds.");

        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
       
      return resp;
    }
    
    
    public String[] toCountDownInfo(LocalDateTime dt) {
        
        String[] resp  = new String[4];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
        try 
        {
                System.out.println("---sdf = " + sdf.format(dt));
               
                String drawDate = sdf.format(dt);
                
                String[] split = drawDate.split("-");
               
                LocalDateTime toDateTime = LocalDateTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 12, 0, 0);

                LocalDateTime tempDateTime = LocalDateTime.now();//.from( fromDateTime );

                long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS );
                tempDateTime = tempDateTime.plusYears( years );

                long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS );
                tempDateTime = tempDateTime.plusMonths( months );

                long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS );
                tempDateTime = tempDateTime.plusDays( days );


                long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
                tempDateTime = tempDateTime.plusHours( hours );

                long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
                tempDateTime = tempDateTime.plusMinutes( minutes );

                long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS );
                
                
                 resp [0] = String.valueOf(days);
                 resp [1] = String.valueOf(hours);
                 resp [2] = String.valueOf(minutes);
                 resp [3] = String.valueOf(seconds);

                System.out.println( years + " years " + months + " months " +  days + " days " +hours + " hours " + minutes + " minutes " + seconds + " seconds.");

        } 
        catch (Exception e) {
        
            e.printStackTrace();
        
        }
       
      return resp;
    }
    
     public boolean isValidAmount(Double value) {
        
        boolean valid = false;
        try 
        {
            if(value > 0.9)
            {
                 valid = true;
            }
            
        } 
        catch (Exception e) {
         
            e.printStackTrace();
        
        }
     return valid;
    }
    
    
    /*
    public LocalDateTime toLDT(Instant inst) {
        
       
        
        return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.systemDefault());
    }
    */
   
   
    public Instant doDateToInstant(String passedDate, String option) {
        if(option == null) option = "";
        Date parse = null;
        String fromPad = " 00:00:00";
        String toPad = " 59:59:59";
        try 
        {
            //System.out.println("toPad = " + option);
            switch(option)
            {
                case "F":
                parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(passedDate+fromPad);
                break;
                case "T":
                parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(passedDate+toPad);
                break;
                default:
                parse = new SimpleDateFormat("yyyy-MM-dd").parse(passedDate);
                break;
            }
           
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
        return parse.toInstant();
    }
    
    public long doNumberOfDaysBTW( Date toDate) {
        Period numberOfDays = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try 
        { 
          
             numberOfDays = Period.between(LocalDate.parse(sdf.format(new Date())), LocalDate.parse(sdf.format(toDate)));
           
             
        } catch (Exception e) {
        
            e.printStackTrace();
        }
       return numberOfDays !=null?numberOfDays.getDays():0; 
    }
   
    
    public LocalDate doFirstDayOfWeek()
    {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        System.out.println(now.with(fieldISO, 1)); // 2015-02-09 (Monday)
            
       return now.with(fieldISO, 1);
    }
    
    public LocalDate doLastDayOfWeek()
    {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        System.out.println(now.with(fieldISO, 7)); // 2015-02-09 (Monday)
            
       return now.with(fieldISO, 7);
    }
    
    
    public String doFirstDayOfWeekStr()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        System.out.println(now.with(fieldISO, 1)); // 2015-02-09 (Monday)
            
       return formatter.format(now.with(fieldISO, 1));
    }
    
    public String doLastDayOfWeekStr()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.UK).dayOfWeek();
        System.out.println(now.with(fieldISO, 7)); // 2015-02-09 (Monday)
            
       return  formatter.format(now.with(fieldISO, 7));
    }
   
    
    
    public boolean isValidDateRange(String fromDate, String toDate) {
        boolean valid = false;
        try 
        {
            if(toDate != null && fromDate !=null)
            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                valid = formatter.parse(fromDate).before(formatter.parse(toDate));
            }
            
        } catch (Exception e) {
       
            e.printStackTrace();
        
        }
      return valid;  
    }
   
 
   public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
    return dateToConvert.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
   }
   
   public  boolean isStrongPIN(String ipAddress) {
        boolean valid = false;
        try 
        {
                 Pattern pattern = Pattern.compile(PIN_REGEX);
            	return pattern.matcher(ipAddress).matches();
            
        } catch (Exception e) {
        
        
        }
       return valid;
    }
   
   public  boolean isStrongPassword(String domainName) {
        boolean valid = false;
        try 
        {
                 Pattern pattern = Pattern.compile(PASSWORD_COMPLEXITY);
            	return pattern.matcher(domainName).matches();
            
        } catch (Exception e) {
        
        
        }
       return valid;
    }
   
    public  boolean isValidIP(String ipAddress) {
        boolean valid = false;
        try 
        {
                 Pattern pattern = Pattern.compile(IP_REGEX);
            	return pattern.matcher(ipAddress).matches();
            
        } catch (Exception e) {
        
        
        }
       return valid;
    }
   
    
    
    public boolean isValidFrequency(String passed) {
        
        
       return Arrays.asList(FREQUENCY).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
  
    
    public boolean isValidOTPMedium(String passed) {
        
        
       return Arrays.asList(FLW_OTP_MEDIUM).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
     public boolean isValidFLWBillerCategory(String passed) {
        
        
       return Arrays.asList(FLW_BILLER_CATEGORIES).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    
    public boolean isValidBroadcastTo(String passed) {
        
        
       return Arrays.asList(BROADCAST_TO).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    
    public boolean isValidTelco(String passed) {
        
        
       return Arrays.asList(TELCO).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    public boolean isValidTransType(String passed) {
        
        
       return Arrays.asList(TRANS_TYPE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
     public boolean isValidImageType(String passed) {
        
        
       return Arrays.asList(IMAGE_TYPE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
     public boolean isValidCardBlockType(String passed) {
        
        
       return Arrays.asList(BLOCK_TYPE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
   
    public boolean isValidFeeTransType(String passed) {
        
        
       return Arrays.asList(FEE_TRANS_TYPE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    
    
    public boolean isValidNotificationOption(String passed) {
        
        
       return Arrays.asList(NOTIFICATION_OPTIONS).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
     public boolean isDestBankCodeRequired(String passed) {
        
        
       return Arrays.asList(CURRENCY_REQUIRING_DEST_BRANCH_CODE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
     /*
     is valis flutterwave transfer currency
     */
    public boolean isValidFWFetchTransferCurrency(String passed) {
        
        
       return Arrays.asList(FW_FETCH_TRANS_CURRENCY).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    public boolean isValidFWTransferCurrency(String passed) {
        
        
       return Arrays.asList(FW_VALID_TRANS_CURRENCY).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    
    
     /*
     is valis flutterwave transfer rate currency
     */
    public boolean isValidFWTransferRateCurrency(String passed) {
        
        
       return Arrays.asList(FW_VALID_TRANS_RATE_CURRENCY).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
     public boolean isValidFWPaymentType(String passed) {
        
        
       return Arrays.asList(FW_VALID_PAYMENT_TYPE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
     
     
     public boolean isValidFWSplitType(String passed) {
        
        
       return Arrays.asList(FW_SPLIT_TYPE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    
    
    
     /*
     is valis flutterwave transfer rate currency
     */
    public boolean isValidFWResourceStatus(String passed) {
      
       return Arrays.asList(FW_VALID_RESOURCE_STATUS).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
     public boolean isValidFWChargebackStatus(String passed) {
      
       return Arrays.asList(FW_VALID_CHARGEBACK_STATUS).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    
    
     public boolean isValidFWTransferStatus(String passed) {
        
        
       return Arrays.asList(FW_VALID_TRANS_STATUS).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    
     
    public boolean isValidBillPayOption(String passed) {
        
        
       return Arrays.asList(BILLPAY_OPTIONS).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
     
    public boolean isValidWalletTierOption(String passed) {
        
        
       return Arrays.asList(WALLET_TIER_OPTIONS).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
    
    public boolean isValidFWPaymentPlanInterval(String passed) {
        
        
      return Arrays.asList(FW_PAYMENT_PLAN_INTERVAL).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
     
    public boolean isValidKYCFileType(String passed) {
        
        
       return Arrays.asList(KYC_FILE_TYPE).stream().filter(a ->(passed !=null && a.equalsIgnoreCase(passed))).findFirst().isPresent();
    }
     
    
    
     public int toRolesPrivilegeStatus(String controlCode) {
        int status = -1;
        try 
        {
            //System.out.println("toPad = " + option);
            if(controlCode ==null) controlCode = "";
            switch(controlCode.toUpperCase())
            {
                case "SYS-ADMINISTRATOR":
                status = 1;
                break;
                case "ADMINISTRATOR":
                status = 1;
                break;
                case "SUPER ADMINISTRATOR":
                status = 1;
                break;
                case "SYS ADMINISTRATOR":
                status = 1;
                break;
                case "SYS-AUTHORIZER":
                status = 2;
                break;
                case "AUTHORIZER":
                status = 2;
                break;
                case "PARTNER-ADMINISTRATOR":
                status = 1;
                break;
                case "PARTNER-AUTHORIZER":
                status = 2;
                break;
                case "SYS AUTHORIZER":
                status = 2;
                break;
                
                case "SUPER-ADMIN":
                status = 0;
                break;
                case "REPORT-VIEWER":
                status = 0;
                break;
                
                default:
                status = status;
                break;
            }
           
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
        
        return status;
    }
     
    public int getRandomInt(int min, int max)
    {
        return (new Random().nextInt(max-min)+min);
    }
    
    public double doRandomDouble() {
       
        return  BigDecimal.valueOf(Math.random() * 75).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
    }
    
    public double doRandomDouble(double multiple) {
       
        return  BigDecimal.valueOf(Math.random() * multiple).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
    }
    
    
    public String toDateFormat(int month) {
        String fmt = "";
        try 
        {
          
            LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), month, 1);//.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLL yyyy"); // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            fmt = localDate.format(formatter);
            
        } catch (Exception e) {
        
        }
        
     return fmt;
    }
    
    public LocalDateTime doUTCtoLDT(Long utcTimestamp) {
        
        LocalDateTime localDateTime = null;
        try 
        {
            Instant instant = Instant.ofEpochMilli(utcTimestamp);
        
        // Define the local time zone (e.g., America/New_York)
            ZoneId localZoneId = ZoneId.of("Africa/Lagos");
        
        // Convert Instant to ZonedDateTime in the local time zone
           ZonedDateTime zonedDateTime = instant.atZone(localZoneId);
        
        // Get LocalDateTime from ZonedDateTime
           localDateTime = zonedDateTime.toLocalDateTime();
            
        } catch (Exception e) {
        
            log.info("--Exception-- UTCToLDT ",e);
        }
        
      return localDateTime;
    }
    
    
    public String toDateFormatAsMMYYYY(int month) {
        String fmt = "";
        try 
        {
          
            LocalDate localDate = LocalDate.of(LocalDate.now().getYear(), month, 1);//.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy"); // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            fmt = localDate.format(formatter);
            
        } catch (Exception e) {
        
        }
        
     return fmt;
    }
    
     public String toDateFormat(long yr, int month) {
        String fmt = "";
        try 
        {
          
            LocalDate localDate = LocalDate.of(LocalDate.now().minusYears(yr).getYear(), month, 1);//.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLL yyyy"); // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            fmt = localDate.format(formatter);
            
        } catch (Exception e) {
        
        }
        
     return fmt;
    }
     
    public String toDateFormatAsMMYYYY(long yr, int month) {
        String fmt = "";
        try 
        {
          
            LocalDate localDate = LocalDate.of(LocalDate.now().minusYears(yr).getYear(), month, 1);//.now();//For reference
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy"); // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
            fmt = localDate.format(formatter);
            
        } catch (Exception e) {
        
        }
        
     return fmt;
    }
    
    
    public String doTrxTypePrefix(String id) {
        String type = "";
        try 
        {
            switch(id)
            {
                case "#-1":
                type =  "W2W";
                break;
                case "#-2":
                type =  "WTP";
                break;
                case "#-3":
                type =  "BTW";
                break;
                case "#-4":
                type =  "DOW";//, WTP, BTW, DOW, WTB, WTPRV, AIRTIME, POS_DR, POS_CR, BILL_PAY, COMM
                break;
                case "#-5":
                type =  "WTB";
                break;
                case "#-6":
                type =  "WTPROVIDUS"; //WTPRV
                break;
                case "#-7":
                type =  "AIRTIME";
                break;
                case "#-8":
                type =  "POS_DR";
                break;
                case "#-9":
                type =  "POS_CR";
                break;
                case "#-10":
                type =  "BILL_PAY";
                break;
                case "#-11":
                type =  "COMM";
                break;
                case "#-12":
                type =  "AIRTIME~RVSL";
                break;
                case "#-13":
                type =  "WALLET_TOPUP~RVSL"; 
                break;
                
                case "#-14":
                type =  "DEBIT_WALLET~RVSL"; 
                break;
                
                case "#-15":
                type =  "WALLET_WALLET~RVSL"; 
                break;
                
                case "#-16":
                type =  "WTPROVIDUS~RVSL"; 
                break;
                
                case "#-17":
                type =  "WALLET_TOPUP_3P~RVSL"; 
                break;
                
                case "#-18":
                type =  "WTB~RVSL"; 
                break;
               
                
                default:
                type =  "NA";
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return type; 
    }
    
    public boolean isValidationBillerRequired(String categoryName) {
        boolean validate = false;
        try 
        {
            switch(categoryName)
            {
                case "Cable TV":
                validate =  true;
                break;
                case "Electricity":
                validate = true;
                break;
                case "Govt Tax":
                validate = true;
                break;
                case "Internet Services":
                validate = true;
                break;
                case "Toll Service":
                validate = true;
                break;
                case "DATA":
                validate = false;
                break;
               
                default:
                validate = false;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return validate; 
    }
    
    
    
    public int doTelcoData(String code) {
        int telcoId = 0;
        try 
        {
            switch(code.toUpperCase().trim())
            {
                case "MTN":
                telcoId =  34;
                break;
                case "GLO":
                telcoId =  35;
                break;
                case "AIRTEL":
                telcoId =  36;
                break;
                case "ETISALAT":
                telcoId =  37;
                break;
               
                default:
                telcoId = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return telcoId; 
    }
    
    
    public String doTelcoByBillerId(int billerId) {
        String telcoId = "";
        try 
        {
            switch(billerId)
            {
                case 34:
                telcoId = "MTN";
                break;
                case 35:
                telcoId =  "GLO";
                break;
                case 36:
                telcoId =  "AIRTEL";
                break;
                case 37:
                telcoId =  "ETISALAT";
                break;
               
                default:
                telcoId = "NA";
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return telcoId; 
    }
    
    public int doElectricityId(String code) {
        int billerid = 0;
        try 
        {
            switch(code.trim())
            {
                case "phcnenu":
                billerid =  8;
                break;
                case "phcnppenu":
                billerid =  9;
                break;
                case "phcneko":
                billerid =  10;
                break;
                case "phcnppeko":
                billerid =  11;
                break;
                case "phcnjos":
                billerid =  14;
                break;
                case "phcnppjos":
                billerid =  15;
                break;
                case "phcnkan":
                billerid =  16;
                break;
                case "phcnppkan":
                billerid =  17;
                break;
                case "phcnabj":
                billerid =  18;
                break;
                case "phcnppabj":
                billerid =  19;
                break;
                case "phcnibd":
                billerid =  22;
                break;
                case "phcnppibd":
                billerid =  23;
                break;
                case "phcnkad":
                billerid =  24;
                break;
                case "phcnppkad":
                billerid =  25;
                break;
                case "phcnphe":
                billerid =  26;
                break;
                case "phcnppphe":
                billerid =  27;
                break;
                default:
                billerid = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return billerid; 
    }
    
    public int doTelcoVTU(String code) {
        int telcoId = 0;
        try 
        {
            switch(code.toUpperCase().trim())
            {
                case "MTN":
                telcoId =  30;
                break;
                case "GLO":
                telcoId =  31;
                break;
                case "AIRTEL":
                telcoId =  32;
                break;
                case "ETISALAT":
                telcoId =  33;
                break;
                case "9MOBILE":
                telcoId =  33;
                break;
               
                default:
                telcoId = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return telcoId; 
    }
    
    
    
    public int doToll(String code) {
        int telcoId = 0;
        try 
        {
            switch(code.toUpperCase().trim())
            {
                case "LCC":
                telcoId =  2;
                break;
                default:
                telcoId = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return telcoId; 
    }
    
    public int doCategoryId(String code) {
        int serviceId = 0;
        try 
        {
            switch(code.trim())
            {
                case "Cable TV":
                serviceId =  1;
                break;
                case "Electricity":
                serviceId =  2;
                break;
                case "Govt Tax":
                serviceId =  3;
                break;
                case "Internet Services":
                serviceId =  4;
                break;
                case "Toll Service":
                serviceId =  5;
                break;
                case "DATA": //   VTU is handled direct
                serviceId =  7;
                break;
                
                case "VTU":   // though handled direct
                serviceId = 8;
                break;
                
                default:
                serviceId = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return serviceId; 
    }
    
    public int doBillerIdMapping(int catId, String serviceCode) {
        int serviceId = 0;
        try 
        {
            switch(catId)
            {
                case 1:
                serviceId =  doPayTV(serviceCode);
                break;
                case 2:
                serviceId =  doElectricityId(serviceCode);
                break;
                //case 3:// "Govt Tax":
                //serviceId =  3;
               // break;
                case 4:
                serviceId =  doInternetService(serviceCode);
                break;
                case 5://"Toll Service":
                serviceId =  doToll(serviceCode);
                break;
                case 7://"Toll Service":
                serviceId = doTelcoData(serviceCode) ;
                break;
                default:
                serviceId = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return serviceId; 
    }
    
    
    public  int  doInternetService(String code) {
        int telcoId = 0;
        try 
        {
            switch(code.toUpperCase().trim())
            {
                case "SWIFT":
                telcoId =  28;
                break;
                case "SMILE":
                telcoId =  3;
                break;
                case "SPECTRANET":
                telcoId =  29;
                break;
                default:
                telcoId = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return telcoId; 
    }
    
    
    public  int  doBillerCategory(int billerId) {
        int option = 0;
        try 
        {
            switch(billerId)
            {
                
                case 5://"DSTV":
                option =  1;
                break;
                case 4://"GOTV":
                option =  1;
                break;
                case 6://"STARTIMES":
                option =  1;
                break;
               
                case 8://"phcnenu":
                option =  2;
                break;
                case 9://"phcnppenu":
                option =  2;
                break;
                case 10://"phcneko":
                option =  2;
                break;
                case 11://"phcnppeko":
                option =  2;
                break;
                case 14://"phcnjos":
                option =  2;
                break;
                case 15://"phcnppjos":
                option =  2;
                break;
                case 16://"phcnkan":
                option =  2;
                break;
                case 17://"phcnppkan":
                option =  2;
                break;
                case 18://"phcnabj":
                option =  2;
                break;
                case 19://"phcnppabj":
                option =  2;
                break;
                case 22://"phcnibd":
                option =  2;
                break;
                case  23://"phcnppibd":
                option =  2;
                break;
                case 24://"phcnkad":
                option =  2;
                break;
                case 25://"phcnppkad":
                option =  2;
                break;
                case 26://"phcnphe":
                option =  2;
                break;
                case 27:// "phcnppphe":
                option =  2;
                break;
                
                //3 reserved
                
                
                case 28: //SWIFT
                option =  4;
                break;
                case 3: //SMILE
                option =  4;
                break;
                case 29: //SPECTRANET
                option =  4;
                break;
                
                case 2://"LCC":
                option =  5;
                break;
                
                //6 reserved
                
                case 34:// "MTN":
                option =  7;
                break;
                case 35://"GLO":
                option =  7;
                break;
                case 36:// "AIRTEL":
                option =  7;
                break;
                case 37://"ETISALAT":
                option =  7;
                break;
                case 30://"MTN":
                option =  8;
                break;
                case 31://"GLO":
                option =  8;
                break;
                case 32:// "AIRTEL":
                option =  8;
                break;
                case 33: //"ETISALAT":
                option =  8;
                break;
                
                default:
                option = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return option; 
    }
    
    
    public int doPayTV(String code) {
        int telcoId = 0;
        try 
        {
            switch(code.toUpperCase())
            {
                case "DSTV":
                telcoId =  5;
                break;
                case "GOTV":
                telcoId =  4;
                break;
                case "STARTIMES":
                telcoId =  6;
                break;
                default:
                telcoId = 0;
                break;
                    
            }
               
            
        } catch (Exception e) {
        
        }
     return telcoId; 
    }
    
    
    public String doBasicAuth(String basic_user, String basic_password) {
        
        try 
        {
             String credz =  basic_user+":"+basic_password;
             return  "Basic "+Base64.getEncoder().encodeToString(credz.getBytes("UTF-8"));
            
        } catch (Exception e) {
        
            return null;
        }
        
    }
    
    
   public String doSanitizeMSISDN(String passed) {
        
        String resp = "";
        try 
        {
             //System.out.println("resp ***= " + passed+"  passed  -- "+passed.length());
             if(passed !=null && passed.trim().length() == 15 && passed.startsWith("+"))
             {
                 resp =  passed.substring(0, 4)+""+passed.substring(5);
                 
             }
             else if(passed !=null  && passed.trim().startsWith("+") && passed.trim().length() == 14)
             {
                 resp =  passed;// "+"+passed.substring(0, 3)+""+passed.substring();
                
             }
             else if(passed !=null  && !passed.trim().startsWith("+") && passed.trim().length() == 13)
             {
                 resp =   "+"+passed;
                
             }
             else if(passed !=null && passed.trim().startsWith("0") && passed.trim().length() == 11)
             {
                 resp =  "+234"+passed.substring(1);
                
             }
             else if(passed !=null && !passed.trim().startsWith("0") && passed.trim().length() == 10)
             {
                 resp =  "+234"+passed;
                
             }
             else
             {
                 resp = passed;
             }
            
        } catch (Exception e) {
        
        }
    
        return resp; 
    }
    
    
    public String toKudaNotificationType(String passed) {
        String resp = "";
        try 
        {
           if(passed ==null) passed = "";
           switch(passed.trim().toLowerCase())
           {
               case "credit":
               resp = "CR";
               break;
               
               case "debit":
               resp = "DR";
               break;
               
               case "revesal":
               resp = "CR";
               break;
               
               default:
               resp = "not-programmed";
               break;
               
               
           }
            
        } 
        catch (Exception e) {
        
           log.error(" --Exception @  toKudaNotificationType @ ",e);
        }
        
        return resp;
    }
    
    
    
    public static Document loadXMLFromString(String xml) throws Exception
    {
        System.out.println(" ## loadXMLFromString rcvd ## "+xml);
        if(xml !=null && xml.trim().startsWith("DEC"))  xml = xml.replaceAll("DEC", "");
        System.out.println(" ## loadXMLFromString to pass ## "+xml);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
    
    
    public  boolean isValidBase64(String base64Str) {
        boolean valid = false;
        try 
        {
                 Pattern pattern = Pattern.compile(BASE_64_REGEX);
            	return pattern.matcher(base64Str).matches();
            
        } catch (Exception e) {
        
        
        }
       return valid;
    }
  
    
}
