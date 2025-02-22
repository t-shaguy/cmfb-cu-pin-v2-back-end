/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paycraftsystems.cmfb.email.resources;

import io.quarkus.mailer.Mail;
import java.util.concurrent.CompletionStage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.reactive.ReactiveMailer;
import java.io.File;

/**
 *
 * @author root
 */

//@ApplicationScoped
@Path("mailer")
public class MailService {
    
    
    @Inject
    Mailer mailer;

    @Inject
    ReactiveMailer reactiveMailer;
    
    
    @GET
    @Path("/simple")
    public Response sendASimpleEmail() {
        System.out.println(" called simple mailer...... ");
        try 
        {
             mailer.send(Mail.withText("taysay_shaguy@yahoo.com", "CU PINS NOTIFICATION", "This is your transaction PIN and all that "),Mail.withText("taysaycoding@gmail.com", "CU PINS NOTIFICATION", "This is your transaction PIN and all that "));
        return Response.accepted().build();
            
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        return Response.noContent().build();
       
    }

    @GET
    @Path("/async")
    public CompletionStage<Response> sendASimpleEmailAsync() {
        System.out.println(" called async mailer...... ");
        return reactiveMailer.send(
                Mail.withText("taysay_shaguy@yahoo.com", "CU PINS NOTIFICATION", "This is your transaction PIN and all that"),
                Mail.withText("taysaycoding@gmail.com", "CU PINS NOTIFICATION", "This is your transaction PIN and all that"))
                .subscribeAsCompletionStage()
                .thenApply(x -> Response.accepted().build());
    }
    
    
    @GET
    @Path("/html")
    public Response sendingHTML() {
        
       
        String body = "<strong>Hello!</strong>" + "\n" +
            "<p>Here is an image for you: <img src=\"cid:my-image@quarkus.io\"/></p>" +
            "<p>Regards</p>";
        mailer.send(Mail.withHtml("to@acme.org", "An email in HTML", body)
            .addInlineAttachment("quarkus-logo.png",
                new File("quarkus-logo.png"),
                "image/png", "<my-image@quarkus.io>"));
        return Response.accepted().build();
    }
    
  
    
}
