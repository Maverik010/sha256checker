import java.io.*;
import java.security.*;
import java.util.Scanner;

public class sprawdzSume {
    
    public static void main(String[] args) {

        /* Tekst powitalny */

        System.out.println(consoleColors.CYAN_BOLD+consoleColors.BLUE_BACKGROUND_BRIGHT+"SHA256checker 0.1v Alpha"+consoleColors.RESET);

        if (args.length != 2) {
            System.out.println(consoleColors.YELLOW+"Użycie: sprawdzSume <ścieżka_do_pliku> <ścieżka_do_sumy_shaDigest>"+consoleColors.RESET);
        }
        else try {

            MessageDigest shaDigest = MessageDigest.getInstance("SHA-256"); // zainicjuj algorytm SHA-256
            FileInputStream sourceFile = new FileInputStream(args[0]);      // ścieżka do pliku źródłowego
            Scanner hashFile = new Scanner(args[1]).useDelimiter("\\p{javaWhitespace}+");      // Zawartość pliku zczytana do pierweszego znaku spacji <sha256>\w
            String parseHash = hashFile.nextLine();
            hashFile.close();
        
        byte[] data = new byte[1024]; // tablica bitów
        int read; // iterator

        while ((read = sourceFile.read(data)) != -1) {
            shaDigest.update(data, 0, read);
        }
        sourceFile.close();

        byte[] hashBytes = shaDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
          sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        String fileHash = sb.toString();
        String hash = ""; //string do hashFileContent
        sb.delete(0, sb.length());

        Boolean diff = false;

        for (int x = 0; x < parseHash.length(); x++){
            if(parseHash.charAt(x) != fileHash.charAt(x)){
                diff = true;
                break;
            }
        }

            if(diff==false){
                System.out.println(consoleColors.YELLOW+fileHash);
                System.out.println(consoleColors.YELLOW+parseHash);
                System.out.println(consoleColors.GREEN+"Sygnatura (shaDigest) jest OK"+consoleColors.RESET);
            }else{
                System.out.println(consoleColors.YELLOW+fileHash);
                System.out.println(consoleColors.YELLOW+parseHash);
                System.out.println(consoleColors.RED+"Sygnatura (shaDigest) pliku się nie zgadza z zadeklarowaną!"+consoleColors.RESET);
            }
        

        } catch (Exception e) {
            System.err.println("Wyjątek: " + e);
        }
}
}
