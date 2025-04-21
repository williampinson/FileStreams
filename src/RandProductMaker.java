import javax.swing.*;
import java.awt.*;

public class RandProductMaker extends JFrame {
    JPanel mainPanel, displayPanel, formPanel, controlPanel;
    JLabel idLabel, nameLabel, descriptionLabel, costLabel;
    JTextField idTextField, nameTextField, descriptionTextField, costTextField, recordCountTextField;
    JButton addItemButton, saveFileButton, prodSearchBtn, exitButton;

    private FileStream fileStream;

    public RandProductMaker(FileStream fileStream) {
        this.fileStream = fileStream;

        setTitle("Random Access Product Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 225);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        createDisplayPanel();
        createControlPanel();
        add(mainPanel);
        setVisible(true);
    }
    private void createDisplayPanel() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        recordCountTextField = new JTextField(3);
        recordCountTextField.setEditable(false);
        recordCountTextField.setText("Added Records: " + fileStream.getProductCount());
        displayPanel.add(recordCountTextField, BorderLayout.NORTH);
        createFormPanel();
        displayPanel.add(formPanel, BorderLayout.CENTER);
        addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(e -> {
            if (isValidInput()) {
                fileStream.newProduct(idTextField.getText(),nameTextField.getText(),descriptionTextField.getText(),Double.parseDouble(costTextField.getText()));
                fileStream.setProductCount(fileStream.getProductCount());
                updateGUI();
                System.out.println("Item added");
            }
        });
        displayPanel.add(addItemButton, BorderLayout.SOUTH);
        mainPanel.add(displayPanel, BorderLayout.CENTER);
    }
    private void createFormPanel() {
        formPanel = new JPanel();
        formPanel.setLayout(new BorderLayout());
        idLabel = new JLabel("   Product ID   ");
        idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel = new JLabel("   Product Name   ");
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        descriptionLabel = new JLabel("   Product Description   ");
        descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        costLabel = new JLabel("   Product Cost   ");
        costLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        idTextField = new JTextField(6);
        nameTextField = new JTextField(35);
        descriptionTextField = new JTextField(75);
        costTextField = new JTextField(6);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(4,1));
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4,1));

        leftPanel.add(idLabel);
        leftPanel.add(nameLabel);
        leftPanel.add(descriptionLabel);
        leftPanel.add(costLabel);
        rightPanel.add(idTextField);
        rightPanel.add(nameTextField);
        rightPanel.add(descriptionTextField);
        rightPanel.add(costTextField);
        formPanel.add(leftPanel, BorderLayout.WEST);
        formPanel.add(rightPanel, BorderLayout.CENTER);
    }
    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1,3));
        saveFileButton = new JButton("Save Records to File");
        saveFileButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you ready to save the product list to file?", "Confirm Save", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                fileStream.toFile();
            }
        });
        prodSearchBtn = new JButton("Product Search");
        prodSearchBtn.addActionListener(e -> fileStream.newProductSearch());
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        controlPanel.add(saveFileButton);
        controlPanel.add(prodSearchBtn);
        controlPanel.add(exitButton);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
    }
    private boolean isValidInput() {
        String id = idTextField.getText();
        String name = nameTextField.getText();
        String description = descriptionTextField.getText();
        String cost = costTextField.getText();
        double costDouble;

        if (id.isEmpty() || id.length() > RandProduct.ID_MAX_LENGTH) {
            JOptionPane.showMessageDialog(this, "Please enter a valid product ID. Max characters: " + RandProduct.ID_MAX_LENGTH, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (name.isEmpty() || name.length() > RandProduct.NAME_MAX_LENGTH) {
            JOptionPane.showMessageDialog(this, "Please enter a valid product name. Max characters: " + RandProduct.NAME_MAX_LENGTH, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (description.isEmpty() || description.length() > RandProduct.DESCRIPTION_MAX_LENGTH) {
            JOptionPane.showMessageDialog(this, "Please enter a valid product description. Max characters: " + RandProduct.DESCRIPTION_MAX_LENGTH, "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (cost.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid product cost", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            try {
                costDouble = Double.parseDouble(cost);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid product cost (Do not include any symbols besides a decimal if needed)", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }
    private void updateGUI() {
        idTextField.setText("");
        nameTextField.setText("");
        descriptionTextField.setText("");
        costTextField.setText("");
        recordCountTextField.setText("Added Records: " + fileStream.getProductCount());
    }
}
