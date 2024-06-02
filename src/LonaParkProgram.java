import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LonaParkProgram {
    private final List<PlayGround> playGrounds = new ArrayList<>();
    private final List<Visitor> visitors = new ArrayList<>();
    public final List<String> PLAY_GROUND = List.of("ANKONDA", "BLACK MOMBA", "ROLLER COASTER", "WATER SLIDE", "FERRIS WHEEL");
    private boolean running = true;

    public LonaParkProgram() {
        for (String name : PLAY_GROUND) {
            playGrounds.add(new PlayGround(name));
        }
        for (int i = 1; i <= 50; i++) {
            visitors.add(new Visitor("Visitor " + i));
        }
        startSimulation();
    }

    private void startSimulation() {
        Thread simulationThread = new Thread(() -> {
            try {
                for (int i = 0; i < 60; i++) {
                    if (!running) break;
                    simulate();
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                stopSimulation();
            }
        });
        simulationThread.start();
    }

    private void simulate() {
        for (Visitor visitor : visitors) {
            if (!visitor.isBusy()) {
                Map<Integer, Integer> preferences = visitor.getPreferences();
                for (Integer playgroundId : preferences.keySet()) {
                    PlayGround playGround = null;
                    for (PlayGround pg : playGrounds) {
                        if (pg.getId() == playgroundId && pg.isAvailable()) { // וודא שהמתקן פנוי
                            playGround = pg;
                            break;
                        }
                    }
                    if (playGround != null && playGround.addVisitor(visitor)) {
                        visitor.addTimeWaiting(5);
                        break;
                    }
                }
            } else {
                visitor.addTimePlaying(5);
            }
        }
        for (PlayGround playGround : playGrounds) {
            if (playGround.isAvailable() && !playGround.getCurrentVisitors().isEmpty()) {
                playGround.operate(); // קריאה לפעולת ההפעלה של המתקן כאשר הוא פנוי ויש לו מבקרים
            }
        }
        printStatus();
    }

    private void printStatus() {
        System.out.println("Current Park Status:");
        for (PlayGround playGround : playGrounds) {
            System.out.println(playGround);
        }
        for (Visitor visitor : visitors) {
            System.out.println(visitor);
        }
    }

    private void stopSimulation() {
        running = false;
        System.out.println("Final Park Status:");
        for (Visitor visitor : visitors) {
            System.out.println(visitor);
        }
        for (PlayGround playGround : playGrounds) {
            System.out.println(playGround);
        }
    }

    public static void main(String[] args) {
        new LonaParkProgram();
    }
}