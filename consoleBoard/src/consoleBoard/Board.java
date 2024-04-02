package consoleBoard;

import java.util.HashMap;
import java.util.Map;

public class Board {

	private String title;
	private String text;
	private int index;
	private int pageSize;
	private int curPageNum;
	private int pageCount;
	private int startRow;
	private int endRow;
	private Map<Integer, Board> boards = new HashMap<>();

	public Board(String title, String text) {
		this.title = title;
		this.text = text;
	}

	public Board() {
		this.index = 0;
		this.pageSize = 5;
		this.curPageNum = 1;
		this.pageCount = 1;
		this.startRow = 0;
		this.endRow = 0;
	}

	public String getTitle() {
		return this.title;
	}

	public String getText() {
		return this.text;
	}

	public Board clone() {
		Board board = new Board(this.title, this.text);
		return board;
	}

	public void setBoard(String title, String text) {
		Board board = new Board(title, text);

		boards.put(index, board);
		index++;
		pageCount = index % pageSize > 0 ? index / pageSize + 1 : index / pageSize;
		startRow = (curPageNum - 1) * pageSize;
		endRow = curPageNum * pageSize >= index ? index : curPageNum * pageSize;
	}

	public Board findBoard(String title) {
		for (int i = 0; i < index; i++) {
			if (title.equals(boards.get(i).getTitle()))
				return boards.get(i);
		}
		return null;
	}
	
	public int findBoardIndex(String title) {
		for (int i = 0; i < index; i++) {
			if (title.equals(boards.get(i).getTitle()))
				return i;
		}
		return -1;
	}

	public void removeBoard(int idx) {
		if (idx != -1) {
			boards.remove(idx);
			for (int i = idx; i < index - 1; i++) {
				boards.put(i, boards.get(i + 1));
			}
			index--;
			pageCount = index % pageSize > 0 ? index / pageSize + 1 : index / pageSize;
			startRow = (curPageNum - 1) * pageSize;
			endRow = curPageNum * pageSize >= index ? index : curPageNum * pageSize;
		}
	}

	public void printBoard(int index) {
		if (boards.get(index - 1) != null && index - 1 >= startRow && index - 1 < endRow)
			System.out.println(boards.get(index - 1));
		else {
			System.err.println("일치하는 인덱스가 없습니다.");
			return;
		}
	}

	public void printAll() {
		if (endRow != 0) {
			System.out.println("-------------");
			for (int i = startRow; i < endRow; i++) {
				System.out.printf("%d)%s\n", i + 1, boards.get(i).getTitle());
			}
			System.out.printf("[%d]/[%d]\n", curPageNum, pageCount);
			System.out.println("-------------");
		}
		System.out.println(index);
	}

	public void turnNext() {
		if (curPageNum < pageCount) {
			curPageNum++;
			startRow = (curPageNum - 1) * pageSize;
			endRow = curPageNum * pageSize >= index ? index : curPageNum * pageSize;
		}
	}

	public void turnPrev() {
		if (curPageNum > 1) {
			curPageNum--;
			startRow = (curPageNum - 1) * pageSize;
			endRow = curPageNum * pageSize >= index ? index : curPageNum * pageSize;
		}
	}

	@Override
	public String toString() {
		String info = title;
		info += "\n=================\n";
		info += text;
		return info;
	}
}
