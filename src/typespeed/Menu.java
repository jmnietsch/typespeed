package typespeed;

public class Menu {
    private static final String GAME_NAME = "Typespeed";

    private static final long serialVersionUID = 4530308932580747353L;

    private static Window mainWindow =  new Window(GAME_NAME);

    private Menu() {

    }

    public static void main(String[] args){
        Menu menu = new Menu();
        Game game = new Game();

        mainWindow.setGame(game);
        mainWindow.pack();

    }

    static int getScreenheight(){
        return mainWindow.getComponent(0).getBounds().height - Inputline.LINEHEIGHT - Tile.TILEHEIGHT;
        //Fixme: getComponent(0) is not nice. Do something else. eg save Canvas of game instance
    }
}
