package cn.edu.hubu.lhy.Weixin_MD.Mes.Music_Player;

import android.app.Application;

public class MusicPlayerStatus extends Application {
    private int update=-1;
    private int current=-1;

    public int getUpdate() {
        return update;
    }

    public void setUpdate(int update) {
        this.update = update;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
