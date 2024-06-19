package campominado;

import java.awt.Point;
import java.io.IOException;
import java.util.Scanner;

public class CampoMinado extends IOException{

    public static void main(String[] args) throws PisouEmMina {
        Scanner entradaString = new Scanner(System.in);
        Scanner entradaString2 = new Scanner(System.in);
        Scanner entradaInt = new Scanner(System.in);
        Point ponto = new Point();
        String tamanho, linhaString = "", colunaString = "", abrirTabuleiro;
        String linhaString2 = "", colunaString2 = "";
        int dificuldadeInt = 100, contador = 0, contador2 = 0;
        boolean eNumerico = false, erro = false, dificuldadeValida = false, abrirTabuleiroPosicao = false, abrirTabuleiroMarcar = false, abrirTabuleiroValido = false, ganhou = false;
        int linhaAbrir = 0, colunaAbrir = 0;
        imprimeApresentacao();
        do {
            System.out.println("Informe Linhas, Colunas: ");
            tamanho = entradaString.nextLine();
            eNumerico = isNumeric(tamanho);
            if(eNumerico == false){System.out.println("Digite o Tamanho em Um Formato Valido!");}
        } while (eNumerico == false);

        for (int i = 0; i < tamanho.length(); i++) {
            if (Character.isDigit(tamanho.charAt(i)) == true) {
                if (contador == 1) {
                    colunaString = colunaString + tamanho.charAt(i);
                } else {
                    linhaString = linhaString + tamanho.charAt(i);
                }
            } else {
                contador = 1;
            }
        }
        try {
            do {
                imprimeEscolhaDificuldade();
                dificuldadeInt = entradaInt.nextInt();
                if(dificuldadeInt > 4 || dificuldadeInt < 0){
                    System.out.println("Você Digitou uma Dificuldade Invalida!");
                    dificuldadeValida = false;
                }else{dificuldadeValida = true;}
            } while (dificuldadeValida == false);
        } catch (Exception e) {
            System.out.println("Você Não Digitou um Numero!");
            e.printStackTrace();
            erro = true;
        }
        if(erro == false){
            Dificuldade dificuldade;
            switch (dificuldadeInt) {
                case 0 -> dificuldade = Dificuldade.SUPERFACIL; 
                case 1 -> dificuldade = Dificuldade.FACIL;
                case 2 -> dificuldade = Dificuldade.MEDIO; 
                case 3 -> dificuldade = Dificuldade.DIFICIL;
                case 4 -> dificuldade = Dificuldade.IMPOSSIVEL;
                default -> dificuldade = Dificuldade.MEDIO; //Default criado em medio somente para a criação do Jogo, pois caso não exista default ele não deixará criar, mas a verificação foi realizada acima evitando dessa forma que alguem dificte um modo não existente//
            }
            int linhaInt = Integer.parseInt(linhaString);
            int colunaInt = Integer.parseInt(colunaString);
            Jogo jogo = new Jogo(linhaInt, colunaInt, dificuldade);
            
            imprimeApresentacaoEscolhaPosicao(jogo.getQntMinas());
            do{ 
                jogo.imprimeParcial();
                abrirTabuleiroMarcar = false; abrirTabuleiroPosicao = false; abrirTabuleiroValido = false;
                do{ 
                    contador2 = 0; linhaString2 = ""; colunaString2 = ""; abrirTabuleiro = "";
                    System.out.println("\nDigite a Posição Desejada: ");
                    abrirTabuleiro = entradaString.nextLine();
                    if(isNumericMarcado(abrirTabuleiro) == true){
                        abrirTabuleiroMarcar = true;
                        abrirTabuleiroValido = true;
                    }else if(isNumeric(abrirTabuleiro) == true){
                        abrirTabuleiroPosicao = true;
                        abrirTabuleiroValido = true;
                    }else{
                        abrirTabuleiroValido = false;
                        System.out.println("Digite Uma Posição Válida!");
                    }
                    
                    if(abrirTabuleiroValido == true){
                        for (int i = 0; i < abrirTabuleiro.length(); i++) {
                            if (Character.isDigit(abrirTabuleiro.charAt(i)) == true) {
                                if (contador2 == 1) {
                                    colunaString2 = colunaString2 + abrirTabuleiro.charAt(i);
                                } else {
                                    linhaString2 = linhaString2 + abrirTabuleiro.charAt(i);
                                }
                            } else {
                                contador2 = 1;
                            }
                        }
                        linhaAbrir = Integer.parseInt(linhaString2);
                        colunaAbrir = Integer.parseInt(colunaString2);
                        
                        if(linhaAbrir < linhaInt && linhaAbrir >= 0 && colunaAbrir < colunaInt && colunaAbrir >= 0){
                            abrirTabuleiroValido = true;
                            if(abrirTabuleiroPosicao == true){
                                if(jogo.verificaTemMina(linhaAbrir, colunaAbrir) == false){
                                    jogo.revelarVizinhos(linhaAbrir, colunaAbrir);
                                    if(jogo.verificarVitoria() == true){ganhou = true;}
                                }else{
                                    jogo.imprimeAberto();
                                    throw new PisouEmMina();
                                }
                            }else if(abrirTabuleiroMarcar == true){
                                jogo.marcarPosicao(linhaAbrir, colunaAbrir);
                            }
                        }else{
                            abrirTabuleiroValido = false;
                            System.out.println("Digite uma Posição Válida!");
                        }
                    }
                }while(abrirTabuleiroValido == false);
            }while(ganhou == false);
            if(ganhou == true){
                System.out.println("\nVocê Ganhou o Jogo, Parabéns! ");
            }
        }
    }

    public static void imprimeApresentacao() {
        System.out.println("--------------------------------------------------------------------");
        System.out.println("        CAMPO MINADO    -   CRIADO POR: Vinicius Gabriel Melz");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Escolha o Tamanho do Tabuleiro Informando a Quantidade de Linhas e Colunas!");
    }

    public static void imprimeEscolhaDificuldade() {
        System.out.println("Escolha a Quantidade de Minas: ");
        System.out.println("0 - SUPER FACIL - 5% De Minas");
        System.out.println("1 - FACIL - 10% De Minas");
        System.out.println("2 - MEDIO - 20% De Minas");
        System.out.println("3 - DIFICIL - 30% De Minas");
        System.out.println("4 - IMPOSSIVEL - 50% De Minas");
    }
    
    public static void imprimeApresentacaoEscolhaPosicao(int numMinas){
        if(numMinas < 1){System.out.println("Tabuleiro Criado --- Existe " + numMinas + " Mina no Tabuleiro ");}
        else{System.out.println("Tabuleiro Criado --- Existem " + numMinas + " Minas no Tabuleiro ");}
        System.out.println("Selecione a Linha e Coluna Que Deseja Abrir");
        System.out.println("Para Abrir Digite a Linha e Coluna Desejada (Linha, Coluna) ");
        System.out.println("Para Marcar Digite a Linha e Coluna Desejada Acrecentando o M (Linha, Coluna, M) ");
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+, [0-9.]+");
    }
    public static boolean isNumericMarcado(String str) {
        return str != null && str.matches("[0-9.]+, [0-9.]+, M");
    }
}
