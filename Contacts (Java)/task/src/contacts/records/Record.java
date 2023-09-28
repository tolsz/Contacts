package contacts.records;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Record implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private final LocalDateTime created;
    protected LocalDateTime timeEdit;
    protected String phoneNumber;
    public final boolean isPerson;

    public abstract void showInfo();
    public abstract String shortInfo();
    public abstract String getFields();
    public abstract boolean change(String value);

    public Record(boolean isPerson) {
        created = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        timeEdit = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        this.isPerson = isPerson;
    }

    public void setPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\+?(\\(\\w+\\)|\\w+[ -]\\(\\w{2,}\\)|\\w+)([ -]\\w{2,})*$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (matcher.find()) {
            this.phoneNumber = phoneNumber;
        } else {
            this.phoneNumber = "";
            System.out.println("Wrong number format!");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        if (phoneNumber.isEmpty()) return "[no data]";
        return phoneNumber;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getTimeEdit() {
        return timeEdit;
    }

    public void setTimeEdit() {
        timeEdit = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }
}
