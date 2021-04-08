package cn.edu.hubu.lhy.Weixin_MD.Recycler_View;

import java.util.ArrayList;
import java.util.List;

import cn.edu.hubu.lhy.Weixin_MD.R;

public class NatureModel {

    private int imageID;
    private String title;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static List<NatureModel> getObjectList(){
        List<NatureModel> dataList = new ArrayList<>();
        int[] images = getImages();

        for (int i = 0; i< images.length; i++){
            NatureModel nature = new NatureModel();
            nature.setImageID(images[i]);
            nature.setTitle("Picture "+ i);
            dataList.add(nature);
        }

        return dataList;
    }

    private static int[] getImages(){

        int[] images = {

                R.drawable.flower1, R.drawable.flower2, R.drawable.flower3, R.drawable.flower4, R.drawable.flower5,
                R.drawable.flower6, R.drawable.flower7, R.drawable.flower8, R.drawable.flower9, R.drawable.flower10,
                R.drawable.flower11, R.drawable.flower12, R.drawable.flower13, R.drawable.flower14, R.drawable.flower15,
                R.drawable.flower16, R.drawable.flower17, R.drawable.flower18, R.drawable.flower19, R.drawable.flower20, R.drawable.flower21,
                R.drawable.flower22, R.drawable.flower23, R.drawable.flower24, R.drawable.flower25, R.drawable.flower26,

                R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,
                R.drawable.image7, R.drawable.image8, R.drawable.image9, R.drawable.image10, R.drawable.image11, R.drawable.image12,
                R.drawable.image13, R.drawable.image14, R.drawable.image15, R.drawable.image16, R.drawable.image17, R.drawable.image18,
                R.drawable.image19, R.drawable.image20, R.drawable.image21, R.drawable.image22, R.drawable.image23, R.drawable.image24,
                R.drawable.image25, R.drawable.image26, R.drawable.image27, R.drawable.image28, R.drawable.image29, R.drawable.image30,
                R.drawable.image31, R.drawable.image32, R.drawable.image33, R.drawable.image34, R.drawable.image35, R.drawable.image36, R.drawable.image37,
                R.drawable.image38, R.drawable.image39, R.drawable.image40, R.drawable.image41, R.drawable.image42, R.drawable.image43,
                R.drawable.image44, R.drawable.image45, R.drawable.image46,

                R.drawable.thumb1, R.drawable.thumb2, R.drawable.thumb3, R.drawable.thumb4, R.drawable.thumb5

        };

        return images;
    }
}
