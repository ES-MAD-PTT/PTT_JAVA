package com.atos.utils.security;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.SimpleByteSource;

public class GenerateHashPassword {

	public static void main(String[] args) {
		
		if(args.length==0){
			System.out.println("You must introduce: HASHNUMBER password1 password2 ... passwordN");
			System.out.println("You must input the number of hash to encrypt (256 or 512) and the passwords that you want to encrypt");
			System.out.println("e.g. 512 pass01 pass02 pass03");
			return;
		}
		if(!args[0].equals("256")&& !args[0].equals("512")){
			System.out.println("You must introduce: HASHNUMBER password1 password2 ... passwordN");
			System.out.println("You must input the number of hash to encrypt (256 or 512) and the passwords that you want to encrypt");
			System.out.println("e.g. 512 pass01 pass02 pass03");
			return;
		}
		// Generate hash and salt for sha256
		if(args[0].equals("256")){
			for(int i=1;i<args.length;i++){
				String password = args[i];
				
	            System.out.println("Password: " + password);
				String salt256 = new BigInteger(250, new SecureRandom()).toString(32);
		
				System.out.println("salt256");
				System.out.println(salt256);
		
				Sha256Hash hash256 = new Sha256Hash(password, (new SimpleByteSource(salt256)).getBytes());
				String saltedHashedPassword256 = hash256.toHex();
		
				System.out.println("pass256");
				System.out.println(saltedHashedPassword256);
				System.out.println("//////////////////////////////////////////");
			}
		}

		// Generate hash and salt for sha512
		if(args[0].equals("512")){
			for(int i=0;i<args.length;i++){
				String password = args[i];
				
	            System.out.println("Password: " + password);
				String salt512 = new BigInteger(500, new SecureRandom()).toString(32);
		
				System.out.println("salt512");
				System.out.println(salt512);
		
				Sha512Hash hash512 = new Sha512Hash(password, (new SimpleByteSource(salt512)).getBytes());
				String saltedHashedPassword512 = hash512.toHex();
		
				System.out.println("pass512");
				System.out.println(saltedHashedPassword512);
				System.out.println("//////////////////////////////////////////");
			}
		}
		
	}

}
