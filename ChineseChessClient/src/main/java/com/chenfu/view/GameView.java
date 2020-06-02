package com.chenfu.view;

import com.chenfu.DefaultSet;
import com.chenfu.components.InformationBoard;
import com.chenfu.adapter.*;
import com.chenfu.components.DiyButton;
import com.chenfu.listener.*;
import com.chenfu.netty.Client;
import com.chenfu.pojo.*;
import com.chenfu.control.GameController;
import com.chenfu.timer.JudgeTimer;
import com.chenfu.timer.StepTimer;
import com.chenfu.timer.TotalTimer;
import com.chenfu.utils.AudioPlayer;
import com.chenfu.utils.JsonUtils;
import com.chenfu.utils.ResourceUtils;
import io.netty.channel.Channel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameView extends JFrame {
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
    private final JudgeTimer judgeTimer;
    private JLabel kuangLabel;
    private Player player;
    private Player competitor;
    private JLabel loginMenu;
    public int status;
    public boolean flag;
    private List<String> boardstatus = new ArrayList<>();
    /*0初始状态
    1人机开始状态
    2网络未登录
    3网络已登录
    4网络匹配成功
    5人机未开始状态*/
    public GameView() {
        gameController = new GameController();
        chessBoard = new ChessBoard('r');
        jLayeredPane = this.getLayeredPane();
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
        DiyButton diyButton1 = new DiyButton("新对局", DefaultSet.buttonX, DefaultSet.buttonY, "button.png");
        diyButton1.addActionListener(new NewGameListener(this, informationBoard,gameController));
        jPanel.add(diyButton1);

        DiyButton diyButton2 = new DiyButton("悔棋", DefaultSet.buttonX + DefaultSet.buttonP, DefaultSet.buttonY, "button.png");
        diyButton2.addActionListener(new WithDrawListener(this));
        jPanel.add(diyButton2);

        DiyButton diyButton3 = new DiyButton("求和", DefaultSet.buttonX + DefaultSet.buttonP * 2, DefaultSet.buttonY, "button.png");
        diyButton3.addActionListener(new AskdrawListener(this));
        jPanel.add(diyButton3);

        DiyButton diyButton4 = new DiyButton("认输", DefaultSet.buttonX + DefaultSet.buttonP * 3, DefaultSet.buttonY, "button.png");
        diyButton4.addActionListener(new GaveUpListener(this));
        jPanel.add(diyButton4);


        JTextField jTextField = new JTextField();
        jTextField.setBounds( DefaultSet.textFieldX, DefaultSet.textFieleY, DefaultSet.textFieldWidth, DefaultSet.textFieleHeight);
        jTextField.setFont(new Font("黑体",Font.BOLD, 15));
        jPanel.add(jTextField);

        DiyButton diyButton5 = new DiyButton("", DefaultSet.sendButtonX, DefaultSet.sendButtonY, "send4.png");
        diyButton5.addActionListener(new SendListener(this,jTextField));
        jPanel.add(diyButton5);
        //添加菜单
        Font font = new Font("宋体", Font.BOLD, 40);
        Color color = Color.blue;
        loginMenu = new JLabel("用户登录");
        loginMenu.setFont(font);
        loginMenu.setForeground(color);
        loginMenu.setBounds(DefaultSet.menuX, DefaultSet.menuY, DefaultSet.menuWidth, DefaultSet.menuHeight);
        loginMenu.addMouseListener(new LoginAdapter(this, loginMenu));
        contentPanel.add(loginMenu);

        JLabel menu2 = new JLabel("人机对战");
        menu2.setFont(font);
        menu2.setForeground(color);
        menu2.setBounds(DefaultSet.menuX, DefaultSet.menuY + DefaultSet.menuP, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu2.addMouseListener(new AIModeAdapter(this, chessBoard, gameController, menu2));
        contentPanel.add(menu2);

        JLabel menu3 = new JLabel("网络对战");
        menu3.setFont(font);
        menu3.setForeground(color);
        menu3.setBounds(DefaultSet.menuX, DefaultSet.menuY + DefaultSet.menuP * 2, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu3.addMouseListener(new NetworkModeAdapter(this, informationBoard, menu3));
        contentPanel.add(menu3);

        JLabel menu4 = new JLabel("关闭音乐");
        menu4.setFont(font);
        menu4.setForeground(color);
        menu4.setBounds(DefaultSet.menuX, DefaultSet.menuY + DefaultSet.menuP * 3, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu4.addMouseListener(new MusicControlAdapter(audioPlayer, menu4));
        contentPanel.add(menu4);

        JLabel menu5 = new JLabel("退出游戏");
        menu5.setFont(font);
        menu5.setForeground(color);
        menu5.setBounds(DefaultSet.menuX, DefaultSet.menuY + DefaultSet.menuP * 4, DefaultSet.menuWidth, DefaultSet.menuHeight);
        menu5.addMouseListener(new ExitAdapter(this, menu5));
        contentPanel.add(menu5);
        //添加棋盘
        ImageIcon boardimage = ResourceUtils.getImageIcon("M_ChessBoard.png");
        boardimage.setImage(boardimage.getImage().getScaledInstance(DefaultSet.canvasPosWidth, DefaultSet.canvasPosHeight, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
        JLabel jLabel = new JLabel(boardimage);
        jLabel.setLocation(DefaultSet.canvasPosX, DefaultSet.canvasPosY);
        jLabel.setSize(DefaultSet.canvasPosWidth, DefaultSet.canvasPosHeight);
        jLabel.addMouseListener(new BoardClickListener(this, chessBoard));
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
            pieceLabel.addMouseListener(new PieceOnClickListener(this, chessBoard, key));
            pieceLabel.setVisible(false);
            stringJLabelMap.put(stringPieceEntry.getKey(), pieceLabel);
            jLayeredPane.add(pieceLabel, 0);
        }

        //添加选中框
        ImageIcon kuangIcon = ResourceUtils.getImageIcon("kuang.png");
        kuangIcon.setImage(kuangIcon.getImage().getScaledInstance(DefaultSet.pieceSize, DefaultSet.pieceSize, Image.SCALE_SMOOTH)); //调整图像的分辨率以适应容器
        kuangLabel = new JLabel(kuangIcon);
        kuangLabel.setLocation(-100,-100);
        kuangLabel.setSize(DefaultSet.pieceSize, DefaultSet.pieceSize);
        jLayeredPane.add(kuangLabel, 0);

        //添加时间标签
        JLabel totaltimerLabel = new JLabel();
        totaltimerLabel.setBounds(DefaultSet.timerLabelX, DefaultSet.timerLabelY, DefaultSet.timerLabelWidth, DefaultSet.timerLabelHeight);
        totaltimerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        totaltimerLabel.setForeground(Color.RED);
        jPanel.add(totaltimerLabel);
        totalTimer = new TotalTimer(this,totaltimerLabel);

        JLabel timerLabel = new JLabel();
        timerLabel.setBounds(DefaultSet.timerLabelX, DefaultSet.timerLabelY + DefaultSet.timerLabelP, DefaultSet.timerLabelWidth, DefaultSet.timerLabelHeight);
        timerLabel.setFont(new Font("华文行楷", Font.CENTER_BASELINE, 28));
        timerLabel.setForeground(Color.RED);
        jPanel.add(timerLabel);
        stepTimer = new StepTimer(this,timerLabel);
        this.setVisible(true);

        judgeTimer = new JudgeTimer(this, chessBoard, gameController);
        audioPlayer.play();
    }

    public void newGame(char c) {
        chessBoard.initChessBoard(c);
        Map<String, ChessPiece> stringChessPieceMap = chessBoard.stringChessPieceMap;
        for (Map.Entry<String, ChessPiece> stringPieceEntry : stringChessPieceMap.entrySet()) {
            String key = stringPieceEntry.getKey();
            ChessPiece value = stringPieceEntry.getValue();
            int[] position = value.position;
            JLabel jLabel = stringJLabelMap.get(key);
            int[] realpos = modelToViewConverter(position);
            jLabel.setLocation(realpos[0], realpos[1]);
            jLabel.setVisible(true);
        }
        stepTimer.start();
        totalTimer.start();
        setSquareLocation(-5, -5);
        saveBoard();
        selectedPieceKey = null;
    }

    public int[] inversePos(int[] pos) {
        int[] inversePosition = {chessBoard.BOARD_HEIGHT - 1 - pos[0], chessBoard.BOARD_WIDTH - 1 - pos[1]};
        return inversePosition;
    }

    public void saveBoard() {
        ArrayList<String> shortPieces = new ArrayList<>(14);
        for (Map.Entry<String, ChessPiece> stringPieceEntry : chessBoard.stringChessPieceMap.entrySet()) {
            ChessPiece chessPiece = stringPieceEntry.getValue();
            shortPieces.add(chessPiece.key+chessPiece.position[0]+chessPiece.position[1]);
        }
        boardstatus.add(JsonUtils.objectToJson(shortPieces));
    }

    public void loadBoard(String json){
        List<String> shortPieces = JsonUtils.jsonToList(json, String.class);
        Iterator<Map.Entry<String, JLabel>> iterator = stringJLabelMap.entrySet().iterator();
        while (iterator.hasNext()){
            JLabel value = iterator.next().getValue();
            value.setVisible(false);
        }
        chessBoard.clearchessBoard();
        for(String s:shortPieces){
            String pieceKey = s.substring(0,3);
            int x = s.charAt(3)-'0';
            int y = s.charAt(4)-'0';
            int[] position = {x,y};
            ChessPiece chessPiece = new ChessPiece(pieceKey, position);
            chessBoard.chessPieceArray[x][y] = chessPiece;
            chessBoard.stringChessPieceMap.put(pieceKey,chessPiece);
            JLabel jLabel = stringJLabelMap.get(pieceKey);
            int[] realpos = modelToViewConverter(position);
            jLabel.setLocation(realpos[0], realpos[1]);
            jLabel.setVisible(true);
        }
    }

    public void goBackOnce(){
        int size = boardstatus.size();
        if(size<2){
            informationBoard.addLog("当前不能悔棋！");
            return;
        }
        boardstatus.remove(size-1);
        loadBoard(boardstatus.get(size-2));
    }

    public void goBackTwice(){
        int size = boardstatus.size();
        if(size<3){
            informationBoard.addLog("当前不能悔棋！");
            return;
        }
        boardstatus.remove(size-1);
        boardstatus.remove(size-2);
        loadBoard(boardstatus.get(size-3));
    }

    public void movePieceFromModel(String pieceKey, int[] to, boolean send) {
        saveBoard();
        chessBoard.updatePiece(pieceKey, to);
        JLabel pieceObject = stringJLabelMap.get(pieceKey);
        int[] despos = modelToViewConverter(to);
        pieceObject.setLocation(despos[0], despos[1]);
        selectedPieceKey = null;
        if (send == true) {
            Channel channel = Client.getInstance().getChannel();
            ChessPiece chessPiece = pieces.get(pieceKey);
            PieceMsg pieceMsg = new PieceMsg(competitor.getUsername(), pieceKey, inversePos(chessPiece.position), inversePos(to));
            DataContent dataContent = new DataContent(pieceMsg);
            dataContent.setAction(MsgActionEnum.PIECELPOS.type);
            channel.writeAndFlush(dataContent);
            chessBoard.wait = true;
        }

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

    public void setSquareLocation(int x, int y) {
        if (status == GameStatusEnum.AI_START.status || status == GameStatusEnum.NETWORK_START.status) {
            getKuangLabel().setLocation(DefaultSet.SX_OFFSET + x * DefaultSet.SX_COE, DefaultSet.SY_OFFSET + y * DefaultSet.SY_COE);
        } else {
            informationBoard.addLog("GameView:请选择游戏模式！");
        }
    }

    private int[] modelToViewConverter(int pos[]) {
        int sx = pos[1] * DefaultSet.SX_COE + DefaultSet.SX_OFFSET, sy = pos[0] * DefaultSet.SY_COE + DefaultSet.SY_OFFSET;
        return new int[]{sx, sy};
    }

    public int[] viewToModelConverter(int sPos[]) {
        int ADDITIONAL_SY_OFFSET = 25;
        int y = (sPos[0] - DefaultSet.SX_OFFSET) / DefaultSet.SX_COE, x = (sPos[1] - DefaultSet.SY_OFFSET - ADDITIONAL_SY_OFFSET) / DefaultSet.SY_COE;
        return new int[]{x, y};
    }

    public void showPlayer(char player) {
        ImageIcon imageIcon = ResourceUtils.getImageIcon(player + ".png");
        jLabel.setIcon(imageIcon);
    }

    public void win(){
        if(!chessBoard.inverse){
            showWinner('r');
        }else {
            showWinner('b');
        }
    }

    public void lose(){
        if(!chessBoard.inverse){
            showWinner('b');
        }else {
            showWinner('r');
        }
    }

    public boolean showWinner(char player) {
        AudioPlayer gameWinPlayer = new AudioPlayer("gamewin.wav", false);
        AudioPlayer gamelosePlayer = new AudioPlayer("gamelose.wav", false);
        if (player == 'r') {
            if(!chessBoard.inverse){
                gameWinPlayer.play();
            }else {
                gamelosePlayer.play();
            }
            totalTimer.stop();
            stepTimer.stop();
            JOptionPane.showMessageDialog(this, "红方获得胜利！", "游戏信息", JOptionPane.INFORMATION_MESSAGE);
            status = GameStatusEnum.INIT.status;
            flag = false;
            return true;
        } else if (player == 'b') {
            if(chessBoard.inverse){
                gameWinPlayer.play();
            }else {
                gamelosePlayer.play();
            }
            totalTimer.stop();
            stepTimer.stop();
            JOptionPane.showMessageDialog(this, "黑方获得胜利!", "游戏信息", JOptionPane.INFORMATION_MESSAGE);
            status = GameStatusEnum.INIT.status;
            flag = false;
            return true;
        }else if(player == 'p'){
            judgeTimer.stop();
            totalTimer.stop();
            stepTimer.stop();
            gamelosePlayer.play();
            JOptionPane.showMessageDialog(this, "双方协商和棋!", "游戏信息", JOptionPane.INFORMATION_MESSAGE);

        }
        return false;
    }

    public StepTimer getStepTimer() {
        return stepTimer;
    }

    public JudgeTimer getJudgeTimer() {
        return judgeTimer;
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
        setSquareLocation(position[1], position[0]);
    }

    public Player getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Player competitor) {
        this.competitor = competitor;
    }

    public JLabel getLoginMenu() {
        return loginMenu;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public List<String> getBoardstatus() {
        return boardstatus;
    }
}
