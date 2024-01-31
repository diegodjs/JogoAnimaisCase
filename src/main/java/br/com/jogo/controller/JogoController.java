package br.com.jogo.controller;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.jogo.domain.NoArvoreBinaria;
import br.com.jogo.repository.SQLite;

public class JogoController {
	
	private static NoArvoreBinaria raiz;
	
	private static int index = 1;
	
	private SQLite SQLite;
	
	private int parentTmp = 0;
	private NoArvoreBinaria noArvoreBinariaEncontrado = new NoArvoreBinaria();
	
	public JogoController(){
		
		JOptionPane.showMessageDialog(null, "Pense em um Animal");
		SQLite = new SQLite();
		
		//Busca na Base a lista
		ArrayList<NoArvoreBinaria> list = SQLite.buscaNoInicioArvoreBinaria();
		
		if (list.isEmpty()) {
			
			//Se a lista estiver vazia Cria nos para iniciar as perguntas
			
			int id = index;
			String valor = "vive na agua";
			
			this.setArvore(new NoArvoreBinaria(id, valor, 1));
			SQLite.criaNoArvoreBinaria(id, valor, 1);
			
			id = ++index;
			valor = "Macaco";
			this.getArvore().noEsquerdo = new NoArvoreBinaria(id, valor, 1);
			SQLite.criaNoArvoreBinaria(id, valor, 1);

			
			id = ++index;
			valor = "Tubarao";
			this.getArvore().noDireito = new NoArvoreBinaria(id, valor, 1);
			SQLite.criaNoArvoreBinaria(id, valor, 1);
		}
		else {
			
			NoArvoreBinaria noArvoreBinaria = null;
			
			for (int i=0; i<list.size(); i++) {
				
				if (noArvoreBinaria == null) {
					
					noArvoreBinaria = list.get(i);
					noArvoreBinaria.noEsquerdo = list.get(++i);
					noArvoreBinaria.noDireito = list.get(++i);
					
					this.setArvore(noArvoreBinaria);
				}
				else {
					
					//Percorre a lista carregando a arvore e seus nos
					int iaux = i;
					NoArvoreBinaria noArvoreBinariaCorrente = list.get(iaux);
					
					//procura o no correspondente para colocar os nos da direita e da esquerda
					noArvoreBinaria = this.buscaNo(noArvoreBinariaCorrente.parent, noArvoreBinaria);
					
					NoArvoreBinaria noArvoreBinariaDireito = list.get(iaux);
					noArvoreBinaria.noDireito =  new NoArvoreBinaria(noArvoreBinariaDireito.elemento, noArvoreBinariaDireito.valor, noArvoreBinariaDireito.parent);
					
					iaux = ++i;
					NoArvoreBinaria noArvoreBinariaEsquerdo = list.get(iaux);
					noArvoreBinaria.noEsquerdo =  new NoArvoreBinaria(noArvoreBinariaEsquerdo.elemento, noArvoreBinariaEsquerdo.valor, noArvoreBinariaEsquerdo.parent);
					
				}
			}
			
		}
		
		int sair = 1;
		do {
			int resposta = JOptionPane.showConfirmDialog(null, "O animal que voce pensou " + this.getArvore().valor, "Confirme", 0, 3);
			
			if (resposta == 0) {
				this.perguntar((this.getArvore()).noDireito);
			} else {
				this.perguntar((this.getArvore()).noEsquerdo);
			}
			if (resposta != -1)
				continue;
			sair = 0;
		} while (sair == 1);
	}
	
	
	private NoArvoreBinaria buscaNo(int parent, NoArvoreBinaria raizArvore) {
						
		raizArvore = this.getArvore();
		
		parentTmp = parent;
		emOrdem(raizArvore);
		
		return noArvoreBinariaEncontrado;
	}
	
	
	//Recursivo para encontrar o nó
	private void emOrdem(NoArvoreBinaria no) {
	    if(no != null){
	    	emOrdem(no.noEsquerdo);
	      
	    	emOrdem(no.noDireito);
	      
	      if (no.elemento == parentTmp)
	      {
	    	  noArvoreBinariaEncontrado = no;
	      }
	    }
	}
	
		
	public void criarNovoAnimal(NoArvoreBinaria noArvore) {
		String animal = JOptionPane.showInputDialog((Component) null, "Qual o animal que voce pensou?");
		
		String caracteristica = JOptionPane.showInputDialog("Um(a) " + animal + "______ mas um(a) " + noArvore.valor + " nao.");
		
		String auxiliar = noArvore.valor;
		noArvore.valor = caracteristica;
		
		SQLite.alteraNoArvoreBinaria(noArvore.elemento, caracteristica);
		
		int id = ++index;
		
		noArvore.noDireito = new NoArvoreBinaria(id, animal, noArvore.elemento);
		SQLite.criaNoArvoreBinaria(id, animal, noArvore.elemento);
		
		id = ++index;
		noArvore.noEsquerdo = new NoArvoreBinaria(id, auxiliar, noArvore.elemento);
		SQLite.criaNoArvoreBinaria(id, auxiliar, noArvore.elemento);
			
	}
	
	public void perguntar(NoArvoreBinaria noArvore) {
		int pergunta = JOptionPane.showConfirmDialog(null, "O animal que voce pensou " + noArvore.valor, "Confirme", 0, 3);
		
		if (pergunta == 0) {
			if (noArvore.noDireito == null) {
				JOptionPane.showMessageDialog(null, "Acertei de novo!");
			} else {
				perguntar(noArvore.noDireito);
			}
		} else if (noArvore.noEsquerdo == null) {
			criarNovoAnimal(noArvore);
		} else {
			perguntar(noArvore.noEsquerdo);
		}
	}
		
	
	public NoArvoreBinaria getArvore() {
		return raiz;
	}
	
	public void setArvore(NoArvoreBinaria arvore) {
		raiz = arvore;
	}

}
