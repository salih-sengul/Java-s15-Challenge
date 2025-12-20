package com.workintech.ui;

import com.workintech.book.category.Category;
import com.workintech.book.category.KidsBook;
import com.workintech.book.category.Novel;
import com.workintech.book.category.Textbook;

import java.util.Scanner;

import static java.lang.System.out;

public class ConsoleUI {

    private Scanner scan;
    private Category kidsBook;
    private Category novel;
    private Category textBook;

    public ConsoleUI() {
        this.scan = new Scanner(System.in);
        this.kidsBook = new KidsBook(1L, "Kids Book", "2-3");
        this.novel = new Novel(2L, "Novel", "Horror");
        this.textBook = new Textbook(3L, "Text Book", "Math");
    }

    public Scanner getScanner() {
        return scan;
    }

    public Category getKidsBook() {
        return kidsBook;
    }

    public Category getNovel() {
        return novel;
    }

    public Category getTextBook() {
        return textBook;
    }

    public void helpMenu() {
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

    public void adminHelpMenu() {
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

    public void helpMenuWithAdmin() {
        String help = "- admin : Admin komutlarına gir";
        out.println(help);
    }

    public void catMenu() {
        String help = """
                - KidsBook : çocuk kitabı kategorisine seç.
                - Novel: Roman kategorisini seç.
                - TextBook : Çalışma kitabı kategorisini seç.
                - list : kategori listesini açın.
                """;
        out.println(help);
    }

    public void updateMenu() {
        String menu = """
                - ad : kitap ismini değiştirir.
                - kategori: kitap kategorisini değiştirir.
                - fiyat : kitap fiyatını değiştirir.
                - yazar : kitap yazar ismini değiştirir.
                - exit : update menüsünden çıkın.
                """;
        out.println(menu);
    }

    public void siralamaMenu() {
        String menu = """
                - düz : alfabetik sırala.
                - ters: alfabeye göre tesr sırala.
                """;
        out.println(menu);
    }

    public Category selectCategory() {
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

    public String readLine(String prompt) {
        out.println(prompt);
        return scan.nextLine().trim();
    }

    public Long readLong(String prompt) {
        out.println(prompt);
        Long value = scan.nextLong();
        scan.nextLine();
        return value;
    }

    public Double readDouble(String prompt) {
        out.println(prompt);
        Double value = scan.nextDouble();
        scan.nextLine();
        return value;
    }
}
