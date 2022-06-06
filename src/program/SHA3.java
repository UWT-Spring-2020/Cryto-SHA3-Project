package src;

import java.nio.file.*;
import java.io.*;
import java.math.*;
import java.security.*;
import java.lang.*;
import java.util.*;

public class SHA3 {

    static final int RATE_KECCA512 = 136; // according to NIST SP 800-185 specifications
    static final String LINE_DIVIDER = "*******************************************************************************";
    static final String PAGE_DIVIDER = "**************************************************************************************************************************************************************";


    public static void main(String[] args) throws IOException {
        byte[] hash_cSHAKE256;;
        byte[] hash_KMACXOF256;
        
        System.out.println("Hello World! from Crypto Project\n");


        // PART 1
        // cSHAKE256(FILE) -> HASH 
        // KMACXOF256(FILE) -> HASH
        File file1 = new File("/home/kali/Programming/Crypto/SHA3/Crypto-SHA3-Project/resources/dummy.pdf");
        File file2 = new File("/home/kali/Programming/Crypto/SHA3/Crypto-SHA3-Project/resources/sampleText.txt");
        
        // InputStream fileStream = new FileInputStream(file);
        byte[] fileBytes1 = Files.readAllBytes(file1.toPath());
        byte[] fileBytes2 = Files.readAllBytes(file2.toPath());

        System.out.println("File Bytes from " + file1.toPath() + ":\n" + Arrays.toString(fileBytes1) + "\n");

        System.out.println("Running cSHAKE256 algorithm ...\n");
        hash_cSHAKE256= cSHAKE256(Arrays.toString(fileBytes1), 256, "", "");
        System.out.println("cSHAKE256 Hash:\n" + Arrays.toString(hash_cSHAKE256));

        System.out.println("\n" + LINE_DIVIDER + "\n");

        System.out.println("Running KMACXOF256 algorithm ...\n");
        hash_KMACXOF256 = KMACXOF256("",Arrays.toString(fileBytes1), 256, "");
        System.out.println("KMACXOF256 Hash:\n" + Arrays.toString(hash_KMACXOF256) + "\n\n");

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");
        

        // // cSHAKE256(TEXT_INPUT) -> HASH
        // // KMACXOF256(TEXT_INPUT) -> HASH
        Scanner myScan = new Scanner(System.in);
        
        System.out.print("Enter a text input: ");
        String userInput = myScan.nextLine();
        System.out.println();

        System.out.println("Running cSHAKE256 algorithm ...\n");
        hash_cSHAKE256 = cSHAKE256(userInput, 256, "", "");
        System.out.println("cSHAKE256 Hash:\n" + Arrays.toString(hash_cSHAKE256));

        System.out.println("\n" + LINE_DIVIDER + "\n");
        
        System.out.println("Running KMACXOF256 algorithm ...\n");
        hash_KMACXOF256 = KMACXOF256("", userInput, 256, "");
        System.out.println("KMACXOF256 Hash:\n" + Arrays.toString(hash_KMACXOF256) + "\n\n");

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");


        // // ENCRYPT AND DECRYPT:
        // // cSHAKE256(FILE, PW)
        // // KMACXOF256(FILE, PW)
        

        System.out.println("File Bytes from " + file2.toPath() + ":\n" + Arrays.toString(fileBytes2) + "\n");

        System.out.println("Running cSHAKE256 algorithm ...\n");
        hash_cSHAKE256 = cSHAKE256(Arrays.toString(fileBytes2), 256, "", "PASSWORD");
        System.out.println("cSHAKE256 Hash:\n " + Arrays.toString(hash_cSHAKE256));
        
        System.out.println("\n" + LINE_DIVIDER + "\n");
        
        System.out.println("Running KMACXOF256 algorithm ...\n");
        hash_KMACXOF256 = KMACXOF256("",Arrays.toString(fileBytes2), 256, "PASSWORD");
        System.out.println("KMACXOF256 Hash:\n" + Arrays.toString(hash_KMACXOF256));
        

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
     * KMACXOF256(K, X, L, S):
     * Validity Conditions: len(K) < 2^2040 and 0 <= L  and len(S) < 2^2040
     *
     * @param K key bit string.
     * @param X input bit string.
     * @param L output length
     * @param S customization string
     */
    public static byte[] KMACXOF256(String K, String X, int L, String S) throws IOException {
        // 1. newX = bytepad(encode_string(K), 136) || X || right_encode(L).
        // 2. return cSHAKE256(newX, L, “KMAC”, S).

        byte[] bytePad = bytepad(new String(encode_string(K)), RATE_KECCA512);
        byte[] paddedInput = bytesConcat(bytePad, X.getBytes(), right_encode(L));
        return cSHAKE256(new String(paddedInput), L, "KMAC", S);
    }


    /**
     * cSHAKE256(X, L, N, S):
     * Validity Conditions: len(K) < 2^2040 and 0 <= L  and len(S) < 2^2040
     *
     * @param X main input bit string
     * @param L output length
     * @param N function-name bit string
     * @param S customization bit string
     */
    public static byte[] cSHAKE256(String X, int L, String N, String S) throws IOException {
        // 1. If N = "" and S = "":
        // return SHAKE256(X, L);
        // 2. Else:
        // return KECCAK[512](bytepad(encode_string(N) || encode_string(S), 136) || X || 00, L).

        if (N.equals("") && S.equals("")){
            return SHAKE256(X, L);
        } else {
            byte[] bytePad = bytepad(new String(bytesConcat(encode_string(N), encode_string(S))), RATE_KECCA512);
            byte[] paddedInput = bytesConcat(bytePad, X.getBytes(), "00".getBytes());
            return KECCAK512(new String(paddedInput), L);
        }
    }

    /**
     * bytepad(X, w):
     * Validity Conditions: w > 0
     *
     * @param X input string
     * @param w padding
     */
    public static byte[] bytepad(String X, int w) throws IOException {

        byte[] z = bytesConcat(left_encode(w), X.getBytes());
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
     *    @param S input bit string
    */
    public static byte[] encode_string(String S) throws IOException {

        return bytesConcat(left_encode(S.length()), S.getBytes());

    }

     /**
     *   left_encode(x):
     *   Validity Conditions: 0 <= x < 2^2040
     *
     *   @param X string length
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
    *   @param X string length
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
    public static byte[] KECCAK512(String input, int length) {
        
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
    
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

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

    public static byte[] SHAKE256(String input, int length) {
        
        try {
            // getInstance() method is called with algorithm SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
    
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

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
