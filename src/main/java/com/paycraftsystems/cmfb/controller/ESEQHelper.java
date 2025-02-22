/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.controller;


//import com.paycraftsystems.hellovas.entities.ESeq;
import com.paycraftsystems.cmfb.entities.ESeq;
import com.paycraftsystems.resources.ValidationHelper;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigInteger;
import java.util.List;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import javax.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author taysayshaguy
 */

@ApplicationScoped
public class ESEQHelper  implements PanacheRepository<ESeq>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ESEQHelper.class);
    
   // @Inject
   // EntityManager em;
    
    ValidationHelper  resourceH = new ValidationHelper();
  
    @Transactional
    public synchronized ESeq genCode(String  seqence_code)
    {
        ESeq resp = null;
        try
        {
            
           if(seqence_code != null && !seqence_code.trim().equals(""))
           {
                TypedQuery<ESeq> query = Panache.getEntityManager().createNamedQuery(ESeq.BY_SEQCODE, ESeq.class).setParameter("passed", seqence_code.trim());
              
                List<ESeq> resultList = query.getResultList();
                if(resultList != null && resultList.size() > 0)
                {
                      resp = resultList.get(0);
                }
               
              
              if(resp == null)
              {
                      resp = new ESeq();
                      resp.lastSeq = BigInteger.ONE;
                      resp.length = 20;
                      resp.seqCode = seqence_code.trim().toUpperCase();
                      //resp = em.merge(resp);
                      resp = Panache.getEntityManager().merge(resp);
              }
              else
              {
                      resp.lastSeq = (resp.lastSeq.add(BigInteger.ONE));
                      resp.length = 20;
                      resp.seqCode = seqence_code.trim().toUpperCase();
                      
                      resp = Panache.getEntityManager().merge(resp);// em.merge(resp);
                  
              }
              
           
           }
           
          
        }
        catch(Exception ex)
        {  //resp = "1";
            ex.printStackTrace();
        }
       return resp;
    }
    
    @Transactional
    public synchronized String genCodePadded(String  seqence_code, int length)
    {
       LOGGER.info(" genCode length = " +   length+" seqence_code : "+seqence_code);
        ESeq resp = null;
        try
        {
            
           if(seqence_code != null && !seqence_code.trim().equals(""))
           {
               TypedQuery<ESeq> query = Panache.getEntityManager().createNamedQuery(ESeq.BY_SEQCODE, ESeq.class).setParameter("passed", seqence_code.trim());
              
               List<ESeq> resultList = query.getResultList();
               if(resultList != null && resultList.size() > 0)
               {
                      resp = resultList.get(0);
               }
               System.out.println(" ESEQ : resp = " + resp);
              if(resp == null)
              {
                      resp = new ESeq();
                      resp.lastSeq = BigInteger.ONE;
                      resp.length = length;
                      resp.seqCode = seqence_code.trim().toUpperCase();
                      
                      resp =Panache.getEntityManager().merge(resp);// em.merge(resp);
                      
                       System.out.println("XX ESEQ : resp = " + resp);
              }
              else
              {
                      resp.lastSeq = resp.lastSeq.add(BigInteger.ONE);
                      resp.length = length;
                      resp.seqCode = seqence_code.trim().toUpperCase();
                      
                      resp = Panache.getEntityManager().merge(resp);//em.merge(resp);
                  
              }
              
           
           }
           
          
        }
        catch(Exception ex)
        {  //resp = "1";
            ex.printStackTrace();
        }
        
       System.out.println("resp = getSeqCode  " + resp.seqCode+" - "+resp.lastSeq+" length - "+length+" -- "+resourceH.padZero(length - resp.lastSeq.intValue()));
       
        return seqence_code.toUpperCase()+""+resp !=null?(resourceH.padZero(length - String.valueOf(resp.lastSeq).length())+resp.lastSeq):"";
     
    }
    
    @Transactional
    public synchronized String genCode(String  seqence_code, int length)
    {
        LOGGER.info(" --  genCode length = " +   length+" seqence_code : "+seqence_code);
        ESeq resp = null;
        try
        {
            
           if(seqence_code != null && !seqence_code.trim().equals(""))
           {
               TypedQuery<ESeq> query = Panache.getEntityManager().createNamedQuery(ESeq.BY_SEQCODE, ESeq.class).setParameter("passed", seqence_code.trim());
              
               List<ESeq> resultList = query.getResultList();
               if(resultList != null && resultList.size() > 0)
               {
                      resp = resultList.get(0);
               }
              
              if(resp == null)
              {
                      resp = new ESeq();
                      resp.lastSeq = BigInteger.ONE;
                      resp.length = length;
                      resp.seqCode = seqence_code.trim().toUpperCase();
                      
                      resp = Panache.getEntityManager().merge(resp);// em.merge(resp);
              }
              else
              {
                      resp.lastSeq = resp.lastSeq.add(BigInteger.ONE);
                      resp.length = length;
                      resp.seqCode = seqence_code.trim().toUpperCase();
                      
                      resp = Panache.getEntityManager().merge(resp);// em.merge(resp);
                  
              }
              
           
           }
           
          
        }
        catch(Exception ex)
        {  //resp = "1";
            ex.printStackTrace();
        }
        
        return resp !=null?(resourceH.padZero(length - String.valueOf(resp.lastSeq).length())+resp.lastSeq):"";
     
    }
    
    
    /*
    public synchronized String genCode2(String  seqence_code, int length)
    {
        //System.out.println(" genCode2 length = " +   length+" seqence_code : "+seqence_code);
       
        ESeq resp = null;
        try
        {
            
           if(seqence_code != null && !seqence_code.trim().equals(""))
           {
               TypedQuery<ESeq> query = em.createNamedQuery(ESeq.BY_SEQCODE, ESeq.class).setParameter("passed", seqence_code.trim());
              
               List<ESeq> resultList = query.getResultList();
               if(resultList != null && resultList.size() > 0)
               {
                      resp = resultList.get(0);
               }
              
              if(resp == null)
              {
                      resp = new ESeq();
                      resp.setLastSeq(BigInteger.ONE);
                      resp.setLength(length);
                      resp.setSeqCode(seqence_code.trim().toUpperCase());
                      
                      resp = em.merge(resp);
              }
              else
              {
                      resp.setLastSeq(resp.getLastSeq().add(BigInteger.ONE));
                      resp.setLength(length);
                      resp.setSeqCode(seqence_code.trim().toUpperCase());
                      
                      resp = em.merge(resp);
                  
              }
              
           
           }
           
          
        }
        catch(Exception ex)
        {  //resp = "1";
            ex.printStackTrace();
        }
        //SysUtil.padZero(5- String.valueOf(resp.getTid()).length())+resp.getTid()+ 
      return resp !=null?(sysUtil.padZero(length - String.valueOf(resp.getLastSeq()).length())+resp.getLastSeq()):null;
       //return resp;
    }
    
    public synchronized String genCode2(String  seqence_code, int length, String branchCode)
    {
        //System.out.println(" genCodexx length = " +   length+" seqence_code : "+seqence_code+" branchCode = "+branchCode);
       
        ESeq resp = null;
        String code = "";
        try
        {
            
           if(seqence_code != null && !seqence_code.trim().equals("")  && branchCode !=null)
           {
              code = branchCode+"-"+seqence_code.trim();
              Map<String, Object> params = new HashMap<>();
              params.put("passed", code);
              resp = findOneResult(Queries.ESEQ_DOES_SEQ_EXIST, params);
              
              if(resp == null)
              {
                      resp = new ESeq();
                      resp.setLastSeq(BigInteger.ONE);
                      resp.setLength(length);
                      resp.setSeqCode(code.trim().toUpperCase());
                      
                      resp = create(resp);
              }
              else
              {
                      resp.setLastSeq(resp.getLastSeq().add(BigInteger.ONE));
                      resp.setLength(length);
                      resp.setSeqCode(seqence_code.trim().toUpperCase());
                      
                      resp = update(resp);
                  
              }
              
           
           }
           
          
        }
        catch(Exception ex)
        {  //resp = "1";
            ex.printStackTrace();
        }
        //SysUtil.padZero(5- String.valueOf(resp.getTid()).length())+resp.getTid()+ 
      return resp !=null?branchCode+(sysUtil.padZero(length - String.valueOf(resp.getLastSeq()).length())+resp.getLastSeq()):"";
       //return resp;
    }
    
    */
    
}
