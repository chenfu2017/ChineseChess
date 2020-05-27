package com.chenfu.view;
import com.chenfu.DefaultSet;
import com.chenfu.inform.InformationBoard;
import com.chenfu.adapter.*;
import com.chenfu.button.DiyButton;
import com.chenfu.chess.ChessBoard;
import com.chenfu.chess.ChessPiece;
import com.chenfu.control.GameController;
import com.chenfu.listener.AskdrawLister;
import com.chenfu.listener.NewGameLister;
import com.chenfu.netty.Client;
import com.chenfu.pojo.GameStatusEnum;
import com.chenfu.pojo.Player;
import com.chenfu.timer.StepTimer;
import com.chenfu.timer.TotalTimer;
import com.chenfu.utils.AudioPlayer;
import com.chenfu.utils.ResourceUtils;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameView extends JFrame{
    public Map<String, JLabel> stringJLabelMap = new HashMap<String, JLabel>();
    public Map<String, ChessPiece> pieces;
    public String selectedPieceKey;
    private ChessBoard chessBoard;
    private final JLayeredPane jLayeredPane;
    private final GameController gameController;
    private final JLabel jLabel;
    private final InformationBoard informationBoard;
    private final AudioPlayer audioPlayer;
    private final StepTimer stepTimer;
    private final TotalTimer totalTimer;
    private JLabel kuangLabel;
    private static Player player;
    /*0初始状态
    1人机开始状态
    2网络未登录
    3网络已登录
    4网络匹配成功
    5人机未开始状态*/
    public int status;
    private Client client;

    public GameView() {
        gameController = new GameController();
        chessBoard = new ChessBoard();
        jLayeredPane = this.getLayeredPane();
        client = Client.getInstance();
        status = GameStatusEnum.INIT.status;
        //设置背景音乐
        audioPlayer = new AudioPlayer("bgm.wav", true);
        this.setTitle("中国象棋在线博弈系统");
        this.setIconImage(ResourceUtils.getImage("logo.jpg"));
        //设置窗口大小
        this.setBounds(0, 0, DefaultSet.frameWidth, DefaultSet.frameHeight);
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
        backGround.setBounds(0, 0, DefaultSet.frameWidth, DefaultSet.frameHeight);
        jLayeredPane.add(backGround, new Integer(Integer.MIN_VALUE));

        //初始化1个JPanel
        JPanel jPanel = new JPanel();
        jPanel.setBounds(0, 0, DefaultSet.frameWidth, DefaultSet.frameHeight);
        jPanel.setOpaque(false);
        jPanel.setVisible(true);
        jPanel.setLayout(null);
        contentPanel.add(jPanel);

        //对Pane1添加信息栏
        informationBoard = InformationBoard.getInstance();
        informationBoard.setBounds(DefaultSet.infBoardX, DefaultSet.infBoardY, DefaultSet.infBoardWidth, DefaultSet.infBoardHeight);
        jPanel.add(informationBoard);

        //添加按钮
        DiyButton diyButton1 = new DiyButton("新对局", DefaultSet.buttonX, DefaultSet.buttonY);
        diyButton1.addActionListener(new NewGameLister(this,chessBoard,informationBoard));
        jPanel.add(diyButton1);

        DiyButton diyButton2 = new DiyButton("悔棋", DefaultSet.buttonX+ DefaultSet.buttonP, DefaultSet.buttonY);
        jPanel.add(diyButton2);

        DiyButton diyButton3 = new DiyButton("求和", DefaultSet.buttonX+ DefaultSet.buttonP*2, DefaultSet.buttonY);
        diyButton3.addActionListener(new AskdrawLister(this));
        jPanel.add(diyButton3);

        DiyButton diyButton4 = new DiyButton("认输", DefaultSet.buttonX+ DefaultSet.buttonP*3, DefaultSet.buttonY);
        jPanel.add(diyButton4);

        //添加菜单
        Font font = new Font("宋体", Font.BOLD, 40);
        Color color = Color.blue;
        JLabel menu1 = new JLabel("用户登录");
        menu1.setFont(font);
        menu1.setForeground(color);
        menu1.setBounds(DefaultSet.menuX, DefaultSet.menuY, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu1.addMouseListener(new LoginAdapter(this,menu1));
        contentPanel.add(menu1);

        JLabel menu2 = new JLabel("人机对战");
        menu2.setFont(font);
        menu2.setForeground(color);
        menu2.setBounds(DefaultSet.menuX, DefaultSet.menuY+ DefaultSet.menuP, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu2.addMouseListener(new AIModeAdapter(this, informationBoard,menu2));
        contentPanel.add(menu2);

        JLabel menu3 = new JLabel("网络对战");
        menu3.setFont(font);
        menu3.setForeground(color);
        menu3.setBounds(DefaultSet.menuX, DefaultSet.menuY+ DefaultSet.menuP*2, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu3.addMouseListener(new NetworkModeAdapter(this,informationBoard,menu3));
        contentPanel.add(menu3);

        JLabel menu4 = new JLabel("关闭音乐");
        menu4.setFont(font);
        menu4.setForeground(color);
        menu4.setBounds(DefaultSet.menuX, DefaultSet.menuY+ DefaultSet.menuP*3, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu4.addMouseListener(new MusicControlAdapter(audioPlayer,menu4));
        contentPanel.add(menu4);

        JLabel menu5 = new JLabel("退出游戏");
        menu5.setFont(font);
        menu5.setForeground(color);
        menu5.setBounds(DefaultSet.menuX, DefaultSet.menuY+ DefaultSet.menuP*4, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu5.addMouseListener(new ExitAdapter(this,menu5));
        contentPanel.add(menu5);
        //添加棋盘
        ImageIcon boardimage = ResourceUtils.getImageIcon("M_ChessBoard.png");
        boardimage.setImage(boardimage.getImage().getScaledInstance(DefaultSet.canvasPosWidth , DefaultSet.canvasPosHeight, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
        JLabel jLabel = new JLabel(boardimage);
        jLabel.setLocation(DefaultSet.canvasPosX, DefaultSet.canvasPosY);
        jLabel.setSize(DefaultSet.canvasPosWidth, DefaultSet.canvasPosHeight);
        jLabel.addMouseListener(new BoardClickListener(this, this.chessBoard,gameController));
        jLayeredPane.add(jLabel, 2);

        /* Initialize player image.*/
        this.jLabel = new JLabel(ResourceUtils.getImageIcon("r.png"));
        this.jLabel.setLocation(DefaultSet.playerIconX, DefaultSet.playerIconY);
        this.jLabel.setSize(DefaultSet.pieceSize, DefaultSet.pieceSize);
        jLayeredPane.add(this.jLabel, 2);

        //初始化棋盘
        pieces = this.chessBoard.stringChessPieceMap;
        for (Map.Entry<String, ChessPiece> stringPieceEntry : pieces.entrySet()) {
            String key = stringPieceEntry.getKey();
            int[] pos = stringPieceEntry.getValue().position;
            int[] realpos = modelToViewConverter(pos);
            ImageIcon imageIcon = ResourceUtils.getImageIcon(key.substring(0, 2) + ".png");
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(DefaultSet.pieceSize, DefaultSet.pieceSize, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
            JLabel pieceLabel = new JLabel(imageIcon);
            pieceLabel.setLocation(realpos[0], realpos[1]);
            pieceLabel.setSize(DefaultSet.pieceSize, DefaultSet.pieceSize);
            pieceLabel.addMouseListener(new PieceOnClickListener(this,gameController,chessBoard,jLayeredPane,key));
            pieceLabel.setVisible(false);
            stringJLabelMap.put(stringPieceEntry.getKey(), pieceLabel);
            jLayeredPane.add(pieceLabel, 0);
        }

        //添加选中框
        ImageIcon kuangIcon = ResourceUtils.getImageIcon("kuang.png");
        kuangIcon.setImage(kuangIcon.getImage().getScaledInstance(DefaultSet.pieceSize, DefaultSet.pieceSize, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
        kuangLabel = new JLabel(kuangIcon);
        kuangLabel.setSize(DefaultSet.pieceSize, DefaultSet.pieceSize);
        jLayeredPane.add(kuangLabel,0);

        //添加时间标签
        JLabel totaltimerLabel = new JLabel();
        totaltimerLabel.setBounds(DefaultSet.timerLabelX, DefaultSet.timerLabelY, DefaultSet.timerLabelWidth, DefaultSet.timerLabelHeight);
        totaltimerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        totaltimerLabel.setForeground(Color.RED);
        jPanel.add(totaltimerLabel);
        totalTimer = new TotalTimer(totaltimerLabel);

        JLabel timerLabel = new JLabel();
        timerLabel.setBounds(DefaultSet.timerLabelX, DefaultSet.timerLabelY+ DefaultSet.timerLabelP, DefaultSet.timerLabelWidth, DefaultSet.timerLabelHeight);
        timerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        timerLabel.setForeground(Color.RED);
        jPanel.add(timerLabel);
        stepTimer = new StepTimer(timerLabel);
        this.setVisible(true);
    }

    public void run() throws InterruptedException{
        while (true){
            this.showPlayer('r');
            while (chessBoard.player == 'r')
                Thread.sleep(1000);
            if (gameController.hasWin(chessBoard) == 'r'){
                showWinner('r');
            }
            this.showPlayer('b');
            int[]pos= gameController.responseMoveChess(chessBoard, this);
            getKuangLabel().setLocation(DefaultSet.SX_OFFSET + pos[1] * DefaultSet.SX_COE, DefaultSet.SY_OFFSET + pos[0] * DefaultSet.SY_COE);
            getStepTimer().reStart();
            if (gameController.hasWin(chessBoard) == 'b'){
                showWinner('b');
            }
        }
    }


    public void newAIGame(){
        chessBoard.initChessBoard();
        Map<String, ChessPiece> stringChessPieceMap = chessBoard.stringChessPieceMap;
        for (Map.Entry<String, ChessPiece> stringPieceEntry : stringChessPieceMap.entrySet()) {
            String key = stringPieceEntry.getKey();
            ChessPiece value = stringPieceEntry.getValue();
            int[] position = value.position;
            JLabel jLabel = stringJLabelMap.get(key);
            int[] realpos = modelToViewConverter(position);
            jLabel.setLocation(realpos[0],realpos[1]);
            jLabel.setVisible(true);
        }
        getStepTimer().start();
        getTotalTimer().start();
        setSquareLocation(-5,-5);
        selectedPieceKey = null;
    }

    public void movePieceFromModel(String pieceKey, int[] to) {
        JLabel pieceObject = stringJLabelMap.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setLocation(sPos[0], sPos[1]);
        /* Clear 'from' and 'to' info on the board */
        selectedPieceKey = null;
    }

    public void movePieceFromAI(String pieceKey, int[] to) {
        ChessPiece inNewPos = chessBoard.getPiece(to);
        if (inNewPos != null) {
            JLabel jLabel = stringJLabelMap.get(inNewPos.key);
            jLabel.setVisible(false);
        }

        JLabel pieceObject = stringJLabelMap.get(pieceKey);
        int[] sPos = modelToViewConverter(to);
        pieceObject.setLocation(sPos[0], sPos[1]);
        selectedPieceKey = null;
    }

    private int[] modelToViewConverter(int pos[]) {
        int sx = pos[1] * DefaultSet.SX_COE + DefaultSet.SX_OFFSET, sy = pos[0] * DefaultSet.SY_COE + DefaultSet.SY_OFFSET;
        return new int[]{sx, sy};
    }

    private int[] viewToModelConverter(int sPos[]) {
        int ADDITIONAL_SY_OFFSET = 25;
        int y = (sPos[0] - DefaultSet.SX_OFFSET) / DefaultSet.SX_COE, x = (sPos[1] - DefaultSet.SY_OFFSET - ADDITIONAL_SY_OFFSET) / DefaultSet.SY_COE;
        return new int[]{x, y};
    }

    public void showPlayer(char player) {
        ImageIcon imageIcon = ResourceUtils.getImageIcon(player + ".png");
        jLabel.setIcon(imageIcon);
    }

    public void showWinner(char player) {
        if(player == 'r'){
            AudioPlayer audioPlayer = new AudioPlayer("gamewin.wav", false);
            audioPlayer.play();
            JOptionPane.showMessageDialog(this,"红方获得胜利！", "游戏信息", JOptionPane.INFORMATION_MESSAGE);
        }else {
            AudioPlayer audioPlayer = new AudioPlayer("gamelose.wav", false);
            audioPlayer.play();
            JOptionPane.showMessageDialog(this, "黑方获得胜利","游戏信息",  JOptionPane.INFORMATION_MESSAGE);
        }
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

    public void setSquareLocation(int[] position) {
        setSquareLocation(position[1],position[0]);
    }

    public void setSquareLocation(int x,int y) {
        if(status == GameStatusEnum.AI_START.status ){
            getKuangLabel().setLocation(DefaultSet.SX_OFFSET + x * DefaultSet.SX_COE, DefaultSet.SY_OFFSET + y * DefaultSet.SY_COE);
        }else {
            informationBoard.AddLog("请选择游戏模式！");
        }
    }
}
