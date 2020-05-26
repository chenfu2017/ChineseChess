package com.chenfu.view;
import com.chenfu.DefultSet;
import com.chenfu.inform.InformationBoard;
import com.chenfu.adapter.*;
import com.chenfu.button.DiyButton;
import com.chenfu.chess.ChessBoard;
import com.chenfu.chess.ChessPiece;
import com.chenfu.control.GameController;
import com.chenfu.listener.AskdrawLister;
import com.chenfu.listener.NewGameLister;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.pojo.Player;
import com.chenfu.timer.StepTimer;
import com.chenfu.timer.TotalTimer;
import com.chenfu.utils.AudioPlayer;
import com.chenfu.utils.ResourceUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GameView extends JFrame{
    public Map<String, JLabel> pieceMap = new HashMap<String, JLabel>();
    private final ChessBoard chessBoard;
    public String selectedPieceKey;
    private final JLayeredPane jLayeredPane;
    private final GameController gameController;
    private final JLabel jLabel;
    private final InformationBoard informationBoard;
    private final AudioPlayer audioPlayer;
    private final StepTimer stepTimer;
    private final TotalTimer totalTimer;
    private JLabel kuangLabel;
    private Player player;
    /*0初始状态
    1人机开始状态
    2网络未登录
    3网络已登录
    4网络匹配成功
    5人机未开始状态*/
    public int status;

    public GameView() {
        gameController = new GameController();
        chessBoard = gameController.playChess();
        jLayeredPane = this.getLayeredPane();
        status = GameStatusEnum.AI_START.status;
        //设置背景音乐
        audioPlayer = new AudioPlayer("bgm.wav", true);
        this.setTitle("中国象棋在线博弈系统");
        this.setIconImage(ResourceUtils.getImage("logo.jpg"));
        //设置窗口大小
        this.setBounds(0, 0, DefultSet.frameWidth, DefultSet.frameHeight);
        //设置窗口不可改变大小
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        //不使用布局
        contentPanel.setLayout(null);
        //设置ContentPane为透明
        contentPanel.setOpaque(false);
        this.setContentPane(contentPanel);

        //添加背景图片
        JLabel backGround = new JLabel("");
        ImageIcon bgimage = ResourceUtils.getImageIcon("bg.jpg");
        bgimage.setImage(bgimage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
        backGround.setIcon(bgimage);
        backGround.setBounds(0, 0, DefultSet.frameWidth, DefultSet.frameHeight);
        jLayeredPane.add(backGround, new Integer(Integer.MIN_VALUE));

        //初始化1个JPanel
        JPanel jPanel = new JPanel();
        jPanel.setBounds(0, 0, DefultSet.frameWidth, DefultSet.frameHeight);
        jPanel.setOpaque(false);
        jPanel.setVisible(true);
        jPanel.setLayout(null);
        contentPanel.add(jPanel);

        //对Pane1添加信息栏
        informationBoard = new InformationBoard();
        informationBoard.setBounds(DefultSet.infBoardX,DefultSet.infBoardY,DefultSet.infBoardWidth,DefultSet.infBoardHeight);
        jPanel.add(informationBoard);

        //添加按钮
        DiyButton diyButton1 = new DiyButton("新对局",DefultSet.buttonX,DefultSet.buttonY);
        diyButton1.addActionListener(new NewGameLister(this, informationBoard));
        jPanel.add(diyButton1);

        DiyButton diyButton2 = new DiyButton("悔棋",DefultSet.buttonX+DefultSet.buttonP,DefultSet.buttonY);
        jPanel.add(diyButton2);

        DiyButton diyButton3 = new DiyButton("求和",DefultSet.buttonX+DefultSet.buttonP*2,DefultSet.buttonY);
        diyButton3.addActionListener(new AskdrawLister());
        jPanel.add(diyButton3);

        DiyButton diyButton4 = new DiyButton("认输",DefultSet.buttonX+DefultSet.buttonP*3,DefultSet.buttonY);
        jPanel.add(diyButton4);

        //添加菜单
        Font font = new Font("宋体", Font.BOLD, 40);
        Color color = Color.blue;
        JLabel menu1 = new JLabel("用户登录");
        menu1.setFont(font);
        menu1.setForeground(color);
        menu1.setBounds(DefultSet.menuX, DefultSet.menuY, DefultSet.menuWidth, DefultSet.menuHeight);
        menu1.addMouseListener(new LoginAdapter(this,menu1));
        contentPanel.add(menu1);

        JLabel menu2 = new JLabel("人机对战");
        menu2.setFont(font);
        menu2.setForeground(color);
        menu2.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP, DefultSet.menuWidth, DefultSet.menuHeight);
        menu2.addMouseListener(new AIModeAdapter(this, informationBoard,menu2));
        contentPanel.add(menu2);

        JLabel menu3 = new JLabel("网络对战");
        menu3.setFont(font);
        menu3.setForeground(color);
        menu3.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP*2, DefultSet.menuWidth, DefultSet.menuHeight);
        menu3.addMouseListener(new NetworkModeAdapter(this,informationBoard,menu3));
        contentPanel.add(menu3);

        JLabel menu4 = new JLabel("关闭音乐");
        menu4.setFont(font);
        menu4.setForeground(color);
        menu4.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP*3, DefultSet.menuWidth, DefultSet.menuHeight);
        menu4.addMouseListener(new MusicControlAdapter(audioPlayer,menu4));
        contentPanel.add(menu4);

        JLabel menu5 = new JLabel("退出游戏");
        menu5.setFont(font);
        menu5.setForeground(color);
        menu5.setBounds(DefultSet.menuX, DefultSet.menuY+DefultSet.menuP*4, DefultSet.menuWidth, DefultSet.menuHeight);
        menu5.addMouseListener(new ExitAdapter(this,menu5));
        contentPanel.add(menu5);

        //添加棋盘
        ImageIcon boardimage = ResourceUtils.getImageIcon("M_ChessBoard.png");
        boardimage.setImage(boardimage.getImage().getScaledInstance(DefultSet.canvasPosWidth , DefultSet.canvasPosHeight, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
        JLabel jLabel = new JLabel(boardimage);
        jLabel.setLocation(DefultSet.canvasPosX, DefultSet.canvasPosY);
        jLabel.setSize(DefultSet.canvasPosWidth, DefultSet.canvasPosHeight);
        jLabel.addMouseListener(new BoardClickListener(this, this.chessBoard,gameController));
        jLayeredPane.add(jLabel, 2);

        /* Initialize player image.*/
        this.jLabel = new JLabel(ResourceUtils.getImageIcon("r.png"));
        this.jLabel.setLocation(DefultSet.playerIconX, DefultSet.playerIconY);
        this.jLabel.setSize(DefultSet.pieceSize,DefultSet.pieceSize);
        jLayeredPane.add(this.jLabel, 2);

        /* Initialize gamepanel.chess pieces and listeners on each piece.*/
        Map<String, ChessPiece> pieces = this.chessBoard.pieces;
        for (Map.Entry<String, ChessPiece> stringPieceEntry : pieces.entrySet()) {
            String key = stringPieceEntry.getKey();
            int[] pos = stringPieceEntry.getValue().position;
            int[] sPos = modelToViewConverter(pos);
            ImageIcon imageIcon = ResourceUtils.getImageIcon(key.substring(0, 2) + ".png");
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(DefultSet.pieceSize, DefultSet.pieceSize, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
            JLabel pieceLabel = new JLabel(imageIcon);
            pieceLabel.setLocation(sPos[0], sPos[1]);
            pieceLabel.setSize(DefultSet.pieceSize, DefultSet.pieceSize);
            pieceLabel.addMouseListener(new PieceOnClickListener(this,gameController,chessBoard,jLayeredPane,key));
            pieceMap.put(stringPieceEntry.getKey(), pieceLabel);
            jLayeredPane.add(pieceLabel, 0);
        }

        //添加选中框
        ImageIcon kuangIcon = ResourceUtils.getImageIcon("kuang.png");
        kuangIcon.setImage(kuangIcon.getImage().getScaledInstance(DefultSet.pieceSize, DefultSet.pieceSize, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
        kuangLabel = new JLabel(kuangIcon);
        kuangLabel.setSize(DefultSet.pieceSize, DefultSet.pieceSize);
        jLayeredPane.add(kuangLabel,0);

        //添加时间标签
        JLabel totaltimerLabel = new JLabel();
        totaltimerLabel.setBounds(DefultSet.timerLabelX,DefultSet.timerLabelY,DefultSet.timerLabelWidth,DefultSet.timerLabelHeight);
        totaltimerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        totaltimerLabel.setForeground(Color.RED);
        jPanel.add(totaltimerLabel);
        totalTimer = new TotalTimer(totaltimerLabel);

        JLabel timerLabel = new JLabel();
        timerLabel.setBounds(DefultSet.timerLabelX,DefultSet.timerLabelY+DefultSet.timerLabelP,DefultSet.timerLabelWidth,DefultSet.timerLabelHeight);
        timerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        timerLabel.setForeground(Color.RED);
        jPanel.add(timerLabel);
        stepTimer = new StepTimer(timerLabel);
        this.setVisible(true);
    }

    public void run() throws InterruptedException {
        while (gameController.hasWin(chessBoard) == 'x') {
            this.showPlayer('r');
            while (chessBoard.player == 'r')
                Thread.sleep(1000);

            if (gameController.hasWin(chessBoard) != 'x')
                this.showWinner('r');
            this.showPlayer('b');
            int[]pos= gameController.responseMoveChess(chessBoard, this);
            getKuangLabel().setLocation(DefultSet.SX_OFFSET + pos[1] * DefultSet.SX_COE,DefultSet.SY_OFFSET + pos[0] * DefultSet.SY_COE);
        }
        this.showWinner('b');
    }

    public void movePieceFromModel(String pieceKey, int[] to) {
        JLabel pieceObject = pieceMap.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setLocation(sPos[0], sPos[1]);
        /* Clear 'from' and 'to' info on the board */
        selectedPieceKey = null;
    }

    public void movePieceFromAI(String pieceKey, int[] to) {
        ChessPiece inNewPos = chessBoard.getPiece(to);
        if (inNewPos != null) {
            jLayeredPane.remove(pieceMap.get(inNewPos.key));
            pieceMap.remove(inNewPos.key);
        }

        JLabel pieceObject = pieceMap.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setLocation(sPos[0], sPos[1]);
        selectedPieceKey = null;
    }

    private int[] modelToViewConverter(int pos[]) {
        int sx = pos[1] * DefultSet.SX_COE + DefultSet.SX_OFFSET, sy = pos[0] * DefultSet.SY_COE + DefultSet.SY_OFFSET;
        return new int[]{sx, sy};
    }

    private int[] viewToModelConverter(int sPos[]) {
        int ADDITIONAL_SY_OFFSET = 25;
        int y = (sPos[0] - DefultSet.SX_OFFSET) / DefultSet.SX_COE, x = (sPos[1] - DefultSet.SY_OFFSET - ADDITIONAL_SY_OFFSET) / DefultSet.SY_COE;
        return new int[]{x, y};
    }

    public void showPlayer(char player) {
        ImageIcon imageIcon = ResourceUtils.getImageIcon(player + ".png");
        jLabel.setIcon(imageIcon);
    }

    public void showWinner(char player) {
        JOptionPane.showMessageDialog(null, (player == 'r') ? "Red player has won!" : "Black player has won!", "Intelligent Chinese Chess", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public StepTimer getStepTimer() {
        return stepTimer;
    }

    public InformationBoard getInformationBoard() {
        return informationBoard;
    }


    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public TotalTimer getTotalTimer() {
        return totalTimer;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public JLabel getKuangLabel() {
        return kuangLabel;
    }
}
