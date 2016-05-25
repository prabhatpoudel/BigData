package Lab2;

import java.util.Comparator;

public abstract class wordComparator implements Comparator<keyValuePair<String, Integer>> {

    @Override
    public int compare(keyValuePair<String, Integer> first, keyValuePair<String, Integer> second) {

        String firstValue = first.getKey();
        String secondValue = second.getKey();
        return firstValue.compareTo(secondValue);
    }

}
