package file.man;

import java.util.ArrayList;
import java.util.HashMap;

public class FileDirectory {
    private String name;
    private HashMap<String,SecFileDirectory> directory = new HashMap<>();

    public FileDirectory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, SecFileDirectory> getDirectory() {
        return directory;
    }

    public void setDirectory(HashMap<String, SecFileDirectory> directory) {
        this.directory = directory;
    }
}
