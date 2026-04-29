package models;

public class CartaoClienteVip extends CartaoCliente {
    private final double desconto;

    public CartaoClienteVip() {
        super();
        this.desconto = 0.1; // Desconto padrão (10%)
    }

    public CartaoClienteVip(String titular) {
        super(titular);
        this.desconto = 0.1;
    }

    public double getDesconto() {
        return desconto;
    }
}
