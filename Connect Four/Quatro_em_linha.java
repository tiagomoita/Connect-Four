import java.util.*;

class Node{
	char configuracao[][] = new char[6][7];
	int valor = 0;

	Node(char m[][]){
		configuracao = m;
	}

	static void imprimir(Node no){
		for(int i=0;i<6;i++){
			for(int j=0;j<7;j++){
				System.out.print(no.configuracao[i][j] + " ");
			}
			System.out.println();
		}
	}

}

public class Quatro_em_linha{
	public static Scanner input;
	public static char configuracao[][];
	public static int comecar,algoritmo;
	public static int terminal_state[] = new int[2];
	public static int nos = 0;

	static void Input(){
		configuracao = new char[6][7];
		input = new Scanner(System.in);
		for(int i=0;i<6;i++){
			for(int j=0;j<7;j++){
				configuracao[i][j] = '-';
			}
		}
		System.out.println();
		System.out.println("-----QUATRO EM LINHA-----");
		System.out.println();
		System.out.println("1 2 3 4 5 6 7");
		imprimir(configuracao);
		System.out.println("Quem comeca a jogar? ");
		System.out.println("    1 - Eu(X)     ");
		System.out.println("    2 - Computador(O)     ");
		comecar = input.nextInt();
		System.out.println();
		System.out.println("Escolha o algoritmo: ");
		System.out.println("    1 - Minimax     ");
		System.out.println("    2 - Alfa-Beta     ");
		algoritmo = input.nextInt();
	}	
	static void imprimir(char matriz[][]){
		for(int i=0;i<6;i++){
			for(int j=0;j<7;j++){
				System.out.print(matriz[i][j] + " ");
			}
			System.out.println();
		}
	}
	static Node Successors_Eu(char matriz[][]){
		char clone[][] = new char[6][7];
		for(int x=0;x<6;x++)
			System.arraycopy(matriz[x],0,clone[x],0,7);

		boolean flag = true;
		while(flag){
			System.out.println("Jogar na coluna: ");
			int coluna = input.nextInt();
			System.out.println();
			if(coluna < 1 || coluna > 7)
				System.out.println("Jogada Impossivel, jogue noutra coluna.");	
			else if(clone[0][coluna-1] == 'X' || clone[0][coluna-1] == 'O')
				System.out.println("Jogada Impossivel, jogue noutra coluna.");	
			else{
				for(int i=5;i>=0;i--){
					if(clone[i][coluna-1] == '-'){
						clone[i][coluna-1] = 'X';
						break;
					}
				}
				flag = false;
			}
		}
		Node no = new Node(clone);
		return no;
	}
	static Node Successors_Computador(char matriz[][],int coluna,char vez){
		char clone[][] = new char[6][7];
		for(int x=0;x<6;x++)
			System.arraycopy(matriz[x],0,clone[x],0,7);	
		for(int i=5;i>=0;i--){
			if(clone[i][coluna] == '-'){
				clone[i][coluna] = vez;
				break;
			}
		}	
		Node no = new Node(clone);
		nos++;
		return no;
	}
	static int Terminal_State(char matriz[][]){
		int contador_x = 0,contador_o = 0;
		int ganhou = 0;

		//HORIZONTAL

		for(int i=0;i<6;i++){
			for(int j=0;j<4;j++){
				for(int k=j;k<j+4;k++){
					if(matriz[i][k] == 'X')
						contador_x++;
					if(matriz[i][k] == 'O')
						contador_o++;
				}	
				if(contador_x == 4){
					ganhou = 1;
					break;
				}
				if(contador_o == 4){
					ganhou = -1;
					break;
				}
				contador_x = 0;
				contador_o = 0;
			}

		}

		//VERTICAL

		for(int i=0;i<3;i++){
			for(int j=0;j<7;j++){

				for(int k=i;k<i+4;k++){
					if(matriz[k][j] == 'X')
						contador_x++;
					if(matriz[k][j] == 'O')
						contador_o++;
				}	
				if(contador_x == 4){
					ganhou = 1;
					break;
				}
				if(contador_o == 4){
					ganhou = -1;
					break;
				}	
				contador_x = 0;contador_o = 0;
			}
		}

		//DIAGONAL 1

		for(int i=0;i<3;i++){
			for(int j=0;j<4;j++){

				for(int k=i,l=j;k<i+4;k++,l++){
					if(matriz[k][l] == 'X')
						contador_x++;
					if(matriz[k][l] == 'O')
						contador_o++;
				}
				if(contador_x == 4){
					ganhou = 1;
					break;
				}
				if(contador_o == 4){
					ganhou = -1;
					break;
				}
				contador_x = 0;contador_o = 0;
			}
		}

		//DIAGONAL 2

		for(int i=0;i<3;i++){
			for(int j=3;j<7;j++){

				for(int k=i,l=j;k<i+4;k++,l--){
					if(matriz[k][l] == 'X')
						contador_x++;
					if(matriz[k][l] == 'O')
						contador_o++;
				}
				if(contador_x == 4){
					ganhou = 1;
					break;
				}
				if(contador_o == 4){
					ganhou = -1;
					break;
				}
				contador_x = 0;contador_o = 0;
			}
		}
		boolean flag = false;
		if(ganhou != 0)
			return ganhou;
		else if(ganhou == 0){
			for(int i=0;i<6;i++){
				for(int j=0;j<7;j++){
					if(matriz[i][j] == '-')
						flag = true;
				}
			}
			if(flag == false)
				return ganhou;
			else{
				ganhou = 2;
				return ganhou;
			}
		}
		return 0;	
	}
	static int Utility(char matriz[][]){
		int contador_x = 0,contador_o = 0;
		int pontos = 0;

		//HORIZONTAL

		for(int i=0;i<6;i++){
			for(int j=0;j<4;j++){
				for(int k=j;k<j+4;k++){
					if(matriz[i][k] == 'X')
						contador_x++;
					if(matriz[i][k] == 'O')
						contador_o++;
				}	
				if(contador_x == 1 && contador_o == 0)
					pontos += -1;
				if(contador_x == 2 && contador_o == 0)
					pontos += -10;
				if(contador_x == 3 && contador_o == 0)
					pontos += -50;
				if(contador_x == 4 && contador_o == 0)
					pontos += -512;
				if(contador_o == 1 && contador_x == 0)
					pontos += 1;
				if(contador_o == 2 && contador_x == 0)
					pontos += 10;
				if(contador_o == 3 && contador_x == 0)
					pontos += 50;
				if(contador_o == 4 && contador_x == 0)
					pontos += 512;
				if(contador_o != 0 && contador_x != 0)
					pontos += 0;

				contador_x = 0;
				contador_o = 0;
			}

		}

		//VERTICAL

		for(int i=0;i<3;i++){
			for(int j=0;j<7;j++){

				for(int k=i;k<i+4;k++){
					if(matriz[k][j] == 'X')
						contador_x++;
					if(matriz[k][j] == 'O')
						contador_o++;
				}	
				if(contador_x == 1 && contador_o == 0)
					pontos += -1;
				if(contador_x == 2 && contador_o == 0)
					pontos += -10;
				if(contador_x == 3 && contador_o == 0)
					pontos += -50;
				if(contador_x == 4 && contador_o == 0)
					pontos += -512;
				if(contador_o == 1 && contador_x == 0)
					pontos += 1;
				if(contador_o == 2 && contador_x == 0)
					pontos += 10;
				if(contador_o == 3 && contador_x == 0)
					pontos += 50;
				if(contador_o == 4 && contador_x == 0)
					pontos += 512;
				if(contador_o != 0 && contador_x != 0)
					pontos += 0;
				contador_x = 0;contador_o = 0;
			}
		}

		//DIAGONAL 1

		for(int i=0;i<3;i++){
			for(int j=0;j<4;j++){

				for(int k=i,l=j;k<i+4;k++,l++){
					if(matriz[k][l] == 'X')
						contador_x++;
					if(matriz[k][l] == 'O')
						contador_o++;
				}
				if(contador_x == 1 && contador_o == 0)
					pontos += -1;
				if(contador_x == 2 && contador_o == 0)
					pontos += -10;
				if(contador_x == 3 && contador_o == 0)
					pontos += -50;
				if(contador_x == 4 && contador_o == 0)
					pontos += -512;
				if(contador_o == 1 && contador_x == 0)
					pontos += 1;
				if(contador_o == 2 && contador_x == 0)
					pontos += 10;
				if(contador_o == 3 && contador_x == 0)
					pontos += 50;
				if(contador_o == 4 && contador_x == 0)
					pontos += 512;
				if(contador_o != 0 && contador_x != 0)
					pontos += 0;	
				contador_x = 0;contador_o = 0;
			}
		}

		//DIAGONAL 2

		for(int i=0;i<3;i++){
			for(int j=3;j<7;j++){

				for(int k=i,l=j;k<i+4;k++,l--){
					if(matriz[k][l] == 'X')
						contador_x++;
					if(matriz[k][l] == 'O')
						contador_o++;
				}
				if(contador_x == 1 && contador_o == 0)
					pontos += -1;
				if(contador_x == 2 && contador_o == 0)
					pontos += -10;
				if(contador_x == 3 && contador_o == 0)
					pontos += -50;
				if(contador_x == 4 && contador_o == 0)
					pontos += -512;
				if(contador_o == 1 && contador_x == 0)
					pontos += 1;
				if(contador_o == 2 && contador_x == 0)
					pontos += 10;
				if(contador_o == 3 && contador_x == 0)
					pontos += 50;
				if(contador_o == 4 && contador_x == 0)
					pontos += 512;
				if(contador_o != 0 && contador_x != 0)
					pontos += 0;	
				contador_x = 0;contador_o = 0;
			}
		}
		
		return pontos;
	}

	static Node Minimax_decision(Node no,int depth){
		Node estado = null;
		Node temp = null;
		int v = Integer.MIN_VALUE;	
		for(int i=0;i<7;i++){
			if(no.configuracao[0][i] == 'X' || no.configuracao[0][i] == 'O')
				continue;
			else{
				temp = Successors_Computador(no.configuracao,i,'O');
				int a = Min_Value(temp,depth);
				if(a > v){
					v = a;
					estado = temp;
				}
			}	
		}
		return estado;
	}
	static int Max_Value(Node no,int depth){
		int x = Terminal_State(no.configuracao);
		if(x == 1 || x == 0 || x == -1 || depth == 5)
			return Utility(no.configuracao);		
			
		int v = Integer.MIN_VALUE;
		for(int i=0;i<7;i++){
			v = Math.max(v,Min_Value(Successors_Computador(no.configuracao,i,'O'),depth+1));
		}
		return v;
	}
	static int Min_Value(Node no,int depth){
		int x = Terminal_State(no.configuracao);
		if(x == 1 || x == 0 || x == -1 || depth == 5)
			return Utility(no.configuracao);
		
		int v = Integer.MAX_VALUE;
		for(int i=0;i<7;i++){
			v = Math.min(v,Max_Value(Successors_Computador(no.configuracao,i,'X'),depth+1));
		}
		return v;	
	}

	static Node Alfa_Beta_Search(Node no,int depth){
		Node estado = null;
		Node temp = null;
		int v = Integer.MIN_VALUE;	
		for(int i=0;i<7;i++){
			if(no.configuracao[0][i] == 'X' || no.configuracao[0][i] == 'O')
				continue;
			else{
				temp = Successors_Computador(no.configuracao,i,'O');
				int a = Min_Value1(temp,depth,Integer.MIN_VALUE,Integer.MAX_VALUE);
				if(a > v){
					v = a;
					estado = temp;
				}
			}	
		}
		return estado;
	}
	static int Max_Value1(Node no,int depth,int alfa,int beta){
		int x = Terminal_State(no.configuracao);
		if(x == 1 || x == 0 || x == -1 || depth == 5)
			return Utility(no.configuracao);		
			
		int v = Integer.MIN_VALUE;
		for(int i=0;i<7;i++){
			v = Math.max(v,Min_Value1(Successors_Computador(no.configuracao,i,'O'),depth+1,alfa,beta));
			if(v >= beta)
				return v;
			alfa = Math.max(alfa,v);
		}
		return v;
	}
	static int Min_Value1(Node no,int depth,int alfa,int beta){
		int x = Terminal_State(no.configuracao);
		if(x == 1 || x == 0 || x == -1 || depth == 5)
			return Utility(no.configuracao);
		
		int v = Integer.MAX_VALUE;
		for(int i=0;i<7;i++){
			v = Math.min(v,Max_Value1(Successors_Computador(no.configuracao,i,'X'),depth+1,alfa,beta));
			if(v <= alfa)
				return v;
			beta = Math.min(beta,v);
		}
		return v;	
	}

	public static void main(String args[]){
		Input();
		Node no = new Node(configuracao);
		int finish = 0; 

		if(algoritmo == 1){
			if(comecar == 1){ 
				while(true){
					no = Successors_Eu(no.configuracao);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
					no = Minimax_decision(no,0);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
				}
			}
			if(comecar == 2){//COMPUTADOR
				while(true){
					no = Minimax_decision(no,0);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
					no = Successors_Eu(no.configuracao);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
				}
			}
		}
		if(algoritmo == 2){
			if(comecar == 1){ 
				while(true){
					no = Successors_Eu(no.configuracao);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
					no = Alfa_Beta_Search(no,0);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
				}
			}
			if(comecar == 2){//COMPUTADOR
				while(true){
					no = Alfa_Beta_Search(no,0);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
					no = Successors_Eu(no.configuracao);
					System.out.println("1 2 3 4 5 6 7");
					Node.imprimir(no);
					System.out.println();
					finish = Terminal_State(no.configuracao);
					if(finish == 1 || finish == -1 || finish == 0)
						break;
				}
			}
		}
		if(finish == 1)
			System.out.println("GANHOU");
		if(finish == 0)
			System.out.println("EMPATE");
		if(finish == -1)
			System.out.println("PERDEU");
		System.out.println();
		System.out.println("Nos gerados: " + nos);
			
	}
}