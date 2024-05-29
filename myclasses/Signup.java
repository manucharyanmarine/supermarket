package myclasses;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
public class Signup extends JFrame implements ActionListener {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JPasswordField confirmPassField;
    private final JToggleButton EyeBtn;
    private final JToggleButton EyeBtn2;
    private final JTextField fullField;
    private final ImageIcon on;
    private final ImageIcon off;
    private final JButton signin;
    private final JButton signup;
    private final JButton exitButton;
    private final JTextField phoneNumberField;

    public Signup() {
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setForeground(Color.LIGHT_GRAY);
        contentPane.setBackground(new Color(77, 77, 77));
        contentPane.setBorder(null);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel SignUpText = new JLabel("SIGN UP");
        SignUpText.setForeground(new Color(215, 210, 203));
        SignUpText.setFont(new Font("Verdana", Font.BOLD, 22));
        SignUpText.setBounds(420, 10, 114, 30);
        contentPane.add(SignUpText);

        JLabel fullName = new JLabel("Full Name :");
        fullName.setForeground(Color.WHITE);
        fullName.setFont(new Font("Verdana", Font.PLAIN, 17));
        fullName.setBounds(240, 68, 100, 30);
        contentPane.add(fullName);

        fullField = new JTextField();
        fullField.setBounds(440, 68, 250, 30);
        Font fullFieldFont = new Font("Times New Roman", Font.PLAIN, 17);
        fullField.setFont(fullFieldFont);
        fullField.setOpaque(false);
        fullField.setForeground(new Color(219, 226, 233));
        fullField.setBorder(BorderFactory.createEmptyBorder());
        Border Border = BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(128, 128, 128));
        fullField.setBorder(Border);
        contentPane.add(fullField);

        JLabel username = new JLabel("User Name :");
        username.setBounds(240, 105, 114, 30);
        Font usernameFont = new Font("Verdana", Font.PLAIN, 17);
        username.setFont(usernameFont);
        username.setForeground(Color.white);
        contentPane.add(username);

        usernameField = new JTextField();
        usernameField.setBounds(440, 105, 248, 30);
        Font userfieldFont = new Font("Times New Roman", Font.PLAIN, 17);
        usernameField.setFont(userfieldFont);
        usernameField.setOpaque(false);
        usernameField.setForeground(new Color(219, 226, 233));
        usernameField.setBorder(BorderFactory.createEmptyBorder());
        usernameField.setBorder(Border);
        contentPane.add(usernameField);

        JLabel phoneNumber = new JLabel("Phone Number :");
        phoneNumber.setForeground(Color.WHITE);
        phoneNumber.setFont(new Font("Verdana", Font.PLAIN, 17));
        phoneNumber.setBounds(240, 148, 150, 27);
        contentPane.add(phoneNumber);

        phoneNumberField = new JTextField();
        phoneNumberField.setBounds(440, 148, 248, 30);
        Font emailFieldFont = new Font("Times New Roman", Font.PLAIN, 17);
        phoneNumberField.setFont(emailFieldFont);
        phoneNumberField.setOpaque(false);
        phoneNumberField.setForeground(new Color(219, 226, 233));
        phoneNumberField.setBorder(BorderFactory.createEmptyBorder());
        phoneNumberField.setBorder(Border);
        contentPane.add(phoneNumberField);

        JLabel password = new JLabel("Password :");
        password.setForeground(Color.WHITE);
        password.setFont(new Font("Verdana", Font.PLAIN, 17));
        password.setBounds(240, 189, 106, 30);
        contentPane.add(password);

        passwordField = new JPasswordField();
        passwordField.setBounds(440, 189, 248, 30);
        Font passfieldFont = new Font("Verdana", Font.PLAIN, 18);
        passwordField.setFont(passfieldFont);
        passwordField.setEchoChar('*');
        passwordField.setOpaque(false);
        passwordField.setForeground(new Color(219, 226, 233));
        passwordField.setBorder(BorderFactory.createEmptyBorder());
        passwordField.setBorder(Border);
        contentPane.add(passwordField);

        JLabel confirmPassTxt = new JLabel("Confirm Password :");
        confirmPassTxt.setForeground(Color.WHITE);
        confirmPassTxt.setFont(new Font("Verdana", Font.PLAIN, 17));
        confirmPassTxt.setBounds(240, 229, 173, 30);
        contentPane.add(confirmPassTxt);

        confirmPassField = new JPasswordField();
        confirmPassField.setBounds(440, 229, 248, 30);
        Font confirmPassFieldFont = new Font("Verdana", Font.PLAIN, 18);
        confirmPassField.setFont(confirmPassFieldFont);
        confirmPassField.setEchoChar('*');
        confirmPassField.setOpaque(false);
        confirmPassField.setForeground(new Color(219, 226, 233));
        confirmPassField.setBorder(BorderFactory.createEmptyBorder());
        confirmPassField.setBorder(Border);
        contentPane.add(confirmPassField);

        BufferedImage imgOn = null;
        BufferedImage imgOff = null;
        try {
            imgOn = ImageIO.read(Objects.requireNonNull(Login.class.getResource("../images/show.png")));
            imgOff = ImageIO.read(Objects.requireNonNull(Login.class.getResource("../images/hide.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        assert imgOn != null : "The 'show.png' image could not be loaded";
        Image imgON = imgOn.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        assert imgOff != null : "The 'hide.png' image could not be loaded";
        Image imgOFF = imgOff.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        on = new ImageIcon(imgON);
        off = new ImageIcon(imgOFF);
        EyeBtn = new JToggleButton(off);
        EyeBtn.setOpaque(false);
        EyeBtn.setForeground(Color.DARK_GRAY);
        EyeBtn.setBackground(Color.DARK_GRAY);
        EyeBtn.setBounds(700, 189, 29, 30);
        contentPane.add(EyeBtn);

        EyeBtn2 = new JToggleButton(off);
        EyeBtn2.setOpaque(false);
        EyeBtn2.setOpaque(false);
        EyeBtn2.setForeground(Color.DARK_GRAY);
        EyeBtn2.setBackground(Color.DARK_GRAY);
        EyeBtn2.setBounds(700, 229, 29, 30);
        contentPane.add(EyeBtn2);

        signin = new JButton("Already have an account?");
        signin.setFont(new Font("Papyrus", Font.BOLD, 17));
        signin.setForeground(new Color(155, 157, 196));
        signin.setBackground(Color.DARK_GRAY);
        signin.setBounds(350, 405, 264, 47);
        signin.setFocusable(false);
        contentPane.add(signin);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        exitButton.setBounds(890, 15, 70, 21);
        exitButton.setFocusable(false);
        contentPane.add(exitButton);

        signup = new JButton("Sign up");
        signup.setFocusable(false);
        signup.setBounds(420, 298, 110, 34);
        contentPane.add(signup);
        signup.setFont(new Font("Tahoma", Font.BOLD, 15));

        signup.addActionListener(this);
        exitButton.addActionListener(this);
        signin.addActionListener(this);
        EyeBtn.addActionListener(this);
        EyeBtn2.addActionListener(this);

        this.setVisible(true);
    }

    public static boolean isValidFullname(String fullname) {
        System.out.println("isValidFullname function called");
        String pattern = "^(?!.*\\d)(?!.*[^a-zA-Z0-9 .'-])(?!.*[ .'-]{2,})[a-zA-Z0-9 .'-]+$";
        System.out.println("isValidFullname function executed successfully");
        return fullname.matches(pattern);
    }
    public void actionPerformed(ActionEvent e) {
        // Get user input
        String user = usernameField.getText(); // Get username
        String pass = String.valueOf(passwordField.getPassword()); // Get password
        String confpass = String.valueOf(confirmPassField.getPassword()); // Get confirmed password
        String name = fullField.getText(); // Get full name
        String phoneNumber = phoneNumberField.getText(); // Get phone number

        // Check if fields are empty
        boolean userEmpty = user.isEmpty(); // Check if username is empty
        boolean passEmpty = pass.isEmpty(); // Check if password is empty
        boolean confEmpty = confpass.isEmpty(); // Check if confirmed password is empty
        boolean nameEmpty = name.isEmpty(); // Check if full name is empty
        boolean phoneEmpty = phoneNumber.isEmpty(); // Check if phone number is empty

        // Check if password matches confirmed password
        boolean check = pass.equals(confpass);
        int numcount = 0;

        try {
            // Check phone number length
            if (phoneNumber.length() != 11) {
                numcount++; // Increment numcount if phone number length is not equal to 11
            }
        } catch (Exception ex) {
            numcount = 1; // Set numcount to 1 if an exception occurs
        }

        if (e.getSource() == signup) {
            if (userEmpty || passEmpty || confEmpty || nameEmpty || phoneEmpty) {
                // Display an error message if any field is empty
                JOptionPane.showMessageDialog(
                        null, "Please fill all of the fields.", "Error!", JOptionPane.WARNING_MESSAGE);
            } else if (!isValidFullname(name)) {
                // Display an error message for invalid Fullname
                JOptionPane.showMessageDialog(
                        null,
                        "Invalid fullname. Please enter a valid fullname.",
                        "Error!",
                        JOptionPane.WARNING_MESSAGE);
            } else if (!validateUsername(user)) {
                // Display an error message for invalid username
                JOptionPane.showMessageDialog(
                        null,
                        "Invalid username. Please enter a valid username.",
                        "Error!",
                        JOptionPane.WARNING_MESSAGE);
            } else if (numcount > 0) {
                // Display an error message for invalid phone number
                JOptionPane.showMessageDialog(
                        null, "Invalid Phone Number", " Error!", JOptionPane.WARNING_MESSAGE);
            } else if (!check) {
                // Display an error message if password doesn't match
                JOptionPane.showMessageDialog(
                        null, "Password is not matching", " Error!", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    File userFile = new File("./files/user_login.json");
                    if (!userFile.exists()) {
                        userFile.createNewFile();
                        JSONObject userData = new JSONObject();
                        userData.put("users", new JSONArray());
                        try (FileWriter fileWriter = new FileWriter(userFile)) {
                            fileWriter.write(userData.toString());
                        }
                    }

                    File adminFile = new File("./files/admin_login.json");
                    if (!adminFile.exists()) {
                        createDefaultAdminFile(adminFile);
                    }

                    JSONObject userData = readJsonFile("./files/user_login.json");
                    JSONObject adminData = readJsonFile("./files/admin_login.json");

                    if (userData != null && adminData != null) {
                        boolean userExists = checkUserExists(user, userData);
                        boolean adminExists = checkAdminExists(user, adminData);

                        if (!userExists && !adminExists) {
                            JSONArray users = userData.getJSONArray("users");

                            JSONObject newUser = new JSONObject();
                            newUser.put("fullName", name);
                            newUser.put("username", user);
                            newUser.put("password", pass);
                            newUser.put("phoneNumber", phoneNumber);
                            newUser.put("timeAndDate", getCurrentTimeAndDate());

                            users.put(newUser);

                            try (FileWriter fileWriter = new FileWriter(userFile)) {
                                fileWriter.write(userData.toString());
                            }

                            System.out.println("New User details added");
                            this.setVisible(false);
                            new Login(); // Open the login window
                        } else {
                            // Display a warning message if username is already taken
                            JOptionPane.showMessageDialog(
                                    null, "User name already taken", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == EyeBtn) {
            if (EyeBtn.isSelected()) {
                EyeBtn.setIcon(on); // Set eye icon to "on"
                passwordField.setEchoChar((char) 0); // Show password characters
            } else {
                EyeBtn.setIcon(off); // Set eye icon to "off"
                passwordField.setEchoChar('*'); // Hide password characters
            }
        } else if (e.getSource() == EyeBtn2) {
            if (EyeBtn2.isSelected()) {
                EyeBtn2.setIcon(on);
                confirmPassField.setEchoChar((char) 0); // Show password characters
            } else {
                EyeBtn2.setIcon(off);
                confirmPassField.setEchoChar('*'); // Hide password characters
            }
        } else if (e.getSource() == signin) {
            this.setVisible(false);
            new Login();
        } else if (e.getSource() == exitButton) {
            int yesORno =
                    JOptionPane.showConfirmDialog(
                            null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == 0) {
                System.exit(1);
            }
        }
    }

    private void createDefaultAdminFile(File file) throws IOException {
        JSONObject admin = new JSONObject();
        admin.put("username", "admin");
        admin.put("password", "admin");

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(admin.toString());
        }
    }

    private JSONObject readJsonFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return new JSONObject(new JSONTokener(reader));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean checkUserExists(String username, JSONObject userData) {
        JSONArray users = userData.getJSONArray("users");
        for (int i = 0; i < users.length(); i++) {
            JSONObject userObj = users.getJSONObject(i);
            if (userObj.getString("username").equals(username)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkAdminExists(String username, JSONObject adminData) {
        return adminData.getString("username").equals(username);
    }

    private String getCurrentTimeAndDate() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm a, dd/MM/yyyy");
        return myDateObj.format(myFormatObj);
    }

    public boolean validateUsername(String username) {
        System.out.println("validateUsername function called");
        // Check for spaces
        if (username.contains(" ")) {
            return false;
        }

        // Check for symbols and allowed formats using regular expression
        if (!username.matches("^[a-zA-Z0-9]+$")
                && !username.matches("^[a-zA-Z]+$")
                && !username.matches("^[a-zA-Z]+[0-9]+$")) {
            return false;
        }

        // Check length
        int length = username.length();
        System.out.println("validateUsername function executed successfully");
        return length >= 3 && length <= 20;
    }
}
