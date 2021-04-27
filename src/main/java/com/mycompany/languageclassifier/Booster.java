/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.languageclassifier;
import java.util.HashMap;
/**
 *
 * @author Imara
 */
public class Booster implements java.io.Serializable {
    HashMap<DTree, Double> trees;
    
    public Booster(){
        trees = new HashMap<DTree, Double>();
    }
    
    public void add_tree(DTree stump){
        trees.put(stump, Double.NaN);
    }
    
    public void add_weight(DTree stump, double w){
        trees.put(stump, w);
    }
    
    public int classify(String[] e){
        int clas = 0;
        int i = 0;
        for (HashMap.Entry<DTree, Double> t : trees.entrySet()){
            if(i==14)
                break;
            clas += t.getKey().solve(e[i])*t.getValue();
            i+=1;
            
        }
        return clas;
    }
    
}
