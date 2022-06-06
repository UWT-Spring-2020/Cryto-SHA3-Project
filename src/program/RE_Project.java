import java.io.*;
import java.util.Arrays;

class RE_Project {

// main
/*
 *• [10 points] Compute a plain cryptographic hash of a given file (this
requires implementing and testing cSHAKE256 and KMACXOF256 first).
BONUS: [4 points] Compute a plain cryptographic hash of text input by
the user directly to the app instead of having to be read from a file.
• [10 points] Encrypt a given data file symmetrically under a given
passphrase.
• [10 points] Decrypt a given symmetric cryptogram under a given
passphrase.
BONUS: [4 points] Compute an authentication tag (MAC) of a given file
under a given passphrase.
2. • [10 points] Generate an elliptic key pair from a given passphrase and
write the public key to a file.
BONUS: [4 points] encrypt the private key under the given password and
write it to a file as well.
• [10 points] Encrypt a data file under a given elliptic public key file.
• [10 points] Decrypt a given elliptic-encrypted file from a given
password.BONUS: [4 points] encrypt/decrypt text input by the user directly to the
app instead of having to be read from a file.
• [10 points] Sign a given file from a given password and write the
signature to a file.
• [10 points] Verify a given data file and its signature file under a given
public key file.
BONUS: [4 points] offer the possibility of encrypting a file under the
recipient’s public key and also signing it under the user’s own private key. 
 */

public static void main(String[] args) throws IOException {
// PART 1
// cSHAKE256(FILE) -> HASH 
// KMACXOF256(FILE) -> HASH

// cSHAKE256(TEXT_INPUT) -> HASH
// KMACXOF256(TEXT_INPUT) -> HASH

// ENCRYPT AND DECRYPT:
// cSHAKE256(FILE, PW)
// KMACXOF256(FILE, PW)

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

String s = "Hello World";
byte[] b = "00".getBytes();
byte[] d = "0000000000".getBytes();
byte[] st = s.getBytes();

System.out.println(Arrays.toString(b));
System.out.println(Arrays.toString(d));
System.out.println(st.toString());
System.out.println(Arrays.toString(bytesConcat(b,d,st)));


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


// cSHAKE256

// KMACXOF256

// bytepad

// encode_string

// left_encode

// right_encode

// Keccak
}
