package ucm.vm.offtheline;

import ucm.vm.engine.desktop.Graphics;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola");
        Graphics desktopEngine = new Graphics();
        desktopEngine.init(640, 480, "test");
        desktopEngine.run();
    }
}