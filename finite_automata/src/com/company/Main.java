package com.company;

import com.company.utils.Pair;

import java.util.*;

public class Main {

    public static void menu() {
        System.out.println("Finite Automata. Choose option:");
        System.out.println("1. Display sets of states");
        System.out.println("2. Display the alphabet");
        System.out.println("3. Display all transitions");
        System.out.println("4. Display the sets of final states");
    }

    private static void displayOption(int option, FiniteAutomata finiteAutomata) {
        switch (option) {
            case 1 :
                System.out.println(Arrays.toString(finiteAutomata.getQ_states()));
                break;
            case 2 :
                System.out.println(Arrays.toString(finiteAutomata.getE_alphabet()));
                break;
            case 3 :
                List<Pair<String, String>> trans = finiteAutomata.getTransitions();
                for(int i = 0; i < trans.size(); i++){
                    System.out.println(trans.get(i).getKey() + "->" + trans.get(i).getValue());
                }
            case 4 :
                System.out.println(finiteAutomata.getFinals());
        }
    }

    public static void main(String[] args) {
        // write your code here
        FiniteAutomata finiteAutomata = new FiniteAutomata();
        finiteAutomata.readFromFile("src/com/company/input.txt");
        System.out.println(finiteAutomata.toString());
        menu();
        Scanner console = new Scanner(System.in);
        int option = console.nextInt();
        displayOption(option, finiteAutomata);


        System.out.println("Testing accepting sequence");
        try {
            System.out.println(finiteAutomata.isAccepted("011"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(finiteAutomata);

    }



}
