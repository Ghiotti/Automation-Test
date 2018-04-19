import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;
import sun.nio.cs.UTF_32;


import java.io.*;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;


@RunWith(JUnitParamsRunner.class)
public class ScriptTests {

    @Parameters(method = "getScripts")
    @Test
    public void getScriptTest(File file) {
        int resultado = executeScript(file);
        Assert.assertEquals(0, resultado);
    }

    public List getScripts() {
        return loadScripts("/home/fghiotti/Incluit/scripts/Test");
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

    public int executeScript(File script) {
        String [] scriptName = {script.getAbsolutePath()};
        Runtime r = Runtime.getRuntime();
        int resultado;
        try {
            Process p = r.exec(scriptName);
            StringWriter writer = new StringWriter();
            p.waitFor();
            resultado = p.exitValue();
            org.apache.commons.io.IOUtils.copy(p.getInputStream(), writer);
            String theString = writer.toString();
            System.out.println(theString);
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
            resultado = 1;
        }
        return resultado;
    }
}