package models;


public class CartaoCliente {

    private int id;
    private String titular;
    private String numeroCartao;
    private double pontos;
    private int idCores;
    private CardGradient cores;

    public CartaoCliente() {
        this.id = 0;
        this.titular = "";
        this.numeroCartao = String.format("%d-%d-%d-%d", gerarNum(), gerarNum(), gerarNum(), gerarNum());
        this.pontos = 0;
        this.idCores = gerarIdCores();
        this.cores = CardGradient.gradientForCard(this.idCores);
    }


    public CartaoCliente(String titular) {
        this.titular = titular;
        this.numeroCartao = String
            .format(
                "%d-%d-%d-%d",
                gerarNum(),
                gerarNum(),
                gerarNum(),
                gerarNum()
            );
        this.pontos = 100; // retirar os pontos
        this.idCores = gerarIdCores();
        this.cores = CardGradient.gradientForCard(this.idCores);
    }

    public CartaoCliente(int id, String titular, String numeroCartao, double pontos, int idCores) {
        this.id = id;
        this.titular = titular == null ? "" : titular;
        this.numeroCartao = numeroCartao;
        this.pontos = pontos; // retirar os pontos
        this.idCores = idCores;
        this.cores = CardGradient.gradientForCard(this.idCores);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitular() {
        return this.titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public double getPontos() {
        return this.pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    public int getIdCores() {
        return this.idCores;
    }

    public void setIdCores(int idCores) {
        this.idCores = idCores;
        this.cores = CardGradient.gradientForCard(this.idCores);
    }

    public CardGradient getCores() {
        return this.cores;
    }

    @Override
    public String toString() {
        return "" + titular;
    }


    // @Override
    // public String toString() {
    //     return "CartaoCliente{"+ "id=" + id + ", titular='" + titular + '\'' + ", numeroCartao=" + numeroCartao + ", pontos=" + pontos + '}';
    // }

    public void debitarPontos(double pontos) {
        if (this.pontos >= pontos) { // Verifica se há pontos suficientes para debitar
            this.pontos -= pontos; // Subtrai os pontos do total
        } else {
            System.out.println("Não há pontos suficientes para débito.");
        }
    }

    public void transferirPontos(CartaoCliente destinatario) {
        transferirPontos(destinatario, this.pontos); // Transfere todos os pontos
                                                    // disponíveis
    }

    public void transferirPontos(CartaoCliente destinatario, double pontos) {
        if (this.pontos >= pontos) { // Verifica se o remetente tem pontos suficientes
            this.debitarPontos(pontos); // Debita os pontos do remetente
            destinatario.setPontos(destinatario.getPontos() + pontos); // Adiciona os
                                                                      // pontos ao
                                                                      // destinatário
        } else {
            System.out.println("Não há pontos suficientes no cartão original para transferência.");
        }
    }

    public void mostrarPontos() {
        System.out.println("Pontos disponíveis: " + getPontos());
    }

    private int gerarIdCores() {
        int min = 0;
        int max = 7;
        int range = max - min + 1;

        return (int) (Math.random() * range) + min;
    }

    private int gerarNum() {
        int min = 0;
        int max = 9999;
        int range = max - min + 1;

        return (int) (Math.random() * range) + min;
    }


}
