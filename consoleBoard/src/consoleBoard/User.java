package consoleBoard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class User {
	private String id;
	private String password;
	private Board boards = new Board();
	private Map<String, User> group = new HashMap<>();
	private Map<User, ArrayList<Board>> myBoard = new HashMap<>();
	private Scanner scan = new Scanner(System.in);

	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public User() {

	}

	public String getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public User clone() {
		User user = new User(this.id, this.password);
		return user;
	}

	private String inputString(String message) {
		System.out.print(message + " : ");
		return scan.next();
	}

	public void setUser(String name, String id, String password) {
		if (group.get(name) != null) {
			System.out.println("중복된 닉네임입니다.");
			return;
		}

		User user = new User(id, password);
		group.put(name, user);

		ArrayList<Board> consoleBaord = new ArrayList<Board>();
		myBoard.put(user, consoleBaord);
		System.out.println("회원가입을 축하합니다.");
	}

	public boolean hasUserId(String id) {
		List keySet = new ArrayList(group.keySet());
		for (Object key : keySet) {
			if (id.equals(group.get(key).getId()))
				return true;
		}
		return false;

	}

	public User findUser(String id, String password) {
		List keySet = new ArrayList(group.keySet());
		for (Object key : keySet) {
			if (id.equals(group.get(key).getId()) && password.equals(group.get(key).getPassword()))
				return group.get(key);
		}
		return null;
	}

	public User findUser(String name) {
		return group.get(name);
	}

	public String findName(User user) {
		List keySet = new ArrayList(group.keySet());
		for (Object key : keySet) {
			if (user.equals(group.get(key)))
				return (String) key;
		}

		return null;
	}

	public boolean hasMyBoard(User user) {
		if (myBoard.get(user) == null)
			return false;

		return true;
	}

	public void removeUser(String name) {
		group.remove(name);
	}

	public void setBoard(String name, Board board) {
		User user = group.get(name);
		if (myBoard.get(user) == null) {
			ArrayList<Board> consoleBaord = new ArrayList<Board>();
			consoleBaord.add(board);
			myBoard.put(user, consoleBaord);
		} else if (myBoard.get(user) != null) {
			ArrayList<Board> consoleBaord = myBoard.get(user);
			consoleBaord.add(board);
			myBoard.put(user, consoleBaord);
		}
	}

	public void removeMyBoard(String name) {
		User user = group.get(name);
		if (myBoard.get(user) == null || myBoard.get(user).size() == 0) {
			System.err.println("삭제할 글이 없습니다.");
			return;
		}

		String title = inputString("삭제하실 글제목을 입력해주세요.");

		ArrayList<Board> consoleBaord = myBoard.get(user);
		Board target = null;
		for (Board board : consoleBaord) {
			if (title.equals(board.getTitle()))
				target = board;
		}
		if (target != null) {
			int idx = boards.findBoardIndex(title);
			boards.removeBoard(idx);
			consoleBaord.remove(target);
			myBoard.put(user, consoleBaord);
			
			System.out.println("게시글 삭제 완료!");
		}

	}

	public void printMyBoard(String name) {
		User user = group.get(name);
		if (myBoard.get(user) == null) 
			return;
		

		for (Board board : myBoard.get(user))
			System.out.println(board.getTitle());
	}
}
