import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;


public class sprawdzSume {
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(consoleColors.YELLOW + "Użycie: sprawdzSume <ścieżka_do_pliku> <ścieżka_do_sumy_shaDigest>" + consoleColors.RESET);
        } else try {

            if (args.length != 2) {
                System.out.println("Użycie: sprawdzSume <ścieżka_do_pliku> <ścieżka_do_sumy_shaDigest>");
            }else{
                FileInputStream sourceFile = new FileInputStream(args[0]);      // ścieżka do pliku źródłowego
                FileInputStream hashFile = new FileInputStream(args[1]);      // ścieżka do pliku .sha256
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                String originalString = sourceFile.toString();
                byte[] encodedhash = digest.digest(
                        originalString.getBytes(StandardCharsets.UTF_8));
                String encoded = bytesToHex(encodedhash);                    //String z hexem hasha
                String hashFromFile = "";                  //Hash z pliku

                BufferedReader br =
                        new BufferedReader( new InputStreamReader(hashFile, StandardCharsets.UTF_8 ));
                StringBuilder sb = new StringBuilder();
                String line;
                while(( line = br.readLine()) != null ) {
                    sb.append( line );
                    hashFromFile = sb.toString();
                }
                br.close();
                sourceFile.close();
                System.out.println(encoded + "   " + encoded.length());
                System.out.println(hashFromFile + "   " + encoded.length());
                if (encoded.equals(hashFromFile)) {
                    System.out.println("Hash prawidlowy");
                } else {
                    System.out.println("Hash zjebany");
                }
            }
        }catch (Exception e) {
            System.err.println("Wyjątek: " + e);
        }
    }
}
