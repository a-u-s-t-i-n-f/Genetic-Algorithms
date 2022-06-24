package edu.sdsu.cs.datastructures;

import java.util.*;

public class GeneticAlgorithm {

    private List<String> labels;
    private List<List<Integer>> Matrix2D;

    public GeneticAlgorithm(List<String> labels, List<List<Integer>> Matrix2D) {    //Constructor
        this.labels = labels;
        this.Matrix2D= Matrix2D;
    }

    public List<List<String>> generatePop(int size) {       //population given from user
        List<List<String>> initialPop = new ArrayList<>();  //population list

        for(int x = 0; x < size; x++) {         //populate list
            List temp = new ArrayList(labels);  //new list
            Collections.shuffle(temp);          //shuffle list
            initialPop.add(temp);               //add shuffled list to initialpop list
        }

        return initialPop;                      //return initialpop list
    }

    public List<List<String>> fitnessSort(List<List<String>> sequenceMatrix) {  //sort by fitness
        List<List<String>> sortedFit = new ArrayList<>();       //sorted fitness list
        System.out.println("\n-----Sorting Fitness List-----");

        int totalCost = 0;

        for(List list : sequenceMatrix) {       //loop calculating total cost
            sortedFit.add(list);                //populate list
            int cost = fitness(list);
            totalCost += cost;

            list.add(0, Integer.toString(cost));    //storing integers as strings
            //System.out.println(list);
        }
        sortedFit.sort((l1, l2) -> l1.get(0).compareTo(l2.get(0))); //compare integers taken in part from stackoverflow
        System.out.println("Fittest: " + sortedFit.get(0).get(0));  //prints from nested list

        for(List list : sortedFit) {        //removes fit value
            list.remove(list.get(0));       //to show the path
        }

        //calculates average fitness
        System.out.println("Average Fitness: " + (double) totalCost
                / (double) sortedFit.size());

        return sortedFit;
    }

    public int fitness(List<String> sequence) {         //calculates fitness of path
        int totalCost = 0;      //total cost of path
        int start, end;         //start and end of path

        for(int x = 0; x < sequence.size() - 1; x++) {  //for loop gets the cost of paths and calculates total
            start = labels.indexOf(sequence.get(x));
            end = labels.indexOf(sequence.get(x + 1));
            totalCost = totalCost + Matrix2D.get(start).get(end);
        }

        start = labels.indexOf(sequence.get(sequence.size() - 1));      //finalizes total cost with path
        end = labels.indexOf(sequence.get(0));
        totalCost = totalCost + Matrix2D.get(start).get(end);

        return totalCost;
    }

    public List<List<String>> crossover(List<List<String>> popTotal) {  //without touching the first two lists
        List<List<String>> crossList = new ArrayList<>();               //calls bread on two lists to complete
        System.out.println("\n--------Crossing List--------");          //crossover

        crossList.add(popTotal.get(0));
        crossList.add(popTotal.get(1));

        for(int x = 2; x < popTotal.size(); x++) {
            crossList.add(breed(popTotal.get(x - 1), popTotal.get(x)));
        }

        return crossList;

    }

    public List<String> breed(List<String> parent, List<String> parent2) {
        List<String> breedList = new ArrayList<>(parent2);

        String temp = "";               //temp string
        String temp2 = "";              //temp string
        Random rand = new Random();     //new random

        for(int x = 0; x < breedList.size() / 2; x++) { //loop for breeding

            int randIndex = rand.nextInt(parent.size());    //get one index from first list
            temp = parent.get(randIndex);                   //store value at index from first list
            temp2 = breedList.get(randIndex);               //store value at index from out breedlist
            int index = breedList.indexOf(temp);            //store index from breedlist
            breedList.set(randIndex, temp);                 //store value at index in breedlist
            breedList.set(index, temp2);                    //store value at index in breedlist

        }

        return breedList;
    }

    public List<String> mutation(List<String> mutateSequence) {     //swaps two random indices of values
        Random rand = new Random();                                 //while also ensuring no duplicates

        int randIndex1 = rand.nextInt(mutateSequence.size());
        int randIndex2 = rand.nextInt(mutateSequence.size());
        while(randIndex1 == randIndex2) {
            randIndex2 = rand.nextInt(mutateSequence.size());
        }

        Collections.swap(mutateSequence, randIndex1, randIndex2);
        return mutateSequence;

    }
}
