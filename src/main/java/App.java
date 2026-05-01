import fabiorodrigues.bricks.components.*;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.core.Effect;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.core.StateList;
import fabiorodrigues.bricks.style.Modifier;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.CartaoCliente;

/**
 * Ponto de entrada da aplicação Bricks. UI declarativa com estado reativo e base de dados
 * SQLite integrada.
 */
public class App extends BricksApplication {

    // ── Estado ────────────────────────────────────────────────────────────────

    private final State<String> titulo = state("Olá, Bricks!");
    private final StateList<CartaoCliente> listaCartoes = stateList(List.of());
    private final State<String> titular = state("");
    
    {
        setTitle("App");
        // setTheme(BricksTheme.dark()); // descomenta para dark mode
    }

    // ── Effects ───────────────────────────────────────────────────────────────

    // Cria o schema da base de dados no arranque
    private final Effect initDB = effect(() -> DatabaseSchema.create());

    // ── root() ────────────────────────────────────────────────────────────────
    private void criarCartao() {
        CartaoCliente cliente;
        if (titular.get().equals("")) {
            cliente = new CartaoCliente();
        } else {
            cliente = new CartaoCliente(titular.get());
        }
        listaCartoes.add(cliente);
    }

    @Override
    public Component root() {
        return new Column()
            .padding(20)
            .gap(12)
            .modifier(new Modifier().fillMaxWidth())
            .children(
                new Text(titulo.get()).modifier(new Modifier().fontSize(24).bold()),
                new Button("Adicionar Cartão").onClick(() -> {
                    Modal
                        .show(
                            this,
                            modal -> new Column()
                                .children(
                                    new Text("Adicionar Cartão").fontSize(18),
                                    new TextField().label("Titular:").bindTo(titular),
                                    new Button("Criar").onClick(() -> {
                                        criarCartao();
                                        modal.close();
                                    }),
                                    new Button("Cancelar").onClick(() -> modal.close())
                                )
                        );
                }),
                new LazyColumn<CartaoCliente>()
                    .gap(12)
                    .padding(16)
                    .items(listaCartoes.get())
                    .emptyState(new Text("Nenhuma nota."))
                    .item(
                        cartao -> new Row()
                            .gap(8)
                            .children(
                                new Card()
                                    .padding(16)
                                    .width(280)
                                    .height(176)
                                    .elevation(2)
                                    .children(
                                        new Row()
                                            .gap(0)
                                            .modifier(new Modifier().alignment(Pos.TOP_RIGHT))
                                            .children(
                                                new Image("/logo_faculdade.png").width(80)
                                            ),
                                        new Spacer(),
                                        new Column().gap(8).children(
                                            new Text(
                                                String.valueOf(cartao.getNumeroCartao())
                                            ).fontSize(15),
                                            new Text(
                                                cartao.getTitular()
                                            ).modifier(new Modifier().bold().fontSize(16))
                                        )
                                    ),
                                new Column()
                                    .gap(8)
                                    .children(
                                        new Button("Debitar").onClick(() -> {
                                        }),
                                        new Button("Transferir").onClick(() -> {
                                        })
                                    )
                            )
                    )
            );
    }

    /**
     * Ponto de entrada da aplicação.
     *
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        launch(args);
    }
}
