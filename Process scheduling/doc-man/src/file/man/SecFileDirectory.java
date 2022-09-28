package file.man;
import java.util.HashMap;

public class SecFileDirectory {
    private String name;
    private HashMap<String,FCB> directory = new HashMap<>();

    public SecFileDirectory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, FCB> getDirectory() {
        return directory;
    }

    public void setDirectory(HashMap<String, FCB> directory) {
        this.directory = directory;
    }
}
