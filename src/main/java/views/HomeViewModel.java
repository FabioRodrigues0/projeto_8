package views;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import java.util.List;
import java.util.Map;
import models.CartaoCliente;

public class HomeViewModel extends BricksViewModel {


    // ── Estado ────────────────────────────────────────────────────────────────
    protected final StateList<CartaoCliente> listaCartoes = stateList(List.of());
    protected final State<String> titular = state("");
    protected final State<String> novoTitular = state("");
    protected final State<String> valorDebitar = state("");
    protected final State<CartaoCliente> clientSelecionado = state(null);
    protected final State<String> valorTransferir = state("");
    protected final State<Integer> cartaoEditandoId = state(-1);

    public void getCartoes() {
        List<CartaoCliente> lista = DB
            .query()
            .select("id", "titular", "numero_cartao", "pontos", "id_cores")
            .from("cartao_client")
            .orderBy("id", "ASC")
            .execute(CartaoCliente.class);

        listaCartoes.clear();
        listaCartoes.addAll(lista);
    }

    public void criarCartao() {
        CartaoCliente cliente;

        if (titular.get().equals("")) {
            cliente = new CartaoCliente();

            System.out.print("idCore:"+ cliente.getIdCores());

            DB
                .query()
                .insertInto("cartao_client")
                .value("numero_cartao", cliente.getNumeroCartao())
                .value("pontos", cliente.getPontos())
                .value("id_cores", cliente.getIdCores())
                .execute();

        } else {
            cliente = new CartaoCliente(titular.get());

            DB
                .query()
                .insertInto("cartao_client")
                .value("titular", cliente.getTitular())
                .value("numero_cartao", cliente.getNumeroCartao())
                .value("pontos", cliente.getPontos())
                .value("id_cores", cliente.getIdCores())
                .execute();
        }
        listaCartoes.add(cliente);
    }


    public void updateTitular(int id) {
        DB
            .query()
            .update("cartao_client")
            .set(Map.of("titular", novoTitular.get(), "pontos", 100.0))
            .where("id", "=", id)
            .execute();
        
        getCartoes();
    }

    public void debitar(int id) {
        CartaoCliente cartao = listaCartoes.get().stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElse(null);

        cartao.debitarPontos(Double.parseDouble(valorDebitar.get()));

        DB.query()
            .update("cartao_client")
            .set(Map.of("pontos", cartao.getPontos()))
            .where("id", "=", id)
            .execute();

        getCartoes();
    }
}
