import java.util.ArrayList;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Student implements Serializable {

    private String ime;
    private String prezime;
    private String brojIndeksa;
    private Map<String,ArrayList<Integer>> ocenePoPredmetima;


    public Student(String ime, String prezime, String brojIndeksa) {
        this.ime = ime;
        this.prezime = prezime;
        this.brojIndeksa = brojIndeksa;
        this.ocenePoPredmetima = new HashMap<>();
    }

    public void dodajOcenu(String predmet, int ocena) {
        ocenePoPredmetima.putIfAbsent(predmet, new ArrayList<>());
        ocenePoPredmetima.get(predmet).add(ocena);
    }

    public double izracunajProsek() {

        int suma = 0;
        int brojOcena = 0;

        for (ArrayList<Integer> ocene : ocenePoPredmetima.values()) {
            for (int ocena : ocene) {
                suma += ocena;
                brojOcena++;
            }
        }
        return brojOcena == 0 ? 0 : (double) suma / brojOcena;
    }


    public String getBrojIndeksa() {
        return brojIndeksa;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public double getProsek() {
        return izracunajProsek();
    }

    public String getOcenePoPredmetimaCSV(){

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String,ArrayList<Integer>> entry : ocenePoPredmetima.entrySet()){
            sb.append(entry.getKey()).append(": ");
            for (int ocena : entry.getValue()){
                sb.append(ocena).append(" ");
            }
            sb.append(" | ");
        }
        return sb.toString();

    }




    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(ime).append(" ").append(prezime).append("(").append(brojIndeksa).append(")\n");
        sb.append(" Prosek: ").append(String.format("%.2f",izracunajProsek())).append("\n");
        sb.append(" Ocene po predmetima: \n");

        for (Map.Entry<String, ArrayList<Integer>> entry : ocenePoPredmetima.entrySet()){
            sb.append("---").append(entry.getKey()).append(": ");
            for (int ocena : entry.getValue()){
                sb.append(ocena).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
}
    }
