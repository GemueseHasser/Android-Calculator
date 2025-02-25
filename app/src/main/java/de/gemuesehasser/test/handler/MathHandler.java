package de.gemuesehasser.test.handler;

import android.view.View;
import com.google.android.material.snackbar.Snackbar;
import org.jetbrains.annotations.NotNull;

public final class MathHandler {

    /**
     * Berechnet einen Term, welcher in Form eines Strings übergeben wird und gibt diesen ausgerechnet wieder zurück.
     *
     * @param term Der String der mathematisch berechnet wird.
     *
     * @return Das Ergebnis der Rechnung.
     */
    public static double eval(@NotNull final String term, final View view) {
        String replacedTerm = term
                .replaceAll("e", String.valueOf(Math.E))
                .replaceAll("π", String.valueOf(Math.PI))
                .replaceAll("\\s", "");

        for (int i = 0; i < replacedTerm.length(); i++) {
            if ((i + 1) >= replacedTerm.length()) continue;
            if (replacedTerm.charAt(i + 1) != '(') continue;

            final char currentChar = replacedTerm.charAt(i);

            if ((currentChar < '0' || currentChar > '9') && currentChar != ')') continue;

            replacedTerm = replacedTerm.substring(0, i + 1) + "*" + replacedTerm.substring(i + 1);
        }

        final String finalTerm = replacedTerm;

        return new Object() {
            private int pos = -1;
            private int ch;

            private void nextChar() {
                ch = (++pos < finalTerm.length()) ? finalTerm.charAt(pos) : -1;
            }

            private boolean eat(final int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            private double parse() {
                nextChar();
                double x = parseExpression();

                if (pos < finalTerm.length()) return 0;

                return x;
            }

            private double parseExpression() {
                double x = parseTerm();
                while (true) {
                    if (eat('+')) {
                        x += parseTerm();
                    } else if (eat('-')) {
                        x -= parseTerm();
                    } else {
                        return x;
                    }
                }
            }

            private double parseTerm() {
                double x = parseFactor();
                while (true) {
                    if (eat('*')) {
                        x *= parseFactor();
                    } else if (eat('/')) {
                        x /= parseFactor();
                    } else {
                        return x;
                    }
                }
            }

            private double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(finalTerm.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = finalTerm.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;

                        case "ln":
                            x = Math.log(x);
                            break;

                        case "log":
                            x = Math.log10(x);
                            break;

                        case "sin":
                            x = getRounded(Math.sin(x));
                            break;

                        case "cos":
                            x = getRounded(Math.cos(x));
                            break;

                        case "tan":
                            x = getRounded(Math.tan(x));
                            break;

                        case "asin":
                            x = getRounded(Math.asin(x));
                            break;

                        case "acos":
                            x = getRounded(Math.acos(x));
                            break;

                        case "atan":
                            x = getRounded(Math.atan(x));
                            break;

                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    Snackbar.make(view, "Leider 0", Snackbar.LENGTH_SHORT).show();
                    return 0;
                }

                if (eat('^')) x = Math.pow(x, parseFactor());

                return x;
            }
        }.parse();
    }

    private static double getRounded(final double x) {
        return Math.round(x * (10 * Math.pow(10, 14))) / (10 * Math.pow(10, 14));
    }

}
