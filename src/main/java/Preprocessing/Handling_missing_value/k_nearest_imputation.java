package Preprocessing.Handling_missing_value;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class k_nearest_imputation {
    private Table table;
    private String featureColumn1;
    private String featureColumn2;
    private int k; // Number of neighbors to consider

    public k_nearest_imputation(Table table, String featureColumn1, String featureColumn2, int k) {
        this.table = table;
        this.featureColumn1 = featureColumn1;
        this.featureColumn2 = featureColumn2;
        this.k = k;
    }

    public static void main(String[] args) {
        System.out.println("Hello");

        // Load the CSV file (replace with your CSV file path)
        Scanner scanner = new Scanner(System.in);

        // Load the CSV file
        System.out.print("Enter the path to the CSV file: ");
        String csvFilePath = scanner.nextLine();
        Table table = Table.read().csv(csvFilePath);

        // Get the target column from the user
        System.out.print("Enter the name of the target column for imputing values: ");
        String targetColumn = scanner.nextLine();

        // Get the feature columns for computing distance from the user
        System.out.print("Enter the name of the first feature column: ");
        String featureColumn1 = scanner.nextLine();
        System.out.print("Enter the name of the second feature column: ");
        String featureColumn2 = scanner.nextLine();

        // Get the value of "k" from the user
        System.out.print("Enter the value of 'k' for KNN imputation: ");
        int k = scanner.nextInt();

        System.out.println("Original Table:");
        System.out.println(table); // Print the original table

        k_nearest_imputation imputer = new k_nearest_imputation(table, featureColumn1, featureColumn2, k);
        imputer.imputeNumericColumns(targetColumn);

        System.out.println("Modified Table (after imputations):");
        System.out.println(table); // Print the modified table

        // Print imputed values for the target column
        if (table.column(targetColumn) instanceof IntColumn) {
            IntColumn targetNumericColumn = table.intColumn(targetColumn);
            targetNumericColumn.forEach(value -> {
                if (value == Integer.MIN_VALUE) {
                    System.out.println("Missing");
                } else {
                    float floatValue = (float) value;
                    System.out.println(floatValue);
                }
            });
        } else if (table.column(targetColumn) instanceof DoubleColumn) {
            DoubleColumn targetNumericColumn = table.doubleColumn(targetColumn);
            targetNumericColumn.forEach(value -> {
                if (Double.isNaN(value)) {
                    System.out.println("Missing");
                } else {
                    float floatValue = value.floatValue();
                    System.out.println(floatValue);
                }
            });
        } else {
            System.out.println("Target column is not of type IntColumn or DoubleColumn. Please check your dataset.");
        }
    }

    public void imputeNumericColumns(String targetColumn) {
        if (table.column(targetColumn) instanceof IntColumn) {
            IntColumn targetNumericColumn = table.intColumn(targetColumn);
            for (int rowIndex = 0; rowIndex < table.rowCount(); rowIndex++) {
                Integer value = targetNumericColumn.get(rowIndex);
                if (value == null) {
                    double imputedValue = imputeNumericValue(rowIndex);
                    if (imputedValue != Double.NaN) {
                        targetNumericColumn.set(rowIndex, (int) imputedValue);
                    }
                }
            }
        } else if (table.column(targetColumn) instanceof DoubleColumn) {
            DoubleColumn targetNumericColumn = table.doubleColumn(targetColumn);
            for (int rowIndex = 0; rowIndex < table.rowCount(); rowIndex++) {
                Double value = targetNumericColumn.get(rowIndex);
                if (value == null) {
                    double imputedValue = imputeNumericValue(rowIndex);
                    if (!Double.isNaN(imputedValue)) {
                        targetNumericColumn.set(rowIndex, imputedValue);
                    }
                }
            }
        } else {
            System.out.println("Target column is not of type IntColumn or DoubleColumn. Please check your dataset.");
        }
    }

    private double imputeNumericValue(int targetRowIndex) {
        List<InputDistancePair> distancesAndInputs = new ArrayList<>();

        double missingValue1 = table.column(featureColumn1).get(targetRowIndex) instanceof Number ? ((Number) table.column(featureColumn1).get(targetRowIndex)).doubleValue() : Double.NaN;
        double missingValue2 = table.column(featureColumn2).get(targetRowIndex) instanceof Number ? ((Number) table.column(featureColumn2).get(targetRowIndex)).doubleValue() : Double.NaN;

        for (int rowIndex = 0; rowIndex < table.rowCount(); rowIndex++) {
            if (rowIndex != targetRowIndex) {
                Object inputValue1Obj = table.column(featureColumn1).get(rowIndex);
                Object inputValue2Obj = table.column(featureColumn2).get(rowIndex);

                if (inputValue1Obj != null && inputValue2Obj != null) {
                    double inputValue1 = inputValue1Obj instanceof Number ? ((Number) inputValue1Obj).doubleValue() : Double.NaN;
                    double inputValue2 = inputValue2Obj instanceof Number ? ((Number) inputValue2Obj).doubleValue() : Double.NaN;
                    double euclideanDistance = computeEuclideanDistance(inputValue1, inputValue2, missingValue1, missingValue2);
                    distancesAndInputs.add(new InputDistancePair(inputValue1, inputValue2, euclideanDistance));
                }
            }
        }

        distancesAndInputs.sort(Comparator.comparingDouble(pair -> pair.euclideanDistance));

        double imputedValue = 0.0;

        for (int i = 0; i < k; i++) {
            imputedValue += distancesAndInputs.get(i).inputValue1;
        }

        return imputedValue / k;
    }

    private double computeEuclideanDistance(double value1, double value2, double value3, double value4) {
        double diff1 = value1 - value3;
        double diff2 = value2 - value4;
        return diff1 * diff1 + diff2 * diff2;
    }

    private class InputDistancePair {
        private double inputValue1;
        private double inputValue2;
        private double euclideanDistance;

        public InputDistancePair(double inputValue1, double inputValue2, double euclideanDistance) {
            this.inputValue1 = inputValue1;
            this.inputValue2 = inputValue2;
            this.euclideanDistance = euclideanDistance;
        }
    }
}
