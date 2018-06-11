package sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;



public class SudokuSolverBackup {

    static int debug = 1;
    static boolean solve = true;
    static String sdkFileName;

    static int[][] sdk = new int[10][10];
    static boolean impossible;

    static int[] Blocknr = {0, 1, 1, 1, 2, 2, 2, 3, 3, 3};

    static int count_entries(int value) {
        int i, r, k;
        i = 0;
        for (r = 1; r < 10; r++) {
            for (k = 1; k < 10; k++) {
                if (sdk[r][k] == value) i++;
            }
        }
        return i;
    }

    static String blockToString(int r, int k){
        int br = Blocknr[r];
        int bk = Blocknr[k];
        return "("+br+","+bk+")";

    }

    static boolean inblock(int r, int k, int value) {
        /*return 1 if the value is found in the same block as (r,k) */
        int br, bk, ir, ik;
        br = Blocknr[r];
        bk = Blocknr[k];
        for (ir = 1; ir < 4; ir++) {
            for (ik = 1; ik < 4; ik++) {
                if (sdk[(br - 1) * 3 + ir][(bk - 1) * 3 + ik] == value) return true;
            }
        }
        return false;
    }

    static boolean inrow(int r, int value) {
        /* return 1 if this value is found in this row */
        int ik;
        for (ik = 1; ik < 10; ik++) {
            if (sdk[r][ik] == value) return true;
        }
        return false;
    }

    static boolean incol(int k, int value) {
        /* return 1 if this value is found in this column */
        int ir;
        for (ir = 1; ir < 10; ir++) {
            if (sdk[ir][k] == value) return true;
        }
        return false;
    }

    static int m_entry(boolean modify) {

        int r, k, ival, nc;
        int[] candidates = new int[10];
        int nrfound = 0;

        for (r = 1; r < 10; r++) {
            for (k = 1; k < 10; k++) {

                if (sdk[r][k] == 0) {

                    if (debug == 2) System.out.printf("Checking (%d,%d)\n", r, k);

                    /* who can be on this position? initially everyone */

                    for (ival = 1; ival < 10; ival++) {
                        candidates[ival] = 1;
                    }

                    nc = 9;
                    for (ival = 1; ival < 10; ival++) {
                        if (debug == 2) System.out.printf("try value %d   ", ival);
                        /* ival is no longer candidate if found in same row, column or block */
                        if (inrow(r, ival)) {
                            if (debug == 2) System.out.printf(" in rij");
                            candidates[ival] = 0;
                            nc--;
                        } else if (incol(k, ival)) {
                            if (debug == 2) System.out.printf(" in kolom");
                            candidates[ival] = 0;
                            nc--;
                        } else if (inblock(r, k, ival)) {
                            if (debug == 2) System.out.printf(" in blok");
                            candidates[ival] = 0;
                            nc--;
                        }
                        if (debug == 2) System.out.printf("\n");
                    }

                    if (debug == 2) System.out.printf("\n nc = %d\n", nc);

                    if (nc == 0) {
                        if (debug == 1) {
                            System.out.printf(" m_entry NO position possible for value %d at entry (%d,%d) - no solution\n", ival, r,k);
                            printSdk();
                        }
                        impossible = true;
                        return nrfound;
                    } else if (nc == 1) {
                        for (ival = 1; ival < 10; ival++) {
                            if (candidates[ival] == 1) {
                                if (debug == 1) {
                                    System.out.printf(" m_entry ONLY candidate for (%d,%d) is %d\n", r, k, ival);
                                    //printSdk();
                                }
                                if (modify) sdk[r][k] = ival;
                                nrfound++;
                            }
                        }
                    }
                }
            }
        }

        return (nrfound);

    }


    static int m_row(boolean modify) {

        int r, k, ival, nc;
        int[] candidates = new int[10];
        int nrfound = 0;

        for (r = 1; r < 10; r++) {

            for (ival = 1; ival < 10; ival++) {

                if (!inrow(r, ival)) {

                    if (debug == 2) System.out.printf("Checking value %d for row %d\n", ival, r);

                    /* in which position can this value be? initially every */

                    for (k = 1; k < 10; k++) {
                        candidates[k] = 1;
                    }

                    nc = 9;
                    for (k = 1; k < 10; k++) { /* check every position if possible */

                        if (debug == 2) System.out.printf("try position %d   ", k);

     /* k is no longer candidate if occupied or ival found
        in same column or block */

                        if (sdk[r][k] != 0) {
                            if (debug == 2) System.out.printf(" bezet");
                            candidates[k] = 0;
                            nc--;
                        } else if (incol(k, ival)) {
                            if (debug == 2) System.out.printf(" in kolom");
                            candidates[k] = 0;
                            nc--;
                        } else if (inblock(r, k, ival)) {
                            if (debug == 2) System.out.printf(" in blok");
                            candidates[k] = 0;
                            nc--;
                        }
                        if (debug == 2) System.out.printf("\n");
                    }

                    if (debug == 2) System.out.printf("\n nc = %d\n", nc);

                    if (nc == 0) {
                        // if (debug == 1) System.out.printf(" m_row NO value possible for row %d - no solution\n", r);
                        if (debug == 1) {
                            System.out.printf(" m_row NO position possible for value %d in column %d - no solution\n", ival, r);
                            printSdk();
                        }
                        impossible = true;
                        return nrfound;
                    } else if (nc == 1) {
                        for (k = 1; k < 10; k++) {
                            if (candidates[k] == 1) {
                                if (debug == 1) {
                                    System.out.printf(" m_row ONLY position for value %d  in row %d is %d\n", ival, r, k);
                                    //printSdk();
                                }
                                if (modify) sdk[r][k] = ival;
                                nrfound++;
                            }
                        }
                    }
                }
            }
        }

        return (nrfound);

    }


    static int m_col(boolean modify) {

        int r, k, ival, nc;
        int[] candidates = new int[10];
        int nrfound = 0;

        for (k = 1; k < 10; k++) {

            for (ival = 1; ival < 10; ival++) {

                if (!incol(k, ival)) {

                    if (debug == 2) System.out.printf("Checking value %d for col %d\n", ival, k);

                    /* in which position can this value be? initially every */

                    for (r = 1; r < 10; r++) {
                        candidates[r] = 1;
                    }

                    nc = 9;
                    for (r = 1; r < 10; r++) { /* check every position if possible */

                        if (debug == 2) System.out.printf("try position %d   ", r);

     /* r is no longer candidate if occupied or ival found
        in same row or block */

                        if (sdk[r][k] != 0) {
                            if (debug == 2) System.out.printf(" bezet");
                            candidates[r] = 0;
                            nc--;
                        } else if (inrow(r, ival)) {
                            if (debug == 2) System.out.printf(" in rij");
                            candidates[r] = 0;
                            nc--;
                        } else if (inblock(r, k, ival)) {
                            if (debug == 2) System.out.printf(" in blok");
                            candidates[r] = 0;
                            nc--;
                        }
                        if (debug == 2) System.out.printf("\n");
                    }

                    if (debug == 2) System.out.printf("\n nc = %d\n", nc);

                    if (nc == 0) {
                        if (debug == 1) {
                            System.out.printf(" m_col NO position possible for value %d in column %d - no solution\n", ival, k);
                            printSdk();
                        }
                        impossible = true;
                        return nrfound;
                    } else if (nc == 1) {
                        for (r = 1; r < 10; r++) {
                            if (candidates[r] == 1) {
                                if (debug == 1) {
                                    System.out.printf(" m_col ONLY position for value %d  in col %d is %d\n", ival, k, r);
                                   // printSdk();
                                }
                                if (modify) sdk[r][k] = ival;
                                nrfound++;
                            }
                        }
                    }
                }
            }
        }

        return (nrfound);

    }


    static int m_block(boolean modify) {

        int r, k, ival, nc;
        int[][] candidates = new int[10][10];
        int br, bk;   /* upper left entry of current block */
        int nrfound = 0;

        for (bk = 1; bk < 10; bk = bk + 3) {
            for (br = 1; br < 10; br = br + 3) {

                for (ival = 1; ival < 10; ival++) {

                    if (!inblock(br, bk, ival)) {

                        if (debug == 2) System.out.printf("Checking value %d for block %s\n", ival, blockToString(br, bk));

                        /* in which position can this value be? initially every */

                        for (r = br; r < br + 3; r++) {
                            for (k = bk; k < bk + 3; k++) {
                                candidates[r][k] = 1;
                            }
                        }

                        nc = 9;

                        for (r = br; r < br + 3; r++) {
                            for (k = bk; k < bk + 3; k++) { /* check every position in this block */

                                if (debug == 2) System.out.printf("try position (%d,%d)   ", r, k);

       /* (r,k) is no longer candidate if occupied or ival found
          in same row or col */

                                if (sdk[r][k] != 0) {
                                    if (debug == 2) System.out.printf(" bezet");
                                    candidates[r][k] = 0;
                                    nc--;
                                } else if (inrow(r, ival)) {
                                    if (debug == 2) System.out.printf(" in rij");
                                    candidates[r][k] = 0;
                                    nc--;
                                } else if (incol(k, ival)) {
                                    if (debug == 2) System.out.printf(" in col");
                                    candidates[r][k] = 0;
                                    nc--;
                                }
                                if (debug == 2) System.out.printf("\n");
                            }
                        }

                        if (debug == 2) System.out.printf("\n nc = %d\n", nc);

                        if (nc == 0) {
                            if (debug == 1) {
                                System.out.printf(" m_block NO position possible for value %d in block %s  - no solution\n", ival, blockToString(br,bk));
                                System.out.println("inblock? "+inblock(br, bk, ival));
                                printSdk();
                            }
                            impossible = true;
                            return nrfound;
                        } else if (nc == 1) {
                            for (r = br; r < br + 3; r++) {
                                for (k = bk; k < bk + 3; k++) {
                                    if (candidates[r][k] == 1) {
                                        if (debug == 1) {
                                            System.out.printf(" m_block ONLY position for value %d  in block %s is (%d,%d)\n", ival, blockToString(br, bk), r, k);
                                           // printSdk();
                                        }
                                        if (modify) sdk[r][k] = ival;
                                        nrfound++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return nrfound;

    }

    static void readSdkFile() {
        try {

            //          File f = new File("C:\\Users\\erikv\\Documents\\sudokus\\s1.txt");
            File f = new File(sdkFileName);

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            char[] carr;

            int row = 0;
            while (((readLine = b.readLine()) != null) && (row < 9)) {
                if (readLine.matches("[123456789.]+")) {
                    carr = readLine.toCharArray();

                    for (int col = 0; col < 9; col++) {
                        String s = new String(carr, col, 1);
                        int ival;
                        if (s.equals("."))
                            ival = 0;
                        else {
                            ival = Integer.parseInt(s);
                        }
                        sdk[row + 1][col + 1] = ival;
                    }
                    row++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    static void printSdk() {
        int i, j;
        System.out.printf("\n");
        for (i = 1; i < 10; i++) {
            for (j = 1; j < 10; j++) {
                if (sdk[i][j] == 0) System.out.printf(".");
                else System.out.printf("%d", sdk[i][j]);
                if ((j % 3) == 0) System.out.printf(" ");
            }
            if ((i % 3) == 0) System.out.printf("\n");
            System.out.printf("\n");
        }
    }


    private static void usage() {
        System.out.println("Usage");
        System.out.println("  sudoku solve=true|false file=\"C:\\...\\sudoku.txt\"");
        System.exit(-1);
    }


    public static void main(String[] args) {

        try {

            // args must be name=value

            for (int arg = 1; arg <= args.length; arg++) {
                String[] s = args[arg - 1].split("=");
                if (s[0].equals("solve")) {
                    solve = true;
                    if (s.length > 1) {
                        if (s[1].toLowerCase().equals("false")) solve = false;
                    }
                } else if (s[0].equals("file")) {
                    sdkFileName = s[1];
                } else {
                    System.out.println("Unknown argument : " + args[arg - 1]);
                }
            }
            System.out.println("solve : " + solve);
            System.out.println("file  : " + sdkFileName);
        } catch (Exception e) {
            usage();
        }

        readSdkFile();
        printSdk();

        System.out.println(" --- hints --- ");


        int nrtotal=0;
        debug = 1; /* otherwise hints are not shown */
        nrtotal += m_row(false);
        nrtotal += m_col(false);
        nrtotal += m_block(false);
        nrtotal += m_entry(false);
        System.out.println(" --- Total of "+nrtotal+" hints --- ");

        if (solve) {
            //  sdk[][] is assumed to contain the unmodified starting values.
            System.out.println(" --- solve --- ");
            impossible = false;  /* methods will set this flag if sdk found to be impossible */
            boolean solve = true;
            while (solve) {

                nrtotal = 0;
                if (!impossible) nrtotal += m_row(true);
                if (!impossible) nrtotal += m_col(true);
                if (!impossible) nrtotal += m_block(true);
                if (!impossible) nrtotal += m_entry(true);

                solve = (nrtotal > 0);
            }

            if (impossible) {
                System.out.println(" --- no solution possible --- ");
            } else {
                if (count_entries(0) == 0) { /* found a solution */
                    System.out.println(" --- solution --- ");
                    printSdk();
                } else {
                    System.out.println(" --- no solution found --- ");
                }
            }


        }

    }
}
