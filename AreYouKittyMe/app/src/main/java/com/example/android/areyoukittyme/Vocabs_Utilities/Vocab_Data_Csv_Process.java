package com.example.android.areyoukittyme.Vocabs_Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by haoyuxiong on 4/12/17.
 */

public class Vocab_Data_Csv_Process {

    public static Scanner ReadInVocabData(InputStream file){


        Scanner fScanner=null;



            //try {
                //File f = new File(file);
                fScanner = new Scanner(file, String.valueOf(Charset.forName("ISO-8859-1")));


            //} catch (FileNotFoundException e) {
                //System.out.println("File not found, please try again!");
                //return null;
            //}


        return fScanner;
    }

    public static ArrayList<ArrayList<String>> processInData(Scanner data) throws UnsupportedEncodingException {
        System.out.println(data.nextLine());
        

        ArrayList<ArrayList<String>> processedData = new ArrayList<ArrayList<String>>();

        while (data.hasNextLine()) {
            String line = new String(data.nextLine().getBytes("ISO-8859-1"), "ISO-8859-15");
            String[] parts = line.split("\",\"");
            ArrayList<String> row = new ArrayList<String>();

            for (int i = 0; i < parts.length; i++) {

                row.add(parts[i]);
                System.out.println(row.size());
            }
            processedData.add(row);
            System.out.println(row.size());
        }
        return processedData;
    }
}




