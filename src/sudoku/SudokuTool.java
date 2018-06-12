package sudoku;

import java.util.ArrayList;
import java.util.Collections;

class RandomOnce {

    ArrayList<Integer> l;
    int index;

    public RandomOnce(int size) {
        l = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            l.add(i);
        }
        Collections.shuffle(l);
        index=0;
    }

    int getNext() {
        index++;
        return l.get(index - 1);
    }
}


public class SudokuTool {


    private static void usage() {
        System.out.println("Usage");
        System.out.println("  sudokuTool solve=true|false file=\"C:\\...\\sudoku.txt\"");
        System.out.println("  sudokuTool generate");
        System.exit(-1);
    }


    public static void main(String[] args) {

        String sdkFileName = "";
        boolean solve = true;
        boolean generate = false;
        int initialEntries = 26;

        try {
            for (int arg = 1; arg <= args.length; arg++) {
                String[] s = args[arg - 1].split("=");
                if (s[0].equals("solve")) {
                    solve = true;
                    if (s.length > 1) {
                        if (s[1].toLowerCase().equals("false")) solve = false;
                    }
                } else if (s[0].equals("file")) {
                    sdkFileName = s[1];
                } else if (s[0].equals("generate")) {
                    generate = true;
                } else if (s[0].equals("entries")) {
                    initialEntries = Integer.parseInt(s[1]);
                } else {
                    System.out.println("Unknown argument : " + args[arg - 1]);
                }
            }
            System.out.println("solve    : " + solve);
            System.out.println("file     : " + sdkFileName);
            System.out.println("generate : " + generate);
            System.out.println("entries  : " + initialEntries);

        } catch (Exception e) {
            usage();
        }

        if (!sdkFileName.equals("")) {
            Sudoku sdkIn = new Sudoku();
            sdkIn.readFromFile(sdkFileName);
            sdkIn.print();

            SudokuSolver sudokuSolver = new SudokuSolver(sdkIn);
            Sudoku sdkHints=sudokuSolver.hints(1);
            int nrHints=81-sdkHints.count_entries(0);
            System.out.println("--- "+nrHints+" hints");

            if (solve) {
                SudokuResult result = sudokuSolver.solve(1);
                System.out.println("--- " + result);
                sudokuSolver.solution().print();
            }
            return;
        }

        if (generate) {

            // take a known complete sudoku

            Sudoku sdk = new Sudoku();
            Sudoku solvedSdk = SudokuTemplate.create();

            int count=0;
            boolean end=false;
            int minHints=Integer.MAX_VALUE;
            while ((!end)){

                // copy a limited nr of entries into an empty sudoku

                sdk.reset();
                RandomOnce randomOnce = new RandomOnce(81);
                for (int i = 1; i <= initialEntries; i++) {
                    int n = randomOnce.getNext();
                    int r = (n - 1) / 9 + 1;
                    int k = n - 9 * (r - 1);
                    sdk.set(r, k, solvedSdk.get(r, k));
                }

                // try to solve it

                SudokuSolver sudokuSolver = new SudokuSolver(sdk);
                SudokuResult result = sudokuSolver.solve(0);

                count++;
                if (count%5000 ==0) System.out.print("*");
                if (count%500000==0) end=true;

                if (result==SudokuResult.COMPLETE) {
                    // count the number of initial unique entries (corresponds to the difficulty)
                    SudokuSolver sudokuSolver2 = new SudokuSolver(sdk);
                    Sudoku sdkHints=sudokuSolver2.hints(0);
                    int nrHints=81-sdkHints.count_entries(0);
                    if (nrHints<minHints) {
                        System.out.println();
                        count=0;
                        System.out.println("--- " + nrHints + " hints");
                        sdkHints.print();
                        sdk.print();
                        minHints=nrHints;
                    }

                }

            }

        }
    }
}


