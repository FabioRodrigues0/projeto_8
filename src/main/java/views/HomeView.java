package views;

import fabiorodrigues.bricks.components.Button;
import fabiorodrigues.bricks.components.Card;
import fabiorodrigues.bricks.components.Column;
import fabiorodrigues.bricks.components.Divider;
import fabiorodrigues.bricks.components.Dropdown;
import fabiorodrigues.bricks.components.IconButton;
import fabiorodrigues.bricks.components.Image;
import fabiorodrigues.bricks.components.LazyColumn;
import fabiorodrigues.bricks.components.Modal;
import fabiorodrigues.bricks.components.Row;
import fabiorodrigues.bricks.components.Spacer;
import fabiorodrigues.bricks.components.Text;
import fabiorodrigues.bricks.components.TextField;
import fabiorodrigues.bricks.core.BricksApplication;
import fabiorodrigues.bricks.core.BricksScene;
import fabiorodrigues.bricks.core.Component;
import fabiorodrigues.bricks.core.State;
import fabiorodrigues.bricks.style.Modifier;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import models.CartaoCliente;

public class HomeView extends BricksScene {

    private final HomeViewModel vm;
    private final BricksApplication app;
    private final State<String> titulo = state("Olá, Bricks!");

    public HomeView(BricksApplication app, HomeViewModel vm) {
        super(app);
        use(vm);
        this.vm = vm;
        this.app = app;
    }

    @Override
    public Component render() {
        int HEIGHT = 30;

        Modifier btn = new Modifier().height(HEIGHT);
        Modifier txt = new Modifier().height(HEIGHT);

        return new Column()
            .padding(20)
            .gap(12)
            .modifier(new Modifier().fillMaxWidth().fillMaxHeight())
            .children(
                new Text(titulo.get()).modifier(new Modifier().fontSize(24).bold()),
                new Button("Adicionar Cartão").onClick(() -> {
                    Modal
                        .show(
                            app,
                            modal -> new Column()
                                .gap(8)
                                .children(
                                    new Text("Adicionar Cartão").fontSize(18),
                                    new TextField().label("Titular:").bindTo(vm.titular),
                                    new Row()
                                        .gap(8)
                                        .children(
                                            new Button("Cancelar").onClick(() -> {
                                                vm.titular.set("");
                                                modal.close();
                                            }),
                                            new Button("Criar").onClick(() -> {
                                                vm.criarCartao();
                                                vm.titular.set("");
                                                modal.close();
                                            })
                                        )
                                )
                        );
                }),
                new LazyColumn<CartaoCliente>()
                    .gap(12)
                    .padding(16)
                    .modifier(new Modifier().fillMaxHeight())
                    .items(vm.listaCartoes.get())
                    .emptyState(new Text("Nenhuma nota."))
                    .item(cartao -> {
                        return new Row()
                            .gap(8)
                            .children(
                                new Card()
                                    .padding(16)
                                    .width(280)
                                    .height(176)
                                    .backgroundGradient(
                                        cartao.getCores().from(),
                                        cartao.getCores().to(),
                                        cartao.getCores().angle()
                                    )
                                    .elevation(2)
                                    .children(
                                        new Row()
                                            .gap(0)
                                            .modifier(new Modifier().alignment(Pos.TOP_RIGHT))
                                            .children(
                                                new Image(
                                                    cartao
                                                        .getCores()
                                                        .isDark() ? "/logo_faculdade_dark.png" : "/logo_faculdade.png"
                                                ).width(80)
                                            ),
                                        new Spacer(),
                                        new Column()
                                            .gap(8)
                                            .children(
                                                new Text(String.valueOf(cartao.getNumeroCartao()))
                                                    .fontSize(15)
                                                    .modifier(
                                                        new Modifier()
                                                            .textColor(
                                                                cartao.getCores().isDark() ? Color.WHITE : Color.BLACK
                                                            )
                                                    ),
                                                cartao.getTitular().equals("") ? new Row()
                                                    .gap(8)
                                                    .children(
                                                        new TextField()
                                                            .bindTo(
                                                                vm.novoTitular
                                                            ),
                                                        new Button("adicionar")
                                                            .onClick(
                                                                () -> {
                                                                    // vm.updateTitutar(id, novoTitular)
                                                                }
                                                            )
                                                    ) : new Text(cartao.getTitular())
                                                        .modifier(
                                                            new Modifier()
                                                                .textColor(
                                                                    cartao
                                                                        .getCores()
                                                                        .isDark() ? Color.WHITE : Color.BLACK
                                                                )
                                                                .bold()
                                                                .fontSize(16)
                                                        )
                                            )
                                    ),
                                new Column()
                                    .gap(8)
                                    .children(
                                        new Text("Pontos:"),
                                        new Text(String.valueOf(cartao.getPontos())).fontSize(50),
                                        new Divider(),
                                        new Row()
                                            .gap(8)
                                            .modifier(new Modifier().alignment(Pos.BOTTOM_LEFT))
                                            .children(
                                                new Column()
                                                    .gap(0)
                                                    .children(
                                                        new Text("Debitar")
                                                            .modifier(
                                                                new Modifier().bold()
                                                            ),
                                                        new Row()
                                                            .gap(5)
                                                            .modifier(
                                                                new Modifier()
                                                                    .alignment(
                                                                        Pos.BOTTOM_LEFT
                                                                    )
                                                            )
                                                            .children(
                                                                new TextField()
                                                                    .label("Valor:")
                                                                    .modifier(
                                                                        new Modifier()
                                                                            .width(80)
                                                                            .height(30)
                                                                    )
                                                                    .bindTo(vm.valorDebitar),
                                                                new IconButton("fas-arrow-right")
                                                                    .modifier(
                                                                        new Modifier()
                                                                            .width(45)
                                                                            .height(30)
                                                                    )
                                                                    .color(Color.WHITE)
                                                                    .onClick(() -> {
                                                                        // vm.debitar(id, valor)
                                                                    })
                                                            )
                                                    ),
                                                new Column()
                                                    .gap(0)
                                                    .modifier(
                                                        new Modifier().alignment(Pos.BOTTOM_LEFT)
                                                    )
                                                    .children(
                                                        new Text("Tranferir")
                                                            .modifier(
                                                                new Modifier().bold()
                                                            ),
                                                        new Row()
                                                            .gap(5)
                                                            .modifier(
                                                                new Modifier()
                                                                    .alignment(Pos.BOTTOM_RIGHT)
                                                                    .fillMaxWidth()
                                                            )
                                                            .children(
                                                                new Dropdown<>(
                                                                    vm.listaCartoes.get()
                                                                )
                                                                    .label("Para:")
                                                                    .modifier(
                                                                        new Modifier().width(170)
                                                                    )
                                                                    .bindTo(vm.clientSelecionado),
                                                                new TextField()
                                                                    .label("Valor")
                                                                    .modifier(
                                                                        new Modifier()
                                                                            .height(30)
                                                                            .width(80)
                                                                    )
                                                                    .bindTo(vm.valorTransferir),
                                                                new IconButton("fas-arrow-right")
                                                                    .modifier(
                                                                        new Modifier()
                                                                            .width(45)
                                                                            .height(30)
                                                                    )
                                                                    .color(Color.WHITE)
                                                                    .onClick(() -> {
                                                                        // vm.transferir(id, para, valor)
                                                                    })
                                                            )
                                                    )
                                            )
                                    )
                            );
                    })
            );
    }
}
