import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RedBlackTree {
	private static final String IN = "in";
	private static final String DEL = "del";
	private static Tree treeRoot = null;

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		System.out.println("Enter your input file name: ");
		String fileName = in.nextLine();
		String filecontents = getFileContents(fileName);
		System.out.println("Display the input before: " + filecontents);
		System.out.println("Display the output after the operation: ");
		String[] parts = filecontents.split("\\s+");
		for (int index = 0; index < parts.length; index++) {
			treeRoot = process(parts[index]);
		}
		in.close();
	}

	private static Tree process(String input) {
		String[] parts = input.split("\\.");
		if (parts.length == 2) {
			int number = Integer.valueOf(parts[0]);
			String operation = parts[1];
			if (operation.equals(IN)) {
				System.out.println("\nInserting " + number);
				Tree newNode = new Tree(number);
				insertNodeInTree(newNode);
				printTree(treeRoot);
			} else if (operation.equals(DEL)) {
				System.out.println("\nDeleting " + number);
				if (!deleteNodeInTree(new Tree(number))) {
					System.out.println("The key " + number + " doesn't exist.");
				} else {
					System.out.println(number + " deleted successfully.");
				}
				printTree(treeRoot);
			} else {
				System.out.println("\nInput is incorrect: " + input);
			}
		}
		return treeRoot;
	}

	private static String getFileContents(String fileName) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String st;
			while ((st = br.readLine()) != null) {
				sb.append(st);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static void insertNodeInTree(Tree newNode) {
		Tree temp = treeRoot;
		if (treeRoot == null) {
			treeRoot = newNode;
			newNode.setColor(Color.BLACK);
		} else {
			newNode.setColor(Color.RED);
			while (true) {
				if (newNode.getInfo() < temp.getInfo()) {
					if (temp.getLeftChild() == null) {
						temp.setLeftChild(newNode);
						newNode.setfatherNode(temp);
						break;
					} else {
						temp = temp.getLeftChild();
					}
				} else if (newNode.getInfo() > temp.getInfo()) {
					if (temp.getRightChild() == null) {
						temp.setRightChild(newNode);
						newNode.setfatherNode(temp);
						break;
					} else {
						temp = temp.getRightChild();
					}
				} else {
					System.out.println("Duplicate key : " + newNode.getInfo());
					return;
				}
			}
			recolorAndBalanceTree(newNode);
		}
	}

	private static void recolorAndBalanceTree(Tree newNode) {
		Tree forefather = null;
		Tree father = null;
		Tree kinsman = null;
		while (newNode != null && newNode.getfatherNode() != null
				&& newNode.getfatherNode().getColor().equals(Color.RED)) {
			father = newNode.getfatherNode();
			forefather = father.getfatherNode();
			if (forefather == null) {
				break;
			}
			if (father == forefather.getLeftChild()) {
				kinsman = forefather.getRightChild();
				if (kinsman != null && kinsman.getColor().equals(Color.RED)) {
					father.setColor(Color.BLACK);
					kinsman.setColor(Color.BLACK);
					forefather.setColor(Color.RED);
					newNode = forefather;
					continue;
				}
				if (newNode == father.getRightChild()) {
					newNode = father;
					needToRotateToTheLeft(newNode);
				}
				father.setColor(Color.BLACK);
				forefather.setColor(Color.RED);
				needToRotateToTheRight(forefather);
			} else {
				kinsman = forefather.getLeftChild();
				if (kinsman != null && kinsman.getColor().equals(Color.RED)) {
					father.setColor(Color.BLACK);
					kinsman.setColor(Color.BLACK);
					forefather.setColor(Color.RED);
					newNode = forefather;
					continue;
				}
				if (newNode == father.getLeftChild()) {
					newNode = father;
					needToRotateToTheRight(newNode);
				}
				father.setColor(Color.BLACK);
				forefather.setColor(Color.RED);
				needToRotateToTheLeft(forefather);
			}
		}
		treeRoot.setColor(Color.BLACK);
	}

	private static void needToRotateToTheLeft(Tree tempNode) {
		if (tempNode.getfatherNode() != null) {
			if (tempNode == tempNode.getfatherNode().getLeftChild()) {
				tempNode.getfatherNode().setLeftChild(tempNode.getRightChild());
			} else {
				tempNode.getfatherNode().setRightChild(tempNode.getRightChild());
			}
			tempNode.getRightChild().setfatherNode(tempNode.getfatherNode());
			tempNode.setfatherNode(tempNode.getRightChild());
			if (tempNode.getRightChild().getLeftChild() != null) {
				tempNode.getRightChild().getLeftChild().setfatherNode(tempNode);
			}
			tempNode.setRightChild(tempNode.getRightChild().getLeftChild());
			tempNode.getfatherNode().setLeftChild(tempNode);
		} else {
			Tree rightChild = treeRoot.getRightChild();
			treeRoot.setRightChild(rightChild.getLeftChild());
			if (rightChild.getLeftChild() != null) {
				rightChild.getLeftChild().setfatherNode(treeRoot);
			}
			treeRoot.setfatherNode(rightChild);
			rightChild.setLeftChild(treeRoot);
			rightChild.setfatherNode(null);
			treeRoot = rightChild;
		}
	}

	private static void needToRotateToTheRight(Tree tempNode) {
		if (tempNode.getfatherNode() != null) {
			if (tempNode == tempNode.getfatherNode().getLeftChild()) {
				tempNode.getfatherNode().setLeftChild(tempNode.getLeftChild());
			} else {
				tempNode.getfatherNode().setRightChild(tempNode.getLeftChild());
			}

			tempNode.getLeftChild().setfatherNode(tempNode.getfatherNode());
			tempNode.setfatherNode(tempNode.getLeftChild());
			if (tempNode.getLeftChild().getRightChild() != null) {
				tempNode.getLeftChild().getRightChild().setfatherNode(tempNode);
			}
			tempNode.setLeftChild(tempNode.getLeftChild().getRightChild());
			tempNode.getfatherNode().setRightChild(tempNode);
		} else {
			Tree leftChild = treeRoot.getLeftChild();
			treeRoot.setLeftChild(treeRoot.getLeftChild().getRightChild());
			if (leftChild.getRightChild() != null) {
				leftChild.getRightChild().setfatherNode(treeRoot);
			}
			treeRoot.setfatherNode(leftChild);
			leftChild.setRightChild(treeRoot);
			leftChild.setfatherNode(null);
			treeRoot = leftChild;
		}
	}

	private static Tree getkinsman(Tree root) {
		if (root == null) {
			return null;
		}

		Tree father = root.getfatherNode();
		if (father == null) {
			return null;
		}
		try {
			Tree forefather = father.getfatherNode();
			if (forefather.getLeftChild().equals(father)) {
				return forefather.getRightChild();
			}
			return forefather.getLeftChild();
		} catch (NullPointerException e) {
			return null;
		}
	}

	private static void manipulateTree(Tree destination, Tree from) {
		if (destination.getfatherNode() == null) {
			treeRoot = from;
		} else if (destination == destination.getfatherNode().getLeftChild()) {
			destination.getfatherNode().setLeftChild(from);
		} else {
			destination.getfatherNode().setRightChild(from);
		}
		if (from != null) {
			from.setfatherNode(destination.getfatherNode());
		}
	}

	private static Tree getLeftmostNode(Tree tempNode) {
		while (tempNode.getLeftChild() != null) {
			tempNode = tempNode.getLeftChild();
		}
		return tempNode;
	}

	private static boolean deleteNodeInTree(Tree nodeToDelete) {
		nodeToDelete = searchNode(nodeToDelete, treeRoot);
		if (nodeToDelete == null) {
			return false;
		}
		Tree tempNode;
		Tree ptrNode = nodeToDelete;
		Color trueColorOfNode = ptrNode.getColor();
		if (nodeToDelete.getLeftChild() == null) {
			tempNode = nodeToDelete.getRightChild();
			manipulateTree(nodeToDelete, nodeToDelete.getRightChild());
		} else if (nodeToDelete.getRightChild() == null) {
			tempNode = nodeToDelete.getLeftChild();
			manipulateTree(nodeToDelete, nodeToDelete.getLeftChild());
		} else {
			ptrNode = getLeftmostNode(nodeToDelete.getRightChild());
			trueColorOfNode = ptrNode.getColor();
			tempNode = ptrNode.getRightChild();
			if (ptrNode.getfatherNode() == nodeToDelete) {
				if (tempNode != null) {
					tempNode.setfatherNode(ptrNode);
				}
			} else {
				manipulateTree(ptrNode, ptrNode.getRightChild());
				ptrNode.setRightChild(nodeToDelete.getRightChild());
				ptrNode.getRightChild().setfatherNode(ptrNode);
			}
			manipulateTree(nodeToDelete, ptrNode);
			ptrNode.setLeftChild(nodeToDelete.getLeftChild());
			ptrNode.getLeftChild().setfatherNode(ptrNode);
			ptrNode.setColor(nodeToDelete.getColor());
		}
		if (trueColorOfNode == Color.BLACK) {
			recolorAndBalanceTreeDelete(tempNode);
		}
		return true;
	}

	private static void recolorAndBalanceTreeDelete(Tree tempNode) {
		while (tempNode != treeRoot && tempNode.getColor() == Color.BLACK) {
			if (tempNode == tempNode.getfatherNode().getLeftChild()) {
				Tree tempRightNode = tempNode.getfatherNode().getRightChild();
				if (tempRightNode.getColor() == Color.RED) {
					tempRightNode.setColor(Color.BLACK);
					tempNode.getfatherNode().setColor(Color.RED);
					needToRotateToTheLeft(tempNode.getfatherNode());
					tempRightNode = tempNode.getfatherNode().getRightChild();
				}
				if (tempRightNode.getLeftChild().getColor() == Color.BLACK
						&& tempRightNode.getRightChild().getColor() == Color.BLACK) {
					tempRightNode.setColor(Color.RED);
					tempNode = tempNode.getfatherNode();
					continue;
				} else if (tempRightNode.getRightChild().getColor() == Color.BLACK) {
					tempRightNode.getLeftChild().setColor(Color.BLACK);
					tempRightNode.setColor(Color.RED);
					needToRotateToTheRight(tempRightNode);
					tempRightNode = tempNode.getfatherNode().getRightChild();
				}
				if (tempRightNode.getRightChild().getColor() == Color.RED) {
					tempRightNode.setColor(tempNode.getfatherNode().getColor());
					tempNode.getfatherNode().setColor(Color.BLACK);
					tempRightNode.getRightChild().setColor(Color.BLACK);
					needToRotateToTheLeft(tempNode.getfatherNode());
					tempNode = treeRoot;
				}
			} else {
				Tree tempLeftNode = tempNode.getfatherNode().getLeftChild();
				if (tempLeftNode.getColor() == Color.RED) {
					tempLeftNode.setColor(Color.BLACK);
					tempNode.getfatherNode().setColor(Color.RED);
					needToRotateToTheRight(tempNode.getfatherNode());
					tempLeftNode = tempNode.getfatherNode().getLeftChild();
				}
				if (tempLeftNode.getRightChild().getColor() == Color.BLACK
						&& tempLeftNode.getLeftChild().getColor() == Color.BLACK) {
					tempLeftNode.setColor(Color.RED);
					tempNode = tempNode.getfatherNode();
					continue;
				} else if (tempLeftNode.getLeftChild().getColor() == Color.BLACK) {
					tempLeftNode.getRightChild().setColor(Color.BLACK);
					tempLeftNode.setColor(Color.RED);
					needToRotateToTheLeft(tempLeftNode);
					tempLeftNode = tempNode.getfatherNode().getLeftChild();
				}
				if (tempLeftNode.getLeftChild().getColor() == Color.RED) {
					tempLeftNode.setColor(tempNode.getfatherNode().getColor());
					tempNode.getfatherNode().setColor(Color.BLACK);
					tempLeftNode.getLeftChild().setColor(Color.BLACK);
					needToRotateToTheRight(tempNode.getfatherNode());
					tempNode = treeRoot;
				}
			}
		}
		tempNode.setColor(Color.BLACK);
	}

	private static Tree searchNode(Tree findThisNode, Tree treeRoot) {
		if (treeRoot == null) {
			return null;
		}
		if (findThisNode.getInfo() < treeRoot.getInfo()) {
			if (treeRoot.getLeftChild() != null) {
				return searchNode(findThisNode, treeRoot.getLeftChild());
			}
		} else if (findThisNode.getInfo() > treeRoot.getInfo()) {
			if (treeRoot.getRightChild() != null) {
				return searchNode(findThisNode, treeRoot.getRightChild());
			}
		} else if (findThisNode.getInfo() == treeRoot.getInfo()) {
			return treeRoot;
		}
		return null;
	}

	/**
	 * prints in order traversal of a tree
	 * 
	 * @param root
	 */
	private static void printTree(Tree root) {
		if (root == null) {
			return;
		}
		printTree(root.getLeftChild());
		System.out.print(root.getInfo() + root.getColor().toString().substring(0, 1) + ", ");
		printTree(root.getRightChild());
	}
}

class Tree {
	private Tree leftChild;
	private Tree rightChild;
	private Tree fatherNode;
	private Color color;
	private Type type;
	private int info;
	private int height;

	Tree(int info) {
		this.info = info;
		this.leftChild = null;
		this.rightChild = null;
		this.fatherNode = null;
		this.color = Color.RED;
		this.type = Type.NODE;
		this.height = 0;
	}

	public Tree getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Tree leftChild) {
		this.leftChild = leftChild;
	}

	public Tree getRightChild() {
		return rightChild;
	}

	public void setRightChild(Tree rightChild) {
		this.rightChild = rightChild;
	}

	public Tree getfatherNode() {
		return fatherNode;
	}

	public void setfatherNode(Tree fatherNode) {
		this.fatherNode = fatherNode;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getInfo() {
		return info;
	}

	public void setInfo(int info) {
		this.info = info;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return this.info + "[" + this.getLeftChild() + ", " + this.getRightChild() + "]" + ",";
	}
}

enum Color {
	RED, BLACK;
}

enum Type {
	NODE, LEAF;
}