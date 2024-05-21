package myclasses;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashBoard extends JFrame implements ActionListener {
    private final JButton products_btn;
    private final JButton logout_btn;
    private final JButton users_btn;

    public DashBoard() {
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

        products_btn = new JButton("Products");
        products_btn.setBounds(290, 150, 150, 70);
        products_btn.setText("Products");
        products_btn.setForeground(Color.WHITE);
        products_btn.setBackground(Color.LIGHT_GRAY);
        contentPane.add(products_btn);

        users_btn = new JButton("Users");
        users_btn.setBounds(530, 150, 150, 70);
        users_btn.setText("Users");
        users_btn.setForeground(Color.WHITE);
        users_btn.setBackground(Color.LIGHT_GRAY);
        contentPane.add(users_btn);

        logout_btn.addActionListener(this);
        products_btn.addActionListener(this);
        users_btn.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logout_btn) {
            int yesORno = JOptionPane.showConfirmDialog(
                    null, "Are you sure ?", "Alert!", JOptionPane.YES_NO_OPTION);

            if (yesORno == JOptionPane.YES_OPTION) {
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
