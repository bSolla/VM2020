package ucm.vm.offthelinelogic;

import ucm.vm.engine.Graphics;
import ucm.vm.generallogic.Logic;

public class OffTheLineLogic implements Logic {
    Graphics engine;

    public void init(Graphics graphics) {
        engine = graphics;
    }

    @Override
    public void render() {
        engine.clear(0, 0,0);
        engine.setColor(255, 50, 0);
        engine.drawLine(0, 0, 100, 100);
        engine.drawLine(100, 100, 0, 200);
    }

    @Override
    public void update(double deltaTime) {
        // movimientos etc
    }
}