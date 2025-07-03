import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class PageOne extends javax.swing.JFrame {

    // VARIABLES
    private javax.swing.JPanel container;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel error;
    private javax.swing.JLabel background;
    private javax.swing.JTextField rowText;
    private javax.swing.JTextField columnText;
    private javax.swing.JTextField randomText;
    private Button startNewGame;
    public static int iCount;
    public static int jCount;
    public static int randomAmount;
    private final Toolkit toolkit;
    private final Dimension size;

    public static void main(String args[]) {
        /* SET THE NIMBUS LOOK AND FEEL */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PageOne.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        new PageOne().setVisible(true);
    }

    public PageOne() {
        toolkit = getToolkit();
        size = toolkit.getScreenSize();
        makeComponents();

        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
    }
    private void makeComponents() {

        container = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        error = new javax.swing.JLabel();
        rowText = new javax.swing.JTextField();
        columnText = new javax.swing.JTextField();
        randomText = new javax.swing.JTextField();
        startNewGame = new Button();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Yin Yang");
        setPreferredSize(new java.awt.Dimension(900, 500));
        setResizable(false);
        getContentPane().setLayout(null);

        container.setLayout(null);

        jLabel1.setFont(new java.awt.Font("", 3, 30));
        jLabel1.setForeground(Color.BLACK);
        jLabel1.setText("Size");
        container.add(jLabel1);
        jLabel1.setBounds(420, 50, 210, 50);

        jLabel2.setFont(new java.awt.Font("", 3, 24));
        jLabel2.setForeground(Color.BLACK);
        jLabel2.setText("Amount of help");
        container.add(jLabel2);
        jLabel2.setBounds(360, 210, 280, 50);

        error.setFont(new java.awt.Font("", 1, 20));
        error.setForeground(new java.awt.Color(255, 0, 0));
        container.add(error);
        error.setText("");
        error.setBounds(360, 420, 470, 40);

        rowText.setBackground(Color.WHITE);
        rowText.setFont(new java.awt.Font("", 1, 25));
        rowText.setForeground(Color.BLACK);
        rowText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        container.add(rowText);
        rowText.setBounds(370, 100, 60, 50);

        columnText.setBackground(Color.WHITE);
        columnText.setFont(new java.awt.Font("", 1, 25));
        columnText.setForeground(Color.BLACK);
        columnText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        container.add(columnText);
        columnText.setBounds(463, 100, 60, 50);

        randomText.setBackground(Color.WHITE);
        randomText.setFont(new java.awt.Font("", 1, 25));
        randomText.setForeground(Color.BLACK);
        randomText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        container.add(randomText);
        randomText.setBounds(400, 280, 83, 50);

        startNewGame.setForeground(Color.BLACK);
        startNewGame.setText("New Game");
        startNewGame.setFont(new java.awt.Font("", 1, 20));
        startNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });
        container.add(startNewGame);
        startNewGame.setBounds(355, 360, 170, 60);
        background.setBounds(0, 0, 900, 500);

        getContentPane().add(container);
        container.setBounds(0, 0, 900, 500);

        pack();
    }

    private void startActionPerformed(java.awt.event.ActionEvent evt) {

        if (rowText.getText().trim().equals("") || columnText.getText().trim().equals("") || randomText.getText().trim().equals("")) {
            error.setText("Enter all required items!");
            return;
        }
        iCount = stringToInt(rowText.getText().trim());
        jCount = stringToInt(columnText.getText().trim());
        randomAmount = stringToInt(randomText.getText().trim());

        if (iCount == -1 || jCount == -1 || randomAmount == -1) {
            error.setText("Invalid Input!");
            return;
        }
        if (iCount < 3 || jCount < 3) {
            error.setText("Size Can not be less than 3!");
            return;
        }
        if (iCount > 10 || jCount > 10) {
            error.setText("Size Can not be more than 10!");
            return;
        }
        if (randomAmount < 0) {
            error.setText("Amount of help Can not be less than Zero!");
            return;
        }
        if (randomAmount > (iCount * jCount)) {
            error.setText("Random Amount Can't be more than the Cells!");
            return;
        }
        new Start().setVisible(true);
        this.dispose();

    }

    private int stringToInt(String String) {
        int result;
        try {
            result = Integer.parseInt(String);
        } catch (NumberFormatException e) {
            result = -1;
        }
        return result;
    }
}
