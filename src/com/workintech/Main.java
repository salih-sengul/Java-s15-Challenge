package com.workintech;

import com.workintech.book.Book;
import com.workintech.book.category.Category;
import com.workintech.book.category.KidsBook;
import com.workintech.book.category.Novel;
import com.workintech.book.category.Textbook;
import com.workintech.library.Library;
import com.workintech.reader.Reader;
import com.workintech.user.Admin;
import com.workintech.user.Athentication;
import com.workintech.user.Librarian;
import com.workintech.user.User;

import java.util.*;

import static java.lang.System.out;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static String keyboard;
    static String sessionName;
    static String noBook = "Böyle bir kitap yok";
    static String adminStr = "admin";
    static Category kidsBook = new KidsBook(1L, "Kids Book", "2-3");
    static Category novel = new Novel(2L, "Novel", "Horror");
    static Category textBook = new Textbook(3L, "Text Book", "Math");
    static Book kitap = new Book(1L, "al", kidsBook, 10d, "yaz");
    static Reader reader = new Reader(Library.getId(Library.getReaders()),"ayşe","05417805555");


    public static void main(String[] args) {
        Library.getCategories().put(1L, kidsBook);
        Library.getCategories().put(2L, novel);
        Library.getCategories().put(3L, textBook);
        kitap.getCategory().addBook(kitap);
        Library.addBook(kitap);
        Library.getReaders().put(reader.getId(),reader);

        Admin admin = new Admin(1L, "ali", "123");

        Library.getUsers().put(admin.getId(), admin);
        String session = "";

        String auth;
        while (true) {
            System.out.println("kullanıcı Adı girin:");
            String username = scan.nextLine();
            sessionName = username;

            System.out.println("Şifre girin");
            String password = scan.nextLine();

            Athentication athentication = new Athentication(username, password);
            auth = athentication.checkNamePassword(username, password);

            System.out.println(auth);

            for (Map.Entry<Long, User> entry : Library.getUsers().entrySet()) {
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
            helpMenuWithAdmin();
        }

        helpMenu();
        while (!(keyboard = scan.nextLine()).equals("exit")) {

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
                        helpMenuWithAdmin();
                    }
                    helpMenu();
                }
                default -> out.println("Bilinmeyen komut. Komutları görmek için \"help\" yazın");

            }

        }
    }

    public static void helpMenu() {
        String help = """
                - addBook : Sisteme kitap ekle.
                - findBookAd : İsim ile kitap ara.
                - findBookI : ID ile kitap ara.
                - findBookY : Yazar adı ile kitap ara.
                - updateBook : Kitap bilgilerini güncelle.
                - deleteBook : Sistemden kitap sil.
                - listCat : Kategorideki tüm kitapları listele.
                - listAut : Yazarın tüm kitaplarını listele.
                - lendBook : Kullanıcıya kitap ver.
                - returnBook : Kullanıcıdan kitap al.
                - help : komutları listele.
                - exit : Programdan çık.
                """;
        out.println(help);
    }

    private static void admin(String session) {
        if (session.equals("Admin")) {
            System.out.println("Admin metodlarına girmek istiyor musunuz? evet is (devam) yazın.");
            if (scan.nextLine().trim().equals("devam")) {
                adminHelpMenu();
                while (!(keyboard = scan.nextLine()).equals("exit")) {
                    switch (keyboard) {
                        case "createAdmin" -> createAdmin();
                        case "deleteAdmin" -> deleteAdmin();
                        case "createLib" -> createLibrarian();
                        case "deleteLib" -> deleteLibrarian();
                        case "list" -> Library.listUsers();
                        case "help" -> adminHelpMenu();
                        default -> out.println("Bilinmeyen komut. help yazıp yardımcı menüyü açın");
                    }
                }
            }

        }
    }

    public static void adminHelpMenu() {
        String help = """
                - createAdmin : Admin kullanıcısı ekle
                - deleteAdmin : Admin kullanıcısı sil
                - createLib : Kütüphaneci kullanıcısı ekle
                - deleteLib : Kütüphaneci kullanıcısı sil
                - list : kullanıcıları listele
                - exit : Admin komutlarından çık.
                """;
        out.println(help);

    }

    public static void helpMenuWithAdmin() {
        String help = "- admin : Admin komutlarına gir";
        out.println(help);
    }

    public static void createAdmin() {
        System.out.println("Kullanıcı adı giriniz.");
        String username = scan.nextLine().trim();

        System.out.println("Şifre giriniz");
        String password = scan.nextLine().trim();

        Admin admin = new Admin(Library.getId(Library.getUsers()), username, password);
        out.println(admin);
        out.println(admin.createAdmin(admin));
    }

    public static void deleteAdmin() {
        System.out.println("Silmek istediğiniz admin kullanıcı adı girin: ");
        String adminName = scan.nextLine().trim();
        if (sessionName.equals(adminName)) {
            out.println("Kendinizi silemezsiniz :D");
            return;
        }
        for (Map.Entry<Long, User> entry : Library.getUsers().entrySet()) {
            if (entry.getValue() instanceof Admin && entry.getValue().getUserName().equals(adminName)) {
                    Library.getUsers().remove(entry.getValue().getId());
                    System.out.println(adminName + " Kullanıcı adlı admin silindi.");
                    return;
                }

        }
        System.out.println("Map'te Admin kullanıcı yok");
        System.out.println(adminName + " kullanıcı adlı kullanıcı bulunamadı!");
    }

    public static void createLibrarian() {
        System.out.println("Kütüphaneci kullanıcı adı girin: ");
        String librarianName = scan.nextLine().trim();

        System.out.println("Kütüphaneci şifre girin: ");
        String librarianPass = scan.nextLine().trim();

        Librarian librarian = new Librarian(Library.getId(Library.getUsers()), librarianName, librarianPass);
        out.println(librarian);
        Library.getUsers().put(librarian.getId(), librarian);
    }

    //kütüphaneci silme
    public static void deleteLibrarian() {

        System.out.println("Silmek istediğiniz kütüphaneci kullanıcı adı girin: ");
        String librarianName = scan.nextLine().trim();

        for (Map.Entry<Long, User> entry : Library.getUsers().entrySet()) {
            if (entry.getValue() instanceof Librarian && entry.getValue().getUserName().equals(librarianName)) {
                    Library.getUsers().remove(entry.getValue().getId());
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
        String bookName = scan.nextLine().trim();

        System.out.println("kitap kategorisi girin: ");
        Category bookCat = selectCats();

        System.out.println("kitap fiyatı girin(sadece sayı): ");
        Double bookPrc = scan.nextDouble();

        scan.nextLine();

        System.out.println("kitap yazar adı girin: ");
        String bookAut = scan.nextLine().trim();

        Book book = new Book(Library.getId(Library.getBooks()), bookName, bookCat, bookPrc, bookAut);

        //kütüphaneye ekleme
        out.println(Library.addBook(book));

        //kategoriye ekleme
        book.getCategory().addBook(book);
    }

    //var olan kategorileri kullanıcıdan alma
    public static Category selectCats() {
        String keyboard;
        catMenu();
        while (true) {
            keyboard = scan.nextLine();
            switch (keyboard) {
                case "KidsBook" -> {
                    return kidsBook;
                }
                case "Novel" -> {
                    return novel;
                }
                case "TextBook" -> {
                    return textBook;
                }
                case "list" -> catMenu();
                default -> out.println("Bilinmeyen komut. list yazıp kategori listesini açın");

            }

        }
    }

    //kategori listesi yazdırma
    public static void catMenu() {
        String help = """
                - KidsBook : çocuk kitabı kategorisine seç.
                - Novel: Roman kategorisini seç.
                - TextBook : Çalışma kitabı kategorisini seç.
                - list : kategori listesini açın.
                """;
        out.println(help);
    }

    public static Book findBookAd() {
        System.out.println("kitap adı girin: ");
        String bookName = scan.nextLine().trim();
        Book book = Library.findBook(bookName);
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
        Long bookId = scan.nextLong();
        scan.nextLine();

        Book book = Library.findBook(bookId);
        if (book != null) {
            out.println(book);
        } else {
            out.println(noBook);
        }
    }

    public static void findBookY() {
        System.out.println("Yazar adı girin: ");
        String authName = scan.nextLine().trim();

        Book book = Library.findBookAuthor(authName);
        if (book != null) {
            out.println(book);
        } else {
            out.println(noBook);
        }
    }

    public static void updatebook() {
        updateMenu();
        Book book  = findBookAd();
        while (!(keyboard = scan.nextLine().trim()).equals("exit")) {

            switch (keyboard) {
                case "ad" -> {
                    out.println("Yeni isim girin: ");
                    book.setName(scan.nextLine());
                    out.println("isim başarıyla değiştirildi");
                }
                case "kategori" -> {
                    Category kat = selectCats();
                    changeCategory(book, kat);
                    out.println("kategori başarıyla değiştirildi");
                }
                case "fiyat" -> {
                    System.out.println("kitap fiyatı girin(sadece sayı): ");
                    Double bookPrc = scan.nextDouble();
                    scan.nextLine();
                    book.setPrice(bookPrc);
                    out.println("fiyat başarıyla değiştirildi");
                }
                case "yazar" -> {
                    System.out.println("kitap yazar adı girin: ");
                    String bookAut = scan.nextLine().trim();
                    book.setAuthor(bookAut);
                    out.println("yazar adı başarıyla değiştirildi");
                }
                case "menu" -> updateMenu();
                default -> out.println("Bilinmeyen komut. menu yazıp komutları listeleyin");
            }
        }
        out.println("update menüsünden çıktınız");
    }

    public static void updateMenu() {
        String menu = """
                - ad : kitap ismini değiştirir.
                - kategori: kitap kategorisini değiştirir.
                - fiyat : kitap fiyatını değiştirir.
                - yazar : kitap yazar ismini değiştirir.
                - exit : update menüsünden çıkın.
                """;
        out.println(menu);
    }

    public static void changeCategory(Book book, Category kat) {
        out.println(book.getCategory().deleteBook(book));
        book.setCategory(kat);
        out.println(book.getCategory().addBook(book));
    }

    public static void deleteBook(){
        Book book = findBookAd();
        out.println(Library.deleteBook(book));
    }

    public static void listCat(){
       Category cat = selectCats();

        siralamaMenu();

        while(true){
            keyboard = scan.nextLine();
            switch (keyboard){
                case "düz" -> {
                    out.println(cat.listBookAsc());
                    return;
                }
                case "ters" -> {
                    out.println(  cat.listBookDesc());
                    return;
                }
                default -> {
                    out.println("bilinmeyen komut");
                    siralamaMenu();
                }
            }
        }

    }

    public static void siralamaMenu() {
        String menu = """
                - düz : alfabetik sırala.
                - ters: alfabeye göre tesr sırala.
                """;
        out.println(menu);
    }

    public static  void  listByAuth(){
        out.println("yazar adı girin: ");
        keyboard = scan.nextLine();

        out.println(Library.listByAuthor(keyboard));
    }

    public static void lendBook(){
        out.println("okuyucu adı girin");
        String readerName = scan.nextLine();

        Reader reader = Library.findReader(readerName);
        if(reader==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        Book book = findBookAd();
        if(book==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        out.println(Library.lendBook(book,reader));
    }

    public static  void returnBook(){
        out.println("okuyucu adı girin");
        String readerName = scan.nextLine();

        Reader reader = Library.findReader(readerName);
        if(reader==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        Book book = findBookAd();
        if(book==null){
            out.println("böyle bir kullanıcı yok");
            return;
        }

        out.println(Library.returnBook(book,reader));
    }
}