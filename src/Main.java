import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static ArrayList<Student> studenti = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args){

        ucitajPodatke();
        int izbor;

        do {
            System.out.println("\n1 Dodajte studenta");
            System.out.println("2. Dodajte ocenu studentu");
            System.out.println("3. Prikazi sve studente");
            System.out.print("4. Sacuvaj i izadji");
            System.out.print("\n Izbor: ");

            izbor = scanner.nextInt(); scanner.nextLine();

            switch (izbor) {

                case 1 -> dodajStudenta();
                case 2 -> dodajOcenu();
                case 3 -> prikaziStudenta();
                case 4 -> sacuvajPodatke();
            }
        } while (izbor != 4);

    }

     static void dodajStudenta(){

        System.out.print(" Ime: ");
        String ime = scanner.nextLine();
        System.out.print(" Prezime: ");
        String prezime = scanner.nextLine();
        System.out.print(" Broj indeksa: ");
        String indeks = scanner.nextLine();
        studenti.add(new Student(ime, prezime, indeks));
     }

    static void dodajOcenu() {
        System.out.print(" Unesite broj indeksa: ");
        String indeks = scanner.nextLine();

        for (Student s : studenti) {
            if (s.getBrojIndeksa().equals(indeks)) {
                System.out.print(" Unesite predmet: ");
                String predmet = scanner.nextLine();  // Unos naziva predmeta

                System.out.print(" Unesite ocenu: ");
                int ocena = scanner.nextInt(); scanner.nextLine();

                s.dodajOcenu(predmet, ocena); // Poziv metode sa dva argumenta
                return;
            }
        }
        System.out.println(" Student nije pronadjen!!");
    }
     static void prikaziStudenta(){

        for (Student s : studenti){
            System.out.println(s);
        }
     }

     static void sacuvajPodatke(){

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Studenti.dat"))){
            out.writeObject(studenti);
            System.out.println(" Podaci su sacuvani ");
        } catch (IOException e){
            System.out.println(" Greska pri ucitavanju: " + e.getMessage());
        }
     }

     static void ucitajPodatke(){

        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("Studenti.dat"))){
            studenti =(ArrayList<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e){
            System.out.println(" Nema predhodnih podataka ili greska u ucitavanju ");
        }

     }

}