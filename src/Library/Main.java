package Library;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        boolean running = true;
        while (running) {
            System.out.println("\n===== Library Lending System =====");
            System.out.println("1. Add Item");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Item");
            System.out.println("4. Return Item");
            System.out.println("5. List Catalog");
            System.out.println("6. Report");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;   // skip back to the top of the loop
            }

            switch (choice) {
                case 1:
                    addItem(scanner, library);
                    break;
                case 2:
                    addMember(scanner, library);
                    break;
                case 3:
                    borrowItem(scanner, library);
                    break;
                case 4:
                    returnItem(scanner, library);
                    break;
                case 5:
                    library.listCatalog();
                    break;
                case 6:
                    library.printReport();
                    break;
                case 7:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please pick 1-7.");
            }
        }

        scanner.close();
    }

    private static void addItem(Scanner scanner, Library library) {
        System.out.println("Kind? (1=Book, 2=Magazine, 3=DVD)");
        String kind = scanner.nextLine().trim();

        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            switch (kind) {
                case "1":
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Pages: ");
                    int pages = Integer.parseInt(scanner.nextLine().trim());
                    library.addItem(new Book(title, author, pages));
                    System.out.println("Book added.");
                    break;
                case "2":
                    System.out.print("Issue number: ");
                    int issueNumber = Integer.parseInt(scanner.nextLine().trim());
                    library.addItem(new Magazine(title, issueNumber));
                    System.out.println("Magazine added.");
                    break;
                case "3":
                    System.out.print("Runtime (minutes): ");
                    int runtime = Integer.parseInt(scanner.nextLine().trim());
                    library.addItem(new DVD(title, runtime));
                    System.out.println("DVD added.");
                    break;
                default:
                    System.out.println("Unknown kind — item not added.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered — item not added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add item: " + e.getMessage());
        }
    }

    private static void addMember(Scanner scanner, Library library) {
        try {
            System.out.print("Member id: ");
            String id = scanner.nextLine().trim();
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Max items allowed: ");
            int maxAllowed = Integer.parseInt(scanner.nextLine().trim());

            library.addMember(new Member(id, name, maxAllowed));
            System.out.println("Member added.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid number entered — member not added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add member: " + e.getMessage());
        }
    }

    private static void borrowItem(Scanner scanner, Library library) {
        System.out.print("Member id: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("Item id: ");
        String itemId = scanner.nextLine().trim();

        try {
            library.borrowItem(memberId, itemId);
            System.out.println("Borrowed " + itemId + " to " + memberId + ".");
        } catch (LibraryException e) {
            System.out.println("Could not borrow: " + e.getMessage());
        }
    }

    private static void returnItem(Scanner scanner, Library library) {
        System.out.print("Member id: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("Item id: ");
        String itemId = scanner.nextLine().trim();

        try {
            library.returnItem(memberId, itemId);
            System.out.println("Returned " + itemId + " from " + memberId + ".");
        } catch (LibraryException e) {
            System.out.println("Could not return: " + e.getMessage());
        }
    }
}
