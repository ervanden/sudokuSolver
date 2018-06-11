package sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {
    private int[][] sdk;
    private int[] Blocknr = {0, 1, 1, 1, 2, 2, 2, 3, 3, 3};

    public Sudoku() {
        sdk = new int[10][10];
        int r, k;
        for (r = 1; r < 10; r++) {
            for (k = 1; k < 10; k++) {
                sdk[r][k] = 0;
            }
        }
    }

    public Sudoku(Sudoku s) {
        sdk = new int[10][10];
        int r, k;
        for (r = 1; r < 10; r++) {
            for (k = 1; k < 10; k++) {
                sdk[r][k] = s.get(r, k);
            }
        }
    }

    void set(int r, int k, int value) {
        sdk[r][k] = value;
    }

    int get(int r, int k) {
        return sdk[r][k];
    }

    boolean is(int r, int k, int value) {
        return sdk[r][k] == value;
    }

    boolean inblock(int r, int k, int value) {
        /*return 1 if the value is found in the same block as (r,k) */
        int br, bk, ir, ik;
        br = Blocknr[r];
        bk = Blocknr[k];
        for (ir = 1; ir < 4; ir++) {
            for (ik = 1; ik < 4; ik++) {
                if (is((br - 1) * 3 + ir, (bk - 1) * 3 + ik, value)) return true;
            }
        }
        return false;
    }

    boolean inrow(int r, int value) {
        /* return 1 if this value is found in this row */
        int ik;
        for (ik = 1; ik < 10; ik++) {
            if (is(r, ik, value)) return true;
        }
        return false;
    }

    boolean incol(int k, int value) {
        /* return 1 if this value is found in this column */
        int ir;
        for (ir = 1; ir < 10; ir++) {
            if (is(ir, k, value)) return true;
        }
        return false;
    }

    int count_entries(int value) {
        int i, r, k;
        i = 0;
        for (r = 1; r < 10; r++) {
            for (k = 1; k < 10; k++) {
                if (is(r, k, value)) i++;
            }
        }
        return i;
    }

    String blockToString(int r, int k) {
        int br = Blocknr[r];
        int bk = Blocknr[k];
        return "(" + br + "," + bk + ")";

    }

    void print() {
        int i, j;
        System.out.print("\n");
        for (i = 1; i < 10; i++) {
            for (j = 1; j < 10; j++) {
                if (sdk[i][j] == 0) System.out.print(".");
                else System.out.printf("%d", sdk[i][j]);
                if ((j % 3) == 0) System.out.print(" ");
            }
            if ((i % 3) == 0) System.out.print("\n");
            System.out.print("\n");
        }
    }

    void readFromFile(String fileName) {
        try {
            File f = new File(fileName);
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine;
            char[] carr;

            int row = 0;
            while (((readLine = b.readLine()) != null) && (row < 9)) {
                if (readLine.matches("[123456789.]+.*")) {
                    carr = readLine.toCharArray();

                    for (int col = 0; col < 9; col++) {
                        String s = new String(carr, col, 1);
                        int ival;
                        if (s.equals("."))
                            ival = 0;
                        else {
                            ival = Integer.parseInt(s);
                        }
                        set(row + 1, col + 1, ival);
                    }
                    row++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
