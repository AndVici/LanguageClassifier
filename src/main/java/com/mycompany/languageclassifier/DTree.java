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
public class DTree implements java.io.Serializable{
    private String root;
    private DTree False;
    private DTree True;
    
    public DTree(String att){
        root = att;
        False =null;
        True = null;
    }
    
    public void addFalse(DTree f){
        False = f;
    }
    
    public void addTrue(DTree T){
        True=T;
    }
    
    public boolean isChild(){
        if(False==null || True==null)
            return true;
        return false;
    }
    
    public DTree getTrue(){
        return True;
    }
    
    public DTree getFalse(){
        return False;
    }
    
    public String getRoot(){
        return root;
    }
    
    public String classify(String[] e){
        if(isChild())
            return root;
        int x = Integer.parseInt(root) - 1;
        if(e[x]=="false")
            return False.classify(e);
        return True.classify(e);
    
    }
    public int solve(String i){

        String x = (i.equals("false")) ? False.getRoot() : True.getRoot();
        return (x.equals("nl")) ? -1 : 1;
    }
    
    
}
