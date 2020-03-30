package sample.objects;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import javafx.beans.property.SimpleStringProperty;
import sample.interfaces.PersonInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Person implements PersonInterface {

    private SimpleStringProperty fullName = new SimpleStringProperty("");
    private SimpleStringProperty phone = new SimpleStringProperty("");
    private SimpleStringProperty passport = new SimpleStringProperty("");


    public Person(){

    }

    public Person(String fullName, String phone, String passport){
        this.fullName = new SimpleStringProperty(fullName);
        this.phone = new SimpleStringProperty(phone);
        this.passport = new SimpleStringProperty(passport);
    }

    @Override
    public String getFullName() {
        return fullName.get();
    }

    @Override
    public boolean setFullName(String fullName) {
        this.fullName.set(fullName);
        return false;
    }

    @Override
    public String getPhone() {
        return phone.get();
    }

    @Override
    public boolean setPhone(String phone) {
        this.phone.set(phone);
        return false;
    }

    @Override
    public String getPassport() {
        return passport.get();
    }

    @Override
    public boolean setPassport(String passport) {
        this.passport.set(passport);
        return false;
    }

    public SimpleStringProperty fullNameProperty(){return fullName;}

    public SimpleStringProperty phoneProperty(){return phone;}

    public SimpleStringProperty passportProperty(){return passport;}

    public boolean fullNameIsCorrect(String fullName){
        if(fullName.length() != 0)
            return true;

        return false;
    }

    public boolean phoneIsCorrect(String phone){
        Pattern p = Pattern.compile("^(((\\+7)|(8))\\d{10})|([1-9]\\d{6})$");
        Matcher m = p.matcher(phone);

        if(m.matches())
            return true;

        return false;
    }

    public boolean passportIsCorrect(String passport){
        Pattern p = Pattern.compile("^\\d{4} \\d{6}$");
        Matcher m = p.matcher(passport);

        if(m.matches())
            return true;

        return false;
    }
}
