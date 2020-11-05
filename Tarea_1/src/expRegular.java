

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class expRegular {

    private int NumEstado;
    private Stack<NFA> stackNfa = new Stack<>();
    private Stack<Character> operador = new Stack<>();
    private Set<Estado> conjunto1 = new HashSet<>();
    private Set<Estado> conjunto2 = new HashSet<>();
    private ArrayList<Character> sigma = new ArrayList<Character>();

    public NFA gererarNFA(String regular) {

        stackNfa.clear();
        operador.clear();

        for (int i = 0; i < regular.length(); i++) {
            if (letraEntrada(regular.charAt(i))) {
                pushStack(regular.charAt(i));
            } else if (operador.isEmpty()) {
                operador.push(regular.charAt(i));
            } else if (regular.charAt(i) == '(') {
                operador.push(regular.charAt(i));
            } else if (regular.charAt(i) == ')') {
                while (operador.get(operador.size() - 1) != '(') {
                    hacerOperacion();
                }
                operador.pop();
            } else {
                while (!operador.isEmpty() && prioridadOperacion(regular.charAt(i), 
                        operador.get(operador.size() - 1))) {
                    hacerOperacion();
                }
                operador.push(regular.charAt(i));
            }
        }

        // limpia los elementos restantesd de la pila.
        while (!operador.isEmpty()) {
            hacerOperacion();
        }

        NFA terminadoNfa = stackNfa.pop();

        terminadoNfa.getNfa().get(terminadoNfa.getNfa().size() - 1).setEstadoFinal(true);

        return terminadoNfa;
    }

    public void pushStack(char simbolo) {

        Estado s0 = new Estado(NumEstado++);
        Estado s1 = new Estado(NumEstado++);

        s0.agregarTransicion(s1, simbolo);

        NFA nfa = new NFA();

        nfa.getNfa().addLast(s0);
        nfa.getNfa().addLast(s1);

        stackNfa.push(nfa);

    }

    public boolean prioridadOperacion(char primero, char segundo) {

        if (primero == segundo) {
            return true;
        }
        if (primero == '*') {
            return false;
        }
        if (segundo == '*') {
            return true;
        }
        if (primero == '.') {
            return false;
        }
        if (segundo == '.') {
            return true;
        }
        if (primero == '|') {
            return false;
        } else {
            return true;
        }
    }

    public void hacerOperacion() {

        if (this.operador.size() > 0) {
            char letra = operador.pop();

            switch (letra) {

                case ('|'):
                    union();
                    break;

                case ('.'):
                    concatenacion();
                    break;

                case ('*'):
                    clausuraKleene();
                    break;

                default:
                    System.out.println("simbolo invalido");
                    break;

            }
        }
    }

    public LinkedList imprimirSigma(String regular) {

        LinkedList nueva = new LinkedList<>();
        for (int i = 0; i < regular.length(); i++) {
            if (letraEntrada(regular.charAt(i))) {
                nueva.add(regular.charAt(i));
            }
        }
        //eliminar repetidos
        HashSet hs = new HashSet();
        hs.addAll(nueva);
        nueva.clear();
        nueva.addAll(hs);
        return nueva;
    }

    private boolean letraEntrada(char i) {
        switch (i) {
            case 'a':
                return true;
            case 'b':
                return true;
            case 'c':
                return true;
            case 'd':
                return true;
            case 'e':
                return true;
            case 'f':
                return true;
            case 'g':
                return true;
            case 'h':
                return true;
            case 'i':
                return true;
            case 'j':
                return true;
            case 'k':
                return true;
            case 'l':
                return true;
            case 'm':
                return true;
            case 'n':
                return true;
            case 'o':
                return true;
            case 'p':
                return true;
            case 'q':
                return true;
            case 'r':
                return true;
            case 's':
                return true;
            case 't':
                return true;
            case 'u':
                return true;
            case 'v':
                return true;
            case 'w':
                return true;
            case 'x':
                return true;
            case 'y':
                return true;
            case 'z':
                return true;
            case 'A':
                return true;
            case 'B':
                return true;
            case 'C':
                return true;
            case 'D':
                return true;
            case 'E':
                return true;
            case 'F':
                return true;
            case 'G':
                return true;
            case 'H':
                return true;
            case 'I':
                return true;
            case 'J':
                return true;
            case 'K':
                return true;
            case 'L':
                return true;
            case 'M':
                return true;
            case 'N':
                return true;
            case 'O':
                return true;
            case 'P':
                return true;
            case 'Q':
                return true;
            case 'R':
                return true;
            case 'S':
                return true;
            case 'T':
                return true;
            case 'U':
                return true;
            case 'V':
                return true;
            case 'W':
                return true;
            case 'X':
                return true;
            case 'Y':
                return true;
            case 'Z':
                return true;
            case '0':
                return true;
            case '1':
                return true;
            case '2':
                return true;
            case '3':
                return true;
            case '4':
                return true;
            case '5':
                return true;
            case '6':
                return true;
            case '7':
                return true;
            case '8':
                return true;
            case '9':
                return true;
            case '_':
                return true;
            case '~':
                return true;
            default:
                return false;
        }
    }

    public void clausuraKleene() {
        NFA nfa = stackNfa.pop(); // obtiene el elemento de la pila y lo elimina a la vez.

        // crea los dos estados para la operacion estrella
        Estado comienzo = new Estado(NumEstado++);
        Estado fin = new Estado(NumEstado++);

        // agrega la transicion del fin hasta el comienzo mediante un epsilon.
        comienzo.agregarTransicion(fin, '_');
        comienzo.agregarTransicion(nfa.getNfa().getFirst(), '_');

        nfa.getNfa().getLast().agregarTransicion(fin, '_');
        nfa.getNfa().getLast().agregarTransicion(nfa.getNfa().getFirst(), '_');

        nfa.getNfa().addFirst(comienzo);
        nfa.getNfa().addLast(fin);

        stackNfa.push(nfa); // coloca en nfa denuevo en la pila.
    }

    public void concatenacion() {
        // obtiene elementos de la pila y los elimina.

        NFA nfa2 = stackNfa.pop();
        NFA nfa1 = stackNfa.pop();

        // agrega la transicion desde el final del nfa1 con el comienzo del nfa2 mediante un epsilon.
        nfa1.getNfa().getLast().agregarTransicion(nfa2.getNfa().getFirst(), '_');

        // agregar todos los estados en nfa2 al final de  nfa1.
        agregarEstados(nfa1, nfa2);

        // pone el nfa1 denuevo en stacknfa.
        stackNfa.push(nfa1);

    }

    public void union() {

        NFA nfa2 = stackNfa.pop();
        NFA nfa1 = stackNfa.pop();

        // crear estados para la operacion de union.
        Estado comienzo = new Estado(NumEstado++);
        Estado fin = new Estado(NumEstado++);

        // hacemos una transicion al comienzo  de cada sub NFA con un epsilon.
        comienzo.agregarTransicion(nfa1.getNfa().getFirst(), '_');
        comienzo.agregarTransicion(nfa2.getNfa().getFirst(), '_');

        // hacemos una transicion al final de cada sub NFA con una cadena vacia.
        nfa1.getNfa().getLast().agregarTransicion(fin, '_');
        nfa2.getNfa().getLast().agregarTransicion(fin, '_');

        // agregamos el inicio al final de cada  nfa.
        nfa1.getNfa().addFirst(comienzo);
        nfa2.getNfa().addLast(fin);

        // agregar todos los estados en nfa2 al final de  nfa1.
        agregarEstados(nfa1, nfa2);

        // pone el nfa1 denuevo en stacknfa.
        stackNfa.push(nfa1);

    }

    public void agregarEstados(NFA nfa1, NFA nfa2) {

        for (Estado s : nfa2.getNfa()) {
            nfa1.getNfa().addLast(s);
        }
    }

    public AFD GenerarAFD(NFA nfa, String regular) {
        for (int i = 0; i < regular.length(); i++) {
            if (letraEntrada(regular.charAt(i)) && regular.charAt(i) != '_') {
                sigma.add(regular.charAt(i));
            }

        }
        //quitar repetidos
        HashSet hs = new HashSet();
        hs.addAll(sigma);
        sigma.clear();
        sigma.addAll(hs);
        // crear el AFD
        AFD afd = new AFD();

        // limpiar las id de estados
        this.NumEstado = 0;

        // Crear un arrayList de estados sin procesar
        LinkedList<Estado> estados_sin_revisar = new LinkedList<Estado>();

        // Create sets
        this.conjunto1 = new HashSet<Estado>();
        this.conjunto2 = new HashSet<Estado>();

        // agregar el primer estado al conjunto de estados
        conjunto1.add(nfa.getNfa().getFirst());

        // revisar todos los estados que corren con epsilon
        verTransicionesEpsilon();

        // crear un estado de inicio del dfa y lo agregamos
        Estado dfaStart = new Estado(this.conjunto2, this.NumEstado++);

        afd.getAFD().addLast(dfaStart);
        estados_sin_revisar.addLast(dfaStart);

        // mientras halla elementos sin revisar
        Set<Estado> sumidero = new HashSet<Estado>();
        Estado pe = null;
        int x = 0;
        while (!estados_sin_revisar.isEmpty()) {
            // revisar y eliminar el último estado en la pila
            Estado estado = estados_sin_revisar.removeLast();

            // Comprobar si pertenece a sigma
            for (Character simbolo : sigma) {
                conjunto1 = new HashSet<Estado>();
                conjunto2 = new HashSet<Estado>();

                conjunto1 = buscarTransicionesDeConjunto(simbolo, estado.getEstados());

                verTransicionesEpsilon();

                boolean encontro = false;
                Estado st = null;
                for (int i = 0; i < afd.getAFD().size(); i++) {
                    st = afd.getAFD().get(i);
                    if(st.getEstados().containsAll(conjunto2) && x==1 && conjunto2.isEmpty()){
                        
                        encontro=true;
                        st=pe;
                        break;
                    }
                    if (st.getEstados().containsAll(conjunto2)) {
                        encontro = true;
                        break;
                    }

                }


                // si no esta en el afd se agrega
                if (!encontro) {
                    Estado p = new Estado(conjunto2, this.NumEstado++);
                    estados_sin_revisar.addLast(p);
                    afd.getAFD().addLast(p);
                    estado.agregarTransicion(p, simbolo);

                    // si es que ya esta en el afd
                } else if(encontro == true && x==0){
                    pe = new Estado(sumidero,-1);
                    afd.getAFD().addLast(pe);
                    estados_sin_revisar.addLast(pe);
                    estado.agregarTransicion(pe, simbolo);
                    x++;
                } else {
                    estado.agregarTransicion(st, simbolo);
                }

            }
        }
        //retornar el afd
        return afd;
    }

    //Este metodo se encarga de buscar las transiciones de los estados de un conjunto
    //con un simbolo determinado de sigma. y retorna el conjunto al que hace transicion con el simbolo
    //determinado.
    private Set<Estado> buscarTransicionesDeConjunto(Character c, Set<Estado> estados) {
        ArrayList<Estado> temp = new ArrayList<Estado>();
        Set<Estado> conjunto = new HashSet<Estado>();

        for (Estado st : estados) {
            temp.add(st);
        }
        for (Estado st : temp) {
            ArrayList<Estado> estados_todos = st.getAllTransitions(c);
            
            for (Estado p : estados_todos) {
                conjunto.add(p);
            }
        }

        return conjunto;
    }

    // Este método se encarga de buscar las transiciones épsilon de cada 
    // uno de los estados de un conjunto de estados 
    //y añadirlo a dicho conjunto si es que no pertenece.
    private void verTransicionesEpsilon() {
        Stack<Estado> stack_aux = new Stack<Estado>();
        conjunto2 = conjunto1;// se copia el conjunto

        for (Estado st : conjunto1) {// le pasamos los estados del conjunto a una pila
            stack_aux.push(st);
        }
        //recorremos la pila encontrando las transiciones epsilon de cada estado
        // y se agrega al conjunto de estados si es que no esta en el conjunto
        while (!stack_aux.isEmpty()) {
            Estado st = stack_aux.pop();
            ArrayList<Estado> estadosEpsilon = st.getAllTransitions('_');
            
            for (Estado p : estadosEpsilon) {
                
                // Probar que p esta en el conjunto de lo contrario Añadirlo
                if (!conjunto2.contains(p)) {                    
                    conjunto2.add(p);
                    stack_aux.push(p);

                }
                
            }
        }
        
    }
}
