/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.languageclassifier;

/**
 *
 * @author Imara
 */
public class Analysis {
    public String[] line;
    private String[] sub = {"de", "een", "het", "the", "to","and"};
    private double[] thresh = {.001,.01,.02,.01,.02,.006,.001};
    private int index =0;
    
    public Analysis(String s){
        line = new String[14];
        if(doubleVowels(s)==true)
            line[index] = "true";
        else
            line[index] = "false";
        index++;
        hasWord(s);
        charCount(s);
    }
    
    void hasWord(String s){

        String[] string = s.split(" ");
        for(int j=0; j<sub.length; j++){
            for(String i:string){
                if(i.equalsIgnoreCase(sub[j])){
                    line[index] = "true";
                    break;
                }
            }
            if(line[index]==null)
                line[index]="false";
            index++;
        }
        
    }
    
    boolean doubleVowels(String string){
        String[] vowes = {"a","e","o"};
        int count = 0;
        
        if(string.contains("aa") || string.contains("ee") 
                || string.contains("oo"))
            return true;
        return false;
    }
    void charCount(String string){
        int e=0, f=0, h=0, j=0, k=0, x=0, y=0, co=string.length();
        
        for(int i=0; i<string.length();i++){
            char charr =string.charAt(i);
            switch(charr){
                case 'ë':
                    e++;
                case 'f':
                    f++;
                case 'h':
                    h++;
                case 'j':
                    j++;
                case 'k':
                    k++;
                case 'x':
                    x++;
                case 'y':
                    y++;
            
            }
            if(!Character.isLetter(charr))
                co = co-1;
            
        }
        
        double arra[] =  {e/co, f/co, h/co, j/co, k/co, x/co,y/co};

        for(int i=0;i<arra.length;i++){
            if(arra[i] < thresh[i])
                line[index] = "false";
            else
                line[index] = "true";
            index++;
        }
        
    }
    
}
