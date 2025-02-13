import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    // Generate the master key to encrypt other keys
    public static SecretKey generateMasterKey(String password) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyGen.init(KEY_SIZE, secureRandom);
        return keyGen.generateKey();
    }

    // Generate a data key to encrypt data
    public static SecretKey generateDataKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(KEY_SIZE);
        return keyGen.generateKey();
    }

    // Encrypt the data key with the master key
    public static String wrapDataKey(SecretKey dataKey, SecretKey masterKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.WRAP_MODE, masterKey);
        byte[] wrappedKey = cipher.wrap(dataKey);
        return Base64.getEncoder().encodeToString(wrappedKey);
    }

    // Unwrap the encrypted data key using the master key
    public static SecretKey unwrapDataKey(String wrappedKeyStr, SecretKey masterKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.UNWRAP_MODE, masterKey);
        byte[] wrappedKey = Base64.getDecoder().decode(wrappedKeyStr);
        return (SecretKey) cipher.unwrap(wrappedKey, ALGORITHM, Cipher.SECRET_KEY);
    }

    // Encrypt data using the data key
    public static String encryptData(String data, SecretKey dataKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, dataKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt data using the data key
    public static String decryptData(String encryptedData, SecretKey dataKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, dataKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            String masterPassword = "SuperSecureMasterPassword";
            String sensitiveData = "Sensitive Data From Database";

            // Generate the master key
            SecretKey masterKey = generateMasterKey(masterPassword);

            // Generate a data key
            SecretKey dataKey = generateDataKey();

            // Encrypt (wrap) the data key with the master key
            String wrappedDataKey = wrapDataKey(dataKey, masterKey);
            System.out.println("Wrapped Data Key: " + wrappedDataKey);

            // Use the data key to encrypt sensitive data
            String encryptedData = encryptData(sensitiveData, dataKey);
            System.out.println("Encrypted Data: " + encryptedData);

            // To decrypt, unwrap the data key with the master key
            SecretKey unwrappedDataKey = unwrapDataKey(wrappedDataKey, masterKey);

            // Use the unwrapped data key to decrypt the sensitive data
            String decryptedData = decryptData(encryptedData, unwrappedDataKey);
            System.out.println("Decrypted Data: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
