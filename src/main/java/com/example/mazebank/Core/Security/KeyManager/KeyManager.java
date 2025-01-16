package com.example.mazebank.Core.Security.KeyManager;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class KeyManager {

        private static final String ALGORITHM = "AES";
        private static final String KEY_FILE = "secret.key";

        public static void generateAndSaveKey() throws Exception {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();

            try (FileOutputStream fos = new FileOutputStream(KEY_FILE)) {
                fos.write(Base64.getEncoder().encode(secretKey.getEncoded()));
                System.out.println("[LOG][KeyManager] - Key saved to: " + new File(KEY_FILE).getAbsolutePath());
            }
            catch (Exception e) {
                System.out.println("[LOG][KeyManager] - Key save failed");
            }
        }

    public static SecretKey loadKey() throws IOException {
            try {
                byte[] keyBytes = Files.readAllBytes(new File(KEY_FILE).toPath());
                byte[] decodedKey = Base64.getDecoder().decode(keyBytes);
                return new SecretKeySpec(decodedKey, ALGORITHM);
            }
            catch (Exception e) {
                System.out.println("[LOG][KeyManager] - Key load failed");
                return null;
            }


    }
}
