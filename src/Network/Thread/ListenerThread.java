package Network.Thread;

import java.io.IOException;
import java.util.function.Consumer;

public abstract class ListenerThread implements Runnable {
    protected Consumer<IOException> m_onErrorCb;

    protected ListenerThread() {
        m_onErrorCb = null;
    }

    public void OnError(Consumer<IOException> cb) {
        m_onErrorCb = cb;
    }

    public void run() {
        StartThread();
    }

    public abstract void StartThread();
}
