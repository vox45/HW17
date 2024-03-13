import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Book {
    private String title;
    private String author;
    private int year;
    private String isbn;

    public Book(String title, String author, int year, String isbn) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getIsbn() {
        return isbn;
    }
}

class Library {
    private Map<String, Book> bookMap;

    public Library() {
        this.bookMap = new HashMap<>();
    }

    public void addBook(String title, String author, int year, String isbn)
            throws DuplicateBookException, InvalidYearException {
        if (bookMap.containsKey(isbn)) {
            throw new DuplicateBookException("Book with ISBN " + isbn + " already exists.");
        }

        if (year < 0 || year > java.time.Year.now().getValue()) {
            throw new InvalidYearException("Invalid year of publication: " + year);
        }

        Book newBook = new Book(title, author, year, isbn);
        bookMap.put(isbn, newBook);
    }

    public void removeBook(String isbn) throws BookNotFoundException {
        if (!bookMap.containsKey(isbn)) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }

        bookMap.remove(isbn);
    }

    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        Book book = bookMap.get(isbn);
        if (book == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found.");
        }

        return book;
    }

    public List<Book> findBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookMap.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findBooksByYear(int year) {
        List<Book> result = new ArrayList<>();
        for (Book book : bookMap.values()) {
            if (book.getYear() == year) {
                result.add(book);
            }
        }
        return result;
    }
}

class DuplicateBookException extends Exception {
    public DuplicateBookException(String message) {
        super(message);
    }
}

class InvalidYearException extends Exception {
    public InvalidYearException(String message) {
        super(message);
    }
}

class BookNotFoundException extends Exception {
    public BookNotFoundException(String message) {
        super(message);
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        try {
            library.addBook("NewBook", "Author1", 2022, "ISBN4");
            library.addBook("Title1", "Author1", 2000, "ISBN1");  // DuplicateBookException
            library.addBook("AnotherBook", "Author2", -5, "ISBN5"); // InvalidYearException
        } catch (DuplicateBookException | InvalidYearException e) {
            e.printStackTrace();
        }

        try {
            library.removeBook("ISBN2");  // BookNotFoundException
            Book foundBook = library.findBookByIsbn("ISBN3");
            System.out.println("Found book: " + foundBook.getTitle());
        } catch (BookNotFoundException e) {
            e.printStackTrace();
        }

        List<Book> booksByAuthor = library.findBooksByAuthor("Author1");
        System.out.println("Books by Author1: " + booksByAuthor);

        List<Book> booksByYear = library.findBooksByYear(2022);
        System.out.println("Books published in 2022: " + booksByYear);
    }
}
