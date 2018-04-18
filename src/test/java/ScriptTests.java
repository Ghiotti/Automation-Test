import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;


@RunWith(JUnitParamsRunner.class)
public class ScriptTests {

    @Test
    @Parameters(method = "getScripts")
    public void getScriptTest(File file) {
        executeScript(file);

    }

    public List getScripts() {
        return loadScripts("C:\\Users\\FGHIOTTI\\Desktop\\Test");
    }

    public List loadScripts(String dir) {
        File file = new File(dir);
        File listFile[] = file.listFiles();
        List <File> scripts  = new ArrayList();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    scripts.addAll(loadScripts(listFile[i].getAbsolutePath()));
                } else if (listFile[i].getName().contains(".sh")) {
                    scripts.add(listFile[i]);
                    return scripts;
                }
            }
        }
        return scripts;
    }

    public void executeScript(File script) {
        String asd = "C:\\Program Files\\Git\\git-bash.exe -c " + script.getAbsolutePath();
        try {
            Process pb = new Runtime.getRuntime().exec(asd);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }
}