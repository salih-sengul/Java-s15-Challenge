import com.workintech.library.Library;
import com.workintech.user.Admin;
import com.workintech.user.Athentication;
import com.workintech.user.Librarian;
import com.workintech.user.User;

import java.util.*;

import static java.lang.System.out;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static String sessionName;


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String keyboard;
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

        /*while(!(keyboard = scan.nextLine().toLowerCase(Locale.ROOT)).equals("exit")){

            switch (keyboard){
                case
            }

        }*/
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

}