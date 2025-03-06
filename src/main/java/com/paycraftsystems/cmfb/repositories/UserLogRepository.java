/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.paycraftsystems.cmfb.repositories;





import com.paycraftsystems.cmfb.controller.ESEQHelper;
import com.paycraftsystems.cmfb.controller.SysDataController;
import com.paycraftsystems.cmfb.dto.UserProfileRequest;
import com.paycraftsystems.cmfb.dto.UserProfileRequestObj;
import com.paycraftsystems.cmfb.entities.RolesInfo;
import com.paycraftsystems.cmfb.entities.UserLog;
import com.paycraftsystems.cmfb.entities.UserProfile;
import com.paycraftsystems.cmfb.enumz.ResourceStatusEnum;
import com.paycraftsystems.exceptions.CMFBException;
import com.paycraftsystems.resources.ErrorCodes;
import io.agroal.api.AgroalDataSource;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.persistence.sessions.DatabaseSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.Date;
import static org.eclipse.microprofile.reactive.messaging.Message.LOGGER;

/**
 *
 * @author paycraftsystems-i
 */

@ApplicationScoped
@Slf4j
public class UserLogRepository implements  PanacheRepository<UserLog> {
    
    
    @Inject
    ESEQHelper eseqHelper;
    
    @Inject
    EntityManager em;
    
    @Inject 
    SysDataController sysdata;
    
    @Inject
    AgroalDataSource defaultDataSource;
    
    @Inject
    PaymentsBeneficiaryRepository paymentsBeneficiaryRepo;
    
    
    public UserLog doFindByUserName(String name)
    {
        return find("emailAddress", name).firstResult();
    }
    /*
    public SysData doFindByName(long id, String paramname, String paramValue)
    {
        return find("tid = ?1 and paramName = ?2 and paramValue = ?3",id,  paramname, paramValue).firstResult();
    }
    */
    public UserLog doFindByName(long id, String name)
    {
        return find("tid = ?1 and emailAddress = ?2 ",id,  name).firstResult();
    }
    
    public String doFindValueById(long tid)
    {
        
        UserLog findById = null;// UserLog.findById(tid);
       
        return (findById !=null)?findById.emailAddress:"NA";
    }
    
    public  UserLog doFindByUsername(String mobileNo)
    {
        UserLog obj = null;
        try 
        {
           
             obj =  find("mobileNo = ?1 ", mobileNo).firstResult();
            
        } catch (Exception e) {
        
           // e.printStackTrace();
            
           
           log.error(" doFindByUsername - ",e);
            
        }
        
      return obj;
    }
   
    public  UserLog doFindByEmailAddress(String emailAddress)
    {
        UserLog obj = null;
        try 
        {
           
             obj =  find("emailAddress = ?1 ", emailAddress).firstResult();
            
        } catch (Exception e) {
        
           // e.printStackTrace();
            
           
           log.error(" doFindByEmailAddress - ",e);
            
        }
        
      return obj;
    }
    
    public  UserLog doFindByEmailAddress(long pid, String emailAddress)
    {
        UserLog obj = null;
        try 
        {
           
             obj =  find("tid = ?1 and emailAddress = ?2 ",pid,  emailAddress).firstResult();
            
        } catch (Exception e) {
        
           // e.printStackTrace();
            
           
           log.error(" doFindByEmailAddress - ",e);
            
        }
        
      return obj;
    }
    
    public  UserLog loadByParams(String lastName, String phoneNo, String enail) throws Exception
    {
        UserLog obj = null;
        try 
        {
          
             obj =  find("lastName = ?1 and (mobileNo = ?2 or emailAddress = ?3) ", lastName, phoneNo, enail).firstResult();
            
        } catch (Exception e) {
        
           // e.printStackTrace();
            
           
           log.error(" loadByParams - ",e);
           
           throw new Exception(e);
            
        }
        
      return obj;
    }
    /*
    public  UserProfile loadByParams(String phoneNo, String enail) throws Exception
    {
        UserProfile obj = null;
        try 
        {
          
             obj =  find("(mobileNo = ?1 and emailAddress = ?2) ", phoneNo, enail).firstResult();
            
        } catch (Exception e) {
        
           // e.printStackTrace();
            
           
           log.error(" loadByParams - ",e);
           
           throw new Exception(e);
            
        }
        
      return obj;
    }
    */
    public  long doClearProfile(String phoneNo)
    {
        long delete =0;
        UserLog obj = null;
        try 
        {
          
             delete = delete("mobileNo ", phoneNo); //.firstResult();
            
        } catch (Exception e) {
        
           log.error(" loadByParams - ",e);
            
        }
        
      return delete;
    }
    
    
    public  UserLog loadByMobileAndAccountNo( String phoneNo, String accountNo)
    {
        System.out.println("accountNo = " + accountNo+" phoneNo : "+phoneNo);
        UserLog obj = null;
        try 
        {
            
             obj =  find(" mobileNo = ?1 and  accountNo = ?2  ", phoneNo, accountNo).firstResult();
            
        } catch (Exception e) {
        
             log.error(" loadByMobileAndAccountNo Exception - ",e);
        }
        
      return obj;
    }
    
    
     public  UserLog loadByMobileAndEmail( String phoneNo, String emailAddress)
    {
        System.out.println("accountNo = " + phoneNo+" emailAddress : "+emailAddress);
        UserLog obj = null;
        try 
        {
            
             obj =  find(" mobileNo = ?1 and  emailAddress = ?2  ", phoneNo, emailAddress).firstResult();
            
        } catch (Exception e) {
        
            //e.printStackTrace();
            
             log.error(" loadByMobileAndAccountNo Exception - ",e);
        }
        
      return obj;
    }
     
    public  List<UserLog> doFindByUsernameLikeAndStatus(String searchKey, long status) {
        List<UserLog>  obj = new ArrayList<>();
        try 
        {
            log.info(" --  searchKey -- "+searchKey+" --status -- "+status);
             if(status == 0)
             {
                 obj = find("userName like ?1  and userRole  = 23 ", searchKey+"%").list(); 
             }
             else
             {
                  obj = find("userName like ?1  and status = ?2 and userRole  = 23", searchKey+"%", status).list();
             }
            
        } 
        catch (Exception e) {
        
            e.printStackTrace();
            
            log.error(" Exception doFindByUsernameLikeAndStatus ",e);
        }
        
     return obj;
    }
     
     public  List<UserLog> doFindByDateRangeAndStatus(LocalDateTime fromDate,LocalDateTime toDate, long status) {
        List<UserLog>  obj = new ArrayList<>();
        try 
        {
            log.info(" ---fromDate  "+fromDate+" --  toDate -- "+toDate+" --status -- "+status);
             if(status == 0)
             {
                 obj = find("createdDate between ?1 and ?2  and userRole not in (22,23)", fromDate, toDate).list();
             }
             else
             {
                  obj = find("createdDate between ?1 and ?2  and status = ?3 and userRole not in (22,23)", fromDate, toDate, status).list();
             }
           
        } 
        catch (Exception e) {
        
            e.printStackTrace();
            
            log.error(" Exception doFindByDateRangeAndStatus ",e);
        }
        
     return obj;
    }
     
    public  UserLog loadByMobile( String phoneNo)
    {
        System.out.println("accountNo = " + phoneNo);
        UserLog obj = null;
        try 
        {
            
             obj =  find(" mobileNo = ?1 ", phoneNo).firstResult();
            
        } catch (Exception e) {
        
            //e.printStackTrace();
            
             log.error(" loadByMobileAndAccountNo Exception - ",e);
        }
        
      return obj;
    }
    
    public  UserLog loadByAccountNo(String accountNo)
    {
        UserLog obj = null;
        try 
        {
            
             obj =  find("accountNo = ?1  ",accountNo).firstResult();
            
        } catch (Exception e) {
        
            //e.printStackTrace();
            
             log.error(" loadByAccountNo Exception - ",e);
        }
        
      return obj;
    }
    /*
    //providerName, providerCode, servicesDesc, contactPerson, contactEmail, contactNumber, state_str, stateStr, countryStr, providerCategoryStr, 
    public PanacheQuery<UserLog> findByParams(long status, LocalDateTime from, LocalDateTime today){
       return UserLog.find("sts = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    
    public PanacheQuery<UserLog> findByParams(String status, LocalDateTime from, LocalDateTime today){
       return UserLog.find("status = ?1 and createdDate between ?2 and ?3 order by createdDate desc", status, from, today);
    }
    
    public PanacheQuery<UserLog> findByParams(String status,  LocalDateTime from, LocalDateTime today, String searchKey){
       return UserLog.find("status = ?1 and createdDate between ?2 and ?3 and (firstName like ?4 or  middleName like ?4 or lastName like ?4 or  mobileNo like ?4 or statusStr  like ?4 or  emailAddress like ?4 or deviceFingerprint like ?4 or userRoleStr like ?4  or full_name like ?4 or mobileNo like ?4) order by createdDate desc", status, from, today, searchKey+'%');
    }
    
    public PanacheQuery<UserLog> findByParams(LocalDateTime from, LocalDateTime today){
       return UserLog.find("createdDate between ?1 and ?2 order by createdDate desc",from, today);
    }

    public PanacheQuery<UserLog> findByParams(LocalDateTime from, LocalDateTime today, String searchKey){
       return UserLog.find("createdDate between ?1 and ?2 and (firstName like ?3 or  middleName like ?3 or lastName like ?3 or  mobileNo like ?3 or statusStr  like ?3 or  emailAddress like ?3 or deviceFingerprint like ?3 or userRoleStr like ?3  or full_name like ?3 or mobileNo like ?3) order by createdDate desc",from, today, searchKey+'%');
    }
    */
     public  String  doFindNameByTID(Long tid) {
        UserLog ulog = null;
        try 
        {
              ulog = find("tid", tid).firstResult();
        } 
        catch (Exception e) {
        
        }
       return ulog==null?"NA":ulog.emailAddress; 
    }
     
    public  UserLog  doFindByTID(Long tid) {
        UserLog ulog = null;
        try 
        {
              ulog = find("tid", tid).firstResult();
        } 
        catch (Exception e) {
        
        }
       return ulog; 
    }
    
    public  UserLog loadByParams(String lastName, String phoneNo) throws Exception
    {
        UserLog obj = null;
        try 
        {
          
             obj =  find("lastName = ?1 and mobileNo = ?2 ", lastName, phoneNo).firstResult();
            
        } catch (Exception e) {
        
           // e.printStackTrace();
           
           log.error(" loadByParams - ",e);
           
           throw new Exception(e);
            
        }
        
      return obj;
    }
    
    public  UserLog loadByTellerParams(String lastName, String phoneNo, String tilAccount) throws Exception
    {
        UserLog obj = null;
        try 
        {
          
             obj =  find("lastName = ?1 and mobileNo = ?2 and tilAccount = ?3 ", lastName, phoneNo, tilAccount).firstResult();
            
        } catch (Exception e) {
        
           // e.printStackTrace();
           
           log.error(" loadByParams - ",e);
           
           throw new Exception(e);
            
        }
        
      return obj;
    }
     @Transactional
     public static boolean  doRemove(UserLog code) {
       log.error(" === called doRemove === "+code);
        boolean done = false;
        try 
        {
             Panache.getEntityManager().remove(code);
             
              done = true;
        } 
        catch (Exception e) {
        
            e.printStackTrace();
        }
       return done; 
    }
    
    @Transactional
    public static UserLog doLog(UserLog obj, long status) throws Exception {
        log.info(" UserProfile doLog "+obj+" status "+status);
        UserLog objx = null;
        try 
        { 
            UserLog findById = UserLog.findById(obj.id);
           log.info(" UserProfile doLog "+findById);
            if(findById != null)
            {         
                obj.status = ResourceStatusEnum.ACTIVE.name();
                //findById.statusStr = Status.doStatusDescById(findById.status);
                objx = Panache.getEntityManager().merge(obj);
            }
            
        } catch (Exception e) {
            
            log.error(" -- Exception @ UserProfile doLog ", e);
            throw new Exception(e);
        }
      return objx; 
    }
    
    
   @Transactional
    public  UserProfile doLogV2(UserProfileRequestObj fromJson, String otp, String otpmsg) throws Exception {
        log.info(" UserProfile doLogV2 "+fromJson);
        UserLog objx = null;
        UserProfile profile = null;//new UserLog();
        
        try
        {
         
            
                        profile = new UserProfile();
                        profile.createdDate = LocalDateTime.now();
                        profile.emailAddress = fromJson.emailAddress;
                        profile.firstName = fromJson.firstName;
                        profile.full_name = fromJson.lastName+" "+((fromJson.middleName ==null)?"":fromJson.middleName)+" "+fromJson.firstName;
                        profile.lastName = fromJson.lastName;
                        profile.loginStatus = 0;
                        profile.mobileNo = fromJson.mobileNo;
                        profile.tilAccount   = fromJson.tilAccount;
                        profile.status = ResourceStatusEnum.INACTIVE.name();// BigInteger.TEN.longValue();
                        //profile.statusStr = Status.doStatusDescById(profile.status);
                        profile.userRole = fromJson.userRole;
                        profile.userRoleStr = RolesInfo.doFindRoleDescByCode(profile.userRole);
                 
                        Panache.getEntityManager().persist(profile);
                        Panache.getEntityManager().flush();
              
        }
        catch (Exception e) {
          
            log.error(" doLogV2 SQL WAHALA", e.getCause());
            e.printStackTrace();
          
        }
        
        
     return profile;
    }
    
    
    
    public  List<UserLog> doListAccounts(String lastName, String phoneNo)
    {
        List<UserLog> obj = new ArrayList<>();
        try 
        {
            
             obj =  find("lastName = ?1 and mobileNo = ?2 ", lastName, phoneNo).list();
            
        } catch (Exception e) {
        
           log.error(" doListAccounts Exception - ",e);
        }
        
      return obj;
    }
    
    
    
    
    @Transactional
    public  int doLog(UserProfileRequestObj fromJson, String otp, String otpmsg) throws Exception {
        log.info(" UserProfile doLog "+fromJson);
        UserLog objx = null;
       // UserLog profile = null;//new UserLog();
        int executeUpdate = 0;
        String sql = "Insert into user_logs (created_Date, email_Address, first_name, full_name, last_name, login_status, mobile_no, till_account,  status, user_role, user_role_str ) values(?,?,?, ?, ?, ?, ?, ?, ?, ?, ?)";   
        try(Connection con =  defaultDataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql))
        {
            
            log.info("== con == "+con);
            LocalDateTime localDateTime = LocalDateTime.now();
            Timestamp timestamp = Timestamp.from(localDateTime.toInstant(ZoneOffset.UTC));

            
                  ps.setTimestamp(1, timestamp);
                  ps.setString(2, fromJson.emailAddress);
                  ps.setString(3, fromJson.firstName);
                  ps.setString(4, fromJson.lastName+" "+((fromJson.middleName ==null)?"":fromJson.middleName)+" "+fromJson.firstName);
                  ps.setString(5, fromJson.lastName);
                  ps.setInt(6, 0);
                  ps.setString(7, fromJson.mobileNo);
                  ps.setString(8, fromJson.tilAccount);
                  ps.setString(9, ResourceStatusEnum.INACTIVE.name());
                  ps.setLong(10, fromJson.userRole);
                  ps.setString(11, RolesInfo.doFindRoleDescByCode(fromJson.userRole));
                  
                  executeUpdate = ps.executeUpdate();
                  
                  defaultDataSource.flush(AgroalDataSource.FlushMode.FILL);
                 
                 log.info("--  executeUpdate -- "+executeUpdate);
            //rs = ps.executeQuery();
              
              
        }
        catch(SQLException e)
        {
            log.warn(" @++SQL local Mesg ##  >>>>>>>>>>> ",e);
          
            
        }
        catch (Exception e) {
            
          
           log.error(" SQL WAHALA", e.getCause());
            e.printStackTrace();
          
            
        }
            
        /*
                        profile = new UserLog();
                        profile.createdDate = LocalDateTime.now();
                        profile.emailAddress = fromJson.emailAddress;
                        profile.firstName = fromJson.firstName;
                        profile.full_name = fromJson.lastName+" "+((fromJson.middleName ==null)?"":fromJson.middleName)+" "+fromJson.firstName;
                        profile.lastName = fromJson.lastName;
                        profile.loginStatus = 0;
                        profile.mobileNo = fromJson.mobileNo;
                        profile.tilAccount   = fromJson.tilAccount;
                        profile.status = ResourceStatusEnum.INACTIVE.name();// BigInteger.TEN.longValue();
                        //profile.statusStr = Status.doStatusDescById(profile.status);
                        profile.userRole = fromJson.userRole;
                        profile.userRoleStr = RolesInfo.doFindRoleDescByCode(profile.userRole);
                       
                      
                       log.info("@@ --UserProfile doLog "+profile);
                       
                       */
            
       
      return executeUpdate; 
    }
    
    
    
    
    
    public  List<UserLog> doListAccountsByMsisdn(String phoneNo)
    {
         log.info(" doListAccounts doListAccountsByMsisdn - "+phoneNo);
        
        //phoneNo = phoneNo.replaceAll("++", "+");
        List<UserLog> obj = new ArrayList<>();
        try 
        {
            
             obj =  find("mobileNo = ?1 ",  phoneNo).list();
            
        } catch (Exception e) {
        
           log.error(" doListAccounts doListAccountsByMsisdn - ",e);
        }
        
      return obj;
    }
    
    
    @Transactional
    public UserLog doLogX(UserLog dto) throws CMFBException,Exception
    {
        int prodOrDev = Integer.parseInt(sysdata.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        
        UserLog newObj = null;
        try 
        {
           
               newObj =  Panache.getEntityManager().merge(dto);
            
        } 
        catch (Exception e) {
            
                if(e.getCause() instanceof CMFBException)
                {
                     System.err.println(" AHA = **  ");
                     throw new CMFBException(ErrorCodes.DUPLICATE_TRANSACTION, ErrorCodes.doErrorDesc(ErrorCodes.DUPLICATE_TRANSACTION, prodOrDev), prodOrDev);
                }
            
            log.error("Exception @ ProviderCategory doLog ",e);
            throw new Exception(e);
        
        }
        
      return newObj;
    }
    /*
    @Transactional
    public SysData doSync(SysDataDTO dto) throws HelloException,Exception
    {
        int prodOrDev = Integer.parseInt(doFindOrDefaultTo("INTEGRATION-MODE", "0"));
        
        SysData newObj = null;
        try 
        {
            SysData doFindByDescription = SysData.findById(dto.tid);// doFindByDescription(dto.description);
            //ProviderCategory.find(ProviderCategory.BY_DESCRIPTION, Parameters.with("", "").);
            SysData find = SysData.find("paramName",  dto.paramName).firstResult(); //ProviderCategory.BY_DESCRIPTION
            if(doFindByDescription != null && find !=null)
             {
               if(find.tid != doFindByDescription.tid)
               {
                   throw new HelloException(ErrorCodes.OBJECT_CONFLICT, ErrorCodes.doDesc(ErrorCodes.OBJECT_CONFLICT, prodOrDev), prodOrDev);
               }
               newObj = new SysData();
               //newObj.code = eseq.genCode("PROVIDER-CODE", 8);
               newObj.paramName = dto.paramName.toUpperCase();
               newObj.paramValue = dto.paramValue.toUpperCase();
               newObj.lastUpatedDate = LocalDateTime.now();
               newObj.lastUpdatedBy = dto.actionBy;
               newObj.lastUpdatedByStr = dto.actionByStr;
               newObj =  Panache.getEntityManager().merge(newObj);
             }
             else
             {
                 throw new HelloException(ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doDesc(ErrorCodes.NO_RECORD_FOUND, prodOrDev), prodOrDev);
             }
             
        } 
        catch (Exception e) {
            
            log.error("Exception @ ProviderCategory doSync ",e);
            throw new Exception(e);
        
        }
        
      return newObj;
    }
    */
    
    @Transactional
    public static UserLog doLog(UserLog request) throws Exception {
        
        UserLog merge = null;
        try 
        {
//            UserProfileRequestObj fromJson =new UserProfileRequestObj(request);
//            UserProfile user = new UserProfile();
//            //user.status = BigInteger.TEN.longValue();
//            user.firstName = fromJson.firstName;
//            user.middleName = fromJson.middleName;
//           // user.accountTag = "A";
//            user.lastName = fromJson.lastName;
//            user.tilAccount = fromJson.tilAccount;
//            user.emailAddress = fromJson.emailAddress;
//            RolesInfo doFindByTid = RolesInfo.doFindByTid(fromJson.userRole);
//            //user.accountType = doFindByTid ==null?"ERROR":doFindByTid.roleName; //doBuildISOMsg.getString(125);
//            //user.accountNo = fromJson.accountNo;
//            user.mobileNo = fromJson.mobileNo;
//            user.status = ResourceStatusEnum.ACTIVE.name();
//            user.userRole = fromJson.userRole;//Integer.parseInt(sysDataHelper.loadValueByName("BANK_CUSTOMER_ROLE_ID", "0"));
//            user.userRoleStr = RolesInfo.doFindRoleCodeById(user.userRole);
//            user.full_name = fromJson.lastName+" "+fromJson.middleName==null?"NA":fromJson.middleName+" "+fromJson.firstName ;//doBuildISOMsg.getString(125);
//            //user.statusStr = Status.doStatusDescById(user.status);
//            user.createdDate = LocalDateTime.now();
           // user.bvn = "NA";//(doBuildISOMsg.hasField(126))? (doBuildISOMsg.getString(126).split("#")[0]).replaceAll("\\+234", "0"):"NA";
            //user.kyc = "1";
            merge = Panache.getEntityManager().merge(request);
            
        } catch (Exception e) {
        
            log.error(" ||||||| ",e);
            throw new Exception(e);
        
        }
      return merge;
        
    }
    @Transactional
    public static UserLog doLogXX(UserLog request) throws Exception {
        
        UserLog merge = null;
        try 
        {
            UserProfileRequestObj fromJson = null;//new UserProfileRequestObj(request);
            UserLog user = new UserLog();
            //user.status = BigInteger.TEN.longValue();
            user.firstName = fromJson.firstName;
            user.middleName = fromJson.middleName;
           // user.accountTag = "A";
            user.lastName = fromJson.lastName;
            user.tilAccount = fromJson.tilAccount;
            user.emailAddress = fromJson.emailAddress;
            RolesInfo doFindByTid = RolesInfo.doFindByTid(fromJson.userRole);
            //user.accountType = doFindByTid ==null?"ERROR":doFindByTid.roleName; //doBuildISOMsg.getString(125);
            //user.accountNo = fromJson.accountNo;
            user.mobileNo = fromJson.mobileNo;
            user.status = ResourceStatusEnum.ACTIVE.name();
            user.userRole = fromJson.userRole;//Integer.parseInt(sysDataHelper.loadValueByName("BANK_CUSTOMER_ROLE_ID", "0"));
            user.userRoleStr = RolesInfo.doFindRoleCodeById(user.userRole);
            user.full_name = fromJson.lastName+" "+fromJson.middleName==null?"NA":fromJson.middleName+" "+fromJson.firstName ;//doBuildISOMsg.getString(125);
            //user.statusStr = Status.doStatusDescById(user.status);
            user.createdDate = LocalDateTime.now();
           // user.bvn = "NA";//(doBuildISOMsg.hasField(126))? (doBuildISOMsg.getString(126).split("#")[0]).replaceAll("\\+234", "0"):"NA";
            //user.kyc = "1";
            merge = Panache.getEntityManager().merge(user);
            
        } catch (Exception e) {
        
            log.error(" ||||||| ",e);
            throw new Exception(e);
        
        }
      return merge;
        
    }
    
    @Transactional
    public static UserLog doLog(UserProfileRequest request , long role) throws Exception {
        
        UserLog merge = null;
        try 
        {
            UserProfileRequestObj fromJson =new UserProfileRequestObj(request);
            UserLog user = new UserLog();
            //user.status = BigInteger.TEN.longValue();
            user.firstName = fromJson.firstName;
            user.middleName = fromJson.middleName;
            //user.accountTag = "A";
            user.lastName = fromJson.lastName;
            user.tilAccount = fromJson.tilAccount;
            user.emailAddress = fromJson.emailAddress;
            //user.accountType = "ADMIN";// doBuildISOMsg.getString(125);
            //user.accountNo = fromJson.accountNo;
            user.mobileNo = fromJson.mobileNo;
            user.status = ResourceStatusEnum.INACTIVE.name();
            user.userRole = role;// Integer.parseInt(SysData.loadValueByName("BANK_CUSTOMER_ROLE_ID", "0"));
            user.userRoleStr = RolesInfo.doFindRoleCodeById(user.userRole);
            user.full_name = fromJson.lastName==null?"":fromJson.lastName+" "+fromJson.middleName==null?"NA":fromJson.middleName+" "+fromJson.firstName ;//doBuildISOMsg.getString(125);
            //user.statusStr = Status.doStatusDescById(user.status);
            user.createdDate = LocalDateTime.now();
            
            merge = Panache.getEntityManager().merge(user);
            
            
        } catch (Exception e) {
        
            log.error(" ||||||| Exception @  UserProfile doLog ",e);
            throw new Exception(e);
        
        }
      return merge;
        
    }
    @Transactional
    public UserLog doSync(UserLog dto) throws CMFBException,Exception
    {
        int prodOrDev = Integer.parseInt(sysdata.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        
        UserLog newObj = null;
        try 
        {
           
            UserLog doFindByDescription = UserLog.findById(dto.id);
            
            if(doFindByDescription == null)
            {
                 throw new CMFBException(ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doErrorDesc(ErrorCodes.NO_RECORD_FOUND, prodOrDev), prodOrDev);
                // return Response.status(ErrorCodes.TRANSACTION_FORBIDDEN).entity(javax.json.Json.createObjectBuilder().add("errorDesc", ErrorCodes.TRANSACTION_FORBIDDEN).build()).build();
            }
            
            if("DELETED".equals(doFindByDescription.status))
            {
                 throw new CMFBException(ErrorCodes.TRANSACTION_FORBIDDEN, ErrorCodes.doErrorDesc(ErrorCodes.TRANSACTION_FORBIDDEN, prodOrDev), prodOrDev);
                // return Response.status(ErrorCodes.TRANSACTION_FORBIDDEN).entity(javax.json.Json.createObjectBuilder().add("errorDesc", ErrorCodes.TRANSACTION_FORBIDDEN).build()).build();
            }
          
            newObj =  Panache.getEntityManager().merge(dto);
           
             
        } 
        catch (Exception e) {
            
            log.error("Exception @ UserProfile  doSync ",e);
            throw new Exception(e);
        
        }
        
      return newObj;
    }
    
    /*
    @Transactional
    public SysData doSync(SysData dto) throws HelloException,Exception
    {
        int prodOrDev = Integer.parseInt(sysdata.doLookUpByParamNameStr("INTEGRATION-MODE", "0"));
        
        SysData newObj = null;
        try 
        {
           
            SysData doFindByDescription = SysData.findById(dto.tid);
            
            if(doFindByDescription.sts == 3)
            {
                 throw new HelloException(ErrorCodes.TRANSACTION_FORBIDDEN, ErrorCodes.doDesc(ErrorCodes.TRANSACTION_FORBIDDEN, prodOrDev), prodOrDev);
                // return Response.status(ErrorCodes.TRANSACTION_FORBIDDEN).entity(javax.json.Json.createObjectBuilder().add("errorDesc", ErrorCodes.TRANSACTION_FORBIDDEN).build()).build();
            }
            SysData find = SysData.find("paramName",  dto.paramName).firstResult(); //ProviderCategory.BY_DESCRIPTION
            if(doFindByDescription != null && find !=null)
             {
               if(find.tid != doFindByDescription.tid)
               {
                   throw new HelloException(ErrorCodes.OBJECT_CONFLICT, ErrorCodes.doDesc(ErrorCodes.OBJECT_CONFLICT, prodOrDev), prodOrDev);
               }
               
               newObj =  Panache.getEntityManager().merge(dto);
             }
             else
             {
                 throw new HelloException(ErrorCodes.NO_RECORD_FOUND, ErrorCodes.doDesc(ErrorCodes.NO_RECORD_FOUND, prodOrDev), prodOrDev);
             }
             
        } 
        catch (Exception e) {
            
            log.error("Exception @ ProviderCategory doSync ",e);
            throw new Exception(e);
        
        }
        
      return newObj;
    }
    */
    
    
    
    public Connection getJdbcConnection() {
        // Get the Hibernate session from the EntityManager
        DatabaseSession databaseSession = (DatabaseSession) em.getDelegate();

        // Get the JDBC connection
       // Connection connection = databaseSession.getDatasourceLogin().getConnection();

       return null;
    }
   
 
    
}
