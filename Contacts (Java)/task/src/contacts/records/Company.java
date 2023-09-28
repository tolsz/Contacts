package contacts.records;

import java.util.Scanner;

public class Company extends Record {
    private String address;

    public Company() {
        super(false);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void showInfo() {
        System.out.printf("""
                Organization name: %s
                Address: %s
                Number: %s
                Time created: %s
                Time last edit: %s\n""", getName(), getAddress(), getPhoneNumber(),
                getCreated(), getTimeEdit());
    }

    @Override
    public String shortInfo() {
        return getName();
    }

    @Override
    public String getFields() {
        return "(name, address, number):";
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
            case "address" -> {
                System.out.print("Enter address: ");
                setAddress(scanner.nextLine());
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
