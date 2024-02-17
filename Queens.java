import java.lang.Math;
import java.util.*;

/* YOU NEED TO ADD YOUR CODE TO THIS CLASS, REMOVING ALL DUMMY CODE
 *
 * DO NOT CHANGE THE NAME OR SIGNATURE OF ANY OF THE EXISTING METHODS
 * (Signature includes parameter types, return types and being static)
 *
 * You can add private methods to this class if it makes your code cleaner,
 * but this class MUST work with the UNMODIFIED Tester.java class.
 *
 * This is the ONLY class that you can submit for your assignment.
 *
 * @version 2601.24.1229.1
 */
public class Queens
{
    private static int boardSize = 10;
    
    // creates a valid genotype with random values
    public static Integer [] randomGeno()
    {
        Integer [] genotype = new Integer [boardSize];
        
        for (int i = 0; i < boardSize; i++) {
            genotype[i] = i + 1;
        }
        shuffleArray(genotype);
        
        return genotype;
    }
    
    // swaps 2 genes in the genotype
    // the swap happens with probability p, so if p = 0.8
    // then 8 out of 10 times this method is called, a swap happens
    public static Integer[] swapMutation(Integer[] genotype, double p)
    {
        if (Math.random() < p) {
            int index1 = (int) (Math.random() * boardSize);
            int index2 = (int) (Math.random() * boardSize);
            while (index1 == index2) {
                index2 = (int) (Math.random() * boardSize);
            }
            int temp = genotype[index1];
            genotype[index1] = genotype[index2];
            genotype[index2] = temp;
        }
        return genotype;
    }
    
    // creates 2 child genotypes using the 'cut-and-crossfill' method
    public static Integer[][] cutAndCrossfill(Integer[] parent0, Integer[] parent1)
    {
        Integer[][] children = new Integer[2][boardSize];
    
        int crossoverPoint = boardSize / 2;

        for (int i = 0; i < crossoverPoint; i++) {
            children[0][i] = parent0[i];
            children[1][i] = parent1[i];
        }
    
        for (int childIndex = 0; childIndex < 2; childIndex++) {
            Integer[] sourceParent = childIndex == 0 ? parent1 : parent0;
            int fillPosition = crossoverPoint;
            
            for (int i = crossoverPoint; i < boardSize; i++) {
                if (!contains(children[childIndex], sourceParent[i], boardSize)) {
                    children[childIndex][fillPosition++] = sourceParent[i];
                }
            }
            
            for (int i = 0; i < crossoverPoint; i++) {
                if (!contains(children[childIndex], sourceParent[i], boardSize)) {
                    if (fillPosition < boardSize) {
                        children[childIndex][fillPosition++] = sourceParent[i];
                    }
                }
            }
        }
    
        return children;
    }
    
    // calculates the fitness of an individual
    public static int getFitness(Integer [] genotype)
    {
        /* The initial fitness is the maximum pairs of queens
         * that can be in check (all possible pairs in check).
         * So we are using it as the maximum fitness value.
         * We deduct 1 from this value for every pair of queens
         * found to be in check.
         * So, the lower the score, the lower the fitness.
         * For a 10x10 board the maximum fitness is 45 (no checks),
         * and the minimum fitness is 0 (all queens in a line).
         */   
        
        int fitness = (int) (0.5 * boardSize * (boardSize - 1));
        
        for (int i = 0; i < genotype.length; i++) {
            for (int j = i + 1; j < genotype.length; j++) {
                if (genotype[i] - genotype[j] == i - j || genotype[j] - genotype[i] == i - j) {
                    fitness--;
                }
            }
        }
        return fitness;
    }

    // Helper method to shuffle an array
    private static void shuffleArray(Integer[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // Helper method to check if the array contains a value
    private static boolean contains(Integer[] array, Integer value, int arrayLength) {
        for (int i = 0; i < arrayLength; i++) {
            if (array[i] != null && array[i].equals(value)) {
                return true;
            }
        }
        return false;
    }
}
