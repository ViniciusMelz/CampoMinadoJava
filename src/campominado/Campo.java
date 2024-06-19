package campominado;

public class Campo {
    private int qntMinasPerto;
    private boolean temMina;
    private boolean clicado;
    private boolean marcado;
    private boolean revelado;

    public int getQntMinasPerto() {
        return qntMinasPerto;
    }

    public void setQntMinasPerto(int qntMinasPerto) {
        this.qntMinasPerto = qntMinasPerto;
    }

    public boolean isTemMina() {
        return temMina;
    }

    public void setTemMina(boolean temMina) {
        this.temMina = temMina;
    }

    public boolean isClicado() {
        return clicado;
    }

    public void setClicado(boolean clicado) {
        this.clicado = clicado;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public boolean isRevelado() {
        return revelado;
    }

    public void setRevelado(boolean revelado) {
        this.revelado = revelado;
    }

    public Campo(int qntMinasPerto, boolean temMina, boolean clicado, boolean marcado, boolean revelado) {
        this.qntMinasPerto = qntMinasPerto;
        this.temMina = temMina;
        this.clicado = clicado;
        this.marcado = marcado;
        this.revelado = revelado;
    }
    
    public void adicionaQntMinasPerto(){
        this.qntMinasPerto++;
    }
    
    public void marcarPosicao(){
        this.marcado = !this.marcado;
    }

    @Override
    public String toString() {
        return "Campo{" + "qntMinasPerto=" + qntMinasPerto + ", temMina=" + temMina + ", clicado=" + clicado + ", marcado=" + marcado + ", revelado=" + revelado + '}';
    }
}
