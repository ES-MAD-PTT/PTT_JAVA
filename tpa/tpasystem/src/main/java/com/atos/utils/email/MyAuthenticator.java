package com.atos.utils.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    String user;
    String pass;
    
    public MyAuthenticator(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
    
    public PasswordAuthentication getPasswordAutentication(){
        return new PasswordAuthentication(user, pass);
      }
}