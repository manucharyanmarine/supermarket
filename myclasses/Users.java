package myclasses;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Users extends JFrame implements ActionListener {
    private final JTable table;
    private final JButton logOut_btn;
    private final JButton back_btn;
    private final JButton del_btn;

    public Users() {
        setResizable(false);
        setTitle("Admin manage product");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(77, 77, 77));
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

        JLabel users_lbl = new JLabel("Users");
        users_lbl.setForeground(Color.LIGHT_GRAY);
        users_lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        users_lbl.setBounds(10, 3, 180, 34);
        contentPane.add(users_lbl);

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
                            component.setBackground(new Color(230, 230, 230));
                        } else {
                            component.setBackground(Color.WHITE);
                        }
                        if (isSelected) {
                            component.setBackground(Color.RED);
                        }

                        return component;
                    }
                });

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(150, 150, 150));
        header.setForeground(Color.WHITE);
        Font headerFont = header.getFont();
        header.setFont(headerFont.deriveFont(Font.BOLD));

        try (BufferedReader br = new BufferedReader(new FileReader("files/user_login.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("==============================================="))
                {
                    String[] rowData = new String[5];
                    rowData[0] = line;
                    for (int i = 1; i < 5; i++) {
                        rowData[i] = br.readLine();
                    }
                    model.addRow(rowData);
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
            int yesORno =
                    JOptionPane.showConfirmDialog(
                            null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new Login();
            }
        } else if (e.getSource() == back_btn) {
            setVisible(false);
            new DashBoard();
        } else if (e.getSource() == del_btn) {
            if (JOptionPane.showConfirmDialog(
                    null, "Confirmation", "Remove This product?", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                DefaultTableModel tempTbl = (DefaultTableModel) table.getModel();
                int selectedRow = table.getSelectedRow();

                if (table.getSelectedRow() != -1) {
                    String[] data = new String[6];
                    for (int i = 0; i < 5; i++) {
                        data[i] = tempTbl.getValueAt(selectedRow, i).toString();
                    }
                    if (data[4].equals("Not Booked")) {
                        try {
                            File inputFile = new File("files/products.txt");
                            File tempFile = new File("./files/products_temp.txt");
                            System.out.println("temp file created");

                            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                            System.out.println("temp file updated");

                            String currentLine;
                            int lineCounter = 0;
                            while ((currentLine = reader.readLine()) != null) {
                                lineCounter++;
                                if (currentLine.contains(data[0])) {
                                    break;
                                }
                            }

                            reader.close();
                            reader = new BufferedReader(new FileReader(inputFile));
                            int k = 0;
                            while ((currentLine = reader.readLine()) != null) {
                                k++;
                                if (k > (lineCounter - 2) && k < (lineCounter + 6)) {
                                } else {
                                    writer.write(currentLine + System.getProperty("line.separator"));
                                }
                            }
                            writer.close();
                            reader.close();

                            inputFile.delete();
                            System.out.println("Original file deleted");

                            tempFile.renameTo(inputFile);
                            System.out.println("temp file renamed as original file");

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        tempTbl.removeRow(table.getSelectedRow());
                    } else {
                        JOptionPane.showMessageDialog(this, "Not have product");
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
