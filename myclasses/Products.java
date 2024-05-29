package myclasses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class Products extends JFrame implements ActionListener {
    // TODO Table data edit and table shift

    private final JTable table;
    private final JTextField productPrice_fld;
    private final JTextField productQuantity_fld;
    private final JTextField productName_fld;
    private final JButton logOut_btn;
    private final JButton back_btn;
    private final JButton add_btn;
    private final JButton del_btn;
    private final JButton update_btn;
    private final JButton search_btn;
    private final JTextField search_fld;
    private final JComboBox<String> productStatus_box;
    private final JComboBox<String> productCategory_box;

    public Products() {
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

        JLabel products_lbl = new JLabel("Products");
        products_lbl.setForeground(Color.LIGHT_GRAY);
        products_lbl.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        products_lbl.setBounds(10, 3, 180, 34);
        contentPane.add(products_lbl);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(22, 130, 536, 366);
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
                            component.setBackground(new Color(54, 173, 41));
                        }

                        return component;
                    }
                });

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(150, 150, 150));
        header.setForeground(Color.WHITE);
        Font headerFont = header.getFont();
        header.setFont(headerFont.deriveFont(Font.BOLD));

        try (BufferedReader br = new BufferedReader(new FileReader("files/products.json"))) {
            JSONObject productsData = new JSONObject(new JSONTokener(br));
            JSONArray products = productsData.getJSONArray("products");

            for (int i = 0; i < products.length(); i++) {
                JSONObject product = products.getJSONObject(i);
                String[] rowData = new String[6];
                rowData[0] = product.get("id").toString();
                rowData[1] = product.getString("name");
                rowData[2] = product.getString("category");
                rowData[3] = product.getString("price");
                rowData[4] = product.getString("status");
                rowData[5] = product.get("quantity").toString();

                model.addRow(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        search_fld = new JTextField();
        search_fld.setBounds(300, 60, 151, 34);
        contentPane.add(search_fld);
        search_fld.setColumns(10);

        JLabel productName_lbl = new JLabel("Product Name");
        productName_lbl.setForeground(Color.BLACK);
        productName_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productName_lbl.setBounds(631, 115, 97, 26);
        contentPane.add(productName_lbl);

        productName_fld = new JTextField();
        productName_fld.setColumns(10);
        productName_fld.setBounds(631, 140, 160, 20);
        contentPane.add(productName_fld);

        JLabel productCategory_lbl = new JLabel("Product Category");
        productCategory_lbl.setForeground(Color.BLACK);
        productCategory_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productCategory_lbl.setBounds(631, 170, 72, 23);
        contentPane.add(productCategory_lbl);

        productCategory_box = new JComboBox<>();
        productCategory_box.setModel(new DefaultComboBoxModel<>(new String[]{"Antibiotics", "Vitamins"}));
        productCategory_box.setBounds(631, 195, 160, 22);
        contentPane.add(productCategory_box);

        JLabel productPrice_lbl = new JLabel("Product Price");
        productPrice_lbl.setForeground(Color.BLACK);
        productPrice_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productPrice_lbl.setBounds(631, 225, 97, 26);
        contentPane.add(productPrice_lbl);

        productPrice_fld = new JTextField();
        productPrice_fld.setColumns(10);
        productPrice_fld.setBounds(631, 250, 160, 20);
        contentPane.add(productPrice_fld);

        JLabel productStatus_lbl = new JLabel("Status");
        productStatus_lbl.setForeground(Color.BLACK);
        productStatus_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productStatus_lbl.setBounds(631, 280, 72, 23);
        contentPane.add(productStatus_lbl);

        productStatus_box = new JComboBox<>();
        productStatus_box.setModel(new DefaultComboBoxModel<>(new String[]{"Have", "Not Have"}));
        productStatus_box.setBounds(631, 305, 160, 22);
        contentPane.add(productStatus_box);

        JLabel productQuantity_lbl = new JLabel("Product Quantity");
        productQuantity_lbl.setForeground(Color.BLACK);
        productQuantity_lbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
        productQuantity_lbl.setBounds(631, 335, 97, 26);
        contentPane.add(productQuantity_lbl);

        productQuantity_fld = new JTextField();
        productQuantity_fld.setColumns(10);
        productQuantity_fld.setBounds(631, 360, 160, 20);
        contentPane.add(productQuantity_fld);

        add_btn = new JButton("Add");
        add_btn.setBounds(631, 420, 111, 34);
        add_btn.setFocusable(false);
        contentPane.add(add_btn);

        del_btn = new JButton("Delete");
        del_btn.setBounds(140, 60, 100, 34);
        del_btn.setFocusable(false);
        contentPane.add(del_btn);

        update_btn = new JButton("Update");
        update_btn.setBounds(20, 60, 100, 34);
        update_btn.setFocusable(false);
        contentPane.add(update_btn);

        search_btn = new JButton("Search");
        search_btn.setBounds(460, 60, 100, 34);
        search_btn.setFocusable(false);
        contentPane.add(search_btn);

        logOut_btn.addActionListener(this);
        back_btn.addActionListener(this);
        add_btn.addActionListener(this);
        del_btn.addActionListener(this);
        update_btn.addActionListener(this);
        search_btn.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer productId_fld = -1;
        String prodId = String.valueOf(productId_fld + 1);
        String prodName = productName_fld.getText();
        String prodCategory = (String) productCategory_box.getSelectedItem();
        String prodPrice = productPrice_fld.getText().trim();
        String prodStatus = (String) productStatus_box.getSelectedItem();
        String prodCount = productQuantity_fld.getText().trim();

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
        } else if (e.getSource() == add_btn) {
//            String prodName = productName_fld.getText().trim();
//            String prodPrice = productPrice_fld.getText().trim();
//            String prodCount = productQuantity_fld.getText().trim();
//            String prodCategory = productCategory_fld.getText().trim();
//            String prodStatus = productStatus_fld.getText().trim();

            // Check if product name, price, and quantity are not empty
            if (!prodName.isEmpty() && !prodPrice.isEmpty() && !prodCount.isEmpty()) {
                try {
                    // File path for the products JSON file
                    String filePath = "files/products.json";
                    File file = new File(filePath);
                    JSONArray productsArray;
                    int newProdId = 1;

                    // Read existing JSON data or create a new array
                    if (!file.exists()) {
                        file.createNewFile();
                        productsArray = new JSONArray();
                    } else {
                        // Read the content from the file
                        String content = new String(Files.readAllBytes(Paths.get(filePath)));

                        // Parse the content as a JSONObject
                        JSONObject jsonContent = new JSONObject(content);

                        // Get the "products" array from the JSONObject
                        productsArray = jsonContent.getJSONArray("products");

                        // Find the highest existing product ID
                        for (int i = 0; i < productsArray.length(); i++) {
                            JSONObject product = productsArray.getJSONObject(i);
                            int prodId1 = product.getInt("id");
                            if (prodId1 >= newProdId) {
                                newProdId = prodId1 + 1;
                            }
                        }
                    }

                    // Create a new product JSONObject
                    JSONObject newProduct = new JSONObject();
                    newProduct.put("id", newProdId);
                    newProduct.put("name", prodName);
                    newProduct.put("category", prodCategory);
                    newProduct.put("price", prodPrice);
                    newProduct.put("status", prodStatus);
                    newProduct.put("quantity", prodCount);

                    // Add the new product to the products array
                    productsArray.put(newProduct);

                    // Write the updated JSON data to the file
                    try (FileWriter fileWriter = new FileWriter(file)) {
                        // Write the entire JSON object, including the "products" key
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("products", productsArray);
                        fileWriter.write(jsonObject.toString(4)); // Indent for better readability
                    }

                    // Clear text fields
                    productName_fld.setText(null);
                    productPrice_fld.setText(null);
                    productQuantity_fld.setText(null);

                    // Refresh table data
                    refreshTable();

                    System.out.println("New Product added");

                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields.");
            }
        } else if (e.getSource() == del_btn) {
            if (JOptionPane.showConfirmDialog(
                    null, "Confirmation", "Remove This product?", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                DefaultTableModel tempTbl = (DefaultTableModel) table.getModel();
                int selectedRow = table.getSelectedRow();

                if (selectedRow != -1) {
                    String productId = tempTbl.getValueAt(selectedRow, 0).toString();
                    try {
                        File jsonFile = new File("files/products.json");
                        List<Product> products = readProductsFromJson(jsonFile);

                        // Find and remove the product with the matching ID
                        Product productToRemove = null;
                        for (Product product : products) {
                            if (product.getId().equals(productId)) {
                                productToRemove = product;
                                break;
                            }
                        }
                        if (productToRemove != null) {
                            products.remove(productToRemove);
                            writeProductsToJson(jsonFile, products);
                            tempTbl.removeRow(selectedRow);
                        } else {
                            JOptionPane.showMessageDialog(null, "Product not found!");
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
        } else if (e.getSource() == update_btn) {
            updateProduct();
        } else if (e.getSource() == search_btn) {
            searchProducts(search_fld.getText());
        }
    }
    private void searchProducts(String searchText) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try {
            // Read the content from the file
            String content = new String(Files.readAllBytes(Paths.get("files/products.json")));

            // Parse the content as a JSONObject
            JSONObject jsonContent = new JSONObject(content);

            // Get the "products" array from the JSONObject
            JSONArray productsArray = jsonContent.getJSONArray("products");

            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject product = productsArray.getJSONObject(i);
                String[] rowData = {
                        product.get("id").toString(),
                        product.getString("name"),
                        product.getString("category"),
                        product.getString("price"),
                        product.getString("status"),
                        product.get("quantity").toString()
                };
                model.addRow(rowData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private List<Product> readProductsFromJson(File file) throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            JSONObject jsonObject = new JSONObject(jsonContent.toString());
            JSONArray productsArray = jsonObject.getJSONArray("products");
            for (int i = 0; i < productsArray.length(); i++) {
                JSONObject productJson = productsArray.getJSONObject(i);
                Product product = new Product(
                        productJson.getString("id"),
                        productJson.getString("name"),
                        productJson.getString("category"),
                        productJson.getString("status"),
                        productJson.getInt("quantity"),
                        productJson.getString("price")
                );
                products.add(product);
            }
        }
        return products;
    }

    private void writeProductsToJson(File file, List<Product> products) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            JSONObject jsonObject = new JSONObject();
            JSONArray productsArray = new JSONArray();
            for (Product product : products) {
                JSONObject productJson = new JSONObject();
                productJson.put("id", product.getId());
                productJson.put("name", product.getName());
                productJson.put("category", product.getCategory());
                productJson.put("status", product.getStatus());
                productJson.put("quantity", product.getQuantity());
                productJson.put("price", product.getPrice());
                productsArray.put(productJson);
            }
            jsonObject.put("products", productsArray);
            writer.write(jsonObject.toString(4)); // Indented JSON string with 4 spaces
        }
    }

    public void updateProduct() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            // Get the product ID from the first column of the selected row
            String prodId = table.getValueAt(selectedRow, 0).toString();

            // Prompt the user to enter new details
            JTextField nameField = new JTextField(table.getValueAt(selectedRow, 1).toString());
            JTextField categoryField = new JTextField(table.getValueAt(selectedRow, 2).toString());
            JTextField priceField = new JTextField(table.getValueAt(selectedRow, 3).toString());
            JTextField statusField = new JTextField(table.getValueAt(selectedRow, 4).toString());
            JTextField quantityField = new JTextField(table.getValueAt(selectedRow, 5).toString());

            Object[] message = {
                    "Name:", nameField,
                    "Category:", categoryField,
                    "Price:", priceField,
                    "Status:", statusField,
                    "Quantity:", quantityField,
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Update Product", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    // File path for the products JSON file
                    String filePath = "files/products.json";
                    File file = new File(filePath);

                    // Read the content of the JSON file
                    String content = new String(Files.readAllBytes(Paths.get(filePath)));
                    JSONObject jsonObject = new JSONObject(content); // Parse content as JSONObject
                    JSONArray productsArray = jsonObject.getJSONArray("products"); // Extract the "products" array

                    // Find and remove the existing product from the JSON array
                    for (int i = 0; i < productsArray.length(); i++) {
                        JSONObject product = productsArray.getJSONObject(i);
                        if (product.getString("id").equals(prodId)) {
                            productsArray.remove(i);
                            break;
                        }
                    }

                    // Create a new JSON object for the updated product
                    JSONObject updatedProduct = new JSONObject();
                    updatedProduct.put("id", prodId);
                    updatedProduct.put("name", nameField.getText().trim());
                    updatedProduct.put("category", categoryField.getText().trim());
                    updatedProduct.put("price", priceField.getText().trim());
                    updatedProduct.put("status", statusField.getText().trim());
                    updatedProduct.put("quantity", quantityField.getText().trim());

                    // Add the updated product to the JSON array
                    productsArray.put(updatedProduct);

                    // Write the updated JSON data to the file
                    try (FileWriter fileWriter = new FileWriter(file)) {
                        fileWriter.write(jsonObject.toString(4)); // Indent for better readability
                        fileWriter.flush(); // Ensure data is actually written
                    }

                    // Update the table model with new values
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setValueAt(nameField.getText().trim(), selectedRow, 1);
                    model.setValueAt(categoryField.getText().trim(), selectedRow, 2);
                    model.setValueAt(priceField.getText().trim(), selectedRow, 3);
                    model.setValueAt(statusField.getText().trim(), selectedRow, 4);
                    model.setValueAt(quantityField.getText().trim(), selectedRow, 5);

                    // Refresh table data
                    table.repaint();

                    System.out.println("Product updated successfully");

                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while updating the JSON file.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to update.");
        }
    }

    public static void main(String args[]) {
        Products manageProducts = new Products();
        manageProducts.setVisible(true);
    }
}
