package contacts.records;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Person extends Record {
    private String surname;
    private LocalDate birthDate;
    private String gender;

    public Person() {
        super(true);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthDate() {
        if (birthDate == null) {
            return "[no data]";
        }
        return birthDate.toString();
    }

    public void setBirthDate(String birthDate) {
        try {
            this.birthDate = LocalDate.parse(birthDate);
        } catch (DateTimeParseException e) {
            this.birthDate = null;
            System.out.println("Bad birth date!");
        }
    }

    public String getGender() {
        if (gender.isEmpty()) return "[no data]";
        return gender;
    }

    public void setGender(String gender) {
        if ("M".equalsIgnoreCase(gender) || "F".equalsIgnoreCase(gender)) {
            this.gender = gender;
            return;
        }
        System.out.println("Bad gender!");
        this.gender = "";
    }

    @Override
    public void showInfo() {
        System.out.printf("""
                Name: %s
                Surname: %s
                Birth date: %s
                Gender: %s
                Number: %s
                Time created: %s
                Time last edit: %s\n""", getName(), getSurname(), getBirthDate(),
                getGender(), getPhoneNumber(), getCreated(), getTimeEdit());
    }

    @Override
    public String shortInfo() {
        return getName() + " " + getSurname();
    }

    @Override
    public String getFields() {
        return "(name, surname, birth, gender, number):";
    }

    @Override
    public boolean change(String value) {
        Scanner scanner = new Scanner(System.in);
        switch (value) {
            case "name" -> {
                System.out.print("Enter name: ");
                setName(scanner.nextLine());
                return true;
            }
            case "surname" -> {
                System.out.print("Enter surname: ");
                setSurname(scanner.nextLine());
                return true;
            }
            case "birth" -> {
                System.out.print("Enter birth: ");
                setBirthDate(scanner.nextLine());
                return true;
            }
            case "gender" -> {
                System.out.print("Enter gender: ");
                setGender(scanner.nextLine());
                return true;
            }
            case "number" -> {
                System.out.print("Enter number: ");
                setPhoneNumber(scanner.nextLine());
                return true;
            }
            default -> {
                System.out.println("Wrong field!");
                return false;
            }
        }
    }
}
