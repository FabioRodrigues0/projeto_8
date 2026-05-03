package models;

import javafx.scene.paint.Color;

public record CardGradient(Color from, Color to, double angle, boolean isDark) {

    public static CardGradient randomCardGradient() {
        Object[][] palettes = {{Color.web("#fa8742"), Color.web("#c45e1e"), 135.0, true},  // escuro — texto branco
            {Color.web("#fa8742"), Color.web("#2c2c2c"), 135.0, true}, {Color.web("#2c2c2c"), Color
                .web("#fa8742"), 120.0, true}, {Color.web("#c45e1e"), Color.web("#2c2c2c"), 150.0, true}, {Color
                    .web("#3a3a3a"), Color.web("#fa8742"), 90.0, true}, {Color.web("#fa8742"), Color
                        .web("#8b3a0f"), 135.0, true}, {Color.web("#2c2c2c"), Color.web("#3a3a3a"), 135.0, true},
        };
        Object[] pair = palettes[(int) (Math.random() * palettes.length)];
        return new CardGradient(
            (Color) pair[0], (Color) pair[1], (double) pair[2], (boolean) pair[3]
        );
    }

    public static CardGradient gradientForCard(int cardId) {

        Object[][] palettes = {{Color.web("#fa8742"), Color.web("#c45e1e"), 135.0, true},  // escuro — texto branco
            {Color.web("#fa8742"), Color.web("#2c2c2c"), 135.0, true}, {Color.web("#2c2c2c"), Color
                .web("#fa8742"), 120.0, true}, {Color.web("#c45e1e"), Color.web("#2c2c2c"), 150.0, true}, {Color
                    .web("#3a3a3a"), Color.web("#fa8742"), 90.0, true}, {Color.web("#fa8742"), Color
                        .web("#8b3a0f"), 135.0, true}, {Color.web("#2c2c2c"), Color.web("#3a3a3a"), 135.0, true},
        };
        Object[] pair = palettes[cardId % palettes.length];
        return new CardGradient(
            (Color) pair[0], (Color) pair[1], (double) pair[2], (boolean) pair[3]
        );
    }
}
