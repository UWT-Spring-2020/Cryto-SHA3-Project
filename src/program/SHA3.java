package program;

import java.nio.file.*;
import java.io.*;
import java.math.*;
import java.security.*;
import java.lang.*;
import java.util.*;
import javax.security.cert.X509Certificate;
import java.security.spec.*;

public class SHA3 {

    static final int RATE_KECCA512 = 136; // according to NIST SP 800-185 specifications
    static final int SECURITY_BIT_LEVEL = 256;
    static final String LINE_DIVIDER = "*******************************************************************************";
    static final String PAGE_DIVIDER = "**************************************************************************************************************************************************************";
    static final String PASSWORD = "Hello World!";
    static final X509Certificate SERVER_CERTIFICATE = null;


    public static void main(String[] args) 
            throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidAlgorithmParameterException {
        String hash_cSHAKE256;;
        String hash_KMACXOF256;
        
        System.out.println("Hello World! from Crypto Project\n");


        // PART 1
        // cSHAKE256(FILE) -> HASH 
        // KMACXOF256(FILE) -> HASH
        File file1 = new File("./../../resources/dummy.pdf");
        File file2 = new File("./../../resources/sampleText.txt");
        
        byte[] fileBytes1 = Files.readAllBytes(file1.toPath());
        byte[] fileBytes2 = Files.readAllBytes(file2.toPath());

        System.out.println("File Bytes from " + file1.toPath() + ":\n" + Arrays.toString(fileBytes1) + "\n");

        System.out.println("Running cSHAKE256 algorithm ...\n");
        hash_cSHAKE256= cSHAKE256(Arrays.toString(fileBytes1), SECURITY_BIT_LEVEL, "", "");
        

        System.out.println("\n" + LINE_DIVIDER + "\n");

        System.out.println("Running KMACXOF256 algorithm ...\n");
        hash_KMACXOF256 = KMACXOF256("",Arrays.toString(fileBytes1), SECURITY_BIT_LEVEL, "");
        

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");
        

        // // cSHAKE256(TEXT_INPUT) -> HASH
        // // KMACXOF256(TEXT_INPUT) -> HASH
        Scanner myScan = new Scanner(System.in);
        
        System.out.print("Enter a text input: ");
        String userInput = myScan.nextLine();
        System.out.println();

        System.out.println("Running cSHAKE256 algorithm ...\n");
        hash_cSHAKE256 = cSHAKE256(userInput, SECURITY_BIT_LEVEL, "", "");
        

        System.out.println("\n" + LINE_DIVIDER + "\n");
        
        System.out.println("Running KMACXOF256 algorithm ...\n");
        hash_KMACXOF256 = KMACXOF256("", userInput, SECURITY_BIT_LEVEL, "");
        

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");


        // // ENCRYPT AND DECRYPT:
        // // cSHAKE256(FILE, PW)
        // // KMACXOF256(FILE, PW)
        System.out.println("Encrypting " + file2.getName() + " with passphrase " + PASSWORD + "\n");

        System.out.println("Running cSHAKE256 algorithm ...\n");
        hash_cSHAKE256 = cSHAKE256(Arrays.toString(fileBytes2), SECURITY_BIT_LEVEL, "", PASSWORD);
        
        System.out.println("\n" + LINE_DIVIDER + "\n");
        
        System.out.println("Running KMACXOF256 algorithm ...\n");
        hash_KMACXOF256 = KMACXOF256("", Arrays.toString(fileBytes2), SECURITY_BIT_LEVEL, PASSWORD);

        
        System.out.println("\n" + LINE_DIVIDER + "\n");

        // decryppt here
        System.out.println("Decrypting " + file2.getName() + " cryptogram with passphrase " + PASSWORD);
        
        System.out.println("\n Implementation coming soon");

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");

        // MAC(FILE, PW)
        System.out.println("Computing Message Authentication Code (MAC) for " + file2.getName() + "\n");
        hash_KMACXOF256 = KMACXOF256("",Arrays.toString(fileBytes2), SECURITY_BIT_LEVEL, PASSWORD);

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");

        // PART 2
        // GENERATE ELLIPTIC KEY PAIR GIVEN PASSPHRASE, WRITE PUBLIC_KEY TO FILE
        // WRITE ENCRYPT(PRIVATE_KEY, PW) TO FILE

        // Setting up profiles and data-management system for the users
        File aliceProfile = new File("./../../resources/profiles/Alice_Context.txt");
        File bobProfile = new File("./../../resources/profiles/Bob_Context.txt");
        
        FileOutputStream outputStreamA = new FileOutputStream(aliceProfile);
        FileOutputStream outputStreamB = new FileOutputStream(bobProfile);

        final String ALICE_MESSAGE = "You meant the world to me. Would love to hear back from you soon! XOXO";        
        final String BOB_MESSAGE = "Who is this?";

        KeyPairGenerator aliceKeyGen = KeyPairGenerator.getInstance("EC","SunEC");
        KeyPairGenerator bobKeyGen = KeyPairGenerator.getInstance("EC","SunEC");
        
        ECGenParameterSpec ecsp = new ECGenParameterSpec("secp224r1");
        ECGenParameterSpec ecspz = new ECGenParameterSpec("secp224r1");

        aliceKeyGen.initialize(ecsp);
        bobKeyGen.initialize(ecspz);

        KeyPair aliceKP = aliceKeyGen.genKeyPair();
        KeyPair bobKP = bobKeyGen.genKeyPair();

        final String ALICE_PRIVATE_KEY = String.valueOf(aliceKP.getPrivate());
        final String ALICE_PUBLIC_KEY = String.valueOf(aliceKP.getPublic());

        final String BOB_PRIVATE_KEY = String.valueOf(bobKP.getPrivate());
        final String BOB_PUBLIC_KEY = String.valueOf(bobKP.getPublic());

        // Write both parties public key to their designated file
        System.out.println("Writing Alice and Bob's public keys to file");

        outputStreamA.write(ALICE_PUBLIC_KEY.getBytes());
        outputStreamB.write(BOB_PUBLIC_KEY.getBytes());

        

        // Write both parties encrypted private key to their designated file
        System.out.println("Writing Alice and Bob's encrypted private keys to file");
        
        String encryptedAlicePrivate = KMACXOF256(ALICE_PRIVATE_KEY, ALICE_MESSAGE, SECURITY_BIT_LEVEL, PASSWORD);
        String encryptedBobPrivate = KMACXOF256(BOB_PRIVATE_KEY, BOB_MESSAGE, SECURITY_BIT_LEVEL, PASSWORD);

        KMACXOF256("", userInput, SECURITY_BIT_LEVEL, "");

        // System.out.println("\n" + encryptedAlicePrivate);
        // System.out.println("\n" + encryptedBobPrivate);

        // outputStreamA.write(encryptedAlicePrivate);
        // outputStreamB.write(encryptedBobPrivate);

        // ENCRYPT(FILE, PUBLIC_KEY)
        // DECRYPT(ENCRYPTED_FILE, PW)
        System.out.println("Encrypting both parties' profile under their respective public key ...\n\n");
        String alice_jointProfile_hash = KMACXOF256(ALICE_PUBLIC_KEY, String.valueOf(aliceProfile), SECURITY_BIT_LEVEL, PASSWORD);
        System.out.println("Alice's Trusted Joint-Profile Hash: " + alice_jointProfile_hash + "\n");
        
        String bob_jointProfile_hash = KMACXOF256(BOB_PUBLIC_KEY, String.valueOf(bobProfile), SECURITY_BIT_LEVEL, PASSWORD);
        System.out.println("Bob's Trusted Joint-Profile Hash: " + bob_jointProfile_hash + "\n");
        
        System.out.println("Decrypting both parties' cryptograms given their password " + PASSWORD);
        
        // decryppt code goes here


        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");

        // ENCRYPT/DECRYPT(TEXT_INPUT, ?)
        System.out.print("Now that we have exchanged our ECDHIES / Schnorr key-pair agreement, we're going to move forward to storing your information to our safe vault. \n" 
                        + "\n Provided with the 256-bit security strength from the latest NIST favorite encryption algorithm, Secured Hashed Algorithm 3 (SHA3), \n "
                        + "\n better now with the integrated elliptic public/private key-pair system, on top of giving users the choice for encrypting their data\n"
                        + "\n with the password of their choice. That's a triple-whammy in terms of Cryptographic Security, a bit of an overkill for an undergraduate level"
                        + "elective course on Intro to Cryptography taught by Professor Barreto himself. Enough said, let's proceed.\n\n");

        System.out.println("Please input the information you want to store in our system:");
        userInput = myScan.nextLine();
        System.out.println();

        System.out.print("Password-protected? [Y/n] ");
        Boolean passProtected = myScan.nextBoolean();

        System.out.println("Encrypting text input using cSHAKE256 algorithm under an elliptic key-pair\n");
        if (!passProtected)
            hash_cSHAKE256 = cSHAKE256(userInput, SECURITY_BIT_LEVEL, "", "");
        else
            hash_cSHAKE256 = cSHAKE256(userInput, SECURITY_BIT_LEVEL, "", PASSWORD);

        System.out.println("cSHAKE256 Hash: " + hash_cSHAKE256);
        

        System.out.println("\n" + LINE_DIVIDER + "\n");
        
        System.out.println("Decrypting cryptogram using reverse-cSHAKE256 mechanism\n");
        
        // decryppt code goes here
        

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");

        // SIGN(FILE, PW) && WRITE SIGNATURE TO FILE
        // VERIFY(FILE, SIGNATURE, PUBLIC_KEY) 
        // ENCRYPT(FILE, RECIPIENT_PUBLIC_KEY) && SIGN(FILE, USER_PRIVATE_KEY)

        Signature aliceSig = Signature.getInstance("SHA256withECDSA","SunEC");
        Signature bobSig = Signature.getInstance("SHA256withECDSA","SunEC");

        aliceSig.initSign(aliceKP.getPrivate());
        bobSig.initSign(bobKP.getPrivate());

        byte[] Alice2BobMsg = ALICE_MESSAGE.getBytes("UTF-8");
        byte[] Bob2AliceMsg = BOB_MESSAGE.getBytes("UTF-8");
        byte[] a_sig; byte[] b_sig;
        
        aliceSig.update(Alice2BobMsg);
        bobSig.update(Bob2AliceMsg);
        
        a_sig = aliceSig.sign();
        b_sig = bobSig.sign();

        Signature aliceSigVerify = Signature.getInstance("SHA256withECDSA", "SunEC");
        Signature bobSigVerify = Signature.getInstance("SHA256withECDSA", "SunEC");

        aliceSigVerify.initVerify(aliceKP.getPublic());
        bobSigVerify.initVerify(bobKP.getPublic());
        
        aliceSigVerify.update(Alice2BobMsg);
        bobSigVerify.update(Bob2AliceMsg);

        boolean validSignature_Alice = aliceSigVerify.verify(aliceSig.sign());
        boolean validSignature_Bob = bobSigVerify.verify(bobSig.sign());

        System.out.println("Alice Signature: " + validSignature_Alice);
        System.out.println("Bob Signature: " + validSignature_Bob);

        System.out.println("\n\n" + PAGE_DIVIDER + "\n" + PAGE_DIVIDER + "\n\n");


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
    public static String KMACXOF256(String K, String X, int L, String S) throws IOException {
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
    public static String cSHAKE256(String X, int L, String N, String S) throws IOException {
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
    public static String KECCAK512(String input, int length) {
        
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
            return hashtext;
        }
    
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }       
    }

    public static String SHAKE256(String input, int length) {
        
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
            return hashtext;
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
