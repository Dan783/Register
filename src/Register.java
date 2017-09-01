import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Dan on 26.04.2017.
 */
public class Register {
    private int module; // модуль
    private int amount; // количсетво чисел в регистре
    private ArrayList<ArrayList<Integer>> consistOf = new ArrayList<>(); // список вариаций регистра
    private ArrayList<Integer> multipleNumbers = new ArrayList<>(); // список чисел на которые умножаются числа регистра
    private ArrayList<Integer> now = new ArrayList<>(); // состояние регистра в данный момент
    private ArrayList<Character> whatToAdd = new ArrayList<>(); // выбор ячеек которые будут задействованы

    private void setModule() throws IOException { // Выбор модуля
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Insert module : ");
        module = Integer.parseInt(reader.readLine());
    }

    private void setWhatToAdd() throws IOException { // Выбор ячеек которые будут зайдействованы
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter row of +, that will be multiplied : ");
        String symbols = reader.readLine();
        char[] buff = symbols.toCharArray();
        for (int i = 0; i < amount; i++) {
            whatToAdd.add(buff[i]);
        }
    }

    private void setMultipleNumbers() throws IOException { // Выбор чисел на которые будут множится числа
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter row of multiply numbers: ");
        String multiply = reader.readLine();
        char[] buff = multiply.toCharArray();
        for (int i = 0; i < amount; i++) {
            multipleNumbers.add(Character.getNumericValue(buff[i]));
        }
    }


    private void setNow() throws IOException { // Выбор чисел в регистр
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter row of numbers: ");
        String numbers = reader.readLine();
        char[] buff = numbers.toCharArray();
        amount = buff.length;
        for (int i = 0; i < amount; i++) {
            now.add(Character.getNumericValue(buff[i]));
        }
    }

    private void addInsist() {
        consistOf.add(now);
    } // Добавление регистра в список вариаций

    private int setS4() { // Утверждение 4 позии
        int S4 = 0;
        for (int i = 0; i < amount; i++) {
            if (whatToAdd.get(i).equals('+')) {
                S4 = (S4 + now.get(i) * multipleNumbers.get(i)) % module;
            }
        }
        return S4;
    }

    private boolean check() { // Проверка на повторение вариации
        for (int i = 0; i < consistOf.size(); i++) {
            if (now.equals(consistOf.get(i))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    private void createNow() { // Пересоздание регистра с измененным S4
        ArrayList<Integer> buff = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            if (i == amount - 1) {
                buff.add(setS4());
                break;
            }
            buff.add(now.get(i + 1));
        }
        now = buff;
    }

    public void start() throws IOException { // Подсчет
        setModule();
        setNow();
        setMultipleNumbers();
        setWhatToAdd();
        int P = 1;
        System.out.println(now);
        addInsist();
        while (true) {
            P++;
            setS4();
            createNow();
            System.out.println(now);
            if (check()) {
                break;
            }
            addInsist();
        }
        System.out.println("P = " + (P - 1));
    }

    public static void main(String args[]) throws IOException {
        Register reg = new Register();
        reg.start();
    }
}