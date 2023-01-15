package tk.lukashuth.draconem.utils;

import tk.lukashuth.draconem.menu.GUI;
import tk.lukashuth.draconem.menu.GameMenu;

public class Player {
    private double position;
    private String name;
    private int money;
    private int groupSize;
    private double moneyScale;
    private long gameId;
    private Game parent;
    private boolean isFinished;

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    private PlayerClass playerClass;
    public Player()
    {
        this.isFinished = false;
        this.parent = null;
        this.position = 0;
        this.name = null;
        this.money = 0;
        this.groupSize = 1;
        this.moneyScale = 1.0;
        this.gameId = 0;
        this.playerClass = PlayerClass.NONE;
    }
    public Player(String name, Game parent)
    {
        this();
        this.parent = parent;
        this.name = name;
    }
    public Player(double position, String name, int money, long gameId)
    {
        this();
        this.position = position;
        this.name = name;
        this.money = money;
        this.gameId = gameId;
    }
    public Player(double position, String name, int money, int groupSize, double moneyScale, long gameId, PlayerClass playerClass)
    {
        this(position, name, money, gameId);
        this.groupSize = groupSize;
        this.moneyScale = moneyScale;
        this.playerClass = playerClass;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public double getMoneyScale() {
        return moneyScale;
    }

    public void setMoneyScale(double moneyScale) {
        this.moneyScale = moneyScale;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public PlayerClass getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;
    }
    public void move(double steps)
    {
        this.position += Math.min(steps, this.parent.getPlayfield().toNextPause(this.position));
    }
    public void addMoney(int money)
    {
        this.money += money;
    }
    public double calcMoneyScale(){
        double scale = ((this.moneyScale-1)/2)+1;
        scale *= 0.5*Math.random() + 0.5;
        return scale;
    }
}
