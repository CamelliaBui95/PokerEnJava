public class Combination {
    public static String getName(int key) {
        return switch(key) {
            case 0 -> "Straight Flush";
            case 1 -> "4-of-a-kind";
            case 2 -> "Full House";
            case 3 -> "Flush";
            case 4 -> "Straight";
            case 5 -> "3-of-a-kind";
            case 6 -> "One Pair";
            case 7 -> "Two Pairs";
            case 8 -> "High Card";
            case 9 -> "Royal Flush";
            default -> "";
        };
    }
}
