package com.workintech;

import com.workintech.book.Book;
import com.workintech.book.category.Category;
import com.workintech.manager.*;
import com.workintech.reader.Reader;
import com.workintech.ui.ConsoleUI;
import com.workintech.user.Admin;
import com.workintech.user.Librarian;
import com.workintech.user.User;
import com.workintech.util.IdGenerator;

import java.util.*;

import static java.lang.System.out;

public class Main {
    static ConsoleUI ui = new ConsoleUI();
    static String keyboard;
    static String sessionName;
    static String noBook = "Böyle bir kitap yok";
    static String adminStr = "admin";
    static Book kitap = new Book(1L, "al", ui.getKidsBook(), 10d, "yaz");


    public static void main(String[] args) {
        CategoryManager.getInstance().getCategories().put(1L, ui.getKidsBook());
        CategoryManager.getInstance().getCategories().put(2L, ui.getNovel());
        CategoryManager.getInstance().getCategories().put(3L, ui.getTextBook());
        CategoryManager.getInstance().addBookToCategory(kitap, kitap.getCategory());
        BookManager.getInstance().addBook(kitap);

        Admin admin = new Admin(1L, "ali", "123");

        UserManager.getInstance().getUsers().put(admin.getId(), admin);
        String session = "";

        String auth;
        while (true) {
            System.out.println("kullanıcı Adı girin:");
            String username = ui.getScanner().nextLine();
            sessionName = username;

            System.out.println("Şifre girin");
            String password = ui.getScanner().nextLine();

            auth = AuthenticationManager.getInstance().authenticate(username, password);

            System.out.println(auth);

            for (Map.Entry<Long, User> entry : UserManager.getInstance().getUsers().entrySet()) {
                if (entry.getValue().getUserName().equals(username)) {
                    session = (entry.getValue() instanceof Admin) ? ("Admin") : ("Librarian");
                }
            }

            if (auth.equals("user logged in")) {
                break;
            }
        }

        System.out.println(session);

        if (session.equalsIgnoreCase(adminStr)) {
            ui.helpMenuWithAdmin();
        }

        ui.helpMenu();
        while (!(keyboard = ui.getScanner().nextLine()).equals("exit")) {

            switch (keyboard) {
                case "admin" -> admin(session);
                case "addBook" -> addBook();
                case "findBookAd" -> findBookAd();
                case "findBookI" -> findBookI();
                case "findBookY" -> findBookY();
                case "updateBook" -> updatebook();
                case "deleteBook"-> deleteBook();
                case "listCat" -> listCat();
                case "listByAuth" -> listByAuth();
                case "lendBook" -> lendBook();
                case "returnBook" -> returnBook();
                case "help" -> {
                    if (session.equalsIgnoreCase(adminStr)) {
                        ui.helpMenuWithAdmin();
                    }
                    ui.helpMenu();
                }
                default -> out.println("Bilinmeyen komut. Komutları görmek için \"help\" yazın");

            }

        }
    }

    private static void admin(String session) {
        if (session.equals("Admin")) {
            System.out.println("Admin metodlarına girmek istiyor musunuz? evet is (devam) yazın.");
            if (ui.getScanner().nextLine().trim().equals("devam")) {
                ui.adminHelpMenu();
                while (!(keyboard = ui.getScanner().nextLine()).equals("exit")) {
                    switch (keyboard) {
                        case "createAdmin" -> createAdmin();
                        case "deleteAdmin" -> deleteAdmin();
                        case "createLib" -> createLibrarian();
                        case "deleteLib" -> deleteLibrarian();
                        case "list" -> UserManager.getInstance().listUsers();
                        case "help" -> ui.adminHelpMenu();
                        default -> out.println("Bilinmeyen komut. help yazıp yardımcı menüyü açın");
                    }
                }
            }

        }
    }

    public static void createAdmin() {
        System.out.println("Kullanıcı adı giriniz.");
        String username = ui.getScanner().nextLine().trim();

        System.out.println("Şifre giriniz");
        String password = ui.getScanner().nextLine().trim();

        Admin admin = new Admin(IdGenerator.generateId(UserManager.getInstance().getUsers()), username, password);
        out.println(admin);
        out.println(UserManager.getInstance().createAdmin(admin));
    }

    public static void deleteAdmin() {
        System.out.println("Silmek istediğiniz admin kullanıcı adı girin: ");
        String adminName = ui.getScanner().nextLine().trim();
        if (sessionName.equals(adminName)) {
            out.println("Kendinizi silemezsiniz :D");
            return;
        }
        for (Map.Entry<Long, User> entry : UserManager.getInstance().getUsers().entrySet()) {
            if (entry.getValue() instanceof Admin && entry.getValue().getUserName().equals(adminName)) {
                    UserManager.getInstance().getUsers().remove(entry.getValue().getId());
                    System.out.println(adminName + " Kullanıcı adlı admin silindi.");
                    return;
                }

        }
        System.out.println("Map'te Admin kullanıcı yok");
        System.out.println(adminName + " kullanıcı adlı kullanıcı bulunamadı!");
    }

    public static void createLibrarian() {
        System.out.println("Kütüphaneci kullanıcı adı girin: ");
        String librarianName = ui.getScanner().nextLine().trim();

        System.out.println("Kütüphaneci şifre girin: ");
        String librarianPass = ui.getScanner().nextLine().trim();

        Librarian librarian = new Librarian(IdGenerator.generateId(UserManager.getInstance().getUsers()), librarianName, librarianPass);
        out.println(librarian);
        out.println(UserManager.getInstance().createLibrarian(librarian));
    }

    //kütüphaneci silme
    public static void deleteLibrarian() {

        System.out.println("Silmek istediğiniz kütüphaneci kullanıcı adı girin: ");
        String librarianName = ui.getScanner().nextLine().trim();

        for (Map.Entry<Long, User> entry : UserManager.getInstance().getUsers().entrySet()) {
            if (entry.getValue() instanceof Librarian && entry.getValue().getUserName().equals(librarianName)) {
                    UserManager.getInstance().getUsers().remove(entry.getValue().getId());
                    System.out.println(librarianName + " Kullanıcı adlı kütüphaneci silindi.");
                    return;
                }

        }
        System.out.println(librarianName + " kullanıcı adlı kullanıcı bulunamadı!");
        System.out.println("Map'te kütüphaneci kullanıcı yok");
    }

    //kitap oluşturma ve kütüphaneye ekleme
    public static void addBook() {
        System.out.println("kitap adı girin: ");
        String bookName = ui.getScanner().nextLine().trim();

        System.out.println("kitap kategorisi girin: ");
        Category bookCat = ui.selectCategory();

        System.out.println("kitap fiyatı girin(sadece sayı): ");
        Double bookPrc = ui.getScanner().nextDouble();

        ui.getScanner().nextLine();

        System.out.println("kitap yazar adı girin: ");
        String bookAut = ui.getScanner().nextLine().trim();

        Book book = new Book(IdGenerator.generateId(BookManager.getInstance().getBooks()), bookName, bookCat, bookPrc, bookAut);

        //kütüphaneye ekleme
        out.println(BookManager.getInstance().addBook(book));

        //kategoriye ekleme
        CategoryManager.getInstance().addBookToCategory(book, book.getCategory());
    }

    public static Book findBookAd() {
        System.out.println("kitap adı girin: ");
        String bookName = ui.getScanner().nextLine().trim();
        Book book = BookManager.getInstance().findBook(bookName);
        if (book != null) {
            out.println(book);
            return book;
        } else {
            out.println(noBook);
            return null;
        }
    }

    public static void findBookI() {
        System.out.println("kitap id'si girin: ");
        Long bookId = ui.getScanner().nextLong();
        ui.getScanner().nextLine();

        Book book = BookManager.getInstance().findBook(bookId);
        if (book != null) {
            out.println(book);
        } else {
            out.println(noBook);
        }
    }

    public static void findBookY() {
        System.out.println("Yazar adı girin: ");
        String authName = ui.getScanner().nextLine().trim();

        Book book = BookManager.getInstance().findBookByAuthor(authName);
        if (book != null) {
            out.println(book);
        } else {
            out.println(noBook);
        }
    }

    public static void updatebook() {
        ui.updateMenu();
        Book book  = findBookAd();
        while (!(keyboard = ui.getScanner().nextLine().trim()).equals("exit")) {

            switch (keyboard) {
                case "ad" -> {
                    out.println("Yeni isim girin: ");
                    book.setName(ui.getScanner().nextLine());
                    out.println("isim başarıyla değiştirildi");
                }
                case "kategori" -> {
                    Category kat = ui.selectCategory();
                    changeCategory(book, kat);
                    out.println("kategori başarıyla değiştirildi");
                }
                case "fiyat" -> {
                    System.out.println("kitap fiyatı girin(sadece sayı): ");
                    Double bookPrc = ui.getScanner().nextDouble();
                    ui.getScanner().nextLine();
                    book.setPrice(bookPrc);
                    out.println("fiyat başarıyla değiştirildi");
                }
                case "yazar" -> {
                    System.out.println("kitap yazar adı girin: ");
                    String bookAut = ui.getScanner().nextLine().trim();
                    book.setAuthor(bookAut);
                    out.println("yazar adı başarıyla değiştirildi");
                }
                case "menu" -> ui.updateMenu();
                default -> out.println("Bilinmeyen komut. menu yazıp komutları listeleyin");
            }
        }
        out.println("update menüsünden çıktınız");
    }

    public static void changeCategory(Book book, Category kat) {
        out.println(CategoryManager.getInstance().removeBookFromCategory(book, book.getCategory()));
        book.setCategory(kat);
        out.println(CategoryManager.getInstance().addBookToCategory(book, kat));
    }

    public static void deleteBook(){
        Book book = findBookAd();
        out.println(BookManager.getInstance().deleteBook(book));
    }

    public static void listCat(){
       Category cat = ui.selectCategory();

        ui.siralamaMenu();

        while(true){
            keyboard = ui.getScanner().nextLine();
            switch (keyboard){
                case "düz" -> {
                    out.println(CategoryManager.getInstance().listBooksInCategoryAsc(cat));
                    return;
                }
                case "ters" -> {
                    out.println(CategoryManager.getInstance().listBooksInCategoryDesc(cat));
                    return;
                }
                default -> {
                    out.println("bilinmeyen komut");
                    ui.siralamaMenu();
                }
            }
        }

    }

    public static  void  listByAuth(){
        out.println("yazar adı girin: ");
        keyboard = ui.getScanner().nextLine();

        out.println(BookManager.getInstance().listByAuthor(keyboard));
    }

    public static void lendBook(){
        out.println("okuyucu adı girin");
        String readerName = ui.getScanner().nextLine();

        Reader reader = ReaderManager.getInstance().findReader(readerName);
        if(reader==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        Book book = findBookAd();
        if(book==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        out.println(LendingManager.getInstance().lendBook(book,reader));
    }

    public static  void returnBook(){
        out.println("okuyucu adı girin");
        String readerName = ui.getScanner().nextLine();

        Reader reader = ReaderManager.getInstance().findReader(readerName);
        if(reader==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        Book book = findBookAd();
        if(book==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        out.println(LendingManager.getInstance().returnBook(book,reader));
    }
}