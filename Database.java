import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private ArrayList<Mahasiswa> data = new ArrayList<>();
    private final String filename = "src/data.csv";
    private final Path path = Path.of(filename);

    public Database() {
        open();
    }

    public ArrayList<Mahasiswa> getData() {
        return data;
    }

    public void open() throws RuntimeException {
        try {
            List<String> lines = Files.readAllLines(path);
            data = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] element = line.split(";");
                if (element.length < 6) continue; // Menghindari error jika data kurang dari 6 kolom
                String nim = element[0];
                String nama = element[1];
                String alamat = element[2];
                int semester = Integer.parseInt(element[3].trim());
                int sks = Integer.parseInt(element[4].trim());
                double ipk = Double.parseDouble(element[5].trim().replace(",", "."));
                Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
                data.add(mhs);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        StringBuilder sb = new StringBuilder();
        sb.append("NIM;NAMA;ALAMAT(KOTA);SEMESTER;SKS;IPK\n"); // Header diperbaiki agar sesuai dengan separator

        for (Mahasiswa mhs : data) {
            String line = String.format("%s;%s;%s;%d;%d;%.2f%n",
                    mhs.getNim(), mhs.getNama(), mhs.getAlamat(),
                    mhs.getSemester(), mhs.getSks(), mhs.getIpk());
            sb.append(line);
        }

        try {
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void view() {
        System.out.println("===================================================================================");
        System.out.printf("| %-8s | %-20s | %-20s | %-8s | %-3s | %-4s |%n",
                "NIM", "NAMA", "ALAMAT", "SEMESTER", "SKS", "IPK");
        System.out.println("-----------------------------------------------------------------------------------");

        for (Mahasiswa mhs : data) {
            System.out.printf("| %-8s | %-20s | %-20s | %8d | %3d | %4.2f |%n",
                    mhs.getNim(), mhs.getNama(), mhs.getAlamat(),
                    mhs.getSemester(), mhs.getSks(), mhs.getIpk());
        }

        System.out.println("-----------------------------------------------------------------------------------");
    }

    public boolean insert(String nim, String nama, String alamat, int semester, int sks, double ipk) {
        for (Mahasiswa mhs : data) {
            if (mhs.getNim().equalsIgnoreCase(nim)) {
                return false;
            }
        }
        data.add(new Mahasiswa(nim, nama, alamat, semester, sks, ipk));
        save();
        return true;
    }

    public int search(String nim) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getNim().equalsIgnoreCase(nim)) {
                return i;
            }
        }
        return -1;
    }

    public boolean update(int index, String nim, String nama, String alamat, int semester, int sks, double ipk) {
        if (index >= 0 && index < data.size()) {
            data.set(index, new Mahasiswa(nim, nama, alamat, semester, sks, ipk));
            save();
            return true;
        }
        return false;
    }

    public boolean delete(int index) {
        if (index >= 0 && index < data.size()) {
            data.remove(index);
            save();
            return true;
        }
        return false;
    }
}
