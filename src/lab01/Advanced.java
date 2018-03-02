// Author: Mutu Gheorghita

package lab01;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.search.strategy.selectors.variables.ImpactBased;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.tools.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;

public class Advanced {
    private Model model = new Model("Magic square");
    private int squareSize = 0;
    private IntVar[] vars;

    private void buildModel() {
        int ms = squareSize * (squareSize * squareSize + 1) / 2;

        IntVar[][] matrix = new IntVar[squareSize][squareSize];
        IntVar[][] invMatrix = new IntVar[squareSize][squareSize];
        vars = new IntVar[squareSize * squareSize];

        int k = 0;
        for (int i = 0; i < squareSize; i++) {
            for (int j = 0; j < squareSize; j++, k++) {
                matrix[i][j] = model.intVar("matrix", 1, squareSize * squareSize, false);
                vars[k] = matrix[i][j];
                invMatrix[j][i] = matrix[i][j];
            }
        }

        IntVar[] diagonal01 = new IntVar[squareSize];
        IntVar[] diagonal02 = new IntVar[squareSize];
        for (int i = 0; i < squareSize; i++) {
            diagonal01[i] = matrix[i][i];
            diagonal02[i] = matrix[(squareSize - 1) - i][i];
        }

        model.allDifferent(vars, "BC").post();

        int[] coeffs = new int[squareSize];
        Arrays.fill(coeffs, 1);
        IntVar msv = model.intVar(ms);

        for (int i = 0; i < squareSize; i++) {
            model.scalar(matrix[i], coeffs, "=",  msv).post();
            model.scalar(invMatrix[i], coeffs, "=", msv).post();
        }
        model.scalar(diagonal01, coeffs, "=", msv).post();
        model.scalar(diagonal02, coeffs, "=", msv).post();

        // Symetries breaking
        model.arithm(matrix[0][squareSize - 1], "<", matrix[squareSize - 1][0]).post();
        model.arithm(matrix[0][0], "<", matrix[squareSize - 1][squareSize - 1]).post();
        model.arithm(matrix[0][0], "<", matrix[squareSize - 1][0]).post();

    }

    private void configureSearch() {
        model.getSolver().setSearch(new ImpactBased(vars, 2, 3, 10, 29091981L, false));
    }

    private void solve() {
        model.getSolver().findSolution();
    }

    private void printSquare() {
        StringBuilder st = new StringBuilder();
        StringBuilder line = new StringBuilder("+");
        for (int i = 0; i < squareSize; i++) {
            line.append("----+");
        }
        line.append("\n");
        st.append(line);
        for (int i = 0; i < squareSize; i++) {
            st.append("|");
            for (int j = 0; j < squareSize; j++) {
                st.append(StringUtils.pad(vars[i * squareSize + j].getValue() + "", -3, " ")).append(" |");
            }
            st.append(MessageFormat.format("\n{0}", line.toString()));
        }
        st.append("\n\n\n");
        System.out.println(st.toString());
    }

    private void setSquareSize(int squareSize){
        this.squareSize = squareSize;
    }

    public void run(int squareSize){
        this.setSquareSize(squareSize);
        this.buildModel();
        this.configureSearch();
        this.solve();
        this.printSquare();
    }
}
