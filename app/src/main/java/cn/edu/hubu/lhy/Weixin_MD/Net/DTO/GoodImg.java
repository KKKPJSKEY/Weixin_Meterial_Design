package cn.edu.hubu.lhy.Weixin_MD.Net.DTO;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class GoodImg implements Serializable {
    Drawable drawable;

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}
