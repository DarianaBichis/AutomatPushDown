import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class Productie {
    String cheie;
    ArrayList<String> valori = new ArrayList<>();

    public String getCheie() {
        return cheie;
    }

    public void setCheie(String cheie) {
        this.cheie = cheie;
    }

    public ArrayList<String> getValori() {
        return valori;
    }

    public void setValori(ArrayList<String> valori) {
        this.valori = valori;
    }

    @Override
    public String toString() {
        return cheie + "->" + valori;
    }

    public String getCheieByValoare(String val) {
        if (this.valori.contains(val)) {
            return this.cheie;
        }
        return null;
    }

}
