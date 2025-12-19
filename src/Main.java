import com.workintech.book.Book;
import com.workintech.book.category.Category;
import com.workintech.book.category.KidsBook;
import com.workintech.book.category.Novel;
import com.workintech.book.category.Textbook;
import com.workintech.library.Library;
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
    static Category kidsBook = new KidsBook(1L,"Kids Book","2-3");
    static Category novel = new Novel(2L,"Novel","Horror");
    static Category textBook = new Textbook(3L,"Text Book","Math");



    public static void main(String[] args) {
        Library.getCategories().put(1L,kidsBook);
        Library.getCategories().put(2L,novel);
        Library.getCategories().put(3L,textBook);


        Admin admin = new Admin(1L,"ali","123");

        Library.getUsers().put(admin.getId(),admin);
        String session="";

        String auth;
        while(true){
            System.out.println("kullanıcı Adı girin:");
            String username = scan.nextLine();
            sessionName =username;

            System.out.println("Şifre girin");
            String password = scan.nextLine();

            Athentication athentication = new Athentication(username,password);
            auth = athentication.checkNamePassword(username,password);

            System.out.println(auth);

            for(Map.Entry<Long,User> entry: Library.getUsers().entrySet()){
                if(entry.getValue().getUserName().equals(username)){
                    session =(entry.getValue() instanceof Admin)?("Admin"):( "Librarian");
                }
            }

            if(auth.equals("user logged in")){
                break;
            }
        }

        System.out.println(session);

        if(session.equalsIgnoreCase("admin")){
            helpMenuWithAdmin();
        }
        helpMenu();

        /*
                    addBook : Sisteme kitap ekle.
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
                - exit : Admin komutlarından çık.
        * */
        while(!(keyboard = scan.nextLine()).equals("exit")){

            switch (keyboard){
                case "admin" -> admin(session);
                case "addBook" -> addBook();
                case "findBookAd" -> findBookAd();
                case "findBookI"-> findBookI();
                case "findBookY" -> findBookY();
                case "updateBook" -> updatebook();
                case "help" -> {
                    if(session.equalsIgnoreCase("admin")){
                        helpMenuWithAdmin();
                    }
                    helpMenu();
                }
                default -> out.println("Bilinmeyen komut. Komutları görmek için \"help\" yazın");

            }

        }
    }

    private static void admin(String session) {
        if(session.equals("Admin")){
            System.out.println("Admin metodlarına girmek istiyor musunuz? evet is (devam) yazın.");
            if(scan.nextLine().trim().equals("devam")){
                adminHelpMenu();
                while (!(keyboard = scan.nextLine()).equals("exit")){
                    switch(keyboard){
                        case "createAdmin"-> createAdmin();
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

    public static void adminHelpMenu(){
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

    public static void helpMenuWithAdmin(){
        String help = "- admin : Admin komutlarına gir";
        out.println(help);
    }

    public static void helpMenu(){
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
                - exit : Admin komutlarından çık.
                """;
        out.println(help);
    }

    public static void createAdmin(){
        System.out.println("Kullanıcı adı giriniz.");
        String username = scan.nextLine().trim();

        System.out.println("Şifre giriniz");
        String password = scan.nextLine().trim();

        Admin admin = new Admin(Library.getId(Library.getUsers()),username,password);
        out.println(admin);
        out.println(admin.createAdmin(admin));
    }

    public static void deleteAdmin(){
        System.out.println("Silmek istediğiniz admin kullanıcı adı girin: ");
        String adminName = scan.nextLine().trim();
        if(sessionName.equals(adminName)){
            out.println("Kendinizi silemezsiniz :D");
            return;
        }
        for(Map.Entry<Long,User> entry: Library.getUsers().entrySet()){
            if(entry.getValue() instanceof Admin ){
                if(entry.getValue().getUserName().equals(adminName)){
                    Library.getUsers().remove(entry.getValue().getId());
                    System.out.println(adminName+" Kullanıcı adlı admin silindi.");
                    return;
                }
            }
        }
        System.out.println("Map'te Admin kullanıcı yok");
        System.out.println(adminName+" kullanıcı adlı kullanıcı bulunamadı!");
    }

    public static  void createLibrarian(){
        System.out.println("Kütüphaneci kullanıcı adı girin: ");
        String librarianName = scan.nextLine().trim();

        System.out.println("Kütüphaneci şifre girin: ");
        String librarianPass = scan.nextLine().trim();

        Librarian librarian = new Librarian(Library.getId(Library.getUsers()),librarianName,librarianPass);
        out.println(librarian);
        Library.getUsers().put(librarian.getId(),librarian);
    }

    //kütüphaneci silme
    public  static void deleteLibrarian(){

        System.out.println("Silmek istediğiniz kütüphaneci kullanıcı adı girin: ");
        String librarianName = scan.nextLine().trim();

        for(Map.Entry<Long,User> entry: Library.getUsers().entrySet()){
            if(entry.getValue() instanceof Librarian ){
                if(entry.getValue().getUserName().equals(librarianName)){
                    Library.getUsers().remove(entry.getValue().getId());
                    System.out.println(librarianName+" Kullanıcı adlı kütüphaneci silindi.");
                    return;
                }
            }
        }
        System.out.println(librarianName+" kullanıcı adlı kullanıcı bulunamadı!");
        System.out.println("Map'te kütüphaneci kullanıcı yok");
    }

    //kitap oluşturma ve kütüphaneye ekleme
    public static void addBook(){
        System.out.println("kitap adı girin: ");
        String bookName = scan.nextLine().trim();

        System.out.println("kitap kategorisi girin: ");
        String bookCat = selectCats();

        System.out.println("kitap fiyatı girin(sadece sayı): ");
        Double bookPrc = scan.nextDouble();

        scan.nextLine();

        System.out.println("kitap yazar adı girin: ");
        String bookAut = scan.nextLine().trim();

        Book book  = new Book(Library.getId(Library.getBooks()),bookName,bookCat,bookPrc,bookAut);

        //kütüphaneye ekleme
        out.println(Library.addBook(book));

        //kategoriye ekleme
        if(bookCat.equals("KidsBook")){
            out.println(kidsBook.addBook(book));
        } else if (bookCat.equals("Novel")) {
            out.println(novel.addBook(book));
        }else {
            out.println(textBook.addBook(book));
        }
    }

    //hazır olan kategorileri kullanıcıdan alma
    public static String selectCats(){
        String keyboard;
        catMenu();
        while(true){
            keyboard = scan.nextLine();
            switch (keyboard){
                case "KidsBook" -> {
                    return "KidsBook";
                }
                case "Novel" -> {
                    return "Novel";
                }
                case "TextBook" ->{
                    return "Textbook";
                }
                case "list" -> catMenu();
                default -> out.println("Bilinmeyen komut. list yazıp kategori listesini açın");

            }

        }
    }

    //kategori listesi yazdırma
    public static void catMenu(){
        String help = """
                - KidsBook : çocuk kitabı kategorisine seç.
                - Novel: Roman kategorisini seç.
                - TextBook : Çalışma kitabı kategorisini seç.
                - list : kategori listesini açın.
                """;
        out.println(help);
    }

    public static void findBookAd(){
        System.out.println("kitap adı girin: ");
        String bookName = scan.nextLine().trim();


        if(Library.findBook(bookName)!=null){
            out.println(Library.findBook(bookName).getName());
        }else {
            out.println("Böyle bir kitap yok");
        }
    }

    public static void findBookI(){
        System.out.println("kitap id'si girin: ");
        Long bookId = scan.nextLong();
        scan.nextLine();

        if(Library.findBook(bookId)!=null){
            out.println(Library.findBook(bookId).getName());
        }else {
            out.println("Böyle bir kitap yok");
        }
    }

    public static void findBookY(){
        System.out.println("Yazar adı girin: ");
        String authName = scan.nextLine().trim();

        if(Library.findBookAuthor(authName)!=null){
            out.println(Library.findBookAuthor(authName).getName());
        }else {
            out.println("Böyle bir kitap yok");
        }
    }

    public static void updatebook() {
        System.out.println("kitap adı girin: ");
        String bookName = scan.nextLine().trim();

        Book book;
        if (Library.findBook(bookName) != null) {
            book = Library.findBook(bookName);
        } else {
            out.println("Böyle bir kitap yok");
            return;
        }

        bookAlanSec(book);

        System.out.println("kitap adı girin: ");
        String bookName = scan.nextLine().trim();

        System.out.println("kitap kategorisi girin: ");
        String bookCat = selectCats();

        System.out.println("kitap fiyatı girin(sadece sayı): ");
        Double bookPrc = scan.nextDouble();

        scan.nextLine();

        System.out.println("kitap yazar adı girin: ");
        String bookAut = scan.nextLine().trim();

    }

    public static void bookAlanSec(Book book){
        keyboard = scan.nextLine().trim();

        while (!Objects.equals(keyboard, "exit")) {
            switch (keyboard){
                case "ad" -> {
                    out.println("Yeni isim girin: ");
                    book.setName(scan.nextLine());
                }
                case "kategori" -> {
                   String kat = selectCats();
                   changeCategory(book, kat);
                }
                case "fiyat" ->{
                    System.out.println("kitap fiyatı girin(sadece sayı): ");
                    Double bookPrc = scan.nextDouble();
                    scan.nextLine();
                    book.setPrice(bookPrc);
                }
                case "yazay" ->{
                    System.out.println("kitap yazar adı girin: ");
                    String bookAut = scan.nextLine().trim();
                    book.setAuthor(bookAut);
                }
                default -> out.println("Bilinmeyen komut. help yazıp komutları listeleyin");
            }
        }
    }



    public static void changeCategory(Book book, String kat){

        if(book.getCategory().equals("KidsBook")){
            out.println(kidsBook.deleteBook(book));
        } else if (book.getCategory().equals("Novel")) {
            out.println(novel.deleteBook(book));
        }else {
            out.println(textBook.deleteBook(book));
        }

        if(kat.equals("KidsBook")){
            out.println(kidsBook.addBook(book));
        } else if (kat.equals("Novel")) {
            out.println(novel.addBook(book));
        }else {
            out.println(textBook.addBook(book));
        }


    }

}