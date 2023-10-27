package Gui;
import Preprocessing.Handling_missing_value.drop_row_column;
import Preprocessing.Handling_missing_value.replace_central_tendency;
import Preprocessing.Loding_file;
import Preprocessing.data_profiling.profiling;
import Preprocessing.display_info;
import com.google.common.io.Files;
import com.ibm.icu.impl.UResource;
import tech.tablesaw.api.NumericColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.columns.Column;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;


public class Main_Screen extends JFrame implements ActionListener {

    public JLabel lable;

    public JTextArea dis ;
    public JTable main_table;
    public Table dataframe;
    public Container contain;
    public JComboBox<String> p2,p;
    public  Font f,fo,font,table_font;
    public JPanel main_panel,display_panel,table_panel,table_chile_panel;
    public JLabel Project_name;

    public  String location_of_file="null";
    public JComboBox<String> choose;
    private JPanel display_child_panel;

    public void Main_Scr()
    {

        setTitle("MyPanel");
        contain=getContentPane();

        main_panel = new JPanel();
        main_panel.setLayout(null);
        main_panel.setSize(1470,400);
        main_panel.setBackground(Color.lightGray);
        main_panel.setBorder(BorderFactory.createLineBorder(Color.black,2));

        display_panel = new JPanel();
        display_panel.setLayout(null);
        display_panel.setSize(730,590);
        display_panel.setBorder(BorderFactory.createLineBorder(Color.black,2));

        table_panel = new JPanel();
        table_panel.setLayout(null);
        table_panel.setSize(730,590);
        table_panel.setBorder(BorderFactory.createLineBorder(Color.black,2));


        font = new Font("Consolas", Font.BOLD, 20);
        f = new Font("Consolas", Font.BOLD,40);
        fo= new Font("Consolas", Font.BOLD,15);
        table_font =new Font("Consolas", Font.BOLD,16);
        //construct preComponents
        String[] Items = {"Data Preprocessing", "Data Analysis"};

        Project_name = new JLabel("Data Analysis ToolBox");
        Project_name.setBounds(500,10,450,50);
        Project_name.setFont(f);
        Project_name.setVisible(true);
        main_panel.add(Project_name);

        //construct components
        lable = new JLabel ("Select the choice: ");
        lable.setBounds (200, 180, 180, 25);
        lable.setFont(font);
        lable.setVisible(true);
        main_panel.add (lable);



        choose = new JComboBox<>(Items);
        choose.setBounds (500, 180, 200, 25);
        choose.setFont(font);
        choose.setEditable(false);
        choose.setVisible(true);
        main_panel.add (choose);



        //adjust size and set layout
        setSize (1500, 1050);
        setLayout (null);

        main_panel.setLocation(10,10);
        display_panel.setLocation(10,420);
        table_panel.setLocation(750,420);



        contain.add(main_panel);
        contain.add(display_panel);
        contain.add(table_panel);

        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setVisible (true);
        setResizable(false);
        setLocation(200,0);

        //////////////////////////////////////////////////////////
        // display screen with scolling
        display_child_panel = new JPanel();
        display_child_panel.setBorder(BorderFactory.createLineBorder(Color.black,2));

        dis = new display_screen().display();
        dis.setFont(table_font);
        display_child_panel.add(dis);

        JScrollPane scroll_dis = new JScrollPane(display_child_panel);
        scroll_dis.setLayout(new ScrollPaneLayout());
        scroll_dis.setLocation(5,5);
        scroll_dis.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll_dis.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_dis.setSize(720,580);
        scroll_dis.setAutoscrolls(true);
        scroll_dis.getPreferredSize();
        scroll_dis.setViewportView(dis);
        display_panel.add(scroll_dis,BorderLayout.CENTER);


        ////////////////////////////////////////////

       //   table screen with scrolling
        table_chile_panel = new JPanel();


        table_chile_panel.setBorder(BorderFactory.createLineBorder(Color.black,2));

        Object [][] data ={{ }};
        String [] columnNames={ };
        // Create a DefaultTableModel with non-editable cells
        DefaultTableModel model1= new DefaultTableModel(data,columnNames);


        main_table = new JTable(model1);

        main_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        main_table.setLocation(20,20);
        main_table.setLayout(new BorderLayout());
        main_table.setDefaultEditor(Object.class,null);
        table_chile_panel.add(main_table);
        JScrollPane scroll = new JScrollPane(table_chile_panel);
        scroll.setLayout(new ScrollPaneLayout());
        scroll.setLocation(5,5);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(720,580);
        scroll.setAutoscrolls(true);
        scroll.getPreferredSize();
        scroll.setViewportView(main_table);
        table_panel.add(scroll,BorderLayout.CENTER);

        main_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        main_table.setFont(table_font);
        main_table.setRowHeight(25);



        main_table.setPreferredScrollableViewportSize(new Dimension(
                main_table.getColumnModel().getTotalColumnWidth(),
                main_table.getRowHeight() * main_table.getRowCount()));


       ////////////////////////////////////// display panel



      ////////////////////////////////////


//        minimize_maximize
//        JRadioButton display_panel_min =  new minimize_maximize().min_max();
//        display_panel_min.setSize(10,10);
//        display_panel_min.setLocation(-4,0);
//        contain.add(display_panel_min);

        //actions

        choose.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            JButton b ;
            JTextField a ;

            if (e.getSource() == choose) {
                if (choose.getSelectedItem() == "Data Preprocessing") {
                    p = new JComboBox<>();
                    p.setBounds (800, 180, 200, 25);
                    p.addItem("Data Profiling");
                    p.addItem("Data Structure");
                    p.addItem("Handling Missing Data");
                    p.setAutoscrolls(true);
                    p.setVisible(false);
                    p.setEnabled(false);
                    p.setFont(fo);
                    main_panel.add(p);



                    lable.setText("Select the File");
                    choose.setVisible(false);

                    b = new Loding_file().Loding_fil();
                    a = new Loding_file().text_path();
                    main_panel.add(a);
                    main_panel.add(b);


                    final String[] s = {null};
                    JTextField finalA = a;
                    finalA.setFont(fo);
                    b.addActionListener(e12 -> {


                        JFileChooser fileChooser = new JFileChooser();
                        int result = fileChooser.showOpenDialog(Main_Screen.this);
                        File selectedFile = null;
                        if (result == JFileChooser.APPROVE_OPTION) {
                            selectedFile = fileChooser.getSelectedFile();
                        }
                        s[0] = String.valueOf(selectedFile);
                        StringJoiner joiner = new StringJoiner("");
                        for (String string : s) {
                            joiner.add(string);
                        }
                        location_of_file = joiner.toString();

                        String ext = Files.getFileExtension(location_of_file);
                        //
                        dataframe = Table.read().csv(location_of_file);

                        if (ext.equals("csv")) {
                            finalA.setText(location_of_file);
                            DefaultTableModel model = new DefaultTableModel(new display_info().info_data(dataframe),new display_info().info_column(dataframe));
                            main_table.setModel(model);
                            new zoom();

                            dis.setText("\t\t-------REPORT-------\n\n");
                            dis.append("COLUMNS \t-------->\t MISSING COUNT\n");
                            for (Column<?> column : dataframe.columns())
                            {
                                dis.append(String.valueOf(column.name())+"\t---------->\t"+ column.countMissing()+" / "+dataframe.rowCount()+"\n");
                            }

                            p.setVisible(true);
                            p.setEnabled(true);

                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong Input File!!!");
                        }


                    }

                    );

                    //end of loading

                    // data profiling

                    p.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(p.getSelectedItem()== "Data Profiling")
                            {
                                Table data_profile_table = new profiling().data_summary(dataframe);
                                DefaultTableModel data_profiling_model = new DefaultTableModel(new display_info().info_data(data_profile_table),new display_info().info_column(data_profile_table));
                                main_table.setModel(data_profiling_model);
                                new zoom();

                            }
                            else if(p.getSelectedItem()=="Data Structure")
                            {
                                Table data_structure_table = new profiling().data_structure(dataframe);
                                DefaultTableModel data_structure_model = new DefaultTableModel(new display_info().info_data(data_structure_table),new display_info().info_column(data_structure_table));
                                main_table.setModel(data_structure_model);
                                new zoom();
                            }
                            else if(p.getSelectedItem()=="Handling Missing Data")
                            {
                                p2 = new JComboBox<>();
                                p2.setBounds(1100,180,240,25);
                                p2.setFont(fo);
                                p2.addItem("Drop row (60% MISSING)");
                                p2.addItem("Drop column(60% MISSING)");
                                p2.addItem("Drop Unwanted Columns");
                                p2.addItem("Global Constant");
                                p2.addItem("Mean");
                                p2.addItem("Median");
                                p2.addItem("Mode");
                                p2.addItem("Minimum");
                                p2.addItem("Maximum");
                                p2.addItem("Regression");
                                p2.addItem("K-nearest");
                                p2.addItem("Machine learning Imputation");
                                p2.setVisible(true);
                                p2.setEnabled(true);
                                p2.setEditable(false);
                                main_panel.add(p2);
                                main_panel.repaint();


                                p2.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(p2.getSelectedItem()== "Mean")
                                        {
                                            // updating table with mean values
                                            replace_central_tendency mena_cal = new replace_central_tendency();
                                            dataframe=mena_cal.replace_mean(dataframe);

                                            // getting column and their mean values
                                            List<Double> mean_values = mena_cal.get_mean();
                                            NumericColumn<?>[] numeric_colums = mena_cal.get_numeric_column();

                                            // displaying
                                            dis.setText("\t\t-------REPORT-------\n\n");
                                            dis.append("COLUMNS \t\t-------->\t MEAN \n");
                                            for (int i=0;i<mean_values.size();i++)
                                            {
                                                dis.append(String.valueOf(numeric_colums[i])+"\t---------->\t"+ mean_values.get(i)+"\n");
                                            }

                                            // creating data model for the table
                                            DefaultTableModel model = new DefaultTableModel(new display_info().info_data(dataframe),new display_info().info_column(dataframe));
                                            main_table.setModel(model);
                                            new zoom();

                                        }

                                        if(p2.getSelectedItem()== "Median")
                                        {
                                            replace_central_tendency median_cal = new replace_central_tendency();
                                            dataframe=median_cal.replace_median(dataframe);

                                            List<Double> median_values = median_cal.get_median();
                                            NumericColumn<?>[] numeric_colums = median_cal.get_numeric_column();

//                                            dis.setText("COLUMNS: \n"+Arrays.toString(numeric_colums)+"\nMedians: \n"+median_values.toString());
                                            dis.setText("\t\t-------REPORT-------\n\n");
                                            dis.append("COLUMNS \t\t-------->\t MEDIAN \n");
                                            for (int i=0;i<median_values.size();i++)
                                            {
                                                dis.append(String.valueOf(numeric_colums[i])+"\t---------->\t"+ median_values.get(i)+"\n");
                                            }

                                            DefaultTableModel model = new DefaultTableModel(new display_info().info_data(dataframe),new display_info().info_column(dataframe));
                                            main_table.setModel(model);
                                            new zoom();

                                        }
                                        if(p2.getSelectedItem()=="Mode")
                                        {
                                            replace_central_tendency mode_cal = new replace_central_tendency();
                                            dataframe = mode_cal.replace_mode(dataframe);

                                            List<Object>  mode_values =mode_cal.get_mode();
                                            List<String>  colu = mode_cal.get_columns();


                                            dis.setText("\t\t-------REPORT-------\n\n");
                                            dis.append("COLUMNS \t-------->\t MODE \n");
                                            for (int i=0;i<mode_values.size();i++)
                                            {
                                                dis.append(colu.get(i) +"\t---------->\t"+ mode_values.get(i)+"\n");
                                            }

                                            DefaultTableModel model = new DefaultTableModel(new display_info().info_data(dataframe),new display_info().info_column(dataframe));
                                            main_table.setModel(model);
                                            new zoom();

                                        }


                                        if(p2.getSelectedItem()=="Drop row")
                                        {
                                            drop_row_column dr  = new drop_row_column();
                                            dataframe=dr.drop_row(dataframe);

                                            DefaultTableModel model = new DefaultTableModel(new display_info().info_data(dataframe),new display_info().info_column(dataframe));
                                            main_table.setModel(model);
                                            new zoom();

                                        }

                                        if(p2.getSelectedItem()=="Drop column")
                                        {
                                            drop_row_column dr  = new drop_row_column();
                                            dataframe=dr.drop_column(dataframe);

                                            DefaultTableModel model = new DefaultTableModel(new display_info().info_data(dataframe),new display_info().info_column(dataframe));
                                            main_table.setModel(model);
                                            new zoom();


                                        }


                                        if(p2.getSelectedItem()=="Drop Unwanted Columns")
                                        {
                                            JComboBox<JCheckBox> p3 = new JComboBox<>();
                                            p3.setBounds(1211,230,200,25);

                                            for (Column<?> column : dataframe.columns()) {
                                                JCheckBox m = new JCheckBox(column.name());
                                                m.setUI(new BasicCheckBoxUI());
                                                p3.addItem(m);
                                                p3.setRenderer(new DefaultListCellRenderer());
                                            }

                                            main_panel.add(p3);
                                            main_panel.repaint();



////                                            dataframe= dr.drop_column_manual(dataframe,);
//                                            DefaultTableModel model = new DefaultTableModel(new display_info().info_data(dataframe),new display_info().info_column(dataframe));
//                                            main_table.setModel(model);
//                                            new zoom();
                                        }


                                    }
                                });


                            }
                        }
                    });


                }
            }

        }
        catch (Exception ignored)
        {
            System.out.println(ignored);
        }
    }

    public class zoom{
        zoom() {
            int column_size = main_table.getColumnCount();
            int rows_size=main_table.getRowCount();
//            System.out.println(column_size);
            if (column_size <= 9 && column_size > 0) {
                main_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            } else {
                main_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }
//            if (rows_size<=25) {
//                DefaultTableModel data_model = (DefaultTableModel) main_table.getModel();
//                for (int i=0;i<40-rows_size;i++){
//                data_model.addRow(new Object[column_size]);
//                }
//            }
        }
    }
}





