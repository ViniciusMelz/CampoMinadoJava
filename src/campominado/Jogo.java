package campominado;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Jogo {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CINZA = "\u001B[1m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_VERDE = "\u001B[32m";
    public static final String ANSI_AMARELO = "\u001B[33m";
    public static final String ANSI_AZUL = "\u001B[34m";
    public static final String ANSI_ROSA = "\u001B[35m";
    public static final String ANSI_CIANO = "\u001B[36m";
    public static final String ANSI_BRANCO = "\u001B[37m";
    private Campo[][] tabuleiro;
    private int linhas;
    private int colunas;
    private Dificuldade nivel;
    private ArrayList<Point> minasSorteadas;
    private double porcentagem, numMinasFloat;
    private int numMinas;
    private long numMinasLong;

    public Campo[][] getTabuleiro() {
        return tabuleiro;
    }
    
    public int getQntMinas(){
        return numMinas;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Dificuldade getNivel() {
        return nivel;
    }

    public ArrayList<Point> getMinasSorteadas() {
        return minasSorteadas;
    }

    public Jogo(int linhas, int colunas, Dificuldade nivel) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.nivel = nivel;

        switch (nivel) {
            case SUPERFACIL -> porcentagem = 0.05f;
            case FACIL -> porcentagem = 0.1f;
            case MEDIO -> porcentagem = 0.2f;
            case DIFICIL -> porcentagem = 0.3f;
            case IMPOSSIVEL -> porcentagem = 0.5f;
        }
        numMinasFloat = (linhas * colunas) * porcentagem;
        numMinasLong = Math.round(numMinasFloat);
        numMinas = Math.toIntExact(numMinasLong);
        
        if(numMinas < 1){numMinas = 1;}
        this.minasSorteadas = new ArrayList<>();
        tabuleiro = new Campo[this.linhas][this.colunas];
        criarTabuleiro();

    }

    private void criarTabuleiro() {
        boolean temMinaInicial = false;
        sorteioMinas();
        for (int contLinha = 0; contLinha < linhas; contLinha++) {
            for (int contColuna = 0; contColuna < colunas; contColuna++) {
                for (int verificarMinas = 0; verificarMinas < numMinas; verificarMinas++) {
                    Point pontoTabuleiroCriacao = new Point(contLinha, contColuna);
                    if(minasSorteadas.contains(pontoTabuleiroCriacao)){
                        temMinaInicial = true;
                        break;
                    }
                    else{
                        temMinaInicial = false;
                    }
                }
                tabuleiro[contLinha][contColuna] = new Campo(0, temMinaInicial, false, false, false);
            }
        }
        atualizarQtdMinasPerto();
    }

    private void sorteioMinas() {
        int linhaBomba, colunaBomba;
        Random aleatorio = new Random();
        for (int i = 0; i < numMinas; i++) {
            linhaBomba = aleatorio.nextInt(0, linhas);
            colunaBomba = aleatorio.nextInt(0, colunas);
            Point pontoMina = new Point(linhaBomba, colunaBomba);
            if(minasSorteadas.contains(pontoMina)){
                --i;
            }else{
                minasSorteadas.add(pontoMina);
            }
        }
    }
    
    private void atualizarQtdMinasPerto() {
        for(int atualizarMinas = 0; atualizarMinas < minasSorteadas.size(); atualizarMinas++){
            Point pontoAtualizaVizinhos = new Point();
            pontoAtualizaVizinhos = minasSorteadas.get(atualizarMinas);
            double linhaAttDouble = pontoAtualizaVizinhos.getX();
            double colunaAttDouble = pontoAtualizaVizinhos.getY();
            int linhaAtt = (int)linhaAttDouble;
            int colunaAtt = (int)colunaAttDouble;
            
            for(int i = -1 ; i <= 1 ; i++){
                for(int j = -1 ; j <= 1 ; j++){
                    if((linhaAtt + i) > (linhas - 1) || (linhaAtt + i) < 0 || (colunaAtt + j) > (colunas - 1) || (colunaAtt + j) < 0){
                    }
                    else{
                        tabuleiro[linhaAtt + i][colunaAtt + j].adicionaQntMinasPerto();
                    }
                }
            }            
        }
    }
    
    public void imprimeAberto(){
        System.out.print(" ");
        for(int desenho = 0 ; desenho < colunas ; desenho++){
            if(desenho < 10){
                System.out.print("  " + desenho);
            }else{
                System.out.print(" " + desenho);
            }
        }
        System.out.print("\n  ");
        for(int desenho2 = 0 ; desenho2 < colunas ; desenho2++){
            System.out.print("---");
        }
        int contadorDesenhoColunas = 0;
        for(int i = 0 ; i < this.linhas ; i++){
            if(contadorDesenhoColunas < 10){
                System.out.print("\n" + contadorDesenhoColunas++ + " |");
            }else{
                System.out.print("\n" + contadorDesenhoColunas++ + "|");
            }
            for(int j = 0 ; j < this.colunas ; j++){
                if(tabuleiro[i][j].isTemMina() == true){
                    System.out.print("*  ");
                }else{
                    int qntMinasPerto = tabuleiro[i][j].getQntMinasPerto();
                    switch(qntMinasPerto){
                        case 0 -> System.out.print(ANSI_BRANCO + qntMinasPerto + "  " + ANSI_RESET);
                        default -> System.out.print(ANSI_RED + qntMinasPerto + "  " + ANSI_RESET);
                    }
                }
            }
        }
    }
    
    public void imprimeParcial(){
        System.out.print("\n ");
        for(int desenho = 0 ; desenho < colunas ; desenho++){
            if(desenho < 10){
                System.out.print("  " + desenho);
            }else{
                System.out.print(" " + desenho);
            }
        }
        System.out.print("\n  ");
        for(int desenho2 = 0 ; desenho2 < colunas ; desenho2++){
            System.out.print("---");
        }
        int contadorDesenhoColunas = 0;
        for(int i = 0 ; i < this.linhas ; i++){
            if(contadorDesenhoColunas < 10){
                System.out.print("\n" + contadorDesenhoColunas++ + " |");
            }else{
                System.out.print("\n" + contadorDesenhoColunas++ + "|");
            }
            for(int j = 0 ; j < this.colunas ; j++){
                if(tabuleiro[i][j].isMarcado() == true && tabuleiro[i][j].isRevelado() == false){
                    System.out.print(ANSI_AZUL + "M  " + ANSI_RESET);
                }else if(tabuleiro[i][j].isRevelado() == false){
                    System.out.print("#  ");
                }else if(tabuleiro[i][j].isRevelado() == true){
                    int qntMinasPerto = tabuleiro[i][j].getQntMinasPerto();
                    switch(qntMinasPerto){
                        case 0 -> System.out.print(ANSI_BRANCO + qntMinasPerto + "  " + ANSI_RESET);
                        default -> System.out.print(ANSI_RED + qntMinasPerto + "  " + ANSI_RESET);
                    }
                }
            }
        }
    }
    
    public void marcarPosicao(int linhaMarcar, int colunaMarcar){
        tabuleiro[linhaMarcar][colunaMarcar].marcarPosicao();
    }
    
    public void revelarVizinhos(int linhaAbrir2, int colunaAbrir2){
        if(linhaAbrir2 < 0 || colunaAbrir2 < 0 || linhaAbrir2 >= this.linhas || colunaAbrir2 >= this.colunas){return;}
        else if(tabuleiro[linhaAbrir2][colunaAbrir2].isTemMina() == true){return;}
        else if(tabuleiro[linhaAbrir2][colunaAbrir2].isRevelado() == true){return;}
        else if(tabuleiro[linhaAbrir2][colunaAbrir2].getQntMinasPerto() > 0){
            tabuleiro[linhaAbrir2][colunaAbrir2].setRevelado(true);
            return;
        }
        
        tabuleiro[linhaAbrir2][colunaAbrir2].setRevelado(true);
        revelarVizinhos((linhaAbrir2 - 1), (colunaAbrir2 - 1));
        revelarVizinhos((linhaAbrir2 - 1), colunaAbrir2);
        revelarVizinhos((linhaAbrir2 - 1), (colunaAbrir2 + 1));
        revelarVizinhos(linhaAbrir2, (colunaAbrir2 - 1));
        revelarVizinhos(linhaAbrir2, (colunaAbrir2 + 1));
        revelarVizinhos((linhaAbrir2 + 1), (colunaAbrir2 - 1));
        revelarVizinhos((linhaAbrir2 + 1), colunaAbrir2);
        revelarVizinhos((linhaAbrir2 + 1), (colunaAbrir2 + 1));
    }
    
    public boolean verificaTemMina(int linhaTemBomba, int colunaTemBomba){
        Point pontoTemBomba = new Point(linhaTemBomba, colunaTemBomba);
        if(minasSorteadas.contains(pontoTemBomba)){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean verificarVitoria(){
        int contadorNaoRevelados = 0;
        for(int i = 0 ; i < linhas ; i++){
            for(int j = 0 ; j < colunas ; j++){
                if(tabuleiro[i][j].isRevelado() == false){
                    ++contadorNaoRevelados;
                }
            }
        }
        if(contadorNaoRevelados == numMinas){
            imprimeAberto();
            return true;
        }else{
            return false;
        }
    }
    
}
