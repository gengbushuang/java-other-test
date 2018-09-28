package com.totp;

public class Base64String {
    private byte b = 0x00;
    private String str;

    private char[] chars;

    private int shift;

    public Base64String(String str) {
        this.str = str;
        this.chars = str.toCharArray();
        this.shift = Integer.numberOfTrailingZeros(chars.length);
        System.out.println(shift);
    }

    public byte[] decode(String encoded) {
        return decodeInternal(encoded);
    }

    protected byte[] decodeInternal(String encoded) {
        byte[] bytes = encoded.getBytes();
        int length = (bytes.length + 2) * Byte.SIZE;
        int group = shift * 4;
        int count = length / group;
        int count_t = bytes.length%3;
        System.out.println(count);
        System.out.println(count_t);
        byte[] result = new byte[count * 4];
        int j = 3;
        int k = 4;
        for (int i = 0; i < count; i++) {
            int val = bytes[i * 3] << 16 | (i * 3 + 1 >= bytes.length ? b : bytes[i * 3 + 1]) << 8 | (i * 3 + 2 >= bytes.length ? b : bytes[i * 3 + 2]);
            result[i * k] = (byte) ((val >> 18) & 0x3f);
            result[i * k + 1] = (byte) ((val >> 12) & 0x3f);
            result[i * k + 2] = (byte) ((val >> 6) & 0x3f);
            result[i * k + 3] = (byte) (val & 0x3f);
        }

        StringBuilder sb = new StringBuilder(count * 4);
        for(int i = 0;i<result.length-count_t;i++){
            sb.append(chars[result[i]]);
        }
        for(int i = 0;i<count_t;i++){
            sb.append('=');
        }
        System.out.println(sb.toString());
        return new byte[0];
    }

    public static void main(String[] args) {
        Base64String base64 = new Base64String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
//		base64.decode("!@#");
        base64.decode("我");
//        System.out.println("我".getBytes().length);
    }
}
