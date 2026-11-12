package ao.co.isptec.aplm.servicesimple;

import android.app.Application;

public class CounterApplication extends Application {

    private int startCount = 0;

    public void incrementStartCount() {
        startCount++;
    }

    public int getStartCount() {
        return startCount;
    }
}
