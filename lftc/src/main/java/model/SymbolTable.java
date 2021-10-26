package model;

import utils.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

public class SymbolTable {
    public static final List<String> separators = Collections.unmodifiableList(Arrays.asList(";", " ", ",", "{", "}", "(", ")"));
    public static final List<String> operators = Collections.unmodifiableList(Arrays.asList("+", "-", "%", "++", "*", "/", ">", "<", "<=", "=>", "=", "==", "!="));
    public static final List<String> reservedWords = Collections.unmodifiableList(Arrays.asList("readFromKeyboard", "repeat", "until", "char", "const", "int", "if", "else", "check", "then", "write", "while", "true", "false"));

    public static final List<String> constants = Collections.unmodifiableList(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
    private Hashtable<String, String> hashtable;
    private List<Pair<String, String>> pif;
    private List<String> tokens;
    private boolean error;

    private final int counter = 0;
    private Boolean constant = false;

    private List<String> inputData;
    private String fileName;
    private String tokenFilename;


    public SymbolTable(String filename, String tokenFile, boolean b) {
        this.fileName = filename;
        this.tokenFilename = tokenFile;
        this.constant = b;
        this.inputData = removeWhitespacesAndSeparators(readFromFile());
//        this.inputData = readFromFile();
        this.getTokens();
        //this.hashtable = removeWhitespaces(hashtable);
    }

    public List<String> getInputData(){
        return inputData;
    }
    public void getTokens() {
        System.out.println(inputData);
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
            while(this.hashtable.containsKey(hash)){
                hash ++;
            }
            if(!hashtable.containsValue(value))
                hashtable.put(String.valueOf(hash), value);
            index++;
        }
    }

    public Hashtable<String, String> getHashtable() {
        return hashtable;
    }

    public String readFromFile(){
        String fileContent = "";
        try{
            File file = new File(this.fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            String[] list;
            int  i = 0;
            while ((str = br.readLine()) != null) {
                fileContent += str;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception while reading from file!");
        }
        return fileContent;
    }

    public boolean isIdentifier(String ch){
        Pattern pattern = Pattern.compile("[a-zA-Z]{1,250}");
        if (ch.length() > 8) {
            return false;
        }
        return (pattern.matcher(ch).matches());
    }

    public boolean isConstant(String ch){
        return (isCharacter(ch) || isInteger(ch));
    }

    public boolean isCharacter(String ch){
        Pattern pattern = Pattern.compile("^'[a-zA-Z0-9]'$");
        return (pattern.matcher(ch).matches());
    }

    public List<String> removeWhitespacesAndSeparators(String str){
        List<String> output  = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\s+");

        String[] content = str.split("\\s+");
        for (int i = 0; i < content.length; i++){
            if (!pattern.matcher(content[i]).matches()){
                output.add(content[i]);
            }
        }
        for(int z = 0; z < separators.size(); z++) {
        for(int i = 0; i <output.size(); i++){
                if (output.get(i).contains(separators.get(z)) && !output.get(i).equals(separators.get(z))) {
                    String patter = separators.get(z);
                    if(separators.get(z) == "("){
                        patter = "\\(";
                    }
                    if(separators.get(z) == ")")
                        patter = "\\)";
                    String[] remainings = output.get(i).split(patter);
                    output.remove(i);
                    for (int j = 0; j < remainings.length; j++)
                        output.add(remainings[j]);
                }
            }
        }
        System.out.println("The output is: " + output);
        return output;
    }

    public boolean isInteger(String ch){
        Pattern pattern = Pattern.compile("[-]?\\d+");
        return (pattern.matcher(ch).matches());
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "hashtable=" +  hashtable +
                '}';
    }
}
