import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class AI extends BoardGUI {
    private boolean runAI;
    private NeuralNetwork nn;

    public AI() {
        super();
        runAI = true;
        nn = new NeuralNetwork(16, 20, 4, 3);
    }

    public AI(int[][] d) {
        super(d);
        runAI = true;
        nn = new NeuralNetwork(16, 20, 4, 3);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == 32) {
            runAI = !runAI;
            if (runAI)
                t.start();
            else
                t.stop();
            return;
        }

        switch (arg0.getKeyCode()) {
            case 39:
                data.right();
                break;
            case 37:
                data.left();
                break;
            case 38:
                data.up();
                break;
            case 40:
                data.down();
                break;
        }

        update();
        /** reset the game if all tiles are populated **/
        if (data.gameOver()) {
            // data = new Board();
            // update();
            runAI = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {

        // new ai algorithm

        float[] inputsArr = data.flattenBoard();
        float[] output = nn.output(inputsArr);

        while (true) {
            float max = Float.MIN_VALUE;
            int mind = 0;
            for (int i = 0; i < 4; i++) {
                if (output[i] > max) {
                    mind = i;
                    max = output[i];
                }
            }
            output[mind] = Float.MIN_VALUE;
            if (data.canMove(mind)) {
                switch (mind) {
                    case 0:
                        data.left();
                        break;
                    case 1:
                        data.right();
                        break;
                    case 2:
                        data.up();
                        break;
                    case 3:
                        data.down();
                        break;
                }
                break;
            }

        }

        update();
        if (data.gameOver()) {
            runAI = false;
            t.stop();
        }
    }
}