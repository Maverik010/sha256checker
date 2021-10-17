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

        System.out.println(consoleColors.BLUE+"Checksum checker (SHA256) 0.5v"+consoleColors.RESET + "\n");

        if (args.length != 2) {
            System.out.println(consoleColors.YELLOW + "Użycie: sprawdzSume <ścieżka_do_pliku> <ścieżka_do_sumy_shaDigest>" + consoleColors.RESET);
        } else try {


                FileInputStream sourceFile = new FileInputStream(args[0]);      // ścieżka do pliku źródłowego
                FileInputStream hashFile = new FileInputStream(args[1]);      // ścieżka do pliku .sha256
                MessageDigest digest = MessageDigest.getInstance("SHA-256");

                /*String originalString = sourceFile.toString();
                byte[] encodedhash = digest.digest(
                        originalString.getBytes(StandardCharsets.UTF_8));*/
            byte[] encodedhash = digest.digest(sourceFile.readAllBytes());
                String encoded = bytesToHex(encodedhash);                    //String z hexem hasha
                String hashFromFile = "";                  //Hash z pliku
                BufferedReader br =
                        new BufferedReader( new InputStreamReader(hashFile, StandardCharsets.UTF_8 ));
                StringBuilder sb = new StringBuilder();
                String line;
                while(( line = br.readLine()) != null ) {
                    sb.append(line,0,64);
                    hashFromFile = sb.toString();
                }
                br.close();
                sourceFile.close();
                //  System.out.println(encoded + "   " + encoded.length());
                //  System.out.println(hashFromFile + "   " + encoded.length());
                String[] names = {
                    args[0].substring(args[0].lastIndexOf("/") + 1), args[1].substring(args[1].lastIndexOf("/") + 1),
                    encoded, hashFromFile
                };

                
                if (encoded.equals(hashFromFile)) {
                    System.out.println(consoleColors.GREEN+"Hash prawidłowy");
                } else {
                    System.out.println(consoleColors.RED+"Hash nieprawidłowy");
                }
                System.out.print(consoleColors.RESET);
            }catch (Exception e) {
            System.err.println("Wyjątek: " + e);
        }
        }
    }

