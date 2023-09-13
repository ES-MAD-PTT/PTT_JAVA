package com.atos.utils.security;

import org.jasypt.util.text.AES256TextEncryptor;

import com.atos.utils.Constants;

public class JasyptEncryption {

	public static void main(String[] args) {
		
/*		// encrypting and checking a password
		String userPassword = "prueba";
		String inputPassword = "prueba";
		
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(userPassword);

		if (passwordEncryptor.checkPassword(inputPassword, encryptedPassword)) {
			System.out.println("correcto");
		} else {
			System.out.println("error");			
		}
		*/
		
		// encrypting and decrypting a text
		String myEncryptionPassword = Constants.AES_encription_password;
		String myText = "Yf!E9NLN7v";
		
		AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
		textEncryptor.setPassword(myEncryptionPassword);
		String myEncryptedText = textEncryptor.encrypt(myText);
		System.out.println("myEncryptedText: " + myEncryptedText);
		
		// password encriptada a introducir en base de datos:
		// FlQA9Qr37NVvEDbraRM/xIXd/c33kwaIKcYXpc+Q/suQrWgiYIhbFWXNDv8RGuhy
		
		
		String plainText = textEncryptor.decrypt(myEncryptedText);
		System.out.println("plainText: " + plainText);
		
	}

}
