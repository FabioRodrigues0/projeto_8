package models;

import java.text.DecimalFormat;

public class CartaoClienteVip extends CartaoCliente {

  private final double desconto;

  public CartaoClienteVip() {
    super();
    this.desconto = 0.7; // Desconto padrão (10%)
  }

  public CartaoClienteVip(String titular) {
    super(titular);
    this.desconto = 0.7;
  }

  public double getDesconto() {
    return desconto;
  }

  @Override
  public void debitarPontos(double pontos) {
    double taxa = 0.02; // 2% de taxa
    double desconto = taxa * this.desconto;
    double totalComTaxa = pontos + (pontos * desconto);

    DecimalFormat formato = new DecimalFormat("#.##");

    if (this.getPontos() >= totalComTaxa) {
      double novosPontos = this.getPontos() - totalComTaxa;
      this.setPontos(Double.valueOf(formato.format(novosPontos)));
    } else {
      System.out.println("Não há pontos suficientes para débito.");
    }
  }

  @Override
  public void transferirPontos(CartaoCliente destinatario) {
    double taxa = 0.02; // 2% de taxa
    double desconto = taxa * this.desconto;
    double totalComTaxa = this.getPontos() - (this.getPontos() * desconto);

    DecimalFormat formato = new DecimalFormat("#.##");

    this.setPontos(0);
    destinatario.setPontos(destinatario.getPontos() + Double.valueOf(formato.format(totalComTaxa)));
  }

  @Override
  public void transferirPontos(CartaoCliente destinatario, double pontos) {
    double taxa = 0.02;
    double desconto = taxa * this.desconto;
    double totalComTaxa = this.getPontos() + (pontos * desconto);

    DecimalFormat formato = new DecimalFormat("#.##");

    if (this.getPontos() >= totalComTaxa) {
      this.setPontos(this.getPontos() - Double.valueOf(formato.format(totalComTaxa)));
      destinatario.setPontos(destinatario.getPontos() + pontos);
    } else {
      System.out.println("Não há pontos suficientes no cartão original para transferência.");
    }
  }
}
