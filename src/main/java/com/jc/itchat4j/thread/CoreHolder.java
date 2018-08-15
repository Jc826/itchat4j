package com.jc.itchat4j.thread;

import com.jc.itchat4j.core.Core;

public class CoreHolder {
    private static ThreadLocal<Core> contextHolder = new ThreadLocal();

    public static Core getCore() {
        return contextHolder.get();
    }

    public static void setCore(Core core) {
        contextHolder.set(core);
    }

    public static void clear() {
        contextHolder.remove();
    }
}
