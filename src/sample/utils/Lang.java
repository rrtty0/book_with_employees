package sample.utils;

import java.util.Locale;

public class Lang {

    private String name;
    private Locale locale;
    private int index;

    public Lang(int index, String name, Locale locale)
    {
        this.name = name;
        this.locale = locale;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return name;
    }
}
