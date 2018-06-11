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

        /*
        System.out.println(" created array list of size " + l.size());
        for (int i = 1; i <= size; i++) {
            System.out.print(l.get(i - 1));
            System.out.print(" ");
        }
        System.out.println(" ");
        */
    }

    void reShuffle(){
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
        System.exit(-1);
    }


    public static void main(String[] args) {

        // args must be name=value
        String sdkFileName = "";
        boolean solve = true;
        boolean generate = false;

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
                } else {
                    System.out.println("Unknown argument : " + args[arg - 1]);
                }
            }
            System.out.println("solve : " + solve);
            System.out.println("file  : " + sdkFileName);
        } catch (Exception e) {
            usage();
        }

        if (!sdkFileName.equals("")) {
            Sudoku sdkIn = new Sudoku();
            sdkIn.readFromFile(sdkFileName);
            sdkIn.print();

            SudokuSolver sudokuSolver = new SudokuSolver(sdkIn); // the solver works on a copy
            int nrhints=sudokuSolver.hints(1);
            System.out.println("--- "+nrhints+" hints");

            if (solve) {
                SudokuResult result = sudokuSolver.solve(1);
                System.out.println("--- " + result);
                sudokuSolver.solution().print();
            }
            return;
        }

        if (generate) {

            // take a known complete sudoku

            Sudoku sdk;
            Sudoku solvedSdk = SudokuTemplate.create();

            int complete=0;
            int incomplete=0;

            boolean found=false;
            while ((!found)){//&&(incomplete<10000)) {
                // copy a limited nr of entries into an empty sudoku

                sdk = new Sudoku();
                RandomOnce randomOnce = new RandomOnce(81);
                for (int i = 1; i <= 24; i++) {
                    int n = randomOnce.getNext();
                    int r = (n - 1) / 9 + 1;
                    int k = n - 9 * (r - 1);
                    sdk.set(r, k, solvedSdk.get(r, k));
                }

                // try to solve it

                SudokuSolver sudokuSolver = new SudokuSolver(sdk);
                SudokuResult result = sudokuSolver.solve(0);

                if (result==SudokuResult.INCOMPLETE) {
                    incomplete++;
                   // System.out.println("--- " + incomplete);
                } else  {
                    SudokuSolver sudokuSolver2 = new SudokuSolver(sdk); // the solver works on a copy
                    int nrhints=sudokuSolver2.hints(0);
                    System.out.println("--- "+nrhints+" hints");
                    sdk.print();
                    //sudokuSolver.solution().print();
                    //found=true;
                }

            }

        }
    }
}


