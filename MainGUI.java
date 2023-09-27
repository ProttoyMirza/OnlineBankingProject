import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainGUI extends JFrame {
    private BankAccount userAccount;

    private JLabel balanceLabel;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton remittanceButton;
    private JButton mobileRechargeButton;
    private JButton educationFeeButton;
    private JButton billPaymentButton;

    public MainGUI() {
        // Initialize the user account (replace with your actual account logic)
        userAccount = new BankAccount("123456789");

        // Set up the JFrame
        setTitle("Online Banking");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create GUI components
        balanceLabel = new JLabel("Current balance: $" + userAccount.getBalance());
        depositButton = new JButton("Deposit");
        withdrawButton = new JButton("Withdraw");
        remittanceButton = new JButton("Remittance");
        mobileRechargeButton = new JButton("Mobile Recharge");
        educationFeeButton = new JButton("Pay Education Fee");
        billPaymentButton = new JButton("Pay Bill");

        // Add action listeners to the buttons
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement deposit logic here
                double depositAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter deposit amount:"));
                userAccount.deposit(depositAmount);
                updateBalanceLabel();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement withdraw logic here
				/*double withdrawAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter withdraw amount:"));
				userAccount.withdraw(withdrawAmount);
				updateBalanceLabel();*/
				 double withdrawAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter withdrawal amount:"));
                if (userAccount.getBalance() >= withdrawAmount) {
                    userAccount.withdraw(withdrawAmount);
                    updateBalanceLabel();
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance.");
                }
            }
        });

   remittanceButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Create a dialog to input remittance details
        JPanel panel = new JPanel();
        JTextField recipientAccountField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JTextField countryField = new JTextField(10);

        panel.setLayout(new GridLayout(3, 2));
        panel.add(new JLabel("Recipient's Account Number:"));
        panel.add(recipientAccountField);
        panel.add(new JLabel("Amount to Send:"));
        panel.add(amountField);
        panel.add(new JLabel("Recipient's Country:"));
        panel.add(countryField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Remittance Details", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String recipientAccountNumber = recipientAccountField.getText();
                double amountToSend = Double.parseDouble(amountField.getText());
                String selectedCountry = countryField.getText();

                // Check if the sender has sufficient balance to send the money
                if (userAccount.getBalance() >= amountToSend) {
                    // Implement logic to deposit the original amount into the receiver's account
                    BankAccount receiverAccount = new BankAccount(recipientAccountNumber);
                    receiverAccount.deposit(amountToSend);

                    // Calculate the remittance charge based on the selected country
                     double remittanceCharge = calculateRemittanceCharge(selectedCountry);

                    // Calculate the total amount to send, including the remittance charge
                    double totalAmountToSend = amountToSend + remittanceCharge;

                    // Withdraw the total amount from the sender's account
                    userAccount.withdraw(totalAmountToSend);

                    // Display a confirmation message
                    String message = "Money sent successfully to " + selectedCountry + "\n" +
                            "Remittance charge: $" + remittanceCharge + "\n" +
                            "Total amount sent: $" + totalAmountToSend;
                    JOptionPane.showMessageDialog(null, message);
                    updateBalanceLabel();
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient balance to send money.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
            }
        }
    }
});


        mobileRechargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement mobile recharge logic here
            }
        });

        educationFeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement education fee payment logic here
            }
        });

        billPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement bill payment logic here
            }
        });

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(remittanceButton);
        buttonPanel.add(mobileRechargeButton);
        buttonPanel.add(educationFeeButton);
        buttonPanel.add(billPaymentButton);

        // Add components to the JFrame
        setLayout(new BorderLayout());
        add(balanceLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Current balance: $" + userAccount.getBalance());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI mainGUI = new MainGUI();
                mainGUI.setVisible(true);
            }
        });
    }
}
