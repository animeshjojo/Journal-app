package com.animesh.journal.service;

import com.animesh.journal.services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

   @Autowired
   private EmailService emailService;

   @Disabled
   @Test
   public void emailServiceTest(){
       emailService.sendEmail("XXX@gmail.com","Testing Java Mail Sender","Hello World!");
   }
}
