package Preprocessing;


import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

import java.util.Arrays;
import java.util.List;


public class display_info {

    public Table t;
    public Object[] info_column(Table t)
    {

        return t.columnNames().toArray();

    }
    public Object[][] info_data(Table t)
    {
        int numRows = t.rowCount();
        int numCols = t.columnCount();
        Object[][] dataArray = new Object[numRows][numCols];

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int colIndex = 0; colIndex < numCols; colIndex++) {
                dataArray[rowIndex][colIndex] = t.get(rowIndex, colIndex);
            }
        }
        return dataArray;
    }

}
