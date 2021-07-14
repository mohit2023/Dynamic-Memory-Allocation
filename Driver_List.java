import java.util.Scanner;

public class Driver_List {
    public static void main(String args[]) {
        int numTestCases;
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        while (numTestCases-- > 0) {
            A1List obj = new A1List();
            int numCommands = sc.nextInt();
            while (numCommands-- > 0) {
                String command;
                command = sc.next();
                int argument1, argument2, argument3;
                A1List temp;
                int result = -5;
                switch (command) {
                    case "Insert":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        argument3 = sc.nextInt();
                        temp = obj.Insert(argument1, argument2, argument3);
                        result = temp.key;
                        break;
                    case "Insert_Move":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        argument3 = sc.nextInt();
                        obj = obj.Insert(argument1, argument2, argument3);
                        result = obj.key;
                        break;
                    case "Delete":
                        argument1 = sc.nextInt();
                        Dictionary tempDictionary = obj.Find(argument1, true);
                        result = obj.Delete(tempDictionary) ? 1 : 0;
                        break;
                    case "Find":
                        argument1 = sc.nextInt();
                        argument2 = sc.nextInt();
                        temp = obj.Find(argument1, (argument2 == 1));
                        result = (temp == null) ? -100 : temp.address;
                        break;
                    default:
                        break;
                }
                System.out.println(result);
            }

        }
    }
}
