import java.util.ArrayList;
import java.util.HashSet;

public class MatricePrecedenta {
    String[][] m;

    public String[][] getM() {
        return m;
    }

    public void setM(String[][] m) {
        this.m = m;
    }

    public void generareMatrice(ArrayList<String> t, ArrayList<String> n, ArrayList<Productie> productii) {
        int capacitate = t.size() + n.size() + 1;
        String[][] mp = new String[capacitate][capacitate];
        for (int i = 0; i < mp.length; i++) {
            for (int j = 0; j < mp[0].length; j++) {
                if (mp[i][j] == null) {
                    mp[i][j] = "0 ";
                }
            }
        }
        for (Productie productie : productii) {
            ArrayList<String> valori = productie.getValori();
            for (String valoare : valori) {
                if (valoare.length() > 1) {
                    generareActiuni(valoare, mp, t, n, productii);
                }
            }
        }
        this.m = mp;
    }

    private void generareActiuni(String valoare, String[][] mp, ArrayList<String> t, ArrayList<String> n, ArrayList<Productie> productii) {
        int indexPrim, indexUrm;
        String caracterPrim, caracterUrm;
        ArrayList<String> listaActiuni = new ArrayList<>();
        for (int i = 0; i < (valoare.length() - 1); i++) {
            caracterPrim = "" + valoare.charAt(i);
            indexPrim = getIndex(caracterPrim, n, t);
            caracterUrm = "" + valoare.charAt(i + 1);
            indexUrm = getIndex(caracterUrm, n, t);
            if (n.contains(caracterPrim)) {
                listaActiuni = tfin(caracterPrim, productii, n);
                for (String str : listaActiuni) {
                    mp[getIndex(str, n, t)][indexUrm] = ">.";
                }
            }
            listaActiuni.clear();
            if (t.contains(caracterPrim)) {
                listaActiuni = tini(caracterUrm, productii, n);
                for (String str : listaActiuni) {
                    mp[indexPrim][getIndex(str, n, t)] = "<.";
                }
            }
            listaActiuni.clear();
            mp[indexPrim][indexUrm] = "=.";
        }

        indexPrim = getIndex("$", n, t);
        indexUrm = getIndex("E", n, t);
        listaActiuni = tfin("E", productii, n);
        for (String str : listaActiuni) {
            mp[getIndex(str, n, t)][indexPrim] = ">.";
        }
        listaActiuni.clear();
        listaActiuni = tini("E", productii, n);
        for (String str : listaActiuni) {
            mp[indexPrim][getIndex(str, n, t)] = "<.";
        }
        mp[indexPrim][indexUrm] = "=.";
        mp[indexUrm][indexPrim] = "acc";
    }

    private static ArrayList<String> tini(String element, ArrayList<Productie> p, ArrayList<String> n) {
        ArrayList<String> listaFinala = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
        String cheie;
        for (Productie productie : p) {
            cheie = productie.getCheie();
            ArrayList<String> valori = productie.getValori();
            if (cheie.equals(element)) {
                for (String valoare : valori) {
                    listaFinala.add("" + valoare.charAt(0));
                }
            }
        }
        for (String item : listaFinala) {
            if (!item.equals(element) && n.contains(element)) {
                aux = tini(item, p, n);
            }
        }
        listaFinala.addAll(aux);
        HashSet<String> hashSet = new HashSet<>(listaFinala);
        listaFinala.clear();
        listaFinala.addAll(hashSet);
        return listaFinala;
    }

    private static ArrayList<String> tfin(String element, ArrayList<Productie> p, ArrayList<String> n) {
        ArrayList<String> listaFinala = new ArrayList<>();
        ArrayList<String> aux = new ArrayList<>();
        String cheie;
        for (Productie productie : p) {
            cheie = productie.getCheie();
            ArrayList<String> valori = productie.getValori();
            if (cheie.equals(element)) {
                for (String valoare : valori) {
                    listaFinala.add("" + valoare.charAt(valoare.length() - 1));
                }
            }
        }
        for (String item : listaFinala) {
            if (!item.equals(element) && n.contains(element)) {
                aux = tfin(item, p, n);
            }
        }
        listaFinala.addAll(aux);
        HashSet<String> hashSet = new HashSet<>(listaFinala);
        listaFinala.clear();
        listaFinala.addAll(hashSet);
        return listaFinala;
    }

    private int getIndex(String element, ArrayList<String> n, ArrayList<String> t) {
        if (element.equals("$"))
            return (n.size() + t.size());
        if (n.contains(element))
            return n.indexOf(element);
        if (t.contains(element))
            return (t.indexOf(element) + n.size());
        return -1;
    }

    public void afisareMatrice() {
        System.out.println("Matrice de precedenta generata:");
        for (int i = 0; i < this.m.length; i++) {
            for (int j = 0; j < this.m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean verificareZero(int i, int j) {
        if (this.m[i][j] == "0 ") {
            return false;
        }
        return true;

    }

    public String getElement(int i, int j) {
        return this.m[i][j];
    }
}
