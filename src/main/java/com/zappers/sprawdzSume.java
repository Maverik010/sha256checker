package com.zappers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;

import de.vandermeer.asciitable.*;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;


public class sprawdzSume {
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
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
                String[] names = {
                    args[0].substring(args[0].lastIndexOf("/") + 1), args[1].substring(args[1].lastIndexOf("/") + 1),
                    encoded, hashFromFile, "PRAWIDŁOWY", "NIEPRAWIDŁOWY"
                };
                boolean flag = encoded.equals(hashFromFile);
            // System.out.println(consoleColors.GREEN+"Hash prawidłowy");

            AsciiTable at = new AsciiTable();
                at.addRule();
                at.addRow("Nazwa Pliku", "Hash").setTextAlignment(TextAlignment.CENTER);
                at.addRule();
                at.addRow("Plik: "+names[0], encoded).setTextAlignment(TextAlignment.LEFT);
                at.addRule();
                at.addRow("Plik z hashem: "+names[1], hashFromFile).setTextAlignment(TextAlignment.LEFT);
                at.addRule();
    
                String rend = at.render();
                System.out.println(rend);
                if(flag){
                    System.out.println("\n"+consoleColors.GREEN+"Prawidłowy hash.");
                }   else    {
                    System.out.println("\n"+consoleColors.RED+"Nieprawidłowy hash!");
                }

                System.out.println(consoleColors.RESET);
            }catch (Exception e) {
            System.err.println("Wyjątek: " + e);
        }
        }
    }

