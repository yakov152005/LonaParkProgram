import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class PlayGround {
    private static AtomicInteger idCounter = new AtomicInteger();
    private final int id;
    private final String name;
    private final int activityTime;
    private final int childrenCapacity;
    private final List<Visitor> currentVisitors = new ArrayList<>();
    private int totalVisitors = 0;
    private boolean isAvailable = true;

    public PlayGround(String name) {
        this.id = idCounter.incrementAndGet();
        this.name = name;
        Random r = new Random();
        this.activityTime = r.nextInt(21) + 10; // 10-30 seconds
        this.childrenCapacity = r.nextInt(6) + 1; // 1-6 visitors
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getActivityTime() {
        return activityTime;
    }

    public List<Visitor> getCurrentVisitors() {
        return currentVisitors;
    }

    public int getChildrenCapacity() {
        return childrenCapacity;
    }

    public synchronized boolean addVisitor(Visitor visitor) {
        if (currentVisitors.size() < childrenCapacity && isAvailable) {
            currentVisitors.add(visitor);
            visitor.setBusy(this);
            totalVisitors++;
            return true;
        }
        return false;
    }

    public synchronized void operate() {
        if (!isAvailable) return;
        isAvailable = false;

        // Simulate the ride activity time
        new Thread(() -> {
            try {
                Thread.sleep(activityTime * 1000);
                synchronized (PlayGround.this) {
                    for (Visitor visitor : currentVisitors) {
                        visitor.setNotBusy(); // שחרור המבקרים לאחר סיום הפעילות
                        visitor.addTimePlaying(activityTime); // ספירת זמן הפעילות של המבקר
                        Thread.sleep(10000); // נוחים למשך 10 שניות
                    }
                    currentVisitors.clear();
                    isAvailable = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }




    public synchronized int getTotalVisitors() {
        return totalVisitors;
    }

    public synchronized int getCurrentVisitorsCount() {
        return currentVisitors.size();
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PlayGround{id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", activityTime=").append(activityTime)
                .append(", childrenCapacity=").append(childrenCapacity)
                .append(", totalVisitors=").append(totalVisitors)
                .append(", currentVisitors=").append(currentVisitors.size())
                .append(", isAvailable=").append(isAvailable)
                .append('}');
        return sb.toString();
    }
}