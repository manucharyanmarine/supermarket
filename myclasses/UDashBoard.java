package myclasses;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class UDashBoard extends JFrame implements ActionListener {
    private static JButton profile_btn;
    private final JButton logout_btn;
    private final JButton filter_btn;
    private final JButton refresh_btn;
    private final JButton addToBill_btn;
    private final JButton clear_btn;
    private final JButton print_btn;
    private final JTable table;
    private final JTextField productId_fld;
    private final JTextField productQuantity_fld;
    private final JTextField productName_fld;
    private final JTable ordersTable;
    private final JComboBox<String> productCategory_box;

    public UDashBoard() {
        System.out.println("Currently in UDashBoard class");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("The Tipton Hotel Management");
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel WelcomeLbl = new JLabel("Billing");
        WelcomeLbl.setFont(new Font("Lucida Handwriting", Font.PLAIN, 35));
        WelcomeLbl.setForeground(Color.GRAY);
        WelcomeLbl.setHorizontalAlignment(SwingConstants.CENTER);
        WelcomeLbl.setBounds(254, 11, 389, 80);
        contentPane.add(WelcomeLbl);

        JPanel titleBack_panel = new JPanel();
        titleBack_panel.setBorder(null);
        titleBack_panel.setBounds(0, 11, 911, 68);
        contentPane.add(titleBack_panel);

        BufferedImage profile = null;
        try {
            profile =
                    ImageIO.read(
                            Objects.requireNonNull(UDashBoard.class.getResource("../images/UserProfile3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        profile_btn = new JButton("Profile");
        profile_btn.setBounds(20, 90, 50, 50);
        assert profile != null : "The 'UserProfile3.png' image could not be loaded";
        Image profileScaledInstance =
                profile.getScaledInstance(
                        profile_btn.getWidth(), profile_btn.getHeight(), Image.SCALE_SMOOTH);
        profile_btn.setIcon(new ImageIcon(profileScaledInstance));
        contentPane.add(profile_btn);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(500, 150, 450, 200);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        DefaultTableModel model =
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Id", "Name", "Category", "Price", "Status", "Quantity"}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Make all cells non-editable
                    }
                };

        table.getTableHeader().setReorderingAllowed(false);
        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(82);

        model.setRowCount(0);

        // Set custom background colors for alternate rows
        table.setDefaultRenderer(
                Object.class,
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table,
                            Object value,
                            boolean isSelected,
                            boolean hasFocus,
                            int row,
                            int column) {
                        Component component =
                                super.getTableCellRendererComponent(
                                        table, value, isSelected, hasFocus, row, column);
                        if (row % 2 == 0) {
                            component.setBackground(new Color(230, 230, 230)); // Light gray for even rows
                        } else {
                            component.setBackground(Color.WHITE); // White for odd rows
                        }
                        // Customize selection colors
                        if (isSelected) {
                            component.setBackground(Color.GREEN); // Set red background for selected row
                        }

                        return component;
                    }
                });

        // Customize table header names
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(150, 150, 150)); // Dark gray for header background
        header.setForeground(Color.WHITE); // White text color for header
        Font headerFont = header.getFont();
        header.setFont(headerFont.deriveFont(Font.BOLD)); // Make the font bold

        try (BufferedReader br = new BufferedReader(new FileReader("files/products.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("Products Detail")) //txt file title
                {
                    String[] rowData = new String[6]; // create an array with 5 elements
                    rowData[0] = line; // add the first element to the Room Number column
                    for (int i = 1; i < 6; i++) {
                        // read the next 4 lines and add the data to the corresponding column
                        rowData[i] = br.readLine();
                    }
                    model.addRow(rowData); // add the row to the JTable
                    br.readLine();
                    br.readLine();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        JLabel productId_lbl = new JLabel("Product Id");
        productId_lbl.setForeground(Color.BLACK);
        productId_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productId_lbl.setBounds(20, 150, 60, 23);
        contentPane.add(productId_lbl);

        productId_fld = new JTextField();
        productId_fld.setBounds(20, 175, 90, 20);
        contentPane.add(productId_fld);
        productId_fld.setColumns(10);


        JLabel productName_lbl = new JLabel("Product Name");
        productName_lbl.setForeground(Color.BLACK);
        productName_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productName_lbl.setBounds(130, 150, 97, 26);
        contentPane.add(productName_lbl);

        productName_fld = new JTextField();
        productName_fld.setColumns(10);
        productName_fld.setBounds(130, 175, 210, 20);
        contentPane.add(productName_fld);


        JLabel productQuantity_lbl = new JLabel("Product Quantity");
        productQuantity_lbl.setForeground(Color.BLACK);
        productQuantity_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productQuantity_lbl.setBounds(360, 150, 97, 26);
        contentPane.add(productQuantity_lbl);

        productQuantity_fld = new JTextField();
        productQuantity_fld.setColumns(10);
        productQuantity_fld.setBounds(360, 175, 100, 20);
        contentPane.add(productQuantity_fld);

        print_btn = new JButton("Print");
        print_btn.setBounds(370, 500, 100, 25);
        print_btn.setFocusable(false);
        contentPane.add(print_btn);

        JLabel productCategory_lbl = new JLabel("Product Category");
        productCategory_lbl.setForeground(Color.BLACK);
        productCategory_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productCategory_lbl.setBounds(500, 85, 120, 23);
        contentPane.add(productCategory_lbl);

        productCategory_box = new JComboBox<>();
        productCategory_box.setModel(new DefaultComboBoxModel<>(new String[]{"Bread", "Drink", "Coat"}));
        productCategory_box.setBounds(500, 110, 156, 25);
        contentPane.add(productCategory_box);

        filter_btn = new JButton("Filter");
        filter_btn.setBounds(680, 110, 100, 25);
        filter_btn.setFocusable(false);
        contentPane.add(filter_btn);

        refresh_btn = new JButton("Refresh");
        refresh_btn.setBounds(800, 110, 100, 25);
        refresh_btn.setFocusable(false);
        contentPane.add(refresh_btn);

        addToBill_btn = new JButton("Add to Bill");
        addToBill_btn.setBounds(100, 220, 100, 25);
        addToBill_btn.setFocusable(false);
        contentPane.add(addToBill_btn);

        clear_btn = new JButton("Clear");
        clear_btn.setBounds(230, 220, 100, 25);
        clear_btn.setFocusable(false);
        contentPane.add(clear_btn);


        JScrollPane scrollPaneOrder = new JScrollPane();
        scrollPaneOrder.setBounds(20, 280, 450, 200);
        contentPane.add(scrollPaneOrder);

        ordersTable = new JTable();
        scrollPaneOrder.setViewportView(ordersTable);

        DefaultTableModel modelOrder =
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Id", "Name", "Quantity", "Price"}) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Make all cells non-editable
                    }
                };

        ordersTable.getTableHeader().setReorderingAllowed(false);
        ordersTable.setModel(modelOrder);
        ordersTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        ordersTable.getColumnModel().getColumn(1).setPreferredWidth(82);

        modelOrder.setRowCount(0);

        JLabel totalPrice_lbl = new JLabel("Total Price");
        totalPrice_lbl.setForeground(Color.BLACK);
        totalPrice_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        totalPrice_lbl.setBounds(20, 500, 120, 23);
        contentPane.add(totalPrice_lbl);

        JLabel price_lbl = new JLabel("55");
        price_lbl.setForeground(Color.BLACK);
        price_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        price_lbl.setBounds(100, 500, 120, 23);
        contentPane.add(price_lbl);

        logout_btn = new JButton("Logout");
        logout_btn.setBounds(780, 440, 85, 31);
        logout_btn.setFocusable(false);
        contentPane.add(logout_btn);


        logout_btn.addActionListener(this);
        profile_btn.addActionListener(this);
        filter_btn.addActionListener(this);
        refresh_btn.addActionListener(this);
        addToBill_btn.addActionListener(this);
        clear_btn.addActionListener(this);
        print_btn.addActionListener(this);

        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == profile_btn) {
            setVisible(false);
            System.out.println("Exited from UDashBoard class");
            new Profile();
        } else if (e.getSource() == logout_btn) {
            int yesORno =
                    JOptionPane.showConfirmDialog(
                            null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == JOptionPane.YES_OPTION) {
                setVisible(false);
                System.out.println("Exited from UDashBoard class");
                new Login();
            }
        } else if (e.getSource() == addToBill_btn) {
            String prodId = productId_fld.getText(); // Room number
            String prodName = productName_fld.getText(); // Room number
            String prodCount = productQuantity_fld.getText().trim(); // Room price
            String prodPrice = prodCount;

            boolean prodIdEmpty = productId_fld.getText().isEmpty();
            boolean prodPriceEmpty = productQuantity_fld.getText().isEmpty();
            boolean prodQuantityEmpty = productName_fld.getText().isEmpty();

            String line = "files/orders.txt";
            if (!prodIdEmpty && !prodPriceEmpty && !prodQuantityEmpty) {
                try {
                    File file = new File(line);
                    if (!file.exists()) {
                        file.createNewFile();
                        // Append data to the file
                        FileWriter fileWriter = new FileWriter(file, true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        PrintWriter printWriter = new PrintWriter(bufferedWriter);
                        printWriter.close();
                    }

                    BufferedReader rdfile3 = new BufferedReader(new FileReader("files/orders.txt"));
                    int ttlLines3 = 0;
                    while (rdfile3.readLine() != null) {
                        ttlLines3++;
                    }
                    rdfile3.close();

                    // Append room details to the file
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    PrintWriter printWriter = new PrintWriter(bufferedWriter);
                    printWriter.println("Order Detail");
                    printWriter.println(prodId);
                    printWriter.println(prodName);
                    printWriter.println(prodCount);
                    printWriter.println(prodPrice);
                    printWriter.println();
                    printWriter.close();

                    // Clear text fields
                    productId_fld.setText(null);
                    productQuantity_fld.setText(null);
                    productName_fld.setText(null);

                    System.out.println("Product added");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                DefaultTableModel model = (DefaultTableModel) ordersTable.getModel();
                model.setRowCount(0);
                try (BufferedReader br = new BufferedReader(new FileReader("files/orders.txt"))) {
                    String orderLine;
                    while ((orderLine = br.readLine()) != null) {
                        if (!orderLine.equals("Order Detail")) {
                            String[] rowData = new String[4]; // create an array with 5 elements
                            rowData[0] = orderLine; // add the first element to the Room Number column
                            for (int i = 1; i < 4; i++) {
                                // read the next 4 lines and add the data to the corresponding column
                                rowData[i] = br.readLine();
                            }
                            model.addRow(rowData); // add the row to the JTable
                            br.readLine(); // skip two empty lines
                            br.readLine();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(
                        null, "Please Fill all the box", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == clear_btn) {
            DefaultTableModel tempTbl = (DefaultTableModel) table.getModel();
            int selectedRow = table.getSelectedRow();

            // Check if a row is selected
            if (table.getSelectedRow() != -1) {

                // Get data from the selected row
                String[] data = new String[6];
                for (int i = 0; i < 6; i++) {
                    data[i] = tempTbl.getValueAt(selectedRow, i).toString();
                }
                System.out.println(data);
                // Check if the room is not booked
//                if (data[4].equals("Not Booked")) {
                    try {
                        File inputFile = new File("files/products.txt");

                        BufferedReader reader = new BufferedReader(new FileReader(inputFile));


                        String currentLine;
                        int lineCounter = 0;
                        while ((currentLine = reader.readLine()) != null) {
                            lineCounter++;
                            if (currentLine.contains(data[0])) {
                                break;
                                // skip the lines that contain the room number to delete
                            }
                        }
                        System.out.println(lineCounter);
                        System.out.println(currentLine);


                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
//                } else {
//                    JOptionPane.showMessageDialog(this, "Room is Booked Please check out it first");
//                }
            }
        }
    }

    public static void main(String args[]) {
        UDashBoard manageProducts = new UDashBoard();
        manageProducts.setVisible(true);
    }
}
