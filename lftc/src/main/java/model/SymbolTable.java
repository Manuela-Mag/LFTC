package model;

import utils.Pair;

import java.util.*;

public class SymbolTable {
    public static final List<String> separators = Collections.unmodifiableList(Arrays.asList(";", " ", ","));
    public static final List<String> operators = Collections.unmodifiableList(Arrays.asList("+", "-", "*", "/", ">", "<", "<=", "=>", "=", "==", "!="));
    public static final List<String> reservedWords = Collections.unmodifiableList(Arrays.asList("readFromKeyboard", "repeat", "until", "char", "const", "int", "if", "else", "check", "then", "write", "while"));

    public static final List<String> constants = Collections.unmodifiableList(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
    private Hashtable<String, String> hashtable;
    private List<Pair<String, String>> pif;
    private List<String> tokens;

    private final int counter = 0;
    private Boolean constant = false;

    private List<String> inputData;

    public SymbolTable(List<String> content, Boolean con){
        this.inputData = content;
        this.constant = con;
        this.getTokens();
    }

    public void getTokens() {
        this.hashtable = new Hashtable<>(inputData.size());
        int index = 0;
        if (this.constant) {
            while (index < inputData.size()-1) {
                if (constants.contains(inputData.get(index)) && !separators.contains(inputData.get(index)) && !operators.contains(inputData.get(index)) && !reservedWords.contains(inputData.get(index))) {
                    int hash = index % inputData.size();
                    if(!hashtable.isEmpty()) {
                        while (this.hashtable.containsKey(hash)) {
                            hash++;
                        }
                    }
                    hashtable.put(String.valueOf(hash), inputData.get(index));
                }
                index++;
            }
        }
        else{
            while (index < inputData.size()-1) {
                if (!constants.contains(inputData.get(index)) && !separators.contains(inputData.get(index)) && !operators.contains(inputData.get(index)) && !reservedWords.contains(inputData.get(index))) {
                    int hash = index % inputData.size();
                    if(!this.hashtable.isEmpty()) {
                        while (this.hashtable.containsKey(hash)) {
                            hash++;
                        }
                    }
                    if(!hashtable.containsValue(inputData.get(index)))
                        hashtable.put(String.valueOf(hash), inputData.get(index));
                }
                index++;
            }
        }
    }

    public String getElementFromHashTable(String key){
        return hashtable.get(key);
    }

    public void addElementToHashTable(String value){
        int index = 0;
        boolean added = false;
        while(index < hashtable.size()){
            int hash = index % inputData.size();
            while(!hashtable.get(String.valueOf(hash)).isEmpty()){
                hash ++;
            }
            if(!hashtable.containsValue(inputData.get(index)))
                hashtable.put(String.valueOf(hash), inputData.get(index));
        }
    }

    public Hashtable<String, String> getHashtable() {
        return hashtable;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "hashtable=" +  hashtable +
                '}';
    }
}
