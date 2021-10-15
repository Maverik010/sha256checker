import java.io.*;
import java.security.*;


public class sprawdzSume {
    
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(consoleColors.YELLOW+"Użycie: sprawdzSume <ścieżka_do_pliku> <ścieżka_do_sumy_shaDigest>"+consoleColors.RESET);
        }
        else try {

            MessageDigest shaDigest = MessageDigest.getInstance("SHA-256"); // zainicjuj algorytm SHA-256
            FileInputStream sourceFile = new FileInputStream(args[0]);      // ścieżka do pliku źródłowego
            FileInputStream hashFile = new FileInputStream(args[1]);      // ścieżka do pliku .sha256
            //  System.out.print(args[0]);
            //  System.out.print(" "+args[1]);
            
        
        byte[] data = new byte[1024]; // przydzielenie długości sha-256 zakresu pamięci
        int read ; // iterator

        while ((read = sourceFile.read(data)) != -1) {
            shaDigest.update(data, 0, read);
        }
        sourceFile.close();

        byte[] hashBytes = shaDigest.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
          sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        hashFile.close();
        String fileHash = sb.toString();
    String hash = ""; //string do hashFileContent
        sb.delete(0, sb.length());
        StringBuilder hashFilecontent = new StringBuilder();

        while(hashFile.available() != 0){
            hashFilecontent.append(hashFile);
            hash = hashFilecontent.toString();
        }
            if(hash.equals(fileHash)){
                System.out.println(consoleColors.YELLOW+hashFilecontent);
                System.out.println(consoleColors.YELLOW+shaDigest);
                System.out.println(consoleColors.GREEN+"Sygnatura (shaDigest) jest OK"+consoleColors.RESET);
            }else{
                System.out.println(consoleColors.YELLOW+hashFilecontent);
                System.out.println(consoleColors.YELLOW+shaDigest);
                System.out.println(consoleColors.RED+"Sygnatura (shaDigest) pliku się nie zgadza z zadeklarowaną!"+consoleColors.RESET);
            }
        


        } catch (Exception e) {
            System.err.println("Wyjątek: " + e);
        }
}
}
