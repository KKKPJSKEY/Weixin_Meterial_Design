package cn.edu.hubu.lhy.Weixin_MD.Ctt.DTO;

public class Person {
    private int selId;
    private String name;
    private String tel;

    public Person(int selId, String name, String tel) {
        this.selId = selId;
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(int age) {
        this.tel = tel;
    }

    public int get_id() {
        return selId;
    }

    public void setSelId(int selId) {
        this.selId = selId;
    }
}
