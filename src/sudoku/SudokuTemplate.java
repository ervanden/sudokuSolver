package sudoku;

public class SudokuTemplate {

    static Sudoku create() {
        Sudoku sdk = new Sudoku();

        sdk.set(1, 1, 3);
        sdk.set(1, 2, 1);
        sdk.set(1, 3, 7);
        sdk.set(1, 4, 2);
        sdk.set(1, 5, 9);
        sdk.set(1, 6, 5);
        sdk.set(1, 7, 8);
        sdk.set(1, 8, 4);
        sdk.set(1, 9, 6);
        sdk.set(2, 1, 2);
        sdk.set(2, 2, 8);
        sdk.set(2, 3, 6);
        sdk.set(2, 4, 4);
        sdk.set(2, 5, 7);
        sdk.set(2, 6, 1);
        sdk.set(2, 7, 5);
        sdk.set(2, 8, 3);
        sdk.set(2, 9, 9);
        sdk.set(3, 1, 9);
        sdk.set(3, 2, 4);
        sdk.set(3, 3, 5);
        sdk.set(3, 4, 3);
        sdk.set(3, 5, 8);
        sdk.set(3, 6, 6);
        sdk.set(3, 7, 1);
        sdk.set(3, 8, 7);
        sdk.set(3, 9, 2);
        sdk.set(4, 1, 4);
        sdk.set(4, 2, 9);
        sdk.set(4, 3, 3);
        sdk.set(4, 4, 1);
        sdk.set(4, 5, 5);
        sdk.set(4, 6, 2);
        sdk.set(4, 7, 6);
        sdk.set(4, 8, 8);
        sdk.set(4, 9, 7);
        sdk.set(5, 1, 7);
        sdk.set(5, 2, 2);
        sdk.set(5, 3, 1);
        sdk.set(5, 4, 8);
        sdk.set(5, 5, 6);
        sdk.set(5, 6, 3);
        sdk.set(5, 7, 4);
        sdk.set(5, 8, 9);
        sdk.set(5, 9, 5);
        sdk.set(6, 1, 6);
        sdk.set(6, 2, 5);
        sdk.set(6, 3, 8);
        sdk.set(6, 4, 7);
        sdk.set(6, 5, 4);
        sdk.set(6, 6, 9);
        sdk.set(6, 7, 3);
        sdk.set(6, 8, 2);
        sdk.set(6, 9, 1);
        sdk.set(7, 1, 5);
        sdk.set(7, 2, 3);
        sdk.set(7, 3, 2);
        sdk.set(7, 4, 9);
        sdk.set(7, 5, 1);
        sdk.set(7, 6, 8);
        sdk.set(7, 7, 7);
        sdk.set(7, 8, 6);
        sdk.set(7, 9, 4);
        sdk.set(8, 1, 8);
        sdk.set(8, 2, 6);
        sdk.set(8, 3, 4);
        sdk.set(8, 4, 5);
        sdk.set(8, 5, 2);
        sdk.set(8, 6, 7);
        sdk.set(8, 7, 9);
        sdk.set(8, 8, 1);
        sdk.set(8, 9, 3);
        sdk.set(9, 1, 1);
        sdk.set(9, 2, 7);
        sdk.set(9, 3, 9);
        sdk.set(9, 4, 6);
        sdk.set(9, 5, 3);
        sdk.set(9, 6, 4);
        sdk.set(9, 7, 2);
        sdk.set(9, 8, 5);
        sdk.set(9, 9, 8);
        return sdk;
    }
}
