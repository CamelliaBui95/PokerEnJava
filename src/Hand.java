import java.util.*;

public class Hand {
    private HashMap<Character, Integer> frequencies = new HashMap<>();
    private int[] suits = new int[4];
    private String SCALES = "BCDEFGHIJKLMN";
    private char quinteFlush = 'A';
    private char carre = 'A';
    private char full = 'A';
    private char couleur = 'A';
    private char suite = 'A';
    private char brelan = 'A';
    private char[] paires = {'A', 'A'};

    public int getCombinationKey() {
        if(quotation.isEmpty())
            evaluateCards();

        return combinationKey;
    }

    private int combinationKey = -1;

    private List<Card> cards = new ArrayList<>();
    private StringBuilder quotation = new StringBuilder();

    public void add(Card card) {
        cards.add(card);
    }

    public Hand() {

    }

    public Hand(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }

    private void sort() {
        cards.sort((c1, c2) -> Integer.compare(c1.getKindIndex(), c2.getKindIndex()));
    }

    private void readFrequencies() {
        sort();
        for(var card : cards) {
            suits[card.getSuitIndex()] += 1;
            var ch = SCALES.charAt(card.getKindIndex());
            frequencies.put(ch, frequencies.getOrDefault(ch, 0) + 1);
        }

        evaluateHighCardForKey();
    }

    public void evaluateCards() {
        readFrequencies();
        quoteSuit();
        quoteCouleur();
        quotePaires();
        quoteBrelanCarre();
        quoteFull();
        quoteQuinteFlush();

        quotation.append(quinteFlush);
        quotation.append(carre);
        quotation.append(full);
        quotation.append(couleur);
        quotation.append(suite);
        quotation.append(brelan);

        for(var ch : paires)
            quotation.append(ch);

        for(int i = cards.size() - 1; i >= 0; i--) {
            var ch = SCALES.charAt(cards.get(i).getKindIndex());
            quotation.append(ch);
        }

    }

    public String getQuotation() {
        if(quotation.toString().equals(""))
            evaluateCards();

        return quotation.toString();
    }

    public void quoteSuit() {
        boolean isSuccessive = true;
        int counter = 0;

        while(counter < cards.size() - 1 && isSuccessive) {
            var diff = cards.get(counter + 1).getKindIndex() - cards.get(counter).getKindIndex();
            if(diff != 1) {
                if (counter + 1 == 4 && cards.get(counter + 1).getKindIndex() == 12)
                    isSuccessive = cards.get(0).getKindIndex() == 0;
                else
                    isSuccessive = false;
            }

            counter++;
        }

        if(!isSuccessive)
            return;

        suite = cards.get(0).getKindIndex() == 0 ? 'B' : 'C';
    }

    public void quoteQuinteFlush() {
        if(suite != 'A' && couleur != 'A') {
            quinteFlush = 'B';
            combinationKey = 0;
        }

        evaluateQFRForKey();
    }

    public void quotePaires() {
        var counter = 0;
        for(var entry : frequencies.entrySet()) {
            if(entry.getValue() == 2)
                paires[counter++] = entry.getKey();
        }

        if(paires[0] > (int) paires[1]) {
            var temp = paires[0];
            paires[0] = paires[1];
            paires[1] = temp;
        }

        if(paires[1] != 'A')
            combinationKey = paires[0] != 'A' ? 7 : 6;

    }

    public void quoteFull() {
        if(brelan != 'A')
            full = paires[1] != 'A' ? 'C' : 'A';

        combinationKey = full != 'A' ? 2 : combinationKey;

    }

    public void quoteBrelanCarre() {
        for(var entry : frequencies.entrySet()) {
            if(entry.getValue() == 3) {
                brelan = entry.getKey();
                combinationKey = 5;
            }

            if(entry.getValue() == 4) {
                carre = entry.getKey();
                combinationKey = 1;
            }
        }
    }

    public void quoteCouleur() {
        for(int i = 0; i < suits.length; i++)
            if(suits[i] == 5) {
                couleur = 'B';
                combinationKey = 3;
            }
    }

    public void evaluateHighCardForKey() {
        combinationKey = cards.get(4).getKindIndex() == 12 ? 8 : combinationKey;
    }

    public void evaluateQFRForKey() {
        if(quinteFlush != 'A')
            combinationKey = quotation.charAt(0) == 8 ? 9 : combinationKey;
    }

    public void print() {
        System.out.println(Arrays.toString(suits));
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }

    public int compareTo(Hand hand) {
        return getQuotation().compareTo(hand.getQuotation());
    }
}
