package myclasses;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class Users extends JFrame implements ActionListener {
    private final JTable table;
    private final JButton logOut_btn;
    private final JButton back_btn;
    private final JButton del_btn;

    public Users() {
        System.out.println("Currently in Users class");
        setResizable(false);
        setTitle("Admin manage product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(18, 77, 8));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        logOut_btn = new JButton("Log Out");
        logOut_btn.setBounds(812, 11, 89, 23);
        logOut_btn.setFocusable(false);
        contentPane.add(logOut_btn);

        back_btn = new JButton("Back");
        back_btn.setBounds(702, 11, 89, 23);
        back_btn.setFocusable(false);
        contentPane.add(back_btn);

        JLabel manageRoom_lbl = new JLabel("Users");
        manageRoom_lbl.setForeground(Color.LIGHT_GRAY);
        manageRoom_lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        manageRoom_lbl.setBounds(10, 3, 180, 34);
        contentPane.add(manageRoom_lbl);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(35, 65, 836, 366);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        DefaultTableModel model =
                new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Full Name", "User Name", "Password", "Phone", "Date"}) {
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
                            component.setBackground(Color.RED); // Set red background for selected row
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

        try (BufferedReader br = new BufferedReader(new FileReader("files/user_login.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("===============================================")) //txt file title
                {
                    String[] rowData = new String[5]; // create an array with 5 elements
                    rowData[0] = line; // add the first element to the Room Number column
                    for (int i = 1; i < 5; i++) {
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

        del_btn = new JButton("Delete");
        del_btn.setBounds(760, 445, 120, 34);
        del_btn.setFocusable(false);
        contentPane.add(del_btn);

        logOut_btn.addActionListener(this);
        back_btn.addActionListener(this);
        del_btn.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logOut_btn) {
            // Prompt for confirmation before logging out
            int yesORno =
                    JOptionPane.showConfirmDialog(
                            null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                System.out.println("Exited from ManageRoom class");
                new Login();
            }
        } else if (e.getSource() == back_btn) {
            setVisible(false);
            System.out.println("Exited from ManageRoom class");
            new DashBoard();
        } else if (e.getSource() == del_btn) {
            if (JOptionPane.showConfirmDialog(
                    null, "Confirmation", "Remove This Room?", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                DefaultTableModel tempTbl = (DefaultTableModel) table.getModel();
                int selectedRow = table.getSelectedRow();

                // Check if a row is selected
                if (table.getSelectedRow() != -1) {

                    // Get data from the selected row
                    String[] data = new String[6];
                    for (int i = 0; i < 5; i++) {
                        data[i] = tempTbl.getValueAt(selectedRow, i).toString();
                    }

                    // Check if the room is not booked
                    if (data[4].equals("Not Booked")) {
                        try {
                            File inputFile = new File("files/products.txt");
                            File tempFile = new File("./files/products_temp.txt");
                            System.out.println("temp file created");

                            // Read the original file and write to the temp file, excluding the room to delete
                            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                            System.out.println("temp file updated");

                            String currentLine;
                            int lineCounter = 0;
                            while ((currentLine = reader.readLine()) != null) {
                                lineCounter++;
                                if (currentLine.contains(data[0])) {
                                    break;
                                    // skip the lines that contain the room number to delete
                                }
                            }

                            reader.close();
                            reader = new BufferedReader(new FileReader(inputFile));
                            int k = 0;
                            while ((currentLine = reader.readLine()) != null) {
                                k++;
                                if (k > (lineCounter - 2) && k < (lineCounter + 6)) {
                                } else {
                                    // write all other lines to the temp file
                                    writer.write(currentLine + System.getProperty("line.separator"));
                                }
                            }

                            System.out.println("Room deleted");

                            writer.close();
                            reader.close();

                            // delete the original file
                            inputFile.delete();
                            System.out.println("Original file deleted");

                            // rename the temp file to the original file name
                            tempFile.renameTo(inputFile);
                            System.out.println("temp file renamed as original file");

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        // Remove the selected row from the table
                        tempTbl.removeRow(table.getSelectedRow());
                    } else {
                        JOptionPane.showMessageDialog(this, "Room is Booked Please check out it first");
                    }

                } else {
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(this, "Table is Empty!");

                    } else {
                        JOptionPane.showMessageDialog(this, "Please select A row to delete ");
                    }
                }
            }
        }
    }
}
