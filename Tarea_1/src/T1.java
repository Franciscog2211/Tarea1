

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T1 {

    private String regular;
    private NFA nfa;
    private AFD afd;
    private LinkedList<Character> nueva = new LinkedList<Character>();
    private ArrayList<Integer> ocurrencias = new ArrayList<Integer>();

    public static void main(String[] args) {
        T1 malf = new T1();
        malf.leer(args);
    }

    private void leer(String[] args) {
        Scanner in = new Scanner(System.in);
        regular = in.nextLine();
        String consulta = in.nextLine();
        expRegular enlace = new expRegular();
        setNfa(enlace.gererarNFA(regular));
        this.setAfd(enlace.GenerarAFD(nfa, regular));
        System.out.println(" ");
        imprimir();
        System.out.println(" ");
        this.imprimirAFD();
        this.imprimirDeltaAFD();
        this.imprimirSandF_AFD();
        System.out.println(" ");
        System.out.println("Ocurrencias:");
        this.ocurrencia_automata(consulta, afd);

    }

    public void imprimirDelta() {
        System.out.println("Delta: ");
        for (int j = 0; j < nfa.getNfa().size(); j++) {
            Iterator<Entry<Character, ArrayList<Estado>>> it = nfa.getNfa().get(j).getEstadoSiguiente().entrySet().iterator();
            while (it.hasNext()) {
                Entry<Character, ArrayList<Estado>> e = it.next();
                for (int k = 0; k < e.getValue().size(); k++) {
                    System.out.println("(q" + nfa.getNfa().get(j).getNumEstado() + "," + e.getKey() + ",q" + e.getValue().get(k).getNumEstado() + ")");
                }
            }
        }
    }

    public void imprimir_K() {

        System.out.print("K= ");
        System.out.print("{");
        for (int i = 0; i < nfa.getNfa().size(); i++) {
            System.out.print("q" + nfa.getNfa().get(i).getNumEstado());
            if (i < nfa.getNfa().size() - 1) {
                System.out.print(",");
            }
        }
        System.out.print("}");
    }

    public void imprimir_S() {
        System.out.print("s= ");
        System.out.print("q" + nfa.getNfa().get(0).getNumEstado());

    }

    public void imprimir_F() {

        int numero = nfa.getNfa().size() - 1;
        System.out.println("");
        System.out.print("F=");
        System.out.print("{q" + nfa.getNfa().get(numero).getNumEstado() + "}");
        System.out.println("");

    }

    public void imprimir() {

        System.out.println("AFND: ");
        imprimir_K();
        expRegular enlace = new expRegular();
        System.out.println("");
        nueva = enlace.imprimirSigma(regular);
        System.out.print("Sigma= {");
        for (int i = 0; i < nueva.size(); i++) {
            System.out.print(nueva.get(i));
            if (i < nueva.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}");
        imprimirDelta();
        imprimir_S();
        imprimir_F();
    }

    public NFA getNfa() {
        return nfa;
    }

    public void setNfa(NFA nfa) {
        this.nfa = nfa;
    }

    public void imprimirAFD() {
        System.out.println("AFD");
        System.out.print("K={");
        for (int j = 0; j < afd.getAFD().size(); j++) {
            System.out.print("q" + afd.getAFD().get(j).getNumEstado());
            if (j == afd.getAFD().size() - 1) {
            } else {
                System.out.print(",");
            }
        }
        System.out.println("}");
        expRegular enlace = new expRegular();
        LinkedList nueva = enlace.imprimirSigma(regular);
        System.out.print("Sigma= {");
        for (int i = 0; i < nueva.size(); i++) {
            System.out.print(nueva.get(i));
            if (i < nueva.size() - 1) {
                System.out.print(",");
            }
        }
        System.out.println("}");

    }

    public void imprimirDeltaAFD() {
        System.out.println("Delta:");
        for (int j = 0; j < afd.getAFD().size(); j++) {
            Iterator<Entry<Character, ArrayList<Estado>>> it = afd.getAFD().
                    get(j).getEstadoSiguiente().entrySet().iterator();
            while (it.hasNext()) {
                Entry<Character, ArrayList<Estado>> e = it.next();
                for (int k = 0; k < e.getValue().size(); k++) {
                    if (afd.getAFD().get(j).getNumEstado() == -1 && e.getValue().get(k).getNumEstado() == -1) {
                        System.out.println("(" + afd.getAFD().get(j).getNumEstado() + ","
                                + e.getKey() + "," + e.getValue().get(k).getNumEstado() + ")");
                    } else if (e.getValue().get(k).getNumEstado() == -1) {
                        System.out.println("(q" + afd.getAFD().get(j).getNumEstado() + ","
                                + e.getKey() + "," + e.getValue().get(k).getNumEstado() + ")");
                    } else {
                        System.out.println("(q" + afd.getAFD().get(j).getNumEstado() + ","
                                + e.getKey() + ",q" + e.getValue().get(k).getNumEstado() + ")");
                    }

                }

            }
        }
    }

    public void imprimirSandF_AFD() {
        System.out.println("s=q" + afd.getAFD().getFirst().getNumEstado());
        System.out.print("F={");
        for (int i = 0; i < afd.getAFD().size(); i++) {
            if (afd.getAFD().get(i).isEstadoFinal()) {
                System.out.print("q" + afd.getAFD().get(i).getNumEstado());
            }
            if (i == afd.getAFD().size() - 1 && afd.getAFD().get(i).isEstadoFinal()) {
            } else if (afd.getAFD().get(i).isEstadoFinal()) {
                System.out.print(",");
            }
        }
        System.out.println("}");
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public void setAfd(AFD afd) {
        this.afd = afd;
    }

    //Metodo para encontrar ocurrencias con el automata AFD
    public void ocurrencia_automata(String cadena, AFD afd) {
        Estado e = afd.getAFD().getFirst();
        if (cadena.isEmpty()) {
            System.out.println("La cadena esta vacia y no tiene ocurrencia");
        }
        boolean hay_estrella = false;
        for (int i = 0; i < regular.length(); i++) {
            if (regular.charAt(i) == '*') {
                hay_estrella = true;
            }
        }
        ArrayList<Character> aux = new ArrayList<>();
        int pos = 1;
        int k = 0;
        int par=0;
        ArrayList<Character> temp = new ArrayList<>();
        boolean sigma = true;
        if (afd.getAFD().size() > 0) {
            while (pos <= cadena.length()) {

                int tem = k;
                while (k < pos) {
                    aux.add(cadena.charAt(k));
                    k++;

                }
                k = tem;
                for (int j = 0; j < aux.size(); j++) {
                    // No hay transicion por lo que se termina el AFD y ademas seria cadena invalida
                    if (e == null) {
                        System.out.println("no sirve");
                        break;
                    }
                    //obtener la transicion siguente con el caracter
                    if (nueva.contains(aux.get(j))) {
                        e = e.getEstadoSiguiente().get(aux.get(j)).get(0);

                    } else {
                        sigma = false;
                    }
                }
                //si es estado final y pertenece a sigma es ocurrencia
                if (e.isEstadoFinal() && sigma == true) {
                    
                    ocurrencias.add(tem);
                    ocurrencias.add(pos - 1);
                    
                    int contador = 0;
                    if (temp.size() == aux.size()) {
                        for (int i = 0; i < aux.size(); i++) {
                            if (aux.get(i) == temp.get(i)) {
                                contador++;
                            }
                        }
                    }else{
                        contador=-1;
                    }
                    if (ocurrencias.size() > 2 && (contador!=aux.size() || contador==-1) && hay_estrella == true) {
                        ocurrencias.add(par,-1);
                        par+=1;
                    }/*else if(ocurrencias.size()==2 && hay_estrella == true){
                        ocurrencias.add(par,-1);
                    }*/
                    k = pos;
                    par+=2;
                    pos++;
                    e = afd.getAFD().getFirst();
                    temp.clear();

                } else if (e.getNumEstado() == -1 && sigma == true) {
                    pos++;
                    e = afd.getAFD().getFirst();
                } else if (sigma == false) {
                    k++;
                    pos++;
                    e = afd.getAFD().getFirst();
                } else {
                    k = tem;
                    pos++;
                    e = afd.getAFD().getFirst();
                }
                temp.clear();
                for (int i = 0; i < aux.size(); i++) {
                    temp.add(aux.get(i));
                }
                sigma = true;
                aux.clear();

            }
            if (hay_estrella == true) {

                int i = 1;
                while (i < ocurrencias.size() - 1) {
                    int sum = ocurrencias.get(i) + 1;
                    int sum1 = ocurrencias.get(i + 1);
                    if (sum == 0) {
                        ocurrencias.remove(i);
                        i+=2;
                    } else if (sum1 == -1) {
                        ocurrencias.remove(i + 1);
                        i+=2;
                    } else if (sum == sum1 && i + 1 <= ocurrencias.size()) {
                        ocurrencias.remove(i);
                        ocurrencias.remove(i);
                    } else {
                        i += 2;
                    }

                }
                for (int j = 0; j < ocurrencias.size(); j += 2) {
                    System.out.println(ocurrencias.get(j) + " " + ocurrencias.get(j + 1));
                }
            } else {
                for (int j = 0; j < ocurrencias.size(); j += 2) {
                    System.out.println(ocurrencias.get(j) + " " + ocurrencias.get(j + 1));
                }
            }
        }
    }

    public AFD getAfd() {
        return afd;
    }

    public String getLinea() {
        return regular;
    }

    public LinkedList getNueva() {
        return nueva;
    }

}
