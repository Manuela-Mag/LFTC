package model;

import utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pif {
    public static final List<String> separators = Collections.unmodifiableList(Arrays.asList(";", " ", ","));
    public static final List<String> operators = Collections.unmodifiableList(Arrays.asList("+", "-", "*", "/", "<", ">", "<=", "=>", "=", "==", "!="));
    public static final List<String> reservedWords = Collections.unmodifiableList(Arrays.asList("readFromKeyboard", "repeat", "until", "char", "const", "int", "if", "else", "check", "then", "write", "while"));
    public static final List<String> constants = Collections.unmodifiableList(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

    private List<Pair<String, String>> pif;

    private List<String> inputData;

    public Pif(List<String> input){
        this.inputData = input;
        this.pif = new ArrayList<Pair<String, String>>();
        getTokens();
    }


    public void getTokens(){
        int index = 0;
            while (index < inputData.size()) {
                if(separators.contains(inputData.get(index)))
                    pif.add(index, new Pair<String, String>(inputData.get(index), "0"));
                else if(reservedWords.contains(inputData.get(index)))
                    pif.add(index, new Pair<String, String>(inputData.get(index), "3"));
                else if(operators.contains(inputData.get(index)))
                    pif.add(index, new Pair<String, String>(inputData.get(index), "1"));
                else if(constants.contains(inputData.get(index)))
                    pif.add(index, new Pair<String, String>(inputData.get(index), "2"));
                else
                    pif.add(index, new Pair<String, String>(inputData.get(index), "4"));
                index++;
            }
    }

    public void addToPif(String value, String key){
        pif.add(new Pair<>(value, key));
    }

    @Override
    public String toString() {
        return "Pif{" +
                "pif=" + pif +
                '}';
    }
}
