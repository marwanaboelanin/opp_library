package Library;
import java.util.*;

public class Library {
    private Map<String, LibraryItem> catalog;
    private Map<String, Member> members;
    private Set<String> borrowedIds;

    public Library() {
        this.catalog = new HashMap<>();
        this.members = new HashMap<>();
        this.borrowedIds = new HashSet<>();
    }

    public void addItem(LibraryItem item) {
        catalog.put(item.getId(), item);
    }

    public void addMember(Member m) {
        members.put(m.getMemberId(), m);
    }

    public void borrowItem(String memberId, String itemId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("No member found with id " + memberId + ".");
        }

        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException("No item found with id " + itemId + ".");
        }

        if (item.isBorrowed()) {
            throw new LibraryException("Item " + itemId + " is already out.");
        }

        if (!member.canBorrowMore()) {
            throw new LibraryException("Member " + memberId + " has reached their borrowing limit.");
        }

        item.markBorrowed();
        member.addBorrowedItem(item);
        borrowedIds.add(itemId);
    }

    public void returnItem(String memberId, String itemId) throws LibraryException {
        Member member = members.get(memberId);
        if (member == null) {
            throw new LibraryException("No member found with id " + memberId + ".");
        }

        LibraryItem item = catalog.get(itemId);
        if (item == null) {
            throw new LibraryException("No item found with id " + itemId + ".");
        }

        if (!member.hasBorrowed(item)) {
            throw new LibraryException("Member " + memberId + " did not borrow item " + itemId + ".");
        }

        item.markReturned();
        member.removeBorrowedItem(item);
        borrowedIds.remove(itemId);
    }

    public void listCatalog() {
        for (LibraryItem item : catalog.values()) {
            item.displayInfo();
        }
    }

    public void printReport() {
        Map<String, Integer> countByType = new HashMap<>();
        for (LibraryItem item : catalog.values()) {
            String type = item.getType();
            countByType.put(type, countByType.getOrDefault(type, 0) + 1);
        }

        System.out.println("---------- REPORT ----------");
        System.out.println("Total items : " + catalog.size());
        System.out.println("Currently out : " + borrowedIds.size());
        System.out.println("Borrowed ids : " + borrowedIds);
        System.out.println("Items by type : " + countByType);
        System.out.println("Total created : " + LibraryItem.getTotalItemsCreated());
        System.out.println("----------------------------");
    }
}
