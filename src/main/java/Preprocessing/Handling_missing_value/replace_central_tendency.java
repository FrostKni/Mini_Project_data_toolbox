package Preprocessing.Handling_missing_value;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.util.ArrayList;
import java.util.List;

public class replace_central_tendency {
    private List<Double> median,meana;
    private NumericColumn<?>[] numericColumns;
    public Table replace_mean(Table data)
    {
        meana = new ArrayList<>();
        numericColumns = data.numericColumns().toArray(new NumericColumn[0]);
        for (NumericColumn<?> column : numericColumns) {
            assert false;
            if(column.type().toString().equals("DOUBLE"))
            {
                DoubleColumn temp = (DoubleColumn) data.column(column.name());
                meana.add(column.mean());
                Column<Double> temp_col = temp.setMissingTo(column.mean());
                data.replaceColumn(column.name(),temp_col);
            }
            if(column.type().toString().equals("INTEGER"))
            {
                IntColumn temp = (IntColumn) data.column(column.name());
                meana.add(column.mean());
                Column<Integer> temp_col = temp.setMissingTo((int) column.mean());
                data.replaceColumn(column.name(),temp_col);
            }

        }
        return data;
    }
    public Table replace_median(Table data)
    {
        median = new ArrayList<>();
        numericColumns = data.numericColumns().toArray(new NumericColumn[0]);
        for (NumericColumn<?> column : numericColumns) {
            assert false;
            if(column.type().toString().equals("DOUBLE"))
            {
                DoubleColumn temp = (DoubleColumn) data.column(column.name());
                median.add(column.median());
                Column<Double> temp_col = temp.setMissingTo(column.median());
                data.replaceColumn(column.name(),temp_col);
            }
            if(column.type().toString().equals("INTEGER"))
            {
                IntColumn temp = (IntColumn) data.column(column.name());
                median.add(column.median());
                Column<Integer> temp_col = temp.setMissingTo((int) column.median());
                data.replaceColumn(column.name(),temp_col);
            }

        }
        return data;
    }

    public  List<Double> get_median()
    {
        return median;
    }
    public  List<Double> get_mean()
    {
        return meana;
    }

    public NumericColumn<?>[] get_numeric_column()
    {
        return numericColumns;
    }
}
