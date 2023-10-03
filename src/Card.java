public class Card {
    private String kind;
    private String suit;
    private int kindIndex;
    private int suitIndex;

    public Card(String kind, String suit) {
        this.kind = kind;
        this.suit = suit;

        this.kindIndex = Utils.indexOf(Game.KINDS, kind);
        this.suitIndex = Utils.indexOf(Game.SUITS, suit);
    }

    public String getKind() {
        return kind;
    }

    public String getSuit() {
        return suit;
    }

    public int getKindIndex() {
        return kindIndex;
    }

    public int getSuitIndex() {
        return suitIndex;
    }

    @Override
    public String toString() {
        return "Card=[" + this.kind + "," + this.suit + "]";
    }
}
