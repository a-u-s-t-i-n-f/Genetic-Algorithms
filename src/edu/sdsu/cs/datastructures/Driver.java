package edu.sdsu.cs.datastructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {

        List<String> labels = new ArrayList<>();            //new list for labels
        List<List<Integer>> matrix = new ArrayList<>();     //nested list matrix

        //scanner for file
        Scanner scanner = new Scanner(new File(args[0]));   //Scanner for file
        System.out.println("----------------Matrix----------------");
        while(scanner.hasNextLine()) {                      //loop to read file

            String labelArr[] = scanner.nextLine()          //scanner that removes whitespace
                    .replace(" ", "")
                    .split(",");

            labels.add(labelArr[0]);                        //store in list

            List<Integer> tempList = new ArrayList<>();     //new list

            for(int x = 1; x < labelArr.length; x++) {      //loop to convert to integers and store in matrix
                tempList.add(Integer.parseInt(labelArr[x]));

            }

            System.out.println(tempList);
            matrix.add(tempList);       //add list to matrix

        }
        System.out.println();

        //scanner for population size and epochs
        Scanner userIn = new Scanner(System.in);
        System.out.print("Enter Population Size: ");
        int userSize = userIn.nextInt();

        System.out.print("Enter Epochs: ");
        int epochs = userIn.nextInt();

        //loop for method calls depending on epochs
        for(int x = 0; x < epochs; x++) {
            GeneticAlgorithm ga = new GeneticAlgorithm(labels, matrix);
            List<List<String>> pop = ga.generatePop(userSize);

            System.out.println("\n-----------Epoch: " + (x + 1) + "-----------");

            for(List list : pop) {
                System.out.println(list);
            }

            List<List<String>> fitnessList = ga.fitnessSort(pop);
            for(List list : fitnessList) {
                System.out.println(list);
            }

            List<List<String>> crossList = ga.crossover(fitnessList);
            for(List list : crossList) {
                System.out.println(list);
            }

            Random rand = new Random();
            int randMute = rand.nextInt(crossList.size());

            for(List list : crossList) {

                ga.mutation(list);

            }
            System.out.println("Index Mutated: " + randMute);
            System.out.println("Mutation: " + ga.mutation(crossList.get(randMute)));

        }

    }
}
