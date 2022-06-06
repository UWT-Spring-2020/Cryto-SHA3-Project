import org.junit.Test;
import org.junit.Assert;

import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileToHash {

    private final String expectedHash = "";
    private final String expectedHash2 = "";


    // given
    private final File file = new File("resources/dummy.pdf");
    private final InputStream inputStream = new FileInputStream(file);

    private final SHA3 myObj = new SHA3();

    @Test
    public void testFileToHashKMACXOF256() throws IOException {
        
        // when
        byte[] hashText = myObj.KMACXOF("", inputStream, 256, "");

        // then
        Assert.assertEquals(expectedHash.getBytes(), hashText);
    }

    @Test
    public void testFileToHashCShake256() throws IOException {

        // when
        byte[] hashText2 = myObj.cSHAKE("", inputStream, 256, "");

        // then
        Assert.assertEquals(expectedHash2.getBytes(), hashText2);
    }
}
