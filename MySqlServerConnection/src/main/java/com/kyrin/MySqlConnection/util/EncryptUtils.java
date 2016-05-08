package com.kyrin.MySqlConnection.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mysql.jdbc.Security;
import com.mysql.jdbc.StringUtils;
import com.mysql.jdbc.Util;

/**
 * mysql client -> server password and scramble encrypt
 * 
 * 算法：SHA1( password ) XOR SHA1( "20-bytes random data from server" <concat> SHA1( SHA1( password ) ) )
 * 
 * XOR：异或运算
 * 
 * 可以参考：mysql-connector-java.jar 中Secutiry类，这里面的scramble411实现了相关算法（也可以自己实现，这里复制了它的源码）
 * 
 * @author kyrin
 *
 */

public final class EncryptUtils {
	
	 // SERVER: public_seed=create_random_string()
    // send(public_seed)
    //
    // CLIENT: recv(public_seed)
    // hash_stage1=sha1("password")
    // hash_stage2=sha1(hash_stage1)
    // reply=xor(hash_stage1, sha1(public_seed,hash_stage2)
    //
    // // this three steps are done in scramble()
    //
    // send(reply)
    //
    //SHA1( password ) XOR SHA1( "20-bytes random data from server" <concat> SHA1( SHA1( password ) ) )
	//
    // SERVER: recv(reply)
    // hash_stage1=xor(reply, sha1(public_seed,hash_stage2))
    // candidate_hash2=sha1(hash_stage1)
    // check(candidate_hash2==hash_stage2)
    public static byte[] scramble411(String password, String seed, String passwordEncoding) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        byte[] passwordHashStage1 = md.digest((passwordEncoding == null || passwordEncoding.length() == 0) ? StringUtils.getBytes(password) : StringUtils
                .getBytes(password, passwordEncoding));
        md.reset();

        byte[] passwordHashStage2 = md.digest(passwordHashStage1);
        md.reset();

        byte[] seedAsBytes = StringUtils.getBytes(seed, "ASCII"); // for debugging
        md.update(seedAsBytes);
        md.update(passwordHashStage2);

        byte[] toBeXord = md.digest();

        int numToXor = toBeXord.length;

        for (int i = 0; i < numToXor; i++) {
            toBeXord[i] = (byte) (toBeXord[i] ^ passwordHashStage1[i]);
        }

        return toBeXord;
    }

	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String scrambled="FC[!0mXOc'3lIfHq\"PLo";
		String password="root";
		//adf367dc5075e76ed4b0134c6e68f7a8cc953efa
		
		System.out.println(HexTranslate.paser(scramble411(password,scrambled, "")));
		//System.out.println(0xf7ff|(0x807f<<16));
	}
}
