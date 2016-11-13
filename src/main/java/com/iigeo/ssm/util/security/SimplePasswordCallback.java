package com.iigeo.ssm.util.security;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SimplePasswordCallback implements PasswordCallback {

	private static final String ALGO = "AES";

    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    private String password;
    private String salt;

    @Override
    public String encryptPassword() {
    	if (password==null || password.trim().length()<=0) {
			throw new IllegalArgumentException("the arg of password is illegal.");
		}
    	if (salt==null || salt.trim().length()<=0) {
    		throw new IllegalArgumentException("the arg of salt is illegal.");
		}
        try {
            Key key = new SecretKeySpec(salt.getBytes(DEFAULT_ENCODING), ALGO);
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(encodeHex(cipher.doFinal(password.getBytes(DEFAULT_ENCODING))));
        } catch (Exception e) {
            throw new IllegalStateException("failed to encrypt password.", e);
        }
    }

    @Override
    public String decryptPassword() {
    	if (password==null || password.trim().length()<=0) {
			throw new IllegalArgumentException("the arg of password is illegal.");
		}
    	if (salt==null || salt.trim().length()<=0) {
    		throw new IllegalArgumentException("the arg of salt is illegal.");
		}
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            Key key = new SecretKeySpec(salt.getBytes(DEFAULT_ENCODING), ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(decodeHex(password.toCharArray())), DEFAULT_ENCODING);
        } catch (Exception e) {
            throw new IllegalStateException("failed to decrypt password.", e);
        }
    }

    protected char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
        	
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }

    public byte[] decodeHex(char[] data) {
        int len = data.length;
        if ((len & 0x01) != 0) {
            throw new IllegalStateException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }

    protected int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new IllegalStateException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

	@Override
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
    	return decryptPassword();
    }
    
    @Override
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public static void main(String[] args) throws Exception {
    	String key="@@__OOO__NO1__##";
//    	String url="http://localhost:8080/lejaneapi/v3?";
    	String url="/lejaneapi/v3?";
    	String param="b=o&ss=6&0=j&x=x&y=y&z=z";
    	String token="2opiop236598";
    	
    	//��ȡsign: Դ�ַ���:(url+param) ��Կ:MD5(token)
        SimplePasswordCallback cb = new SimplePasswordCallback();
        cb.setPassword(url+param);
        cb.setSalt(MD5(token));
        String sign=cb.encryptPassword();
        System.out.println("encrypted sign-string is:"+url+param);
        System.out.println("encrypted sign-salt is:"+MD5(token));
        System.out.println("encrypted sign is:  " + sign);
        System.out.println();
        
        //��ȡsifude_param ԭ�ַ���:(param+"&"+sign) ��Կ:@@__OOO__NO1__##
        cb.setPassword(param+"&"+"sifude_sign="+sign);
        cb.setSalt(key);
        System.out.println("encrypted password is:  " + cb.encryptPassword());
        System.out.println();
        System.out.println("http://localhost:8080/ccc/v3?sifude_param="+cb.encryptPassword());
    }
    
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return result.toString().substring(8, 24);
    }
}
