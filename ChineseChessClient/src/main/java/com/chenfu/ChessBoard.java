package com.chenfu;

import javax.swing.*;
import java.awt.*;

public class ChessBoard extends JPanel {

    private static final int ROW = 10;
    private static final int COL = 9;
    public int step = 0;
    int xx = DefultSet.chessBoarderX;
    int yy = DefultSet.chessBoarderY;
    int pp = DefultSet.chessBoarderP;
    public ChessPiece[][] chessPieces;
    private Point point;
    private final Image imageKuang;

    public ChessBoard() {

        chessPieces = new ChessPiece[ROW][COL];
        //设置默认棋子位置
        //设置黑车
        chessPieces[0][0] = new ChessPiece(20);
        //设置黑马
        chessPieces[0][1] = new ChessPiece(19);
        //设置黑相
        chessPieces[0][2] = new ChessPiece(18);
        //设置黑士
        chessPieces[0][3] = new ChessPiece(17);
        //设置黑将
        chessPieces[0][4] = new ChessPiece(16);
        //设置黑士
        chessPieces[0][5] = new ChessPiece(17);
        //设置黑相
        chessPieces[0][6] = new ChessPiece(18);
        //设置黑马
        chessPieces[0][7] = new ChessPiece(19);
        //设置黑车
        chessPieces[0][8] = new ChessPiece(20);
        //设置黑卒
        for (int i = 0; i < 9; i += 2) {
            chessPieces[3][i] = new ChessPiece(22);
        }
        //设置黑炮
        chessPieces[2][1] = new ChessPiece(21);
        chessPieces[2][7] = new ChessPiece(21);

        //设置红车
        chessPieces[9][0] = new ChessPiece(12);
        //设置红马
        chessPieces[9][1] = new ChessPiece(11);
        //设置红象
        chessPieces[9][2] = new ChessPiece(10);
        //设置红士
        chessPieces[9][3] = new ChessPiece(9);
        //设置红帅
        chessPieces[9][4] = new ChessPiece(8);
        //设置红士
        chessPieces[9][5] = new ChessPiece(9);
        //设置红象
        chessPieces[9][6] = new ChessPiece(10);
        //设置红马
        chessPieces[9][7] = new ChessPiece(11);
        //设置红车
        chessPieces[9][8] = new ChessPiece(12);
        //设置红兵
        for (int i = 0; i < 9; i += 2) {
            chessPieces[6][i] = new ChessPiece(14);
        }
        //设置红炮
        chessPieces[7][1] = new ChessPiece(13);
        chessPieces[7][7] = new ChessPiece(13);
        imageKuang = Utils.getImage("kuang.png");
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //设置背景图片
        ImageIcon image = Utils.getImageIcon("M_ChessBoard.png");
        image.setImage(image.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_FAST)); //调整图像的分辨率以适应容器
        image.paintIcon(this, g, 0, 0);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                //发现棋子就绘制
                if (chessPieces[i][j] != null) {
                    Image chessImage = chessPieces[i][j].getImage();
                    g.drawImage(chessImage, xx + j * pp, yy + i * pp, chessImage.getWidth(null), chessImage.getHeight(null), this);
                }
            }
        }
        if (point != null) {
            g.drawImage(imageKuang, xx + point.x * pp, yy + point.y * pp, 51, 51, this);
        }
    }

    public float Distance(Point src, Point des) {
        return (float) Math.sqrt((src.x - des.x) * (src.x - des.x) + (src.y - des.y) * (src.y - des.y));
    }

    public int IsBlock(Point src, Point des) {
        int n = 0;
        if (src.x == des.x) {
            int min, max;
            if (src.y > des.y) {
                min = des.y;
                max = src.y;
            } else {
                min = src.y;
                max = des.y;
            }
            for (int i = min + 1; i < max; i++) {
                if (chessPieces[i][src.x] != null) {
                    n++;
                }
            }
        } else {
            int min, max;
            if (src.x > des.x) {
                min = des.x;
                max = src.x;
            } else {
                min = src.x;
                max = des.x;
            }
            for (int i = min + 1; i < max; i++) {
                if (chessPieces[src.y][i] != null) {
                    n++;
                }
            }
        }
        return n;
    }

    public boolean move(Point src, Point des) {
        ChessPiece chessPiece = chessPieces[src.y][src.x];
        int id = chessPiece.getId();
        switch (id) {
            case 16:
                if (des.x >= 3 && des.x <= 5 && des.y >= 0 && des.y <= 2) {
                    if (Distance(src, des) == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
                break;
            case 8:
                //判断是否在九宫内
                if (des.x >= 3 && des.x <= 5 && des.y >= 7 && des.y <= 9) {
                    if (Distance(src, des) == 1) {
                        return true;
                    } else {
                        return false;
                    }
                }
                break;
            case 9:
                if (des.x >= 3 && des.x <= 5 && des.y >= 7 && des.y <= 9) {
                    //判断是否是斜着走
                    if (Distance(src, des) > 1.4 && Distance(src, des) < 1.5) {
                        return true;
                    } else {
                        return false;
                    }
                }
                break;
            case 17:
                if (des.x >= 3 && des.x <= 5 && des.y >= 0 && des.y <= 2) {
                    if (Distance(src, des) > 1.4 && Distance(src, des) < 1.5) {
                        return true;
                    } else {
                        return false;
                    }
                }
                break;
            case 18:
                if (des.y <= 4) {
                    if (Distance(src, des) > 2.8 && Distance(src, des) < 2.9) {
                        if (chessPieces[(des.y + src.y) / 2][(des.x + src.x) / 2] == null) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
                break;
            case 10:
                if (des.y >= 5) {
                    //判断是否斜走2格
                    if (Distance(src, des) > 2.8 && Distance(src, des) < 2.9) {
                        //是否未压象脚
                        if (chessPieces[(des.y + src.y) / 2][(des.x + src.x) / 2] == null) {
                            //输出走子信息
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                break;
            case 11:
            case 19:
                //判断是否走2根号5格
                if (Distance(src, des) > 2.2 && Distance(src, des) < 2.3) {
                    //判断是否压马脚
                    if (Math.abs(src.x - des.x) == 1) {
                        if (chessPieces[(src.y + des.y) / 2][src.x] == null) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if (chessPieces[src.y][(src.x + des.x) / 2] == null) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                break;
            case 12:
            case 20:
                if (src.y == des.y || src.x == des.x) {
                    //直线上没有棋子挡住
                    if (IsBlock(src, des) == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
                break;
            case 13:
            case 21:
                if (src.y == des.y || src.x == des.x) {
                    //直线上没有棋子挡住
                    if (IsBlock(src, des) == 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
                break;
            case 14:
                if (des.y <= src.y) {
                    //过河
                    if (des.y <= 4) {
                        if (Distance(src, des) == 1) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if (des.x == src.x && Math.abs(des.y - src.y) == 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                break;
            case 22:
                if (des.y >= src.y) {
                    //过河
                    if (des.y >= 5) {
                        if (Distance(src, des) == 1) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        if (des.x == src.x && Math.abs(des.y - src.y) == 1) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                break;
        }
        return false;
    }

    public boolean eatPiece(Point src, Point des) {
        ChessPiece srcPiece = chessPieces[src.y][src.x];
        ChessPiece desPiece = chessPieces[des.y][des.x];
        int id = srcPiece.getId();
        if((srcPiece.getId()&8) == (desPiece.getId()&8)||(srcPiece.getId()&16) == (desPiece.getId()&16) ){
            return false;
        }
        switch (id) {
            case 13:
            case 21:
                if (IsBlock(src, des) == 1) {
                    return true;
                } else {
                    return false;
                }
            default:
                return move(src, des);
        }
    }
}


