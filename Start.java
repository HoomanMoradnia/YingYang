import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.Timer;

public class Start extends javax.swing.JFrame  {

    public static final int BLACK = 0;
    public static final int GRAY  = 1;
    public static final int WHITE = 2;
    public final int iCount;
    public final int jCount;
    public final int randomCount;

    // VARIABLES
    private javax.swing.JPanel container;
    private javax.swing.JPanel buttonHolder;
    private javax.swing.JLabel error;
    private javax.swing.JLabel jLabel1;
    private Button[][] button;
    private Button undoButton;
    private Button redoButton;
    private Button paintButton;
    // END OF VARIABLES

    private int[][] buttonColor;
    private int[] colored;
    private int colorWith;
    private int widthContainer;
    private int widthHolder;
    private int heightContainer;
    private int heightHolder;
    private int buttonWidth;
    private Button clickedBtn;
    private String errorString;
    private int errorAlpha;
    // SIDE CHECK VARIABLES
    private int iFirst;
    private int jFirst;
    private int colorFirst;
    private boolean sideLoop;
    // END OF SIDE CHECK VARIABLES
    private ArrayList<Situation> undoList;
    private ArrayList<Situation> redoList;

    private final Toolkit toolkit;
    private final Dimension size;
    private final MouseAdapter mouseAdapter;
    private final Timer errorTimer;

    int count = 0;
    public Start() {
//                setLookAndFeel();

        iCount = PageOne.iCount;
        jCount = PageOne.jCount;
        randomCount = PageOne.randomAmount;
        buttonColor = new int[iCount][jCount];
        colored = new int[3];
        colored[BLACK] = 0;
        colored[GRAY] = iCount * jCount;
        colored[WHITE] = 0;
        colorWith = BLACK;
        errorString = "";
        errorAlpha = 255;
        iFirst = 0;
        jFirst = 0;
        colorFirst = GRAY;
        sideLoop = false;
        undoList = new ArrayList<>();
        redoList = new ArrayList<>();

//        icon = PageOne.icon;
        toolkit = getToolkit();
        size = toolkit.getScreenSize();
        widthContainer = percent(size.width, 66);
        heightContainer = percent(size.height, 80);
        widthHolder = percent(widthContainer, 80);
        heightHolder = percent(heightContainer, 78);
        buttonWidth = calculateWidth(iCount, jCount, widthHolder, heightHolder);

        mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    leftClicked((Button) e.getSource());
                }
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    rightClicked((Button) e.getSource());
                }
            }
        };

        errorTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                error.setForeground(new Color(255, 0, 0, errorAlpha));
                if (errorAlpha == 255)
                    errorTimer.setDelay(50);
                errorAlpha -= 50;
                if (errorAlpha < 0)
                    errorAlpha = 0;
                if (errorAlpha == 0) {
                    count = 0;
                    errorTimer.stop();
                }
            }
        });

        makeComponents();

        setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);

        chooseRandom(randomCount);
    }
    private void makeComponents() {

        container = new javax.swing.JPanel();
        buttonHolder = new javax.swing.JPanel();
        error = new javax.swing.JLabel();
        undoButton = new Button();
        redoButton = new Button();
        jLabel1 = new javax.swing.JLabel();
        paintButton = new Button();
        button = new Button[iCount][jCount];
        for (int i = 0; i < iCount; i++) {
            for (int j = 0; j < jCount; j++) {
                button[i][j] = new Button();
            }
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Yin Yang");
        setPreferredSize(new Dimension(widthContainer, heightContainer));
        setMinimumSize(new Dimension(percent(size.width, 60), percent(size.height, 70)));
        setResizable(false);
        getContentPane().setLayout(null);

        container.setLayout(null);

        undoButton.setForeground(Color.BLACK);
        undoButton.setText("Undo");
        undoButton.setFont(new Font("", 1, percent(widthContainer, 2)));
        undoButton.setVisible(false);
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                undoBtnActionPerformed();
            }
        });
        container.add(undoButton);
        undoButton.setBounds(percent(widthContainer, 10), percent(heightContainer, 84), percent(widthContainer, 8), percent(heightContainer, 8));

        redoButton.setForeground(Color.BLACK);
        redoButton.setText("Redo");
        redoButton.setFont(new Font("", 1, percent(widthContainer, 2)));
        redoButton.setVisible(false);
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redoBtnActionPerformed();
            }
        });
        container.add(redoButton);
        redoButton.setBounds(percent(widthContainer, 20), percent(heightContainer, 84), percent(widthContainer, 8), percent(heightContainer, 8));

        jLabel1.setFont(new Font("", 1, percent(widthContainer, 3)));
        jLabel1.setForeground(Color.BLACK);
        jLabel1.setText("Color:");
        container.add(jLabel1);
        jLabel1.setBounds(percent(widthContainer, 75), percent(heightContainer, 84), percent(widthContainer, 20), percent(heightContainer, 8));

        paintButton.setBackground(getColor(colorWith));
        paintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintBtnActionPerformed();
            }
        });
        container.add(paintButton);
        paintButton.setBounds(percent(widthContainer, 85), percent(heightContainer, 84), percent(heightContainer, 8), percent(heightContainer, 8));

        buttonHolder.setLayout(null);

        for (int i = 0; i < iCount; i++) {
            for (int j = 0; j < jCount; j++) {
                button[i][j].addMouseListener(mouseAdapter);
                buttonHolder.add(button[i][j]);
                button[i][j].setBounds(i * buttonWidth, j * buttonWidth,
                        buttonWidth, buttonWidth);
                button[i][j].i = i;
                button[i][j].j = j;
                button[i][j].setBackground(Color.GRAY);
                buttonColor[i][j] = GRAY;
            }
        }

        container.add(buttonHolder);
        buttonHolder.setBounds((int) ((widthContainer - widthHolder) /2),
                (int) (((percent(heightContainer, 78) - heightHolder) / 2) + percent(heightContainer, 4)),
                widthHolder, heightHolder);

        error.setFont(new Font("", 1, percent(widthContainer, 2.4f)));
        error.setForeground(Color.RED);
        error.setText("");
        container.add(error);
        error.setBounds(percent(widthContainer, 25), 0, percent(widthContainer, 70), percent(heightContainer, 4));

        getContentPane().add(container);
        container.setBounds(0, 0, widthContainer, heightContainer);

        pack();
    }

    private void undoBtnActionPerformed() {
        int index, i, j, color;
        index = undoList.size() - 1;
        i = undoList.get(index).i;
        j = undoList.get(index).j;
        color = undoList.get(index).color;

        redoList.add(new Situation(i, j, buttonColor[i][j]));
        redoButton.setVisible(true);
        undoList.remove(index);
        if (undoList.isEmpty())
            undoButton.setVisible(false);

        button[i][j].setBackground(getColor(color));
        colored[color]++;
        colored[buttonColor[i][j]]--;
        buttonColor[i][j] = color;

    }

    private void redoBtnActionPerformed() {
        int index, i, j, color;
        index = redoList.size() - 1;
        i = redoList.get(index).i;
        j = redoList.get(index).j;
        color = redoList.get(index).color;

        undoList.add(new Situation(i, j, buttonColor[i][j]));
        undoButton.setVisible(true);
        redoList.remove(index);
        if (redoList.isEmpty())
            redoButton.setVisible(false);

        button[i][j].setBackground(getColor(color));
        colored[color]++;
        colored[buttonColor[i][j]]--;
        buttonColor[i][j] = color;
    }

    private void paintBtnActionPerformed() {
        if (colorWith == BLACK) {
            colorWith = WHITE;
        }
        else {
            colorWith = BLACK;
        }
        paintButton.setBackground(getColor(colorWith));
    }

    private void rightClicked(Button h) {
        int i, j;
        clickedBtn = h;
        i = clickedBtn.i;
        j = clickedBtn.j;
        if (buttonColor[i][j] == GRAY)
            return;

        // Check Connection Breaking
        if (!connectionCondition(i, j, 2, buttonColor[i][j])) {
            error.setText("Not Connected!");
            setError();
            return;
        }
        // CAN EMPTY SIDE CHECK
        if (isSide(i, j) && !EmptySide(i, j, buttonColor[i][j])) {
            error.setText("Not Connected!");
            setError();
            return;
        }

        clickedBtn.setBackground(Color.GRAY);
        colored[GRAY]++;
        colored[buttonColor[i][j]]--;

        undoList.add(new Situation(i, j, buttonColor[i][j]));
        redoList.clear();
        undoButton.setVisible(true);
        redoButton.setVisible(false);

        buttonColor[i][j] = GRAY;

    }

    private void leftClicked(Button h) {
        int i, j;
        clickedBtn = h;
        i = clickedBtn.i;
        j = clickedBtn.j;
        if (buttonColor[i][j] == GRAY) { // GRAY BUTTON CLICKED

            // CHECK CONNECTION
            if (!connectionCondition(i, j, 1, colorWith)) {
                error.setText(errorString);
                setError();
                return;
            }
            // CHECK SIDE
            if (isSide(i, j)) {
                buttonColor[i][j] = colorWith;
                colorFirst = colorWith;
                iFirst = i;
                jFirst = j;
                count = 0;
                if (!sideCheck(0, 0, i, j, i, j)) {
                    error.setText(errorString);
                    setError();
                    buttonColor[i][j] = GRAY;
                    return;
                }
            }


            clickedBtn.setBackground(getColor(colorWith));
            colored[colorWith]++;
            colored[GRAY]--;
            buttonColor[i][j] = colorWith;
            if (colored[GRAY] == 0) {
                error.setText("You Win");
                error.setForeground(Color.GREEN);
            }

            undoList.add(new Situation(i, j, GRAY));
            redoList.clear();
            undoButton.setVisible(true);
            redoButton.setVisible(false);

            return;
        } // END OF GRAY BUTTON CLICKED

        // CHECK CONNECTION BRAKING
        if (!connectionCondition(i, j, 2, buttonColor[i][j])) {
            error.setText("Not Connected!");
            setError();
            return;
        }
        // CAN EMPTY SIDE CHECK
        if (isSide(i, j) && !EmptySide(i, j, buttonColor[i][j])) {
            error.setText("Not Connected!");
            setError();
            return;
        }

        if (buttonColor[i][j] == BLACK) { // BLACK BUTTON CLICKED

            // CHECK CONNECTION
            if (!connectionCondition(i, j, 1, WHITE)) {
                error.setText(errorString);
                setError();
                return;
            }
            // CHECK SIDE
            if (isSide(i, j)) {
                buttonColor[i][j] = WHITE;
                colorFirst = WHITE;
                iFirst = i;
                jFirst = j;
                count = 0;
                if (!sideCheck(0, 0, i, j, i, j)) {
                    error.setText(errorString);
                    setError();
                    buttonColor[i][j] = BLACK;
                    return;
                }
            }

            clickedBtn.setBackground(Color.WHITE);
            colored[WHITE]++;
            colored[BLACK]--;
            buttonColor[i][j] = WHITE;

            undoList.add(new Situation(i, j, BLACK));
            redoList.clear();
            undoButton.setVisible(true);
            redoButton.setVisible(false);

        } // END OF BLACK BUTTON CLICKED

        else { // WHITE BUTTON CLICKED

            // CHECK CONNECTION
            if (!connectionCondition(i, j, 1, BLACK)) {
                error.setText(errorString);
                setError();
                return;
            }
            // CHECK SIDE
            if (isSide(i, j)) {
                buttonColor[i][j] = BLACK;
                colorFirst = BLACK;
                iFirst = i;
                jFirst = j;
                count = 0;
                if (!sideCheck(0, 0, i, j, i, j)) {
                    error.setText(errorString);
                    setError();
                    buttonColor[i][j] = WHITE;
                    return;
                }
            }

            clickedBtn.setBackground(Color.BLACK);
            colored[BLACK]++;
            colored[WHITE]--;
            buttonColor[i][j] = BLACK;

            undoList.add(new Situation(i, j, WHITE));
            redoList.clear();
            undoButton.setVisible(true);
            redoButton.setVisible(false);

        } // END OF WHITE BUTTON CLICKED
    }

    private boolean connectionCondition(int i, int j, int type, int color) {
        int sameColorCount = 0;
        int around = 0;
        boolean inCenter = false;
        if (i > 0 && j > 0 && i < (iCount - 1) && j < (jCount - 1)) {
            inCenter = true;
        }
        if (type == 2 && !inCenter)
            return true;
        if (colored[color] == 0) {
            if ((i + 1) < iCount) {
                around++;
                if (buttonColor[i + 1][j] != GRAY)
                    sameColorCount++;
            }
            if ((j + 1) < jCount) {
                around++;
                if (buttonColor[i][j + 1] != GRAY)
                    sameColorCount++;
            }
            if ((i - 1) >= 0) {
                around++;
                if (buttonColor[i - 1][j] != GRAY)
                    sameColorCount++;
            }
            if ((j - 1) >= 0) {
                around++;
                if (buttonColor[i][j - 1] != GRAY)
                    sameColorCount++;
            }
            if (around == sameColorCount) {
                errorString = "not connected!";
                return false;
            }
            return true;
        }

        errorString = "Wrong(2x2)!";
        sameColorCount = 0;
        if ((i + 1) < iCount && color == buttonColor[i + 1][j]) {
            if (type == 1 && inCenter) {
                if (color == buttonColor[i - 1][j - 1] || color == buttonColor[i - 1][j + 1]) {
                    return false;
                }
            }
            sameColorCount++;
        }
        if ((j + 1) < jCount && color == buttonColor[i][j + 1]) {
            if (type == 1 && inCenter) {
                if (color == buttonColor[i - 1][j - 1] || color == buttonColor[i + 1][j - 1]) {
                    return false;
                }
            }
            sameColorCount++;
        }
        if ((i - 1) >= 0 && color == buttonColor[i - 1][j]) {
            if (type == 1 && inCenter) {
                if (color == buttonColor[i + 1][j - 1] || color == buttonColor[i + 1][j + 1]) {
                    return false;
                }
            }
            sameColorCount++;
        }
        if ((j - 1) >= 0 && color == buttonColor[i][j - 1]) {
            if (type == 1 && inCenter) {
                if (color == buttonColor[i - 1][j + 1] || color == buttonColor[i + 1][j + 1]) {
                    return false;
                }
            }
            sameColorCount++;
        }
        if (type == 1 && sameColorCount == 0) errorString = "not connected!";

        if (type == 1 && !inCenter && sameColorCount <= 2 && sameColorCount > 0)
            return true;
        return (type == 1 && sameColorCount == 1) || (type == 2 && sameColorCount <= 1);
    }

    private boolean sideCheck(int count, int centerCount, int iPre, int jPre, int iNow, int jNow) {
        int iAround, jAround, centerAround;

        errorString = "Wrong(2x2)!";
        // GOING RIGHT
        iAround = iNow + 1;
        jAround = jNow;
        centerAround = centerCount;
        if (iAround != iPre && iAround < iCount && colorFirst == buttonColor[iAround][jAround]) {
            if (iAround < (iCount - 1) && jAround > 0 && jAround < (jCount - 1)) centerAround++;
            if (centerAround > 0 && iAround == (iCount - 1) && jAround > 0 && jAround < (jCount - 1)) {
                return false;
            }
            if (centerAround == 0 && (iAround == iFirst && jAround == jFirst)) {
                sideLoop = true;
                return true;
            }
            if (centerAround > 0 && count > 2 && ((iAround == iFirst && jAround == jFirst) ||
                    ((iAround + 1) == iFirst && (jAround + 1) == jFirst) ||
                    ((iAround + 1) == iFirst && (jAround - 1) == jFirst))) {
                return false;
            }
            if (!sideCheck(count + 1, centerAround, iNow, jNow, iAround, jAround)) return false;
        }

        // GOING UP
        iAround = iNow;
        jAround = jNow + 1;
        centerAround = centerCount;
        if (jAround != jPre && jAround < jCount && colorFirst == buttonColor[iAround][jAround]) {
            if (jAround < (iCount - 1) && iAround > 0 && iAround < (iCount - 1)) centerAround++;
            if (centerAround > 0 && jAround == (jCount - 1) && iAround > 0 && iAround < (iCount - 1)) {
                return false;
            }
            if (centerAround == 0 && (iAround == iFirst && jAround == jFirst)) {
                sideLoop = true;
                return true;
            }
            if (centerAround > 0 && count > 2 && ((iAround == iFirst && jAround == jFirst) ||
                    ((iAround + 1) == iFirst && (jAround + 1) == jFirst) ||
                    ((iAround - 1) == iFirst && (jAround + 1) == jFirst))) {
                return false;
            }
            if (!sideCheck(count + 1, centerAround, iNow, jNow, iAround, jAround)) return false;
        }

        // GOING LEFT
        iAround = iNow - 1;
        jAround = jNow;
        centerAround = centerCount;
        if (iAround != iPre && iAround >= 0 && colorFirst == buttonColor[iAround][jAround]) {
            if (iAround > 0 && jAround > 0 && jAround < (jCount - 1)) centerAround++;
            if (centerAround > 0 && iAround == 0 && jAround > 0 && jAround < (jCount - 1)) {
                return false;
            }
            if (centerAround == 0 && (iAround == iFirst && jAround == jFirst)) {
                sideLoop = true;
                return true;
            }
            if (centerAround > 0 && count > 2 && ((iAround == iFirst && jAround == jFirst) ||
                    ((iAround - 1) == iFirst && (jAround - 1) == jFirst) ||
                    ((iAround - 1) == iFirst && (jAround + 1) == jFirst))) {
                return false;
            }
            if (!sideCheck(count + 1, centerAround, iNow, jNow, iAround, jAround)) return false;
        }

        // GOING DOWN
        iAround = iNow;
        jAround = jNow - 1;
        centerAround = centerCount;
        if (jAround != jPre && jAround >= 0 && colorFirst == buttonColor[iAround][jAround]) {
            if (jAround > 0 && iAround > 0 && iAround < (iCount - 1)) centerAround++;
            if (centerAround > 0 && jAround == 0 && iAround > 0 && iAround < (iCount - 1)) {
                return false;
            }
            if (centerAround == 0 && (iAround == iFirst && jAround == jFirst)) {
                sideLoop = true;
                return true;
            }
            if (centerAround > 0 && count > 2 && ((iAround == iFirst && jAround == jFirst)  ||
                    ((iAround + 1) == iFirst && (jAround - 1) == jFirst) ||
                    ((iAround - 1) == iFirst && (jAround - 1) == jFirst)) ) {
                return false;
            }
            if (!sideCheck(count + 1, centerAround, iNow, jNow, iAround, jAround)) return false;
        }
        return true;
    }

    private boolean EmptySide(int i, int j, int color) {
        int sameColorCount = 0;

        if ((i + 1) < iCount && buttonColor[i + 1][j] == color) sameColorCount++;
        if ((j + 1) < jCount && buttonColor[i][j + 1] == color) sameColorCount++;
        if ((i - 1) >= 0 && buttonColor[i - 1][j] == color) sameColorCount++;
        if ((j - 1) >= 0 && buttonColor[i][j - 1] == color) sameColorCount++;

        if (sameColorCount <= 1) return true;
        if (sameColorCount == 3) return false;

        // CHECK SIDE
        colorFirst = color;
        iFirst = i;
        jFirst = j;
        sideLoop = false;
        count = 0;
        if (sideCheck(0, 0, i, j, i, j)) {
            if (sideLoop) {
                return true;
            }
        }
        return false;
    }

    private void chooseRandom(int amount) {

        if (amount == 0) return;
        int toColor;
        int temp;
        int color;
        int index;// get index from : index =  (j + (i * jCount))  ;
        int trys = 0;
        int loopBreaker = 2000;
        int ir, jr;
        ArrayList<Situation> btnList = new ArrayList<>();
        ArrayList<Situation> availableBtn = new ArrayList<>();
        Random rand = new Random();
        do {
            trys++;
            // COLORING FIRST COLOR
            toColor = (int) amount / 2;
            temp = toColor;
            color = rand.nextInt(2);
            if (color == 1) color = WHITE;
            btnList.clear();
            availableBtn.clear();
            for (int i = 0; i < iCount; i++) {
                for (int j = 0; j < jCount; j++) {
                    btnList.add(new Situation(i, j, GRAY));
                    buttonColor[i][j] = GRAY;
                }
            }
            colored[BLACK] = 0;
            colored[GRAY] = iCount * jCount;
            colored[WHITE] = 0;

            index = rand.nextInt(btnList.size());
            ir = btnList.get(index).i;
            jr = btnList.get(index).j;
            buttonColor[ir][jr] = color;
            colored[color]++;
            colored[GRAY]--;
            toColor--;
            if ((ir + 1) < iCount) availableBtn.add(new Situation(ir + 1, jr, GRAY));
            if ((jr + 1) < jCount) availableBtn.add(new Situation(ir, jr + 1, GRAY));
            if ((ir - 1) >= 0) availableBtn.add(new Situation(ir - 1, jr, GRAY));
            if ((jr - 1) >= 0) availableBtn.add(new Situation(ir, jr - 1, GRAY));
            btnList.remove(index);

            while (!availableBtn.isEmpty()) {
                index = rand.nextInt(availableBtn.size());
                ir = availableBtn.get(index).i;
                jr = availableBtn.get(index).j;
                availableBtn.remove(index);

                // CHECK CONNECTION
                if (!connectionCondition(ir, jr, 1, color)) {
                    continue;
                }
                // CHECK SIDE
                if (isSide(ir, jr)) {
                    buttonColor[ir][jr] = color;
                    colorFirst = color;
                    iFirst = ir;
                    jFirst = jr;
                    count = 0;
                    if (!sideCheck(0, 0, ir, jr, ir, jr)) {
                        buttonColor[ir][jr] = GRAY;
                        continue;
                    }
                }
                colored[color]++;
                colored[GRAY]--;
                toColor--;
                buttonColor[ir][jr] = color;

                if (toColor == 0) break;

                if ((ir + 1) < iCount && buttonColor[ir + 1][jr] == GRAY)
                    availableBtn.add(new Situation(ir + 1, jr, GRAY));
                if ((jr + 1) < jCount && buttonColor[ir][jr + 1] == GRAY)
                    availableBtn.add(new Situation(ir, jr + 1, GRAY));
                if ((ir - 1) >= 0 && buttonColor[ir - 1][jr] == GRAY)
                    availableBtn.add(new Situation(ir - 1, jr, GRAY));
                if ((jr - 1) >= 0 && buttonColor[ir][jr - 1] == GRAY)
                    availableBtn.add(new Situation(ir, jr - 1, GRAY));


            } // END OF COLORING FIRST COLOR

            System.out.println("looping for " + trys + " times!");
            if (toColor != 0) continue;

            // COLORING SECOND COLOR
            toColor = amount - temp;
            if (color == BLACK) color = WHITE;
            else color = BLACK;
            btnList.clear();
            availableBtn.clear();
            for (int i = 0; i < iCount; i++) {
                for (int j = 0; j < jCount; j++) {
                    if (buttonColor[i][j] == GRAY)
                        btnList.add(new Situation(i, j, GRAY));
                }
            }

            index = rand.nextInt(btnList.size());
            ir = btnList.get(index).i;
            jr = btnList.get(index).j;
            buttonColor[ir][jr] = color;
            colored[color]++;
            colored[GRAY]--;
            toColor--;
            if ((ir + 1) < iCount && buttonColor[ir + 1][jr] == GRAY)
                availableBtn.add(new Situation(ir + 1, jr, GRAY));
            if ((jr + 1) < jCount && buttonColor[ir][jr + 1] == GRAY)
                availableBtn.add(new Situation(ir, jr + 1, GRAY));
            if ((ir - 1) >= 0 && buttonColor[ir - 1][jr] == GRAY)
                availableBtn.add(new Situation(ir - 1, jr, GRAY));
            if ((jr - 1) >= 0 && buttonColor[ir][jr - 1] == GRAY)
                availableBtn.add(new Situation(ir, jr - 1, GRAY));
            btnList.remove(index);

            while (!availableBtn.isEmpty()) {
                index = rand.nextInt(availableBtn.size());
                ir = availableBtn.get(index).i;
                jr = availableBtn.get(index).j;
                availableBtn.remove(index);

                // CHECK CONNECTION
                if (!connectionCondition(ir, jr, 1, color)) {
                    continue;
                }
                // CHECK SIDE
                if (isSide(ir, jr)) {
                    buttonColor[ir][jr] = color;
                    colorFirst = color;
                    iFirst = ir;
                    jFirst = jr;
                    count = 0;
                    if (!sideCheck(0, 0, ir, jr, ir, jr)) {
                        buttonColor[ir][jr] = GRAY;
                        continue;
                    }
                }
                colored[color]++;
                colored[GRAY]--;
                toColor--;
                buttonColor[ir][jr] = color;

                if (toColor == 0) break;

                if ((ir + 1) < iCount && buttonColor[ir + 1][jr] == GRAY)
                    availableBtn.add(new Situation(ir + 1, jr, GRAY));
                if ((jr + 1) < jCount && buttonColor[ir][jr + 1] == GRAY)
                    availableBtn.add(new Situation(ir, jr + 1, GRAY));
                if ((ir - 1) >= 0 && buttonColor[ir - 1][jr] == GRAY)
                    availableBtn.add(new Situation(ir - 1, jr, GRAY));
                if ((jr - 1) >= 0 && buttonColor[ir][jr - 1] == GRAY)
                    availableBtn.add(new Situation(ir, jr - 1, GRAY));

            } // END OF COLORING SECOND COLOR

        }while(trys < loopBreaker && toColor != 0);

        if (trys == loopBreaker) {
            for (int i = 0; i < iCount; i++) {
                for (int j = 0; j < jCount; j++) {
                    buttonColor[i][j] = GRAY;
                }
            }
            colored[BLACK] = 0;
            colored[GRAY] = iCount * jCount;
            colored[WHITE] = 0;
            System.out.println("Just couldn't do it!");
            return;
        }

        for (int i = 0; i < iCount; i++) {
            for (int j = 0; j < jCount; j++) {
                button[i][j].setBackground(getColor(buttonColor[i][j]));
                button[i][j].repaint();
            }
        }
    }


    private Color getColor(int color) {
        if (color == BLACK)
            return Color.BLACK;
        if (color == WHITE)
            return Color.WHITE;
        return Color.GRAY;
    }

    private int percent(int number, float percentage) {
        return (int) ((number * percentage) / 100);
    }

    private boolean isSide(int i, int j) {
        return i == 0 || j == 0 || i == (iCount - 1) || j == (jCount - 1);
    }

    private int calculateWidth(int i, int j, int width, int height) {
        int result;
        result = (int) height / j;
        if (result * i >= width) {
            result = width / i;
        }
        widthHolder = i * result;
        heightHolder = j * result;
        return result;
    }

    private void setError() {
        errorAlpha = 255;
        errorTimer.setDelay(2500);
        errorTimer.restart();
    }

}


