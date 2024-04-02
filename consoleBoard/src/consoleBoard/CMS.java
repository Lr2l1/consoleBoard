package consoleBoard;

import java.util.Scanner;

public class CMS {
	private final int USER = 1;
	private final int BOARD = 2;

	private final int JOIN = 1;
	private final int LEAVE = 2;
	private final int LOG_IN = 3;
	private final int LOG_OUT = 4;

	private final int NEXT = 1;
	private final int PREV = 2;
	private final int VIEW = 3;
	private final int WRITE = 4;
	private final int DELETE = 5;

	private Scanner scan = new Scanner(System.in);
	private String log;
	private User group = new User();
	private Board boards = new Board();

	public CMS() {
		this.log = null;
	}

	private int inputNumber(String message) {
		int number = -1;
		System.out.print(message + " : ");
		try {
			String input = scan.next();
			number = Integer.parseInt(input);
		} catch (Exception e) {
			System.err.println("숫자만 입력해주세요");
		}
		return number;
	}

	private String inputString(String message) {
		System.out.print(message + " : ");
		return scan.next();
	}

	private int option() {
		return inputNumber("메뉴");
	}

	private boolean isLogin() {
		if (log != null)
			return true;

		System.err.println("로그인 후 사용해주세요.");
		return false;
	}

	private boolean isLogout() {
		if (log == null)
			return true;

		System.err.println("로그아웃 후 사용해주세요.");
		return false;
	}

	private void printMenu() {
		System.out.println("[1]유저메뉴");
		System.out.println("[2]게시판메뉴");
	}

	private void runMenu(int select) {
		switch (select) {
		case (USER):
			printUserMenu();
			runUserMenu(option());
			break;
		case (BOARD):
			printBoardMenu();
			runBoardMenu(option());
			break;
		}
	}

	private void printUserMenu() {
		System.out.println("[1]회원가입");
		System.out.println("[2]회원탈퇴");
		System.out.println("[3]로그인");
		System.out.println("[4]로그아웃");
	}

	private void runUserMenu(int select) {
		if (select == JOIN && isLogout())
			join();
		else if (select == LEAVE && isLogin())
			leave();
		else if (select == LOG_IN && isLogout())
			login();
		else if (select == LOG_OUT && isLogin())
			logout();
	}

	private void join() {
		String id = inputString("ID");
		String pw = inputString("PW");

		if (group.hasUserId(id)) {
			System.err.println("중복된 아이디입니다.");
			return;
		}

		String name = inputString("닉네임 입력");
		group.setUser(name, id, pw);

	}

	private void leave() {
		String pw = inputString("비밀번호를 입력해주세요.");

		if (group.findUser(log, pw) == null) {
			System.out.println("잘못된 비밀번호입니다.");
			return;
		}

		group.removeUser(log);
	}

	private void login() {
		String id = inputString("ID");
		String pw = inputString("PW");

		if (group.findUser(id, pw) == null) {
			System.out.println("잘못된 회원정보입니다.");
			return;
		}

		User user = group.findUser(id, pw);
		log = group.findName(user);
		System.out.printf("%s회원님 로그인을 환영합니다.\n", log);
	}

	private void logout() {
		log = null;
		System.out.println("로그아웃되었습니다.");
	}

	private void printBoardMenu() {
		boards.printAll();
		System.out.println("[1]다음페이지");
		System.out.println("[2]이전페이지");
		System.out.println("[3]조회하기");
		System.out.println("[4]글작성");
		System.out.println("[5]글삭제");
	}

	private void runBoardMenu(int select) {
		boards.printAll();
		if (select == NEXT)
			next();
		else if (select == PREV)
			prev();
		else if (select == VIEW)
			view();
		else if (select == WRITE && isLogin())
			write();
		else if (select == DELETE && isLogin())
			delete();
	}

	private void next() {
		boards.turnNext();
	}

	private void prev() {
		boards.turnPrev();
	}

	private void view() {
		int index = inputNumber("글번호");
		boards.printBoard(index);
	}

	private void write() {
		String title = inputString("글제목");
		String text = inputString("글내용");

		User user = group.findUser(log);
		boards.setBoard(title, text);
		Board board = boards.findBoard(title);

		user.setBoard(log, board);
	}

	private void delete() {
		User user = group.findUser(log);

		user.printMyBoard(log);
		user.removeMyBoard(log);
		
	}

	public void run() {
		while (true) {
			printMenu();
			int sel = inputNumber("메뉴");
			runMenu(sel);
		}
	}
}
