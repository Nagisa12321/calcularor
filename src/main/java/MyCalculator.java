import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JT Chen
 * @Date 2020/11/13
 * 用栈来处理字符串
 * - 括号 > 乘除 > 加减
 * 1 若为数字，则直接入栈
 * 2 若为运算符，栈空则直接入栈
 * 3 若为运算符，栈顶为"(" 则直接入栈
 * 4 若为运算符，栈顶元素优先级大于此运算符，则取数字栈的两个数字
 * 与栈顶的运算符号进行计算，重新压入数字栈，符号栈出栈，直到遇到"("或者优先级小于或等于
 * 此运算符的栈顶元素为止
 * 5 若为“（”直接入栈，
 * 6 若为“）”，则依次两两弹出数字栈顶并进行计算，直到遇到“（”为止
 */
public class MyCalculator {
    private static final Stack<Character> theCharStack = new Stack<>();//操作符栈
    private static final Stack<Double> theDoubleStack = new Stack<>();//数字栈
    private static final String regex = "[0-9]*\\.?[0-9]*";//Double型字符串
    private static final Pattern thePattern = Pattern.compile(regex);//定义模板

    public MyCalculator() {
    }

    public static String Calculate(String theString) {
        Scanner in = new Scanner(theString);
        while (in.hasNext()) {
            StackOperation(in.next());
        }
        //进行最后的计算环节
        while (!theCharStack.isEmpty()) {
            EmptyTheStack(theCharStack.pop());
        }
        return theDoubleStack.pop() + "";
    }

    /**
     * @param theString in.next()
     * @return 是否为运算符
     */
    private static boolean isOperator(String theString) {
        return theString.equals("+") || theString.equals("-") || theString.equals("*") || theString.equals("/");
    }

    /**
     * @param a 左操作数
     * @param b 右操作数
     * @param x 运算符
     * @return 运算结果
     */
    private static Double BinaryOperation(double a, double b, char x) {
        switch (x) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
        }
        return .0;
    }

    private static void StackOperation(String theString) {
        //匹配数字模板
        Matcher m = thePattern.matcher(theString);
        if (theString.equals("(")) {
            //5 若为“（”直接入栈，
            theCharStack.push('(');
        } else if (m.matches()) {
            //1 若为数字，则直接入栈
            theDoubleStack.push(Double.parseDouble(theString));
        } else if (isOperator(theString)) {
            //2 若为运算符，栈空则直接入栈 || 3 若为运算符，栈顶为"(" 则直接入栈
            if (theCharStack.isEmpty() || theCharStack.peek() == '(')
                theCharStack.push(theString.charAt(0));
                // 若为运算符，栈顶元素优先级大于此运算符，则取数字栈的两个数字
                // 与栈顶的运算符号进行计算，重新压入数字栈，符号栈出栈，
                // 直到遇到"("或者优先级小于或等于此运算符的栈顶元素为止
            else if (theString.charAt(0) == '+' || theString.charAt(0) == '-') {
                while (!theCharStack.isEmpty() && (theCharStack.peek() == '*'
                        || theCharStack.peek() == '/')) {
                    double b = theDoubleStack.pop();
                    double a = theDoubleStack.pop();
                    theDoubleStack.push(BinaryOperation(a, b, theCharStack.pop()));
                }
                theCharStack.push(theString.charAt(0));
            }else if(theString.charAt(0) == '*' || theString.charAt(0) == '/'){
                theCharStack.push(theString.charAt(0));
            }
        } else if (theString.equals(")")) {
            while (theCharStack.peek() != '(') {
                double b = theDoubleStack.pop();
                double a = theDoubleStack.pop();
                theDoubleStack.push(BinaryOperation(a, b, theCharStack.pop()));
            }
            theCharStack.pop();
        }
    }

    private static void EmptyTheStack(char ch){
        double b = theDoubleStack.pop();
        double a = theDoubleStack.pop();
        theDoubleStack.push(BinaryOperation(a, b, ch));
    }
}

