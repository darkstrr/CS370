package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class PrimSearch {

	public static void main(String[] args) {
		// initialize variables
		Scanner scan = new Scanner(System.in);
		int maxSize = 0;
		int currSize = 0;
		int maxStates = 0;
		int currStates = 0;
		int nums = 0;
		String temp = "";
		String top = "";
		String bottom = "";
		String path = "";
		boolean notdone = true;
		boolean poss = true;
		boolean prints = false;
		int temps = 0;

		ArrayList<DomiNode> frontier = new ArrayList<DomiNode>();
		Queue<DomiNode> current = new LinkedList<DomiNode>();
		DomiNode tempNode = new DomiNode();
		DomiNode currNode = new DomiNode();

		// fill out the limits for search
		System.out.println("Enter maximum size of the queue used in breadth-first search:");
		maxSize = scan.nextInt();
		System.out.println("Enter maximum number of states to search:");
		maxStates = scan.nextInt();
		System.out.println("Enter 1 to print states generated");
		temps = scan.nextInt();
		if (temps == 1)
			prints = true;
		System.out.println("Enter number of dominoes: ");
		nums = scan.nextInt();
		String[][] dominoes = new String[nums][2];
		System.out.println("Enter dominoes");
		temp = scan.nextLine();
		for (int i = 0; i < nums; i++) {
			System.out.println("Enter top of domino: ");
			dominoes[i][0] = scan.nextLine();
			System.out.println("Enter bottom of domino: ");
			dominoes[i][1] = scan.nextLine();
		}
		/*
		 * for (int i = 0; i< 3; i++) for (int j=0; j< 2; j++)
		 * System.out.println(dominoes[i][j]);
		 */

		/*
		 * 1st domino is C1 2nd domino is C2 3rd domino is C3
		 */

		// populate the queue with the initial states
		for (int i = 0; i < dominoes.length; i++) {
			tempNode = new DomiNode();
			tempNode.setInfo(dominoes[i][0], dominoes[i][1]);
			tempNode.setPath("C" + (i + 1));
			frontier.add(tempNode);
			current.add(tempNode);
			currStates++;
			currSize++;
		}
		/*
		 * for (int i = 0; i< 3; i++) { currNode = current.remove();
		 * System.out.println(currNode.getTop() + currNode.getBottom());
		 * System.out.println(currNode.getPath()); }
		 */

		// breadth first search
		while (notdone && currStates < maxStates && currSize < maxSize && frontier.size() != 0) {
			currNode = current.remove();
			// print nodes if requested
			// check if the current node is a solution
			if (currNode.isSolution()) {
				System.out.print("Solution size: " + currNode.getSize());
				System.out.println(" (Solution found in Stage 1)");
				System.out.println("The solution sequence is: " + currNode.getPath());
				System.out.println("The top and bottom of the sequence looks like: " + currNode.getTop() + "/"
						+ currNode.getBottom());
				notdone = false;
				break;
			}

			// get the top and bottom strings
			top = currNode.getTop();
			bottom = currNode.getBottom();

			// if still a valid sequence, set new nodes off of current Node
			if (top.contains(bottom) || bottom.contains(top)) {
				// get existing strings from currNode
				path = currNode.getPath();
				top = currNode.getTop();
				bottom = currNode.getBottom();

				for (int i = 0; i < dominoes.length; i++) {
					tempNode = new DomiNode();
					tempNode.setInfo(top + dominoes[i][0], bottom + dominoes[i][1]);
					tempNode.setPath(path + "C" + (i + 1));
					tempNode.setSize(currNode.getSize() + 1);
					frontier.add(tempNode);
					current.add(tempNode);
					currStates++;
					currSize++;

					if (prints) {
						System.out.println("top: " + tempNode.getTop());
						System.out.println("bottom: " + tempNode.getBottom());
						System.out.println("path: " + tempNode.getPath());
					}
					if (tempNode.isSolution()) {
						System.out.print("Solution size: " + tempNode.getSize());
						System.out.println(" (Solution found in Stage 1)");
						System.out.println("The solution sequence is: " + tempNode.getPath());
						System.out.println("The top and bottom of the sequence looks like: " + tempNode.getTop() + "/"
								+ tempNode.getBottom());
						notdone = false;
						break;
					}
				}
			}
			// if not valid, remove from frontier
			else {
				frontier.remove(currNode);
				currSize--;
			}
		}
		// checks whether failed b/c of solution or overflow
		if (frontier.size() == 0) {
			System.out.println("Failed, no solution exists");
			poss = false;
		} else if (notdone)
			System.out.println("Failed, too many states\n");

		// part 2: iterative deepening
		if (notdone) {

			currStates = 0;
			currSize = 0;

			System.out.println("Starting Part 2 iterative deepening");
			// create stack used for iterative deepening
			Stack<DomiNode> front = new Stack<DomiNode>();

			// add Nodes from the queue into the stack
			ArrayList<DomiNode> temp1 = new ArrayList<DomiNode>();
			while (!current.isEmpty()) {
				temp1.add(current.remove());
			}
			for (int i = temp1.size() - 1; i >= 0; i--) {
				front.push(temp1.get(i));
			}
			while (front.size() < maxSize && frontier.size() != 0 && poss && notdone && !front.isEmpty()) {
				currNode = front.pop();

				// print nodes if requested
				if (prints) {
					System.out.println("top: " + currNode.getTop());
					System.out.println("bottom: " + currNode.getBottom());
					System.out.println("path: " + currNode.getPath());
				}

				// check if the current node is a solution
				if (currNode.isSolution()) {
					System.out.print("Solution size: " + currNode.getSize());
					System.out.println(" (Solution found in Stage 2)");
					System.out.println("The solution sequence is: " + currNode.getPath());
					System.out.println("The top and bottom of the sequence looks like: " + currNode.getTop() + "/"
							+ currNode.getBottom());
					notdone = false;
					break;
				}

				// get the top and bottom strings
				top = currNode.getTop();
				bottom = currNode.getBottom();

				// if still a valid sequence, set new nodes off of current Node
				if (top.contains(bottom) || bottom.contains(top)) {
					path = currNode.getPath();
					for (int i = dominoes.length - 1; i >= 0; i--) {
						tempNode = new DomiNode();
						tempNode.setInfo(top + dominoes[i][0], bottom + dominoes[i][1]);
						tempNode.setPath(path + "C" + (i + 1));
						tempNode.setSize(currNode.getSize() + 1);
						frontier.add(tempNode);
						front.push(tempNode);
						currStates++;
						currSize++;
					}
				} else {
					frontier.remove(currNode);
					currSize--;
				}
			}
			if (frontier.size() == 0 || !poss || front.isEmpty()) {
				System.out.println("Failed, no solution exists");
				poss = false;
			} else if (notdone)
				System.out.println("Failed, too many states");
		}

	}
}