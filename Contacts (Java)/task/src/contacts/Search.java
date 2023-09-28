package contacts;

import contacts.records.Record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {
    List<Record> records;
    private static final Scanner scanner = new Scanner(System.in);

    public Search(List<Record> records) {
        this.records = records;
    }
    public void search() {
        while (true) {
            List<Record> temp = find();
            System.out.println("Found " + temp.size() + " results:");
            if (temp.isEmpty()) {
                continue;
            }
            printShortInfo(temp);
            System.out.print("\n[search] Enter action ([number], back, again): ");
            String option = scanner.nextLine();
            if ("back".equals(option)) break;
            else if ("again".equals(option)) {
                continue;
            } else if (option.matches("[0-9]+")) {
                Record r = temp.get(Integer.parseInt(option) - 1);
                r.showInfo();
                if (edit(r)) break;
            } else System.out.println("Wrong action");
        }
    }
    
    private List<Record> find() {
        List<Record> temp = new ArrayList<>();
        System.out.print("Enter search query: ");
        Pattern pattern = Pattern.compile(".*" +
                scanner.nextLine() + ".*", Pattern.CASE_INSENSITIVE);
        for (Record r : records) {
            Matcher matcher = pattern.matcher(r.shortInfo());
            Matcher matcher1 = pattern.matcher(r.getPhoneNumber());
            if (matcher.find() || matcher1.find()) {
                temp.add(r);
            }
        }
        return temp;
    }
    
    private void printShortInfo(List<Record> temp) {
        int i = 1;
        for (Record r : temp) {
            System.out.println(i + ". " + r.shortInfo());
            i++;
        }
    }

    public boolean edit(Record record) {
        while (true) {
            System.out.print("\n[record] Enter action (edit, delete, menu): ");
            switch (scanner.nextLine()) {
                case "menu" -> {
                    return true;
                }
                case "delete" -> {
                    records.remove(record);
                    try {
                        SerializationUtils.serialize(records, SerializationUtils.FILE_NAME);
                    } catch (IOException e) {
                        System.out.println("serialization error");
                    }
                }
                case "edit" -> editRecord(record);
            }
        }
    }

    private void editRecord(Record record) {
        System.out.print("Select a field " + record.getFields() + " ");
        if (record.change(scanner.nextLine())) {
            record.setTimeEdit();
            try {
                SerializationUtils.serialize(records, SerializationUtils.FILE_NAME);
            } catch (IOException e) {
                System.out.println("Hoo lee fak");
            }
        }
        System.out.println("Saved");
        record.showInfo();
    }
}
