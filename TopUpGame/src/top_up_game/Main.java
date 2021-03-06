package top_up_game;


import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/toko_game";
    static final String USERNAME = "root";
    static final String PASSWORD = "";
    static final Scanner myObj = new Scanner(System.in);

    static void templateAdmin1(String str) {
        System.out.printf("=== PENGELOLAAN DATA %s === \n", str.toUpperCase());
        System.out.println("1. Lihat Data " + str);
        System.out.println("2. Ubah Data " + str);
        System.out.println("3. Hapus Data " + str);
        System.out.println("0. Kembali");
    }
    static void templateAdmin2(String str) {
        System.out.printf("=== PENGELOLAAN DATA %s === \n", str.toUpperCase());
        System.out.println("1. Lihat Data " + str);
        System.out.println("2. Tambah Data " + str);
        System.out.println("3. Ubah Data " + str);
        System.out.println("4. Hapus Data " + str);
        System.out.println("0. Kembali");
    }
    static void menuAdmin() {
        System.out.println("1. Pengelolaan Data Game");
        System.out.println("2. Pengelolaan Data User");
        System.out.println("3. Pengelolaan Data Voucher");
        System.out.println("4. Lihat Data Riwayat Pembelian");
        System.out.println("0. Logout");
    }

    static void menuUser() {
        System.out.println("1. Top Up");
        System.out.println("2. Edit Akun");
        System.out.println("0. Logout");
    }
    
    
    static ResultSet query(String query) {
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // untuk connect ke dbmysql
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD);
            
            Statement statement = connection.createStatement();
            
            // mengeksekusi query
            resultSet = statement.executeQuery(query);
            
            // mengembalikan hasil query
            return resultSet;
            
        } catch(Exception e) {
            System.out.println(e);
        }
        return resultSet;
    }
    
    static int update(String query) {        
        int result = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // untuk connect ke dbmysql
            Connection connection = DriverManager.getConnection(
                    URL, USERNAME, PASSWORD);
            
            Statement statement = connection.createStatement();
            
            // mengeksekusi query
            result = statement.executeUpdate(query);
            
            // mengembalikan hasil query
            return result;
            
        } catch(Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    // Users
    // ini dipakai pas daftar akun user di menu 2
    static String addUser(
            String id,
            String username,
            String password,
            String nama,
            String alamat,
            String noTelp
        ) {
        try {
            String query = "INSERT INTO users "
                            + "(id, username, password, nama, alamat, noTelp, otorisasi) "
                            + "VALUES ("
                            + "'" + id + "', "
                            + "'" + username + "', "
                            + "'" + password + "', "
                            + "'" + nama + "', "
                            + "'" + alamat + "', "
                            + "'" + noTelp + "', "
                            + "'user'"
                            + ")";
            int result = update(query);
            if(result != 0) {
                return "BERHASIL MENDAFTARKAN AKUN";
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return "AKUN GAGAL DIDAFTARKAN, DATA YANG DIBERIKAN TIDAK BENAR";
    }
    
    static void getUsers(ArrayList<Customer> n) {
        try {           
            n.clear();
            String query = "SELECT * FROM users WHERE otorisasi = 'user'";
            ResultSet resultSet = query(query);
            while (resultSet.next()) {
                String resultId = resultSet.getString(1);
                String resultUsername = resultSet.getString(2);
                String resultPassword = resultSet.getString(3);
                String resultNama = resultSet.getString(4);
                String resultAlamat = resultSet.getString(5);
                String resultNoTelp = resultSet.getString(6);
                String resultOtorisasi = resultSet.getString(7);
                Customer user = new Customer(
                    resultUsername,
                    resultPassword
                );
                user.setId(resultId);
                user.setNama(resultNama);
                user.setNoTelp(resultNoTelp);
                user.setAlamat(resultAlamat);
                user.setOtorisasi(resultOtorisasi);
                n.add(user);
                resultId = null; resultUsername = null; resultPassword = null;
                resultNama = null; resultAlamat = null; resultNoTelp = null;
                resultOtorisasi = null;
            }
            String leftAlignFormat = "| %-3s | %-15s | %-15s | %-15s | %-15s | %-32s | %-43s |%n";
            System.out.format("+-----+-----------------+-----------------+-----------------+-----------------+----------------------------------+---------------------------------------------+%n");
            System.out.format("| No  | Username        | Password        | Nama            | No Telepon      |  Alamat                          | ID                                          |%n");
            System.out.format("+-----+-----------------+-----------------+-----------------+-----------------+----------------------------------+---------------------------------------------+%n");
            for (int i = 0; i < n.size(); i++) {
                System.out.format(
                        leftAlignFormat, 
                        i+1,
                        n.get(i).getUsername(), 
                        n.get(i).getPassword(), 
                        n.get(i).getNama(), 
                        n.get(i).getNoTelp(), 
                        n.get(i).getAlamat(), 
                        n.get(i).getId()
                    );
            }
            System.out.format("+-----+-----------------+-----------------+-----------------+-----------------+----------------------------------+---------------------------------------------+%n");
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
    static Customer getUserById(String id) {
        Customer user = null;
        try {
            String query = "SELECT * FROM users WHERE id='" + id + "'";
            ResultSet resultSet = query(query);
            while (resultSet.next()) {
                String resultId = resultSet.getString(1);
                String resultUsername = resultSet.getString(2);
                String resultPassword = resultSet.getString(3);
                String resultNama = resultSet.getString(4);
                String resultAlamat = resultSet.getString(5);
                String resultNoTelp = resultSet.getString(6);
                String resultOtorisasi = resultSet.getString(7);
                user = new Customer(
                    resultUsername,
                    resultPassword
                );
                user.setId(resultId);
                user.setNama(resultNama);
                user.setNoTelp(resultNoTelp);
                user.setAlamat(resultAlamat);
                user.setOtorisasi(resultOtorisasi);
                return user;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return user;
    }
    
    static String getOneUser(String username, String password) {
        try {
            String query = "SELECT id FROM users WHERE username='" + username + "'" + " && password='" + password + "'" ;
            ResultSet resultSet = query(query);
            while (resultSet.next()) {
                String resultId = resultSet.getString(1);
                return resultId;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return "";
    }
    
    
    static String getOneUser(String id) {
        try {
            String query = "SELECT username FROM users WHERE id='" + id + "'";
            ResultSet resultSet = query(query);
            while (resultSet.next()) {
                String resultUsername = resultSet.getString(1);
                return resultUsername;
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return "";
    }
    
    static String updateUserById(String id, String username, String password, String nama, String alamat, String noTelp) {
        try {
            String query = "UPDATE users SET "
                    + "username='" + username + "', "
                    + "password='" + password + "', "
                    + "nama='" + nama + "', "
                    + "alamat='" + alamat + "', "
                    + "noTelp='" + noTelp + "' "
                    + "WHERE id='" + id + "'";
            int result = update(query);
                
            if(result != 0) {
                return "BERHASIL MEMPERBARUI AKUN";
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return "GAGAL MEMPERBARUI AKUN, TERJADI KESALAHAN";
    }
    
    static String deleteUserById(String id) {
        try {
            String query = "DELETE FROM users WHERE id='" + id + "'";
            int result = update(query);
                
            if(result != 0) {
                return "BERHASIL MENGHAPUS AKUN";
            }
          //ErHand
        } catch(Exception e) {
            System.out.println(e);
        }
        return "GAGAL MENGHAPUS AKUN, TERJADI KEGAGALAN PADA SERVER";
    }
    
    static void continueInput() {
        System.out.print("Tekan Enter untuk melanjutkan...");
        myObj.nextLine();
    }
    
    static void clear_screen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

//  MENU
    public static void main(String[] args) {              
        // declare variable
        ArrayList<Customer> dataCustomer = new ArrayList<>();
        // ArrayList<Admin> dataAdmin = new ArrayList<>();
        ArrayList<Game> dataGames = new ArrayList<>();
        ArrayList<Voucher> dataVouchers = new ArrayList<>();
        ArrayList<Transaction> dataTransactions = new ArrayList<>();
        
        boolean repeat = true;
        String pil, pil2, pil3, pil4, pil5;
        String username, password, nama, alamat, noTelp;
        String deskripsi;
        String result;
        String credentialId;
        String idGame;
        String nominalVoucher, tempHargaVoucher;
        int hargaVoucher;
        
        String[] metodePembayaran = {"gopay","OVO","DANA","ATM","Wallet", "Link Aja","Alfamart","Indomaret"};
        
//      MENU
        while(repeat) {
            clear_screen();
            System.out.println("\n===== TOKO GAME ETAM =====");
            System.out.println("1. Login"); // ini kalo login admin sama user campur
            System.out.println("2. Register");
            System.out.println("0. Keluar");
            System.out.println("==========================");
            System.out.print("Masukkan Pilihan: ");
            pil = myObj.nextLine();
            
            switch(pil) {
                case "1" -> {
                    clear_screen();
                    System.out.println("\n===========LOGIN===========");
                    System.out.print("Username: ");
                    username = myObj.nextLine();
                    System.out.print("Password: ");
                    password = myObj.nextLine();
                    //setelah user nginputkan data login, jalankan fungsi login
                    // disini tenpat login sebagai admin atau user
                    if (username.equals("admin")) {
                        Admin user = new Admin(username, password);
                        result = user.login(username, password);
                    } else {
                        Customer user = new Customer(username, password);
                        result = user.login(username, password);
                    }
                    credentialId = getOneUser(username, password);
                    username = null; password = null;
                    
                    // Error jika Query ada yang salah
                    if (result == null) {
                        System.out.println("\nKREDENSIAL YANG DIBERIKAN TIDAK BENAR");
                        continueInput();
                        continue;
                    // Error jika Akun tidak ditemukan
                    } else if(result == "") {
                        System.out.println("\nAKUN TIDAK DITEMUKAN ATAU BELUM TERDAFTAR");
                        continueInput();
                        continue;
                    } else {
                        if (result.equals("admin") && credentialId != "") {
                            //MENU ADMIN
                            boolean repeat2 = true;
                            while(repeat2) {
                                clear_screen();
                                System.out.println("=== SELAMAT DATANG ADMIN === ");
                                menuAdmin();
                                System.out.print("Masukkan Pilihan: ");
                                pil2 = myObj.nextLine();
                                switch(pil2) {
                                    case "1" -> {
                                        boolean repeat3 = true;
                                        while(repeat3) {
                                            clear_screen();
                                            templateAdmin2("Game");
                                            System.out.print("Masukkan Pilihan: ");
                                            pil3 = myObj.nextLine();
                                            switch(pil3) {
                                                case "1" -> {
                                                    // read game
                                                    clear_screen();
                                                    Game.getGames(dataGames);
                                                    continueInput();
                                                }
                                                case "2" -> {
                                                    // add game
                                                    while(true) {
                                                        while (true){
                                                            System.out.print("Nama: ");
                                                            nama = myObj.nextLine();
                                                            if (nama.equals("")) {
                                                                System.out.println("Input tidak boleh kosong");
                                                                continue;
                                                            }
                                                            break;
                                                        }

                                                        while (true){
                                                            System.out.print("Deskripsi: ");
                                                            deskripsi = myObj.nextLine();
                                                            if (deskripsi.equals("")) {
                                                                System.out.println("Input tidak boleh kosong");
                                                                continue;
                                                            }
                                                            break;
                                                        }

                                                        final UUID uuid = UUID.randomUUID();
                                                        final String id = "game-" + uuid.toString();

                                                        System.out.println(Game.addGame(id, nama, deskripsi));
                                                        nama = null;
                                                        deskripsi = null;
                                                        break;
                                                    }
                                                    break;
                                                }
                                                case "3" -> {
                                                    while(true) {
                                                        try{
                                                            // update game
                                                            clear_screen();
                                                            Game.getGames(dataGames);
                                                            System.out.println("Pilih No. game yang ingin diubah.");
                                                            System.out.print("Masukkan Pilihan: ");
                                                            pil4 = myObj.nextLine();

                                                            Game selectedGame = Game.getGameById(dataGames.get(Integer.parseInt(pil4)-1).getId());
                                                            System.out.println("\n\n=== Data Game yang anda pilih ===");
                                                            System.out.println("ID        : " + selectedGame.getId());
                                                            System.out.println("Nama      : " + selectedGame.getNama());
                                                            System.out.println("Deskripsi : " + selectedGame.getDeskripsi());

                                                            System.out.println("\n\n=== Masukkan Data Baru Game ===");
                                                            
                                                            while (true){
                                                                System.out.print("Nama: ");
                                                                nama = myObj.nextLine();
                                                                if (nama.equals("")) {
                                                                    System.out.println("Input tidak boleh kosong");
                                                                    continue;
                                                                }
                                                                break;
                                                            }
                                                            
                                                            while (true){
                                                                System.out.print("Deskripsi: ");
                                                                deskripsi = myObj.nextLine();
                                                                if (deskripsi.equals("")) {
                                                                    System.out.println("Input tidak boleh kosong");
                                                                    continue;
                                                                }
                                                                break;
                                                            }
                                                            

                                                            System.out.println(Game.updateGameById(selectedGame.getId(), nama, deskripsi));
                                                            continueInput();
                                                            nama = null;
                                                            deskripsi = null;
                                                            break;
                                                        } catch (IndexOutOfBoundsException e) {
                                                            System.out.println("Game tidak ada, masukkan input dengan benar.");
                                                            continueInput();
                                                            continue;
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Input harus berupa Integer.");
                                                            continueInput();
                                                            continue;
                                                        }
                                                    }
                                                    break;
                                                }
                                                case "4" -> {
                                                    // delete game
                                                    clear_screen();
                                                    Game.getGames(dataGames);
                                                    System.out.println("Pilih No. game yang ingin dihapus.");
                                                    while(true) {
                                                        try {
                                                            System.out.print("Masukkan Pilihan: ");
                                                            pil4 = myObj.nextLine();

                                                            Game selectedGame = Game.getGameById(dataGames.get(Integer.parseInt(pil4)-1).getId());
                                                            System.out.println("\n\n=== Data Game yang anda pilih ===");
                                                            System.out.println("ID        : " + selectedGame.getId());
                                                            System.out.println("Nama      : " + selectedGame.getNama());
                                                            System.out.println("Deskripsi : " + selectedGame.getDeskripsi());

                                                            while(true) {
                                                                System.out.println("Apakah anda yakin ingin menghapus game ini ? (Y/N)");
                                                                System.out.print("Masukkan Pilihan: ");
                                                                pil5 = myObj.nextLine();
                                                                if (pil5.equals("Y") || pil5.equals("y")) {
                                                                    System.out.println(Game.deleteGameById(selectedGame.getId()));
                                                                    continueInput();
                                                                }
                                                                break;
                                                            }
                                                            break;
                                                        } catch (IndexOutOfBoundsException e) {
                                                            System.out.println("Game tidak ada, masukkan input dengan benar.");
                                                            continueInput();
                                                            continue;
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Input harus berupa Integer.");
                                                            continueInput();
                                                            continue;
                                                        }
                                                    }
                                                }
                                                case "0" -> {
                                                    repeat3 = false;
                                                    break;
                                                }
                                                default -> {
                                                    System.out.println("\nMenu yang anda pilih tidak tersedia.");
                                                    continueInput();
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case "2" -> { // Pengelolaan Data User
                                        boolean repeat3 = true;
                                        while(repeat3) {
                                            clear_screen();
                                            templateAdmin1("User");
                                            System.out.print("Masukkan Pilihan: ");
                                            pil3 = myObj.nextLine();
                                            switch(pil3) {
                                                case "1" -> {
                                                    // read user
                                                    clear_screen();
                                                    getUsers(dataCustomer);
                                                    continueInput();
                                                }
                                                case "2" -> {
                                                    // update user
                                                    clear_screen();
                                                    getUsers(dataCustomer);
                                                    System.out.println("Pilih No. User yang mau diubah.");
                                                    try {
                                                        System.out.print("Masukkan Pilihan: ");
                                                        pil4 = myObj.nextLine();
                                                        
                                                        Customer selectedUser = getUserById(dataCustomer.get(Integer.parseInt(pil4)-1).getId());
                                                        System.out.println("\n\n=== Data User yang anda pilih ===");
                                                        System.out.println("ID          : " + selectedUser.getId());
                                                        System.out.println("Username    : " + selectedUser.getUsername());
                                                        System.out.println("Password    : " + selectedUser.getPassword());
                                                        System.out.println("Nama        : " + selectedUser.getNama());
                                                        System.out.println("Alamat      : " + selectedUser.getAlamat());
                                                        System.out.println("NoTelp      : " + selectedUser.getNoTelp());
                                                        
                                                        System.out.println("\n\n=== Silahkan Masukkan Data Baru User ===");
                                                        System.out.print("Username: ");
                                                        username = myObj.nextLine();
                                                        if(username.length() < 4) {
                                                            System.out.println("\nUsername tidak dapat kurang dari 4 karakter.");
                                                            continueInput();
                                                            break;
                                                        }
                                                        System.out.print("Password: ");
                                                        password = myObj.nextLine();
                                                        if(password.length() < 4) {
                                                            System.out.println("\nPassword tidak dapat kurang dari 4 karakter.");
                                                            continueInput();
                                                            break;
                                                        }
                                                        System.out.print("Nama: ");
                                                        nama = myObj.nextLine();
                                                        if(nama.length() < 3) {
                                                            System.out.println("\nNama tidak dapat kurang dari 3 karakter.");
                                                            continueInput();
                                                            break;
                                                        }
                                                        System.out.print("Alamat: ");
                                                        alamat = myObj.nextLine();
                                                        if(alamat.length() < 4) {
                                                            System.out.println("\nAlamat tidak dapat kurang dari 4 karakter.");
                                                            continueInput();
                                                            break;
                                                        }
                                                        System.out.print("No Telp: ");
                                                        noTelp = myObj.nextLine();
                                                        try {
                                                            if(String.valueOf(Long.parseLong(noTelp)).length() < 10 || String.valueOf(Long.parseLong(noTelp)).length() > 14) {
                                                                System.out.println("\nNo Telp tidak dapat kurang dari 10 angka");
                                                                System.out.println("atau, lebih dari 14 angka.");
                                                                continueInput();
                                                                break;
                                                            }
                                                        } catch(Exception e) {
                                                            System.out.println("\nNo Telp tidak dapat terdiri dari huruf.");
                                                            continueInput();
                                                            break;
                                                        }
                
                                                        System.out.println("\n"+updateUserById(selectedUser.getId(), username, password, nama, alamat, noTelp));
                                                        continueInput();
                                                        
                                                        pil4 = null; username = null; password = null;
                                                        nama = null; alamat = null; noTelp = null;
                                                    } catch (Exception e) {
                                                        System.out.println("\nNo. user yang anda pilih tidak tersedia.");
                                                        continueInput();
                                                    }
                                                    break;
                                                }
                                                case "3" -> {
                                                    // delete user
                                                    clear_screen();
                                                    getUsers(dataCustomer);
                                                    System.out.println("Pilih No. User yang mau dihapus.");
                                                    while(true) {
                                                        try {
                                                            System.out.print("Masukkan Pilihan: ");
                                                            pil4 = myObj.nextLine();

                                                            Customer selectedUser = getUserById(dataCustomer.get(Integer.parseInt(pil4)-1).getId());
                                                            System.out.println("\n\n=== Data User yang anda pilih ===");
                                                            System.out.println("ID          : " + selectedUser.getId());
                                                            System.out.println("Username    : " + selectedUser.getUsername());
                                                            System.out.println("Password    : " + selectedUser.getPassword());
                                                            System.out.println("Nama        : " + selectedUser.getNama());
                                                            System.out.println("Alamat      : " + selectedUser.getAlamat());
                                                            System.out.println("NoTelp      : " + selectedUser.getNoTelp());

                                                            while(true) {
                                                                System.out.println("Apakah anda yakin ingin menghapus user ini ? (Y/N)");
                                                                System.out.print("Masukkan Pilihan: ");
                                                                pil5 = myObj.nextLine();
                                                                if (pil5.toLowerCase().equals("y")){
                                                                    System.out.println(deleteUserById(selectedUser.getId()));
                                                                    continueInput();
                                                                }
                                                                break;
                                                            }
                                                            break;
                                                        } catch (IndexOutOfBoundsException e) {
                                                            System.out.println("User tidak ada, masukkan input dengan benar.");
                                                            continueInput();
                                                            continue;
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Input harus berupa Integer.");
                                                            continueInput();
                                                            continue;
                                                        }
                                                    }
                                                    break;
                                                }
                                                case "0" -> {
                                                    repeat3 = false;
                                                }
                                                default -> {
                                                    System.out.println("\nMenu yang anda pilih tidak tersedia.");
                                                    continueInput();
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case "3" -> {
                                        // Pengelolaan Data Voucher
                                        boolean repeat3 = true;
                                        while(repeat3) {
                                            clear_screen();
                                            templateAdmin2("Voucher");
                                            System.out.print("Masukkan Pilihan: ");
                                            pil3 = myObj.nextLine();
                                            switch(pil3) {
                                                case "1" -> {
                                                    // read vouchers
                                                    clear_screen();
                                                    Voucher.getVouchers(dataVouchers);
                                                    continueInput();
                                                }
                                                case "2" -> {
                                                    while(true) {
                                                        // add vouchers
                                                        Game.getGames(dataGames);
                                                        System.out.println("Pilih No. game");
                                                        while(true) {
                                                            try { 
                                                                while (true){
                                                                    System.out.print("Masukkan Pilihan: ");
                                                                    pil4 = myObj.nextLine();
                                                                    
                                                                    idGame = Game.getGameById(dataGames.get(Integer.parseInt(pil4)-1).getId()).getId();
                                                                    break;
                                                                }
                                                            } catch (NumberFormatException e) {
                                                                System.out.println("Input harus berupa Integer.");
                                                                continue;
                                                            } catch (IndexOutOfBoundsException e) {
                                                                System.out.println("Game yang anda pilih tidak tersedia.");
                                                                continue;
                                                            }
                                                            break;
                                                        }
                                                        
                                                        while (true){
                                                            System.out.print("Nominal : ");
                                                            nominalVoucher = myObj.nextLine();

                                                            if (nominalVoucher.equals("")) {
                                                                System.out.println("Input tidak boleh kosong");
                                                                continue;
                                                            }
                                                            break;
                                                        }                                                            
                                                        
                                                        while(true) {
                                                            try { 
                                                                while (true){
                                                                    System.out.print("Harga  : ");
                                                                    tempHargaVoucher = myObj.nextLine();
                                                                    if (tempHargaVoucher.equals("")) {
                                                                        System.out.println("Input tidak boleh kosong");
                                                                        continue;
                                                                    }
                                                                    hargaVoucher = Integer.parseInt(tempHargaVoucher);
                                                                    break;
                                                                }                                                            
                                                            } catch (NumberFormatException e) {
                                                                System.out.println("Input harus berupa Integer.");
                                                                continue;
                                                            }
                                                            break;
                                                        }
                                                        break;
                                                    }
    
                                                    final UUID uuid = UUID.randomUUID();
                                                    final String id = "voucher-" + uuid.toString();
                                                    
                                                    System.out.println(Voucher.addVoucher(id, idGame, nominalVoucher, hargaVoucher)); 
                                                    continueInput();

                                                    idGame = null;
                                                    nominalVoucher = null;
                                                    hargaVoucher = 0;
                                                    break;
                                                }
                                                case "3" -> {
                                                    while(true) {
                                                        try {
                                                            // update voucher
                                                            clear_screen();
                                                            Voucher.getVouchers(dataVouchers);
                                                            System.out.println("Pilih No. Voucher yang mau diubah.");
                                                            System.out.print("Masukkan Pilihan: ");
                                                            pil4 = myObj.nextLine();

                                                            Voucher selectedVoucher = Voucher.getVoucherById(dataVouchers.get(Integer.parseInt(pil4)-1).getId());
                                                            clear_screen();
                                                            System.out.println("\n\n=== Data Voucher yang anda pilih ===");
                                                            System.out.println("ID          : " + selectedVoucher.getId());
                                                            System.out.println("ID Game     : " + selectedVoucher.getIdGame());
                                                            System.out.println("Nominal     : " + selectedVoucher.getNominalVoucher());
                                                            System.out.println("Harga       : " + selectedVoucher.getHargaVoucher());

                                                            System.out.println("\n\n=== Silahkan Masukkan Data Baru Voucher ===");                                                            
                                                            while (true){
                                                                System.out.print("Nominal : ");
                                                                nominalVoucher = myObj.nextLine();

                                                                if (nominalVoucher.equals("")) {
                                                                    System.out.println("Input tidak boleh kosong");
                                                                    continue;
                                                                }
                                                                break;
                                                            }         
                                                            while(true) {
                                                                try { 
                                                                    while (true){
                                                                        System.out.print("Harga  : ");
                                                                        tempHargaVoucher = myObj.nextLine();
                                                                        if (tempHargaVoucher.equals("")) {
                                                                            System.out.println("Input tidak boleh kosong");
                                                                            continue;
                                                                        }
                                                                        hargaVoucher = Integer.parseInt(tempHargaVoucher);
                                                                        break;
                                                                    }                                                            
                                                                } catch (NumberFormatException e) {
                                                                    System.out.println("Input harus berupa Integer.");
                                                                    continue;
                                                                }
                                                                break;
                                                            }

                                                            System.out.println(Voucher.updateVoucherById(selectedVoucher.getId(), nominalVoucher, hargaVoucher)); // ini klo gk pke Voucher. jg bsa tapi import updateVoucherById nya
                                                            continueInput();

                                                            pil4 = null; idGame = null; nominalVoucher = null; hargaVoucher = 0;
                                                            break;
                                                        } catch (IndexOutOfBoundsException e) {
                                                            System.out.println("\nVoucher tidak ada, masukkan input dengan benar.");
                                                            continueInput();
                                                            continue;
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("\nInput harus berupa Integer.");
                                                            continueInput();
                                                            continue;
                                                        }
                                                    }
                                                    break;
                                                }
                                                case "4" -> {
                                                    while(true) {
                                                        try {
                                                            // delete voucher
                                                            clear_screen();
                                                            Voucher.getVouchers(dataVouchers);
                                                            System.out.println("Pilih No. Voucher yang mau dihapus.");
                                                            System.out.print("Masukkan Pilihan: ");
                                                            pil4 = myObj.nextLine();

                                                            clear_screen();
                                                            Voucher selectedVoucher = Voucher.getVoucherById(dataVouchers.get(Integer.parseInt(pil4)-1).getId()); // ini klo gk pke Voucher. jg bsa tapi import getVoucherById nya
                                                            System.out.println("\n\n=== Data Voucher yang anda pilih ===");
                                                            System.out.println("ID          : " + selectedVoucher.getId());
                                                            System.out.println("ID Game     : " + selectedVoucher.getIdGame());
                                                            System.out.println("Nominal     : " + selectedVoucher.getNominalVoucher());
                                                            System.out.println("Harga       : " + selectedVoucher.getHargaVoucher());

                                                            while(true) {
                                                                System.out.println("Apakah anda yakin ingin menghapus voucher ini ? (Y/N)");
                                                                System.out.print("Masukkan Pilihan: ");
                                                                pil5 = myObj.nextLine();
                                                                if (pil5.toLowerCase().equals("y")){
                                                                    System.out.println(Voucher.deleteVoucherById(selectedVoucher.getId())); // ini klo gk pke Voucher. jg bsa tapi import deleteVoucherById nya
                                                                    continueInput();
                                                                } else if (pil5.toLowerCase().equals("n")){
                                                                    System.out.println("Voucher Gagal Dihapus."); // ini klo gk pke Voucher. jg bsa tapi import deleteVoucherById nya
                                                                    continueInput();
                                                                }
                                                                break;
                                                            }
                                                            break;
                                                        } catch (IndexOutOfBoundsException e) {
                                                            System.out.println("\nVoucher tidak ada, masukkan input dengan benar.");
                                                            continueInput();
                                                            continue;
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("\nInput harus berupa Integer.");
                                                            continueInput();
                                                            continue;
                                                        }
                                                    }
                                                    break;
                                                }
                                                case "0" -> {
                                                    repeat3 = false;
                                                    break;
                                                }
                                                default -> {
                                                    System.out.println("\nMenu yang anda pilih tidak tersedia.");
                                                    continueInput();
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case "4" -> {
                                        // read transaksi
                                        clear_screen();
                                        Transaction.getTransactions(dataTransactions);
                                        System.out.print("\nTekan Untuk Melanjutkan...");
                                        myObj.nextLine();
                                        break;
                                    }
                                    case "0" -> {
                                        System.out.println("Keluar akun...");
                                        repeat2 = false;
                                        break;
                                    }
                                    default -> {
                                        System.out.println("\nMenu yang anda pilih tidak tersedia.");
                                        continueInput();
                                        break;
                                    }
                                }
                                
                            }
                        } else if (result.equals("user") && credentialId != "") {
                            // MENU USER
                            boolean repeat2 = true;
                            while(repeat2) {
                                clear_screen();
                                System.out.println("=== SELAMAT DATANG USER === ");
                                menuUser();
                                System.out.print("Masukkan Pilihan: ");
                                pil2 = myObj.nextLine();
                                switch(pil2) {
                                    // Lihat game dan juga TopUp
                                    case "1" -> {
                                        clear_screen();
                                        System.out.println("\n=== Silahkan Lakukan TopUp ===");
                                        Game.getGames(dataGames);
                                        try {
                                            // milih game
                                            System.out.print("Pilih nomor game : ");
                                            String numb = myObj.nextLine();
                                            String selectedGame = dataGames.get(Integer.parseInt(numb)-1).getId();
                                            String selectedGameName = Game.getNamaGameById(selectedGame);
                                            
                                            // milih jumlah topup                                        
                                            Voucher.getVouchersByIdGame(dataVouchers, selectedGame);
                                            System.out.print("Pilih nominal TopUp : ");
                                            String voucher = myObj.nextLine();
                                            Voucher selectedVoucher = dataVouchers.get(Integer.parseInt(voucher)-1);
                                            String selectedIdVoucher = dataVouchers.get(Integer.parseInt(voucher)-1).getId();
                                            String selectedNominalVoucher = selectedVoucher.getNominalVoucher();
                                            int selectedHargaVoucher = selectedVoucher.getHargaVoucher();
                                            
                                            // milih metode topUp
                                            String leftAlignFormat = "| %-3s | %-17s |%n";
                                            System.out.format("+-----+-------------------+%n");
                                            System.out.format("| No  | Jenis Pembayaran  |%n");
                                            System.out.format("+-----+-------------------+%n");
                                            for(int i = 0; i < metodePembayaran.length; i++) {
                                                System.out.format(
                                                    leftAlignFormat, 
                                                    i+1,
                                                    metodePembayaran[i]
                                                );
                                            }
                                            System.out.format("+-----+-------------------+%n");
                                            System.out.print("Pilih metode pembayaran anda : ");
                                            String nomor = myObj.nextLine();
                                            String metode = metodePembayaran[Integer.parseInt(nomor)-1];
    
                                            // input id game user
                                            System.out.print("\nMasukkan id game anda : ");
                                            String userIdGame = myObj.nextLine();
                                            
                                            final UUID uuid = UUID.randomUUID();
                                            final String id = "transaksi-" + uuid.toString();
                                            
                                            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());
                                            
                                            clear_screen();
                                            Transaction.addTransaction(id, selectedIdVoucher, credentialId, selectedGameName, userIdGame, metode, timeStamp, selectedNominalVoucher, selectedHargaVoucher);
                                            System.out.println("\nBERHASIL MELAKUKAN TOPUP");
                                            continueInput();
                                        
                                        } catch(NumberFormatException e) {
                                            System.out.println("\nInput tidak dapat berupa huruf.");
                                            continueInput();
                                        } catch(IndexOutOfBoundsException e) {
                                            System.out.println("\nPilihan anda tidak tersedia.");
                                            continueInput();
                                        }
                                        break;
                                    }
                                    // Update akun (username, password, nama, alamat, notelp)
                                    case "2" -> {
                                        clear_screen();
                                        Customer selectedUser = getUserById(credentialId);
                                        System.out.println("\n\n=== Data Anda Sebelumnya ===");
                                        System.out.println("ID          : " + selectedUser.getId());
                                        System.out.println("Username    : " + selectedUser.getUsername());
                                        System.out.println("Password    : " + selectedUser.getPassword());
                                        System.out.println("Nama        : " + selectedUser.getNama());
                                        System.out.println("Alamat      : " + selectedUser.getAlamat());
                                        System.out.println("NoTelp      : " + selectedUser.getNoTelp());
                                        
                                        
                                        System.out.println("\n\n=== Silahkan Masukkan Data Baru Akun Anda ===");
                                        System.out.print("Username: ");
                                        username = myObj.nextLine();
                                        if(username.length() < 4) {
                                            System.out.println("\nUsername tidak dapat kurang dari 4 karakter.");
                                            continueInput();
                                            break;
                                        }
                                        System.out.print("Password: ");
                                        password = myObj.nextLine();
                                        if(password.length() < 4) {
                                            System.out.println("\nPassword tidak dapat kurang dari 4 karakter.");
                                            continueInput();
                                            break;
                                        }
                                        System.out.print("Nama: ");
                                        nama = myObj.nextLine();
                                        if(nama.length() < 3) {
                                            System.out.println("\nNama tidak dapat kurang dari 3 karakter.");
                                            continueInput();
                                            break;
                                        }
                                        System.out.print("Alamat: ");
                                        alamat = myObj.nextLine();
                                        if(alamat.length() < 4) {
                                            System.out.println("\nAlamat tidak dapat kurang dari 4 karakter.");
                                            continueInput();
                                            break;
                                        }
                                        System.out.print("No Telp: ");
                                        noTelp = myObj.nextLine();
                                        try {
                                            if(String.valueOf(Long.parseLong(noTelp)).length() < 10 || String.valueOf(Long.parseLong(noTelp)).length() > 14) {
                                                System.out.println("\nNo Telp tidak dapat kurang dari 10 angka");
                                                System.out.println("atau, lebih dari 14 angka.");
                                                continueInput();
                                                break;
                                            }
                                        } catch(Exception e) {
                                            System.out.println("\nNo Telp tidak dapat terdiri dari huruf.");
                                            continueInput();
                                            break;
                                        }

                                        System.out.println("\n"+updateUserById(credentialId, username, password, nama, alamat, noTelp));
                                        continueInput();

                                        username = null; password = null;
                                        nama = null; alamat = null; noTelp = null;
                                        break;                                        
                                    }
                                    case "0" -> {
                                        System.out.println("Keluar akun...");
                                        repeat2 = false;
                                        break;
                                    } 
                                    default -> {
                                        System.out.println("\nMenu yang anda pilih tidak tersedia.");
                                        continueInput();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                
                // Buat akun
                case "2" -> {
                    clear_screen();
                    System.out.println("\n==========REGISTER==========");
                    
                    System.out.print("Username: ");
                    username = myObj.nextLine();
                    if(username.length() < 4) {
                        System.out.println("\nUsername tidak dapat kurang dari 4 karakter.");
                        continueInput();
                        break;
                    }
                    System.out.print("Password: ");
                    password = myObj.nextLine();
                    if(password.length() < 4) {
                        System.out.println("\nPassword tidak dapat kurang dari 4 karakter.");
                        continueInput();
                        break;
                    }
                    System.out.print("Nama: ");
                    nama = myObj.nextLine();
                    if(nama.length() < 3) {
                        System.out.println("\nNama tidak dapat kurang dari 3 karakter.");
                        continueInput();
                        break;
                    }
                    System.out.print("Alamat: ");
                    alamat = myObj.nextLine();
                    if(alamat.length() < 4) {
                        System.out.println("\nAlamat tidak dapat kurang dari 4 karakter.");
                        continueInput();
                        break;
                    }
                    System.out.print("No Telp: ");
                    noTelp = myObj.nextLine();
                    try {
                        if(String.valueOf(Long.parseLong(noTelp)).length() < 10 || String.valueOf(Long.parseLong(noTelp)).length() > 14) {
                            System.out.println("\nNo Telp tidak dapat kurang dari 10 angka");
                            System.out.println("atau, lebih dari 14 angka.");
                            continueInput();
                            break;
                        }
                    } catch(Exception e) {
                        System.out.println("\nNo Telp tidak dapat terdiri dari huruf.");
                        continueInput();
                        break;
                    }

                    final UUID uuid = UUID.randomUUID();
                    final String id = "user-" + uuid.toString();
                    
                    //setelah user nginputkan data register, jalankan fungsi register
                    System.out.println("\n"+addUser(id, username, password, nama, alamat, noTelp));
                    continueInput();

                    username = null;
                    password = null;
                    nama = null;
                    alamat = null;
                    noTelp = null;
                    break;
                }
                case "0" -> {
                    clear_screen();
                    System.out.println("===========KELUAR===========");
                    System.out.println("Terima Kasih Telah Menggunakan Aplikasi");
                    // close scanner
                    myObj.close();
                    // end program
                    repeat = false;
                    break;
                }
                default -> {
                    System.out.println("\nMenu yang anda pilih tidak tersedia.");
                    continueInput();
                    break;
                }
            }
        }
    }
    
}
