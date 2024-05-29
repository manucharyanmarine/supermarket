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

        try {
            File jsonFile = new File("files/user_login.json");
            BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            reader.close();

            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray usersArray = jsonObject.getJSONArray("users");

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObject = usersArray.getJSONObject(i);
                String[] rowData = new String[5];
                rowData[0] = userObject.getString("username");
                rowData[1] = userObject.getString("fullName");
                rowData[2] = userObject.getString("password");
                rowData[3] = userObject.getString("phoneNumber");
                rowData[4] = userObject.getString("timeAndDate");
                model.addRow(rowData);
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
                    null, "Confirmation", "Remove This user?", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                DefaultTableModel tempTbl = (DefaultTableModel) table.getModel();
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    String username = tempTbl.getValueAt(selectedRow, 0).toString();
                    try {
                        File jsonFile = new File("files/user_login.json");
                        List<User> users = readUsersFromJson(jsonFile);

                        // Check if the user with the specified username exists
                        boolean found = false;
                        for (User user : users) {
                            if (user.getUsername().equals(username)) {
                                found = true;
                                break;
                            }
                        }

                        if (found) {
                            // Remove the user with the matching username
                            users.removeIf(user -> user.getUsername().equals(username));
                            writeUsersToJson(jsonFile, users);
                            tempTbl.removeRow(selectedRow);
                        } else {
                            JOptionPane.showMessageDialog(null, "User not found!");
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    if (table.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Table is Empty!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    }
                }
            }
        }
    }

    private List<User> readUsersFromJson(File file) throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray usersArray = jsonObject.getJSONArray("users");
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userJson = usersArray.getJSONObject(i);
                User user = new User(
                        userJson.getString("username"),
                        userJson.getString("password"),
                        userJson.getString("fullName"),
                        userJson.getString("phoneNumber"),
                        userJson.getString("timeAndDate")
                );
                users.add(user);
            }
        }
        return users;
    }

    private void writeUsersToJson(File file, List<User> users) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            JSONObject jsonObject = new JSONObject();
            JSONArray usersArray = new JSONArray();
            for (User user : users) {
                JSONObject userJson = new JSONObject();
                userJson.put("username", user.getUsername());
                userJson.put("password", user.getPassword());
                userJson.put("fullName", user.getFullName());
                userJson.put("phoneNumber", user.getPhoneNumber());
                userJson.put("timeAndDate", user.getTimeAndDate());
                usersArray.put(userJson);
            }
            jsonObject.put("users", usersArray);
            writer.write(jsonObject.toString(4)); // Indented JSON string with 4 spaces
        }
    }

    public static void main(String args[]) {
        Users manageProducts = new Users();
        manageProducts.setVisible(true);
    }
}
