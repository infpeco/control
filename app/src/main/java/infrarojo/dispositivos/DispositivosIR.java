package infrarojo.dispositivos;

import java.util.ArrayList;
import java.util.List;

public class DispositivosIR {
    private int id;
    private String name;
    private List<FuncionIR> functions = new ArrayList<>();

    public DispositivosIR(int id, String name) {
        this.id = id;
        this.name = name == null || name.trim().length() <= 0 ? "D:" + id : name.trim();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<FuncionIR> getFunctions() {
        return functions;
    }

    public void addFunction(FuncionIR function) {
        if (function != null)
            functions.add(function);
    }
}
