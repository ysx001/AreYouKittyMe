package com.example.android.areyoukittyme.Vocabs_Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by haoyuxiong on 4/12/17.
 */

public class Vocab_Data_Csv_Process {

    public static Scanner ReadInVocabData(String filename){


        Scanner fScanner;

        do {

            try {
                File f = new File(filename);
                fScanner = new Scanner(f);
                break;

            } catch (FileNotFoundException e) {
                System.out.println("File not found, please try again!");
                System.out.print("Filename: ");
            }
        } while (true);

        return fScanner;
    }

    public static ArrayList<ArrayList<String>> processInData(Scanner data){




            data.nextLine();
        

        ArrayList<ArrayList<String>> processedData = new ArrayList<ArrayList<String>>();

        while (data.hasNextLine()) {
            String line = data.nextLine();
            String[] parts = line.split(",");

            for (int i = 0; i < parts.length; i++) {
                processedData.get(i).add(parts[i]);

            }

        }


        return processedData;
    }


}




