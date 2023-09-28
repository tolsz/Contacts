package contacts;

import contacts.records.*;
import contacts.records.Record;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Run {
    private static final Scanner scanner = new Scanner(System.in);
    List<Record> records = new ArrayList<>();
    public void run(String[] args) {
        containsFile(args);
        loadList();

        boolean flag = true;
        while (flag) {
            System.out.print("[menu] Enter action (add, list, search, count, exit): ");
            switch (scanner.nextLine()) {
                case "add" -> {
                    Record record = add();
                    if (record != null) {
                        records.add(record);
                        try {
                            SerializationUtils.serialize(records, SerializationUtils.FILE_NAME);
                        } catch (IOException e) {
                            System.out.println("Serialization error");
                        }
                    }
                    System.out.println("The record added.");
                }
                case "search" -> {
                    Search search = new Search(records);
                    search.search();
                }
                case "list" -> list();
                case "count" -> System.out.println("The Phone Book has " +
                        records.size() + " records.");
                case "exit" -> flag = false;
            }
            if (flag) {
                System.out.println();
            }
        }
    }

    private Record add() {
        System.out.print("Enter the type (person, organization): ");
        switch (scanner.nextLine()) {
            case "person" -> {
                return addPerson();
            }
            case "organization" -> {
                return addCompany();
            }
            default -> {
                System.out.println("Wrong type!");
                return null;
            }
        }
    }

    private void list() {
        int i = 1;
        for (Record r: records) {
            System.out.println(i + ". " + r.shortInfo());
            i++;
        }
        System.out.print("\n[list] Enter action ([number], back): ");
        try {
            Record record = records.get(Integer.parseInt(scanner.nextLine()) - 1);
            record.showInfo();
            Search search = new Search(records);
            search.edit(record);
        } catch (Exception e) {
            System.out.println("List is empty");
        }
    }

    private Person addPerson() {
        Person person = new Person();
        System.out.print("Enter the name: ");
        person.setName(scanner.nextLine());
        System.out.print("Enter the surname: ");
        person.setSurname(scanner.nextLine());
        System.out.print("Enter the birth date: ");
        person.setBirthDate(scanner.nextLine());
        System.out.print("Enter the gender (M, F): ");
        person.setGender(scanner.nextLine());
        System.out.print("Enter the number: ");
        person.setPhoneNumber(scanner.nextLine());
        return person;
    }

    private Company addCompany() {
        Company company = new Company();
        System.out.print("Enter the organization name: ");
        company.setName(scanner.nextLine());
        System.out.print("Enter the address: ");
        company.setAddress(scanner.nextLine());
        System.out.print("Enter the number: ");
        company.setPhoneNumber(scanner.nextLine());
        return company;
    }

    @SuppressWarnings("all")
    private void loadList() {
        try {
            records = (List<Record>) SerializationUtils.deserialize(SerializationUtils.FILE_NAME);
        } catch (Exception e) {
            System.out.println("There is no contacts file, the file will be created");
        }
    }

    private void containsFile(String[] args) {
        if (args.length == 0) return;
        boolean flag = false;
        for (String s : args) {
            if (flag) {
                SerializationUtils.FILE_NAME = s;
            }
            if ("open".equals(s)) {
                flag = true;
            }
        }
    }
}
