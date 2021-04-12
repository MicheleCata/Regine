package it.polito.tdp.regine.model;

import java.util.ArrayList;
import java.util.List;

public class Regine {

	private int N; //è il numero di righe e colonne della scacchiera
	//   (righe e colonne numerate da 0 a N-1)
	// ad ogni livello posizioniamo una regina in una nuova riga
	
	// soluzione parziale: lista delle colonne in cui mettere le regine (prime righe)
	// 		List<Integer>
	// livello = quante righe sono già piene
	// livello = 0 => nessuna riga piena (devo mettere la regina nella riga 0)
	// livello = 3 => 3 righe piene (0, 1, 2), devo mettere la regina nella riga 3
	// [0]
	//     [0, 2]
	//            [0, 2, 1]
	
	private List<Integer> soluzione;
	
	public List<Integer> risolvi(Integer n) {
		this.N=n;
		
		List<Integer> parziale = new ArrayList<Integer>(); //ArrayList perchè la get ha tempo costante
		this.soluzione=null;
		
		cerca(parziale,0);
		
		return this.soluzione;
	}
	
	// cerca = true --> trovato; cerca = false --> continua a cercare
	private boolean cerca(List<Integer>parziale, int livello) {
		if(livello==N) {
			// caso terminale
			// System.out.println(parziale);
			this.soluzione=new ArrayList<>(parziale); // non ci serve il riferimento alla soluzione ma una copia
			return true;
		} else {
			for(int colonna=0; colonna<N; colonna++) { //Esempio: parziale = [0,6,4,7]
				// if la mossa nella casella [livello][colonna] è valida
				// se sì, aggiungi a parziale e fai ricorsione
				
				if (posValida(parziale, colonna)) {
					/* SOLUZIONE alternativa senza backtracking
					 * MENO EFFICIENTE in quanto richiede la copia di un'intera lista
					List<Integer> parzialeNuovo = new ArrayList<>(parziale);
					parzialeNuovo.add(colonna);
					cerca(parzialeNuovo, livello+1);
					*/
					parziale.add(colonna); //aggiungo un elemento al parziale = [0,6,4,7,1]
					boolean trovato = cerca(parziale, livello+1);
					if (trovato) //trovata una soluzione non bisogna più andare avanti poichè è sufficiente trovarne una
						return true;
					//Backtracking: per risalire di livello bisogna ripristinare la situazione precedente
					parziale.remove((parziale.size()-1));
				}
			}
			
			return false; // continuamo a cercare poichè non è stata trovata una soluzione valida
		}
	}

	private boolean posValida(List<Integer> parziale, int colonna) {
		int livello= parziale.size();
		// controlla se la regina viene mangiata in verticale
		if (parziale.contains(colonna))
			return false;
		// controllo su diagonale: confrontare la posizione (livello,colonna) con 
		// (r,c) delle regine esistenti
		for (int r=0; r<livello; r++) {
			
			int c=parziale.get(r);// ricavo la colonna associata a ciascuna soluzione parziale
			
			if (r+c == livello+colonna || r-c == livello -colonna)
				return false;
		}
		return true;
	}

	
	
	
	
}
