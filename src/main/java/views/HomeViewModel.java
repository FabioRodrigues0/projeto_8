package views;

import fabiorodrigues.bricks.core.BricksViewModel;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.data.DB;
import java.util.List;
import java.util.Map;
import models.CartaoCliente;
import models.CartaoClienteVip;

public class HomeViewModel extends BricksViewModel {

  // ── Estado ────────────────────────────────────────────────────────────────
  protected final StateList<CartaoCliente> listaCartoes = stateList(List.of());
  protected final StateList<CartaoCliente> listaCartoesDropdown = stateList(List.of());
  protected final State<String> titular = state("");
  protected final State<String> novoTitular = state("");
  protected final State<String> valorDebitar = state("");
  protected final State<CartaoCliente> clientSelecionado = state(null);
  protected final State<String> valorTransferir = state("");
  protected final State<Integer> cartaoEditandoId = state(-1);
  protected final State<Integer> cartaoAtivoId = state(-1);
  protected final State<Boolean> isVip = state(false);

  private List<CartaoCliente> queryCartoes() {
    return DB
      .query()
      .select("id", "titular", "numero_cartao", "pontos", "id_cores")
      .from("cartao_client")
      .where("desconto", "IS NULL", null)
      .orderBy("id", "ASC")
      .execute(CartaoCliente.class);
  }

  private List<CartaoClienteVip> queryCartoesVip() {
    return DB
      .query()
      .select("id", "titular", "numero_cartao", "pontos", "id_cores", "desconto")
      .from("cartao_client")
      .where("desconto", "IS NOT NULL", null)
      .orderBy("id", "ASC")
      .execute(CartaoClienteVip.class);
  }

  public void getCartoes() {
    List<CartaoCliente> lista = queryCartoes();
    List<CartaoClienteVip> listaVip = queryCartoesVip();
    listaCartoes.clear();
    listaCartoes.addAll(lista);
    listaVip.forEach(listaCartoes::add);
  }

  public void getCartoesDropdown() {
    List<CartaoCliente> lista = queryCartoes();
    List<CartaoClienteVip> listaVip = queryCartoesVip();
    listaCartoesDropdown.clear();
    listaCartoesDropdown.add(new CartaoCliente());
    listaCartoesDropdown.addAll(lista);
    listaVip.forEach(listaCartoesDropdown::add);
  }

  public void criarCartao() {
    CartaoCliente cliente;

    if (titular.get().equals("")) {
      cliente = isVip.get() ? new CartaoClienteVip() : new CartaoCliente();

      DB
        .query()
        .insertInto("cartao_client")
        .value("numero_cartao", cliente.getNumeroCartao())
        .value("pontos", cliente.getPontos())
        .value("id_cores", cliente.getIdCores())
        .when(isVip.get(), q -> q.value("desconto", ((CartaoClienteVip) cliente).getDesconto()))
        .execute();
    } else {
      cliente = isVip.get() ? new CartaoClienteVip(titular.get()) : new CartaoCliente(titular.get());

      DB
        .query()
        .insertInto("cartao_client")
        .value("titular", cliente.getTitular())
        .value("numero_cartao", cliente.getNumeroCartao())
        .value("pontos", cliente.getPontos())
        .value("id_cores", cliente.getIdCores())
        .when(isVip.get(), q -> q.value("desconto", ((CartaoClienteVip) cliente).getDesconto()))
        .execute();
    }
    listaCartoes.add(cliente);
    listaCartoesDropdown.add(cliente);
  }

  public void updateTitular(int id) {
    DB
      .query()
      .update("cartao_client")
      .set(Map.of("titular", novoTitular.get(), "pontos", 100.0))
      .where("id", "=", id)
      .execute();

    getCartoes();
    getCartoesDropdown();
  }

  public void debitar(int id) {
    CartaoCliente cartao = listaCartoes
      .get()
      .stream()
      .filter(c -> c.getId() == id)
      .findFirst()
      .orElse(null);

    cartao.debitarPontos(Double.parseDouble(valorDebitar.get()));

    DB
      .query()
      .update("cartao_client")
      .set(Map.of("pontos", cartao.getPontos()))
      .where("id", "=", id)
      .execute();

    getCartoes();
  }

  public void transferir(int id) {
    CartaoCliente destino = listaCartoes
      .get()
      .stream()
      .filter(c -> c.getId() == clientSelecionado.get().getId())
      .findFirst()
      .orElse(null);

    CartaoCliente cartao = listaCartoes
      .get()
      .stream()
      .filter(c -> c.getId() == id)
      .findFirst()
      .orElse(null);

    System.out.print(cartao);

    cartao.transferirPontos(destino, Double.parseDouble(valorTransferir.get()));

    System.out.println(cartao);
    System.out.println(destino);

    DB
      .query()
      .update("cartao_client")
      .set(Map.of("pontos", cartao.getPontos()))
      .where("id", "=", cartao.getId())
      .execute();

    DB
      .query()
      .update("cartao_client")
      .set(Map.of("pontos", destino.getPontos()))
      .where("id", "=", destino.getId())
      .execute();

    getCartoes();
    valorTransferir.set("");
  }
}
