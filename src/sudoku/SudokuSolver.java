package sudoku;

public class SudokuSolver {
    
    private Sudoku sdk;
    boolean impossible;

    public SudokuSolver(Sudoku sudoku) {
        sdk = new Sudoku(sudoku);
    }

    private int m_entry(int debug, Sudoku sdkToModify) {

        int r, k, ival, nc;
        int[] candidates = new int[10];
        int nrfound = 0;

        for (r = 1; r < 10; r++) {
            for (k = 1; k < 10; k++) {

                if (sdk.is(r, k, 0)) {

                    /* who can be on this position? initially everyone */

                    for (ival = 1; ival < 10; ival++) {
                        candidates[ival] = 1;
                    }

                    nc = 9;
                    for (ival = 1; ival < 10; ival++) {
                        /* ival is no longer candidate if found in same row, column or block */
                        if (sdk.inrow(r, ival)) {
                            candidates[ival] = 0;
                            nc--;
                        } else if (sdk.incol(k, ival)) {
                            candidates[ival] = 0;
                            nc--;
                        } else if (sdk.inblock(r, k, ival)) {
                            candidates[ival] = 0;
                            nc--;
                        }
                    }

                    if (nc == 0) {
                        if (debug == 1) {
                            System.out.printf(" m_entry NO position possible for value %d at entry (%d,%d) - no solution\n", ival, r, k);
                            sdk.print();
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
                                if (sdkToModify!=null) sdkToModify.set(r, k, ival);
                                nrfound++;
                            }
                        }
                    }
                }
            }
        }

        return (nrfound);

    }


        private int m_row(int debug, Sudoku sdkToModify) {

        int r, k, ival, nc;
        int[] candidates = new int[10];
        int nrfound = 0;

        for (r = 1; r < 10; r++) {

            for (ival = 1; ival < 10; ival++) {

                if (!sdk.inrow(r, ival)) {

                    /* in which position can this value be? initially every */

                    for (k = 1; k < 10; k++) {
                        candidates[k] = 1;
                    }

                    nc = 9;
                    for (k = 1; k < 10; k++) { /* check every position if possible */

     /* k is no longer candidate if occupied or ival found
        in same column or block */

                        if (!sdk.is(r, k, 0)) {
                            candidates[k] = 0;
                            nc--;
                        } else if (sdk.incol(k, ival)) {
                            candidates[k] = 0;
                            nc--;
                        } else if (sdk.inblock(r, k, ival)) {
                            candidates[k] = 0;
                            nc--;
                        }
                    }

                    if (nc == 0) {
                        if (debug == 1) {
                            System.out.printf(" m_row NO position possible for value %d in column %d - no solution\n", ival, r);
                            sdk.print();
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
                                if (sdkToModify!=null) sdkToModify.set(r, k, ival);
                                nrfound++;
                            }
                        }
                    }
                }
            }
        }

        return (nrfound);

    }

    private int m_col(int debug, Sudoku sdkToModify) {

        int r, k, ival, nc;
        int[] candidates = new int[10];
        int nrfound = 0;

        for (k = 1; k < 10; k++) {

            for (ival = 1; ival < 10; ival++) {

                if (!sdk.incol(k, ival)) {

                    /* in which position can this value be? initially every */

                    for (r = 1; r < 10; r++) {
                        candidates[r] = 1;
                    }

                    nc = 9;
                    for (r = 1; r < 10; r++) { /* check every position if possible */

     /* r is no longer candidate if occupied or ival found
        in same row or block */

                        if (!sdk.is(r, k, 0)) {
                            candidates[r] = 0;
                            nc--;
                        } else if (sdk.inrow(r, ival)) {
                            candidates[r] = 0;
                            nc--;
                        } else if (sdk.inblock(r, k, ival)) {
                            candidates[r] = 0;
                            nc--;
                        }
                    }

                    if (nc == 0) {
                        if (debug == 1) {
                            System.out.printf(" m_col NO position possible for value %d in column %d - no solution\n", ival, k);
                            sdk.print();
                        }
                        impossible = true;
                        return nrfound;
                    } else if (nc == 1) {
                        for (r = 1; r < 10; r++) {
                            if (candidates[r] == 1) {
                                if (debug == 1) {
                                    System.out.printf(" m_col ONLY position for value %d  in col %d is %d\n", ival, k, r);
                                }
                                if (sdkToModify!=null) sdkToModify.set(r, k, ival);
                                nrfound++;
                            }
                        }
                    }
                }
            }
        }

        return (nrfound);

    }


    private int m_block(int debug, Sudoku sdkToModify) {

        int r, k, ival, nc;
        int[][] candidates = new int[10][10];
        int br, bk;   /* upper left entry of current block */
        int nrfound = 0;

        for (bk = 1; bk < 10; bk = bk + 3) {
            for (br = 1; br < 10; br = br + 3) {

                for (ival = 1; ival < 10; ival++) {

                    if (!sdk.inblock(br, bk, ival)) {

                        /* in which position can this value be? initially every */

                        for (r = br; r < br + 3; r++) {
                            for (k = bk; k < bk + 3; k++) {
                                candidates[r][k] = 1;
                            }
                        }

                        nc = 9;

                        for (r = br; r < br + 3; r++) {
                            for (k = bk; k < bk + 3; k++) { /* check every position in this block */

       /* (r,k) is no longer candidate if occupied or ival found
          in same row or col */

                                if (!sdk.is(r, k, 0)) {
                                    candidates[r][k] = 0;
                                    nc--;
                                } else if (sdk.inrow(r, ival)) {
                                    candidates[r][k] = 0;
                                    nc--;
                                } else if (sdk.incol(k, ival)) {
                                    candidates[r][k] = 0;
                                    nc--;
                                }
                            }
                        }

                        if (nc == 0) {
                            if (debug == 1) {
                                System.out.printf(" m_block NO position possible for value %d in block %s  - no solution\n", ival, sdk.blockToString(br, bk));
                                sdk.print();
                            }
                            impossible = true;
                            return nrfound;
                        } else if (nc == 1) {
                            for (r = br; r < br + 3; r++) {
                                for (k = bk; k < bk + 3; k++) {
                                    if (candidates[r][k] == 1) {
                                        if (debug == 1) {
                                            System.out.printf(" m_block ONLY position for value %d  in block %s is (%d,%d)\n", ival, sdk.blockToString(br, bk), r, k);
                                            // printSdk();
                                        }
                                        if (sdkToModify!=null) sdkToModify.set(r, k, ival);
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

    Sudoku hints(int debug) {
        Sudoku sdkHints=new Sudoku();
        int nrtotal = 0;
        nrtotal += m_row(debug,sdkHints);
        nrtotal += m_col( debug,sdkHints);
        nrtotal += m_block(debug,sdkHints);
        nrtotal += m_entry(debug,sdkHints);
        return sdkHints;
    }


    SudokuResult solve(int debug) {
        impossible = false;  /* methods will set this flag if sdk found to be impossible */
        boolean progress = true;
        int nrtotal;
        while (progress) {
            nrtotal = 0;
            if (!impossible) nrtotal += m_row(debug,sdk);
            if (!impossible) nrtotal += m_col(debug,sdk);
            if (!impossible) nrtotal += m_block(debug,sdk);
            if (!impossible) nrtotal += m_entry(debug,sdk);
            progress = (nrtotal > 0);
        }

        if (impossible) {
            return SudokuResult.IMPOSSIBLE;
        } else {
            if (sdk.count_entries(0) == 0) {
                return SudokuResult.COMPLETE;
            } else {
                return SudokuResult.INCOMPLETE;
            }
        }

    }

    Sudoku solution(){
        return sdk;
    }
}
