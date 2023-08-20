package Gui;
import Preprocessing.Loding_file;
import Preprocessing.display_info;
import com.google.common.io.Files;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.StringJoiner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class Main_Screen extends JFrame implements ActionListener {

    public JLabel lable;
    public JTable main_table;
    public Container contain;


    public  Font f,fo,font;
    public JPanel main_panel,display_panel,table_panel,table_chile_panel;
    public JLabel Project_name;

    public  String location_of_file="null";
    public JComboBox choose;
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
        fo= new Font("Consolas", Font.BOLD,40);
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



        choose = new JComboBox(Items);
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

        JTextArea dis ;
        dis = new display_screen().display();
        display_panel.add(dis);

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

        int visibleColumns =4;
        main_table.setPreferredScrollableViewportSize(new Dimension(
                main_table.getColumnModel().getTotalColumnWidth(),
                main_table.getRowHeight() * main_table.getRowCount()));

      ////////////////////////////////////


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
                    lable.setText("Select the File");
                    choose.setVisible(false);

                    b = new Loding_file().Loding_fil();
                    a = new Loding_file().text_path();
                    main_panel.add(a);
                    main_panel.add(b);


                    final String[] s = {null};
                    JTextField finalA = a;
                    b.addActionListener(new ActionListener() {

                                            @Override
                                            public void actionPerformed(ActionEvent e) {


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

                                                if (ext.equals("csv")) {
                                                    finalA.setText(location_of_file);
                                                    DefaultTableModel model = new DefaultTableModel(new display_info().info_data(location_of_file),new display_info().info_column(location_of_file));
                                                    main_table.setModel(model);


                                                } else {
                                                    JOptionPane.showMessageDialog(null, "Wrong Input File!!!");
                                                }


                                            }

                                        }

                    );

                    //end of loading

                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}




