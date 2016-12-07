package calc;

import java.util.EmptyStackException;
import java.util.Stack;

public class Calc {
	private int cs = 0;
	int[][] auto = { { 2, -1, 1, -1 }, { 2, -1, 1, -1 }, { 2, 3, -1, 4 },
			{ 2, -1, 1, -1 }, { -1, 3, -1, -1 } };
	boolean u = true;
	public Stack<Integer> si = new Stack<Integer>();
	public Stack<String> ss = new Stack<String>();

	public static void main(String[] args) throws FormulaException {
		Calc c = new Calc();
		Inputt i = new Inputt();
		String o = i.readCom();
		while (!o.equals("exit")) {
			if (!i.parantez(o)) {
				System.out.println("parantez error");
				o = i.readCom();
			}
			while (o.length() > 0 && c.u) {
				String[] e = i.calcCom(o);
				o = i.charDelete(e[0].length(), o).trim();
				try {
					c.rEvent(e);
					// System.out.println(c.ss);
					// System.out.println(c.si);
					// System.out.println("----------------------");
				} catch (FormulaException uu) {
					System.out.println("Formula is not valid");
					c.u = false;
					break;
				}
			}
			if (c.u == true)
				while (c.calculate())
					;
			while (!c.si.isEmpty())
				c.si.pop();
			while (!c.ss.isEmpty())
				c.ss.pop();
			c.u = true;
			c.cs = 0;
			o = i.readCom();
		}
	}

	private void rEvent(String[] e) throws FormulaException {
		try {
			this.cs = this.auto[cs][Integer.parseInt(e[1])];
		} catch (NumberFormatException w) {
			throw (new FormulaException());
		}
		if (cs < 0)
			throw (new FormulaException());
		doEvent(e);
	}

	private void doEvent(String[] e) {
		switch (e[1].charAt(0)) {
		case '0': {
			Integer a = Integer.parseInt(e[0]);
			si.push(a);
			break;
		}
		case '1': {
			if (ss.isEmpty()) {
				ss.push(e[0]);
				break;
			}
			char a = ss.peek().charAt(0);
			if (!(a == '*' && e[0].charAt(0) == '+')
					&& !(a == '*' && e[0].charAt(0) == '-')
					&& !(a == '+' && e[0].charAt(0) == '-')
					&& !(a == '-' && e[0].charAt(0) == '+')) {
				ss.push(e[0]);
				break;
			} else {
				calculate();
				doEvent(e);
				break;
			}
		}
		case '2': {
			ss.push(e[0]);
			break;
		}
		case '3': {
			while (calculate())
				;
		}
		}

	}

	private boolean calculate() {
		if (ss.isEmpty()) {
			try {
				System.out.println(si.peek());
			} catch (EmptyStackException e) {
			}
			this.u = false;
			return false;
		} else if (ss.peek().charAt(0) == '(') {
			ss.pop();
			return false;
		}
		int a1 = si.pop();
		int a2 = 0;
		try {
			a2 = si.pop();
		} catch (EmptyStackException e) {
			System.out.println(a1);
			return false;
		}
		String e = ss.pop();
		char c = e.charAt(0);
		if (c == '+') {
			si.push(a1 + a2);
			return true;
		} else if (c == '*') {
			si.push(a1 * a2);
			return true;
		} else if (c == '-') {
			si.push(a2 - a1);
			return true;
		} else {
			si.push(a2);
			si.push(a1);
			return false;
		}
	}

}

class FinishExeption extends Exception {

	private static final long serialVersionUID = 1L;

}

class FormulaException extends Exception {

	private static final long serialVersionUID = 1L;

}