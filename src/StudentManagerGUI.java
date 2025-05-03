import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class StudentManagerGUI extends JFrame {

    private ArrayList<Student> studenti;
    private JTextArea prikaz;

    public StudentManagerGUI() {
        studenti = ucitajPodatke();

        setTitle(" Student Manager ");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Dugmad i paneli
        JPanel panel = new JPanel(new GridLayout(1, 7));
        JButton dodajStudentaBtn = new JButton(" Dodaj studenta ");
        JButton dodajOcenuBtn = new JButton(" Dodaj ocenu ");
        JButton prikaziBtn = new JButton(" Prikazi sve");
        JButton sacuvajBtn = new JButton(" Sacuvaj ");
        JButton pretraziStudentaBtn = new JButton(" Pretrazi studenta ");
        JButton obrisiStudentaBtn = new JButton(" Obrisi Studenta ");
        JButton eksportCSVBtn = new JButton(" Eksport podataka u CSV fajl");


        panel.add(dodajStudentaBtn);
        panel.add(dodajOcenuBtn);
        panel.add(prikaziBtn);
        panel.add(sacuvajBtn);
        panel.add(pretraziStudentaBtn);
        panel.add(obrisiStudentaBtn);
        panel.add(eksportCSVBtn);
        add(panel, BorderLayout.NORTH);

        // Prikaz
        prikaz = new JTextArea();
        prikaz.setEnabled(false);
        add(new JScrollPane(prikaz), BorderLayout.CENTER);

        // Akcije
        dodajStudentaBtn.addActionListener(e -> dodajStudenta());
        dodajOcenuBtn.addActionListener(e -> dodajOcenu());
        prikaziBtn.addActionListener(e -> prikaziStudente());
        sacuvajBtn.addActionListener(e -> sacuvajPodatke());
        pretraziStudentaBtn.addActionListener(e -> pretraziStudenta());
        obrisiStudentaBtn.addActionListener(e -> obrisiStudenta());
        eksportCSVBtn.addActionListener(e -> eksportCSV());


        setVisible(true);

    }

    private void dodajStudenta() {

        String ime = JOptionPane.showInputDialog(" Unesite Ime: ");
        String prezime = JOptionPane.showInputDialog(" Unesite Prezime: ");
        String indeks = JOptionPane.showInputDialog(" Unesite broj indeksa: ");

        if (ime != null && prezime != null && indeks != null) {
            studenti.add(new Student(ime, prezime, indeks));
        }
    }

    private void dodajOcenu() {

        String indeks = JOptionPane.showInputDialog("Unesite indeks: ");
        if (indeks == null || indeks.trim().isEmpty()) return;

        String[] predmeti = {"Matematika", "Programiranje", "Fizika"};
        JComboBox<String> predmetBox = new JComboBox<>(predmeti);

        int izbor = JOptionPane.showConfirmDialog(this, predmetBox, "Izaberite predmet", JOptionPane.OK_CANCEL_OPTION);
        if (izbor != JOptionPane.OK_OPTION) return;

        String izabraniPredmet = (String) predmetBox.getSelectedItem();
        if (izabraniPredmet == null || izabraniPredmet.trim().isEmpty()) return;

        for (Student s : studenti) {
            if (s.getBrojIndeksa().equals(indeks)) {
                String ocenaStr = JOptionPane.showInputDialog("Unesite ocenu za " + izabraniPredmet + ": ");
                if (ocenaStr == null || ocenaStr.trim().isEmpty()) return;
                try {
                    int ocena = Integer.parseInt(ocenaStr.trim());
                    s.dodajOcenu(izabraniPredmet, ocena);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Neispravna ocena!");
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Student nije pronađen");
    }
    private void prikaziStudente() {

        StringBuilder sb = new StringBuilder();
        for (Student s : studenti) {
            sb.append(s.toString()).append("\n");
        }
        prikaz.setText(sb.toString());
    }

    private void pretraziStudenta(){

        String indeks = JOptionPane.showInputDialog(" Unesite broj indeksa: ");
        for (Student s : studenti){
            if (s.getBrojIndeksa().equals(indeks)){
                JOptionPane.showMessageDialog(this, s.toString());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, " Student nije pronadjen");
    }

    private void obrisiStudenta(){

        String indeks = JOptionPane.showInputDialog(" Unesite broj indeksa studenta za brisanje");
        for (Student s : studenti){
            if (s.getBrojIndeksa().equals(indeks)){
                studenti.remove(s);
                JOptionPane.showMessageDialog(this, " Student obrisan");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, " Student nije pronadjen");

    }

    private void eksportCSV(){

        try (PrintWriter writer = new PrintWriter(new FileWriter("Studenti.csv"))) {
            writer.println("ime,prezime,indeks,prosek,ocene_po_predmetima");

            for (Student s : studenti) {
                writer.printf("%s,%s,%s,%.2f,\"%s\"%n",
                        s.getIme(),
                        s.getPrezime(),
                        s.getBrojIndeksa(),
                        s.getProsek(),
                        s.getOcenePoPredmetimaCSV()
                );
            }

            JOptionPane.showMessageDialog(this, "Podaci eksportovani u CSV");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Greska pri pisanju u CSV: " + e.getMessage());
        }

    }

    private void sacuvajPodatke(){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Studenti.dat"))){
            out.writeObject(studenti);
            JOptionPane.showMessageDialog(this, " Podaci su sacuvani");
        } catch (IOException e){
            JOptionPane.showMessageDialog(this, " Greska: " + e.getMessage());
        }
    }

    private ArrayList<Student> ucitajPodatke(){

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("Studenti.data"))){
            return(ArrayList<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            return new ArrayList<>();
        }

    }

    public static void main (String[] args){

        SwingUtilities.invokeLater(StudentManagerGUI :: new);
    }

 }


