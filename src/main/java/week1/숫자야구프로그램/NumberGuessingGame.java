package week1.숫자야구프로그램;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class NumberGuessingGame {

  public static void main(String[] args) {
    playGame();
  }

  private static void playGame() {
    int[] answer = generateNumber();
    Scanner scanner = new Scanner(System.in);
    int attemptCount = 0;
    System.out.println("컴퓨터가 숫자를 생성하였습니다. 답을 맞춰보세요!");

    while (true) {
      attemptCount++;
      System.out.print(attemptCount + "번째 시도 : ");
      String input = scanner.nextLine();

      if (validateInput(input)) {
        continue;
      }

      AttemptResult result = getStrikesAndBalls(input, answer);
      int strikes = result.strikes;
      int balls = result.balls;

      System.out.println(balls + "B" + strikes + "S");

      if (strikes == 3) {
        System.out.println(attemptCount + "번만에 맞히셨습니다.");
        System.out.println("게임을 종료합니다.");
        break;
      }
    }

    scanner.close();
  }

  private static int[] generateNumber() {
    Random random = new Random();
    Set<Integer> numberSet = new HashSet<>();
    int[] number = new int[3];

    while (numberSet.size() < 3) {
      int digit = random.nextInt(10);
      if (numberSet.add(digit)) {
        number[numberSet.size() - 1] = digit;
      }
    }

    return number;
  }

  private static AttemptResult getStrikesAndBalls(String input, int[] answer) {
    int[] guess = Arrays.stream(input.split("")).mapToInt(Integer::parseInt).toArray();

    int strikes = 0;
    int balls = 0;

    for (int i = 0; i < 3; i++) {
      if (guess[i] == answer[i]) {
        strikes++;
      } else if (contains(answer, guess[i])) {
        balls++;
      }
    }

    return new AttemptResult(balls, strikes);
  }

  private static boolean validateInput(String input) {
    // 입력한 숫자가 3자리 숫자가 아닌 경우 예외 처리
    if (input.length() != 3 || !input.matches("\\d+")) {
      System.out.println("잘못된 입력입니다. 3자리 숫자를 입력해주세요.");
      return true;
    }

    // 입력한 숫자가 중복된 숫자가 있는지 체크
    Set<Character> guessSet = new HashSet<>();
    boolean isValidInput = true;
    for (int i = 0; i < 3; i++) {
      if (!guessSet.add(input.charAt(i))) {
        isValidInput = false;
      }
    }
    if (!isValidInput) {
      System.out.println("서로 다른 숫자 3개를 입력해주세요.");
      return true;
    }
    return false;
  }

  private static boolean contains(int[] array, int value) {
    for (int i : array) {
      if (i == value) {
        return true;
      }
    }
    return false;
  }

  static class AttemptResult{
    int balls;
    int strikes;

    public AttemptResult(int balls, int strikes) {
      this.balls = balls;
      this.strikes = strikes;
    }
  }
}
