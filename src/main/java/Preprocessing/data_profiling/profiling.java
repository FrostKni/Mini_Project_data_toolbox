package Preprocessing.data_profiling;

import tech.tablesaw.api.Table;

public class profiling {
    public Table data_summary(Table t)
    {
        return t.summary();
    }
    public Table data_structure(Table t)
    {
        return t.structure();
    }
}
