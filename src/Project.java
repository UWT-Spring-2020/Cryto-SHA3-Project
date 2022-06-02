package source;

import java.lang.Cloneable;

class Project {

    public static void main(String[] args) {
        System.out.println("Hello World! from Crypto Project");

        // Part 1
       cSHAKE256(public_key, 256, "", "key fingerprint");
       cSHAKE128(email_contents, 256, "", "email signature");

       // more

       // Part 2
    }


    /**
     * KMACXOF256(K, X, L, S):
     * Validity Conditions: len(K) < 2^2040 and 0 <= L  and len(S) < 2^2040
     *
     * @param k a key bit string of any length, including zero
     * @param x the main input bit string. It may be of any length, including zero
     * @param l an optional customization bit string of any length, including zero.
     *          If no customization is desired, S is set to the empty string.
     * @param s an integer representing the requested output length in bits
     */
    public static byte[] KMACXOF256_KECCAK(String K, String X, int L, String S) {


        byte[] newX = bytepad(encode_string(K), 136) || X || right_encode(0);
        byte[] T = bytepad(encode_string("KMAC") || encode_string(S), 136);
        return KECCAK[512](T || newX || 00, L);

    }


    /**
     * KMACXOF256(K, X, L, S):
     * Validity Conditions: len(K) < 2^2040 and 0 <= L  and len(S) < 2^2040
     *
     * @param k a key bit string of any length, including zero
     * @param x the main input bit string. It may be of any length, including zero
     * @param l an optional customization bit string of any length, including zero.
     *          If no customization is desired, S is set to the empty string.
     * @param s an integer representing the requested output length in bits
     */
    public static byte[] KMACXOF256_cSHAKE(String K, String X, int L, String S) {

        byte[] newX = bytepad(encode_string(K), 136) || X || right_encode(0);
        return cSHAKE256(newX, L, "KMAC", S);

    }

    /**
     * cSHAKE256(X, L, N, S):
     * Validity Conditions: len(N) < 2^2040 and len(S) < 2^2040
     *
     * @param x main input bit string. It may be of any length, including zero
     * @param l an integer representing the requested length in bits
     * @param n a function name bit string based on cSHAKE given by NIST
     *          When no function other than cSHAKE is desired, n is set to empty string
     * @param s customization bit string used to define a variant of the function
     *          When no customization is desired, s is set to an empty string
     */
    public static byte[] cSHAKE256(String X, int L, String N, String S) {

               if (N.equals("") && S.equal("")) {
                    return SHAKE256(X,L);
               } else {
                    return KECCAK[512](bytepad(encode_string(N) || encode_string(S), 136) || X || 00, L);
               }

    }


    /**
     * bytepad(X, w):
     * Validity Conditions: w > 0
     *
     * @param X
     * @param w
     */
    public static byte[] bytepad(int X, int w) {

        byte[] z = left-encode(w) || X;
        while (z.length % 8 != 0)
            z = z || 0;
        while ((z.length/8) % w != 0)
            z = z || 00000000;
        return z;

    }
    /** 
     *    encode_string(S):
     *    Validity Conditions: 0 <= len(S) < 2^2040
     *
     *    @param S    
    */
    public static String encode_string(String S) {

               return left_encode(len(S)) || S;
    }

     /**
     *   right_encode(x):
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

    
}