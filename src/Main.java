import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Global.contor = 0;
//        citire din fisier a productiilor
        ArrayList<String> terminale = new ArrayList<>();
        ArrayList<String> neterminale = new ArrayList<>();
        ArrayList<Productie> productii;
        productii = citireFisier(terminale, neterminale);

//        generare matrice de precedenta
        MatricePrecedenta matricePrecedenta = new MatricePrecedenta();
        matricePrecedenta.generareMatrice(terminale, neterminale, productii);
        matricePrecedenta.afisareMatrice();

//        citire sir de intrare de la tastatura
        String sirIntrare = citireSirIntrare();
        System.out.println("Sir de intrare introdus: " + sirIntrare);

//        determinare daca sir intrare reprezinta o expresie matematica corecta
        Stack<String> stivaComparare = new Stack<>();
        stivaComparare.push("$");
        Stack<String> stivaAtribute = new Stack<>();
        System.out.println("Stiva          Sir Intrare");
        String ultimStivaComparare = "" + (stivaComparare.peek()).charAt(stivaComparare.peek().length() - 1);
        String primSirIntrare = "" + sirIntrare.charAt(0);
        int indexUltim = getIndex(ultimStivaComparare, neterminale, terminale);
        int indexPrim = getIndex(primSirIntrare, neterminale, terminale);
        String pivot;
        while (matricePrecedenta.verificareZero(indexUltim, indexPrim)) {
            ultimStivaComparare = "" + (stivaComparare.peek()).charAt(stivaComparare.peek().length() - 1);
            primSirIntrare = "" + sirIntrare.charAt(0);
            indexUltim = getIndex(ultimStivaComparare, neterminale, terminale);
            indexPrim = getIndex(primSirIntrare, neterminale, terminale);
            pivot = determinarePivot(stivaComparare, productii);
            String elemMatrice = matricePrecedenta.getElement(indexUltim, indexPrim);
            System.out.print(stivaComparare.peek() + "              " + sirIntrare);
            switch (elemMatrice) {
                case "<.":
                case "=.":
                    System.out.println("  -- Deplasare -- ");
                    sirIntrare = sirIntrare.substring(1);
                    stivaComparare.push(stivaComparare.peek() + primSirIntrare);
                    break;
                case ">.":
                    System.out.print("  -- Reducere --  ");
                    reducere(stivaComparare, stivaAtribute, pivot, terminale, productii);
                    break;
                case "acc":
                    System.out.println("\n -- Acceptare -- ");
                    break;
            }
            if (elemMatrice.equals("acc")) {
                break;
            }
        }
        if (matricePrecedenta.getElement(indexUltim, indexPrim).equals("0 ")) {
            System.out.println("\n  -- Eroare -- ");
        }
    }

    public static ArrayList<Productie> citireFisier(ArrayList<String> t, ArrayList<String> n) {
        try {
            File file = new File("./input");
            Scanner scanner = new Scanner(file);
            ArrayList<Productie> productii = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                if (data.equals("}")) {
                    break;
                }
                if (data.contains("T={")) {
                    separateArray(data, t, "T={,} ");
                    System.out.println("Terminale: " + t);
                } else {
                    if (data.contains("N={")) {
                        separateArray(data, n, "N={,} ");
                        System.out.println("Neterminale: " + n);
                    } else {
                        ArrayList<String> pf = new ArrayList<>();
                        Productie productie = new Productie();
                        separateArray(data, pf, "P={|, ");
                        String cheie = pf.get(0);
                        ArrayList<String> valori = new ArrayList<>();
                        for (int i = 0; i < pf.size() - 1; i++) {
                            valori.add(pf.get(i + 1));
                        }
                        productie.setCheie(cheie);
                        productie.setValori(valori);
                        productii.add(productie);
                        pf.clear();
                    }
                }
            }
            System.out.println("Productii: " + productii);
            return productii;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        return null;
    }

    public static void separateArray(String data, ArrayList<String> array, String separator) {
        StringTokenizer tokenizer = new StringTokenizer(data);
        while (tokenizer.hasMoreElements()) {
            data = tokenizer.nextToken(separator);
            array.add(data);
        }

    }

    public static String citireSirIntrare() {
        String sir = " ";
        while (sir.charAt(sir.length() - 1) != '$') {
            System.out.print("Introduceti sir intrare: ");
            Scanner scannerIn = new Scanner(System.in);
            sir = scannerIn.nextLine();
            if (sir.charAt(sir.length() - 1) != '$') {
                System.out.println("Sirul nu se termina cu caracterul $!");
            }
        }
        return sir;
    }

    public static int getIndex(String element, ArrayList<String> n, ArrayList<String> t) {
        if (element.equals("$"))
            return (n.size() + t.size());
        if (n.contains(element))
            return n.indexOf(element);
        if (t.contains(element))
            return (t.indexOf(element) + n.size());
        return -1;
    }

    public static String determinarePivot(Stack<String> s, ArrayList<Productie> productii) {
        String pivot = "";
        String sirAux = "";
        String peekStiva = s.peek();
        while (!peekStiva.equals("$")) {
            sirAux = peekStiva.charAt(peekStiva.length() - 1) + sirAux;
            for (Productie productie : productii) {
                ArrayList<String> valori = productie.getValori();
                for (String valoare : valori) {
                    if (valoare.equals(sirAux)) {
                        pivot = sirAux;
                        break;
                    }
                }
            }
            peekStiva = peekStiva.substring(0, peekStiva.length() - 1);
        }
        return pivot;
    }

    public static void reducere(Stack<String> sc, Stack<String> sa, String pivot, ArrayList<String> t, ArrayList<Productie> productii) {
        String peekStivaComp = sc.peek();
        String cheie = "";
        sc.pop();
        for (Productie productie : productii) {
            cheie = productie.getCheieByValoare(pivot);
            if (cheie != null) {
                break;
            }
        }
        System.out.println(cheie + "<-" + pivot);
        if (pivot.equals("a")) {
            sa.push(pivot);
        }
        String caracterPrimPivot = "" + pivot.charAt(0);
        if (caracterPrimPivot.equals("-")) {
            ArrayList<String> peekStivaAtribute = new ArrayList<>();
            peekStivaAtribute.add(sa.pop());
            String temp = creareTempDupaPivot(pivot, peekStivaAtribute, t);
            System.out.println("t" + (++Global.contor) + "=" + temp);
            sa.push("t" + (Global.contor));
        } else if (pivot.length() > 1 && !caracterPrimPivot.equals("(")) {
            ArrayList<String> caractereStivaAtribute = new ArrayList<>();
            caractereStivaAtribute.add(sa.pop());
            caractereStivaAtribute.add(sa.pop());
            String temp = creareTempDupaPivot(pivot, caractereStivaAtribute, t);
            System.out.println("t" + (++Global.contor) + "=" + temp);
            sa.push("t" + (Global.contor));
        }
        if (peekStivaComp.endsWith(pivot)) {
            peekStivaComp = peekStivaComp.substring(0, peekStivaComp.length() - pivot.length());
            peekStivaComp += cheie;
        }
        sc.push(peekStivaComp);
    }

    public static String creareTempDupaPivot(String pivot, ArrayList<String> elementeStiva, ArrayList<String> t) {
        String temp = pivot;
        String caracter;
        int k = elementeStiva.size() - 1;
        for (int i = 0; i < pivot.length(); i++) {
            caracter = "" + pivot.charAt(i);
            if (!t.contains(caracter)) {
                temp = temp.replace(caracter, elementeStiva.get(k--));
            }
        }
        if (temp.contains("(") || temp.contains(")")) {
            temp = temp.replace("(", "");
            temp.replace(")", "");
        }
        return temp;
    }
}