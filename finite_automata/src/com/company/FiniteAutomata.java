package com.company;

import com.company.utils.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class FiniteAutomata {
    private String[] q_states;
    private String[] e_alphabet;
    private List<Pair<String, String>> transitions;
    private String q0_initial_state;
    private String[] trans_values;
    private List<String> finals;

    public String[] getTrans_values() {
        return trans_values;
    }

    public void setTrans_values(String[] trans_values) {
        this.trans_values = trans_values;
    }

    public FiniteAutomata(){
        this.transitions = new ArrayList<>();
        this.finals = new ArrayList<>();
    }

    public void readFromFile(String fileName){
        try{
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            this.q_states = br.readLine().split(" ");
            this.e_alphabet = br.readLine().split(" ");
            this.q0_initial_state = br.readLine();
            this.trans_values = br.readLine().split(" ");
            String line ="";
            while( (line = br.readLine()) != null){
                String[] elems = line.split(" ");
                this.transitions.add(new Pair<>(elems[0], elems[1]));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception while reading from file!");
        }
    }

    public String getQ0_initial_state() {
        return q0_initial_state;
    }

    public void setQ0_initial_state(String q0_initial_state) {
        this.q0_initial_state = q0_initial_state;
    }

    public List<Pair<String, String>> getTransitions() {
        return transitions;
    }


    public String[] getQ_states() {
        return q_states;
    }

    public void setQ_states(String[] q_states) {
        this.q_states = q_states;
    }

    public String[] getE_alphabet() {
        return e_alphabet;
    }

    public void setE_alphabet(String[] e_alphabet) {
        this.e_alphabet = e_alphabet;
    }

    @Override
    public String toString() {
        return "FiniteAutomata{" +
                "q_states=" + Arrays.toString(q_states) +
                ", e_alphabet=" + Arrays.toString(e_alphabet) +
                ", transitions=" + transitions +
                ", q0_initial_state='" + q0_initial_state + '\'' +
                ", trans_values=" + Arrays.toString(trans_values) +
                '}';
    }

    public List<String> getFinals(){
        List<String> keys = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<Pair<String, String>> transition = this.getTransitions();
        for(int i = 0; i < transition.size() ; i++){
            keys.add(transition.get(i).getKey());
        }
        for(int i = 0; i < transition.size() ; i++){
            if(!keys.contains(transition.get(i).getValue()))
                values.add(transition.get(i).getValue());
        }
        this.finals = values;
        return values;
    }

    public boolean isAccepted(String s) throws Exception {
        String current_state = this.q0_initial_state;
        int passedBy = 0;
        int chrNr = 1;
        char[] seq = s.toCharArray();
        for(char chr : seq){
            if(!Arrays.stream(this.e_alphabet).anyMatch(c -> c.equals(String.valueOf(chr))))
                throw new Exception("Not a literal");
            for(String val : trans_values){
                if(val.equals(String.valueOf(chr))) {
                    Pair<String, String> transition = this.transitions.get(passedBy);
                    current_state = transition.getValue();
                    if(this.finals.contains(current_state) && chrNr == seq.length && trans_values[passedBy].equals(String.valueOf(chr)))
                        return true;
                    if(this.finals.contains(current_state) && chrNr != seq.length)
                        return false;
                    break;
                }
                passedBy += 1;
            }
            chrNr += 1;
        }
        return false;
    }
}
