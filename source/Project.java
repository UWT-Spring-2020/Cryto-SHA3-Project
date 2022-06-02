package source;

import java.lang.Cloneable;

class Project {

    public static void main(String[] args) {
        System.out.println("Hello World! from Crypto Project");

//        cSHAKE256(public_key, 256, "", "key fingerprint");
//        cSHAKE128(email_contents, 256, "", "email signature"),
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
    private static void KMACXOF256_KECCAK(String k, String x, int l, String s) {


//         newX = bytepad(encode_string(K), 136) || X || right_encode(0);
//         T = bytepad(encode_string"KMAC") || encode_string(S), 136);
//         return KECCAK[512](T || newX || 00, L);

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
    private static void KMACXOF256_cSHAKE(String k, String x, int l, String s) {

//         newX = bytepad(encode_string(K), 136) || X || right_encode(0);
//         return cSHAKE256(newX, L, "KMAC", S);

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
    private static void cSHAKE256(String x, int l, String n, String s) {

        //        if N = "" and S = ""
        //        return SHAKE256(X,L);
        //        else
        //        return KECCAK[512](bytepad(encode_string(N) || encode_string(S), 136) || X || 00, L)

    }


    /**
     * bytepad(X, w):
     * Validity Conditions: w > 0
     *
     * @param X
     * @param w
     */
    private static void bytepad(int X, int w) {

        //        z = left-encode(w) || X
        //        while len(z) mod 8 != 0
        //        z = z || 0
        //        while (len(z)/8) mod w != 0
        //        z = z || 00000000
        //        return z

    }

    private static void encode_string(String S) {

        //        encode_string(S):
        //        # Validity Conditions: 0 <= len(S) < 2^2040
        //
        //        return left_encode(len(S)) || S
    }

    private static void left_encode(int x) {
        //        left_encode(x):
        //        Validity Conditions:  0 <= x < 2^2040
        //
        //        Let n be the smallest positive integer for which 2 ^ 8n > x
        //        Let x1, x2, ..., xn be the base-256 encoding of x satisfying:
        //        x = (Rheiman Sum) 2^8(n - i) xi, for i = 1 to n
        //        Let Oi = enc8(xi), i = 1 to n
        //        Let O0 = enc8(n)
        //        Return O = O0 || Oi || ... || On-1 || On

    }

    private static void right_encode(int x) {
        //        right_encode(x):
        //        # Validity Conditions: 0 <= x < 2^2040
        //
        //        # Let n be the smallest positive integer for which 2^8n > x
        //        # Let x1, x2, ..., xn be the base-256 encoding of x satisfying:
        //        #	x = (Rheiman Sum) 2^8(n-1) xi, for i = 1 to n
        //        # Let Oi = enc8(x1), for i = 1 to n
        //        # Let On+1 = enc8(n)
        //        # Return O = O1 || O2 || ... || On || On+1
    }

//        # KECCAK ?? Import from java.lang.Cloneable

}