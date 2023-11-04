/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quiz52022901;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class NilaiMahasiswaGUI extends JFrame implements ActionListener {
    private ArrayList<Mahasiswa> mahasiswaList = new ArrayList<>();
    private JButton inputButton, lihatButton;
    private JTextField nomorStambukField, namaField, tugasField, quizField, midField, finalField;
    private JTextArea resultArea;

    public NilaiMahasiswaGUI() {
        setTitle("Program Nilai Mahasiswa");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));

        JLabel nomorStambukLabel = new JLabel("Nomor Stambuk:");
        nomorStambukField = new JTextField(10);

        JLabel namaLabel = new JLabel("Nama Mahasiswa:");
        namaField = new JTextField(10);

        JLabel tugasLabel = new JLabel("Nilai Tugas:");
        tugasField = new JTextField(10);

        JLabel quizLabel = new JLabel("Nilai Quiz:");
        quizField = new JTextField(10);

        JLabel midLabel = new JLabel("Nilai Mid:");
        midField = new JTextField(10);

        JLabel finalLabel = new JLabel("Nilai Final:");
        finalField = new JTextField(10);

        inputButton = new JButton("Input Nilai");
        lihatButton = new JButton("Lihat Nilai");

        resultArea = new JTextArea(10, 20);
        resultArea.setEditable(false);

        inputButton.addActionListener(this);
        lihatButton.addActionListener(this);

        panel.add(nomorStambukLabel);
        panel.add(nomorStambukField);
        panel.add(namaLabel);
        panel.add(namaField);
        panel.add(tugasLabel);
        panel.add(tugasField);
        panel.add(quizLabel);
        panel.add(quizField);
        panel.add(midLabel);
        panel.add(midField);
        panel.add(finalLabel);
        panel.add(finalField);
        panel.add(inputButton);
        panel.add(lihatButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inputButton) {
            inputNilai();
        } else if (e.getSource() == lihatButton) {
            lihatNilai();
        }
    }

    private void inputNilai() {
        String nomorStambuk = nomorStambukField.getText();
        String nama = namaField.getText();
        double tugas = Double.parseDouble(tugasField.getText());
        double quiz = Double.parseDouble(quizField.getText());
        double mid = Double.parseDouble(midField.getText());
        double finalScore = Double.parseDouble(finalField.getText());

        double nilaiAkhir = (0.2 * tugas) + (0.2 * quiz) + (0.3 * mid) + (0.3 * finalScore);
        String nilaiHuruf = hitungNilaiHuruf(nilaiAkhir);

        Mahasiswa mahasiswa = new Mahasiswa(nomorStambuk, nama, tugas, quiz, mid, finalScore, nilaiAkhir, nilaiHuruf);
        mahasiswaList.add(mahasiswa);

        // Simpan data ke file
        simpanDataKeFile();

        // Reset input fields
        nomorStambukField.setText("");
        namaField.setText("");
        tugasField.setText("");
        quizField.setText("");
        midField.setText("");
        finalField.setText("");
    }

    private String hitungNilaiHuruf(double nilaiAkhir) {
        if (nilaiAkhir >= 80) {
            return "A";
        } else if (nilaiAkhir >= 70) {
            return "B";
        } else if (nilaiAkhir >= 60) {
            return "C";
        } else if (nilaiAkhir >= 50) {
            return "D";
        } else {
            return "E";
        }
    }

    private void simpanDataKeFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Quiz52021008.dat"))) {
            oos.writeObject(mahasiswaList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void lihatNilai() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Quiz52021008.dat"))) {
            mahasiswaList = (ArrayList<Mahasiswa>) ois.readObject();

            resultArea.setText("");
            for (Mahasiswa mahasiswa : mahasiswaList) {
                resultArea.append("Nomor Stambuk: " + mahasiswa.getNomorStambuk() + "\n");
                resultArea.append("Nama Mahasiswa: " + mahasiswa.getNama() + "\n");
                resultArea.append("Nilai Tugas: " + mahasiswa.getTugas() + "\n");
                resultArea.append("Nilai Quiz: " + mahasiswa.getQuiz() + "\n");
                resultArea.append("Nilai Mid: " + mahasiswa.getMid() + "\n");
                resultArea.append("Nilai Final: " + mahasiswa.getFinalScore() + "\n");
                resultArea.append("Nilai Akhir: " + mahasiswa.getNilaiAkhir() + "\n");
                resultArea.append("Nilai Huruf: " + mahasiswa.getNilaiHuruf() + "\n\n");
            }

            int jumlahLulus = 0;
            int jumlahTidakLulus = 0;

            for (Mahasiswa mahasiswa : mahasiswaList) {
                if (mahasiswa.getNilaiHuruf().equals("A") || mahasiswa.getNilaiHuruf().equals("B")
                        || mahasiswa.getNilaiHuruf().equals("C") || mahasiswa.getNilaiHuruf().equals("D")) {
                    jumlahLulus++;
                } else if (mahasiswa.getNilaiHuruf().equals("E")) {
                    jumlahTidakLulus++;
                }
            }

            resultArea.append("Jumlah data = " + mahasiswaList.size() + "\n");
            resultArea.append("Jumlah mahasiswa yang lulus = " + jumlahLulus + "\n");
            resultArea.append("Jumlah mahasiswa yang tidak lulus = " + jumlahTidakLulus + "\n");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NilaiMahasiswaGUI frame = new NilaiMahasiswaGUI();
            frame.setVisible(true);
        });
    }
}

class Mahasiswa implements Serializable {
    private String nomorStambuk;
    private String nama;
    private double tugas;
    private double quiz;
    private double mid;
    private double finalScore;
    private double nilaiAkhir;
    private String nilaiHuruf;

    public Mahasiswa(String nomorStambuk, String nama, double tugas, double quiz, double mid, double finalScore, double nilaiAkhir, String nilaiHuruf) {
        this.nomorStambuk = nomorStambuk;
        this.nama = nama;
        this.tugas = tugas;
        this.quiz = quiz;
        this.mid = mid;
        this.finalScore = finalScore;
        this.nilaiAkhir = nilaiAkhir;
        this.nilaiHuruf = nilaiHuruf;
    }

    public String getNomorStambuk() {
        return nomorStambuk;
    }

    public String getNama() {
        return nama;
    }

    public double getTugas() {
        return tugas;
    }

    public double getQuiz() {
        return quiz;
    }

    public double getMid() {
        return mid;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public double getNilaiAkhir() {
        return nilaiAkhir;
    }

    public String getNilaiHuruf() {
        return nilaiHuruf;
    }
}
