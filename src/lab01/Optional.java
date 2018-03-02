package Lab01;

public class Optional {

    public void run(String[] args) {
        long startTime = System.nanoTime();

        int squareSize = Integer.parseInt(args[0]);
        int[][] square = new int[squareSize][squareSize];

        for (int index = 0; index < squareSize; index++) {
            for (int j = 0; j < squareSize; j++) {
                square[index][j] = squareSize * ((index + j + 1 + squareSize / 2) % squareSize) + (index + 2 * j + 1) % squareSize + 1;
            }
        }

        int magicConstant = squareSize * (squareSize * squareSize + 1) / 2;

        for (int columnIndex = 0; columnIndex < squareSize; columnIndex++) {
            int columnSum = 0;
            for (int elemIndex = 0; elemIndex < squareSize; elemIndex++) {
                columnSum += square[columnIndex][elemIndex];
            }
            if (magicConstant != columnSum) {
                System.out.println("Invalid square!");
                System.exit(0);
            }
        }

        for (int rowIndex = 0; rowIndex < squareSize; rowIndex++) {
            int rowSum = 0;
            for (int elemIndex = 0; elemIndex < squareSize; elemIndex++) {
                rowSum += square[elemIndex][rowIndex];
            }
            if (magicConstant != rowSum) {
                System.out.println("Invalid square!");
                System.exit(0);
            }
        }

        int pDiag = 0, sDiag = 0;

        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++) {

                // primary diagonal
                if (i == j) {
                    pDiag += square[i][j];
                }

                // secondary diagonal
                if (i == squareSize - j - 1) {
                    sDiag += square[i][j];
                }
            }
        }

        if (pDiag != magicConstant || sDiag != magicConstant) {
            System.out.println("Invalid square!");
            System.exit(0);
        }

        int maxSquareSizePrint = 20;
        int unicodeConstant = 9632;
        if (squareSize < maxSquareSizePrint) {
            for (int i = 0; i < squareSize; i++) {
                for (int j = 0; j < squareSize; j++) {
                    System.out.print(Character.toString((char) (unicodeConstant + square[i][j])) + " ");
                }
                System.out.println("");
            }
            System.out.println("\n");
        } else System.out.println("Square size too big to be printed!");

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("Optional part of Lab 01 took " + seconds + "!");
    }
}
