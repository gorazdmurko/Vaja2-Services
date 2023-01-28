package si.academia.unit29.vaja2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class App {

    private static final Logger LOG = Logger.getLogger(String.valueOf(App.class));

    public static void main(String[] args) throws IOException {

        test();

        testPersistence();
    }

    private static void test() {
        AuthorService service = new AuthorService();
        Author author1 = new Author("Dirk Amelburger", 67, true, null);
        service.createAuthor(author1);
        Author author2 = new Author("Steve Oualline", 46, true, null);
        service.createAuthor(author2);
        Author author11 = service.getAuthor("Dirk Amelburger");
        Author author22 = service.getAuthor("Steve Oualline");

        List<Author> authorsList = service.getAuthors();
        LOG.info("Author list: " + authorsList);

        service.deleteAuthor(author2);
        authorsList = service.getAuthors();
        LOG.info("Author list: " + authorsList);
    }

    private static void testPersistence() throws IOException {
        AuthorService service = new AuthorService();
        List<Book> bookList_1 = new ArrayList<Book>();
        bookList_1.add(new Book("Born to run", "McDougall", new Date(), 319));
        Author newAuthor1 = new Author("Cristopher McDougall", 67, false, bookList_1);
        service.createAuthor(newAuthor1);

        Book book = new Book("Eat & Run", "Jurek", new Date(), 217);
        List<Book> bookList_2 = new ArrayList<Book>();
        bookList_2.add(book);
        Author newAuthor2 = new Author("Scott Jurek", 52, true, bookList_2);
        bookList_2.add(newAuthor2.getBooks().get(0));   // getBooks returns a List
        service.createAuthor(newAuthor2);


        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path + "authors.json";

        // SAVE
        service.save(path);
        // LOAD
        AuthorService service2 = new AuthorService();
        service2.load(path);

        Author author1b = service2.getAuthor("Cristopher McDougall");
        Author author2b = service2.getAuthor("Scott Jurek");
        List<Author> authors = service2.getAuthors();

        LOG.info("List of authors: " + authors);
    }
}
