import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

public class PepperEncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;
    private static final int PEPPER_LENGTH = 8; // Number of random characters to add

    // Generates a random AES secret key
    public static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }

    // Adds random characters (pepper) to the beginning of the plaintext
    public static String addPepper(String data) {
        SecureRandom random = new SecureRandom();
        StringBuilder pepper = new StringBuilder(PEPPER_LENGTH);
        for (int i = 0; i < PEPPER_LENGTH; i++) {
            // Append a random alphanumeric character
            pepper.append((char) ('a' + random.nextInt(26)));
        }
        return pepper + data;
    }

    // Removes the pepper from the decrypted data
    public static String removePepper(String data) {
        return data.substring(PEPPER_LENGTH);
    }

    // Encrypts data with AES, adding pepper before encryption
    public static String encrypt(String data, SecretKey key) throws Exception {
        // Add pepper to the plaintext
        String pepperedData = addPepper(data);

        // Encrypt the peppered data
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(pepperedData.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypts AES-encrypted data and removes the pepper
    public static String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        // Remove the pepper from the decrypted data
        String decryptedString = new String(decryptedBytes);
        return removePepper(decryptedString);
    }

    public static void main(String[] args) {
        try {
            String sensitiveData = "Sensitive Data From Database";

            // Generate the secret key
            SecretKey key = generateSecretKey();

            // Encrypt data
            String encryptedData = encrypt(sensitiveData, key);
            System.out.println("Encrypted Data: " + encryptedData);

            // Decrypt data
            String decryptedData = decrypt(encryptedData, key);
            System.out.println("Decrypted Data: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}