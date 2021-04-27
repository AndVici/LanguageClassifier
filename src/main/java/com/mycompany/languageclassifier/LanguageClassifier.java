/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.languageclassifier;

import com.mycompany.languageclassifier.Training.Train;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author Imara
 */
public class LanguageClassifier {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        Booster h = new Train("train.txt").adaBoost(15);
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("AB.ser"));
            out.writeObject(h);
            out.close();
        }catch(IOException e){
            System.out.println(e);
        }*/
        // TODO code application logic here

        Booster adaB = null;
//new Train("train.txt").adaBoost(15);
        try{
            ObjectInputStream out = new ObjectInputStream(new FileInputStream("AB.ser"));
            adaB = (Booster)out.readObject();
            out.close();
        }catch(IOException e){
            System.out.println(e);
        }catch(ClassNotFoundException e){
            System.out.println(e);
        }
        Analysis A;
        ArrayList<String[]> vAB = new ArrayList<String[]>();
        try {
            BufferedReader in = new BufferedReader(new FileReader("exa.txt"));
            String line = in.readLine();
            while(line != null){
                A = new Analysis(line);
                vAB.add(A.line);
                line = in.readLine();
            }
        }catch(IOException e){
            System.out.print(e);
        }
        
        for(String[] e : vAB){

            int ans = adaB.classify(e);
            if(ans < 0)
                System.out.println("Dutch");
            else
                System.out.println("English");
        }
        
        
    }
    
}
