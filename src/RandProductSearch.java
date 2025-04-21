import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RandProductSearch extends JFrame {
    FileStream fileStream;

    JPanel mainPanel, searchPanel, displayPanel, controlPanel;

    JLabel displayLabel;
    JTextArea displayTA, searchTA;
    JScrollPane scroller;

    JButton exitBtn, searchBtn;

    public RandProductSearch(FileStream fileStream) {
        this.fileStream = fileStream;

        setTitle("Product Search");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        createSearchPanel();
        createDisplayPanel();
        createControlPanel();
        add(mainPanel);
        setVisible(true);
    }
    private void createSearchPanel() {
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchTA = new JTextArea();
        searchBtn = new JButton("Search");
        searchBtn.addActionListener(e -> {
            fileStream.searchProduct(searchTA.getText());
        });
        searchPanel.add(searchTA, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);
        mainPanel.add(searchPanel, BorderLayout.NORTH);
    }
    private void createDisplayPanel() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayTA = new JTextArea();
        displayTA.setEditable(false);
        displayTA.setFont(new Font("Monospaced", Font.PLAIN, 12));
        displayLabel = new JLabel("Enter key phrases in the search box above to display all products containing that text.");

        scroller = new JScrollPane(displayTA);

        displayPanel.add(displayLabel, BorderLayout.NORTH);
        displayPanel.add(scroller, BorderLayout.CENTER);

        mainPanel.add(displayPanel, BorderLayout.CENTER);
    }
    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1,1));
        exitBtn = new JButton("Exit");
        exitBtn.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit search?","Confirm Exit",JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        controlPanel.add(exitBtn);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }
}

