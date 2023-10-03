import java.util.*;

public class Game {
    private static int nbCards;
    private static int nbPlayers;
    private static List<Card> cards;
    private static Queue<Card> deck;
    private static HashMap<String, Integer> handTypes;
    private static Player[] players;
    public static String[] KINDS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    public static String[] SUITS = {"Club", "Spade", "Heart", "Diamond"};
    private static Scanner myScanner = new Scanner(System.in);

    private static void generateCards() {
        if(cards == null)
            cards = new ArrayList<>();

        for(int suit = 0 ; suit < SUITS.length; suit++) {
            for(int kind = 0; kind < KINDS.length; kind++) {
                cards.add(new Card(KINDS[kind], SUITS[suit]));
            }
        }
    }

    private static void shuffleCards() {
        if(deck == null)
            deck = new LinkedList();

        Collections.shuffle(cards);
        for(int card = 0; card < nbCards; card++)
            deck.add(cards.get(card));
    }

    private static void distribute() {
        for(int i = 0; i < players.length; i++) {
            var counter = 0;
            Hand hand = new Hand();

            while(counter < 5) {
                Card card = deck.remove();
                hand.add(card);
                deck.add(card);
                counter++;
            }

            players[i].setHand(hand);
        }
    }

    public static void start() {
        do {
            System.out.print("Number of players: ");
            nbPlayers = myScanner.nextInt();
        } while (nbPlayers == 0);

        initialize();
    }

    private static void initialize() {
        players = new Player[nbPlayers];
        nbCards = nbPlayers * 13;

        for(int i = 0; i < nbPlayers; i++) {
            var name = getPlayerName(i);
            players[i] = new Player(name);
        }

        generateCards();
        shuffleCards();
        distribute();
        sortPlayers();
        report();
    }

    private static String getPlayerName(int index) {
        String name = "";

        do {
            System.out.print("Player n°" + index + " : ");
            name = myScanner.next();
        } while(name.equals(""));

        return name;
    }

    private static void sortPlayers() {
        var firstIndex = 0;
        var lastIndex = nbPlayers;

        while(!sortPlayers(firstIndex, lastIndex--));

    }

    private static boolean sortPlayers(int firstIndex, int lastIndex) {
        boolean isSorted = true;

        for(int i = firstIndex; i < lastIndex - 1; i++) {
            var current = players[i].getHand();
            var next = players[i + 1].getHand();
            if(current.compareTo(next) > 0) {
                var temp = players[i];
                players[i] = players[i + 1];
                players[i + 1] = temp;
                isSorted = false;
            }
        }

        return isSorted;
    }

    private static void getHandTypes() {
        if(handTypes == null)
            handTypes = new HashMap<>();

        for(Player player : players) {
            String key = Combination.getName(player.getHand().getCombinationKey());
            handTypes.put(key, handTypes.getOrDefault(key, 0) + 1);
        }

        for(Map.Entry entry : handTypes.entrySet())
            if(!entry.getKey().equals(""))
                System.out.println(entry.getKey() + " = " + entry.getValue());


    }

    private static void report() {
        System.out.println("");
        System.out.println("Report");
        System.out.println("--------------------------------------------------------------------------------------------");
        for(int i = nbPlayers - 1; i >= 0; i--)
            System.out.println(players[i]);
        System.out.println("--------------------------------------------------------------------------------------------");
        getHandTypes();
    }
}
