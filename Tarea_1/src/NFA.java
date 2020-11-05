
import java.util.LinkedList;


class NFA {
    
    private LinkedList<Estado> nfa;
    
    
    public NFA(){
        this.nfa = new LinkedList<Estado>();
        
    }

    public LinkedList<Estado> getNfa() {
        return nfa;
    }

    public void setNfa(LinkedList<Estado> nfa) {
        this.nfa = nfa;
    }

    
}
