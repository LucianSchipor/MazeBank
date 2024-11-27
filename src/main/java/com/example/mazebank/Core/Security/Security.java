package com.example.mazebank.Core.Security;

import com.example.mazebank.Core.Models.Model;
import com.example.mazebank.Core.Models.UserLoggedIn;
import com.example.mazebank.Core.Users.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import de.taimos.totp.TOTP;
import javafx.scene.image.Image;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.net.URLEncoder;
import java.security.SecureRandom;

public class Security {
private static Security instance;

    public Security() {
    }

    public static synchronized Security getInstance() {
        if (instance == null) {
            instance = new Security();
        }
        return instance;
    }

    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    //Used when 2FA is disabled (no secret key in db)
    public static String generateQR_enable_2FA(User user) throws IOException, WriterException {
        String secretKey = generateSecretKey();
        String email = user.getEmail();
        String companyName = "Maze Bank";
        String barCodeUrl = Security.getGoogleAuthenticatorBarCode(secretKey, email, companyName);
//        startSecurityThread(barCodeUrl);

//        Model.getInstance().getViewFactory().showQRCode(barCodeUrl);
        System.out.println(barCodeUrl);
    return barCodeUrl;
    }

    public void verifyQRCode() throws IOException, WriterException {

    }

    private void startSecurityThread(String secretKey) throws IOException, WriterException {
        Thread securityThread = new Thread(() -> {
            String lastCode = null;
            while (true) {
                String code = Security.getTOTPCode(secretKey);
                if (!code.equals(lastCode)) {
                    System.out.println(code);
                }
                lastCode = code;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("[LOG] - " + e.getMessage());
                    break;
                }
                ;
            }
        });
        securityThread.setDaemon(true);
        securityThread.start();
    }
    public static Image createQRCode(String barCodeData)
            throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(generateQR_enable_2FA(UserLoggedIn.getInstance().getLoggedInUser()), BarcodeFormat.QR_CODE,
                300, 300);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
            return new javafx.scene.image.Image(inputStream);
        }
    }

    public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}
