/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.languageclassifier.Training;

import java.lang.Math.*;
import java.util.ArrayList;
import java.io.*;
import com.mycompany.languageclassifier.DTree;
import com.mycompany.languageclassifier.Booster;
/**
 *
 * @author Imara
 */


public class Train{
    ArrayList<String[]> array;
    
    public Train(String file){
        array = new ArrayList<String[]>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while(line != null){
                String temp[] = line.split("\\r?\\n")[0].split(" ");
                String lang = (temp[0]=="nl")? "-1" : "1";
                String x[] = new String[temp.length];
                for(int i=0; i<temp.length-1; i++)
                    x[i] = temp[i+1];
                x[temp.length-1] = lang;
                array.add(x);
                line = in.readLine();
            }
        }catch(IOException e){
            System.out.print(e);
        }
    }
    
    int abCount(){
        int aCount = 0;
        
        for(String[] i : array){
            if(i[13].equals("-1") || i[0].equals("nl"))
                aCount+=1;
        }
        return aCount;
    }
    
    double attribx( int x, double w[]){
        double ta = 0, fa = 0;
        ArrayList<String[]> t = new ArrayList<String[]>(),
                f=new ArrayList<String[]>();
        int ii = 0;
        for(String[] i: array){
            if(i[x].equals("true")){
                t.add(i);
                if(i[13].equals("-1"))
                    ta+=w[ii];
            }
            else if(i[x].equals("false")){
                f.add(i);
                if(i[13].equals("-1"))
                    fa+=w[ii];
            }
            ii++;
        }
        if(t.isEmpty())
            return 0;
        double a = ta/t.size();
        return a / (t.size()/array.size());
    }
   
    double entropy(double a){
        if(a==0)
            return Double.NEGATIVE_INFINITY;
        if(a==1)
            return 0;
        return -1*(a * (Math.log(a)/Math.log(2)) + 
                (1-a)*(Math.log(a)/Math.log(2)));
    }
    
    double attrib(int x, ArrayList<String[]> t,
            ArrayList<String[]> f){
        int ta=0, fa=0;
        for(String[] i: array){
            if(i[x].equals("true")){
                t.add(i);
                if(i[0].equals("nl"))
                    ta ++;
            }
            if(i[x].equals("false")){
                f.add(i);
                if(i[0].equals("nl"))
                    fa ++;
            }
        }
        double a, b;
        if(t.isEmpty())
            a = 0;
        else
            a = ta/t.size();
        if(f.isEmpty())
            b = 0;
        else
            b = fa/f.size();
        return 1-(t.size()/array.size()) * entropy(a) +
                (f.size()/array.size()) * entropy(b);
    }
    
    double[] NORMALIZE(double w[]){
        double sum = 0;
        double w2[] = new double[w.length];
        for(double i: w)
            sum+=i;
        for(int i=0;i<w.length;i++)
            w2[i] = w[i]/sum;
        return w2;
    }
    
    DTree hx(double w[], int A){
        double p = attribx(A,w);
        String labelT, labelF;
        if(p > 1-p){
            labelT = "nl";
            labelF = "en";
        }
        else{
            labelT = "en";
            labelF = "nl";
        }
        DTree stump = new DTree(Integer.toString(A));
        stump.addFalse(new DTree(labelF));
        stump.addTrue(new DTree(labelT));
        return stump;
    }
    
    public Booster adaBoost(int K){
        int N = array.size();
        
        double w[] = new double [N];
        for(int i=0; i <N; i++)
            w[i] = 1/N;
        Booster h = new Booster();
        double z[] = new double[K];
        for(int i=0; i<K; i++)
            z[i] = 0;
        
        for(int k=0; k <K; k++){
            double error =0;
            DTree temp = hx(w, k);
            for(int j=0; j<N;j++){
                int x = Integer.parseInt(array.get(j)[14]) +
                    temp.solve(array.get(j)[k]);
                error += (x < 0)? w[j] : 0;
            }
            for(int j=0; j<N;j++){
                int x = Integer.parseInt(array.get(j)[14]) +
                    temp.solve(array.get(j)[k]);
                w[j]= (x > 0)? w[j]*error/(1-error) : w[j];
            }
            w = NORMALIZE(w);
            
            if(error==0)
                z[k] = 1;
            else if(error==1)
                z[k] = 0;
            else
                z[k] = Math.log((1-error)/error);
            h.add_weight(temp,z[k]);
                       
        }
        return h;
    }
   
    

}
