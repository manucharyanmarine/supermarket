package myclasses;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DashBoard extends JFrame implements ActionListener {
    // TODO table editable table changeable logout image

    private final JButton products_btn;
    private final JButton logout_btn;
    private final JButton users_btn;

    public DashBoard() {
        System.out.println("Currently in DashBoard class");
        setResizable(false);
        setTitle("Admin dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        JPanel contentPane = new JPanel();
        contentPane.setForeground(Color.LIGHT_GRAY);
        contentPane.setBackground(new Color(77, 77, 77));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        logout_btn = new JButton("Log Out");
        logout_btn.setFocusable(false);
        logout_btn.setBounds(880, 15, 89, 23);
        contentPane.add(logout_btn);

        BufferedImage productsPhoto = null;
        BufferedImage usersPhoto = null;
        try { // Load the images
            productsPhoto =
                    ImageIO.read(Objects.requireNonNull(DashBoard.class.getResource("../images/products.png")));
            usersPhoto =
                    ImageIO.read(Objects.requireNonNull(DashBoard.class.getResource("../images/users.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        products_btn = new JButton("Products");
        products_btn.setBounds(290, 150, 150, 70);
//        assert productsPhoto != null : "'room.png' not found";
//        Image products =
//                productsPhoto.getScaledInstance(
//                        products_btn.getWidth(), products_btn.getHeight(), Image.SCALE_SMOOTH);
//        products_btn.setIcon(new ImageIcon(products));
        products_btn.setText("Products");
        products_btn.setForeground(Color.WHITE);
        products_btn.setBackground(Color.LIGHT_GRAY);
        contentPane.add(products_btn);

//        JTextField products_lbl = new JTextField();
//        products_lbl.setText("Products");
//        products_lbl.setHorizontalAlignment(SwingConstants.CENTER);
//        products_lbl.setForeground(Color.WHITE);
//        products_lbl.setColumns(10);
//        products_lbl.setFont(new Font("Jokerman", Font.PLAIN, 22));
//        products_lbl.setBorder(null);
//        products_lbl.setBackground(new Color(77, 77, 77));
//        products_lbl.setBounds(310, 280, 120, 50);
//        contentPane.add(products_lbl);


        users_btn = new JButton("Users");
        users_btn.setBounds(530, 150, 150, 70);
//        assert usersPhoto != null : "'room.png' not found";
//        Image users = usersPhoto.getScaledInstance(
//                users_btn.getWidth(), users_btn.getHeight(), Image.SCALE_SMOOTH);
//        users_btn.setIcon(new ImageIcon(users));
        users_btn.setText("Users");
        users_btn.setForeground(Color.WHITE);
        users_btn.setBackground(Color.LIGHT_GRAY);
        contentPane.add(users_btn);

//        JTextField users_lbl = new JTextField();
//        users_lbl.setText("Users");
//        users_lbl.setHorizontalAlignment(SwingConstants.CENTER);
//        users_lbl.setForeground(Color.WHITE);
//        users_lbl.setColumns(10);
//        users_lbl.setFont(new Font("Jokerman", Font.PLAIN, 22));
//        users_lbl.setBorder(null);
//        users_lbl.setBackground(new Color(77, 77, 77));
//        users_lbl.setBounds(550, 280, 120, 50);
//        contentPane.add(users_lbl);


        logout_btn.addActionListener(this);
        products_btn.addActionListener(this);
        users_btn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logout_btn) { // Prompt user for confirmation before logging out
            int yesORno = JOptionPane.showConfirmDialog(
                    null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == JOptionPane.YES_OPTION) { // If user confirms, hide current window and open Login window
                this.setVisible(false);
                System.out.println("Exited from DashBoard class");
                new Login();
            }
        } else if (e.getSource() == products_btn) {
            setVisible(false);
            System.out.println("Exited from DashBoard class");
            new Products();
        } else if (e.getSource() == users_btn) {
            setVisible(false);
            System.out.println("Exited from DashBoard class");
            new Users();
        }
    }
}
