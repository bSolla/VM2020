package ucm.vm.offtheline;

import ucm.vm.engine.desktop.Graphics;
import ucm.vm.offthelinelogic.OffTheLineLogic;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola");


        Graphics desktopEngine = new Graphics();
        OffTheLineLogic gameLogic = new OffTheLineLogic();

        gameLogic.init(desktopEngine);

        desktopEngine.setLogic(gameLogic);
        desktopEngine.init(640, 480, "test");
        desktopEngine.run();
    }
}