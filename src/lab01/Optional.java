// Author: Mutu Gheorghita
// command line: java -cp "C:\Users\ghita\Documents\java-pa-homeworks\out\production\java-pa-homeworks"  MainWindow 15

package lab01;

class Optional {

    void run(String[] args) {
        long startTime = System.nanoTime();

        int squareSize = Integer.parseInt(args[0]);
        if(squareSize % 2 == 0){
            System.out.println("Square size must be odd!");
            System.exit(0);
        }
        int[][] square = new int[squareSize][squareSize];

        int row = squareSize - 1;
        int col = squareSize / 2;
        square[row][col] = 1;

        for(int i = 2; i <= squareSize * squareSize; i++){
            if(square[(row + 1) % squareSize][(col + 1) % squareSize] == 0){
                row = (row + 1) % squareSize;
                col = (col + 1) % squareSize;
            }
            else {
                row = (row - 1 + squareSize) % squareSize;
            }
            square[row][col] = i;
        }

        int maxSquareSizePrint = 20;
        int unicodeConstant = 9632;
        if (squareSize < maxSquareSizePrint) {
            for (int i = 0; i < squareSize; i++) {
                for (int j = 0; j < squareSize; j++) {
                    System.out.print(Character.toString((char) (unicodeConstant + square[i][j])) + " ");
                }
                System.out.println();
            }
            System.out.println();
        } else System.out.println("Square size too big to be printed!");

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        double seconds = (double) totalTime / 1000000000.0;
        System.out.println("Optional part of Lab 01 took " + seconds + "!");
    }
}
