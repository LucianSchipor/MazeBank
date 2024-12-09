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
import javafx.util.Pair;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.io.*;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;

public class Security {
    private static Security instance;

    private Pair<String, String> authCodes = new Pair<>("", "");
    private Boolean FA_Permision = false;
    private Boolean FA_Enabled = false;
    private boolean FA_Verified = false;
    private String FA_Key = "";
    private LocalDateTime FA_Verification_Time;

    public void setFA_Enabled(Boolean FA_Enabled) {
        this.FA_Enabled = FA_Enabled;
    }

    public boolean isFA_Enabled() {
        return FA_Enabled;
    }


    public boolean isFA_Verified() {
//    Pe baza timpului cand s-a efectuat ultima verificare, stocat in BD in 2FA_Verification_Time
//    Vad daca aceasta verificare a fost factuta acum mai mult de 15 minute.
//    Daca da, cer iar sa-si verifice codul

        return Duration.between(FA_Verification_Time, LocalDateTime.now()).toMinutes() <= 15;
    }

    public void setFA_Verified(boolean FA_Verified) {
        this.FA_Verified = FA_Verified;
        setFA_Verification_Time(LocalDateTime.now());
    }

    public void setFA_Enabled(boolean FA_Enabled) {
        this.FA_Enabled = FA_Enabled;
    }

    public String getFA_Key() {
        return FA_Key;
    }

    public void setFA_Key(String FA_Key) {
        this.FA_Key = FA_Key;
    }

    public void setFA_Verification_Time(LocalDateTime FA_Verification_Time) {
        this.FA_Verification_Time = FA_Verification_Time;
    }
    public Security() throws IOException, WriterException {
    }

    public static synchronized Security getInstance() throws IOException, WriterException {
        if (instance == null) {
            instance = new Security();
        }
        return instance;
    }


    public Boolean verifyOTP(String otp) throws IOException, WriterException {
        if (otp.equals(getTOTPCode(Security.getInstance().authCodes.getKey()))) {
            System.out.println("[LOG][Security] - OTP Code is valid for this User");
            return true;
        } else {
            System.out.println("[LOG][Security] - Invalid OTP Code");
            return false;
        }
    }

    private static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        return base32.encodeToString(bytes);
    }

    private static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public void verifyQRCode() throws IOException, WriterException {

    }

    public void startSecurityThread(String secretKey) throws IOException, WriterException {
        Thread securityThread = new Thread(() -> {
            String lastCode = null;
            while (true) {
                String code = Security.getTOTPCode(secretKey);
                if (!code.equals(lastCode)) {
                    System.out.println("[LOG][Google Auth] - Key: " + secretKey + "Code: " + code);
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
        BitMatrix matrix = new MultiFormatWriter().encode(Security.getInstance().getAuthCodes().getValue(), BarcodeFormat.QR_CODE,
                300, 300);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            MatrixToImageWriter.writeToStream(matrix, "png", out);

            ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
            return new javafx.scene.image.Image(inputStream);
        }
    }

    private static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public Pair<String, String> getAuthCodes() {
        return authCodes;
    }

    public void setAuthCodes(String secretKey) throws IOException, WriterException {
        String email = UserLoggedIn.getInstance().getLoggedInUser().getEmail();
        String companyName = "Maze Bank";
        String barCodeUrl = Security.getGoogleAuthenticatorBarCode(secretKey, email, companyName);
        authCodes = new Pair<>(secretKey, barCodeUrl);
    }

    public void setAuthCodes() throws IOException, WriterException {
        String secretKey = generateSecretKey();
        String email = UserLoggedIn.getInstance().getLoggedInUser().getEmail();
        String companyName = "Maze Bank";
        String barCodeUrl = Security.getGoogleAuthenticatorBarCode(secretKey, email, companyName);
        authCodes = new Pair<>(secretKey, barCodeUrl);
    }

    public Boolean getFA_Permision() {
        return FA_Permision;
    }

    public void setFA_Permision(Boolean FA_Permision) {
        this.FA_Permision = FA_Permision;
    }
}
