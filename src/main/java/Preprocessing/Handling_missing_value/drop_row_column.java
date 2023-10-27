package Preprocessing.Handling_missing_value;

import tech.tablesaw.api.Table;

public class drop_row_column {


    public Table drop_row(Table data)
    {
        String[] attr=data.columnNames().toArray(new String[0]);
        int totalRows=data.rowCount();
        for(int i=0;i<totalRows;i++) {
            int count=0;
            for(int j=0;j<attr.length;j++) {
                if(data.column(attr[j]).isMissing(i)) {
                    count+=1;
                }
            }
            if(count>=(0.6*attr.length)) {
                data=data.dropRows(i);
                totalRows-=1;
            }
        }
        return  data;
    }

    public Table drop_column(Table data)
    {
        String[] attr=data.columnNames().toArray(new String[0]);
        int totalRows=data.rowCount();
        for (String s : attr) {
            int count = 0;
            for (int j = 0; j < totalRows; j++) {
                if (data.column(s).isMissing(j)) {
                    count += 1;
                }
            }
            if (count >= (0.6 * totalRows)) {
                data = data.removeColumns(s);
            }
        }
        return  data;

    }

    public Table drop_column_manual(Table data,String[] attri)
    {
        data=data.removeColumns(attri);
        return  data;
    }








}
