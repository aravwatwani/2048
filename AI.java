import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class AI extends BoardGUI {
    private boolean runAI;

    public AI(){
        super();
        runAI = true;
    }
    public AI(int[][] d){
        super(d);
        runAI = true;
    }

    @Override
	public void keyPressed(KeyEvent arg0) {
        if(arg0.getKeyCode() == 32){
            runAI = !runAI;
            if(runAI) t.start();
            else t.stop();
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
        int rand = (int) (Math.random()*4);
        if(rand == 0) data.left();
        if(rand == 1) data.right();
        if(rand == 2) data.up();
        if(rand == 3) data.down();

        update();
        if(data.gameOver()){
            runAI = false;
            t.stop();
        }
    }
}