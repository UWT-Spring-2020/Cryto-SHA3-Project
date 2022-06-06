package source;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.lang.Cloneable;

public class SHA3 {

    public SHA3() {

    }

    public static void main(String[] args) {
        System.out.println("Hello World! from Crypto Project");

        // PART 1
        // cSHAKE256(FILE) -> HASH 
        // KMACXOF256(FILE) -> HASH
        // SHA3_CSHAKE("", FILE, 256, "");
        // SHA3_KMACXOF("", FILE, 256, "");

        // // cSHAKE256(TEXT_INPUT) -> HASH
        // // KMACXOF256(TEXT_INPUT) -> HASH
        // SHA3_CSHAKE("", TEXT_INPUT, 256, "");
        // SHA3_KMACXOF("", TEXT_INPUT, 256, "");

        // // ENCRYPT AND DECRYPT:
        // // cSHAKE256(FILE, PW)
        // // KMACXOF256(FILE, PW)
        // SHA3_CSHAKE("", FILE, 256, PW);
        // SHA3_KMACXOF("", FILE, 256, PW);

        // decryppt here




        // MAC(FILE, PW)

        // PART 2
        // GENERATE ELLIPTIC KEY PAIR GIVEN PASSPHRASE, WRITE PUBLIC_KEY TO FILE
        // WRITE ENCRYPT(PRIVATE_KEY, PW) TO FILE

        // ENCRYPT(FILE, PUBLIC_KEY)
        // DECRYPT(ENCRYPTED_FILE, PW)

        // ENCRYPT/DECRYPT(TEXT_INPUT, ?)

        // SIGN(FILE, PW) && WRITE SIGNATURE TO FILE
        // VERIFY(FILE, SIGNATURE, PUBLIC_KEY) 

        // ENCRYPT(FILE, RECIPIENT_PUBLIC_KEY) && SIGN(FILE, USER_PRIVATE_KEY)
    }

                                                                                                                          /**
     * SHA3_KMACXOF(K, X, L, S):
     * Validity Conditions: len(K) < 2^2040 and 0 <= L  and len(S) < 2^2040
     *
     * @param K key bit string.
     * @param X input bit string.
     * @param L output length
     * @param S customization string
     */
    public static byte[] KMACXOF(byte[] K, byte[] X, int L, byte[] S) throws IOException {
        // 1. newX = bytepad(encode_string(K), 136) || X || right_encode(0).
        // 2. T = bytepad(encode_string(“KMAC”) || encode_string(S), 136).
        // 3. return KECCAK[512](T || newX || 00, L).

        byte[] newX = bytesConcat(bytepad(encode_string(K), 136), X, right_encode(0));
        byte[] T = bytepad(bytesConcat(encode_string("KMAC".getBytes()) , encode_string(S)), 136);

        return KECCAK(bytesConcat(T, newX, "00".getBytes()), L);
    }


    /**
     * SHA3_CSHAKE256(K, X, L, S):
     * Validity Conditions: len(K) < 2^2040 and 0 <= L  and len(S) < 2^2040
     *
     * @param K key bit string.
     * @param X input bit string.
     * @param L output length
     * @param S customization string
     */
    public static byte[] cSHAKE(byte[] K, byte[] X, int L, byte[] S) throws IOException {
        // 1. newX = bytepad(encode_string(K), 136) || X || right_encode(0).
        // 2. return cSHAKE256(newX, L, “KMAC”, S).

        byte[] newX = bytesConcat(bytepad(encode_string(K), 136), X, right_encode(0));
        return cSHAKE256(newX, L, "KMAC".getBytes(), S);

    }

    /**
     * cSHAKE256(X, L, N, S):
     * Validity Conditions: len(N) < 2^2040 and len(S) < 2^2040
     *
     * @param X input bit string
     * @param L output length
     * @param N function-name bit string
     * @param S customization bit string
     */
    public static byte[] cSHAKE256(byte[] X, int L, byte[] N, byte[] S) throws IOException {
        // 1. If N = "" and S = "":
        //      return SHAKE256(X, L);
        // 2. Else:
        //      return KECCAK[512](bytepad(encode_string(N) || encode_string(S), 136) || X || 00, L)

        
        byte[] pad = bytepad(bytesConcat(encode_string(N), encode_string(S)), 136);
        byte[] arg = bytesConcat(pad, X , "00".getBytes());
        return KECCAK(arg, L);
    
    }


    /**
     * bytepad(X, w):
     * Validity Conditions: w > 0
     *
     * @param X input string
     * @param w padding
     */
    public static byte[] bytepad(byte[] X, int w) throws IOException {

        byte[] z = bytesConcat(left_encode(w), X);
        while (z.length % 8 != 0)
            z = bytesConcat(z, "00".getBytes());
        while ((z.length/8) % w != 0)
            z = bytesConcat(z, "00000000".getBytes());
        return z;
    }
    /** 
     *    encode_string(S):
     *    Validity Conditions: 0 <= len(S) < 2^2040
     *
     *    @param S    
    */
    public static byte[] encode_string(byte[] S) throws IOException {

        return bytesConcat(left_encode(S.length), S);

    }

     /**
     *   left_encode(x):
     *   Validity Conditions: 0 <= x < 2^2040
     *
     *   @param X string length
     *
     */
    public static byte[] left_encode(int X) {
        
        //        Let n be the smallest positive integer for which 2 ^ 8n > x
        //        Let x1, x2, ..., xn be the base-256 encoding of x satisfying:
        //        x = (Rheiman Sum) 2^8(n - i) xi, for i = 1 to n
        //        Let Oi = enc8(xi), i = 1 to n
        //        Let O0 = enc8(n)
        //        Return O = O0 || Oi || ... || On-1 || On

        byte n = 1;

        long v = X;
        while ((v >>= 8) != 0)
            n++;

        byte[] b = new byte[n + 1];

        b[0] = n;

        for (int i = 1; i <= n; i++)
            b[i] = (byte)(X >> (8 * (n - i)));

        return b;
    }

    /**
    *   right_encode(x):
    *   Validity Conditions: 0 <= x < 2^2040
    *
    *  @param X string length
    *
    */
    public static byte[] right_encode(int X) {
        
        //        Let n be the smallest positive integer for which 2^8n > x
        //        Let x1, x2, ..., xn be the base-256 encoding of x satisfying:
        //        x = (Rheiman Sum) 2^8(n-1) xi, for i = 1 to n
        //        Let Oi = enc8(x1), for i = 1 to n
        //        Let On+1 = enc8(n)
        //        Return O = O1 || O2 || ... || On || On+1

        byte n = 1;

        long v = X;
        while ((v >>= 8) != 0)
            n++;

        byte[] b = new byte[n + 1];

        b[n] = n;

        for (int i = 0; i < n; i++)
            b[i] = (byte)(X >> (8 * (n - i - 1)));

        return b;
    }

//        # KECCAK ?? Import from java.lang.Cloneable
    public static byte[] KECCAK(byte[] input, int length) {
        if (length == 512){
            try {
                // getInstance() method is called with algorithm SHA-512
                MessageDigest md = MessageDigest.getInstance("SHA-512");
      
                // digest() method is called
                // to calculate message digest of the input string
                // returned as array of byte
                byte[] messageDigest = md.digest(input);
    
                System.out.println("Message Digest:\n" + messageDigest + "\n");
      
                // Convert byte array into signum representation
                BigInteger no = new BigInteger(1, messageDigest);
    
                System.out.println("Big Integer:\n" + no + "\n");
      
                // Convert message digest into hex value
                String hashtext = no.toString(16);
    
                System.out.println("Hash Text:\n" + hashtext + "\n");
      
                // Add preceding 0s to make it 32 bit
                while (hashtext.length() < 32) {
                    hashtext = "0" + hashtext;
                }
    
                System.out.println("Outval:\n" + hashtext + "\n");
      
                // return the HashText
                return hashtext.getBytes();
            }
      
            // For specifying wrong message digest algorithms
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }       
    }
        return null;
    }

    private static byte[] bytesConcat(byte[] ... arrays) throws IOException {

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
            for (byte[] arr : arrays)
                outputStream.write(arr);
    
            return outputStream.toByteArray();
    
        } catch (IOException e) {
            throw new IOException("Invalid input for bytes concatenation! Check bytesConcat");
        }
    }
    
    
}