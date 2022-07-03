package sdk.Tools;

import java.math.BigInteger;

public class Util {
	
	/**	������ת���ֽ������ֽ����飩������*/
	public static byte[] bigInteger2Bytes(BigInteger n) 
	{
		byte tmp[] = null;
        if(n == null){
        	return null;
        }
        
        if(n.toByteArray().length == 33){
            tmp = new byte[32];
            System.arraycopy(n.toByteArray(), 1, tmp, 0, 32);
        } 
        else if(n.toByteArray().length == 32){
            tmp = n.toByteArray();
        } 
        else{
            tmp = new byte[32];
            for(int i = 0; i < 32 - n.toByteArray().length; i++){
            	tmp[i] = (byte) 0;
            }
            System.arraycopy(n.toByteArray(), 0, tmp, 32 - n.toByteArray().length, n.toByteArray().length);
        }
        return tmp;
	}
	
	/** bytes ת����16�����ַ��� */
	public static String bytes2HexString(byte[] b){
		 final String hexChar = "0123456789ABCDEF";
		 StringBuilder sb = new StringBuilder();
		 
		 for (int i = 0; i < b.length; i++){
			 sb.append(hexChar.charAt((b[i] & 0xf0) >> 4));
			 sb.append(hexChar.charAt(b[i] & 0x0f));
		 }
		 
		 return sb.toString();
	 }
	
	/** 16�����ַ���ת���� bytes */
	public static byte[] hexString2Bytes(String hexString){
		if (hexString == null || hexString.equals("")){
			return null;
		}
		
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++){
			int pos = i * 2;
			d[i] = (byte) (char2Byte(hexChars[pos]) << 4 | char2Byte(hexChars[pos + 1]));
		}
		return d;
	}
	
	/** char to bytes*/
	public static byte char2Byte(char c) 
	{
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	public static byte[] int2ByteArray(int i) {
        byte[] byteArray = new byte[2];
        byteArray[0] = (byte) ((i & 0xFFFF) >>> 8);
        byteArray[1] = (byte) (i & 0xFF);
        return byteArray;
    }
	
	/** long to bytes*/
    public static byte[] long2bytes(long l) {
        byte[] bytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (byte) (l >>> ((7 - i) * 8));
        }
        return bytes;
    }
	
//	public static void main(String[] args){
//		String s = "49";
//		byte[] b = hexString2Bytes(s);
//		s = bytes2HexString(b);
//		System.out.println(b);
//		System.out.println(s);
//	}
}
