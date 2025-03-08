import java.util.Scanner;

public class userinterface {

    public static void tampilkanMenu() {
        System.out.println();
        System.out.println("+===============+");
        System.out.println("|  Pilih menu:   ");
        System.out.println("+---------------+");
        System.out.println("|  [C] : Create |");
        System.out.println("|  [R] : Read   |");
        System.out.println("|  [U] : Update |");
        System.out.println("|  [D] : Delete |");
        System.out.println("|  [X] : Exit   |");
        System.out.println("+===============+");
    }

    public static int getInputInt(Scanner sc, String prompt) {
        int value = -1;
        while (true) {
            try {
                System.out.print(prompt);
                value = sc.nextInt();
                sc.nextLine();
                break;
            } catch (java.util.InputMismatchException e) {
                System.err.println("Input tidak valid! Harap masukkan angka.");
                sc.nextLine();
            }
        }
        return value;
    }

    public static double getInputDouble(Scanner sc, String prompt) {
        double value = -1.0;
        while (true) {
            try {
                System.out.print(prompt);
                value = sc.nextDouble();
                sc.nextLine();
                break;
            } catch (java.util.InputMismatchException e) {
                System.err.println("Input tidak valid! Harap masukkan angka.");
                sc.nextLine();
            }
        }
        return value;
    }

    public static void main(String[] args) {
        Database db = new Database();
        Scanner sc = new Scanner(System.in);
        System.out.println("SELAMAT DATANG DI APLIKASI DATABASE");

        while (true) {
            tampilkanMenu();
            System.out.print("Pilih : ");
            String pilihan = sc.nextLine().trim().toUpperCase();

            switch (pilihan) {
                case "C":
                    System.out.println("INFO: Anda memilih menu Create");
                    System.out.println("--------------------------------------------");
                    System.out.print("NIM            : ");
                    String nim = sc.nextLine();
                    System.out.print("NAMA MAHASISWA : ");
                    String nama = sc.nextLine();
                    System.out.print("ALAMAT         : ");
                    String alamat = sc.nextLine();


                    int semester = getInputInt(sc, "SEMESTER       : ");
                    int sks = getInputInt(sc, "SKS            : ");
                    double ipk = getInputDouble(sc, "IPK            : ");

                    System.out.println("--------------------------------------------");
                    boolean status = db.insert(nim, nama, alamat, semester, sks, ipk);
                    if (status) {
                        System.out.println("DATA BARU BERHASIL DITAMBAHKAN");
                    } else {
                        System.out.println("NIM " + nim + " sudah ada di database");
                        System.out.println("GAGAL MENAMBAHKAN DATA BARU");
                    }
                    System.out.println("--------------------------------------------");
                    break;

                case "R":
                    System.out.println("INFO: Anda memilih menu Read");
                    db.view();
                    break;

                case "U":
                    System.out.println("INFO: Anda memilih menu Update");
                    db.view();
                    System.out.print("Input Key (NIM Mahasiswa yang akan diupdate): ");
                    String key = sc.nextLine();
                    int index = db.search(key);

                    if (index >= 0) {
                        System.out.println("Anda akan meng-update data " + db.getData().get(index));
                        System.out.println("--------------------------------------------");
                        System.out.print("NIM            : ");
                        nim = sc.nextLine();
                        System.out.print("NAMA MAHASISWA : ");
                        nama = sc.nextLine();
                        System.out.print("ALAMAT         : ");
                        alamat = sc.nextLine();


                        semester = getInputInt(sc, "SEMESTER       : ");
                        sks = getInputInt(sc, "SKS            : ");
                        ipk = getInputDouble(sc, "IPK            : ");

                        System.out.println("--------------------------------------------");
                        status = db.update(index, nim, nama, alamat, semester, sks, ipk);
                        if (status) {
                            System.out.println("DATA BERHASIL DIPERBARUI");
                        } else {
                            System.out.println("GAGAL MEMPERBARUI DATA");
                        }
                        System.out.println("--------------------------------------------");
                    } else {
                        System.err.println("Mahasiswa dengan NIM: " + key + " tidak ada di database");
                    }
                    break;

                case "D":
                    System.out.println("INFO: Anda memilih menu Delete");
                    db.view();
                    System.out.print("Input key (NIM Mahasiswa yang akan dihapus): ");
                    key = sc.nextLine();
                    index = db.search(key);

                    if (index >= 0) {
                        System.out.println("APAKAH ANDA YAKIN INGIN MENGHAPUS DATA " + db.getData().get(index) + "? (Y/N)");
                        System.out.print("Pilih : ");
                        pilihan = sc.nextLine();

                        if (pilihan.equalsIgnoreCase("Y")) {
                            status = db.delete(index);
                            if (status) {
                                System.out.println("DATA BERHASIL DIHAPUS");
                            } else {
                                System.err.println("Gagal menghapus data");
                            }
                        }
                    } else {
                        System.err.println("Mahasiswa dengan NIM: " + key + " tidak ada di database");
                    }
                    break;

                case "X":
                    System.out.println("INFO: Anda memilih menu EXIT");
                    System.out.print("APAKAH ANDA YAKIN INGIN KELUAR DARI APLIKASI? (Y/N): ");
                    pilihan = sc.nextLine();

                    if (pilihan.equalsIgnoreCase("Y")) {
                        System.out.println("TERIMA KASIH TELAH MENGGUNAKAN APLIKASI");
                        sc.close(); // Tutup scanner sebelum keluar
                        System.exit(0);
                    }
                    break;

                default:
                    System.out.println("Pilihan tidak valid! Silakan coba lagi.");
                    break;
            }
        }
    }
}
