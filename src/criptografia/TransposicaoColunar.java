package src.criptografia;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import src.services.Arquivo;

public class TransposicaoColunar {
    public static void start() {
        System.out.println("============== CIFRAR POR COLUNA ==============");
        String[] nomes = Arquivo.getVetorDeNomesDoCsv();
        for(int i = 0; i < 10; i++) {
            System.out.println("Original : " + nomes[i]);
            System.out.println("criptografia: "); 
            build(nomes[i]);
            System.out.println("\n=======================");
        }
    }

	public static void build(String stringInput) {
		String treatedString = stringInput.replace(" ", "");
        int sqrtLength = (int) Math.ceil(Math.sqrt(treatedString.length()));
		List<String> stringsSeparatedList = new ArrayList<>();
		String[][] organizatedString = new String[sqrtLength][sqrtLength];
		List<String> encryptedList = new ArrayList<>();
		
		for(int i = 0; i < treatedString.length(); i++) {
			stringsSeparatedList.add(Character.toString(treatedString.charAt(i)));
		}
		
	        int indexK=0;
	        for(int i=0;i<organizatedString.length; i++) {
	            for(int j=0;j<organizatedString[i].length; j++) {
	            	if(indexK < treatedString.length()) {
	            		organizatedString[i][j] = stringsSeparatedList.get(indexK);
	            		indexK++;
	            	}
	            }
	        } 
        
	        for(int i=0; i<organizatedString.length; i++) {
	            for(int j=0; j<organizatedString[i].length; j++) {
	            	if(organizatedString[j][i] != null) {
	            		encryptedList.add(organizatedString[j][i]); 
	            	}   
	            }
	            encryptedList.add(" ");
	        } 
        
	        for(String encryptedResult : encryptedList) {
	        	System.out.print(encryptedResult);
	        }
        
	}
}