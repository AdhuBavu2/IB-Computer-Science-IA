package MainFrame.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class DocumentPanel extends JPanel implements ActionListener {
    private JButton uploadButton;
    private JLabel uploadTitle;
    private ArrayList<String> listOfDocuments = new ArrayList<String>();
    private JLabel comboboxLabel;
    private JComboBox documentCombobox;
    private JLabel uploadFile;
    private JButton submit;
    private JLabel documentLabel;
    private JLabel linkLabel;

    public DocumentPanel() {
        setBackground(new Color(0, 120, 246));
        setLayout(null);

        documentLabel = new JLabel("Hillcrest Track Documents");
        documentLabel.setBounds(50, 30, 350, 100);
        documentLabel.setFont(new Font("Open Sans", Font.BOLD, 24));
        documentLabel.setForeground(Color.BLACK);
        add(documentLabel);

        linkLabel = new JLabel();
        linkLabel.setText("<html><a href=\\\"http://www.example.com\\\">Click here to visit example.com</a></html>");
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.example.com"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(linkLabel);


        listOfDocuments.add("$75 Dollar Participation Fee");
        listOfDocuments.add("Assumption Of Risk Form");
        listOfDocuments.add("Athlete Physical Form");

        comboboxLabel = new JLabel("Select Document");
        comboboxLabel.setBounds(550, 97, 350, 100);
        comboboxLabel.setFont(new Font("Open Sans", Font.BOLD, 16));
        comboboxLabel.setForeground(Color.BLACK);
        add(comboboxLabel);

        documentCombobox = new JComboBox(listOfDocuments.toArray(new String[0]));
        documentCombobox.setBounds(550, 170, 225, 33);
        add(documentCombobox);

        uploadTitle = new JLabel("Upload Completed Documents");
        uploadTitle.setBounds(550, 30, 350, 100);
        uploadTitle.setFont(new Font("Open Sans", Font.BOLD, 24));
        uploadTitle.setForeground(Color.BLACK);
        add(uploadTitle);

        uploadFile = new JLabel("Upload Document");
        uploadFile.setBounds(550, 197, 350, 100);
        uploadFile.setFont(new Font("Open Sans", Font.BOLD, 16));
        uploadFile.setForeground(Color.BLACK);
        add(uploadFile);

        uploadButton = new JButton("Upload File");
        uploadButton.setBounds(550, 270, 100, 30);
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(DocumentPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer_science_ia", "root", "");
                        String name = selectedFile.getName();
                        byte[] content = Files.readAllBytes(selectedFile.toPath());
                        String sql = "INSERT INTO uploaded_documents (name, content) VALUES (?, ?)";
                        try (PreparedStatement statement = connection.prepareStatement(sql)) {
                            statement.setString(1, name);
                            statement.setBytes(2, content);
                            statement.executeUpdate();
                        }
                        JOptionPane.showMessageDialog(DocumentPanel.this, "File uploaded successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(DocumentPanel.this, "Error uploading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(uploadButton);

        submit = new JButton("Submit Document");
        submit.addActionListener(this);
        submit.setBounds(550, 350, 225, 33);
        submit.setFocusable(false);
        submit.setBackground(new Color(246, 254, 219));
        submit.setOpaque(true);
        add(submit);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
