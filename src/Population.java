import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Population implements ActionListener {
    private AI[] brains;
    private int size, iter, moves, scoreSum, citer;
    private final int fps = 60;
    private int[] scores;
    private Timer t;
    private int bestScore;
    private NeuralNetwork bestBrain;
    private float mutationRate = 0.02f;

    public Population(int size, int iter) {
        citer = 0;
        this.size = size;
        this.iter = iter;
        t = new Timer(1000 / fps, this);
        init();
    }

    public void init() {
        brains = new AI[size];
        for (int i = 0; i < size; i++) {
            brains[i] = new AI(false, false);
        }
        brains[0] = new AI(true, false);
        bestScore = 0;
        bestBrain = brains[0].getNN();
    }

    public void start() {
        scoreSum = 0;
        moves = 0;
        scores = new int[size];
        t.start();
    }

    public void step() {
        move();
    }

    public void move() {
        moves++;
        boolean end = true;
        for (int i = 0; i < brains.length; i++) {
            brains[i].AIstep();
            if (brains[i].gameOver()) {
                if (scores[i] == 0)
                    scoreSum += moves;
                scores[i] = moves;
            } else {
                end = false;
            }
        }

        if (end) {
            citer++;
            t.stop();
            if (citer == iter)
                return;
            naturalSelection();
        }
    }

    public void naturalSelection() {
        AI[] newBrains = new AI[brains.length];
        for (int i = 0; i < newBrains.length; i++) {
            newBrains[i] = new AI(false, false);
        }
        newBrains[0] = new AI(true, false);
        setBestBrain();
        newBrains[0].reset(bestBrain.clone());
        for (int i = 1; i < brains.length; i++) {
            NeuralNetwork child = selectParent().crossover(selectParent());
            child.mutate(mutationRate);
            newBrains[i].reset(child);
        }
        brains = newBrains;
        scoreSum = 0;
        moves = 0;
        scores = new int[size];
        t.start();
    }

    public void setBestBrain() {
        float max = 0;
        int maxInd = 0;
        for (int i = 0; i < brains.length; i++) {
            if (scores[i] > max) {
                max = scores[i];
                maxInd = i;
            }
        }
        if (max > bestScore) {
            bestBrain = brains[maxInd].getNN().clone();
            bestScore = scores[maxInd];
        }
        System.out.println(bestScore);
    }

    public NeuralNetwork selectParent() {
        float rand = (float) (Math.random() * scoreSum);
        float sum = 0;
        for (int i = 0; i < brains.length; i++) {
            sum += scores[i];
            if (sum > rand) {
                return brains[i].getNN();
            }
        }
        return brains[0].getNN();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        step();
    }
}