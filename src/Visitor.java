import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


class Visitor {
    private static AtomicInteger idCounter = new AtomicInteger();
    private final int id;
    private final String name;
    private final Map<Integer, Integer> preferences;
    private int totalTimeWaitingLine = 0;
    private int totalTimePlayGround = 0;
    private boolean isBusy;
    private PlayGround currentPlayGround = null;
    private int totalVisitedPlaygrounds = 0;

    public Visitor(String name) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        this.preferences = generatePreferences();
        this.isBusy = false;
    }

    private Map<Integer, Integer> generatePreferences() {
        Map<Integer, Integer> temp = new HashMap<>();
        Random r = new Random();
        int remaining = 100;
        for (int i = 1; i <= 5; i++) { // 5 play grounds
            int preference = (i == 5) ? remaining : r.nextInt(remaining + 1);
            temp.put(i, preference);
            remaining -= preference;
        }
        return temp;
    }

    public void setBusy(PlayGround playGround) {
        isBusy = true;
        currentPlayGround = playGround;
        totalVisitedPlaygrounds++;
    }

    public void setNotBusy() {
        isBusy = false;
        currentPlayGround = null;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public Map<Integer, Integer> getPreferences() {
        return preferences;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addTimeWaiting(int time) {
        totalTimeWaitingLine += time;
    }

    public void addTimePlaying(int time) {
        totalTimePlayGround += time;
    }

    public PlayGround getCurrentPlayGround() {
        return currentPlayGround;
    }

    public int getTotalVisitedPlaygrounds() {
        return totalVisitedPlaygrounds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Visitor{id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", totalTimeWaitingLine=").append(totalTimeWaitingLine)
                .append(", totalTimePlayGround=").append(totalTimePlayGround)
                .append(", isBusy=").append(isBusy)
                .append(", currentPlayGround=").append(currentPlayGround != null ? currentPlayGround.getName() : "None")
                .append(", totalVisitedPlaygrounds=").append(totalVisitedPlaygrounds)
                .append('}');
        return sb.toString();
    }
}
