import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class advent2 {
    public static void main(String[] args) {
        String filePath = "Cinputs\\input2.txt";
        int count = 0;
        int count1 = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] nums = line.trim().split("\\s+");
                List<Integer> numbers = new ArrayList<>();
                for (String num : nums) {
                    numbers.add(Integer.parseInt(num));
                }
                if (isSafe(numbers)) {
                    count++;
                }
                if (isSafe1(numbers)) {
                    count1++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(count);
        System.out.println(count1);
    }

    private static boolean isSafe(List<Integer> numbers) {
        return isSafe0(numbers, true) || isSafe0(numbers, false);
    }

    private static boolean isSafe0(List<Integer> numbers, boolean increasing) {
        for (int i = 1; i < numbers.size(); i++) {
            int diff = numbers.get(i) - numbers.get(i - 1);
            if ((increasing && diff <= 0) || (!increasing && diff >= 0) || Math.abs(diff) > 3) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSafe1(List<Integer> numbers) {
        if (isSafe(numbers)) return true;
        for (int i = 0; i < numbers.size(); i++) {
            List<Integer> numbers1 = new ArrayList<>(numbers);
            numbers1.remove(i);
            if (isSafe(numbers1)) return true;
        }
        return false;
    }
}
