package br.ucsal.moduloClienteAE4.uteis;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ComandoSO { 
    public static void main(String [] args) throws IOException {
        
        String[] command = {"CMD", "/C", "route", "print"};
        ProcessBuilder probuilder = new ProcessBuilder( command );
        //You can set up your work directory
        probuilder.directory(new java.io.File("c:\\windows"));
        
        Process process = probuilder.start();
        
        //Read out dir output
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        
        StringBuffer resultadoComando = new StringBuffer();
        while ((line = br.readLine()) != null) {
        	resultadoComando.append(line + "\n");
        	//fas = fas + line + "\n";
        }
        resultadoComando.replace(0, resultadoComando.indexOf("Rotas ativas:"), "");
        resultadoComando.replace(0 , resultadoComando.indexOf("Endere"), "");
        int PosicaoInterface =   resultadoComando.indexOf(" Interface  ");
        resultadoComando.replace(0, resultadoComando.indexOf("          0.0.0.0          0.0.0.0"), "");
        resultadoComando.replace(0, PosicaoInterface, "");
        System.out.println(resultadoComando.substring(0, 15).trim());
        //resultadoComando.
		//System.out.println(fas);
        //System.out.println(resultadoComando);
        //Wait to get exit value
        /*
        try {
            int exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }
}