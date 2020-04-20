import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AI extends BoardGUI {
    private boolean runAI, gameOver, mod;
    private NeuralNetwork nn;

    public AI() {
        super();
        mod = true;
        runAI = false;
        gameOver = false;
        nn = new NeuralNetwork(16, 20, 4, 3);
    }

    public AI(boolean a, boolean b) {
        super(a);
        mod = b;
        runAI = false;
        gameOver = false;
        nn = new NeuralNetwork(16, 20, 4, 3);
    }

    public AI(int[][] d) {
        super(d);
        mod = true;
        runAI = false;
        gameOver = false;
        nn = new NeuralNetwork(d.length * d[0].length, 20, 4, 3);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub
        /* call the helper methods for the Board object data */
        if (!mod)
            return;

        if (arg0.getKeyCode() == 32) {
            if (gameOver) {
                data = new Board();
                update();
                gameOver = false;
                if (runAI)
                    t.start();
            } else {
                runAI = !runAI;
                if (runAI)
                    t.start();
                else
                    t.stop();
            }
            return;
        }

        if (runAI || gameOver)
            return;

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
            gameOver = true;
        }
    }

    public NeuralNetwork getNN() {
        return nn;
    }

    public void reset(NeuralNetwork n) {
        nn = n;
        data = new Board();
    }

    public void AIstep() {
        float[] inputsArr = data.flattenBoard();
        float[] output = nn.output(inputsArr);

        for (int n = 0; n < 4; n++) {
            float max = -Float.MAX_VALUE;
            int mind = 0;
            for (int i = 0; i < 4; i++) {
                if (output[i] > max) {
                    mind = i;
                    max = output[i];
                }
            }
            output[mind] = -Float.MAX_VALUE;
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

        // every move call populate and update
        update();

        if (data.gameOver()) {
            gameOver = true;
        }
    }

    public boolean gameOver() {
        return gameOver;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        AIstep();
        if (gameOver)
            t.stop();
    }
}